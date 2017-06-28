package powercrystals.minefactoryreloaded.item.tool;

import cofh.api.block.IDismantleable;
import cofh.api.item.IToolHammer;
import cofh.asm.relauncher.Implementable;
import cofh.lib.util.helpers.BlockHelper;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockFire;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;

import net.minecraftforge.fml.common.eventhandler.Event.Result;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.api.IMFRHammer;
import powercrystals.minefactoryreloaded.item.base.ItemFactoryTool;
import powercrystals.minefactoryreloaded.render.ModelHelper;
import powercrystals.minefactoryreloaded.setup.Machine;

@Implementable("buildcraft.api.tools.IToolWrench")
public class ItemFactoryHammer extends ItemFactoryTool implements IMFRHammer, IToolHammer {

	public ItemFactoryHammer() {

		setHarvestLevel("wrench", 1);
		setUnlocalizedName("mfr.hammer");
		setMaxStackSize(1);
		setRegistryName(MineFactoryReloadedCore.modId, "hammer");
	}

	@Override
	public EnumActionResult onItemUseFirst(ItemStack stack, EntityPlayer player, World world,
			BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {

		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (block != null) {
			PlayerInteractEvent.RightClickBlock e = new PlayerInteractEvent.RightClickBlock(player, hand, stack, pos, side, new Vec3d(hitX, hitY, hitZ));
			if (MinecraftForge.EVENT_BUS.post(e) || e.getResult() == Result.DENY
					|| e.getUseBlock() == Result.DENY || e.getUseItem() == Result.DENY) {
				return EnumActionResult.PASS;
			}

			if (player.isSneaking() && block instanceof IDismantleable &&
					((IDismantleable) block).canDismantle(world, pos, state, player)) {
				if (!world.isRemote)
					((IDismantleable) block).dismantleBlock(world, pos, state, player, false);
				player.swingArm(hand);
				return EnumActionResult.PASS;
			}

			if (BlockHelper.canRotate(block)) {
				player.swingArm(hand);
				if (player.isSneaking()) {
					//TODO there used to be an alt rotate in core which rotated in opposite direction - rotateVanillaBlockAlt - get it back
					world.setBlockState(pos, BlockHelper.rotateVanillaBlock(world, state, pos), 3);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.getSoundType(state, world, pos, player).getBreakSound(), SoundCategory.PLAYERS, 1.0F, 0.6F);
				} else {
					world.setBlockState(pos, BlockHelper.rotateVanillaBlock(world, state, pos), 3);
					world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.getSoundType(state, world, pos, player).getBreakSound(), SoundCategory.PLAYERS, 1.0F, 0.8F);
				}
				return !world.isRemote ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
			} else if (!player.isSneaking() && block.rotateBlock(world, pos, side)) {
				player.swingArm(hand);
				world.playSound(null, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, block.getSoundType(state, world, pos, null).getBreakSound(), SoundCategory.PLAYERS, 1.0F, 0.8F);
				return !world.isRemote ? EnumActionResult.SUCCESS : EnumActionResult.PASS;
			}
		}
		return EnumActionResult.PASS;
	}

	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, BlockPos pos) {

		return true;
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, BlockPos pos) {

	}

	@Override
	public boolean isUsable(ItemStack item, EntityLivingBase user, Entity ent) {

		return true;
	}

	@Override
	public void toolUsed(ItemStack item, EntityLivingBase user, Entity ent) {

	}

	//@Override
	public boolean canWrench(EntityPlayer player, BlockPos pos) {

		return true;
	}

	//@Override
	public void wrenchUsed(EntityPlayer player, BlockPos pos) {

	}

	@Override
	public boolean doesSneakBypassUse(ItemStack stack, IBlockAccess world, BlockPos pos, EntityPlayer player) {

		return true;
	}

	@Override
	public boolean canHarvestBlock(IBlockState state, ItemStack stack) {

		if (state == null)
			return false;
		Material mat = state.getMaterial();
		return mat == Material.ICE |
				mat == Material.CAKE |
				mat == Material.IRON |
				mat == Material.ROCK |
				mat == Material.WOOD |
				mat == Material.GOURD |
				mat == Material.ANVIL |
				mat == Material.GLASS |
				mat == Material.PISTON |
				mat == Material.PLANTS |
				mat == Machine.MATERIAL |
				mat == Material.CIRCUITS |
				mat == Material.PACKED_ICE;
	}

	@Override
	public float getStrVsBlock(ItemStack stack, IBlockState state) {

		if (state == null)
			return 0;
		Material mat = state.getMaterial();
		if (mat == Material.ICE |
				mat == Material.CAKE |
				mat == Material.GOURD |
				mat == Material.GLASS)
			return 15f;
		return canHarvestBlock(state, stack) ? 1.35f : 0.15f;
	}

	@Override
	public boolean onBlockStartBreak(ItemStack stack, BlockPos pos, EntityPlayer player) {

		IBlockState state = player.world.getBlockState(pos);
		if (state.getBlockHardness(player.world, pos) > 2.9f) {
			Random rnd = player.getRNG();
			player.world.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_ITEM_BREAK, SoundCategory.PLAYERS, 0.8F + rnd.nextFloat() * 0.4F, 0.4F);

			for (int i = 0, e = 10 + rnd.nextInt(5); i < e; ++i) {
				Vec3d vec3 = new Vec3d((rnd.nextFloat() - 0.5D) * 0.1D, Math.random() * 0.1D + 0.1D, 0.0D);
				vec3.rotatePitch(-player.rotationPitch * (float) Math.PI / 180.0F);
				vec3.rotateYaw(-player.rotationYaw * (float) Math.PI / 180.0F);
				Vec3d vec31 = new Vec3d((rnd.nextFloat() - 0.5D) * 0.3D, rnd.nextFloat(), 0.6D);
				vec31.rotatePitch(-player.rotationPitch * (float) Math.PI / 180.0F);
				vec31.rotateYaw(-player.rotationYaw * (float) Math.PI / 180.0F);
				vec31 = vec31.addVector(player.posX, player.posY + player.getEyeHeight(), player.posZ);
				player.world.spawnParticle(EnumParticleTypes.BLOCK_CRACK, vec31.xCoord, vec31.yCoord, vec31.zCoord, vec3.xCoord,
					vec3.yCoord + 0.05D, vec3.zCoord, Block.getStateId(Blocks.FIRE.getDefaultState()));
			}
			return true;
		}
		return false;
	}

	@Override
	protected int getWeaponDamage(ItemStack stack) {

		return 4;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(this, "tool", "variant=hammer");
	}
}
