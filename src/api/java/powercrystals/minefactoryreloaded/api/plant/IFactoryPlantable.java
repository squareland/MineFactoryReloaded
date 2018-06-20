package powercrystals.minefactoryreloaded.api.plant;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nonnull;

/**
 * Defines a plantable object for use in the Planter.
 *
 * @author PowerCrystals
 */
public interface IFactoryPlantable {

	/**
	 * @return The item this plantable is managing.
	 */
	@Nonnull
	Item getSeed();

	/**
	 * Called to determine if this {@link ItemStack} can be planted.
	 *
	 * @param stack
	 * 		The stack being planted.
	 * @param forFermenting
	 * 		True if this stack will be converted to biofuel
	 *
	 * @return True if this plantable can be planted (useful for metadata
	 * items).
	 */
	boolean canBePlanted(@Nonnull ItemStack stack, boolean forFermenting);

	/**
	 * Called to get the {@link ReplacementBlock} to place at this location.
	 *
	 * @param world
	 * 		The {@link World} instance this block or item will be placed into.
	 * @param pos
	 * 		The position.
	 * @param stack
	 * 		The stack being planted.
	 *
	 * @return The block that will be placed into the world.
	 */
	@Nonnull
	IReplacementBlock getPlantedBlock(World world, BlockPos pos, @Nonnull ItemStack stack);

	/**
	 * Called to determine if the {@link ItemStack} can be planted at this location.
	 *
	 * @param world
	 * 		The world instance this plantable will be placed into.
	 * @param pos
	 * 		The position.
	 * @param stack
	 * 		The stack being planted.
	 *
	 * @return True if this plantable can be placed at the provided coordinates.
	 */
	boolean canBePlantedHere(World world, BlockPos pos, @Nonnull ItemStack stack);

}
