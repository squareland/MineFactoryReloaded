package powercrystals.minefactoryreloaded.gui;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.ITextComponent;

import javax.annotation.Nullable;

public class NeedlegunContainerWrapper implements IInventory
{
	private ItemStack _stack;

	public NeedlegunContainerWrapper(ItemStack stack)
	{
		_stack = stack;
		if (stack.getTagCompound() == null)
			stack.setTagCompound(new NBTTagCompound());
	}

	public ItemStack getStack()
	{
		return _stack;
	}

	@Override
	public int getSizeInventory()
	{
		return 1;
	}

	@Override
	public ItemStack getStackInSlot(int i)
	{
		if(_stack.getTagCompound().getCompoundTag("ammo") == null || _stack.getTagCompound().getCompoundTag("ammo").hasNoTags())
		{
			return ItemStack.EMPTY;
		}
		else
		{
			return new ItemStack(_stack.getTagCompound().getCompoundTag("ammo"));
		}
	}

	@Override
	public ItemStack decrStackSize(int i, int j)
	{
		if(_stack.getTagCompound().getCompoundTag("ammo") == null || _stack.getTagCompound().getCompoundTag("ammo").hasNoTags())
		{
			return ItemStack.EMPTY;
		}
		ItemStack s = new ItemStack(_stack.getTagCompound().getCompoundTag("ammo"));
		ItemStack r = s.splitStack(j);
		if(s.getCount() <= 0)
		{
			_stack.getTagCompound().setTag("ammo", new NBTTagCompound());
		}
		else
		{
			NBTTagCompound t = new NBTTagCompound();
			s.writeToNBT(t);
			_stack.getTagCompound().setTag("ammo", t);
		}
		return r;
	}

	@Nullable
	@Override
	public ItemStack removeStackFromSlot(int index)
	{
		return ItemStack.EMPTY;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack itemstack)
	{
		if(itemstack.isEmpty())
		{
			_stack.getTagCompound().setTag("ammo", new NBTTagCompound());
		}
		else
		{
			NBTTagCompound t = new NBTTagCompound();
			itemstack.writeToNBT(t);
			_stack.getTagCompound().setTag("ammo", t);
		}
	}

	@Override
	public String getName()
	{
		return "Needlegun";
	}

	@Override
	public boolean hasCustomName()
	{
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return null;
	}

	@Override
	public int getInventoryStackLimit()
	{
		return 1;
	}

	@Override
	public void markDirty()
	{
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer entityplayer)
	{
		return true;
	}

	@Override
	public void openInventory(EntityPlayer player)
	{
	}

	@Override
	public void closeInventory(EntityPlayer player)
	{
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack)
	{
		return true;
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {

	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public void clear() {
		_stack = ItemStack.EMPTY;
	}

	@Override
	public boolean isEmpty() {

		return _stack.isEmpty();
	}
}
