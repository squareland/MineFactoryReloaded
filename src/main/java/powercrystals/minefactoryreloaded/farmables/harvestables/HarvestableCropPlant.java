package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;

public class HarvestableCropPlant extends HarvestableStandard
{
	private int _targetMeta;

	public HarvestableCropPlant(net.minecraft.block.Block block, int targetMeta)
	{
		super(block, powercrystals.minefactoryreloaded.api.HarvestType.Normal);
		_targetMeta = targetMeta;
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, java.util.Map<String, Boolean> settings, BlockPos pos)
	{
		//TODO look into replacing this with just a simple BlockCrops.isMaxAge call
		IBlockState state = world.getBlockState(pos);
		return state.getBlock().getMetaFromState(state) >= _targetMeta;
	}
}
