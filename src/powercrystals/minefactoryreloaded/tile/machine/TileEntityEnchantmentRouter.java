package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Map;

import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import powercrystals.minefactoryreloaded.gui.client.GuiEnchantmentRouter;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.container.ContainerEnchantmentRouter;
import powercrystals.minefactoryreloaded.setup.Machine;

public class TileEntityEnchantmentRouter extends TileEntityItemRouter {

	protected boolean _matchLevels = false;

	public TileEntityEnchantmentRouter() {

		super(Machine.EnchantmentRouter);
	}

	@Override
	@SuppressWarnings("rawtypes")
	protected int[] getRoutesForItem(ItemStack stack) {

		int[] routeWeights = new int[_outputDirections.length];

		Map stackEnchants = EnchantmentHelper.getEnchantments(stack);
		// return false if the item is unenchanted
		if (stackEnchants == null || stackEnchants.isEmpty()) {
			for (int i = 0; i < routeWeights.length; i++) {
				routeWeights[i] = 0;
			}
			return routeWeights;
		}

		for (int i = 0; i < _outputDirections.length; i++) {
			int sideStart = _invOffsets[_outputDirections[i].ordinal()];
			routeWeights[i] = 0;

			for (int j = sideStart; j < sideStart + 9; j++) {
				if (_inventory[j] == null)
					continue;
				if (_inventory[j].hasTagCompound()) {
					Map inventoryEnchants = EnchantmentHelper.getEnchantments(_inventory[j]);
					if (inventoryEnchants.isEmpty()) {
						continue;
					}
					for (Object stackEnchantId : stackEnchants.keySet()) {
						if (inventoryEnchants.containsKey(stackEnchantId)) {
							if (!_matchLevels || inventoryEnchants.get(stackEnchantId).equals(stackEnchants.get(stackEnchantId))) {
								routeWeights[i] += _inventory[j].getCount();
							}
						}
					}
				} else if (_inventory[j].getItem().equals(Items.BOOK)) {
					routeWeights[i] += (1 + _inventory[j].getCount()) / 2;
				}
			}
		}
		return routeWeights;
	}

	public boolean getMatchLevels() {

		return _matchLevels;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer) {

		return new GuiEnchantmentRouter(getContainer(inventoryPlayer), this);
	}

	@Override
	public ContainerEnchantmentRouter getContainer(InventoryPlayer inventoryPlayer) {

		return new ContainerEnchantmentRouter(this, inventoryPlayer);
	}

	public void setMatchLevels(boolean newMatchLevelsSetting) {

		_matchLevels = newMatchLevelsSetting;
	}

	@Override
	public void writePortableData(EntityPlayer player, NBTTagCompound tag) {

		super.writePortableData(player, tag);
		tag.setBoolean("matchLevels", _matchLevels);
	}

	@Override
	public void readPortableData(EntityPlayer player, NBTTagCompound tag) {

		super.readPortableData(player, tag);
		_matchLevels = tag.getBoolean("matchLevels");
	}

	@Override
	public void writeItemNBT(NBTTagCompound tag) {

		super.writeItemNBT(tag);
		if (_matchLevels)
			tag.setBoolean("matchLevels", _matchLevels);
	}

	@Override
	public void readFromNBT(NBTTagCompound tag) {

		super.readFromNBT(tag);
		_matchLevels = tag.getBoolean("matchLevels");
	}
}
