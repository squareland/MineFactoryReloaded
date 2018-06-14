package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;

public class HarvestableTreeLeaves extends HarvestableShearable
{
	public HarvestableTreeLeaves(Block block)
	{
		super(block, HarvestType.TreeLeaf);
	}

	@Override
	public void postHarvest(World world, BlockPos pos)
	{
		Block id = getPlant();

		notifyBlock(world, pos.down(), id);
		notifyBlock(world, pos.west(), id);
		notifyBlock(world, pos.east(), id);
		notifyBlock(world, pos.north(), id);
		notifyBlock(world, pos.south(), id);
		notifyBlock(world, pos.up(), id);
	}

	protected void notifyBlock(World world, BlockPos pos, Block id)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (!block.isLeaves(state, world, pos))
			world.neighborChanged(pos, id, pos);
	}
}
