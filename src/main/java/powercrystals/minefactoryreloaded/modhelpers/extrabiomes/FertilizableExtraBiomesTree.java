/*
package powercrystals.minefactoryreloaded.modhelpers.extrabiomes;

import java.lang.reflect.Method;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableExtraBiomesTree implements IFactoryFertilizable
{
	private Method _fertilize;
	private Block _blockId;
	
	public FertilizableExtraBiomesTree(Block blockId, Method fertilize)
	{
		_blockId = blockId;
		_fertilize = fertilize;
	}
	
	@Override
	public Block getPlant()
	{
		return _blockId;
	}
	
	@Override
	public boolean canFertilize(World world, BlockPos pos, FertilizerType fertilizerType)
	{
		return fertilizerType == FertilizerType.GrowPlant;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, BlockPos pos, FertilizerType fertilizerType)
	{
		try
		{
			_fertilize.invoke(_blockId, world, x, y, z, rand);
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return world.getBlock(x, y, z) != _blockId;
	}
}
*/
