package powercrystals.minefactoryreloaded.block.transport;

import cofh.lib.inventory.IInventoryManager;
import cofh.lib.inventory.InventoryManager;
import net.minecraft.util.math.BlockPos;

import java.util.Map.Entry;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraft.util.EnumFacing;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.UtilInventory;

public class BlockRailCargoDropoff extends BlockFactoryRail
{
	public BlockRailCargoDropoff()
	{
		super(true, false);
		setUnlocalizedName("mfr.rail.cargo.dropoff");
		setRegistryName(MineFactoryReloadedCore.modId, "rail_cargo_dropoff");
	}

	@Override
	public void onMinecartPass(World world, EntityMinecart entity, BlockPos pos)
	{
		if (world.isRemote || !(entity instanceof IInventory))
			return;

		IInventoryManager minecart = InventoryManager.create(entity, null);

		for (Entry<Integer, ItemStack> contents : minecart.getContents().entrySet())
		{
			if (contents.getValue() == null)
			{
				continue;
			}

			ItemStack stackToAdd = contents.getValue().copy();
			ItemStack remaining = UtilInventory.dropStack(world, pos, contents.getValue(), EnumFacing.values(), null);

			if (remaining != null)
			{
				stackToAdd.shrink(remaining.getCount());
			}

			minecart.removeItem(stackToAdd.getCount(), stackToAdd);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		registerRailModel(this, "cargo_dropoff");
	}
}
