/*
package powercrystals.minefactoryreloaded.modhelpers.chococraft;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;

public class FertilizableChococraft implements IFactoryFertilizable
{
	private final Block _blockId;
	
	public FertilizableChococraft(Block blockId)
	{
		this._blockId = blockId;
	}
	
	@Override
	public Block getPlant()
	{
		return this._blockId;
	}
	
	@Override
	public boolean canFertilize(World world, BlockPos pos, FertilizerType fertilizerType)
	{
		if (fertilizerType != FertilizerType.GrowPlant)
			return false;

		IBlockState state = world.getBlockState(pos);
		return state.getBlock().getMetaFromState(state) <= 4;
	}
	
	@Override
	public boolean fertilize(World world, Random rand, BlockPos pos, FertilizerType fertilizerType)
	{
		IBlockState state = world.getBlockState(pos);
		world.setBlockState(pos, state.getBlock().getStateFromMeta(4));
		return true;
	}
}
*/
