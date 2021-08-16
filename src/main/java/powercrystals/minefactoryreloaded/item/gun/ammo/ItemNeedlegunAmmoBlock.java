package powercrystals.minefactoryreloaded.item.gun.ammo;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.BlockSnapshot;
import net.minecraftforge.event.world.BlockEvent;

import javax.annotation.Nonnull;

public class ItemNeedlegunAmmoBlock extends ItemNeedlegunAmmoStandard {

	protected IBlockState _blockState;

	public ItemNeedlegunAmmoBlock(IBlockState state, float spread) {
		super(2, spread, 4);
		if (state == null) throw new IllegalArgumentException("Null state");
		_blockState = state;
	}

	public ItemNeedlegunAmmoBlock(IBlockState state) {
		this(state, 1.5f);
	}

	@Override
	public void onHitBlock(@Nonnull ItemStack stack, EntityPlayer owner, World world, BlockPos pos, EnumFacing side, double distance) {
		BlockPos offPos = pos.offset(side);
		net.minecraftforge.common.util.BlockSnapshot snapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(world, offPos);
		placeBlockAt(world, offPos, distance);
		if (net.minecraftforge.event.ForgeEventFactory.onPlayerBlockPlace(owner, snapshot, net.minecraft.util.EnumFacing.UP, EnumHand.MAIN_HAND).isCanceled()) {
			snapshot.restore(true, false);
		}
	}

	protected Vec3d calculatePlacement(Entity hit) {
		AxisAlignedBB bb = hit.getEntityBoundingBox();
		int i = MathHelper.floor(bb.minX + 0.001D);
		int k = MathHelper.floor(bb.minZ + 0.001D);
		int l = MathHelper.floor(bb.maxX - 0.001D);
		int j1 = MathHelper.floor(bb.maxZ - 0.001D);
		return new Vec3d((i + l) / 2, bb.minY + 0.25, (k + j1) / 2);
	}

	@Override
	public boolean onHitEntity(@Nonnull ItemStack stack, EntityPlayer owner, Entity hit, double distance) {
		super.onHitEntity(stack, owner, hit, distance);
		Vec3d placement = calculatePlacement(hit);
		BlockPos pos = new BlockPos((int) placement.x, (int) placement.y, (int) placement.z);

		net.minecraftforge.common.util.BlockSnapshot snapshot = net.minecraftforge.common.util.BlockSnapshot.getBlockSnapshot(hit.world, pos);
		placeBlockAt(hit.world, pos, distance);
		if (net.minecraftforge.event.ForgeEventFactory.onPlayerBlockPlace(owner, snapshot, net.minecraft.util.EnumFacing.UP, EnumHand.MAIN_HAND).isCanceled()) {
			snapshot.restore(true, false);
		}
		return true;
	}

	@Override
	public float getSpread(@Nonnull ItemStack stack) {
		return 1.5F;
	}

	protected void placeBlockAt(World world, BlockPos pos, double distance) {
		Block block = world.getBlockState(pos).getBlock();
		if (!world.isRemote && (world.isAirBlock(pos) || block.isReplaceable(world, pos))) {
			world.setBlockState(pos, _blockState, 3);
		}
	}

}
