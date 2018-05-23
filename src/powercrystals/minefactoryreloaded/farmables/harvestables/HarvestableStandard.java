package powercrystals.minefactoryreloaded.farmables.harvestables;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableStandard implements IFactoryHarvestable
{
	private Block _block;
	private HarvestType _harvestType;

	public HarvestableStandard(Block block, HarvestType harvestType)
	{
		if (block == Blocks.AIR)
			throw new IllegalArgumentException("Passed air FactoryHarvestableStandard");

		_block = block;
		_harvestType = harvestType;
	}

	public HarvestableStandard(Block block)
	{
		this(block, HarvestType.Normal);
	}

	@Override
	public Block getPlant()
	{
		return _block;
	}

	@Override
	public HarvestType getHarvestType()
	{
		return _harvestType;
	}

	@Override
	public boolean breakBlock()
	{
		return true;
	}

	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, BlockPos pos)
	{
		return true;
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		return state.getBlock().getDrops(world, pos, state, 0);
	}

	@Override
	public void preHarvest(World world, BlockPos pos)
	{
	}

	@Override
	public void postHarvest(World world, BlockPos pos)
	{
		world.notifyNeighborsOfStateChange(pos, getPlant(), false);
	}
}
