package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.util.EnumFacing;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.ItemStack;

import powercrystals.minefactoryreloaded.api.ISyringe;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryInventory;
import powercrystals.minefactoryreloaded.gui.client.GuiFactoryPowered;
import powercrystals.minefactoryreloaded.gui.container.ContainerFactoryPowered;
import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

public class TileEntityVet extends TileEntityFactoryPowered
{
	public TileEntityVet()
	{
		super(Machine.Vet);
		createEntityHAM(this);
		setManageSolids(true);
		setCanRotate(true);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public GuiFactoryInventory getGui(InventoryPlayer inventoryPlayer)
	{
		return new GuiFactoryPowered(getContainer(inventoryPlayer), this);
	}
	
	@Override
	public ContainerFactoryPowered getContainer(InventoryPlayer inventoryPlayer)
	{
		return new ContainerFactoryPowered(this, inventoryPlayer);
	}
	
	@Override
	public int getWorkMax()
	{
		return 1;
	}
	
	@Override
	public int getIdleTicksMax()
	{
		return 200;
	}
	
	@Override
	public boolean activateMachine()
	{
		List<?> entities = world.getEntitiesWithinAABB(EntityLivingBase.class, _areaManager.getHarvestArea().toAxisAlignedBB());
		for(Object o : entities)
		{
			if(!(o instanceof EntityLivingBase) || o instanceof EntityPlayer || o instanceof EntityMob)
			{
				continue;
			}
			EntityLivingBase e = (EntityLivingBase)o;
			
			for(int i = 0; i < getSizeInventory(); i++)
			{
				ItemStack s = getStackInSlot(i);
				if (s != null && s.getItem() instanceof ISyringe)
				{
					ISyringe syringe = (ISyringe)s.getItem();
					if (syringe.canInject(world, e, s))
					{
						if (syringe.inject(world, e, s))
						{
							setInventorySlotContents(i, syringe.getEmptySyringe(s));
							return true;
						}
					}
				}
			}
		}
		setIdleTicks(getIdleTicksMax());
		return false;
	}
	
	@Override
	public int getSizeInventory()
	{
		return 9;
	}
	
	@Override
	public boolean canInsertItem(int slot, ItemStack s, EnumFacing side)
	{
		if (s != null && s.getItem() instanceof ISyringe)
		{
			ISyringe syringe = (ISyringe)s.getItem();
			return !syringe.isEmpty(s);
		}
		return false;
	}
	
	@Override
	public boolean canExtractItem(int slot, ItemStack itemstack, EnumFacing side)
	{
		ItemStack s = getStackInSlot(slot);
		if (s != null && s.getItem() instanceof ISyringe)
		{
			ISyringe syringe = (ISyringe)s.getItem();
			return syringe.isEmpty(s);
		}
		return true;
	}
}
