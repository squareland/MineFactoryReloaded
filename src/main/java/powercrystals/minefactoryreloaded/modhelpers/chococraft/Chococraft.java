package powercrystals.minefactoryreloaded.modhelpers.chococraft;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableCropPlant;

import static powercrystals.minefactoryreloaded.modhelpers.Compats.ModIds.CHOCOCRAFT;

@IMFRIntegrator.DependsOn(CHOCOCRAFT)
public class Chococraft implements IMFRIntegrator {

	public void load() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

			Class<?> blocks = Class.forName("chococraft.common.config.ChocoCraftBlocks");

			Block blockId = ((Block) (blocks.getField("gysahlStemBlock").get(null)));

			Class<?> items = Class.forName("chococraft.common.config.ChocoCraftItems");
			Item seedId = ((Item) (items.getField("gysahlSeedsItem").get(null)));

			MFRRegistry.registerPlantable(new PlantableCropPlant(seedId, blockId));
			MFRRegistry.registerHarvestable(new HarvestableChococraft(blockId));
			MFRRegistry.registerFertilizable(new FertilizableChococraft(blockId));
	}

}

