package powercrystals.minefactoryreloaded.setup;

import cofh.core.util.core.IInitializer;
import gnu.trove.map.TIntObjectMap;
import gnu.trove.map.hash.TIntObjectHashMap;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.ExistingSubstitutionException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.block.*;
import powercrystals.minefactoryreloaded.block.decor.*;
import powercrystals.minefactoryreloaded.block.fluid.BlockTank;
import powercrystals.minefactoryreloaded.block.transport.*;
import powercrystals.minefactoryreloaded.entity.DebugTracker;
import powercrystals.minefactoryreloaded.entity.EntityPinkSlime;
import powercrystals.minefactoryreloaded.item.*;
import powercrystals.minefactoryreloaded.item.base.*;
import powercrystals.minefactoryreloaded.item.gun.ItemNeedleGun;
import powercrystals.minefactoryreloaded.item.gun.ItemPotatoCannon;
import powercrystals.minefactoryreloaded.item.gun.ItemRocketLauncher;
import powercrystals.minefactoryreloaded.item.gun.ItemSafariNetLauncher;
import powercrystals.minefactoryreloaded.item.gun.ammo.*;
import powercrystals.minefactoryreloaded.item.syringe.*;
import powercrystals.minefactoryreloaded.item.tool.*;

import java.util.ArrayList;

public class MFRThings {

	public static TIntObjectMap<Block> machineBlocks = new TIntObjectHashMap<Block>();

	private static ArrayList<IInitializer> initList = new ArrayList<>();

	public static void registerInitializer(IInitializer init) {

		initList.add(init);
	}

	public static Block conveyorBlock;

	public static Block factoryGlassBlock;
	public static Block factoryGlassPaneBlock;
	public static Block factoryRoadBlock;
	public static Block factoryPlasticBlock;
	public static Block factoryDecorativeBrickBlock;
	public static Item factoryDecorativeBrickItem;
	public static Block factoryDecorativeStoneBlock;
	public static Block pinkSlimeBlock;
	public static Item pinkSlimeBlockItem;

	public static Block rubberWoodBlock;
	public static Block rubberLeavesBlock;
	public static BlockRubberSapling rubberSaplingBlock;

	public static Item rubberWoodItem;
	public static Item rubberLeavesItem;
	public static Item rubberSaplingItem;

	public static Block railPickupCargoBlock;
	public static Block railDropoffCargoBlock;
	public static Block railPickupPassengerBlock;
	public static Block railDropoffPassengerBlock;

	public static BlockRedNetCable rednetCableBlock;
	public static BlockPlasticPipe plasticPipeBlock;

	public static BlockRedNetLogic rednetLogicBlock;
	public static BlockRedNetPanel rednetPanelBlock;

	public static Block plasticTank;
	public static Item plasticTankItem;

	public static Block fakeLaserBlock;

	public static Block vineScaffoldBlock;

	public static Block detCordBlock;

	public static Block fertileSoil;

	public static Block machineBlock;
	public static Item machineBaseItem;

	public static Item factoryHammerItem;
	public static Item fertilizerItem;
	public static Item plasticSheetItem;
	public static Item rubberBarItem;
	public static Item rawPlasticItem;
	public static Item syringeEmptyItem;
	public static Item syringeHealthItem;
	public static Item syringeGrowthItem;
	public static Item rawRubberItem;
	public static Item safariNetItem;
	public static ItemMulti ceramicDyeItem;
	public static Item blankRecordItem;
	public static Item syringeZombieItem;
	public static Item safariNetSingleItem;
	public static ItemMulti upgradeItem;
	public static Item safariNetLauncherItem;
	public static Item sugarCharcoalItem;
	public static Item milkBottleItem;
	public static Item spyglassItem;
	public static Item portaSpawnerItem;
	public static Item strawItem;
	public static Item xpExtractorItem;
	public static Item syringeSlimeItem;
	public static Item syringeCureItem;
	public static Item logicCardItem;
	public static Item rednetMeterItem;
	public static Item rednetMemoryCardItem;
	public static Item rulerItem;
	public static Item meatIngotRawItem;
	public static Item meatIngotCookedItem;
	public static Item meatNuggetRawItem;
	public static Item meatNuggetCookedItem;
	public static Item pinkSlimeItem;
	public static Item safariNetJailerItem;
	public static ItemMulti laserFocusItem;
	public static Item needlegunItem;
	public static Item needlegunAmmoEmptyItem;
	public static Item needlegunAmmoStandardItem;
	public static Item needlegunAmmoPierceItem;
	public static Item needlegunAmmoLavaItem;
	public static Item needlegunAmmoSludgeItem;
	public static Item needlegunAmmoSewageItem;
	public static Item needlegunAmmoFireItem;
	public static Item needlegunAmmoAnvilItem;
	public static Item rocketLauncherItem;
	public static Item rocketItem;
	public static ItemFactoryCup plasticCupItem;
	public static Item plasticCellItem;
	public static Item fishingRodItem;
	public static Item plasticBagItem;
	public static ItemFactoryArmor plasticGlasses;
	public static ItemFactoryArmor plasticHelmetItem;
	public static ItemFactoryArmor plasticChestplateItem;
	public static ItemFactoryArmor plasticLeggingsItem;
	public static ItemFactoryArmor plasticBootsItem;
	public static Item safariNetFancyJailerItem;
	public static Item potatoLauncherItem;

	public static void preInit() {

		MFRThings.machineBlocks.put(0, new BlockFactoryMachine(0));
		MFRThings.machineBlocks.put(1, new BlockFactoryMachine(1));
		MFRThings.machineBlocks.put(2, new BlockFactoryMachine(2));

		conveyorBlock = new BlockConveyor();
		factoryGlassBlock = new BlockFactoryGlass();
		factoryGlassPaneBlock = new BlockFactoryGlassPane();
		factoryRoadBlock = new BlockFactoryRoad();
		factoryPlasticBlock = new BlockFactoryPlastic();
		factoryDecorativeBrickBlock = new BlockDecorativeBricks();
		factoryDecorativeStoneBlock = new BlockDecorativeStone();
		pinkSlimeBlock = new BlockPinkSlime();
		rubberWoodBlock = new BlockRubberWood();
		rubberLeavesBlock = new BlockRubberLeaves();
		rubberSaplingBlock = new BlockRubberSapling();
		railDropoffCargoBlock = new BlockRailCargoDropoff();
		railPickupCargoBlock = new BlockRailCargoPickup();
		railDropoffPassengerBlock = new BlockRailPassengerDropoff();
		railPickupPassengerBlock = new BlockRailPassengerPickup();
		rednetCableBlock = new BlockRedNetCable();
		rednetLogicBlock = new BlockRedNetLogic();
		rednetPanelBlock = new BlockRedNetPanel();
		fakeLaserBlock = new BlockFakeLaser();
		vineScaffoldBlock = new BlockVineScaffold();
		detCordBlock = new BlockDetCord();
		plasticPipeBlock = new BlockPlasticPipe();
		fertileSoil = new BlockFertileSoil();
		machineBlock = new BlockFactoryDecoration();
		plasticTank = new BlockTank();

		factoryHammerItem = new ItemFactoryHammer();
		plasticHelmetItem = new ItemFactoryArmor(ItemFactoryArmor.PLASTIC_ARMOR, EntityEquipmentSlot.HEAD)
				.setModelLocation("armor", "type=helm");
		plasticHelmetItem.setRegistryName(MineFactoryReloadedCore.modId, "plastic_helm");
		plasticChestplateItem = new ItemFactoryArmor(ItemFactoryArmor.PLASTIC_ARMOR, EntityEquipmentSlot.CHEST)
				.setModelLocation("armor", "type=chest");
		plasticChestplateItem.setRegistryName(MineFactoryReloadedCore.modId, "plastic_chest");
		plasticLeggingsItem = new ItemFactoryArmor(ItemFactoryArmor.PLASTIC_ARMOR, EntityEquipmentSlot.LEGS)
				.setModelLocation("armor", "type=legs");
		plasticLeggingsItem.setRegistryName(MineFactoryReloadedCore.modId, "plastic_legs");
		plasticBootsItem = new ItemPlasticBoots();
		plasticGlasses = new ItemFactoryArmor(ItemFactoryArmor.GLASS_ARMOR, EntityEquipmentSlot.HEAD)
				.setModelLocation("armor", "type=glass_helm");
		plasticGlasses.setRegistryName(MineFactoryReloadedCore.modId, "glass_helm");
		{
			int i = MFRConfig.armorStacks.getBoolean(false) ? 4 : 1;
			plasticHelmetItem.setRepairIngot("itemPlastic").setUnlocalizedName("mfr.plastic.armor.helm").setMaxStackSize(i);
			plasticChestplateItem.setRepairIngot("itemPlastic").setUnlocalizedName("mfr.plastic.armor.chest").setMaxStackSize(i);
			plasticLeggingsItem.setRepairIngot("itemPlastic").setUnlocalizedName("mfr.plastic.armor.legs").setMaxStackSize(i);
			plasticBootsItem.setRepairIngot("itemPlastic").setMaxStackSize(i);
			plasticGlasses.setRepairIngot("itemPlastic").setUnlocalizedName("mfr.glass.armor.helm");
		}

		rawRubberItem = (new ItemFactory()).setModelLocation("material", "type=rubber_raw").setUnlocalizedName("mfr.rubber.raw")
				.setRegistryName(MineFactoryReloadedCore.modId, "rubber_raw");
		rubberBarItem = (new ItemFactory()).setModelLocation("material", "type=rubber_bar").setUnlocalizedName("mfr.rubber.bar")
				.setRegistryName(MineFactoryReloadedCore.modId, "rubber_bar");

		rawPlasticItem = (new ItemFactory()).setModelLocation("material", "type=plastic_raw")
				.setUnlocalizedName("mfr.plastic.raw")
				.setRegistryName(MineFactoryReloadedCore.modId, "plastic_raw");
		plasticSheetItem = (new ItemFactory()).setModelLocation("material", "type=plastic_sheet")
				.setUnlocalizedName("mfr.plastic.sheet").setMaxStackSize(96)
				.setRegistryName(MineFactoryReloadedCore.modId, "plastic_sheet");

		upgradeItem = new ItemUpgrade();

		rednetMeterItem = new ItemRedNetMeter();
		rednetMemoryCardItem = new ItemRedNetMemoryCard();
		logicCardItem = new ItemLogicUpgradeCard();

		float meatNuggetSaturation = MFRConfig.meatSaturation.getBoolean(false) ? 0.1F : 0.2F;
		float meatIngotSaturation = MFRConfig.meatSaturation.getBoolean(false) ? 0.2F : 0.8F;
		meatIngotRawItem = (new ItemFactoryFood(4, meatIngotSaturation))
				.setModelLocation("food", "variant=meat_ingot_raw").setUnlocalizedName("mfr.meat.ingot.raw")
				.setRegistryName(MineFactoryReloadedCore.modId, "meat_ingot_raw");
		meatIngotCookedItem = (new ItemFactoryFood(10, meatIngotSaturation))
				.setModelLocation("food", "variant=meat_ingot_cooked").setUnlocalizedName("mfr.meat.ingot.cooked")
				.setRegistryName(MineFactoryReloadedCore.modId, "meat_ingot_cooked");
		meatNuggetRawItem = (new ItemFactoryFood(1, meatNuggetSaturation))
				.setModelLocation("food", "variant=meat_nugget_raw").setUnlocalizedName("mfr.meat.nugget.raw")
				.setRegistryName(MineFactoryReloadedCore.modId, "meat_nugget_raw");
		meatNuggetCookedItem = (new ItemFactoryFood(4, meatNuggetSaturation))
				.setModelLocation("food", "variant=meat_nugget_cooked").setUnlocalizedName("mfr.meat.nugget.cooked")
				.setRegistryName(MineFactoryReloadedCore.modId, "meat_nugget_cooked");
		pinkSlimeItem = new ItemPinkSlime();

		if (MFRConfig.enableLiquidSyringe.getBoolean(true))
			syringeEmptyItem = new ItemSyringeLiquid();
		else
			syringeEmptyItem = (new ItemFactory()).setModelLocation("syringe", "variant=empty")
					.setUnlocalizedName("mfr.syringe.empty").setRegistryName(MineFactoryReloadedCore.modId, "syringe_empty");
		syringeHealthItem = new ItemSyringeHealth();
		syringeGrowthItem = new ItemSyringeGrowth();
		syringeZombieItem = new ItemSyringeZombie();
		syringeSlimeItem = new ItemSyringeSlime();
		syringeCureItem = new ItemSyringeCure();

		safariNetLauncherItem = new ItemSafariNetLauncher();
		safariNetItem = (new ItemSafariNet(0, true)).setModelLocation("safari_net", "reusable")
				.setUnlocalizedName("mfr.safarinet.reusable")
				.setRegistryName(MineFactoryReloadedCore.modId, "safari_net_reusable");
		safariNetSingleItem = (new ItemSafariNet(0)).setModelLocation("safari_net", "single_use")
				.setUnlocalizedName("mfr.safarinet.singleuse")
				.setRegistryName(MineFactoryReloadedCore.modId, "safari_net_single_use");
		safariNetJailerItem = (new ItemSafariNet(1)).setModelLocation("safari_net", "jailer")
				.setUnlocalizedName("mfr.safarinet.jailer")
				.setRegistryName(MineFactoryReloadedCore.modId, "safari_net_jailer");
		safariNetFancyJailerItem = (new ItemSafariNet(3)).setModelLocation("safari_net", "jailer_fancy")
				.setUnlocalizedName("mfr.safarinet.jailer.fancy")
				.setRegistryName(MineFactoryReloadedCore.modId, "safari_net_jailer_fancy");

		portaSpawnerItem = new ItemPortaSpawner();

		xpExtractorItem = new ItemXpExtractor();
		strawItem = new ItemStraw();
		milkBottleItem = new ItemMilkBottle();
		plasticCupItem = new ItemFactoryCup(24, 16);

		/*
		CarbonContainer.cell = new CarbonContainer(MFRConfig.plasticCellItemId.getInt(), 64, "mfr.bucket.plasticcell", false);
		CarbonContainer.cell.setFilledItem(CarbonContainer.cell).setEmptyItem(CarbonContainer.cell);

		MinecraftForge.EVENT_BUS.register(new LiquidRegistry(_configFolder,
				Loader.instance().activeModContainer()));//*/
		//plasticCellItem = CarbonContainer.cell;
		plasticBagItem = new ItemFactoryBag();

		sugarCharcoalItem = (new ItemFactory()).setModelLocation("material", "type=sugar_charcoal")
				.setUnlocalizedName("mfr.sugarcharcoal")
				.setRegistryName(MineFactoryReloadedCore.modId, "sugar_charcoal");
		fertilizerItem = (new ItemFactory()).setModelLocation("material", "type=fertilizer").setUnlocalizedName("mfr.fertilizer")
				.setRegistryName(MineFactoryReloadedCore.modId, "fertilizer");

		ceramicDyeItem = new ItemCeramicDye();
		(laserFocusItem = new ItemFactoryColored()).setModelLocation("laser_focus", "").setUnlocalizedName("mfr.laserfocus")
				.setMaxStackSize(1)
				.setRegistryName(MineFactoryReloadedCore.modId, "laser_focus");

		blankRecordItem = (new ItemFactory()).setModelLocation("material", "type=blank_record")
				.setUnlocalizedName("mfr.record.blank").setMaxStackSize(1)
				.setRegistryName(MineFactoryReloadedCore.modId, "blank_record");
		spyglassItem = new ItemSpyglass();
		rulerItem = new ItemRuler();
		fishingRodItem = new ItemFishingRod();

		potatoLauncherItem = new ItemPotatoCannon();

		needlegunItem = new ItemNeedleGun();
		needlegunAmmoEmptyItem = (new ItemFactory()).setModelLocation("needle_gun_ammo", "variant=empty")
				.setUnlocalizedName("mfr.needlegun.ammo.empty")
				.setRegistryName(MineFactoryReloadedCore.modId, "needle_gun_ammo_empty");
		needlegunAmmoStandardItem = (new ItemNeedlegunAmmoStandard())
				.setModelLocation("needle_gun_ammo", "variant=standard").setUnlocalizedName("mfr.needlegun.ammo.standard")
				.setRegistryName(MineFactoryReloadedCore.modId, "needle_gun_ammo_standard");
		needlegunAmmoPierceItem = (new ItemNeedlegunAmmoStandard(16, 2f, 8))
				.setModelLocation("needle_gun_ammo", "variant=pierce").setUnlocalizedName("mfr.needlegun.ammo.pierce")
				.setRegistryName(MineFactoryReloadedCore.modId, "needle_gun_ammo_pierce");
		needlegunAmmoLavaItem = (new ItemNeedlegunAmmoBlock(Blocks.FLOWING_LAVA.getDefaultState(), 3))
				.setModelLocation("needle_gun_ammo", "variant=lava").setUnlocalizedName("mfr.needlegun.ammo.lava")
				.setRegistryName(MineFactoryReloadedCore.modId, "needle_gun_ammo_lava");
		needlegunAmmoSludgeItem = (new ItemNeedlegunAmmoBlock(MFRFluids.sludgeLiquid.getDefaultState(), 6))
				.setModelLocation("needle_gun_ammo", "variant=sludge").setUnlocalizedName("mfr.needlegun.ammo.sludge")
				.setRegistryName(MineFactoryReloadedCore.modId, "needle_gun_ammo_sludge");
		needlegunAmmoSewageItem = (new ItemNeedlegunAmmoBlock(MFRFluids.sewageLiquid.getDefaultState(), 6))
				.setModelLocation("needle_gun_ammo", "variant=sewage").setUnlocalizedName("mfr.needlegun.ammo.sewage")
				.setRegistryName(MineFactoryReloadedCore.modId, "needle_gun_ammo_sewage");
		needlegunAmmoFireItem = (new ItemNeedlegunAmmoFire()).setUnlocalizedName("mfr.needlegun.ammo.fire")
				.setRegistryName(MineFactoryReloadedCore.modId, "needle_gun_ammo_fire");
		needlegunAmmoAnvilItem = (new ItemNeedlegunAmmoAnvil()).setUnlocalizedName("mfr.needlegun.ammo.anvil")
				.setRegistryName(MineFactoryReloadedCore.modId, "needle_gun_ammo_anvil");

		rocketLauncherItem = new ItemRocketLauncher();
		rocketItem = new ItemRocket();

		EntityRegistry
				.registerModEntity(new ResourceLocation(MineFactoryReloadedCore.modId, "pink_slime"), EntityPinkSlime.class, "mfrEntityPinkSlime", 1, MineFactoryReloadedCore.instance(), 160, 5,
						true);
		LootTableList.register(EntityPinkSlime.PINK_SLIME);

		EntityRegistry
				.registerModEntity(new ResourceLocation(MineFactoryReloadedCore.modId, "debug_tracker"), DebugTracker.class, "DebugTracker", 99, MineFactoryReloadedCore.instance(), 250, 10, true);

		for (IInitializer init : initList) {
			init.preInit();
		}

		machineBaseItem = Item.getItemFromBlock(machineBlock);
		plasticTankItem = Item.getItemFromBlock(plasticTank);

		rubberSaplingItem = Item.getItemFromBlock(rubberSaplingBlock);
		rubberWoodItem = Item.getItemFromBlock(rubberWoodBlock);
		rubberLeavesItem = Item.getItemFromBlock(rubberLeavesBlock);

		factoryDecorativeBrickItem = Item.getItemFromBlock(factoryDecorativeBrickBlock);
		pinkSlimeBlockItem = Item.getItemFromBlock(pinkSlimeBlock);
	}

	public static void initialize() {

		for (IInitializer init : initList) {
			init.initialize();
		}
	}

	public static void postInit() {

		for (IInitializer init : initList) {
			init.postInit();
		}
	}
}
