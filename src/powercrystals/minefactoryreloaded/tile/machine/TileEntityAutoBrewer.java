package powercrystals.minefactoryreloaded.tile.machine;

import cofh.core.fluid.FluidTankCore;
import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.PotionTypes;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.core.UtilInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiAutoBrewer;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerAutoBrewer;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntityAutoBrewer extends TileEntityFactoryPowered {

	protected boolean _inventoryDirty;

	public TileEntityAutoBrewer() {

		super(Machine.AutoBrewer);
		setManageSolids(true);
		_tanks[0].setLock(FluidRegistry.WATER);
	}

	private int getProcessSlot(int row) {

		return row * 5;
	}

	private int getTemplateSlot(int row) {

		return row * 5 + 1;
	}

	private int getResourceSlot(int row, int slot) {

		return row * 5 + slot + 2;
	}

	@Override
	protected FluidTankCore[] createTanks() {

		return new FluidTankCore[] { new FluidTankCore(BUCKET_VOLUME * 4) };
	}

	@Override
	public int getSizeInventory() {

		// 6 sets of: process, template, res, res, res
		// 30 is output, 31 is empty bottle input
		return 32;
	}

	@Override
	public ContainerFactoryPowered getContainer(InventoryPlayer inventoryPlayer) {

		return new ContainerAutoBrewer(this, inventoryPlayer);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer) {

		return new GuiAutoBrewer(getContainer(inventoryPlayer), this);
	}

	@Override
	protected boolean activateMachine() {

		boolean hasWorkToDo = false, didWork = false;
		boolean doingWork = getWorkDone() > 0;
		if (doingWork & !_inventoryDirty)
			hasWorkToDo = true;
		else {
			final int waterCost = MFRConfig.autobrewerFluidCost.getInt();
			for (int row = 0; row < 6; row++) {
				int processSlot = getProcessSlot(row), templateSlot = getTemplateSlot(row);
				if (!_inventory.get(31).isEmpty() && _inventory.get(processSlot).isEmpty() && !_inventory.get(templateSlot).isEmpty()) {
					if (row == 0 || _inventory.get(getTemplateSlot(row - 1)).isEmpty()) {
						@Nonnull ItemStack waterBottle = PotionUtils.addPotionToItemStack(new ItemStack(Items.POTIONITEM), PotionTypes.WATER);
						if (getPotionResult(waterBottle, _inventory.get(templateSlot)) != waterBottle)
							if (drain(waterCost, false, _tanks[0]) == waterCost) {
								drain(waterCost, true, _tanks[0]);
								_inventory.set(31, ItemHelper.consumeItem(_inventory.get(31)));
								_inventory.set(processSlot, waterBottle);
								didWork = true;
							}
					}
				}
				if (!_inventory.get(processSlot).isEmpty()) {
					if (_inventory.get(getProcessSlot(row + 1)).isEmpty() && canBrew(row))
						hasWorkToDo = true;
				}
			}
			_inventoryDirty = false;
		}

		if (!hasWorkToDo) {
			setWorkDone(0);
			setIdleTicks(getIdleTicksMax());
			return didWork;
		}

		if (getWorkDone() < getWorkMax()) {
			return incrementWorkDone();
		}

		setWorkDone(0);

		for (int row = 6; row-- > 0;) {
			@Nonnull ItemStack current = _inventory.get(getProcessSlot(row));
			@Nonnull ItemStack next = _inventory.get(getProcessSlot(row + 1));
			if (!next.isEmpty() && !current.isEmpty()) {
				continue;
				// no exiting early, we know there's a potion that can be moved/brewed
			}

			@Nonnull ItemStack ingredient = _inventory.get(getTemplateSlot(row));

			if (!current.isEmpty()) {
				if (ingredient.isEmpty()) {
					_inventory.set(getProcessSlot(row + 1), current);
					_inventory.set(getProcessSlot(row), ItemStack.EMPTY);
					continue;
				}
				for (int i = 0; i < 3; i++) {
					int slot = getResourceSlot(row, i);
					if (_inventory.get(slot).isEmpty() || !UtilInventory.stacksEqual(_inventory.get(slot), ingredient)) {
						continue;
					}

					@Nonnull ItemStack newPotion = this.getPotionResult(current, ingredient.copy());

					if (!newPotion.isEmpty() && current != newPotion) {
						_inventory.set(getProcessSlot(row + 1), newPotion);
					} else {
						_inventory.set(getProcessSlot(row + 1), current);
					}

					_inventory.set(getProcessSlot(row), ItemStack.EMPTY);

					if (current == newPotion)
						break;

					_inventory.get(slot).shrink(1);

					if (ingredient.getItem().hasContainerItem(_inventory.get(slot))) {
						@Nonnull ItemStack r = ingredient.getItem().getContainerItem(_inventory.get(slot));
						if (!r.isEmpty() && r.isItemStackDamageable() && r.getItemDamage() > r.getMaxDamage())
							r = ItemStack.EMPTY;
						_inventory.set(slot, r);
					}
					if (_inventory.get(slot).isEmpty())
						_inventory.set(slot, ItemStack.EMPTY);
					break;
				}
			}
		}
		return true;
	}

	private boolean canBrew(int row) {

		if (_inventory.get(getTemplateSlot(row)).isEmpty()) {
			return false;
		}

		@Nonnull ItemStack ingredient = _inventory.get(getTemplateSlot(row));

		if (!BrewingRecipeRegistry.isValidIngredient(ingredient)) {
			return false;
		}

		boolean hasIngredients = false;
		for (int i = 0; i < 3; i++) {
			if (UtilInventory.stacksEqual(ingredient, _inventory.get(getResourceSlot(row, i)))) {
				hasIngredients = true;
				break;
			}
		}
		if (!hasIngredients) {
			return false;
		}

		if (!_inventory.get(getProcessSlot(row)).isEmpty()) {
			@Nonnull ItemStack newPotion = this.getPotionResult(_inventory.get(getProcessSlot(row)), ingredient);

			if (!newPotion.isEmpty() && _inventory.get(getProcessSlot(row)) != newPotion) {
				return true; //existingPotion != newPotion;
				// push potions without effects that have been previously brewed on through
			}
		}

		return false;
	}

	@Nonnull
	private ItemStack getPotionResult(@Nonnull ItemStack existingPotion, @Nonnull ItemStack ingredient) {

		if (ingredient.isEmpty() || !BrewingRecipeRegistry.isValidIngredient(ingredient)) {
			return existingPotion;
		}
		return BrewingRecipeRegistry.getOutput(existingPotion, ingredient);
	}

	@Override
	public int getWorkMax() {

		return 160;
	}

	@Override
	public int getIdleTicksMax() {

		return 10;
	}

	@Override
	public boolean canInsertItem(int slot, @Nonnull ItemStack itemstack, EnumFacing side) {

		int row = slot / 5;
		int column = slot % 5;

		if (itemstack.isEmpty()) return false;
		if (slot == 31) return itemstack.getItem().equals(Items.GLASS_BOTTLE);
		if (row == 6) return false;
		if (column == 1) return false;
		if (column == 0) return !_inventory.get(getTemplateSlot(row)).isEmpty() &&
				BrewingRecipeRegistry.isValidInput(itemstack) &&
				(row == 0 || _inventory.get(getTemplateSlot(row - 1)).isEmpty());
		return ingredientsEqual(_inventory.get(getTemplateSlot(row)), itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, @Nonnull ItemStack itemstack, EnumFacing side) {

		int row = slot / 5;
		int column = slot % 5;

		if (row == 6) return slot != 31;
		if (column == 1) return false;
		if (column == 0) return _inventory.get(getTemplateSlot(row)).isEmpty();
		return !ingredientsEqual(_inventory.get(getTemplateSlot(row)), itemstack);
	}

	private boolean ingredientsEqual(@Nonnull ItemStack template, @Nonnull ItemStack ingredient) {

		if (template.isEmpty() || ingredient.isEmpty() || !BrewingRecipeRegistry.isValidIngredient(template)) {
			return false;
		}
		return PotionUtils.getEffectsFromStack(template).equals(PotionUtils.getEffectsFromStack(ingredient));
	}

	@Override
	public void setInventorySlotContents(int slot, @Nonnull ItemStack itemstack) {

		if (!itemstack.isEmpty() && !shouldDropSlotWhenBroken(slot))
			itemstack.setCount(1); // ghost item; stack size (0, 1) also used to reduce resource consumption
		super.setInventorySlotContents(slot, itemstack);
	}

	@Override
	protected void onFactoryInventoryChanged() {

		super.onFactoryInventoryChanged();
		_inventoryDirty = true;
	}

	@Override
	public boolean shouldDropSlotWhenBroken(int slot) {

		return slot % 5 != 1 || slot == 31;
	}

	@Override
	public void writePortableData(EntityPlayer player, NBTTagCompound tag) {

		// TODO: read/write template slots
	}

	@Override
	public void readPortableData(EntityPlayer player, NBTTagCompound tag) {

	}

	@Override
	public boolean allowBucketDrain(EnumFacing facing, @Nonnull ItemStack stack) {

		return !stack.getItem().equals(Items.GLASS_BOTTLE);
	}

	@Override
	public boolean allowBucketFill(EnumFacing facing, @Nonnull ItemStack stack) {

		return !stack.getItem().equals(Items.POTIONITEM);
	}

	@Override
	protected boolean canDrainTank(EnumFacing facing, int index) {

		return false;
	}

	@Nullable
	@Override
	public FluidStack drain(EnumFacing facing, FluidStack resource, boolean doDrain) {

		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(EnumFacing facing, int maxDrain, boolean doDrain) {

		return null;
	}

}
