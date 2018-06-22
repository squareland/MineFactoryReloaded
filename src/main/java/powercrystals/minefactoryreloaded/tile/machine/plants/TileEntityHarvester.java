package powercrystals.minefactoryreloaded.tile.machine.plants;

import cofh.core.fluid.FluidTankCore;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.plant.HarvestType;
import powercrystals.minefactoryreloaded.api.plant.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings;
import powercrystals.minefactoryreloaded.api.util.IFactorySettings.SettingNames;
import powercrystals.minefactoryreloaded.core.Area;
import powercrystals.minefactoryreloaded.core.HarvestMode;
import powercrystals.minefactoryreloaded.core.IHarvestManager;
import powercrystals.minefactoryreloaded.core.TreeHarvestManager;
import powercrystals.minefactoryreloaded.core.settings.BooleanSetting;
import powercrystals.minefactoryreloaded.core.settings.FactorySettings;
import powercrystals.minefactoryreloaded.core.settings.ISetting;
import powercrystals.minefactoryreloaded.core.settings.Vec3Setting;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiHarvester;
import powercrystals.minefactoryreloaded.gui.container.ContainerHarvester;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

import javax.annotation.Nonnull;
import java.util.*;

public class TileEntityHarvester extends TileEntityFactoryPowered {

	private static boolean skip = false;
	private static Map<String, ISetting> DEFAULT_SETTINGS;
	static {

		HashMap<String, ISetting> _settings = new HashMap<>();
		_settings.put(SettingNames.SHEARS_MODE, BooleanSetting.FALSE);
		_settings.put(SettingNames.HARVEST_SMALL_MUSHROOMS, BooleanSetting.FALSE);
		_settings.put(SettingNames.PLAY_SOUNDS, (BooleanSetting) MFRConfig.playSounds::getBoolean);
		DEFAULT_SETTINGS = java.util.Collections.unmodifiableMap(_settings);
	}

	private Map<String, ISetting> _settings;
	private IFactorySettings _immutableSettings;

	private Random _rand;

	private IHarvestManager _treeManager;
	private BlockPos _lastTree;

	public TileEntityHarvester() {

		super(Machine.Harvester);
		createHAM(this, 1);
		setManageSolids(true);

		_settings = new HashMap<>();
		_settings.putAll(DEFAULT_SETTINGS);
		_settings.put(SettingNames.HARVESTING_TREE,  (BooleanSetting) () -> !_treeManager.getIsDone());
		_immutableSettings = new FactorySettings(_settings);

		_rand = new Random();
		setCanRotate(true);

		skip = MFRConfig.harvesterSkip.getBoolean(false);
	}

	@Override
	public void onChunkUnload() {

		super.onChunkUnload();
		if (_treeManager != null)
			_treeManager.free();
		_lastTree = null;
	}

	@Override
	public void validate() {

		super.validate();
		if (!world.isRemote) {
			createHAM(this, 1);
			onFactoryInventoryChanged();
			if (_treeManager != null && _areaManager.getHarvestArea().contains(_treeManager.getOrigin())) {
				_treeManager.setWorld(world);
			} else {
				_treeManager = new TreeHarvestManager(world,
						new Area(pos, 0, 0, 0),
						HarvestMode.FruitTree, _immutableSettings);
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer) {

		return new GuiHarvester(getContainer(inventoryPlayer), this);
	}

	@Override
	public ContainerHarvester getContainer(InventoryPlayer inventoryPlayer) {

		return new ContainerHarvester(this, inventoryPlayer);
	}

	public Map<String, ISetting> getSettings() {

		return _settings;
	}

	public IFactorySettings getImmutableSettings() {

		return _immutableSettings;
	}

	@Override
	protected boolean shouldPumpLiquid() {

		return true;
	}

	@Override
	public int getWorkMax() {

		return 1;
	}

	@Override
	public int getIdleTicksMax() {

		return 5 + getExtraIdleTime(10);
	}

	protected int getExtraIdleTime(int additionalDelay) {

		return (_tanks[0].getFluidAmount() * additionalDelay / _tanks[0].getCapacity());
	}

	@Override
	public boolean activateMachine() {

		BlockPos target = getNextHarvest();

		if (target == null) {
			setIdleTicks(getIdleTicksMax());
			return false;
		}

		IBlockState harvestState = world.getBlockState(target);
		Block harvestedBlock = harvestState.getBlock();

		IFactoryHarvestable harvestable = MFRRegistry.getHarvestables().get(harvestedBlock);

		List<ItemStack> drops = harvestable.getDrops(world, _rand, _immutableSettings, target);

		harvestable.preHarvest(world, target);

		if (drops instanceof ArrayList) {
			ForgeEventFactory.fireBlockHarvesting(drops, world, target, harvestState, 0,
				1f, _immutableSettings.getBoolean(SettingNames.SHEARS_MODE), null);
		}

		if (harvestable.breakBlock()) {
			if (!world.setBlockState(target, Blocks.AIR.getDefaultState(), 2))
				return false;
			if (_immutableSettings.getBoolean(SettingNames.PLAY_SOUNDS)) {
				world.playEvent(null, 2001, target, Block.getStateId(harvestState));
			}
		}

		setIdleTicks(getExtraIdleTime(10));

		doDrop(drops);
		_tanks[0].fill(FluidRegistry.getFluidStack("sludge", 10), true);

		harvestable.postHarvest(world, target);

		return true;
	}

	private BlockPos getNextHarvest() {

		// eating a tree
		if (!_treeManager.getIsDone())
			return getNextTreeSegment(_lastTree);

		// increment the counter for the manager
		BlockPos bp = _areaManager.getNextBlock();
		// skip blocks if configured
		if (skip) {
			int extra = getExtraIdleTime(10);
			if (extra > 0 && extra > _rand.nextInt(15))
				return null;
		}
		if (!world.isBlockLoaded(bp)) {
			return null;
		}

		Block search = world.getBlockState(bp).getBlock();

		IFactoryHarvestable harvestable = MFRRegistry.getHarvestables().get(search);

		if (harvestable == null) {
			return null;
		}

		_settings.put(SettingNames.START_POSITION, Vec3Setting.of(bp));

		HarvestType type = harvestable.getHarvestType();
		if (type == HarvestType.PlantStem || harvestable.canBeHarvested(world, _immutableSettings, bp)) {
			switch (type) {
			case PlantStem:
				return getNextAdjacent(bp, harvestable);
			case Column:
			case LeaveBottom:
				return getNextVertical(bp, type == HarvestType.Column ? 0 : 1, harvestable);
			case Tree:
			case TreeLeaf:
				return getNextTreeSegment(bp);
			case TreeFruit:
			case Normal:
				return bp;
			}
		}
		return null;
	}

	private BlockPos getNextAdjacent(BlockPos pos, IFactoryHarvestable harvestable) {

		for (EnumFacing side : EnumFacing.HORIZONTALS) {
			BlockPos offsetPos = pos.offset(side);
			if (world.isBlockLoaded(offsetPos) && harvestable.canBeHarvested(world, _immutableSettings, offsetPos))
				return offsetPos;
		}
		return null;
	}

	private BlockPos getNextVertical(BlockPos pos, int startOffset, IFactoryHarvestable harvestable) {

		int highestBlockOffset = -1;
		int maxBlockOffset = MFRConfig.verticalHarvestSearchMaxVertical.getInt();

		Block plant = harvestable.getPlant();
		for (int currentYOffset = startOffset; currentYOffset < maxBlockOffset; ++currentYOffset) {
			BlockPos offsetPos = pos.offset(EnumFacing.UP, currentYOffset);
			Block block = world.getBlockState(offsetPos).getBlock();
			if (!block.equals(plant) ||
					!harvestable.canBeHarvested(world, _immutableSettings, offsetPos))
				break;

			highestBlockOffset = currentYOffset;
		}

		if (highestBlockOffset >= 0)
			return pos.offset(EnumFacing.UP, highestBlockOffset);

		return null;
	}

	private BlockPos getNextTreeSegment(BlockPos pos) {

		Block block;

		if (!pos.equals(_lastTree) || _treeManager.getIsDone()) {

			_lastTree = new BlockPos(pos);
			Area a = new Area(_lastTree, MFRConfig.treeSearchMaxHorizontal.getInt(), _lastTree.getY(), world.getHeight() - _lastTree.getY());

			_treeManager.reset(world, a, HarvestMode.HarvestTree, _immutableSettings);
		}

		Map<Block, IFactoryHarvestable> harvestables = MFRRegistry.getHarvestables();
		while (!_treeManager.getIsDone()) {
			BlockPos bp = _treeManager.getNextBlock();
			_treeManager.moveNext();
			if (!world.isBlockLoaded(bp)) {
				return null;
			}
			block = world.getBlockState(bp).getBlock();

			if (harvestables.containsKey(block)) {
				IFactoryHarvestable obj = harvestables.get(block);
				HarvestType t = obj.getHarvestType();
				if (t == HarvestType.Tree | t == HarvestType.TreeLeaf | t == HarvestType.TreeFruit)
					if (obj.canBeHarvested(world, _immutableSettings, bp))
						return bp;
			}
		}
		_lastTree = null;
		return null;
	}

	@Override
	protected FluidTankCore[] createTanks() {

		return new FluidTankCore[] { new FluidTankCore(4 * BUCKET_VOLUME) };
	}

	@Override
	public void writePortableData(EntityPlayer player, NBTTagCompound tag) {

		NBTTagCompound list = new NBTTagCompound();
		DEFAULT_SETTINGS.forEach((key, value) -> {
			if (!SettingNames.PLAY_SOUNDS.equals(key))
				list.setBoolean(key, _immutableSettings.getBoolean(key));
		});
		tag.setTag("harvesterSettings", list);
	}

	@Override
	public void readPortableData(EntityPlayer player, NBTTagCompound tag) {

		NBTTagCompound list = (NBTTagCompound) tag.getTag("harvesterSettings");
		DEFAULT_SETTINGS.forEach((key, value) -> {
			if (!SettingNames.PLAY_SOUNDS.equals(key) && list.hasKey(key, NBT.TAG_BYTE)) {
				boolean b = list.getBoolean(key);
				_settings.put(key, b ? BooleanSetting.TRUE : BooleanSetting.FALSE);
			}
		});
	}

	@Override
	public void writeItemNBT(NBTTagCompound tag) {

		super.writeItemNBT(tag);
		NBTTagCompound list = new NBTTagCompound();
		DEFAULT_SETTINGS.forEach((key, value) -> {
			if (!SettingNames.PLAY_SOUNDS.equals(key))
				list.setBoolean(key, _immutableSettings.getBoolean(key));
		});
		if (!list.hasNoTags())
			tag.setTag("harvesterSettings", list);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound tag) {

		tag = super.writeToNBT(tag);

		_treeManager.writeToNBT(tag);
		tag.setInteger("bpos", _areaManager.getPosition());

		return tag;
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		super.readFromNBT(tag);
		NBTTagCompound list = (NBTTagCompound) tag.getTag("harvesterSettings");
		DEFAULT_SETTINGS.forEach((key, value) -> {
			if (!SettingNames.PLAY_SOUNDS.equals(key)) {
				boolean b = list.getBoolean(key);
				_settings.put(key, b ? BooleanSetting.TRUE : BooleanSetting.FALSE);
			}
		});
		if (_treeManager != null)
			_treeManager.free();
		_treeManager = new TreeHarvestManager(tag, _immutableSettings);
		if (!_treeManager.getIsDone())
			_lastTree = _treeManager.getOrigin();
		_areaManager.getHarvestArea();
		_areaManager.setPosition(tag.getInteger("bpos"));
	}

	@Override
	public int getSizeInventory() {

		return 1;
	}

	@Override
	public int getStartInventorySide(EnumFacing side) {

		return 0;
	}

	@Override
	public int getSizeInventorySide(EnumFacing side) {

		return 0;
	}

	@Override
	public int getUpgradeSlot() {

		return 0;
	}

	@Override
	public boolean canInsertItem(int slot, @Nonnull ItemStack itemstack, EnumFacing side) {

		return slot == 0 && isUsableAugment(itemstack);
	}

	@Override
	public boolean canExtractItem(int slot, @Nonnull ItemStack itemstack, EnumFacing side) {

		return false;
	}

	@Override
	public boolean allowBucketDrain(EnumFacing facing, @Nonnull ItemStack stack) {

		return true;
	}

	@Override
	protected boolean canFillTank(EnumFacing facing, int index) {

		return false;
	}

	@Override
	public int fill(EnumFacing facing, FluidStack resource, boolean doFill) {

		return 0;
	}

}
