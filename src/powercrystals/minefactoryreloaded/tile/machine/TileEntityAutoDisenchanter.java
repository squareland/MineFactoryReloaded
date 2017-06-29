package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumFacing;

import powercrystals.minefactoryreloaded.gui.client.GuiAutoDisenchanter;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoDisenchanter;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntityAutoDisenchanter extends TileEntityFactoryPowered {

	private boolean _repeatDisenchant;

	public TileEntityAutoDisenchanter() {

		super(Machine.AutoDisenchanter);
		setManageSolids(true);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer) {

		return new GuiAutoDisenchanter(getContainer(inventoryPlayer), this);
	}

	@Override
	public ContainerAutoDisenchanter getContainer(InventoryPlayer inventoryPlayer) {

		return new ContainerAutoDisenchanter(this, inventoryPlayer);
	}

	public boolean getRepeatDisenchant() {

		return _repeatDisenchant;
	}

	public void setRepeatDisenchant(boolean repeatDisenchant) {

		_repeatDisenchant = repeatDisenchant;
	}

	@Override
	public int getSizeInventory() {

		return 5;
	}

	@Override
	public int getSizeInventorySide(EnumFacing side) {

		return 4;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack stack, EnumFacing side) {

		if (stack == null)
			return false;
		if (slot == 0) {
			return stack.getEnchantmentTagList() != null || stack.getItem().equals(Items.ENCHANTED_BOOK);
		} else if (slot == 1) {
			return stack.getItem().equals(Items.BOOK);
		}
		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing side) {

		if (slot == 2 || slot == 3) return true;
		return false;
	}

	@Override
	protected boolean activateMachine() {

		if (_inventory[4] == null) {
			if (_inventory[0] == null)
				return false;
			_inventory[4] = _inventory[0].splitStack(1);
			if (_inventory[0].getCount() <= 0)
				_inventory[0] = null;
			markChunkDirty();
		}

		ItemStack stack = _inventory[4];
		boolean isBook = stack.getItem().equals(Items.ENCHANTED_BOOK);
		NBTTagList list = isBook ? Items.ENCHANTED_BOOK.getEnchantments(stack) : stack.getEnchantmentTagList();
		if ((list == null || list.tagCount() <= 0) && _inventory[2] == null) {
			_inventory[2] = stack;
			setInventorySlotContents(4, null);
		} else if ((list != null && list.tagCount() > 0) &&
				(_inventory[1] != null && _inventory[1].getItem().equals(Items.BOOK)) &
				_inventory[2] == null &
				_inventory[3] == null) {
			if (getWorkDone() >= getWorkMax()) {
				decrStackSize(1, 1);

				NBTTagCompound enchTag;
				if (isBook) {
					enchTag = list.getCompoundTagAt(0);
					list.removeTag(0);
					if (list.tagCount() == 0) {
						_inventory[4] = new ItemStack(Items.BOOK, 1);
					}
				} else {
					int enchIndex = world.rand.nextInt(list.tagCount());
					enchTag = list.getCompoundTagAt(enchIndex);

					list.removeTag(enchIndex);
					if (list.tagCount() == 0) {
						stack.getTagCompound().removeTag("ench");
						if (stack.getTagCompound().hasNoTags()) {
							stack.setTagCompound(null);
						}
					}

					if (stack.isItemStackDamageable()) {
						int damage = world.rand.nextInt(1 + (stack.getMaxDamage() / 4));
						int m = stack.getMaxDamage();
						damage = Math.min(m, damage + 1 + (m / 10)) + (m == 1 ? 1 : 0);
						if (stack.attemptDamageItem(damage, world.rand)) {
							_inventory[4] = null;
						}
					}
				}

				if (!_repeatDisenchant || (_inventory[4] != null && _inventory[4].getEnchantmentTagList() == null)) {
					_inventory[2] = _inventory[4];
					_inventory[4] = null;
				}

				setInventorySlotContents(3, new ItemStack(Items.ENCHANTED_BOOK, 1));

				NBTTagCompound baseTag = new NBTTagCompound();
				NBTTagList enchList = new NBTTagList();
				enchList.appendTag(enchTag);
				baseTag.setTag("StoredEnchantments", enchList);
				_inventory[3].setTagCompound(baseTag);

				setWorkDone(0);
			} else {
				markChunkDirty();
				if (!incrementWorkDone())
					return false;
			}

			return true;
		}
		return false;
	}

	@Override
	public int getWorkMax() {

		return 600;
	}

	@Override
	public int getIdleTicksMax() {

		return 1;
	}

	@Override
	public void writePortableData(EntityPlayer player, NBTTagCompound tag) {

		tag.setBoolean("repeatDisenchant", _repeatDisenchant);
	}

	@Override
	public void readPortableData(EntityPlayer player, NBTTagCompound tag) {

		_repeatDisenchant = tag.getBoolean("repeatDisenchant");
	}

	@Override
	public void writeItemNBT(NBTTagCompound tag) {

		super.writeItemNBT(tag);
		if (_repeatDisenchant)
			tag.setBoolean("repeatDisenchant", _repeatDisenchant);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		super.readFromNBT(tag);
		_repeatDisenchant = tag.getBoolean("repeatDisenchant");
	}

}
