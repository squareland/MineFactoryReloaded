package powercrystals.minefactoryreloaded.modhelpers.buildcraft;

//import buildcraft.api.fuels.BuildcraftFuelRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import powercrystals.minefactoryreloaded.setup.MFRFluids;

/*@ChildMod(parent = MineFactoryReloadedCore.modId, mod = @Mod(modid = "minefactoryreloaded_compatbuildcraft",
		name = "MFR Compat: BuildCraft",
		version = MineFactoryReloadedCore.version,
		dependencies = "after:MineFactoryReloaded;after:BuildCraftAPI|fuels",
		customProperties = @CustomProperty(k = "cofhversion", v = "true")))*/
public class Buildcraft {

	@Mod.EventHandler
	private void postInit(FMLPostInitializationEvent evt) {

		try {
			//if (BuildcraftFuelRegistry.fuel != null)
				//BuildcraftFuelRegistry.fuel.addFuel(MFRFluids.getFluid("biofuel"), 40, 15000);
		} catch (Throwable $) {
			ModContainer This = FMLCommonHandler.instance().findContainerFor(this);
			LogManager.getLogger(This.getModId()).log(Level.ERROR, "There was a problem loading " + This.getName(), $);
		}
	}

}
