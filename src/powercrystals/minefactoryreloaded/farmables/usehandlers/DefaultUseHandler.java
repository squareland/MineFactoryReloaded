package powercrystals.minefactoryreloaded.farmables.usehandlers;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.world.World;
import net.minecraftforge.fluids.FluidStack;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fluids.capability.CapabilityFluidHandler;
import net.minecraftforge.fluids.capability.IFluidTankProperties;
import powercrystals.minefactoryreloaded.core.IAdvFluidContainerItem;
import powercrystals.minefactoryreloaded.core.IUseHandler;
import powercrystals.minefactoryreloaded.core.IUseable;
import powercrystals.minefactoryreloaded.core.MFRLiquidMover;

import javax.annotation.Nonnull;

public class DefaultUseHandler implements IUseHandler {
	@Override
	public boolean canUse(@Nonnull ItemStack bucket, EntityLivingBase entity, EnumHand hand) {

		IAdvFluidContainerItem fluidHandler = (IAdvFluidContainerItem) bucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);

		FluidStack liquid = fluidHandler.getTankProperties().length > 0 ? fluidHandler.getTankProperties()[0].getContents() : null;
		if (liquid == null || liquid.amount <= 0)
			return fluidHandler.canBeFilledFromWorld();
		return fluidHandler.canPlaceInWorld();
	}

	@Nonnull
	@Override
	public ItemStack onTryUse(@Nonnull ItemStack bucket, World world, EntityLivingBase entity, EnumHand hand) {
		EntityPlayer player = entity instanceof EntityPlayer ? (EntityPlayer)entity : null;
		if (world.isRemote) return bucket;
		Item item = bucket.getItem();

		@Nonnull ItemStack q = new ItemStack(Items.BUCKET, 1, 0);
		IAdvFluidContainerItem fluidHandler = (IAdvFluidContainerItem) bucket.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
		FluidStack liquid = fluidHandler.getTankProperties().length > 0 ? fluidHandler.getTankProperties()[0].getContents() : null;
		if (liquid == null || liquid.amount <= 0) {
			if (!fluidHandler.canBeFilledFromWorld()) return bucket;
			@Nonnull ItemStack bucket2 = bucket.getCount() > 1 ? bucket.copy() : bucket;
			IAdvFluidContainerItem fluidHandler2 = (IAdvFluidContainerItem) bucket2.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null);
			bucket2.setCount(1);
			RayTraceResult objectPosition = ((IUseable)item).rayTrace(world, entity, false);
			if (objectPosition != null && objectPosition.typeOfHit == Type.BLOCK) {
				BlockPos pos = objectPosition.getBlockPos();
				if (canEntityAct(world, entity, pos, objectPosition.sideHit, bucket, false))
				{
					Block block = world.getBlockState(pos).getBlock();
					if (block instanceof IFluidBlock) {
						liquid = ((IFluidBlock)block).drain(world, pos, false);
						if (liquid != null) {
							if (fluidHandler2.fill(liquid, false) == liquid.amount) {
								fluidHandler2.fill(((IFluidBlock)block).drain(world, pos, true), true);
								if (!fluidHandler.shouldReplaceWhenFilled() || bucket2 != bucket)
									MFRLiquidMover.disposePlayerItem(bucket, bucket2, player,
											true, fluidHandler.shouldReplaceWhenFilled());
								return bucket;
							}
						}
					}
				}
			}
			if (player == null) return bucket;
			q = q.getItem().onItemRightClick(world, player, hand).getResult();
			IFluidTankProperties[] tankProps = q.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).getTankProperties();
			FluidStack contents = tankProps.length > 0 ? tankProps[0].getContents() : null;
			if (contents == null || contents.amount == 0) return bucket;
			fluidHandler2.fill(contents, true);
			if (!fluidHandler.shouldReplaceWhenFilled() || bucket2 != bucket)
				MFRLiquidMover.disposePlayerItem(bucket, bucket2, player, true, fluidHandler.shouldReplaceWhenFilled());
			return bucket;
		}
		if (fluidHandler.canPlaceInWorld()) {
			if (!liquid.getFluid().canBePlacedInWorld()) return bucket;
			Block block = liquid.getFluid().getBlock();
			if (!(block instanceof IFluidBlock)) return bucket;
			RayTraceResult objectPosition = ((IUseable)item).rayTrace(world, entity, false);
			if (objectPosition != null && objectPosition.typeOfHit == Type.BLOCK) {
				BlockPos pos = objectPosition.getBlockPos();
				if (canEntityAct(world, entity, pos, objectPosition.sideHit, bucket, true))
				{
					if (world.setBlockState(pos, block.getDefaultState(), 3))
					{
						liquid = ((IFluidBlock)block).drain(world, pos, false);
						@Nonnull ItemStack drop = bucket.splitStack(1);
						drop.getCapability(CapabilityFluidHandler.FLUID_HANDLER_CAPABILITY, null).drain(liquid.amount, true);
						if (item.hasContainerItem(drop)) {
							drop = item.getContainerItem(drop);
							if (!drop.isEmpty() && drop.isItemStackDamageable() && drop.getItemDamage() > drop.getMaxDamage())
								drop = ItemStack.EMPTY;
						}
						return drop;
					}
				}
			}
		}
		return bucket;
	}

	private boolean canEntityAct(World world, EntityLivingBase entity, BlockPos pos, EnumFacing side,
			@Nonnull ItemStack item, boolean isPlace) {
		EntityPlayer player = (entity instanceof EntityPlayer) ? (EntityPlayer)entity : null;
		return (player == null || (world.isBlockModifiable(player, pos) &&
						player.canPlayerEdit(pos, side, item))) &&
						(!isPlace || world.isAirBlock(pos) ||
								!world.getBlockState(pos).getMaterial().isSolid());
	}

	@Override
	public int getMaxUseDuration(@Nonnull ItemStack item) {
		return 0;
	}
	@Override
	public boolean isUsable(@Nonnull ItemStack item) {
		return false;
	}
	@Override
	public EnumAction useAction(@Nonnull ItemStack item) {
		return EnumAction.NONE;
	}

	@Nonnull
	@Override
	public ItemStack onUse(@Nonnull ItemStack item, EntityLivingBase entity, EnumHand hand) {
		return item;
	}
}
