package powercrystals.minefactoryreloaded.core;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHarvestManager
{
	public void moveNext();
	public BlockPos getNextBlock();
	public BlockPos getOrigin();
	public void reset(World world, Area area, HarvestMode harvestMode, Map<String, Boolean> settings);
	public void setWorld(World world);
	public boolean getIsDone();
	public void writeToNBT(NBTTagCompound tag);
	public void readFromNBT(NBTTagCompound tag);
	public void free();
}
