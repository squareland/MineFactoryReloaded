package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class EntityAgeableHandler implements ISafariNetHandler {

	@Override
	public Class<?> validFor() {

		return EntityAgeable.class;
	}

	@Override
	public void addInformation(@Nonnull ItemStack safariNetStack, World world, List<String> infoList, ITooltipFlag tooltipFlag) {

		if (safariNetStack.getTagCompound().getInteger("Age") < 0) {
			infoList.add("Baby");
		}
	}
}
