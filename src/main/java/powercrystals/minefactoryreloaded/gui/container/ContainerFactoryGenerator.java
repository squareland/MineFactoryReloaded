package powercrystals.minefactoryreloaded.gui.container;

import cofh.core.gui.slot.SlotEnergy;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.entity.player.InventoryPlayer;

import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryGenerator;

public class ContainerFactoryGenerator extends ContainerFactoryInventory
{
	public ContainerFactoryGenerator(TileEntityFactoryGenerator tileentity, InventoryPlayer inv)
	{
		super(tileentity, inv);
	}

	@Override
	protected void addSlots()
	{
		addSlotToContainer(new SlotEnergy(_te, 0, 8, 15));
	}

	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).sendWindowProperty(this, 100, ((TileEntityFactoryGenerator)_te).getBuffer());
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		if(var == 100) ((TileEntityFactoryGenerator)_te).setBuffer(value);
	}
}
