package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import net.minecraft.entity.passive.EntitySheep;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

import javax.annotation.Nonnull;
import java.util.List;

public class SheepHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntitySheep.class;
	}
	
	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	public void addInformation(@Nonnull ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		infoList.add("Wool: " + EnumDyeColor.byMetadata((safariNetStack.getTagCompound().getByte("Color") & 15)));
	}
}
