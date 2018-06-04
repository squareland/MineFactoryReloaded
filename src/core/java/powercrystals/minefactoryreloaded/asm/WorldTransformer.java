package powercrystals.minefactoryreloaded.asm;

import codechicken.lib.reflect.ObfMapping;
import com.google.common.base.Throwables;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.*;

import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

import static org.objectweb.asm.Opcodes.*;

public class WorldTransformer implements IClassTransformer {

	static final String worldServerProxy = "powercrystals/minefactoryreloaded/asmhooks/WorldServerProxy";
	static final String worldServer = "net/minecraft/world/WorldServer";
	static final String world = "net/minecraft/world/World";
	static final String serverSig;
	static final String worldSig;

	static {
		final String sigBody = "Lnet/minecraft/world/storage/ISaveHandler;" + "Lnet/minecraft/world/storage/WorldInfo;" +
				"Lnet/minecraft/world/WorldProvider;" + "Lnet/minecraft/profiler/Profiler;" + "Z";
		serverSig = "(Lnet/minecraft/server/MinecraftServer;" + sigBody + ")V";
		worldSig = "(" + sigBody + ")V";
	}

	private final static Object2IntOpenHashMap<String> transformerMap = new Object2IntOpenHashMap<>();
	static {
		transformerMap.put(worldServer.replace('/', '.'), 1);
		transformerMap.put(world.replace('/', '.'), 2);
		transformerMap.put(worldServerProxy.replace('/', '.'), 3);
	}

	{
		ObfMapping.init();
	}

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {

		switch (transformerMap.getInt(transformedName)) {
			case 1:
				return modifyWorldServer(basicClass);
			case 2:
				return modifyWorld(basicClass);
			case 3:
				return modifyWorldServerProxy(basicClass);
			case 0:
			default:
				break;
		}
		return basicClass;
	}

	private static void makeAllPublic(ClassNode cn) {

		for (MethodNode a : cn.methods) {
			a.access = (a.access & ~(ACC_PRIVATE | ACC_PROTECTED | ACC_FINAL)) | ACC_PUBLIC;
			// tweak access, but do not tweak calls: internal private calls remain calls to the method on the given class, and not a subclass
			// this catches reflection calls out, but leaves code calls unmolested
		}
	}

	private static byte[] modifyWorld(byte[] bytes) {

		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, ClassReader.EXPAND_FRAMES);

		makeAllPublic(cn);

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cn.accept(cw);
		bytes = cw.toByteArray();
		return bytes;
	}

	private static byte[] modifyWorldServer(byte[] bytes) {

		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, ClassReader.EXPAND_FRAMES);

		makeAllPublic(cn);

		addConstructor: {
			for (MethodNode method : cn.methods) {
				if ("<init>".equals(method.name) && serverSig.equals(method.desc))
					break addConstructor; // someone has created it for us
			}

			ObfMapping mcServer = new ObfMapping(worldServer, "field_73061_a", "Lnet/minecraft/server/MinecraftServer;").toRuntime();

			MethodVisitor mv = cn.visitMethod(ACC_PUBLIC | ACC_SYNTHETIC, "<init>", serverSig, null, null);
			mv.visitCode();
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 2);
			mv.visitVarInsn(ALOAD, 3);
			mv.visitVarInsn(ALOAD, 4);
			mv.visitVarInsn(ALOAD, 5);
			mv.visitVarInsn(ILOAD, 6);
			// [World] super(saveHandler, worldInfo, provider, theProfiler, isRemote);
			mv.visitMethodInsn(INVOKESPECIAL, world, "<init>", worldSig, false);
			mv.visitVarInsn(ALOAD, 0);
			mv.visitVarInsn(ALOAD, 1);
			mv.visitFieldInsn(PUTFIELD, mcServer.s_owner, mcServer.s_name, mcServer.s_desc);
			for (FieldNode field : cn.fields) {
				if (isFinal(field.access) && !field.name.equals(mcServer.s_name)) {
					mv.visitVarInsn(ALOAD, 0);
					Type fType = Type.getType(field.desc);
					switch (fType.getSort()) {
						case Type.METHOD:
						case Type.ARRAY:
						case Type.OBJECT:
							mv.visitInsn(ACONST_NULL);
							break;
						case Type.FLOAT:
							mv.visitInsn(FCONST_0);
							break;
						case Type.DOUBLE:
							mv.visitInsn(DCONST_0);
							break;
						case Type.LONG:
							mv.visitInsn(LCONST_0);
							break;
						default:
							mv.visitInsn(ICONST_0);
							switch (fType.getSort()) {
								case Type.SHORT:
									mv.visitInsn(I2S);
									break;
								case Type.CHAR:
									mv.visitInsn(I2C);
									break;
								case Type.BYTE:
									mv.visitInsn(I2B);
									break;
							}
							break;
						case Type.VOID: // void fields, mmm
							break;
					}
					mv.visitFieldInsn(PUTFIELD, worldServer, field.name, field.desc);
				}
			}
			mv.visitInsn(RETURN);
			mv.visitMaxs(7, 7);
			mv.visitEnd();
		}

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cn.accept(cw);
		bytes = cw.toByteArray();
		return bytes;
	}

	private static byte[] modifyWorldServerProxy(byte[] bytes) {

		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, ClassReader.EXPAND_FRAMES);

		cn.superName = worldServer;
		for (MethodNode m : cn.methods) {
			if ("<init>".equals(m.name)) {
				InsnList l = m.instructions;
				for (int i = 0, e = l.size(); i < e; i++) {
					AbstractInsnNode n = l.get(i);
					if (n instanceof MethodInsnNode) {
						MethodInsnNode mn = (MethodInsnNode) n;
						if (mn.getOpcode() == INVOKESPECIAL) {
							mn.owner = cn.superName;
							break;
						}
					}
				}
			}
		}

		Method[] worldServerMethods = null;
		try {
			worldServerMethods = net.minecraft.world.WorldServer.class.getDeclaredMethods();
		} catch (Throwable e) {
			Throwables.propagate(e);
		}
		Method[] worldMethods = null;
		try {
			worldMethods = net.minecraft.world.World.class.getDeclaredMethods();
		} catch (Throwable e) {
			Throwables.propagate(e);
		}

		for (Method m : worldMethods) {
			if (!Modifier.isStatic(m.getModifiers())) {
				String desc = Type.getMethodDescriptor(m);
				{
					cn.methods.removeIf(m2 -> m2.name.equals(m.getName()) && m2.desc.equals(desc));
				}
				MethodVisitor mv = cn.visitMethod(getAccess(m), m.getName(), desc, null, getExceptions(m));
				mv.visitCode();
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, cn.name, "proxiedWorld", "L" + worldServer + ";");
				Type[] types = Type.getArgumentTypes(m);
				for (int i = 0, w = 1, e = types.length; i < e; i++) {
					mv.visitVarInsn(types[i].getOpcode(ILOAD), w);
					w += types[i].getSize();
				}
				mv.visitMethodInsn(INVOKEVIRTUAL, world, m.getName(), desc, false);
				mv.visitInsn(Type.getReturnType(m).getOpcode(IRETURN));
				mv.visitMaxs(types.length + 1, types.length + 1);
				mv.visitEnd();
			}
		}

		for (Method m : worldServerMethods) {
			if (!Modifier.isStatic(m.getModifiers())) {
				String desc = Type.getMethodDescriptor(m);
				{
					cn.methods.removeIf(m2 -> m2.name.equals(m.getName()) && m2.desc.equals(desc));
				}
				MethodVisitor mv = cn.visitMethod(getAccess(m), m.getName(), desc, null, getExceptions(m));
				mv.visitCode();
				mv.visitVarInsn(ALOAD, 0);
				mv.visitFieldInsn(GETFIELD, cn.name, "proxiedWorld", "L" + worldServer + ";");
				Type[] types = Type.getArgumentTypes(m);
				for (int i = 0, w = 1, e = types.length; i < e; i++) {
					mv.visitVarInsn(types[i].getOpcode(ILOAD), w);
					w += types[i].getSize();
				}
				mv.visitMethodInsn(INVOKEVIRTUAL, worldServer, m.getName(), desc, false);
				mv.visitInsn(Type.getReturnType(m).getOpcode(IRETURN));
				mv.visitMaxs(types.length + 1, types.length + 1);
				mv.visitEnd();
			}
		}

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cn.accept(cw);
		bytes = cw.toByteArray();
		return bytes;
	}

	private static boolean isFinal(int access) {

		return 0 != (access & ACC_FINAL) && 0 == (access & ACC_STATIC);
	}

	private static int getAccess(Method m) {

		int r = m.getModifiers();
		r &= ~(ACC_PUBLIC | ACC_PRIVATE | ACC_PROTECTED | ACC_FINAL | ACC_BRIDGE | ACC_ABSTRACT);
		r |= ACC_PUBLIC | ACC_SYNTHETIC;
		return r;
	}

	private static String[] getExceptions(Method m) {

		Class<?>[] d = m.getExceptionTypes();
		if (d == null) {
			return null;
		}
		String[] r = new String[d.length];
		for (int i = 0; i < d.length; ++i) {
			r[i] = Type.getInternalName(d[i]);
		}
		return r;
	}

}
