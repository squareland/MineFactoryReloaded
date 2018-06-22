package powercrystals.minefactoryreloaded.modcompat.ic2;

import ic2.core.block.BlockRubWood;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableWood;

import java.util.List;
import java.util.Random;

public class HarvestableIC2RubberWood extends HarvestableWood {

	private Item _resin;

	HarvestableIC2RubberWood(Block sourceId, Item resin) {

		super(sourceId);
		_resin = resin;
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, IFactorySettings harvesterSettings, BlockPos pos) {

		List<ItemStack> drops = super.getDrops(world, rand, harvesterSettings, pos);
		if (world.getBlockState(pos).getValue(BlockRubWood.stateProperty).wet) {
			drops.add(new ItemStack(_resin, 1, 0));
		}

		return drops;
	}

}
