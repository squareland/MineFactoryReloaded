package powercrystals.minefactoryreloaded.asm;

import codechicken.lib.reflect.ObfMapping;
import net.minecraft.launchwrapper.IClassTransformer;
import org.objectweb.asm.ClassReader;
import org.objectweb.asm.ClassWriter;
import org.objectweb.asm.MethodVisitor;
import org.objectweb.asm.Type;
import org.objectweb.asm.tree.ClassNode;
import org.objectweb.asm.tree.FieldNode;
import org.objectweb.asm.tree.MethodNode;

import static org.objectweb.asm.Opcodes.*;

public class WorldTransformer implements IClassTransformer {

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

	@Override
	public byte[] transform(String name, String transformedName, byte[] basicClass) {

		if (worldServer.equals(transformedName)) {
			return addWorldServerConstructor(basicClass);
		}
		return basicClass;
	}

	private static byte[] addWorldServerConstructor(byte[] bytes) {

		ClassReader cr = new ClassReader(bytes);
		ClassNode cn = new ClassNode();
		cr.accept(cn, ClassReader.EXPAND_FRAMES);
		for (MethodNode method : cn.methods) {
			if ("<init>".equals(method.name) && serverSig.equals(method.desc))
				return bytes; // someone has created it for us
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

		ClassWriter cw = new ClassWriter(ClassWriter.COMPUTE_FRAMES);
		cn.accept(cw);
		bytes = cw.toByteArray();
		return bytes;
	}

	private static boolean isFinal(int access) {

		return 0 != (access & ACC_FINAL) && 0 == (access & ACC_STATIC);
	}

}
