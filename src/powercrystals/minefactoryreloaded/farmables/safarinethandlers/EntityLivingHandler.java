package powercrystals.minefactoryreloaded.farmables.safarinethandlers;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;

import javax.annotation.Nonnull;
import java.util.List;

import static net.minecraft.util.text.TextFormatting.DARK_GRAY;
import static net.minecraft.util.text.TextFormatting.ITALIC;

public class EntityLivingHandler implements ISafariNetHandler
{
	@Override
	public Class<?> validFor()
	{
		return EntityLiving.class;
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void addInformation(@Nonnull ItemStack safariNetStack, EntityPlayer player, List infoList, boolean advancedTooltips)
	{
		NBTTagCompound tag = safariNetStack.getTagCompound();
		if (tag.hasKey("CustomName"))
		{
			String name = tag.getString("CustomName");
			if (name != null && !name.isEmpty())
			{
				infoList.add("Name: " + name);
			}
		}
		if (advancedTooltips && tag.getBoolean("PersistenceRequired"))
			infoList.add(DARK_GRAY + (ITALIC + "Persistant"));
	}
}
