package powercrystals.minefactoryreloaded.modhelpers.ic2;

import java.util.List;
import java.util.Map;
import java.util.Random;

import ic2.core.block.BlockRubWood;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableWood;

public class HarvestableIC2RubberWood extends HarvestableWood
{
	private Item _resin;
	
	public HarvestableIC2RubberWood(Block sourceId, Item resin)
	{
		super(sourceId);
		_resin = resin;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, BlockPos pos)
	{
		List<ItemStack> drops = super.getDrops(world, rand, harvesterSettings, pos);
		if(world.getBlockState(pos).getValue(BlockRubWood.stateProperty).wet)
		{
			drops.add(new ItemStack(_resin, 1, 0));
		}
		
		return drops;
	}
}
