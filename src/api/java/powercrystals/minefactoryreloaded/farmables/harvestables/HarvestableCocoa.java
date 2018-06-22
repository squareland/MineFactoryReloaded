package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.BlockCocoa;
import net.minecraft.util.math.BlockPos;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings;

public class HarvestableCocoa extends HarvestableStandard {

	public HarvestableCocoa(net.minecraft.block.Block blockId) {

		super(blockId, HarvestType.TreeFruit);
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, IFactorySettings settings, BlockPos pos) {

		if (settings.getBoolean("isHarvestingTree"))
			return true;
		return world.getBlockState(pos).getValue(BlockCocoa.AGE) >= 2;
	}

}
