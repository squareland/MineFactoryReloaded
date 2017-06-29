package powercrystals.minefactoryreloaded.block;

import cofh.api.energy.IEnergyContainerItem;
import cofh.lib.util.helpers.StringHelper;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import powercrystals.minefactoryreloaded.setup.Machine;

public class ItemBlockFactoryMachine extends ItemBlockFactory implements IEnergyContainerItem {

	private int _machineBlockIndex;

	public ItemBlockFactoryMachine(net.minecraft.block.Block blockId) {

		super(blockId);
		setMaxDamage(0);
		setHasSubtypes(true);

		_machineBlockIndex = ((BlockFactoryMachine) blockId).getBlockIndex();
		int highestMeta = Machine.getHighestMetadata(_machineBlockIndex);
		String[] names = new String[highestMeta + 1];
		for (int i = 0; i <= highestMeta; i++) {
			names[i] = Machine.getMachineFromIndex(_machineBlockIndex, i).getInternalName();
		}
		setNames(names);
	}

	@Override
	public String getUnlocalizedName(ItemStack stack) {

		return _names[Math.min(stack.getItemDamage(), _names.length - 1)];
	}

	@SuppressWarnings("rawtypes")
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, EntityPlayer player, List info, boolean adv) {

		Machine machine = getMachine(stack);
		if (!machine.hasTooltip(stack))
			return;
		if (StringHelper.displayShiftForDetail && !StringHelper.isShiftKeyDown()) {
			info.add(StringHelper.shiftForDetails());
		} else {
			machine.addInformation(stack, player, info, adv);
		}
	}

	private Machine getMachine(ItemStack stack) {

		return Machine.getMachineFromIndex(_machineBlockIndex, stack.getItemDamage());
	}

	// TE methods

	private int getTransferRate(ItemStack container) {

		if (container.getCount() != 1)
			return 0;
		return getMachine(container).getActivationEnergy();
	}

	private void setEnergy(ItemStack container, int newEnergy) {

		NBTTagCompound tag = container.getTagCompound();
		if (tag == null) container.setTagCompound(tag = new NBTTagCompound());
		tag.setInteger("energyStored", newEnergy);
	}

	@Override
	public int receiveEnergy(ItemStack container, int maxReceive, boolean simulate) {

		maxReceive = Math.min(getTransferRate(container), maxReceive);
		if (maxReceive <= 0)
			return 0;
		int energy = getEnergyStored(container);
		int maxEnergy = getMaxEnergyStored(container);
		int newEnergy = Math.max(0, Math.min(maxEnergy, energy + maxReceive));
		int received = newEnergy - energy;
		if (!simulate & received > 0)
			setEnergy(container, newEnergy);
		return received;
	}

	@Override
	public int extractEnergy(ItemStack container, int maxExtract, boolean simulate) {

		maxExtract = Math.min(getTransferRate(container), maxExtract);
		if (maxExtract <= 0)
			return 0;
		int energy = getEnergyStored(container);
		int newEnergy = Math.max(0, energy - maxExtract);
		int removed = energy - newEnergy;
		if (!simulate & removed > 0)
			setEnergy(container, newEnergy);
		return removed;
	}

	@Override
	public int getEnergyStored(ItemStack container) {

		if (container.hasTagCompound())
			return container.getTagCompound().getInteger("energyStored");
		return 0;
	}

	@Override
	public int getMaxEnergyStored(ItemStack container) {

		return getMachine(container).getMaxEnergyStorage();
	}
}
