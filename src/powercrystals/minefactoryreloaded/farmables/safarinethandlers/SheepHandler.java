package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class SheepHandler implements ISafariNetHandler {

	@Override
	public Class<?> validFor() {

		return EntitySheep.class;
	}

	@Override
	public void addInformation(@Nonnull ItemStack safariNetStack, World world, List<String> infoList, ITooltipFlag tooltipFlag) {

		infoList.add("Wool: " + EnumDyeColor.byMetadata((safariNetStack.getTagCompound().getByte("Color") & 15)));
	}
}
