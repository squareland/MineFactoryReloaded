package powercrystals.minefactoryreloaded.modcompat.forestry;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.farmables.drinkhandlers.DrinkHandlerBiofuel;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizerStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableWood;
import powercrystals.minefactoryreloaded.tile.machine.processing.TileEntityUnifier;

import static net.minecraft.init.Blocks.AIR;
import static powercrystals.minefactoryreloaded.core.MFRUtil.findBlock;
import static powercrystals.minefactoryreloaded.core.MFRUtil.findItem;
import static powercrystals.minefactoryreloaded.modcompat.Compats.ModIds.FORESTRY;

@IMFRIntegrator.DependsOn(FORESTRY)
public class Forestry implements IMFRIntegrator {

	public void load() {

		Item item = findItem(FORESTRY, "fertilizerCompound");
		if (item != null)
			REGISTRY.registerFertilizer(new FertilizerStandard(item, 0));
		else
			MineFactoryReloadedCore.log().error("Forestry fertilizer null!");

		item = findItem(FORESTRY, "fertilizerBio");
		if (item != null)
			REGISTRY.registerFertilizer(new FertilizerStandard(item, 0));
		else
			MineFactoryReloadedCore.log().error("Forestry compost null!");

		item = findItem(FORESTRY, "peat");
		if (item != null)
			REGISTRY.registerSludgeDrop(10, new ItemStack(item));
		else
			MineFactoryReloadedCore.log().error("Forestry peat null!");

		item = findItem(FORESTRY, "ash");
		if (item != null)
			REGISTRY.registerSludgeDrop(1, new ItemStack(item));
		else
			MineFactoryReloadedCore.log().error("Forestry ash null!");

		item = findItem(FORESTRY, "decayingWheat");
		if (item != null)
			REGISTRY.registerSludgeDrop(20, new ItemStack(item));
		else
			MineFactoryReloadedCore.log().error("Forestry wheat null!");

		item = findItem(FORESTRY, "sapling");
		Block block = findBlock(FORESTRY, "saplingGE");
		if (item != null && block != AIR) {
			ForestrySapling sapling = new ForestrySapling(item, block);
			REGISTRY.registerPlantable(sapling);
			REGISTRY.registerFertilizable(sapling);
		} else
			MineFactoryReloadedCore.log().error("Forestry sapling/block null!");

		block = findBlock(FORESTRY, "soil");
		if (block != AIR) {
			ForestryBogEarth bog = new ForestryBogEarth(block);
			REGISTRY.registerPlantable(bog);
			REGISTRY.registerFertilizable(bog);
			REGISTRY.registerHarvestable(bog);
			REGISTRY.registerFruit(bog);
		} else
			MineFactoryReloadedCore.log().error("Forestry bog earth null!");

		for (int i = 1; true; ++i) {
			block = findBlock(FORESTRY, "log" + i);
			l: if (block == AIR) {
				if (i > 1)
					MineFactoryReloadedCore.log().debug("Forestry logs null at " + i + ".");
				else {
					block = findBlock(FORESTRY, "logs");
					if (block != AIR) {
						break l;
					}
					MineFactoryReloadedCore.log().error("Forestry logs null!");
				}
				break;
			}
			REGISTRY.registerHarvestable(new HarvestableWood(block));
			REGISTRY.registerFruitLogBlock(block);
		}

		for (int i = 1; true; ++i) {
			block = findBlock(FORESTRY, "fireproofLog" + i);
			l: if (block == AIR) {
				if (i > 1)
					MineFactoryReloadedCore.log().debug("Forestry logs null at " + i + ".");
				else {
					block = findBlock(FORESTRY, "logsFireproof");
					if (block != AIR) {
						break l;
					}
					MineFactoryReloadedCore.log().error("Forestry logs null!");
				}
				break;
			}
			REGISTRY.registerHarvestable(new HarvestableWood(block));
			REGISTRY.registerFruitLogBlock(block);
		}

		block = findBlock(FORESTRY, "leaves");
		if (block != AIR) {
			ForestryLeaf leaf = new ForestryLeaf(block);
			REGISTRY.registerFertilizable(leaf);
			REGISTRY.registerHarvestable(leaf);
			REGISTRY.registerFruit(leaf);
		} else
			MineFactoryReloadedCore.log().error("Forestry leaves null!");

		block = findBlock(FORESTRY, "pods");
		item = findItem(FORESTRY, "grafterProven");
		if (block != AIR) {
			ForestryPod pod = new ForestryPod(block, item);
			REGISTRY.registerFertilizable(pod);
			REGISTRY.registerHarvestable(pod);
			REGISTRY.registerFruit(pod);
		} else
			MineFactoryReloadedCore.log().error("Forestry pods null!");
	}

	public void postLoad() {

		REGISTRY.registerLiquidDrinkHandler("bioethanol", new DrinkHandlerBiofuel());

		TileEntityUnifier.updateUnifierLiquids();
	}

}
