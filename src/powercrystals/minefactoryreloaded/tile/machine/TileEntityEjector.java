package powercrystals.minefactoryreloaded.tile.machine;

import buildcraft.api.transport.IPipeTile.PipeType;

import cofh.asm.relauncher.Strippable;
import cofh.core.util.CoreUtils;
import cofh.lib.inventory.IInventoryManager;
import cofh.lib.inventory.InventoryManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.LinkedList;
import java.util.Map;
import java.util.Map.Entry;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.UtilInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiEjector;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerEjector;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class TileEntityEjector extends TileEntityFactoryInventory {

	protected boolean _lastRedstoneState;
	protected boolean _whitelist = false;
	protected boolean _matchNBT = true;
	protected boolean _ignoreDamage = true;

	protected boolean _hasItems = false;
	protected EnumFacing[] _pullDirections = {};

	public TileEntityEjector() {

		super(Machine.Ejector);
		setManageSolids(true);
		setCanRotate(true);
	}

	@Override
	protected void onRotate() {

		LinkedList<EnumFacing> list = new LinkedList<EnumFacing>();
		list.addAll(MFRUtil.VALID_DIRECTIONS);
		list.remove(getDirectionFacing());
		_pullDirections = list.toArray(new EnumFacing[5]);
		super.onRotate();
	}

	@Override
	public void update() {

		super.update();
		if (world.isRemote) {
			return;
		}
		boolean redstoneState = _rednetState != 0 || CoreUtils.isRedstonePowered(this);

		if (redstoneState & !_lastRedstoneState & (!_whitelist | (_whitelist == _hasItems))) {
			final EnumFacing facing = getDirectionFacing();
			Map<EnumFacing, IInventory> chests = UtilInventory.
					findChests(world, pos, _pullDirections);
			inv:
			for (Entry<EnumFacing, IInventory> chest : chests.entrySet()) {
				if (chest.getKey() == facing) {
					continue;
				}

				IInventoryManager inventory = InventoryManager.create(chest.getValue(),
						chest.getKey().getOpposite());
				Map<Integer, ItemStack> contents = inventory.getContents();

				set:
				for (Entry<Integer, ItemStack> stack : contents.entrySet()) {
					ItemStack itemstack = stack.getValue();
					if (itemstack == null || itemstack.stackSize < 1 || !inventory.canRemoveItem(itemstack, stack.getKey()))
						continue;

					boolean hasMatch = false;

					int amt = 1;
					for (int i = getSizeItemList(); i-- > 0; )
						if (itemMatches(_inventory[i], itemstack)) {
							hasMatch = true;
							amt = Math.max(1, _inventory[i].stackSize);
							break;
						}

					if (_whitelist != hasMatch)
						continue set;

					ItemStack stackToDrop = itemstack.copy();
					amt = Math.min(itemstack.stackSize, amt);
					stackToDrop.stackSize = amt;
					ItemStack remaining = UtilInventory.dropStack(this, stackToDrop,
							facing, facing);

					// remaining == null if dropped successfully.
					if (remaining == null || remaining.stackSize < amt) {
						inventory.removeItem(amt - (remaining == null ? 0 : remaining.stackSize), stackToDrop);
						break inv;
					}
				}
			}
		}
		_lastRedstoneState = redstoneState;
	}

	protected boolean itemMatches(ItemStack itemA, ItemStack itemB) {

		if (itemA == null | itemB == null)
			return false;

		if (!itemA.getItem().equals(itemB.getItem()))
			return false;

		if (!_ignoreDamage)
			if (!itemA.isItemEqual(itemB))
				return false;

		if (_matchNBT) {
			if (itemA.getTagCompound() == null && itemB.getTagCompound() == null)
				return true;
			if (itemA.getTagCompound() == null || itemB.getTagCompound() == null)
				return false;
			return itemA.getTagCompound().equals(itemB.getTagCompound());
		}

		return true;
	}

	@Override
	protected void onFactoryInventoryChanged() {

		super.onFactoryInventoryChanged();
		for (int i = getSizeItemList(); i-- > 0; )
			if (_inventory[i] != null) {
				_hasItems = true;
				return;
			}
	}

	public int getSizeItemList() {

		return 9;
	}

	@Override
	public int getSizeInventory() {

		return getSizeItemList();
	}

	@Override
	public boolean shouldDropSlotWhenBroken(int slot) {

		return false;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing side) {

		return false;
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing side) {

		return false;
	}

	@Override
	public boolean isItemValidForSlot(int i, ItemStack itemstack) {

		return false;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer) {

		return new GuiEjector(getContainer(inventoryPlayer), this);
	}

	@Override
	public ContainerFactoryInventory getContainer(InventoryPlayer inventoryPlayer) {

		return new ContainerEjector(this, inventoryPlayer);
	}

	@Override
	public void writePortableData(EntityPlayer player, NBTTagCompound tag) {

		tag.setBoolean("whitelist", _whitelist);
		tag.setBoolean("matchNBT", _matchNBT);
		tag.setBoolean("ignoreDamage", _ignoreDamage);
	}

	@Override
	public void readPortableData(EntityPlayer player, NBTTagCompound tag) {

		_whitelist = tag.getBoolean("whitelist");
		_matchNBT = !tag.hasKey("matchNBT") || tag.getBoolean("matchNBT");
		_ignoreDamage = tag.getBoolean("ignoreDamage");
	}

	@Override
	public void writeItemNBT(NBTTagCompound tag) {

		super.writeItemNBT(tag);
		if (_whitelist)
			tag.setBoolean("whitelist", _whitelist);
		if (!_matchNBT)
			tag.setBoolean("matchNBT", _matchNBT);
		if (!_ignoreDamage)
			tag.setBoolean("ignoreDamage", _ignoreDamage);
		// TODO: write items
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {

		tag = super.writeToNBT(tag);
		tag.setBoolean("redstone", _lastRedstoneState);

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		super.readFromNBT(tag);
		_lastRedstoneState = tag.getBoolean("redstone");
		_whitelist = tag.getBoolean("whitelist");
		_matchNBT = !tag.hasKey("matchNBT") || tag.getBoolean("matchNBT");
		_ignoreDamage = !tag.hasKey("ignoreDamage") || tag.getBoolean("ignoreDamage");
	}

	public boolean getIsWhitelist() {

		return _whitelist;
	}

	public boolean getIsNBTMatch() {

		return _matchNBT;
	}

	public boolean getIsIDMatch() {

		return _ignoreDamage;
	}

	public void setIsWhitelist(boolean whitelist) {

		_whitelist = whitelist;
	}

	public void setIsNBTMatch(boolean matchNBT) {

		_matchNBT = matchNBT;
	}

	public void setIsIDMatch(boolean idMatch) {

		_ignoreDamage = idMatch;
	}

	@Override
	public ConnectionType canConnectInventory(EnumFacing from) {

		return from == getDirectionFacing() ? ConnectionType.FORCE : ConnectionType.DENY;
	}

	@Override
	@Strippable("buildcraft.api.transport.IPipeConnection")
	public ConnectOverride overridePipeConnection(PipeType type, EnumFacing with) {

		if (type == PipeType.STRUCTURE)
			return ConnectOverride.CONNECT;
		if (with == getDirectionFacing())
			return super.overridePipeConnection(type, with);
		return ConnectOverride.DISCONNECT;
	}

}
