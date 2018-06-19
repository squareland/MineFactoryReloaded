package powercrystals.minefactoryreloaded.modhelpers.ic2;

import ic2.api.item.IC2Items;
import ic2.api.recipe.IRecipeInput;
import ic2.api.recipe.ISemiFluidFuelManager.BurnProperty;
import ic2.api.recipe.Recipes;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.oredict.OreDictionary;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.farmables.fertilizables.FertilizerStandard;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.farmables.plantables.PlantableSapling;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.List;

import static powercrystals.minefactoryreloaded.modhelpers.Compats.ModIds.INDUSTRIAL_CRAFT;

@IMFRIntegrator.DependsOn(INDUSTRIAL_CRAFT)
public class IndustrialCraft implements IMFRIntegrator {

	public void postLoad() {

		ItemArmor boots = net.minecraft.init.Items.LEATHER_BOOTS;
		@Nonnull
		ItemStack booties = new ItemStack(boots, 64, 0);
		boots.setColor(booties, 0x3479F2);
		OreDictionary.registerOre("greggy_greg_do_please_kindly_stuff_a_sock_in_it", booties);
	}

	public void load() {

		@Nonnull
		ItemStack crop = IC2Items.getItem("crop");
		@Nonnull
		ItemStack rubber = IC2Items.getItem("rubber").copy();
		@Nonnull
		ItemStack rubberSapling = IC2Items.getItem("rubberSapling");
		@Nonnull
		ItemStack rubberLeaves = IC2Items.getItem("rubberLeaves");
		@Nonnull
		ItemStack rubberWood = IC2Items.getItem("rubberWood");
		@Nonnull
		ItemStack stickyResin = IC2Items.getItem("resin");

		if (rubberSapling != null) {
			MFRRegistry.registerPlantable(new PlantableSapling(rubberSapling.getItem(), Block.getBlockFromItem(rubberSapling.getItem())));
			MFRRegistry.registerFertilizable(new FertilizableIC2RubberTree(Block.getBlockFromItem(rubberSapling.getItem())));
		}
		if (rubberLeaves != null) {
			MFRRegistry.registerHarvestable(new HarvestableTreeLeaves(Block.getBlockFromItem(rubberLeaves.getItem())));
		}
		if (rubberWood != null) {
			MFRRegistry.registerHarvestable(new HarvestableIC2RubberWood(Block.getBlockFromItem(rubberWood.getItem()), stickyResin.getItem()));
			MFRRegistry.registerFruitLogBlock(Block.getBlockFromItem(rubberWood.getItem()));
			FruitIC2Resin resin = new FruitIC2Resin(rubberWood, stickyResin);
			MFRRegistry.registerFruit(resin);
			MFRRegistry.registerFertilizable(resin);
		}

		@Nonnull
		ItemStack fertilizer = IC2Items.getItem("fertilizer");
		if (fertilizer != null) {
			MFRRegistry.registerFertilizer(new FertilizerStandard(fertilizer.getItem(), fertilizer.getItemDamage()));
		}

		if (crop != null) {
			IC2Crop ic2crop = new IC2Crop(Block.getBlockFromItem(crop.getItem()));
			MFRRegistry.registerHarvestable(ic2crop);
			MFRRegistry.registerFertilizable(ic2crop);
			MFRRegistry.registerFruit(ic2crop);
		}

		copyEthanol();

		@Nonnull
		ItemStack item = new ItemStack(MFRThings.rubberSaplingBlock);
		rubber.setCount(1);
		Recipes.extractor.addRecipe(
				new IRecipeInput() {

					@Override
					public boolean matches(@Nonnull ItemStack itemStack) {

						return itemStack.getItem() == Item.getItemFromBlock(MFRThings.rubberSaplingBlock);
					}

					@Override
					public int getAmount() {

						return 1;
					}

					@Override
					public List<ItemStack> getInputs() {

						return Collections.singletonList(item);
					}
				}, null, false, rubber);
	}

	private static void copyEthanol() {

		BurnProperty q = Recipes.semiFluidGenerator.getBurnProperty(FluidRegistry.getFluid("bioethanol"));
		if (q != null)
			Recipes.semiFluidGenerator.addFluid("biofuel", q.amount, q.power);
		else if (FluidRegistry.getFluid("bioethanol") == null)
			Recipes.semiFluidGenerator.addFluid("biofuel", 10, 16);
	}

}
