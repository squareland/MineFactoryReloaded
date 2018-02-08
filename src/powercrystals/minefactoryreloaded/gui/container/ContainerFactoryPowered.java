package powercrystals.minefactoryreloaded.gui.container;

import net.minecraft.entity.player.InventoryPlayer;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

/* packet values:
 * 0: current work
 * 1: current energy
 * 2: current idle
 * 3: current tank 
 * 4: tank id
 * 5: tank meta
 */

public class ContainerFactoryPowered extends ContainerFactoryInventory
{
	int energyTemp;
	int workTemp;
	
	public ContainerFactoryPowered(TileEntityFactoryPowered te, InventoryPlayer inv)
	{
		super(te, inv);
	}
	
	@Override
	public void detectAndSendChanges()
	{
		super.detectAndSendChanges();
		for(int i = 0; i < listeners.size(); i++)
		{
			listeners.get(i).sendWindowProperty(this, 0, ((TileEntityFactoryPowered)_te).getWorkDone() & 65535);
			listeners.get(i).sendWindowProperty(this, 1, ((TileEntityFactoryPowered)_te).getEnergyStored() & 65535);
			listeners.get(i).sendWindowProperty(this, 2, ((TileEntityFactoryPowered)_te).getIdleTicks());
			listeners.get(i).sendWindowProperty(this, 3, (((TileEntityFactoryPowered)_te).getEnergyStored() >> 16) & 65535);
			listeners.get(i).sendWindowProperty(this, 4, ((TileEntityFactoryPowered)_te).getWorkDone() >> 16);
		}
	}
	
	@Override
	public void updateProgressBar(int var, int value)
	{
		super.updateProgressBar(var, value);
		
		if(var == 0) workTemp = (value & 65535);
		else if(var == 1) energyTemp = (value & 65535);
		else if(var == 2) ((TileEntityFactoryPowered)_te).setIdleTicks(value);
		else if(var == 3) ((TileEntityFactoryPowered)_te).setEnergyStored(((value & 65535) << 16) | energyTemp);
		else if(var == 4) ((TileEntityFactoryPowered)_te).setWorkDone(((value & 65535) << 16) | workTemp);
	}
}
