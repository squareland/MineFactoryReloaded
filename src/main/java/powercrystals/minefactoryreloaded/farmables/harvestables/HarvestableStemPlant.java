package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;

public class HarvestableStemPlant extends HarvestableGourd
{
	protected Block _fruit;

	public HarvestableStemPlant(Block block, Block fruit)
	{
		super(block, powercrystals.minefactoryreloaded.api.HarvestType.Gourd);
		_fruit = fruit;
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, java.util.Map<String, Boolean> s, BlockPos pos)
	{
		return world.getBlockState(pos).getBlock().equals(_fruit);
	}
}
