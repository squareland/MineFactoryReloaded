package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;

public class HarvestableGourd extends HarvestableStandard
{
	public HarvestableGourd(Block block, powercrystals.minefactoryreloaded.api.HarvestType harvestType)
	{
		super(block, harvestType);
	}

	public HarvestableGourd(Block block)
	{
		super(block);
	}

	@Override
	public void postHarvest(net.minecraft.world.World world, BlockPos pos)
	{
		Block ground = world.getBlockState(pos.down()).getBlock();
		if (world.isAirBlock(pos) &&
				(ground.equals(Blocks.DIRT) || ground.equals(Blocks.GRASS)))
		{
			world.setBlockState(pos.down(), Blocks.FARMLAND.getDefaultState());
		}
		super.postHarvest(world, pos);
	}
}
