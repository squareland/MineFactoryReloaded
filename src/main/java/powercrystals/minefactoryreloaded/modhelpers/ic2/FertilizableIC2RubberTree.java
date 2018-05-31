package powercrystals.minefactoryreloaded.modhelpers.ic2;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockSapling;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableIC2RubberTree implements IFactoryFertilizable
{
	private Block _saplingId;
	
	public FertilizableIC2RubberTree(Block blockId)
	{
		_saplingId = blockId;
	}
	
	@Override
	public Block getPlant()
	{
		return _saplingId;
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
			((BlockSapling)_saplingId).grow(world, rand, pos, world.getBlockState(pos));
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
		return world.getBlockState(pos).getBlock() != _saplingId;
	}
	
}
