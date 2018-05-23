package powercrystals.minefactoryreloaded.api;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import javax.annotation.Nonnull;

/**
 * Defines a fertilizer item for use in the Fertilizer.
 *
 * @author PowerCrystals
 */
public interface IFactoryFertilizer {

	/**
	 * @return The ID of this fertilizer item.
	 */
	Item getFertilizer();

	/**
	 * @return The type of fertilizer this is.
	 */
	FertilizerType getFertilizerType(@Nonnull ItemStack stack);

	/**
	 * Called when a fertilization is successful. If you set the @Nonnull ItemStack size
	 * to 0, it will be deleted by the fertilizer.
	 *
	 * @param fertilizer
	 *            The @Nonnull ItemStack used to fertilize.
	 */
	void consume(@Nonnull ItemStack fertilizer);

}
