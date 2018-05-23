package powercrystals.minefactoryreloaded.farmables.plantables;

import cofh.core.util.helpers.FluidHelper;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fluids.BlockFluidBase;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;

import javax.annotation.Nonnull;

public class PlantableSoil extends PlantableStandard {

	public PlantableSoil(Block block) {

		super(Item.getItemFromBlock(block), block);
	}

	public PlantableSoil(Item block, Block plantedBlock) {

		super(block, plantedBlock);
	}

	public PlantableSoil(Item block, Block plantedBlock, int validMeta) {

		super(block, plantedBlock, validMeta);
	}

	public PlantableSoil(Block block, int plantedMeta) {

		this(Item.getItemFromBlock(block), plantedMeta, block);
	}

	public PlantableSoil(Item block, int plantedMeta, Block plantedBlock) {

		super(block, plantedBlock, WILDCARD, new ReplacementBlock(plantedBlock).setMeta(plantedMeta));
	}

	@Override
	public boolean canBePlanted(@Nonnull ItemStack stack, boolean forFermenting) {

		return !forFermenting && super.canBePlanted(stack, forFermenting);
	}

	@Override
	public boolean canBePlantedHere(World world, BlockPos pos, @Nonnull ItemStack stack) {

		if (!world.isAirBlock(pos))
			if (FluidHelper.lookupFluidForBlock(world.getBlockState(pos).getBlock()) == FluidHelper.WATER_FLUID) {
				IBlockState stateUp = world.getBlockState(pos.up());
				if (FluidHelper.lookupFluidForBlock(stateUp.getBlock()) == FluidHelper.WATER_FLUID)
					return false;
				else
					return stateUp.getValue(BlockFluidBase.LEVEL) != 0;
			} else {
				IBlockState state = world.getBlockState(pos);
				Block block = state.getBlock();
				return !state.getMaterial().isLiquid() && block.isReplaceable(world, pos);
			}

		return true;
	}
}
