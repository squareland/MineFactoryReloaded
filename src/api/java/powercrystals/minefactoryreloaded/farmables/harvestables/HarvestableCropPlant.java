package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.BlockCrops;
import net.minecraft.util.math.BlockPos;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings;

public class HarvestableCropPlant extends HarvestableStandard {

	public HarvestableCropPlant(net.minecraft.block.BlockCrops block) {

		super(block, HarvestType.Normal);
	}

	protected BlockCrops getCrop() {

		return (BlockCrops) getPlant();
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, IFactorySettings settings, BlockPos pos) {

		return getCrop().isMaxAge(world.getBlockState(pos));
	}

}
