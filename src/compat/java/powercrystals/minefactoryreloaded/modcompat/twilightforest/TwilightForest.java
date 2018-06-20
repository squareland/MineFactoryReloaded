
package powercrystals.minefactoryreloaded.modcompat.twilightforest;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.item.Item;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableShearable;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableWood;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableSapling;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableStandard;

import static powercrystals.minefactoryreloaded.modcompat.Compats.ModIds.TWILIGHT_FOREST;

@IMFRIntegrator.DependsOn(TWILIGHT_FOREST)
public class TwilightForest {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	public void load() throws ClassNotFoundException, NoSuchFieldException, IllegalAccessException {

			Class tfBighorn = Class.forName("twilightforest.entity.passive.EntityTFBighorn");
			Class tfHydra = Class.forName("twilightforest.entity.boss.EntityTFHydra");
			Class tfHydraHead = Class.forName("twilightforest.entity.boss.EntityTFHydraHead");
			Class tfHydraNeck = Class.forName("twilightforest.entity.boss.EntityTFHydraNeck");
			Class tfHydraPart = Class.forName("twilightforest.entity.boss.EntityTFHydraPart");
			Class tfKingSpider = Class.forName("twilightforest.entity.EntityTFKingSpider");
			Class tfLich = Class.forName("twilightforest.entity.boss.EntityTFLich");
			Class tfNaga = Class.forName("twilightforest.entity.boss.EntityTFNaga");
			Class tfNagaSegment = Class.forName("twilightforest.entity.boss.EntityTFNagaSegment");
			Class tfQuestRam = Class.forName("twilightforest.entity.passive.EntityTFQuestRam");
			Class tfUrGhast = Class.forName("twilightforest.entity.boss.EntityTFUrGhast");
			Class tfYeti = Class.forName("twilightforest.entity.boss.EntityTFYetiAlpha");
			Class tfSnowQueen = Class.forName("twilightforest.entity.boss.EntityTFSnowQueen");
			Class tfPhantomKnight = Class.forName("twilightforest.entity.boss.EntityTFKnightPhantom");

			MFRRegistry.registerSafariNetBlacklist(tfHydra);
			MFRRegistry.registerSafariNetBlacklist(tfHydraHead);
			MFRRegistry.registerSafariNetBlacklist(tfHydraNeck);
			MFRRegistry.registerSafariNetBlacklist(tfHydraPart);
			MFRRegistry.registerSafariNetBlacklist(tfKingSpider);
			MFRRegistry.registerSafariNetBlacklist(tfLich);
			MFRRegistry.registerSafariNetBlacklist(tfNaga);
			MFRRegistry.registerSafariNetBlacklist(tfNagaSegment);
			MFRRegistry.registerSafariNetBlacklist(tfQuestRam);
			MFRRegistry.registerSafariNetBlacklist(tfUrGhast);
			MFRRegistry.registerSafariNetBlacklist(tfYeti);
			MFRRegistry.registerSafariNetBlacklist(tfSnowQueen);
			MFRRegistry.registerSafariNetBlacklist(tfPhantomKnight);

			MFRRegistry.registerGrinderBlacklist(tfUrGhast);
			MFRRegistry.registerGrinderBlacklist(tfNagaSegment);
			MFRRegistry.registerGrinderBlacklist(tfNaga);
			MFRRegistry.registerGrinderBlacklist(tfLich);
			MFRRegistry.registerGrinderBlacklist(tfHydraPart);
			MFRRegistry.registerGrinderBlacklist(tfHydraNeck);
			MFRRegistry.registerGrinderBlacklist(tfHydraHead);
			MFRRegistry.registerGrinderBlacklist(tfHydra);
			MFRRegistry.registerGrinderBlacklist(tfYeti);
			MFRRegistry.registerGrinderBlacklist(tfSnowQueen);
			MFRRegistry.registerGrinderBlacklist(tfPhantomKnight);

			//MFRRegistry.registerMobEggHandler(new TwilightForestEggHandler());

			MFRRegistry.registerRanchable(new RanchableTFBighorn(tfBighorn));

			Class tfBlocks = Class.forName("twilightforest.block.TFBlocks");
			Class tfItems = Class.forName("twilightforest.item.TFItems");

			MFRRegistry.registerHarvestable(new HarvestableWood(((Block) tfBlocks.getField("log").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableWood(((Block) tfBlocks.getField("giantLog").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableWood(((Block) tfBlocks.getField("magicLog").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableWood(((Block) tfBlocks.getField("magicLogSpecial").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableWood(((Block) tfBlocks.getField("hugeStalk").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableStandard(((Block) tfBlocks.getField("root").get(null)),
					HarvestType.Tree));
			MFRRegistry.registerHarvestable(new HarvestableStandard(((Block) tfBlocks.getField("hugeGloomBlock").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("leaves").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("leaves3").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("darkleaves").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("giantLeaves").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("magicLeaves").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("thornRose").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("hedge").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("firefly").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(((Block) tfBlocks.getField("cicada").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableShearable(((Block) tfBlocks.getField("plant").get(null))));
			MFRRegistry.registerHarvestable(new HarvestableShearable(((Block) tfBlocks.getField("trollBer").get(null)),
					HarvestType.Column));

			MFRRegistry.registerPlantable(new PlantableSapling(((Block) tfBlocks.getField("sapling").get(null))));
			MFRRegistry.registerPlantable(new PlantableStandard(((Item) tfItems.getField("torchberries").get(null)),
					((Block) tfBlocks.getField("unripeTrollBer").get(null))));

			MFRRegistry.registerFertilizable(new FertilizableStandard(((IGrowable) tfBlocks.getField("sapling").get(null))));

			MFRRegistry.registerRandomMobProvider(new TwilightForestMobProvider());
	}

}
