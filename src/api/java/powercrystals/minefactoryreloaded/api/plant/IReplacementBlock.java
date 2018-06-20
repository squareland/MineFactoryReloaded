package powercrystals.minefactoryreloaded.api.plant;

import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * TODO: docs
 */
@FunctionalInterface
public interface IReplacementBlock {

	/**
	 * Called to replace a block in the world.
	 *
	 * @param world
	 * 		The world object
	 * @param pos
	 * 		Block position
	 * @param stack
	 * 		The ItemStack being used to replace the block
	 *
	 * @return True if the block was set successfully
	 */
	boolean replaceBlock(World world, BlockPos pos, @Nonnull ItemStack stack);

	/**
	 * Return this for times when you do not need to do anything to the in-world block.
	 */
	IReplacementBlock NO_OP = (world, pos, stack) -> true;

}
