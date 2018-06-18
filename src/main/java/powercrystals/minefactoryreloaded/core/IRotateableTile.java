package powercrystals.minefactoryreloaded.core;

import net.minecraft.util.EnumFacing;

public interface IRotateableTile {

	boolean canRotate();

	boolean canRotate(EnumFacing axis);

	void rotate(EnumFacing axis);

	void rotateDirectlyTo(int facing);

	EnumFacing getDirectionFacing();

}
