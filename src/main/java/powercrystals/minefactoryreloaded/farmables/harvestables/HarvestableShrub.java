package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.Block;
import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockTallGrass;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.HarvestType;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class HarvestableShrub extends HarvestableStandard {

	public HarvestableShrub(Block block) {

		super(block, HarvestType.Normal);
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, BlockPos pos) {

		NonNullList<ItemStack> drops = NonNullList.create();

		boolean doublePlant = getPlant() == Blocks.DOUBLE_PLANT, top = false;

		IBlockState state = world.getBlockState(pos);
		if (doublePlant && world.getBlockState(pos).getValue(BlockDoublePlant.HALF) == BlockDoublePlant.EnumBlockHalf.UPPER) {
			top = true;
			state = world.getBlockState(pos.down());
		}

		if (harvesterSettings.get("silkTouch") == Boolean.TRUE) {
			//TODO get back to this and try to replace meta magic numbers with something better
			int size = 1, oMeta = 1;
			if (getPlant() == Blocks.TALLGRASS) {
				BlockTallGrass.EnumType type = state.getValue(BlockTallGrass.TYPE);
				if (type == BlockTallGrass.EnumType.GRASS || type == BlockTallGrass.EnumType.FERN) {
					if (type == BlockTallGrass.EnumType.FERN) {
						oMeta = 1;
					}
					drops.add(new ItemStack(Blocks.TALLGRASS , size, oMeta));
				}
			} else if (doublePlant) {
				BlockDoublePlant.EnumPlantType variant = state.getValue(BlockDoublePlant.VARIANT);
				if (variant == BlockDoublePlant.EnumPlantType.GRASS || variant == BlockDoublePlant.EnumPlantType.FERN) {
					size = 2;
					if (variant == BlockDoublePlant.EnumPlantType.FERN) {
						oMeta = 2;
					}
					drops.add(new ItemStack(Blocks.TALLGRASS , size, oMeta));
				}
			}
		} else {
			drops.addAll(getPlant().getDrops(world, pos, state, 0));
		}

		if (doublePlant && top) {
			world.setBlockToAir(pos.down());
		}

		return drops;
	}

	@Override
	public void postHarvest(World world, BlockPos pos) {

		super.postHarvest(world, pos);
		if (getPlant() == Blocks.DOUBLE_PLANT) {
			super.postHarvest(world, pos.down());
		}
	}

}
