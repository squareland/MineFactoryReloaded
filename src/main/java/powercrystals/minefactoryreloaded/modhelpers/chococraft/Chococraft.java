/*
package powercrystals.minefactoryreloaded.modhelpers.chococraft;

import cofh.mod.ChildMod;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.FMLLog;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.CustomProperty;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;

@ChildMod(parent = MineFactoryReloadedCore.modId, mod = @Mod(modid = "minefactoryreloaded_compatchococraft",
		name = "MFR Compat: Chococraft",
		version = MineFactoryReloadedCore.version,
		dependencies = "required-after:MineFactoryReloaded;after:chococraft",
		customProperties = @CustomProperty(k = "cofhversion", v = "true")))
public class Chococraft {

	@EventHandler
	public void load(FMLInitializationEvent event) {

		try {
			Class<?> blocks = Class.forName("chococraft.common.config.ChocoCraftBlocks");

			FMLLog.info("Registering Gysahls for Planter/Harvester/Fertilizer");
			Block blockId = ((Block) (blocks.getField("gysahlStemBlock").get(null)));

			Class<?> items = Class.forName("chococraft.common.config.ChocoCraftItems");
			Item seedId = ((Item) (items.getField("gysahlSeedsItem").get(null)));

			MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
			MFRRegistry.registerHarvestable(new HarvestableChococraft(blockId));
			MFRRegistry.registerFertilizable(new FertilizableChococraft(blockId));
		} catch (Throwable $) {
			ModContainer This = FMLCommonHandler.instance().findContainerFor(this);
			LogManager.getLogger(This.getModId()).log(Level.ERROR, "There was a problem loading " + This.getName(), $);
		}
	}

}
*/
