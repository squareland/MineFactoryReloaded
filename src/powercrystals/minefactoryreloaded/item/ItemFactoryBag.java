package powercrystals.minefactoryreloaded.item;

import codechicken.lib.inventory.InventoryUtils;
import cofh.api.item.IInventoryContainerItem;
import cofh.lib.gui.container.InventoryContainerItemWrapper;
import cofh.lib.util.helpers.InventoryHelper;
import cofh.lib.util.helpers.ItemHelper;
import cofh.lib.util.helpers.StringHelper;

import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

import net.minecraft.world.WorldServer;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraft.world.storage.loot.LootTableManager;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.item.base.ItemFactory;
import powercrystals.minefactoryreloaded.render.ModelHelper;
import powercrystals.minefactoryreloaded.setup.MFRLoot;

public class ItemFactoryBag extends ItemFactory implements IInventoryContainerItem {

	public ItemFactoryBag() {

		setUnlocalizedName("mfr.plastic.bag");
		setMaxStackSize(24);
		setRegistryName(MineFactoryReloadedCore.modId, "plastic_bag");
	}

	@Override
	public int getItemStackLimit(ItemStack stack) {

		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null && (tag.hasKey("inventory") || tag.hasKey("Inventory") || tag.hasKey("loot")))
			return 1;
		return maxStackSize;
	}

	@Override
	public void addInfo(ItemStack stack, EntityPlayer player, List<String> infoList, boolean advancedTooltips) {

		super.addInfo(stack, player, infoList, advancedTooltips);

		if (getItemStackLimit(stack) == 1) {
			if (stack.getTagCompound().hasKey("loot")) {
				infoList.add(MFRUtil.localize("info.mfr.loot", true));
			} else if (stack.getTagCompound().hasKey("inventory")) {
				infoList.add(MFRUtil.localize("info.mfr.legacy", true));
			} else if (!StringHelper.displayShiftForDetail || MFRUtil.isShiftKeyDown()) {
				ItemHelper.addInventoryInformation(stack, infoList);
			} else {
				infoList.add(MFRUtil.shiftForInfo());
			}
		} else {
			infoList.add(MFRUtil.localize("info.mfr.folded", true));
		}
	}

	@Override
	public int getSizeInventory(ItemStack container) {

		return 5;
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {

		ItemStack stack = player.getHeldItem(hand);

		if (stack.getCount() != 1) {
			if (!world.isRemote)
				player.sendMessage(new TextComponentTranslation("chat.info.mfr.bag.stacksize"));
			return new ActionResult<>(EnumActionResult.SUCCESS, stack);
		}

		if (!world.isRemote && stack.hasTagCompound() && stack.getTagCompound().hasKey("loot")) {
			stack = fillWithLoot((WorldServer) world, player, stack);
			stack.getTagCompound().removeTag("loot");
		}

		if (!world.isRemote)
			player.openGui(MineFactoryReloadedCore.instance(), 2, world, 0, 0, 0);
		return new ActionResult<>(EnumActionResult.SUCCESS, stack);
	}

	private ItemStack fillWithLoot(WorldServer world, EntityPlayer player, ItemStack stack) {
		
		LootTable lootTable = world.getLootTableManager().getLootTableFromLocation(MFRLoot.FACTORY_BAG);
		InventoryContainerItemWrapper wrapper = new InventoryContainerItemWrapper(stack);
		lootTable.fillInventory(wrapper, world.rand, new LootContext.Builder(world).withLuck(player.getLuck()).build());
		
		return wrapper.getContainerStack();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(this, "plastic_bag");
	}
}
