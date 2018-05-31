package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.BlockCocoa;
import net.minecraft.util.math.BlockPos;

public class HarvestableCocoa extends HarvestableStandard
{
	public HarvestableCocoa(net.minecraft.block.Block blockId)
	{
		super(blockId, powercrystals.minefactoryreloaded.api.HarvestType.TreeFruit);
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, java.util.Map<String, Boolean> settings, BlockPos pos)
	{
		if (settings.get("isHarvestingTree") == Boolean.TRUE)
			return true;
		return world.getBlockState(pos).getValue(BlockCocoa.AGE) >= 2;
	}
}
