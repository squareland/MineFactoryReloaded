package powercrystals.minefactoryreloaded.block.transport;

import cofh.lib.inventory.IInventoryManager;
import cofh.lib.inventory.InventoryManager;
import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.UtilInventory;

import javax.annotation.Nonnull;
import java.util.Map.Entry;

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
				if (contents.getValue().isEmpty() || !chest.canRemoveItem(contents.getValue(), contents.getKey()))
				{
					continue;
				}
				@Nonnull ItemStack stackToAdd = contents.getValue().copy();

				@Nonnull ItemStack remaining = minecart.addItem(stackToAdd);

				if (!remaining.isEmpty())
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
