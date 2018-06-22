package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.util.math.BlockPos;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings.SettingNames;

public class HarvestableMushroom extends HarvestableStandard {

	public HarvestableMushroom(net.minecraft.block.Block block) {

		super(block, HarvestType.Normal);
	}

	@Override
	public boolean canBeHarvested(net.minecraft.world.World world, IFactorySettings settings, BlockPos pos) {

		return settings.getBoolean(SettingNames.HARVEST_SMALL_MUSHROOMS);
	}

}
