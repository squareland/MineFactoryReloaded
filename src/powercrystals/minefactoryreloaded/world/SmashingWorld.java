package powercrystals.minefactoryreloaded.world;

import java.util.List;

import cofh.asmhooks.world.WorldProxy;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.core.UtilInventory;

import javax.annotation.Nullable;

// Nigel says: This is a
public class SmashingWorld extends WorldProxy
{
	protected Block block;
	protected int meta;
	protected BlockPos pos = new BlockPos(0, 1, 0);
	public SmashingWorld(World world)
	{
		super(world);
	}

	@Override
	public boolean spawnEntity(Entity par1Entity)
	{
		return true;
	}

	@Override
	protected boolean isChunkLoaded(int x, int z, boolean allowEmpty)
	{
		return true;
	}

	@Override
	public boolean setBlockState(BlockPos pos, IBlockState newState, int flags)
	{
		return true;
	}

	@Override
	public boolean setBlockToAir(BlockPos pos)
	{
		return true;
	}

	@Override
	public boolean destroyBlock(BlockPos pos, boolean dropBlock)
	{
		return true;
	}

	@Override
	public IBlockState getBlockState(BlockPos pos)
	{
		return this.pos.equals(pos) ? block.getStateFromMeta(meta) : Blocks.AIR.getDefaultState();
	}

	@Nullable
	@Override
	public TileEntity getTileEntity(BlockPos pos)
	{
		return null;
	}

	public List<ItemStack> smashBlock(ItemStack input, Block block, int meta, int fortune)
	{
		List<ItemStack> drops = null;
		if (block != null)
		{
			this.block = block;
			this.meta = meta;

			drops = block.getDrops(this, pos, block.getStateFromMeta(meta), fortune);
			if (drops.size() == 1)
				if (UtilInventory.stacksEqual(drops.get(0), input, false))
					return null;
		}
		return drops;
	}
}
