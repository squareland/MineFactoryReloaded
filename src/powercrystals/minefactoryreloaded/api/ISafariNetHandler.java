package powercrystals.minefactoryreloaded.api;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import javax.annotation.Nonnull;
import java.util.List;

/**
 * Defines an object that can display information about a captured mob in a
 * Safari net.
 *
 * @author PowerCrystals
 */
public interface ISafariNetHandler {

	/**
	 * @return The class of mob that this handler applies to.
	 */
	public Class<?> validFor();

	/**
	 * Called to add information regarding a mob contained in a SafariNet.
	 *
	 * @param safariNetStack
	 *            The Safari Net that is requesting information.
	 * @param world
	 *            World reference.
	 * @param infoList
	 *            The current list of information strings. Add yours to this.
	 * @param tooltipFlag
	 *            Normal or Advanced tooltip.
	 */
	public void addInformation(@Nonnull ItemStack safariNetStack, World world, List<String> infoList, ITooltipFlag tooltipFlag);

}
