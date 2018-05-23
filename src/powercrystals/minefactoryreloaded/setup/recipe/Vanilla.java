package powercrystals.minefactoryreloaded.setup.recipe;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.block.ItemBlockRedNetLogic;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.setup.MFRFluids;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.setup.recipe.handler.ShapelessMachineTinker;

import javax.annotation.Nonnull;
import java.util.Arrays;

import static cofh.core.util.helpers.ItemHelper.*;
import static cofh.core.util.helpers.RecipeHelper.*;
import static net.minecraft.init.Blocks.*;
import static net.minecraft.init.Items.*;
import static net.minecraftforge.oredict.OreDictionary.*;
import static powercrystals.minefactoryreloaded.core.UtilRecipe.*;
import static powercrystals.minefactoryreloaded.setup.MFRConfig.*;
import static powercrystals.minefactoryreloaded.setup.MFRThings.*;
import static powercrystals.minefactoryreloaded.setup.Machine.*;

public class Vanilla {

	// prevent derived recipe sets from double-registering this one if multiple sets are enabled
	private static boolean _registeredMachines;
	private static boolean _registeredMachineUpgrades;
	private static boolean _registeredMachineTinkers;
	private static boolean _registeredConveyors;
	private static boolean _registeredDecorative;
	private static boolean _registeredSyringes;
	private static boolean _registeredArmor;
	private static boolean _registeredPlastics;
	private static boolean _registeredMiscItems;
	private static boolean _registeredSafariNets;
	private static boolean _registeredSmelting;
	private static boolean _registeredVanillaImprovements;
	private static boolean _registeredRails;
	private static boolean _registeredGuns;
	private static boolean _registeredRedNet;
	private static boolean _registeredRedNetManual;
	private static boolean _registeredOreDict;

	protected static String[] DYES = { "Black", "Red", "Green", "Brown",
			"Blue", "Purple", "Cyan", "LightGray", "Gray", "Pink", "Lime",
			"Yellow", "LightBlue", "Magenta", "Orange", "White" }; // order copied from forge

	public final void registerRecipes() {

		gatherItems();
		registerConveyors();
		registerMachines();
		registerMachineUpgrades();
		registerMachineTinkers();
		registerPlastics();
		registerArmor();
		registerDecorative();
		registerMiscItems();
		registerSmelting();
		registerVanillaImprovements();
		registerSafariNets();
		registerRails();
		if (enableSyringes.getBoolean(true))
			registerSyringes();
		if (enableGuns.getBoolean(true))
			registerGuns();
		registerRedNet();
		registerRedNetManual();
	}

	public static void registerOredict() {

		if (_registeredOreDict) return;
		_registeredOreDict = true;

		registerOre("itemRubber", rubberBarItem);
		registerOre("itemRawRubber", rawRubberItem);
		registerOre("woodRubber", rubberWoodBlock);
		registerOre("leavesRubber", rubberLeavesBlock);
		registerOre("treeSapling", rubberSaplingBlock);
		registerOre("blockPlastic", factoryPlasticBlock);
		registerOre("sheetPlastic", plasticSheetItem);
		registerOre("itemPlastic", plasticSheetItem);
		registerOre("dustPlastic", rawPlasticItem);
		registerOre("itemPlastic", rawPlasticItem);
		registerOre("ingotMeat", meatIngotCookedItem);
		registerOre("ingotMeatRaw", meatIngotRawItem);
		registerOre("nuggetMeat", meatNuggetCookedItem);
		registerOre("nuggetMeatRaw", meatNuggetRawItem);
		registerOre("blockMeat", stack(factoryDecorativeBrickBlock, 1, 13));
		registerOre("blockMeatRaw", stack(factoryDecorativeBrickBlock, 1, 12));
		registerOre("itemCharcoalSugar", sugarCharcoalItem);
		registerOre("blockCharcoalSugar", stack(factoryDecorativeBrickBlock, 1, 15));
		registerOre("cableRedNet", stack(rednetCableBlock, 1, 0));
		registerOre("cableRedNet", stack(rednetCableBlock, 1, 1));
		registerOre("cableRedNetEnergy", stack(rednetCableBlock, 1, 2));
		registerOre("cableRedNetEnergy", stack(rednetCableBlock, 1, 3));
		registerOre("slimeballPink", stack(pinkSlimeItem, 1, 0));
		registerOre("slimeball", stack(pinkSlimeItem, 1, 0));
		registerOre("blockSlimePink", stack(pinkSlimeBlock));
		registerOre("blockSlime", stack(pinkSlimeBlock));
		registerOre("fertilizerOrganic", fertilizerItem);
		registerOre("fertilizer", fertilizerItem);
		registerOre("dyeBrown", fertilizerItem);
		registerOre("wireExplosive", detCordBlock);
		registerOre("listAllmilk", milkBottleItem);
		registerOre("listAllmeatraw", meatIngotRawItem);
		registerOre("listAllmeatcooked", meatIngotCookedItem);

		{ // GLASS:
			String pane = "paneGlass", glass = "blockGlass";
			@Nonnull ItemStack glassStack = stack2(factoryGlassBlock, 1);
			@Nonnull ItemStack paneStack = stack2(factoryGlassPaneBlock, 1);
			registerOre(glass, glassStack.copy());
			registerOre(pane, paneStack.copy());

			for (int i = 0; i < 16; i++) {
				@Nonnull ItemStack ceramicDye = stack(ceramicDyeItem, 1, i);
				glassStack = stack(factoryGlassBlock, 1, i);
				paneStack = stack(factoryGlassPaneBlock, 1, i);
				String dye = DYES[15 - i];
				String dye2 = "dyeCeramic" + dye;
				registerOre(glass + dye, glassStack.copy());
				registerOre(pane + dye, paneStack.copy());
				registerOre(dye2, ceramicDye.copy());
			}
		}

		registerOre("stone", stack(factoryDecorativeStoneBlock, 1, 0));
		registerOre("stone", stack(factoryDecorativeStoneBlock, 1, 1));
		registerOre("cobblestone", stack(factoryDecorativeStoneBlock, 1, 2));
		registerOre("cobblestone", stack(factoryDecorativeStoneBlock, 1, 3));

		// vanilla items
		registerOre("listAllmilk", MILK_BUCKET);
		registerOre("listAllwater", WATER_BUCKET);
		registerOre("listAllwater", stack(POTIONITEM, 1, 0));
	}

	protected void gatherItems() {

	}

	protected void registerMachines() {

		if (_registeredMachines) {
			return;
		}
		_registeredMachines = true;

		// regex: if\s*\((Machine\.\w+)[^\n]+\n[^\n]+\n[^\n]+\n\s+(\{[^}]+\} \))[^\n]+\n[^\n]+

		registerMachine(Planter, new Object[] {
				"GGG",
				"CPC",
				" M ",
				'G', "sheetPlastic",
				'P', PISTON,
				'C', Items.FLOWER_POT,
				'M', machineBaseItem,
		});

		registerMachine(Fisher, new Object[] {
				"GGG",
				"RRR",
				"BMB",
				'G', "sheetPlastic",
				'R', FISHING_ROD,
				'B', BUCKET,
				'M', machineBaseItem,
		});

		registerMachine(Harvester, new Object[] {
				"GGG",
				"XSX",
				" M ",
				'G', "sheetPlastic",
				'X', GOLDEN_AXE,
				'S', SHEARS,
				'M', machineBaseItem,
		});

		registerMachine(Rancher, new Object[] {
				"GGG",
				"SBS",
				"PMP",
				'G', "sheetPlastic",
				'B', BUCKET,
				'S', SHEARS,
				'P', plasticPipeBlock,
				'M', machineBaseItem,
		});

		registerMachine(Fertilizer, new Object[] {
				"GGG",
				"LBL",
				" M ",
				'G', "sheetPlastic",
				'L',LEATHER,
				'B',GLASS_BOTTLE,
				'M', machineBaseItem,
		});

		registerMachine(Vet, new Object[] {
				"GGG",
				"SSS",
				"EME",
				'G', "sheetPlastic",
				'E',SPIDER_EYE,
				'S', syringeEmptyItem,
				'M', machineBaseItem,
		});

		registerMachine(ItemCollector, 8, new Object[] {
				"GGG",
				" C ",
				" M ",
				'G', "sheetPlastic",
				'C',CHEST,
				'M', machineBaseItem,
		});

		registerMachine(BlockBreaker, new Object[] {
				"GGG",
				"PHS",
				" M ",
				'G', "sheetPlastic",
				'P',GOLDEN_PICKAXE,
				'H', factoryHammerItem,
				'S', GOLDEN_SHOVEL,
				'M', machineBaseItem,
		});

		registerMachine(WeatherCollector, new Object[] {
				"GGG",
				"BBB",
				"UMU",
				'G', "sheetPlastic",
				'B', IRON_BARS,
				'U', BUCKET,
				'M', machineBaseItem,
		});

		registerMachine(SludgeBoiler, new Object[] {
				"GGG",
				"FFF",
				" M ",
				'G', "sheetPlastic",
				'F', FURNACE,
				'M', machineBaseItem,
		});

		registerMachine(Sewer, 4, new Object[] {
				"GGG",
				"BUB",
				"BMB",
				'G', "sheetPlastic",
				'B', BRICK,
				'U', BUCKET,
				'M', machineBaseItem,
		});

		registerMachine(Composter, new Object[] {
				"GGG",
				"PFP",
				" M ",
				'G', "sheetPlastic",
				'P', PISTON,
				'F', FURNACE,
				'M', machineBaseItem,
		});

		registerMachine(Breeder, new Object[] {
				"GGG",
				"CAC",
				"PMP",
				'G', "sheetPlastic",
				'P', "dyePurple",
				'C', GOLDEN_CARROT,
				'A', GOLDEN_APPLE,
				'M', machineBaseItem,
		});

		registerMachine(Grinder, new Object[] {
				"GGG",
				"BSP",
				" M ",
				'G', "sheetPlastic",
				'P', PISTON,
				'B', BOOK,
				'S', GOLDEN_SWORD,
				'M', machineBaseItem,
		});

		registerMachine(AutoEnchanter, new Object[] {
				"GGG",
				"BBB",
				"DMD",
				'G', "sheetPlastic",
				'B', BOOK,
				'D', DIAMOND,
				'M', machineBaseItem,
		});

		registerMachine(Chronotyper, new Object[] {
				"GGG",
				"EEE",
				"PMP",
				'G', "sheetPlastic",
				'E', EMERALD,
				'P', "dyePurple",
				'M', machineBaseItem,
		});

		registerMachine(Ejector, 8, new Object[] {
				"GGG",
				" D ",
				"RMR",
				'G', "sheetPlastic",
				'D', DROPPER,
				'R', "dustRedstone",
				'M', machineBaseItem,
		});

		registerMachine(ItemRouter, 8, new Object[] {
				"GGG",
				"RCR",
				" M ",
				'G', "sheetPlastic",
				'C', CHEST,
				'R', REPEATER,
				'M', machineBaseItem,
		});

		registerMachine(LiquidRouter, 8, new Object[] {
				"GGG",
				"RBR",
				"PMP",
				'G', "sheetPlastic",
				'R', REPEATER,
				'B', BUCKET,
				'P', plasticPipeBlock,
				'M', machineBaseItem,
		});

		int dsuCount = craftSingleDSU.getBoolean(false) ? 1 : 4;
		registerMachine(DeepStorageUnit, dsuCount, new Object[] {
				"GGG",
				"PPP",
				"EME",
				'G', "sheetPlastic",
				'P', ENDER_PEARL,
				'E', ENDER_EYE,
				'M', machineBaseItem,
		});

		if (enableCheapDSU.getBoolean(false)) {
			registerMachine(DeepStorageUnit, new Object[] {
					"GGG",
					"CCC",
					"CMC",
					'G', "sheetPlastic",
					'C', CHEST,
					'M', machineBaseItem,
			});
		}

		registerMachine(LiquiCrafter, new Object[] {
				"GGG",
				"BWB",
				"FMF",
				'G', "sheetPlastic",
				'B', BUCKET,
				'W', CRAFTING_TABLE,
				'F', ITEM_FRAME,
				'M', machineBaseItem,
		});

		registerMachine(LavaFabricator, new Object[] {
				"GGG",
				"OBO",
				"CMC",
				'G', "sheetPlastic",
				'O', OBSIDIAN,
				'B', BLAZE_ROD,
				'C', MAGMA_CREAM,
				'M', machineBaseItem,
		});

		registerMachine(SteamBoiler, new Object[] {
				"GGG",
				"OTO",
				"NBN",
				'G', "sheetPlastic",
				'T', Items.CAULDRON,
				'O', OBSIDIAN,
				'N', NETHER_BRICK_STAIRS,
				'B', SludgeBoiler,
		});

		registerMachine(AutoJukebox, new Object[] {
				"GGG",
				" J ",
				" M ",
				'G', "sheetPlastic",
				'J', JUKEBOX,
				'M', machineBaseItem,
		});

		registerMachine(Unifier, new Object[] {
				"GGG",
				"CBC",
				" M ",
				'G', "sheetPlastic",
				'B', BOOK,
				'C', COMPARATOR,
				'M', machineBaseItem,
		});

		registerMachine(AutoSpawner, new Object[] {
				"GGG",
				"NCS",
				"EME",
				'G', "sheetPlastic",
				'C', MAGMA_CREAM,
				'N', Items.NETHER_WART,
				'S', SUGAR,
				'E', "gemEmerald",
				'M', machineBaseItem,
		});

		registerMachine(BioReactor, new Object[] {
				"GGG",
				"UEU",
				"SMS",
				'G', "sheetPlastic",
				'U', SUGAR,
				'E', FERMENTED_SPIDER_EYE,
				'S', "slimeball",
				'M', machineBaseItem,
		});

		registerMachine(BioFuelGenerator, new Object[] {
				"GGG",
				"PFP",
				"RMR",
				'G', "sheetPlastic",
				'F', FURNACE,
				'P', PISTON,
				'R', BLAZE_ROD,
				'M', machineBaseItem,
		});

		registerMachine(AutoDisenchanter, new Object[] {
				"GGG",
				"RDR",
				"BMB",
				'G', "sheetPlastic",
				'B', BOOK,
				'D', DIAMOND,
				'R', NETHER_BRICK,
				'M', machineBaseItem,
		});

		registerMachine(Slaughterhouse, new Object[] {
				"GGG",
				"SSS",
				"XMX",
				'G', "sheetPlastic",
				'S', GOLDEN_SWORD,
				'X', GOLDEN_AXE,
				'M', machineBaseItem,
		});

		registerMachine(MeatPacker, new Object[] {
				"GGG",
				"BFB",
				"BMB",
				'G', "sheetPlastic",
				'B', BRICK_BLOCK,
				'F', FLINT_AND_STEEL,
				'M', machineBaseItem,
		});

		registerMachine(EnchantmentRouter, new Object[] {
				"GGG",
				"RBR",
				" M ",
				'G', "sheetPlastic",
				'B', BOOK,
				'R', REPEATER,
				'M', machineBaseItem,
		});

		registerMachine(LaserDrill, new Object[] {
				"GGG",
				"LLL",
				"DMD",
				'G', "sheetPlastic",
				'L', GLOWSTONE,
				'D', "gemDiamond",
				'M', machineBaseItem,
		});

		registerMachine(LaserDrillPrecharger, new Object[] {
				"GGG",
				"LSL",
				"DMD",
				'G', "sheetPlastic",
				'L', GLOWSTONE,
				'D', "gemDiamond",
				'S', stack(pinkSlimeItem, 1, 1),
				'M', machineBaseItem,
		});

		registerMachine(AutoAnvil, new Object[] {
				"GGG",
				"AAA",
				" M ",
				'G', "sheetPlastic",
				'A', ANVIL,
				'M', machineBaseItem,
		});

		registerMachine(BlockSmasher, new Object[] {
				"GGG",
				"HHH",
				"BMB",
				'G', "sheetPlastic",
				'H', factoryHammerItem,
				'B', BOOK,
				'M', machineBaseItem,
		});

		registerMachine(RedNote, new Object[] {
				"GGG",
				"CNC",
				" M ",
				'G', "sheetPlastic",
				'C', "cableRedNet",
				'N', NOTEBLOCK,
				'M', machineBaseItem,
		});

		registerMachine(AutoBrewer, new Object[] {
				"GGG",
				"CBC",
				"RMR",
				'G', "sheetPlastic",
				'C', plasticPipeBlock,
				'B', Items.BREWING_STAND,
				'R', COMPARATOR,
				'M', machineBaseItem,
		});

		registerMachine(FruitPicker, new Object[] {
				"GGG",
				"SXS",
				" M ",
				'G', "sheetPlastic",
				'S', SHEARS,
				'X', GOLDEN_AXE,
				'M', machineBaseItem,
		});

		registerMachine(BlockPlacer, new Object[] {
				"GGG",
				"DDD",
				" M ",
				'G', "sheetPlastic",
				'D', DISPENSER,
				'M', machineBaseItem,
		});

		registerMachine(MobCounter, new Object[] {
				"GGG",
				"RCR",
				"SMS",
				'G', "sheetPlastic",
				'R', REPEATER,
				'C', COMPARATOR,
				'S', spyglassItem,
				'M', machineBaseItem,
		});

		registerMachine(SteamTurbine, new Object[] {
				"GGG",
				"PFP",
				"RMR",
				'G', "sheetPlastic",
				'F', FURNACE,
				'P', PISTON,
				'R', NETHERBRICK,
				'M', machineBaseItem,
		});

		registerMachine(ChunkLoader, new Object[] {
				"GGG",
				"PFP",
				"RMR",
				'G', "sheetPlastic",
				'F', NETHER_STAR,
				'P', DeepStorageUnit,
				'R', "blockRedstone",
				'M', machineBaseItem,
		});
		if (enableCheapCL.getBoolean(false)) {
			registerMachine(ChunkLoader, new Object[] {
					"GGG",
					"PFP",
					"RMR",
					'G', "sheetPlastic",
					'F', "blockGold",
					'P', ENDER_EYE,
					'R', "blockRedstone",
					'M', machineBaseItem,
			});
		}

		registerMachine(Fountain, new Object[] {
				"GBG",
				"GBG",
				"UMU",
				'G', "sheetPlastic",
				'B', IRON_BARS,
				'U', BUCKET,
				'M', machineBaseItem,
		});

		registerMachine(MobRouter, new Object[] {
				"GGG",
				"BRB",
				"PCP",
				'G', "sheetPlastic",
				'B', IRON_BARS,
				'R', ItemRouter,
				'P', "dyeOrange",
				'C', Chronotyper,
		});

		addShapedRecipe(stack(plasticTank, 1), new Object[] {
				"PPP",
				"P P",
				"PMP",
				'P', "sheetPlastic",
				'M', machineBaseItem,
		});
	}

	protected void registerMachine(Machine machine, Object... recipe) {

		registerMachine(machine, 1, recipe);
	}

	protected void registerMachine(Machine machine, int amount, Object... recipe) {

		if (machine.getIsRecipeEnabled()) {
			@Nonnull ItemStack item = machine.getItemStack();
			item.setCount(amount);
			for (int i = recipe.length; i-- > 0;)
				if (recipe[i] instanceof Machine)
					recipe[i] = ((Machine) recipe[i]).getItemStack();
			addShapedRecipe(item, recipe);
		}
	}

	protected void registerMachineUpgrades() {

		if (_registeredMachineUpgrades) {
			return;
		}
		_registeredMachineUpgrades = true;

		String[] materials = { "gemLapis", "ingotTin", "ingotIron", "ingotCopper", "ingotBronze",
				"ingotSilver", "ingotGold", "gemQuartz", "gemDiamond", "ingotPlatinum", "gemEmerald",
				"cobblestone" };
		Object[] upgrade = { "nuggetGold", "nuggetGold", "nuggetGold", "nuggetGold", "nuggetGold",
				"nuggetGold", "nuggetGold", "nuggetGold", "nuggetGold", "nuggetGold", "nuggetGold",
				"nuggetGold" };
		String center = "dustPlastic";
		if (enableExpensiveUpgrades.getBoolean(false)) {
			center = "gemDiamond";
			upgrade[0] = stack(upgradeItem, 1, Arrays.asList(materials).indexOf("cobblestone"));
			for (int i = 1; i < 11; ++i)
				upgrade[i] = stack(upgradeItem, 1, i - 1);
		}

		for (int i = 0, e = materials.length; i < e; ++i) {
			addShapedRecipe(stack(upgradeItem, 1, upgradeItem.getMetadataValues()[i]), new Object[] {
					"III",
					"PCP",
					"RGR",
					'I', materials[i],
					'P', "dustPlastic",
					'C', center,
					'R', "dustRedstone",
					'G', upgrade[i],
			});
		}

		for (int i = 0; i < 16; i++) {
			addShapedRecipe(stack(laserFocusItem, 1, i), new Object[] {
					"ENE",
					"NGN",
					"ENE",
					'E', "gemEmerald",
					'N', "nuggetGold",
					'G', stack(factoryGlassPaneBlock, 1, i)
			});
		}
	}

	protected void registerMachineTinkers() {

		if (_registeredMachineTinkers) {
			return;
		}
		_registeredMachineTinkers = true;

		addRecipe(new ShapelessMachineTinker(Machine.ItemCollector, "Emits comparator signal",
				stack(GOLD_NUGGET)) {

			@Override
			protected boolean isMachineTinkerable(@Nonnull ItemStack machine) {

				return !machine.hasTagCompound() || !machine.getTagCompound().hasKey("hasTinkerStuff");
			}

			@Nonnull
			@Override
			protected ItemStack getTinkeredMachine(@Nonnull ItemStack machine) {

				machine = machine.copy();
				machine.setCount(1);
				NBTTagCompound tag = machine.getTagCompound();
				if (tag == null) machine.setTagCompound(tag = new NBTTagCompound());
				tag.setBoolean("hasTinkerStuff", true);
				return machine;
			}
		});
	}

	protected void registerConveyors() {

		if (_registeredConveyors) {
			return;
		}
		_registeredConveyors = true;

		addShapedRecipe(stack(conveyorBlock, 16, 16), new Object[] {
				"UUU",
				"RIR",
				'U', "itemRubber",
				'R', "dustRedstone",
				'I', "ingotIron",
		});

		for (int i = 0; i < 16; i++) {
			addShapelessRecipe(stack(conveyorBlock, 1, i),
				stack2(conveyorBlock, 1),
				"dyeCeramic" + DYES[15 - i]);
		}
	}

	protected void registerDecorative() {

		if (_registeredDecorative) {
			return;
		}
		_registeredDecorative = true;

		addSurroundRecipe(stack(factoryRoadBlock, 16), "sheetPlastic", stack(STONEBRICK, 1, 0));
		addRotatedGearRecipe(stack(factoryRoadBlock, 4, 1), stack(factoryRoadBlock, 1, 0), stack(REDSTONE_LAMP));
		addTwoWayConversionRecipe(stack(factoryRoadBlock, 1, 4), stack(factoryRoadBlock, 1, 1));
		{ // GLASS
			String pane = "paneGlass", glass = "blockGlass";
			for (int i = 0; i < 16; i++) {
				@Nonnull ItemStack ceramicDye = stack(ceramicDyeItem, 1, i);
				@Nonnull ItemStack glassStack = stack(factoryGlassBlock, 1, i);
				@Nonnull ItemStack paneStack = stack(factoryGlassPaneBlock, 1, i);
				String dye = DYES[15 - i];
				String dye2 = "dyeCeramic" + dye;
				String dye3 = "dye" + dye;
				addShapelessRecipe(cloneStack(ceramicDye, 4), stack(CLAY_BALL), dye3);
				addShapelessRecipe(cloneStack(ceramicDye, 8), stack(CLAY_BALL), stack(CLAY_BALL), dye3, dye3);
				addShapelessRecipe(cloneStack(glassStack, 1), dye2, glass);
				addShapelessRecipe(cloneStack(glassStack, 3), dye2, glass, glass, glass);
				addShapelessRecipe(cloneStack(glassStack, 6), dye2, dye2, glass, glass, glass, glass, glass, glass);
				addShapelessRecipe(cloneStack(paneStack, 1), dye2, pane);
				addShapelessRecipe(cloneStack(paneStack, 3), dye2, pane, pane, pane);
				addShapelessRecipe(cloneStack(paneStack, 8), dye2, pane, pane, pane, pane, pane, pane, pane, pane);

				addFenceRecipe(cloneStack(paneStack, 16), cloneStack(glassStack, 1));
			}
		}

		addTwoWayConversionRecipe(stack(factoryPlasticBlock, 1, 1), stack(factoryPlasticBlock, 1, 0));
		addShapedRecipe(stack(factoryPlasticBlock, 3, 2), new Object[] {
			"XXX",
			'X', stack(factoryPlasticBlock, 1, 1)
		});
		addSmallStorageRecipe(stack(factoryPlasticBlock, 4, 3), stack(factoryPlasticBlock, 1, 0));
		addSmallStorageRecipe(stack(factoryPlasticBlock, 4, 4), stack(factoryPlasticBlock, 1, 5));
		addShapedRecipe(stack(factoryPlasticBlock, 8, 5), new Object[] {
			"XXX",
			"X X",
			"XXX",
			'X', stack(factoryPlasticBlock, 1, 1)
		});
		addSmallStorageRecipe(stack(factoryPlasticBlock, 4, 6), stack(factoryPlasticBlock, 1, 3));

		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 0), stack2(ICE), stack2(BRICK_BLOCK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 1), stack2(GLOWSTONE), stack2(BRICK_BLOCK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 2), "blockLapis", stack2(BRICK_BLOCK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 3), stack2(OBSIDIAN), stack2(BRICK_BLOCK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 4), stack(STONE_SLAB, 1, 0), stack2(BRICK_BLOCK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 5), stack2(SNOW), stack2(BRICK_BLOCK));

		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 6), stack2(ICE), stack(STONEBRICK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 7), stack2(GLOWSTONE), stack(STONEBRICK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 8), "blockLapis", stack(STONEBRICK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 9), stack2(OBSIDIAN), stack(STONEBRICK));
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 4, 10), stack(STONE_SLAB, 1, 0), stack(STONEBRICK));
/*		TODO double slab doesn't have item as it did before, do we use something else instead here?
		if (enableSmoothSlabRecipe.getBoolean(true)) {
			addRecipe(stack(factoryDecorativeBrickBlock, 8, 10), new Object[] {
				"VV",
				"VV",
				'V', stack(DOUBLE_STONE_SLAB, 1, 8)
			});
		}*/
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 11), stack2(SNOW), stack(STONEBRICK));
		// 12 & 13 are special blocks
		addRotatedGearRecipe(stack(factoryDecorativeBrickBlock, 8, 14), stack(BRICK_BLOCK, 1, 0), stack(STONEBRICK));
		// 15 is special

		addSmallStorageRecipe(stack(factoryDecorativeBrickBlock, 4, 0), stack(factoryDecorativeBrickBlock, 1, 6));
		addSmallStorageRecipe(stack(factoryDecorativeBrickBlock, 4, 1), stack(factoryDecorativeBrickBlock, 1, 7));
		addSmallStorageRecipe(stack(factoryDecorativeBrickBlock, 4, 2), stack(factoryDecorativeBrickBlock, 1, 8));
		addSmallStorageRecipe(stack(factoryDecorativeBrickBlock, 4, 3), stack(factoryDecorativeBrickBlock, 1, 9));
		addSmallStorageRecipe(stack(factoryDecorativeBrickBlock, 4, 4), stack(factoryDecorativeBrickBlock, 1, 10));
		addSmallStorageRecipe(stack(factoryDecorativeBrickBlock, 4, 5), stack(factoryDecorativeBrickBlock, 1, 11));
		addSmallStorageRecipe(stack(BRICK_BLOCK, 2, 0), stack(factoryDecorativeBrickBlock, 1, 14));

		/**
		 * Smooth:
		 **/
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 0), "dyeBlack", "stone");
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 1), stack2(SUGAR), "stone");

		/**
		 * Cobble:
		 **/
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 2), "dyeBlack", "cobblestone");
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 3), stack2(SUGAR), "cobblestone");

		/**
		 * Large brick:
		 **/
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 4), "dyeBlack", stack(STONEBRICK));
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 5), stack2(SUGAR), stack(STONEBRICK));
		// smooth->large brick
		addSmallStorageRecipe(stack(factoryDecorativeStoneBlock, 4, 4), stack(factoryDecorativeStoneBlock, 1, 0));
		addSmallStorageRecipe(stack(factoryDecorativeStoneBlock, 4, 5), stack(factoryDecorativeStoneBlock, 1, 1));

		// TODO: add cracked large bricks?

		/**
		 * Small brick:
		 **/
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 6), "dyeBlack", stack2(BRICK_BLOCK));
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 7), stack2(SUGAR), stack2(BRICK_BLOCK));
		// large brick->small brick
		addSmallStorageRecipe(stack(factoryDecorativeStoneBlock, 4, 6), stack(factoryDecorativeStoneBlock, 1, 4));
		addSmallStorageRecipe(stack(factoryDecorativeStoneBlock, 4, 7), stack(factoryDecorativeStoneBlock, 1, 5));

		/**
		 * Gravel:
		 **/
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 8), "dyeBlack", stack2(GRAVEL));
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 8, 9), stack2(SUGAR), stack2(GRAVEL));

		/**
		 * Paved:
		 **/
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 4, 10), "dyeBlack", stack(STONE_SLAB, 1, 0));
		addSurroundRecipe(stack(factoryDecorativeStoneBlock, 4, 11), stack2(SUGAR), stack(STONE_SLAB, 1, 0));
		// smooth<->paved
		addShapelessRecipe(stack(factoryDecorativeStoneBlock, 1, 0), stack(factoryDecorativeStoneBlock, 1, 10));
		addShapelessRecipe(stack(factoryDecorativeStoneBlock, 1, 1), stack(factoryDecorativeStoneBlock, 1, 11));

		// TODO: add white/black sand?

		addStorageRecipe(stack(meatIngotRawItem), "nuggetMeatRaw");
		addStorageRecipe(stack(meatIngotCookedItem), "nuggetMeat");
		addStorageRecipe(stack(factoryDecorativeBrickBlock, 1, 12), "ingotMeatRaw");
		addStorageRecipe(stack(factoryDecorativeBrickBlock, 1, 13), "ingotMeat");

		addReverseStorageRecipe(stack(meatIngotRawItem), stack(factoryDecorativeBrickBlock, 1, 12));
		addReverseStorageRecipe(stack(meatIngotCookedItem), stack(factoryDecorativeBrickBlock, 1, 13));
		addReverseStorageRecipe(stack(meatNuggetRawItem), "ingotMeatRaw");
		addReverseStorageRecipe(stack(meatNuggetCookedItem), "ingotMeat");

		addTwoWayStorageRecipe(stack(pinkSlimeBlock), stack(pinkSlimeItem));
	}

	protected void registerSyringes() {

		if (_registeredSyringes) {
			return;
		}
		_registeredSyringes = true;

		addShapedRecipe(stack(xpExtractorItem), new Object[] {
				"PLP",
				"PLP",
				"RPR",
				'R', "itemRubber",
				'L', "blockGlass",
				'P', "sheetPlastic",
		});

		addShapedRecipe(stack(syringeEmptyItem, 1), new Object[] {
				"PRP",
				"P P",
				" I ",
				'P', "sheetPlastic",
				'R', "itemRubber",
				'I', "ingotIron",
		});

		addShapelessRecipe(stack(syringeHealthItem), new Object[] { syringeEmptyItem, APPLE });
		addShapelessRecipe(stack(syringeGrowthItem), new Object[] { syringeEmptyItem, GOLDEN_CARROT });

		addSurroundRecipe(stack(syringeZombieItem, 1), stack(syringeEmptyItem), stack2(ROTTEN_FLESH));

		addShapedRecipe(stack(syringeSlimeItem, 1), new Object[] {
				" S ",
				"BLB",
				'B', "slimeball",
				'L', "gemLapis",
				'S', syringeEmptyItem,
		});

		addShapelessRecipe(stack(syringeCureItem), new Object[] { syringeEmptyItem, GOLDEN_APPLE });
	}

	protected void registerPlastics() {

		if (_registeredPlastics) {
			return;
		}
		_registeredPlastics = true;

		addShapedRecipe(stack(machineBaseItem, 3), new Object[] {
				"PPP",
				"SSS",
				'P', "sheetPlastic",
				'S', "stone",
		});

		addSmallStorageRecipe(stack(plasticSheetItem, 4), "dustPlastic");

		addSmallStorageRecipe(stack(factoryPlasticBlock, 1), "sheetPlastic");
		addSmallReverseStorageRecipe(stack(plasticSheetItem, 4), "blockPlastic");

		addShapedRecipe(stack(factoryHammerItem, 1), new Object[] {
				"PPP",
				" S ",
				" S ",
				'P', "sheetPlastic",
				'S', "stickWood",
		});

		addShapedRecipe(stack(strawItem), new Object[] {
				"PP",
				"P ",
				"P ",
				'P', "sheetPlastic",
		});

		addShapedRecipe(stack(rulerItem), new Object[] {
				"P",
				"A",
				"P",
				'P', "sheetPlastic",
				'A', PAPER,
		});

		addShapedRecipe(stack(plasticCupItem, 16), new Object[] {
				" P ",
				"P P",
				'P', "sheetPlastic",
		});
		/*
		 * RecipeHelper.addShapedRecipe(stack(plasticCellItem, 12), new Object[] {
		 * " P ",
		 * "P P",
		 * " P ",
		 * 'P', "sheetPlastic",
		 * } ));//
		 */

		addShapedRecipe(stack(plasticBagItem, 3), new Object[] {
				"SPS",
				"P P",
				"PPP",
				'P', "sheetPlastic",
				'S', STRING
		});

		addShapelessRecipe(stack(plasticBagItem), plasticBagItem);

		addShapedRecipe(stack(plasticBootsItem, 1), new Object[] {
				"P P",
				"P P",
				'P', "sheetPlastic",
		});

		addShapedRecipe(stack(plasticPipeBlock, 8), new Object[] {
				"PPP",
				"   ",
				"PPP",
				'P', "sheetPlastic",
		});
	}

	protected void registerArmor() {

		if (_registeredArmor) {
			return;
		}
		_registeredArmor = true;

		addShapedRecipe(stack(plasticGlasses, 1), new Object[] {
				"GPG",
				"P P",
				'P', "sheetPlastic",
				'G', "paneGlassBlack",
		});

		addShapedRecipe(stack(plasticHelmetItem, 1), new Object[] {
				"PPP",
				"P P",
				'P', "sheetPlastic",
		});

		addShapedRecipe(stack(plasticChestplateItem, 1), new Object[] {
				"P P",
				"PPP",
				"PPP",
				'P', "sheetPlastic",
		});

		addShapedRecipe(stack(plasticLeggingsItem, 1), new Object[] {
				"PPP",
				"P P",
				"P P",
				'P', "sheetPlastic",
		});
	}

	protected void registerMiscItems() {

		if (_registeredMiscItems) {
			return;
		}
		_registeredMiscItems = true;

		addShapedRecipe(stack(fertilizerItem, 16), new Object[] {
				"WBW",
				"STS",
				"WBW",
				'W', Items.WHEAT,
				'B', stack(DYE, 1, 15),
				'S', STRING,
				'T', "stickWood",
		});

		addShapedRecipe(stack(spyglassItem), new Object[] {
				"GLG",
				"PLP",
				" S ",
				'G', "ingotGold",
				'L', "blockGlass",
				'P', "sheetPlastic",
				'S', "stickWood",
		});

		if (enablePortaSpawner.getBoolean(true))
			addShapedRecipe(stack(portaSpawnerItem), new Object[] {
					"GLG",
					"DND",
					"GLG",
					'G', "ingotGold",
					'L', "blockGlass",
					'D', "gemDiamond",
					'N', NETHER_STAR
			});

		addSurroundRecipe(stack(detCordBlock, 12), stack2(TNT), "itemRubber");

		addShapedRecipe(stack(fishingRodItem, 1), new Object[] {
				"DD ",
				"DFD",
				"TDD",
				'D', "wireExplosive",
				'F', FISHING_ROD,
				'T', REDSTONE_TORCH
		});
	}

	protected void registerSafariNets() {

		if (_registeredSafariNets) {
			return;
		}
		_registeredSafariNets = true;

		if (enableExpensiveSafariNet.getBoolean(false)) {
			addShapedRecipe(stack(safariNetItem, 1), new Object[] {
					"SLS",
					"PBP",
					"SPS",
					'S', STRING,
					'L', LEATHER,
					'P', ENDER_PEARL,
					'B', portaSpawnerItem,
			});
		} else {
			addGearRecipe(stack(safariNetItem, 1), stack2(ENDER_PEARL), stack2(GHAST_TEAR));
		}

		addShapedRecipe(stack(safariNetSingleItem, 3), new Object[] {
				"SPS",
				" B ",
				"S S",
				'S', STRING,
				'P', "sheetPlastic",
				'B', "slimeball",
		});

		addGearRecipe(stack(safariNetJailerItem, 1), stack2(IRON_BARS), stack(safariNetSingleItem));

		if (enableFancySafariNet.getBoolean(true))
			addShapedRecipe(stack(safariNetFancyJailerItem, 1), new Object[] {
					"GGG",
					"GBG",
					"GGG",
					'G', GOLD_NUGGET,
					'B', safariNetJailerItem,
			});

		if (enableNetLauncher.getBoolean(true))
			addShapedRecipe(stack(safariNetLauncherItem, 1), new Object[] {
					"PGP",
					"LGL",
					"IRI",
					'P', "sheetPlastic",
					'L', GLOWSTONE_DUST,
					'G', GUNPOWDER,
					'I', "ingotIron",
					'R', "dustRedstone",
			});
	}

	protected void registerSmelting() {

		if (_registeredSmelting) return;
		_registeredSmelting = true;

		addWeakSmelting(stack(rawRubberItem), stack(rubberBarItem));

		for (@Nonnull ItemStack s : getOres("itemRubber"))
			addSmelting(s, stack(rawPlasticItem), 0.3f);
		for (@Nonnull ItemStack s : getOres("blockPlastic"))
			addSmelting(s, stack(rawPlasticItem, 4));
		for (@Nonnull ItemStack s : getOres("sheetPlastic"))
			addSmelting(s, stack(rawPlasticItem));

		addSmelting(plasticBagItem, stack(rawPlasticItem, 2));
		addSmelting(strawItem, stack(rawPlasticItem, 4));
		addSmelting(rulerItem, stack(rawPlasticItem, 2));

		addSmelting(meatIngotRawItem, stack(meatIngotCookedItem), 0.5f);
		addSmelting(meatNuggetRawItem, stack(meatNuggetCookedItem), 0.3f);
		addWeakSmelting(stack(SUGAR), stack(sugarCharcoalItem));
		// cooked meat block -> charcoal
		addWeakSmelting(stack(factoryDecorativeBrickBlock, 1, 13), stack(COAL, 3, 1));
		addWeakSmelting(stack(rubberWoodBlock), stack(COAL, 1, 1));

		addSmelting(pinkSlimeBlock, stack(pinkSlimeItem, 1, 1), 0.5f);

		// decorative bricks: cobble->smooth
		addWeakSmelting(stack(factoryDecorativeStoneBlock, 1, 2), stack(factoryDecorativeStoneBlock, 1, 0));
		addWeakSmelting(stack(factoryDecorativeStoneBlock, 1, 3), stack(factoryDecorativeStoneBlock, 1, 1));
		// smooth -> paved
		addSmelting(stack(factoryDecorativeStoneBlock, 1, 0), stack(factoryDecorativeStoneBlock, 1, 10));
		addSmelting(stack(factoryDecorativeStoneBlock, 1, 1), stack(factoryDecorativeStoneBlock, 1, 11));
	}

	private void addWeakSmelting(ItemStack input, ItemStack output) {
		addSmelting(input, output, 0.1f);
	}

	protected void registerVanillaImprovements() {

		if (_registeredVanillaImprovements) {
			return;
		}
		_registeredVanillaImprovements = true;

		addShapelessRecipe(stack(PLANKS, 3, 3), stack(rubberWoodBlock));

		addShapelessRecipe(stack(PISTON, 1, 0), stack(STICKY_PISTON, 1, 0), "listAllmilk");

		addShapedRecipe(stack(STICKY_PISTON), new Object[] {
				"R",
				"P",
				'R', "itemRawRubber",
				'P', PISTON
		});

		addSurroundRecipe(stack(blankRecordItem, 1), stack2(PAPER), "dustPlastic");

		if (enableMossyCobbleRecipe.getBoolean(true)) {
			addShapelessRecipe(stack(MOSSY_COBBLESTONE), new Object[] {
					COBBLESTONE, COBBLESTONE, COBBLESTONE,
					COBBLESTONE, COBBLESTONE, COBBLESTONE,
					COBBLESTONE,
					"listAllwater",
					Items.WHEAT,
			});
			addShapelessRecipe(stack(STONEBRICK, 1, 1), new Object[] {
					stack(STONEBRICK, 1, 0), stack(STONEBRICK, 1, 0),
					stack(STONEBRICK, 1, 0), stack(STONEBRICK, 1, 0),
					stack(STONEBRICK, 1, 0), stack(STONEBRICK, 1, 0),
					stack(STONEBRICK, 1, 0),
					"listAllwater",
					Items.WHEAT,
			});
		}

/*		TODO double stone slab has no item as it did before. Use something else instead of it?
		if (enableSmoothSlabRecipe.getBoolean(true)) {
			addRecipe(stack(DOUBLE_STONE_SLAB, 3, 0), new Object[] {
				"VVV",
				'V', stack(DOUBLE_STONE_SLAB, 1, 8)
			});

			addRecipe(stack(DOUBLE_STONE_SLAB, 1, 8), new Object[] {
					"VV",
					'V', stack(STONE_SLAB, 1, 0)
			});
			addRecipe(stack(DOUBLE_STONE_SLAB, 1, 9), new Object[] {
				"VV",
				'V', stack(STONE_SLAB, 1, 1)
			});
		}
*/

		addShapedRecipe(stack(vineScaffoldBlock, 8), new Object[] {
				"VV",
				"VV",
				"VV",
				'V', VINE,
		});

		addShapelessRecipe(stack(milkBottleItem), new Object[] {
				MILK_BUCKET,
				GLASS_BOTTLE
		});

		addSurroundRecipe(stack(DIRT, 1, 2), stack(DIRT), stack(LEAVES, 1, 1));

		addShapelessRecipe(stack(fertileSoil), stack(DIRT, 1, 2), stack(fertilizerItem), "listAllmilk");

		addShapelessRecipe(MFRUtil.getBucketFor(MFRFluids.chocolateMilk), "listAllmilk", BUCKET, stack(DYE, 1, 3));

		addStorageRecipe(stack(factoryDecorativeBrickBlock, 1, 15), stack(sugarCharcoalItem));

		addShapedRecipe(stack(TORCH, 3), new Object[] {
				"R",
				"S",
				'R', "itemRawRubber",
				'S', "stickWood",
		});

		addShapedRecipe(stack(TORCH, 2), new Object[] {
				"C",
				"S",
				'C', "itemCharcoalSugar",
				'S', "stickWood",
		});

		for (@Nonnull ItemStack torchStone : getOres("torchStone")) {
			if (torchStone.isEmpty())
				continue;
			addShapedRecipe(cloneStack(torchStone, 3), new Object[] {
					"R",
					"S",
					'R', "itemRawRubber",
					'S', "stoneRod",
			});

			addShapedRecipe(cloneStack(torchStone, 2), new Object[] {
					"C",
					"S",
					'C', "itemCharcoalSugar",
					'S', "stoneRod",
			});
			break;
		}
	}

	protected void registerRails() {

		if (_registeredRails) {
			return;
		}
		_registeredRails = true;

		addShapedRecipe(stack(railPickupCargoBlock, 2), new Object[] {
				" C ",
				"SDS",
				"SSS",
				'C', CHEST,
				'S', "sheetPlastic",
				'D', DETECTOR_RAIL
		});

		addShapedRecipe(stack(railDropoffCargoBlock, 2), new Object[] {
				"SSS",
				"SDS",
				" C ",
				'C', CHEST,
				'S', "sheetPlastic",
				'D', DETECTOR_RAIL
		});

		addShapedRecipe(stack(railPickupPassengerBlock, 3), new Object[] {
				" L ",
				"SDS",
				"SSS",
				'L', LAPIS_BLOCK,
				'S', "sheetPlastic",
				'D', DETECTOR_RAIL
		});

		addShapedRecipe(stack(railDropoffPassengerBlock, 3), new Object[] {
				"SSS",
				"SDS",
				" L ",
				'L', LAPIS_BLOCK,
				'S', "sheetPlastic",
				'D', DETECTOR_RAIL
		});
	}

	protected void registerGuns() {

		if (_registeredGuns) {
			return;
		}
		_registeredGuns = true;

		addShapedRecipe(stack(needlegunItem), new Object[] {
				"PGP",
				"PLP",
				"SIS",
				'P', "sheetPlastic",
				'I', "ingotIron",
				'S', MAGMA_CREAM,
				'L', safariNetLauncherItem,
				'G', spyglassItem
		});

		addShapedRecipe(stack(potatoLauncherItem), new Object[] {
				" L ",
				"PLP",
				"PTP",
				'P', "sheetPlastic",
				'L', stack(plasticPipeBlock),
				'T', stack(plasticTank)
		});

		addShapedRecipe(stack(rocketLauncherItem), new Object[] {
				"PCP",
				"PRP",
				"ILI",
				'P', "sheetPlastic",
				'I', MINECART,
				'L', needlegunItem,
				'R', stack(logicCardItem, 1, 1),
				'C', stack(logicCardItem, 1, 2)
		});

		addShapedRecipe(stack(needlegunAmmoEmptyItem, 4), new Object[] {
				"P P",
				"PIP",
				"PPP",
				'P', "sheetPlastic",
				'I', "ingotIron",
		});

		addShapedRecipe(stack(rocketItem, 2, 0), new Object[] {
				"PCP",
				"PTP",
				"IMI",
				'C', stack(logicCardItem, 1, 0),
				'M', needlegunAmmoEmptyItem,
				'P', "sheetPlastic",
				'T', TNT,
				'I', FIREWORKS
		});

		addShapedRecipe(stack(rocketItem, 2, 1), new Object[] {
				"PPP",
				"PTP",
				"IMI",
				'M', needlegunAmmoEmptyItem,
				'P', "sheetPlastic",
				'T', TNT,
				'I', FIREWORKS
		});

		addShapelessRecipe(stack(rocketItem, 2, 0), new Object[] {
				stack(rocketItem, 1, 1),
				stack(logicCardItem, 1, 0),
				stack(rocketItem, 1, 1)
		});

		addShapedRecipe(stack(needlegunAmmoStandardItem), new Object[] {
				"AAA",
				"AAA",
				"GMG",
				'A', ARROW,
				'M', needlegunAmmoEmptyItem,
				'G', GUNPOWDER
		});

		addShapedRecipe(stack(needlegunAmmoPierceItem), new Object[] {
				"AAA",
				"AAA",
				"GMG",
				'A', FLINT,
				'M', needlegunAmmoEmptyItem,
				'G', GUNPOWDER
		});

		addShapedRecipe(stack(needlegunAmmoAnvilItem), new Object[] {
				"SAS",
				"STS",
				"SMS",
				'S', STRING,
				'A', stack(ANVIL, 1, 0),
				'T', TNT,
				'M', needlegunAmmoEmptyItem,
		});

		addShapelessRecipe(stack(needlegunAmmoFireItem),
			needlegunAmmoPierceItem, FLINT_AND_STEEL);

		addShapelessRecipe(stack(needlegunAmmoLavaItem),
			needlegunAmmoStandardItem, plasticCupItem,
			LAVA_BUCKET);

		addShapelessRecipe(stack(needlegunAmmoSludgeItem),
			needlegunAmmoStandardItem, plasticCupItem,
			MFRUtil.getBucketFor(MFRFluids.sludge));

		addShapelessRecipe(stack(needlegunAmmoSewageItem),
			needlegunAmmoStandardItem, plasticCupItem,
				MFRUtil.getBucketFor(MFRFluids.sewage));
	}

	protected void registerRedNet() {

		if (_registeredRedNet) {
			return;
		}
		_registeredRedNet = true;

		addShapedRecipe(stack(rednetCableBlock, 8), new Object[] {
				"PPP",
				"RRR",
				"PPP",
				'R', "dustRedstone",
				'P', "sheetPlastic",
		});

		addShapelessRecipe(stack(rednetCableBlock, 5), new Object[] {
				"dustRedstone",
				"dustRedstone",
				stack(plasticPipeBlock),
				stack(plasticPipeBlock),
				stack(plasticPipeBlock),
				stack(plasticPipeBlock),
				stack(plasticPipeBlock),
		});

		addShapelessRecipe(stack(rednetCableBlock, 1, 2), new Object[] {
				"nuggetGold",
				"nuggetGold",
				"nuggetGold",
				"dustRedstone",
				"dustRedstone",
				stack(rednetCableBlock),
		});

		addShapelessRecipe(stack(rednetCableBlock, 6, 2), new Object[] {
				"ingotGold",
				"ingotGold",
				"blockRedstone",
				stack(rednetCableBlock),
				stack(rednetCableBlock),
				stack(rednetCableBlock),
				stack(rednetCableBlock),
				stack(rednetCableBlock),
				stack(rednetCableBlock),
		});

		addShapedRecipe(stack(machineBlock, 1, 1), new Object[] {
				"PRP",
				"RGR",
				"PIP",
				'R', "dustRedstone",
				'P', "sheetPlastic",
				'G', "blockGlass",
				'I', "ingotIron",
		});

		addShapedRecipe(stack(rednetLogicBlock), new Object[] {
				"RDR",
				"LGL",
				"PHP",
				'H', stack(machineBlock, 1, 1),
				'P', "sheetPlastic",
				'G', "ingotGold",
				'L', "gemLapis",
				'D', "gemDiamond",
				'R', "dustRedstone",
		});

		addShapedRecipe(stack(logicCardItem, 1, 0), new Object[] {
				"RPR",
				"PGP",
				"RPR",
				'P', "sheetPlastic",
				'G', "ingotGold",
				'R', "dustRedstone",
		});

		addShapedRecipe(stack(logicCardItem, 1, 1), new Object[] {
				"GPG",
				"PCP",
				"RGR",
				'C', stack(logicCardItem, 1, 0),
				'P', "sheetPlastic",
				'G', "ingotGold",
				'R', "dustRedstone",
		});

		addShapedRecipe(stack(logicCardItem, 1, 2), new Object[] {
				"DPD",
				"RCR",
				"GDG",
				'C', stack(logicCardItem, 1, 1),
				'P', "sheetPlastic",
				'G', "ingotGold",
				'D', "gemDiamond",
				'R', "dustRedstone",
		});

		addShapedRecipe(stack(rednetMeterItem, 1, 0), new Object[] {
				" G",
				"PR",
				"PP",
				'P', "sheetPlastic",
				'G', "nuggetGold",
				'R', "dustRedstone",
		});

		addShapedRecipe(stack(rednetMeterItem, 1, 1), new Object[] {
				"RGR",
				"IMI",
				"PPP",
				'P', "sheetPlastic",
				'G', "ingotGold",
				'I', "ingotIron",
				'R', "dustRedstone",
				'M', stack(rednetMeterItem, 1, 0)
		});

		addShapedRecipe(stack(rednetMemoryCardItem, 1, 0), new Object[] {
				"GGG",
				"PRP",
				"PPP",
				'P', "sheetPlastic",
				'G', "nuggetGold",
				'R', "dustRedstone",
		});

		addShapedRecipe(stack(rednetPanelBlock, 1, 0), new Object[] {
				"PCP",
				"PBP",
				"KPK",
				'P', "sheetPlastic",
				'C', rednetCableBlock,
				'B', BOOKSHELF,
				'K', "dyeBlack"
		});

		addShapelessRecipe(stack(rednetMemoryCardItem, 1, 0), stack(rednetMemoryCardItem, 1, 0));
	}

	private final void registerRedNetManual() {

		if (_registeredRedNetManual) {
			return;
		}
		_registeredRedNetManual = true;

		addShapelessRecipe(ItemBlockRedNetLogic.manual, plasticSheetItem, "dustRedstone", BOOK);
	}

	/* CREATING ItemStacks */

	public static ItemStack stack(Item t) {

		return new ItemStack(t);
	}

	public static ItemStack stack(Item t, int s) {

		return new ItemStack(t, s);
	}

	public static ItemStack stack(Item t, int s, int m) {

		return new ItemStack(t, s, m);
	}

	public static ItemStack stack(Block t) {

		return new ItemStack(t);
	}

	public static ItemStack stack(Block t, int s) {

		return new ItemStack(t, s);
	}

	public static ItemStack stack(Block t, int s, int m) {

		return new ItemStack(t, s, m);
	}

	public static ItemStack stack2(Item t) {

		return new ItemStack(t, 1, WILDCARD_VALUE);
	}

	public static ItemStack stack2(Item t, int s) {

		return new ItemStack(t, s, WILDCARD_VALUE);
	}

	public static ItemStack stack2(Block t) {

		return new ItemStack(t, 1, WILDCARD_VALUE);
	}

	public static ItemStack stack2(Block t, int s) {

		return new ItemStack(t, s, WILDCARD_VALUE);
	}

}
