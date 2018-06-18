package powercrystals.minefactoryreloaded.core;

import java.util.Map;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public interface IHarvestManager
{
	void moveNext();
	BlockPos getNextBlock();
	BlockPos getOrigin();
	void reset(World world, Area area, HarvestMode harvestMode, Map<String, Boolean> settings);
	void setWorld(World world);
	boolean getIsDone();
	void writeToNBT(NBTTagCompound tag);
	void readFromNBT(NBTTagCompound tag);
	void free();
}
