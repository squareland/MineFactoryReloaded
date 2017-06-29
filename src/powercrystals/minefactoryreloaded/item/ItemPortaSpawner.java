package powercrystals.minefactoryreloaded.item;

import cofh.lib.util.helpers.ItemHelper;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.item.base.ItemFactory;
import powercrystals.minefactoryreloaded.net.Packets;
import powercrystals.minefactoryreloaded.render.ModelHelper;

public class ItemPortaSpawner extends ItemFactory {

	private static Block _block = Blocks.MOB_SPAWNER;
	public static final String spawnerTag = "spawner";
	private static final String placeTag = "placeDelay";

	public ItemPortaSpawner() {

		setUnlocalizedName("mfr.portaspawner");
		setMaxStackSize(1);
		setRegistryName(MineFactoryReloadedCore.modId, "porta_spawner");
	}

	public static NBTTagCompound getSpawnerTag(ItemStack stack) {

		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null) {
			if (tag.hasKey(spawnerTag))
				return tag.getCompoundTag(spawnerTag);
		}
		return null;
	}

	private static String getEntityId(ItemStack stack) {

		NBTTagCompound tag = getSpawnerTag(stack);
		if (tag != null)
			return tag.getString("EntityId");
		return null;
	}

	public static boolean hasData(ItemStack stack) {

		return getEntityId(stack) != null;
	}

	private static int getDelay(ItemStack stack) {

		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null) {
			return tag.getInteger(placeTag);
		}
		return 0;
	}

	@Override
	public void addInfo(ItemStack stack, EntityPlayer player, List<String> infoList, boolean advancedTooltips) {

		super.addInfo(stack, player, infoList, advancedTooltips);
		String id = getEntityId(stack);
		if (id != null)
			infoList.add(MFRUtil.localize("tile.mobSpawner") + ": " +
					MFRUtil.localize("entity.", id));
		int delay = getDelay(stack);
		if (delay > 0) {
			String s = MFRUtil.localize("tip.info.mfr.cannotplace", true, "%s");
			infoList.add(String.format(s, Math.ceil(delay / 20f)));
		}
	}

	@Override
	public void onUpdate(ItemStack stack, World world, Entity entity, int par4, boolean par5) {

		NBTTagCompound tag = stack.getTagCompound();
		if (tag != null && tag.hasKey(placeTag) && tag.getInteger(placeTag) > 0) {
			tag.setInteger(placeTag, tag.getInteger(placeTag) - 1);
		}
	}

	@Override
	public boolean shouldCauseReequipAnimation(ItemStack oldStack, ItemStack newStack, boolean slotChanged) {
		
		return !ItemHelper.areItemStacksEqualIgnoreTags(oldStack, newStack, placeTag);
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing side,
			float xOffset, float yOffset, float zOffset) {

		ItemStack itemstack = player.getHeldItem(hand);

		if (world.isRemote) {
			return EnumActionResult.SUCCESS;
		}
		if (getEntityId(itemstack) == null) {
			if (world.getBlockState(pos).getBlock().equals(_block)) {
				TileEntity te = world.getTileEntity(pos);
				NBTTagCompound tag = new NBTTagCompound();
				tag.setTag(spawnerTag, new NBTTagCompound());
				te.writeToNBT(tag.getCompoundTag(spawnerTag));
				tag.setInteger(placeTag, 40 * 20);
				itemstack.setTagCompound(tag);
				world.setBlockToAir(pos);
				return EnumActionResult.SUCCESS;
			} else
				return EnumActionResult.PASS;
		} else {
			if (getDelay(itemstack) <= 0 &&
					placeBlock(itemstack, player, hand, world, pos, side, xOffset, yOffset, zOffset)) {
				return EnumActionResult.SUCCESS;
			}
			return EnumActionResult.PASS;
		}
	}

	private boolean placeBlock(ItemStack itemstack, EntityPlayer player, EnumHand hand, World world, BlockPos pos, EnumFacing side,
			float xOffset, float yOffset, float zOffset) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		
		BlockPos placePos = pos;

		if (block == Blocks.SNOW_LAYER) {
			side = EnumFacing.UP;
		} else if (!block.isReplaceable(world, placePos)) {
			placePos = placePos.offset(side);
		}

		if (itemstack.getCount() == 0) {
			return false;
		} else if (!player.canPlayerEdit(placePos, side, itemstack)) {
			return false;
		} else if (placePos.getY() == 255 && state.getMaterial().isSolid()) {
			return false;
		} else if (world.mayPlace(_block, placePos, false, side, player)) {
			IBlockState placedState = _block.getStateForPlacement(world, placePos, side, xOffset, yOffset, zOffset, 0, player, hand);

			if (placeBlockAt(itemstack, player, world, placePos, side, xOffset, yOffset, zOffset, placedState)) {
				SoundType soundType = block.getSoundType(state, world, placePos, null);
				world.playSound(null, placePos.getX() + 0.5F, placePos.getY() + 0.5F, placePos.getZ() + 0.5F, soundType.getStepSound(), SoundCategory.BLOCKS,
					(soundType.getVolume() + 1.0F) / 2.0F, soundType.getPitch() * 0.8F);
				itemstack.shrink(1);
			}

			return true;
		} else {
			return false;
		}
	}

	private boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState state) {

		// TODO: record and read the block that was consumed
		if (!world.setBlockState(pos, state, 3)) {
			return false;
		}

		Block block = world.getBlockState(pos).getBlock();
		if (block.equals(_block)) {
			block.onBlockPlacedBy(world, pos, state, player, stack);
			block.onBlockPlacedBy(world, pos, state, player, stack);
			TileEntity te = world.getTileEntity(pos);
			NBTTagCompound tag = stack.getTagCompound();
			if (tag.hasKey(spawnerTag))
				tag = tag.getCompoundTag(spawnerTag);
			tag.setInteger("x", pos.getX());
			tag.setInteger("y", pos.getY());
			tag.setInteger("z", pos.getZ());
			te.readFromNBT(tag);
			Packets.sendToAllPlayersWatching(world, pos, te.getUpdatePacket());
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public boolean hasEffect(ItemStack stack) {

		return hasData(stack);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public EnumRarity getRarity(ItemStack stack) {

		return hasData(stack) ? EnumRarity.EPIC : EnumRarity.RARE;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(this, "porta_spawner");
	}
}
