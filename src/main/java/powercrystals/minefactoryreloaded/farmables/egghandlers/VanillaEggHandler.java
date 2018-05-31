package powercrystals.minefactoryreloaded.farmables.egghandlers;

import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityList.EntityEggInfo;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.IMobEggHandler;

import javax.annotation.Nonnull;

public class VanillaEggHandler implements IMobEggHandler
{
	@Override
	public EntityEggInfo getEgg(@Nonnull ItemStack safariNet)
	{
		return EntityList.ENTITY_EGGS.get(safariNet.getTagCompound().getString("id"));
	}
}
