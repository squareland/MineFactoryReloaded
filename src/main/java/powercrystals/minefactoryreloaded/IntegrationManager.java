package powercrystals.minefactoryreloaded;

import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.discovery.ASMDataTable;
import net.minecraftforge.fml.common.discovery.ModCandidate;
import org.apache.logging.log4j.Level;
import org.objectweb.asm.Type;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.modhelpers.vanilla.Minecraft;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

import java.io.File;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

import static powercrystals.minefactoryreloaded.MineFactoryReloadedCore.log;

public class IntegrationManager {

	private static LinkedList<IntegrationContainer> integrationSets = new LinkedList<>();

	public static void load(ASMDataTable data) {

		for (ASMDataTable.ASMData obj : data.getAll(Type.getInternalName(IMFRIntegrator.class))) {
			integrationSets.add(new IntegrationContainer(obj));
		}
	}

	private static void iterate(Consumer<IntegrationContainer> consumer, String error) {

		for (IntegrationContainer container : integrationSets) {
			if (container.enabled) {
				try {
					consumer.accept(container);
				} catch (Throwable t) {
					container.enabled = false;
					log().error(error, container.name, container.mod);
					log().catching(Level.ERROR, t);
				}
			}
		}
	}

	public static void configure(final Function<String, File> getConfig) {

		for (IntegrationContainer container : integrationSets) {
			if (container.integrator != null) {
				container.enabled = MFRConfig.isIntegrationEnabled(container.name, container.mod);
			} else {
				container.enabled = false;
			}
		}
		final String path = "integration/";
		getConfig.apply(path + "common").getParentFile().mkdirs();
		iterate(c -> c.integrator.readConfig(getConfig.apply(path + c.name)), "Error reading config for MFR integration {} from {}");
	}

	public static void preInit() {

		iterate(c -> c.integrator.preLoad(), "Error during pre-initialization for MFR integration {} from {}");
	}

	public static void init() {

		iterate(c -> c.integrator.load(), "Error during initialization for MFR integration {} from {}");
	}

	public static void postInit() {

		iterate(c -> c.integrator.postLoad(), "Error during post-initialization for MFR integration {} from {}");
	}

	public static void completeInit() {

		iterate(c -> c.integrator.completeLoad(), "Error during load complete for MFR integration {} from {}");
		integrationSets.clear();
		integrationSets = null;
	}

	private static class IntegrationContainer {

		private static HashSet<String> createdSets = new HashSet<>();

		private static String asString(ModCandidate candidate) {

			String str = null;

			List<ModContainer> data = candidate.getContainedMods();

			if (data.size() == 1) {
				str = data.get(0).getName();
				if (str != null && str.length() > 60)
					str = null; // that's a long name, we'll use the jar name thanks
			}

			if (str == null) {
				str = "Mod jar: `" + candidate.getModContainer().getName() + "`";
			}

			return str;
		}

		IntegrationContainer(ASMDataTable.ASMData asmData) {

			try {
				mod = asString(asmData.getCandidate());
				String clazz = Type.getObjectType(asmData.getClassName()).getClassName();
				integrator = (IMFRIntegrator) Class.forName(clazz).newInstance();
				String name = integrator.getIntegratorName();
				if (name == null || name.contains(".") ||
						name.contains("/") || name.contains("\\") ||
						name.contains(";") || name.contains(":")) {
					throw new IllegalArgumentException(String.format("Invalid integration name `%s`", name));
				}
				if ("Minecraft".equals(name) && !clazz.equals(Minecraft.class.getName())) {
					throw new IllegalArgumentException("Cannot replace vanilla integration");
				}
				if (!createdSets.add(name)) {
					throw new IllegalArgumentException(String.format("Duplicate integration name `%s`", name));
				}
				this.name = name;
			} catch (Throwable t) {
				log().error("Error loading MFR integration from {}", mod);
				log().catching(Level.ERROR, t);
				integrator = null;
			}
		}

		String mod;
		String name;
		IMFRIntegrator integrator;
		boolean enabled = false;

	}

}
