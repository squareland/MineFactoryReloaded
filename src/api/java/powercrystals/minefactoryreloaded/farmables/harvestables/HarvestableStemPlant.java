package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.Block;
import net.minecraft.util.math.BlockPos;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings;

public class HarvestableStemPlant extends HarvestableGourd {

	protected Block _fruit;

	public HarvestableStemPlant(Block block, Block fruit) {

		super(block, HarvestType.PlantStem);
		_fruit = fruit;
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, IFactorySettings s, BlockPos pos) {

		return world.getBlockState(pos).getBlock().equals(_fruit);
	}

}
