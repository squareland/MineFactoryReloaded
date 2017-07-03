/*
package powercrystals.minefactoryreloaded.modhelpers.atum;

import cofh.mod.ChildMod;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.CustomProperty;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.world.World;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableWood;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;
import powercrystals.minefactoryreloaded.modhelpers.FertilizableCropReflection;
import powercrystals.minefactoryreloaded.modhelpers.FertilizableSaplingReflection;

@ChildMod(parent = MineFactoryReloadedCore.modId, mod = @Mod(modid = "minefactoryreloaded|compatatum",
		name = "MFR Compat: Atum",
		version = MineFactoryReloadedCore.version,
		dependencies = "after:MineFactoryReloaded;after:Atum",
		customProperties = @CustomProperty(k = "cofhversion", v = "true")))
public class Atum {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@EventHandler
	public void load(FMLInitializationEvent e) {

		try {
			Class AtumItems = Class.forName("rebelkeithy.mods.atum.AtumItems");
			Class AtumBlocks = Class.forName("rebelkeithy.mods.atum.AtumBlocks");

			String entityprefix = "rebelkeithy.mods.atum.entities.Entity";

			Class banditWarlord = Class.forName(entityprefix + "BanditWarlord");
			Class pharaoh = Class.forName(entityprefix + "Pharaoh");

			Block atumLogId = ((Block) AtumBlocks.getField("log").get(null));
			Block atumLeavesId = ((Block) AtumBlocks.getField("leaves").get(null));
			Block atumSaplingId = ((Block) AtumBlocks.getField("palmSapling").get(null));

			Item flaxSeedsId = ((Item) AtumItems.getField("flaxSeeds").get(null));

			Block flaxId = ((Block) AtumBlocks.getField("flax").get(null));
			Block papyrusId = ((Block) AtumBlocks.getField("papyrus").get(null));
			Block shrubId = ((Block) AtumBlocks.getField("shrub").get(null));
			Block weedId = ((Block) AtumBlocks.getField("weed").get(null));

			Method atumSaplingGrowTree = Class.forName("rebelkeithy.mods.atum.blocks.BlockPalmSapling").getMethod("growTree",
				World.class, int.class, int.class, int.class, Random.class);
			Method atumFlaxFertilize = Class.forName("rebelkeithy.mods.atum.blocks.BlockFlax").getMethod("fertilize",
				World.class, int.class, int.class, int.class);

			MFRRegistry.registerSafariNetBlacklist(banditWarlord);
			MFRRegistry.registerSafariNetBlacklist(pharaoh);

			MFRRegistry.registerGrinderBlacklist(banditWarlord);
			MFRRegistry.registerGrinderBlacklist(pharaoh);

			MFRRegistry.registerPlantable(new PlantableStandard(atumSaplingId, atumSaplingId));
			MFRRegistry.registerPlantable(new PlantableStandard(flaxSeedsId, flaxId));

			MFRRegistry.registerFertilizable(new FertilizableCropReflection(flaxId, atumFlaxFertilize, 5));
			MFRRegistry.registerFertilizable(new FertilizableSaplingReflection(atumSaplingId, atumSaplingGrowTree));

			MFRRegistry.registerHarvestable(new HarvestableWood(atumLogId));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(atumLeavesId));
			MFRRegistry.registerHarvestable(new HarvestableStandard(flaxId, HarvestType.Normal));
			MFRRegistry.registerHarvestable(new HarvestableStandard(papyrusId, HarvestType.LeaveBottom));
			MFRRegistry.registerHarvestable(new HarvestableStandard(shrubId, HarvestType.Normal));
			MFRRegistry.registerHarvestable(new HarvestableStandard(weedId, HarvestType.Normal));

		} catch (Throwable $) {
			ModContainer This = FMLCommonHandler.instance().findContainerFor(this);
			LogManager.getLogger(This.getModId()).log(Level.ERROR, "There was a problem loading " + This.getName(), $);
		}
	}

}
*/
