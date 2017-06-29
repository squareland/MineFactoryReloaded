package powercrystals.minefactoryreloaded.block.transport;

import cofh.lib.inventory.IInventoryManager;
import cofh.lib.inventory.InventoryManager;

import java.util.Map.Entry;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.UtilInventory;

public class BlockRailCargoPickup extends BlockFactoryRail
{
	public BlockRailCargoPickup()
	{
		super(true, false);
		setUnlocalizedName("mfr.rail.cargo.pickup");
		setRegistryName(MineFactoryReloadedCore.modId, "rail_cargo_pickup");
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart entity, BlockPos pos)
	{
		if (world.isRemote || !(entity instanceof IInventory))
			return;

		IInventoryManager minecart = InventoryManager.create(entity, null);

		for (Entry<EnumFacing, IInventory> inventory : UtilInventory.findChests(world, pos).entrySet())
		{
			IInventoryManager chest = InventoryManager.create(inventory.getValue(), inventory.getKey().getOpposite()); 
			for (Entry<Integer, ItemStack> contents : chest.getContents().entrySet())
			{
				if (contents.getValue() == null || !chest.canRemoveItem(contents.getValue(), contents.getKey()))
				{
					continue;
				}
				ItemStack stackToAdd = contents.getValue().copy();

				ItemStack remaining = minecart.addItem(stackToAdd);

				if (remaining != null)
				{
					stackToAdd.shrink(remaining.getCount());
					if (stackToAdd.getCount() > 0)
					{
						chest.removeItem(stackToAdd.getCount(), stackToAdd);
					}
				}
				else
				{
					chest.removeItem(stackToAdd.getCount(), stackToAdd);
					break;
				}
			}
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		registerRailModel(this, "cargo_pickup");
	}
}
