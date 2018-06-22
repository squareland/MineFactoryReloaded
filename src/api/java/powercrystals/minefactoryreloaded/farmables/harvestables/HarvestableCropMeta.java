package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings;

public class HarvestableCropMeta extends HarvestableStandard {

	private final PropertyInteger _ageProperty;
	private final int _targetAge;

	public HarvestableCropMeta(net.minecraft.block.Block block, PropertyInteger prop, int age) {

		super(block, HarvestType.Normal);
		_ageProperty = prop;
		_targetAge = age;
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, IFactorySettings settings, BlockPos pos) {

		IBlockState state = world.getBlockState(pos).getActualState(world, pos);
		return state.getValue(_ageProperty) >= _targetAge;
	}

}
