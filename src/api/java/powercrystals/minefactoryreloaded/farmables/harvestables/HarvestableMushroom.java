package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.util.math.BlockPos;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;

public class HarvestableMushroom extends HarvestableStandard
{
	public HarvestableMushroom(net.minecraft.block.Block block)
	{
		super(block, HarvestType.Normal);
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, java.util.Map<String, Boolean> settings, BlockPos pos)
	{
		return settings.get("harvestSmallMushrooms") == Boolean.TRUE;
	}
}
