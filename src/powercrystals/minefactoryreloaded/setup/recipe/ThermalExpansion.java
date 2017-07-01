/*
package powercrystals.minefactoryreloaded.setup.recipe;

import static cofh.lib.util.helpers.ItemHelper.*;
import static powercrystals.minefactoryreloaded.setup.MFRThings.*;

import cofh.api.modhelpers.ThermalExpansionHelper;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.MissingModsException;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.versioning.ArtifactVersion;
import net.minecraftforge.fml.common.versioning.DefaultArtifactVersion;

import java.util.Collections;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.ShapedOreRecipe;
import net.minecraftforge.oredict.ShapelessOreRecipe;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;

public class ThermalExpansion extends Vanilla {

	private static final String TE = "ThermalExpansion";
	private static final String TF = "ThermalFoundation";
	@Nonnull ItemStack conduitLiquid;
	@Nonnull ItemStack tankBasic;
	@Nonnull ItemStack tankReinforced;
	@Nonnull ItemStack lamp;
	@Nonnull ItemStack machineFrame;
	@Nonnull ItemStack pneumaticServo;
	@Nonnull ItemStack powerCoilElectrum;
	@Nonnull ItemStack powerCoilGold;
	@Nonnull ItemStack powerCoilSilver;
	@Nonnull ItemStack tesseractFrameFull;
	@Nonnull ItemStack strongBox;
	@Nonnull ItemStack strongBoxBasic;
	@Nonnull ItemStack sulfur;
	@Nonnull ItemStack multimeter;
	@Nonnull ItemStack invarAxe;
	@Nonnull ItemStack invarSword;
	@Nonnull ItemStack invarPickaxe;
	@Nonnull ItemStack invarShovel;
	@Nonnull ItemStack dynamoSteam;
	@Nonnull ItemStack tesseract;
	@Nonnull ItemStack tesseractFrameEmpty;
	@Nonnull ItemStack cellResonant;
	@Nonnull ItemStack cellRedstone;
	@Nonnull ItemStack igniter;

	@Override
	protected void gatherItems() {

		if (!Loader.isModLoaded(TE)) {
			MineFactoryReloadedCore.log().fatal("ThermalExpansion is required for ThermalExpansion recipes to be enabled.");
			throw new MissingModsException(Collections.
					singleton((ArtifactVersion) new DefaultArtifactVersion(TE)), MineFactoryReloadedCore.modId, MineFactoryReloadedCore.modName);
		}

		{
			conduitLiquid = new ItemStack(plasticPipeBlock);
		}

		*/
/* Blocks *//*

		tankBasic = GameRegistry.findItemStack(TE, "tankBasic", 1);
		tankReinforced = GameRegistry.findItemStack(TE, "tankReinforced", 1);
		lamp = GameRegistry.findItemStack(TE, "illuminator", 1);
		machineFrame = new ItemStack(GameRegistry.findBlock(TE, "Frame"), 1, 0);
		strongBox = GameRegistry.findItemStack(TE, "strongboxReinforced", 1);
		strongBoxBasic = GameRegistry.findItemStack(TE, "strongboxBasic", 1);
		dynamoSteam = GameRegistry.findItemStack(TE, "dynamoSteam", 1);
		tesseract = new ItemStack(GameRegistry.findBlock(TE, "Tesseract"), 1, 0);
		tesseractFrameEmpty = new ItemStack(GameRegistry.findBlock(TE, "Frame"), 1, 7);
		tesseractFrameFull = new ItemStack(GameRegistry.findBlock(TE, "Frame"), 1, 8);
		cellResonant = GameRegistry.findItemStack(TE, "cellResonant", 1);
		cellRedstone = GameRegistry.findItemStack(TE, "cellReinforced", 1);

		*/
/* Items *//*

		pneumaticServo = GameRegistry.findItemStack(TE, "pneumaticServo", 1);
		powerCoilElectrum = GameRegistry.findItemStack(TE, "powerCoilElectrum", 1);
		powerCoilGold = GameRegistry.findItemStack(TE, "powerCoilGold", 1);
		powerCoilSilver = GameRegistry.findItemStack(TE, "powerCoilSilver", 1);
		multimeter = GameRegistry.findItemStack(TE, "multimeter", 1);
		igniter = GameRegistry.findItemStack(TE, "igniter", 1);
		invarAxe = GameRegistry.findItemStack(TF, "toolInvarAxe", 1);
		invarSword = GameRegistry.findItemStack(TF, "toolInvarSword", 1);
		invarPickaxe = GameRegistry.findItemStack(TF, "toolInvarPickaxe", 1);
		invarShovel = GameRegistry.findItemStack(TF, "toolInvarShovel", 1);
		sulfur = GameRegistry.findItemStack(TF, "dustSulfur", 1);
	}

	@Override
	protected void registerMachines() {

		String prefix = "ingot";
		if (true) {
			prefix = "thermalexpansion:machine";
		}

		registerMachine(Machine.Planter, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Items.FLOWER_POT,
				'S', Blocks.PISTON,
				'F', machineFrame,
				'O', prefix + "Copper",
				'C', powerCoilGold,
		});

		registerMachine(Machine.Fisher, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Items.FISHING_ROD,
				'S', Items.BUCKET,
				'F', machineFrame,
				'O', prefix + "Iron",
				'C', powerCoilGold
		});

		registerMachine(Machine.Harvester, new Object[] {
				"PSP",
				"TFT",
				"OCO",
				'P', "sheetPlastic",
				'S', Items.SHEARS,
				'T', invarAxe,
				'F', machineFrame,
				'O', prefix + "Gold",
				'C', powerCoilGold
		});

		registerMachine(Machine.Rancher, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', conduitLiquid,
				'S', Items.SHEARS,
				'F', machineFrame,
				'O', prefix + "Tin",
				'C', powerCoilGold
		});

		registerMachine(Machine.Fertilizer, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Items.GLASS_BOTTLE,
				'S', Items.LEATHER,
				'F', machineFrame,
				'O', prefix + "Nickel",
				'C', powerCoilGold
		});

		registerMachine(Machine.Vet, new Object[] {
				"PTP",
				"TFT",
				"OCO",
				'P', "sheetPlastic",
				'T', syringeEmptyItem,
				'F', machineFrame,
				'O', prefix + "Copper",
				'C', powerCoilGold
		});

		registerMachine(Machine.ItemCollector, 8, new Object[] {
				"P P",
				" F ",
				"PCP",
				'P', "sheetPlastic",
				'F', machineFrame,
				'C', Blocks.CHEST
		});

		registerMachine(Machine.BlockBreaker, new Object[] {
				"PTP",
				"SFA",
				"OCO",
				'P', "sheetPlastic",
				'T', "gearInvar",
				'S', invarPickaxe,
				'F', machineFrame,
				'A', invarShovel,
				'O', prefix + "Iron",
				'C', powerCoilGold
		});

		registerMachine(Machine.WeatherCollector, new Object[] {
				"PBP",
				"TFT",
				"OCO",
				'P', "sheetPlastic",
				'B', Blocks.IRON_BARS,
				'T', Items.BUCKET,
				'F', machineFrame,
				'O', prefix + "Copper",
				'C', powerCoilGold
		});

		registerMachine(Machine.SludgeBoiler, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Items.BUCKET,
				'S', Blocks.FURNACE,
				'F', machineFrame,
				'O', prefix + "Iron",
				'C', powerCoilGold
		});

		registerMachine(Machine.Sewer, 4, new Object[] {
				"PTP",
				"SFS",
				"SQS",
				'P', "sheetPlastic",
				'T', Items.BUCKET,
				'S', Items.BRICK,
				'F', machineFrame,
				'Q', pneumaticServo,
		});

		registerMachine(Machine.Composter, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Blocks.FURNACE,
				'S', Blocks.PISTON,
				'F', machineFrame,
				'O', Items.BRICK,
				'C', powerCoilGold
		});

		registerMachine(Machine.Breeder, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Items.GOLDEN_APPLE,
				'S', Items.GOLDEN_CARROT,
				'F', machineFrame,
				'O', "dyePurple",
				'C', powerCoilGold
		});

		registerMachine(Machine.Grinder, new Object[] {
				"PTP",
				"OFO",
				"SCS",
				'P', "sheetPlastic",
				'T', invarSword,
				'O', Items.BOOK,
				'F', machineFrame,
				'S', prefix + "Tin",
				'C', powerCoilGold
		});

		registerMachine(Machine.AutoEnchanter, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Blocks.OBSIDIAN,
				'S', Items.BOOK,
				'F', machineFrame,
				'O', "gemDiamond",
				'C', powerCoilGold
		});

		registerMachine(Machine.Chronotyper, new Object[] {
				"PTP",
				"TFT",
				"OCO",
				'P', "sheetPlastic",
				'T', "gemEmerald",
				'F', machineFrame,
				'O', "dyePurple",
				'C', powerCoilGold
		});

		registerMachine(Machine.Ejector, 8, new Object[] {
				"PPP",
				" T ",
				"OFO",
				'P', "sheetPlastic",
				'T', pneumaticServo,
				'F', machineFrame,
				'O', "dustRedstone"
		});

		registerMachine(Machine.ItemRouter, 8, new Object[] {
				"PTP",
				"SFS",
				"PSP",
				'P', "sheetPlastic",
				'T', Blocks.CHEST,
				'S', multimeter,
				'F', machineFrame
		});

		registerMachine(Machine.LiquidRouter, 8, new Object[] {
				"PTP",
				"SFS",
				"PSP",
				'P', "sheetPlastic",
				'T', conduitLiquid,
				'S', multimeter,
				'F', machineFrame
		});

		int dsuCount = MFRConfig.craftSingleDSU.getBoolean(false) ? 1 : 4;
		registerMachine(Machine.DeepStorageUnit, dsuCount, new Object[] {
				"PCP",
				"CFC",
				"PCP",
				'P', "sheetPlastic",
				'C', strongBox,
				'F', tesseractFrameFull
		});

		if (MFRConfig.enableCheapDSU.getBoolean(false)) {
			registerMachine(Machine.DeepStorageUnit, new Object[] {
					"PCP",
					"CFC",
					"PCP",
					'P', "sheetPlastic",
					'C', strongBoxBasic,
					'F', machineFrame
			});
		}

		registerMachine(Machine.LiquiCrafter, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Blocks.CRAFTING_TABLE,
				'S', tankBasic,
				'F', machineFrame,
				'O', Items.BOOK,
				'C', pneumaticServo
		});

		registerMachine(Machine.LavaFabricator, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Blocks.OBSIDIAN,
				'S', Items.MAGMA_CREAM,
				'F', machineFrame,
				'O', Items.BLAZE_ROD,
				'C', powerCoilGold
		});

		registerMachine(Machine.SteamBoiler, new Object[] {
				"PPP",
				"TBT",
				"SSS",
				'P', "sheetPlastic",
				'T', tankReinforced,
				'B', Machine.SludgeBoiler.getItemStack(),
				'S', Blocks.NETHER_BRICK_STAIRS
		});

		registerMachine(Machine.AutoJukebox, new Object[] {
				"PJP",
				"PFP",
				'P', "sheetPlastic",
				'J', Blocks.JUKEBOX,
				'F', machineFrame
		});

		registerMachine(Machine.Unifier, new Object[] {
				"PTP",
				"OFO",
				"SCS",
				'P', "sheetPlastic",
				'T', multimeter,
				'O', Items.COMPARATOR,
				'F', machineFrame,
				'S', prefix + "Silver",
				'C', Items.BOOK
		});

		registerMachine(Machine.AutoSpawner, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Items.NETHER_WART,
				'S', Items.MAGMA_CREAM,
				'F', machineFrame,
				'O', "gemEmerald",
				'C', powerCoilGold
		});

		registerMachine(Machine.BioReactor, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Items.FERMENTED_SPIDER_EYE,
				'S', "slimeball",
				'F', machineFrame,
				'O', Items.BRICK,
				'C', Items.SUGAR
		});

		registerMachine(Machine.BioFuelGenerator, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Blocks.FURNACE,
				'S', Blocks.PISTON,
				'F', machineFrame,
				'O', Items.BLAZE_ROD,
				'C', powerCoilSilver
		});

		registerMachine(Machine.AutoDisenchanter, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', Blocks.NETHER_BRICK,
				'S', Items.BOOK,
				'F', machineFrame,
				'O', "gemDiamond",
				'C', powerCoilGold
		});

		registerMachine(Machine.Slaughterhouse, new Object[] {
				"GIG",
				"SFS",
				"XCX",
				'G', "sheetPlastic",
				'S', invarSword,
				'X', invarAxe,
				'I', "gearInvar",
				'F', machineFrame,
				'C', powerCoilGold
		});

		registerMachine(Machine.MeatPacker, new Object[] {
				"GSG",
				"BFB",
				"BCB",
				'G', "sheetPlastic",
				'B', Blocks.BRICK_BLOCK,
				'S', igniter,
				'F', machineFrame,
				'C', powerCoilGold
		});

		registerMachine(Machine.EnchantmentRouter, new Object[] {
				"PBP",
				"SFS",
				"PSP",
				'P', "sheetPlastic",
				'B', Items.BOOK,
				'S', Items.REPEATER,
				'F', machineFrame
		});

		registerMachine(Machine.LaserDrill, new Object[] {
				"GFG",
				"CFC",
				"DHD",
				'G', "sheetPlastic",
				'D', "gemDiamond",
				'H', "blockGlassHardened",
				'F', lamp,
				'C', powerCoilGold
		});

		registerMachine(Machine.LaserDrillPrecharger, new Object[] {
				"GSG",
				"HFH",
				"CDC",
				'G', "sheetPlastic",
				'D', "gemDiamond",
				'S', new ItemStack(pinkSlimeItem, 1, 1),
				'H', "blockGlassHardened",
				'F', lamp,
				'C', powerCoilElectrum
		});

		registerMachine(Machine.AutoAnvil, new Object[] {
				"GGG",
				"AFA",
				"OCO",
				'G', "sheetPlastic",
				'A', Blocks.ANVIL,
				'F', machineFrame,
				'C', powerCoilGold,
				'O', prefix + "Iron"
		});

		registerMachine(Machine.BlockSmasher, new Object[] {
				"GPG",
				"HFH",
				"BCB",
				'G', "sheetPlastic",
				'P', Blocks.PISTON,
				'H', factoryHammerItem,
				'B', Items.BOOK,
				'F', machineFrame,
				'C', powerCoilGold
		});

		registerMachine(Machine.RedNote, new Object[] {
				"GNG",
				"CFC",
				'G', "sheetPlastic",
				'C', "cableRedNet",
				'N', Blocks.NOTEBLOCK,
				'F', machineFrame
		});

		registerMachine(Machine.AutoBrewer, new Object[] {
				"GBG",
				"CFC",
				"RPR",
				'G', "sheetPlastic",
				'C', conduitLiquid,
				'B', Items.BREWING_STAND,
				'R', Items.REPEATER,
				'F', machineFrame,
				'P', powerCoilGold
		});

		registerMachine(Machine.FruitPicker, new Object[] {
				"GXG",
				"SFS",
				"OCO",
				'G', "sheetPlastic",
				'X', invarAxe,
				'S', Items.SHEARS,
				'F', machineFrame,
				'C', powerCoilGold,
				'O', prefix + "Tin"
		});

		registerMachine(Machine.BlockPlacer, new Object[] {
				"GDG",
				"DMD",
				"GSG",
				'G', "sheetPlastic",
				'D', Blocks.DISPENSER,
				'S', powerCoilGold,
				'M', machineFrame,
		});

		registerMachine(Machine.MobCounter, new Object[] {
				"GGG",
				"RCR",
				"SMS",
				'G', "sheetPlastic",
				'R', Items.REPEATER,
				'C', Items.COMPARATOR,
				'S', multimeter,
				'M', machineFrame,
		});

		registerMachine(Machine.SteamTurbine, new Object[] {
				"PTP",
				"SFS",
				"OCO",
				'P', "sheetPlastic",
				'T', dynamoSteam,
				'S', Blocks.PISTON,
				'F', machineFrame,
				'O', prefix + "Silver",
				'C', powerCoilSilver
		});

		registerMachine(Machine.ChunkLoader, new Object[] {
				"PEP",
				"TFT",
				"OCO",
				'P', "sheetPlastic",
				'T', tesseract,
				'E', cellResonant,
				'F', tesseractFrameEmpty,
				'O', prefix + "Electrum",
				'C', powerCoilElectrum
		});
		if (MFRConfig.enableCheapCL.getBoolean(false)) {
			registerMachine(Machine.ChunkLoader, new Object[] {
					"PEP",
					"TFT",
					"OCO",
					'P', "sheetPlastic",
					'T', tesseractFrameEmpty,
					'E', cellRedstone,
					'F', machineFrame,
					'O', prefix + "Electrum",
					'C', powerCoilElectrum
			});
		}

		registerMachine(Machine.Fountain, new Object[] {
				"PBP",
				"TFT",
				"OCO",
				'P', "sheetPlastic",
				'B', Blocks.IRON_BARS,
				'T', tankBasic,
				'F', machineFrame,
				'O', prefix + "Nickel",
				'C', powerCoilGold
		});

		registerMachine(Machine.MobRouter, new Object[] {
				"PPP",
				"BRB",
				"OCO",
				'P', "sheetPlastic",
				'B', Blocks.IRON_BARS,
				'R', Machine.ItemRouter.getItemStack(),
				'O', "dyeOrange",
				'C', Machine.Chronotyper.getItemStack(),
		});

		addRecipe(ShapedRecipe(stack(plasticTank, 1), new Object[] {
				"PPP",
				"P P",
				"PMP",
				'P', "sheetPlastic",
				'M', machineBaseItem,
		}));
	}

	@Override
	protected void registerSmelting() {

		super.registerSmelting();
		ThermalExpansionHelper.addSmelterRecipe(2000, stack(rawRubberItem, 2), sulfur,
			stack(rubberBarItem, 4), stack(rubberBarItem, 1), 45);
	}

	@Override
	protected void registerMiscItems() {

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fertilizerItem, 24), new Object[] {
				"WBW",
				"STS",
				"WBW",
				'W', Items.WHEAT,
				'B', new ItemStack(Items.DYE, 1, 15),
				'S', "dustSulfur",
				'T', "stickWood",
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(spyglassItem), new Object[] {
				"GLG",
				"PLP",
				" S ",
				'G', "ingotBronze",
				'L', "blockGlass",
				'P', "sheetPlastic",
				'S', "stickWood",
		}));

		if (MFRConfig.enablePortaSpawner.getBoolean(true))
			GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(portaSpawnerItem), new Object[] {
					"GLG",
					"DND",
					"GLG",
					'G', "ingotInvar",
					'L', "blockGlass",
					'D', "ingotEnderium",
					'N', Items.NETHER_STAR
			}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(detCordBlock, 12), new Object[] {
				"PPP",
				"PTP",
				"PPP",
				'P', "itemRubber",
				'T', Blocks.TNT,
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(fishingRodItem, 1), new Object[] {
				"DD ",
				"DFD",
				"TDD",
				'D', "wireExplosive",
				'F', Items.FISHING_ROD,
				'T', Blocks.REDSTONE_TORCH
		}));
	}

	@Override
	protected void registerRedNet() {

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rednetCableBlock, 8), new Object[] {
				"PPP",
				"RRR",
				"PPP",
				'R', "dustRedstone",
				'P', "sheetPlastic",
		}));

		{
			@Nonnull ItemStack pipe = stack(plasticPipeBlock);
			addRecipe(ShapelessRecipe(stack(rednetCableBlock, 5), pipe, pipe, pipe, pipe, pipe, "dustRedstone", "dustRedstone"));
		}

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(rednetCableBlock, 1, 2), new Object[] {
				"nuggetElectrum",
				"nuggetElectrum",
				"nuggetElectrum",
				"dustRedstone",
				"dustRedstone",
				new ItemStack(rednetCableBlock),
		}));

		GameRegistry.addRecipe(new ShapelessOreRecipe(new ItemStack(rednetCableBlock, 6, 2), new Object[] {
				"ingotElectrum",
				"ingotElectrum",
				Blocks.REDSTONE_BLOCK,
				new ItemStack(rednetCableBlock),
				new ItemStack(rednetCableBlock),
				new ItemStack(rednetCableBlock),
				new ItemStack(rednetCableBlock),
				new ItemStack(rednetCableBlock),
				new ItemStack(rednetCableBlock),
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(machineBlock, 1, 1), new Object[] {
				"PRP",
				"RGR",
				"PIP",
				'R', "dustRedstone",
				'P', "sheetPlastic",
				'G', "blockGlass",
				'I', "ingotIron",
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rednetLogicBlock), new Object[] {
				"RDR",
				"LGL",
				"PHP",
				'H', new ItemStack(machineBlock, 1, 1),
				'P', "sheetPlastic",
				'G', "ingotGold",
				'L', "gemLapis",
				'D', "gemDiamond",
				'R', "dustRedstone",
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(logicCardItem, 1, 0), new Object[] {
				"RPR",
				"PGP",
				"RPR",
				'P', "sheetPlastic",
				'G', "ingotGold",
				'R', "dustRedstone",
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(logicCardItem, 1, 1), new Object[] {
				"GPG",
				"PCP",
				"RGR",
				'C', new ItemStack(logicCardItem, 1, 0),
				'P', "sheetPlastic",
				'G', "ingotGold",
				'R', "dustRedstone",
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(logicCardItem, 1, 2), new Object[] {
				"DPD",
				"RCR",
				"GDG",
				'C', new ItemStack(logicCardItem, 1, 1),
				'P', "sheetPlastic",
				'G', "ingotGold",
				'D', "gemDiamond",
				'R', "dustRedstone",
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rednetMeterItem, 1, 0), new Object[] {
				" G",
				"PR",
				"PP",
				'P', "sheetPlastic",
				'G', "nuggetElectrum",
				'R', "dustRedstone",
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rednetMeterItem, 1, 1), new Object[] {
				"RGR",
				"IMI",
				"PPP",
				'P', "sheetPlastic",
				'G', powerCoilElectrum,
				'I', "ingotCopper",
				'R', "dustRedstone",
				'M', new ItemStack(rednetMeterItem, 1, 0)
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rednetMemoryCardItem, 1, 0), new Object[] {
				"GGG",
				"PRP",
				"PPP",
				'P', "sheetPlastic",
				'G', "nuggetGold",
				'R', "dustRedstone",
		}));

		GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(rednetPanelBlock, 1, 0), new Object[] {
				"PCP",
				"PBP",
				"KPK",
				'P', "sheetPlastic",
				'C', rednetCableBlock,
				'B', Blocks.BOOKSHELF,
				'K', new ItemStack(Items.DYE, 1, 0)
		}));

		GameRegistry.addShapelessRecipe(new ItemStack(rednetMemoryCardItem, 1, 0), new ItemStack(rednetMemoryCardItem, 1, 0));
	}

}
*/
