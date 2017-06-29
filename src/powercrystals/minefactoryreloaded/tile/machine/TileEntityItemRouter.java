package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;

import powercrystals.minefactoryreloaded.core.IEntityCollidable;
import powercrystals.minefactoryreloaded.core.UtilInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiItemRouter;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerItemRouter;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryInventory;

public class TileEntityItemRouter extends TileEntityFactoryInventory implements IEntityCollidable {

	private boolean _routing = false;

	private boolean _rejectUnmapped;

	protected static final int[] _invOffsets = new int[] { 0, 0, 9, 18, 36, 27 };
	protected static final EnumFacing[] _outputDirections = new EnumFacing[] { EnumFacing.DOWN, EnumFacing.NORTH, EnumFacing.SOUTH, EnumFacing.EAST,
			EnumFacing.WEST };

	private int[] _defaultRoutes = new int[_outputDirections.length];

	public TileEntityItemRouter() {

		this(Machine.ItemRouter);
	}

	public TileEntityItemRouter(Machine machine) {

		super(machine);
		setManageSolids(true);
	}

	public boolean getRejectUnmapped() {

		return _rejectUnmapped;
	}

	public void setRejectUnmapped(boolean rejectUnmapped) {

		_rejectUnmapped = rejectUnmapped;
	}

	@Override
	public void update() {

		super.update();
		if (!world.isRemote) {
			for (int i = 45; i < getSizeInventory(); i++) {
				if (_inventory[i] != null) {
					_inventory[i] = routeItem(_inventory[i]);
				}
			}
		}
	}

	@Override
	public void onEntityCollided(Entity entity) {

		if (entity instanceof EntityItem && !entity.isDead) {
			ItemStack s = routeItem(((EntityItem) entity).getEntityItem());
			if (s == null)
				entity.setDead();
			else
				((EntityItem) entity).setEntityItemStack(s);
		}
	}

	public ItemStack routeItem(ItemStack stack) {

		int[] filteredRoutes = getRoutesForItem(stack);

		_routing = true;
		if (hasRoutes(filteredRoutes)) {
			stack = weightedRouteItem(stack, filteredRoutes);
			stack = (stack == null || stack.getCount() <= 0) ? null : stack;
		}
		else if (!_rejectUnmapped && hasRoutes(_defaultRoutes)) {
			stack = weightedRouteItem(stack, _defaultRoutes);
			stack = (stack == null || stack.getCount() <= 0) ? null : stack;
		}
		_routing = false;
		return stack;
	}

	private ItemStack weightedRouteItem(ItemStack stack, int[] routes) {

		ItemStack remainingOverall = stack.copy();
		int weight = totalWeight(routes);
		if (stack.getCount() >= weight) {
			int startingAmount = stack.getCount();
			for (int i = 0; i < routes.length; i++) {
				ItemStack stackForThisRoute = stack.copy();
				stackForThisRoute.setCount(startingAmount * routes[i] / weight);
				if (stackForThisRoute.getCount() > 0) {
					ItemStack remainingFromThisRoute = UtilInventory.dropStack(this, stackForThisRoute, _outputDirections[i], _outputDirections[i]);
					if (remainingFromThisRoute == null) {
						remainingOverall.shrink(stackForThisRoute.getCount());
					}
					else {
						remainingOverall.shrink((stackForThisRoute.getCount() - remainingFromThisRoute.getCount()));
					}

					if (remainingOverall.getCount() <= 0) {
						break;
					}
				}
			}
		}

		if (0 < remainingOverall.getCount() && remainingOverall.getCount() < totalWeight(routes)) {
			int outdir = weightedRandomSide(routes);
			remainingOverall = UtilInventory.dropStack(this, remainingOverall, _outputDirections[outdir], _outputDirections[outdir]);
		}
		return remainingOverall;
	}

	private int weightedRandomSide(int[] routeWeights) {

		int random = world.rand.nextInt(totalWeight(routeWeights));
		for (int i = 0; i < routeWeights.length; i++) {
			random -= routeWeights[i];
			if (random < 0)
				return i;
		}

		return -1;
	}

	private int totalWeight(int[] routeWeights) {

		int total = 0;

		for (int weight : routeWeights)
			total += weight;
		return total;
	}

	private boolean hasRoutes(int[] routeWeights) {

		for (int weight : routeWeights)
			if (weight > 0) return true;

		return false;
	}

	protected int[] getRoutesForItem(ItemStack stack) {

		int[] routeWeights = new int[_outputDirections.length];

		Item item = stack.getItem();

		for (int i = 0; i < _outputDirections.length; i++) {
			int sideStart = _invOffsets[_outputDirections[i].ordinal()];
			routeWeights[i] = 0;
			for (int j = sideStart; j < sideStart + 9; j++) {
				if (_inventory[j] != null) {
					if (_inventory[j].getItem().equals(item) &&
							(stack.isItemStackDamageable() ||
							_inventory[j].getItemDamage() == stack.getItemDamage())) {
						routeWeights[i] += _inventory[j].getCount();
					}
				}
			}
		}
		return routeWeights;
	}

	private void recalculateDefaultRoutes() {

		for (int i = 0; i < _outputDirections.length; i++)
			_defaultRoutes[i] = isSideEmpty(_outputDirections[i]) ? 1 : 0;
	}

	public boolean hasRouteForItem(ItemStack stack) {

		return hasRoutes(getRoutesForItem(stack));
	}

	private boolean isSideEmpty(EnumFacing side) {

		if (side == null || side == EnumFacing.UP) {
			return false;
		}

		int sideStart = _invOffsets[side.ordinal()];

		for (int i = sideStart; i < sideStart + 9; i++) {
			if (_inventory[i] != null) {
				return false;
			}
		}

		return true;
	}

	@Override
	public int getSizeInventory() {

		return 48;
	}

	@Override
	public boolean shouldDropSlotWhenBroken(int slot) {

		return slot >= 45;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer) {

		return new GuiItemRouter(getContainer(inventoryPlayer), this);
	}

	@Override
	public ContainerFactoryInventory getContainer(InventoryPlayer inventoryPlayer) {

		return new ContainerItemRouter(this, inventoryPlayer);
	}

	@Override
	public int getInventoryStackLimit() {

		return 64;
	}

	@Override
	public int getStartInventorySide(EnumFacing side) {

		return 45;
	}

	@Override
	public int getSizeInventorySide(EnumFacing side) {

		return 3;
	}

	@Override
	public void setInventorySlotContents(int i, ItemStack stack) {

		if (world != null && !world.isRemote) {
			int start = getStartInventorySide(null);
			if (i >= start && i <= (start + getSizeInventorySide(null))) {
				l: if (stack != null) {
					if (stack.getCount() <= 0) {
						stack = null;
						break l;
					}
					stack = routeItem(stack);
					if (stack != null)
						if (stack.getCount() > getInventoryStackLimit()) {
							stack.setCount(getInventoryStackLimit());
						}
				}
				_inventory[i] = stack;
				return;
			}
		}
		super.setInventorySlotContents(i, stack);
	}

	@Override
	public boolean canInsertItem(int slot, ItemStack itemstack, EnumFacing side) {

		return !_routing;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack itemstack) {

		return !_routing;
	}

	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing side) {

		return false;
	}

	@Override
	protected void onFactoryInventoryChanged() {

		super.onFactoryInventoryChanged();
		recalculateDefaultRoutes();
	}

	@Override
	public void writePortableData(EntityPlayer player, NBTTagCompound tag) {

		tag.setBoolean("rejectUnmapped", _rejectUnmapped);
	}

	@Override
	public void readPortableData(EntityPlayer player, NBTTagCompound tag) {

		_rejectUnmapped = tag.getBoolean("rejectUnmapped");
		recalculateDefaultRoutes();
	}

	@Override
	public void writeItemNBT(NBTTagCompound tag) {

		super.writeItemNBT(tag);
		if (_rejectUnmapped)
			tag.setBoolean("rejectUnmapped", _rejectUnmapped);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		super.readFromNBT(tag);
		_rejectUnmapped = tag.getBoolean("rejectUnmapped");
		recalculateDefaultRoutes();
	}

}
