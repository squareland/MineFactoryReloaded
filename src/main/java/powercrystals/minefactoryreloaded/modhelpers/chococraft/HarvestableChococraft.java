/*
package powercrystals.minefactoryreloaded.modhelpers.chococraft;

import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;

public class HarvestableChococraft implements IFactoryHarvestable
{
	
	private Block _block;
	
	public HarvestableChococraft(Block block)
	{
		_block = block;
	}
	
	@Override
	public Block getPlant()
	{
		return _block;
	}
	
	@Override
	public HarvestType getHarvestType()
	{
		return HarvestType.Normal;
	}
	
	@Override
	public boolean breakBlock()
	{
		return true;
	}
	
	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, BlockPos pos)
	{
		return world.getBlockMetadata(x, y, z) >= 4;
	}
	
	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, BlockPos pos)
	{
		return _block.getDrops(world, x, y, z, world.getBlockMetadata(x, y, z), 0);
	}
	
	@Override
	public void preHarvest(World world, BlockPos pos)
	{
		if (world.getBlockMetadata(x, y, z) > 4)
		{
			world.setBlockMetadataWithNotify(x, y, z, 4, 2);
		}
	}
	
	@Override
	public void postHarvest(World world, BlockPos pos)
	{
		world.notifyBlocksOfNeighborChange(x, y, z, _block);
	}
}
*/
