package powercrystals.minefactoryreloaded.item.syringe;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

import net.minecraft.util.EnumHand;
import powercrystals.minefactoryreloaded.api.ISyringe;
import powercrystals.minefactoryreloaded.item.base.ItemFactory;
import powercrystals.minefactoryreloaded.setup.MFRThings;

public abstract class ItemSyringe extends ItemFactory implements ISyringe
{
	public ItemSyringe()
	{
		setMaxStackSize(1);
	}

	@Override
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase entity, EnumHand hand)
	{
		if (!entity.world.isRemote && canInject(entity.world, entity, stack))
		{
			if (inject(entity.world, entity, stack))
			{
				stack.setItem(MFRThings.syringeEmptyItem);
				return true;
			}
		}

		return false;
	}

	@Override
	public boolean isEmpty(ItemStack syringe)
	{
		return false;
	}

	@Override
	public ItemStack getEmptySyringe(ItemStack syringe)
	{
		return new ItemStack(MFRThings.syringeEmptyItem);
	}
}
