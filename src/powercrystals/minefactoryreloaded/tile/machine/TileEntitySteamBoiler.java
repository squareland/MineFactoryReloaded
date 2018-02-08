package powercrystals.minefactoryreloaded.tile.machine;

import cofh.core.fluid.FluidTankCore;
import cofh.core.util.CoreUtils;
import cofh.core.util.helpers.ItemHelper;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidTank;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.core.UtilInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiSteamBoiler;
import powercrystals.minefactoryreloaded.gui.container.ContainerSteamBoiler;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.MFRFluids;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryTickable;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class TileEntitySteamBoiler extends TileEntityFactoryTickable {

	public static final int maxTemp = 730;

	public static final int getItemBurnTime(@Nonnull ItemStack stack) {
		// TODO: special-case some items (e.g., TE's dynamo)
		return TileEntityFurnace.getItemBurnTime(stack) / 2;
	}

	private final Fluid _liquid;
	private int _ticksUntilConsumption = 0;
	private int _ticksSinceLastConsumption = 0;
	private int _totalBurningTime;
	private float _temp;

	public TileEntitySteamBoiler() {

		super(Machine.SteamBoiler);
		setManageSolids(true);
		_liquid = MFRFluids.getFluid("steam");
		_tanks[0].setLock(_liquid);
		_tanks[1].setLock(MFRFluids.getFluid("water"));
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer) {

		return new GuiSteamBoiler(getContainer(inventoryPlayer), this);
	}

	@Override
	public ContainerSteamBoiler getContainer(InventoryPlayer inventoryPlayer) {

		return new ContainerSteamBoiler(this, inventoryPlayer);
	}

	public float getTemp() {

		return _temp;
	}

	public int getWorkMax() {

		return _ticksUntilConsumption;
	}

	public int getWorkDone() {

		return _ticksSinceLastConsumption;
	}

	@SideOnly(Side.CLIENT)
	public int getFuelConsumptionPerTick() {

		return 1 + (Math.abs(Math.max(_totalBurningTime, -180)) + 1063) / 1064;
	}

	@SideOnly(Side.CLIENT)
	public void setTemp(int temp) {

		_temp = (temp / 10f);
	}

	@SideOnly(Side.CLIENT)
	public void setWorkDone(int a) {

		_ticksSinceLastConsumption = a;
	}

	@SideOnly(Side.CLIENT)
	public void setWorkMax(int a) {

		_ticksUntilConsumption = a;
	}

	@Override
	public void update() {

		super.update();
		if (!world.isRemote) {
			boolean active = _ticksSinceLastConsumption < _ticksUntilConsumption;
			setIsActive(active);

			if (_ticksUntilConsumption > 0) {
				int inc = 1 + (Math.abs(_totalBurningTime) + 1063) / 1064;
				_ticksSinceLastConsumption = Math.min(_ticksSinceLastConsumption + inc, _ticksUntilConsumption);
			}
			boolean skipConsumption = _ticksSinceLastConsumption < _ticksUntilConsumption;

			if (active)
				_totalBurningTime = Math.max(Math.min(_totalBurningTime + 1, 10649), -180);
			else if (_temp != 0) {
				_totalBurningTime = Math.max(_totalBurningTime - 16, -(10649 * 2));
				_ticksUntilConsumption = 0;
			}

			if (_temp == 0 && _inventory.get(3).isEmpty()) {
				if ((world.getTotalWorldTime() & 0x6F) == 0 && !(_rednetState != 0 || CoreUtils.isRedstonePowered(this)))
					mergeFuel();
				return; // we're not burning anything and not changing the temp
			}

			if (_temp == maxTemp ? _totalBurningTime < 0 : (_totalBurningTime > 0 ? true : _temp != 0)) {
				float diff = (float) Math.sqrt(Math.abs(_totalBurningTime)) / 103f;
				diff = Math.copySign(diff, _totalBurningTime) / 1.26f;

				_temp = Math.max(Math.min(_temp + (diff * diff * diff) / 50f, maxTemp), 0);
			}

			if (_temp > 80) {
				int toDrain = Math.min(_tanks[0].getSpace(), 100);

				if (toDrain > 0 && drain(toDrain, false, _tanks[1]) > 0) {
					int waterDrained = drain(toDrain, true, _tanks[1]);
					_tanks[0].fill(new FluidStack(_liquid, waterDrained * 4), true);
				} else {
					return;
				}
			}

			if (skipConsumption || CoreUtils.isRedstonePowered(this))
				return;

			if (consumeFuel())
				_ticksSinceLastConsumption = 0;

			mergeFuel();
		}
	}

	protected void mergeFuel() {

		if (!_inventory.get(3).isEmpty())
			for (int i = 0; _inventory.get(3).getCount() < _inventory.get(3).getMaxStackSize() && i < 3; ++i) {
				UtilInventory.mergeStacks(_inventory.get(3), _inventory.get(i));
				if (!_inventory.get(i).isEmpty() && _inventory.get(i).getCount() == 0)
					_inventory.set(i, ItemStack.EMPTY);
			}
		else
			for (int i = 0; i < 3; ++i)
				if (!_inventory.get(i).isEmpty()) {
					_inventory.set(3, _inventory.get(i));
					_inventory.set(i, ItemStack.EMPTY);
					break;
				}
	}

	protected boolean consumeFuel() {

		if (_inventory.get(3).isEmpty())
			return false;

		int burnTime = getItemBurnTime(_inventory.get(3));
		if (burnTime <= 0)
			return false;

		_ticksUntilConsumption = burnTime;
		_inventory.set(3, ItemHelper.consumeItem(_inventory.get(3)));
		notifyNeighborTileChange();

		return true;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {

		tag = super.writeToNBT(tag);

		tag.setInteger("ticksSinceLastConsumption", _ticksSinceLastConsumption);
		tag.setInteger("ticksUntilConsumption", _ticksUntilConsumption);
		tag.setInteger("buffer", _totalBurningTime);
		tag.setFloat("temp", _temp);

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		super.readFromNBT(tag);

		_ticksSinceLastConsumption = tag.getInteger("ticksSinceLastConsumption");
		_ticksUntilConsumption = tag.getInteger("ticksUntilConsumption");
		_totalBurningTime = tag.getInteger("buffer");
		_temp = tag.getFloat("temp");
	}

	//{ Solids
	@Override
	public int getSizeInventory() {

		return 4;
	}

	@Override
	public boolean canInsertItem(int slot, @Nonnull ItemStack stack, EnumFacing side) {

		if (!stack.isEmpty())
			return getItemBurnTime(stack) > 0;

		return false;
	}

	@Override
	public boolean canExtractItem(int slot, @Nonnull ItemStack itemstack, EnumFacing side) {

		return getItemBurnTime(_inventory.get(slot)) <= 0;
	}
	//}

	//{ Fluids
	@Override
	protected boolean shouldPumpLiquid() {

		return true;
	}

	@Override
	protected boolean shouldPumpTank(IFluidTank tank) {

		return tank == _tanks[0];
	}

	@Override
	protected FluidTankCore[] createTanks() {

		return new FluidTankCore[] { new FluidTankCore(BUCKET_VOLUME * 32),
				new FluidTankCore(BUCKET_VOLUME * 16) };
	}

	@Nullable
	@Override
	public FluidStack drain(EnumFacing facing, FluidStack resource, boolean doDrain) {

		if (resource != null) {
			FluidTankCore _tank = _tanks[0];
			if (resource.isFluidEqual(_tank.getFluid()))
				return _tank.drain(resource.amount, doDrain);
		}
		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(EnumFacing facing, int maxDrain, boolean doDrain) {

		FluidTankCore _tank = _tanks[0];
		if (_tank.getFluidAmount() > 0)
			return _tank.drain(maxDrain, doDrain);
		return null;
	}

	@Override
	public int fill(EnumFacing facing, FluidStack resource, boolean doFill) {

		if (resource != null && resource.getFluid() == FluidRegistry.WATER) {
			if (MFRConfig.steamBoilerExplodes.getBoolean(false)) {
				if (_temp > 80 && _tanks[1].getFluidAmount() == 0) {
					world.createExplosion(null, pos.getX() + 0.5d, pos.getY() + 0.5d, pos.getZ() + 0.5d, 3, true);
				}
			}
			return _tanks[1].fill(resource, doFill);
		}
		return 0;
	}

	@Override
	protected boolean canFillTank(EnumFacing facing, int index) {

		return index == 1;
	}

	@Override
	protected boolean canDrainTank(EnumFacing facing, int index) {

		return index == 0;
	}

	@Override
	public boolean allowBucketFill(EnumFacing facing, @Nonnull ItemStack stack) {

		return true;
	}

	@Override
	public boolean allowBucketDrain(EnumFacing facing, @Nonnull ItemStack stack) {

		return true;
	}
	//}

}
