package powercrystals.minefactoryreloaded.modhelpers.ic2;

import cofh.asm.relauncher.Strippable;
import ic2.api.item.IC2Items;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.ISemiFluidFuelManager.BurnProperty;
import ic2.api.recipe.Recipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizerStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableSapling;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

/*@ChildMod(parent = MineFactoryReloadedCore.modId, mod = @Mod(modid = "MineFactoryReloaded|CompatIC2",
		name = "MFR Compat: IC2",
		version = MineFactoryReloadedCore.version,
		dependencies = "after:MineFactoryReloaded;after:IC2",
		customProperties = @CustomProperty(k = "cofhversion", v = "true")))*/
public class IC2 {

	@EventHandler
	public static void postLoad(FMLPostInitializationEvent evt) {

		ItemArmor boots = net.minecraft.init.Items.LEATHER_BOOTS;
		@Nonnull ItemStack booties = new ItemStack(boots, 64, 0);
		boots.setColor(booties, 0x3479F2);
		OreDictionary.registerOre("greggy_greg_do_please_kindly_stuff_a_sock_in_it", booties);
	}

	@EventHandler
	@Strippable("mod:IC2")
	public void load(FMLInitializationEvent evt) {

		try {
			@Nonnull ItemStack crop = IC2Items.getItem("crop");
			@Nonnull ItemStack rubber = IC2Items.getItem("rubber").copy();
			@Nonnull ItemStack rubberSapling = IC2Items.getItem("rubberSapling");
			@Nonnull ItemStack rubberLeaves = IC2Items.getItem("rubberLeaves");
			@Nonnull ItemStack rubberWood = IC2Items.getItem("rubberWood");
			@Nonnull ItemStack stickyResin = IC2Items.getItem("resin");
			@Nonnull ItemStack plantBall = IC2Items.getItem("plantBall");

			if (rubberSapling != null) {
				MFRRegistry.registerPlantable(new PlantableSapling(rubberSapling.getItem(),
						Block.getBlockFromItem(rubberSapling.getItem())));
				MFRRegistry.registerFertilizable(new FertilizableIC2RubberTree(Block.getBlockFromItem(rubberSapling.getItem())));
			}
			if (rubberLeaves != null) {
				MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(Block.getBlockFromItem(rubberLeaves.getItem())));
			}
			if (rubberWood != null) {
				MFRRegistry.registerHarvestable(new HarvestableIC2RubberWood(Block.getBlockFromItem(rubberWood.getItem()),
						stickyResin.getItem()));
				MFRRegistry.registerFruitLogBlock(Block.getBlockFromItem(rubberWood.getItem()));
				FruitIC2Resin resin = new FruitIC2Resin(rubberWood, stickyResin);
				MFRRegistry.registerFruit(resin);
				MFRRegistry.registerFertilizable(resin);
			}

			@Nonnull ItemStack fertilizer = IC2Items.getItem("fertilizer");
			if (fertilizer != null) {
				MFRRegistry.registerFertilizer(new FertilizerStandard(fertilizer.getItem(), fertilizer.getItemDamage()));
			}

			if (crop != null) {
				IC2Crop ic2crop = new IC2Crop(Block.getBlockFromItem(crop.getItem()));
				MFRRegistry.registerHarvestable(ic2crop);
				MFRRegistry.registerFertilizable(ic2crop);
				MFRRegistry.registerFruit(ic2crop);
			}

			GameRegistry.addShapedRecipe(plantBall, new Object[] {
					"LLL",
					"L L",
					"LLL",
					Character.valueOf('L'), new ItemStack(MFRThings.rubberLeavesBlock)
			});

			@Nonnull ItemStack item = new ItemStack(MFRThings.rubberSaplingBlock);
			rubber.setCount(1);
			try {
				Recipes.extractor.addRecipe(
						new IRecipeInput() {
							@Override public boolean matches(@Nonnull ItemStack itemStack) {
								return itemStack.getItem() == Item.getItemFromBlock(MFRThings.rubberSaplingBlock);
							}
							@Override public int getAmount() { return 1; }
							@Override public List<ItemStack> getInputs() { return Collections.singletonList(item); }
						}
					, null, false, rubber);
			} catch (Throwable $) {
				ModContainer This = FMLCommonHandler.instance().findContainerFor(this);
				LogManager.getLogger(This.getModId()).log(Level.ERROR, "There was a problem loading " + This.getName(), $);
			}
			copyEthanol();
		} catch (Throwable $) {
			ModContainer This = FMLCommonHandler.instance().findContainerFor(this);
			LogManager.getLogger(This.getModId()).log(Level.ERROR, "There was a problem loading " + This.getName(), $);
		}
	}

	private static void copyEthanol() {

		BurnProperty q = Recipes.semiFluidGenerator.getBurnProperty(FluidRegistry.getFluid("bioethanol"));
		if (q != null)
			Recipes.semiFluidGenerator.addFluid("biofuel", q.amount, q.power);
		else if (FluidRegistry.getFluid("bioethanol") == null)
			Recipes.semiFluidGenerator.addFluid("biofuel", 10, 16);
	}

}
