package powercrystals.minefactoryreloaded.gui.container;

import cofh.lib.gui.slot.SlotAcceptInsertable;
import cofh.lib.gui.slot.SlotPotion;
import cofh.lib.gui.slot.SlotPotionIngredient;
import cofh.lib.gui.slot.SlotRemoveOnly;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionUtils;
import powercrystals.minefactoryreloaded.gui.slot.SlotFake;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;

import javax.annotation.Nonnull;

public class ContainerAutoBrewer extends ContainerFactoryPowered {

	private class SlotBoundPotionIngredient extends SlotPotionIngredient {

		private final int slotIndex;

		public SlotBoundPotionIngredient(IInventory inventory, int index, int x, int y) {

			super(inventory, index, x, y);
			slotIndex = index / 5 * 5 + 1;
		}

		@Override
		public boolean isItemValid(@Nonnull ItemStack stack) {

			if (super.isItemValid(stack)) {
				@Nonnull ItemStack slot = getSlot(slotIndex).getStack();
				return slot.isEmpty() || PotionUtils.getEffectsFromStack(stack).equals(PotionUtils.getEffectsFromStack(slot));
			}
			return false;
		}
	}

	public static String ingredient;
	public static String bottle;

	public ContainerAutoBrewer(TileEntityFactoryPowered te, InventoryPlayer inv) {

		super(te, inv);
	}

	@Override
	protected void addSlots() {

		final int y = 24;
		for (int row = 0; row < 6; row++) {
			addSlotToContainer(new SlotPotion(_te, row * 5, 8, y + row * 18));
			addSlotToContainer(new SlotFake(_te, row * 5 + 1, 44, y + row * 18));
			addSlotToContainer(new SlotBoundPotionIngredient(_te, row * 5 + 2, 80, y + row * 18));
			addSlotToContainer(new SlotBoundPotionIngredient(_te, row * 5 + 3, 98, y + row * 18));
			addSlotToContainer(new SlotBoundPotionIngredient(_te, row * 5 + 4, 116, y + row * 18));
		}
		addSlotToContainer(new SlotRemoveOnly(_te, 30, 8, y + 6 * 18));
		addSlotToContainer(new SlotAcceptInsertable(_te, 31, 146, 141));

		for (int row = 0; row < 6; row++)
			getSlot(row * 5 + 1).setBackgroundName(ingredient);
		getSlot(31).setBackgroundName(bottle);
	}

	@Override
	protected int getPlayerInventoryVerticalOffset() {

		return 162;
	}

	@Override
	protected boolean performMerge(int slotIndex, @Nonnull ItemStack stack) {

		int invBase = getSizeInventory();
		int invFull = inventorySlots.size();

		if (slotIndex < invBase) {
			return mergeItemStack(stack, invBase, invFull, true);
		}
		return mergeItemStack(stack, 0, invBase, false);
	}

}
