package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;

import net.minecraft.inventory.IContainerListener;
import powercrystals.minefactoryreloaded.gui.slot.SlotAcceptUpgrade;
import powercrystals.minefactoryreloaded.tile.machine.plants.TileEntityHarvester;

public class ContainerHarvester extends ContainerUpgradeable
{
	public ContainerHarvester(TileEntityHarvester te, InventoryPlayer inv)
	{
		super(te, inv);
	}

	@Override
	protected void addSlots()
	{
		addSlotToContainer(new SlotAcceptUpgrade(_te, 0, 152, 79));
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();

		for (IContainerListener listener : listeners) {
			listener.sendWindowProperty(this, 100, getSetting("silkTouch"));
			listener.sendWindowProperty(this, 101, getSetting("harvestSmallMushrooms"));
		}
	}

	@Override
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);

		if(var == 100) setSetting("silkTouch", value);
		if(var == 101) setSetting("harvestSmallMushrooms", value);
	}

	private int getSetting(String setting)
	{
		TileEntityHarvester h = (TileEntityHarvester)_te;
		if(h.getSettings().get(setting) == null)
		{
			return 0;
		}
		return h.getSettings().get(setting) ? 1 : 0;
	}

	private void setSetting(String setting, int value)
	{
		((TileEntityHarvester)_te).getSettings().put(setting, value == 0 ? false : true);
	}
}
