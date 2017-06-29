package powercrystals.minefactoryreloaded.item.tool;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.RayTraceResult.Type;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.item.base.ItemFactoryTool;
import powercrystals.minefactoryreloaded.render.ModelHelper;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

public class ItemSpyglass extends ItemFactoryTool {

	public ItemSpyglass() {

		setUnlocalizedName("mfr.spyglass");
		setMaxStackSize(1);
		setRegistryName(MineFactoryReloadedCore.modId, "spyglass");
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		if (world.isRemote) {
			RayTraceResult result = rayTrace();
			if (result == null || (result.typeOfHit == Type.ENTITY && result.entityHit == null)) {
				player.sendMessage(new TextComponentTranslation("chat.info.mfr.spyglass.nosight"));
			} else if(result.typeOfHit == Type.ENTITY) {
				player.sendMessage(new TextComponentString("")
						.appendText(I18n
								.translateToLocalFormatted("chat.info.mfr.spyglass.hitentity",
										getEntityName(result.entityHit),
										result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ)));
			} else {
				IBlockState state = world.getBlockState(result.getBlockPos());
				Block block = state.getBlock();
				ItemStack tempStack = null;
				if (block != null)
					tempStack = block.getPickBlock(state, result, world, result.getBlockPos(), player);
				if (tempStack == null)
					tempStack = new ItemStack(block, 1, block.getMetaFromState(state));
				if (tempStack.getItem() != null) {
					player.sendMessage(new TextComponentString("")
							.appendText(I18n
									.translateToLocalFormatted("chat.info.mfr.spyglass.hitblock",
											tempStack.getDisplayName(), block.getRegistryName(),
											block.getMetaFromState(state), //TODO replace with list of properties and their values
											result.getBlockPos())));
				} else {
					player.sendMessage(new TextComponentString("")
							.appendText(I18n
									.translateToLocalFormatted("chat.info.mfr.spyglass.hitunknown",
										result.getBlockPos())));
				}
			}
		}

		return super.onItemRightClick(world, player, hand);
	}

	private String getEntityName(Entity entity) {
		String name = EntityList.getEntityString(entity);
		return name != null ? I18n.translateToLocal("entity." + name + ".name") : "Unknown Entity";
	}

	private RayTraceResult rayTrace() {
		if (Minecraft.getMinecraft().getRenderViewEntity() == null || Minecraft.getMinecraft().world == null) {
			return null;
		}

		double range = MFRConfig.spyglassRange.getInt();
		RayTraceResult objHit = Minecraft.getMinecraft().getRenderViewEntity().rayTrace(range, 1.0F);
		double blockDist = range;
		Vec3d playerPos = new Vec3d(Minecraft.getMinecraft().getRenderViewEntity().getPosition());

		if (objHit != null) {
			if (objHit.typeOfHit == RayTraceResult.Type.MISS) {
				objHit = null;
			} else {
				blockDist = objHit.hitVec.distanceTo(playerPos);
			}
		}

		Vec3d playerLook = Minecraft.getMinecraft().getRenderViewEntity().getLook(1.0F);
		Vec3d playerLookRel = playerPos.addVector(playerLook.xCoord * range, playerLook.yCoord * range, playerLook.zCoord * range);
		List<?> list = Minecraft.getMinecraft().world.getEntitiesWithinAABBExcludingEntity(
				Minecraft.getMinecraft().getRenderViewEntity(),
				Minecraft.getMinecraft().getRenderViewEntity().getEntityBoundingBox().addCoord(playerLook.xCoord * range, playerLook.yCoord * range, playerLook.zCoord * range).expand(1, 1, 1));

		double entityDistTotal = blockDist;
		Entity pointedEntity = null;
		for (int i = 0; i < list.size(); ++i) {
			Entity entity = (Entity)list.get(i);

			if (entity.canBeCollidedWith()) {
				double entitySize = entity.getCollisionBorderSize();
				AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().expand(entitySize, entitySize, entitySize);
				RayTraceResult movingobjectposition = axisalignedbb.calculateIntercept(playerPos, playerLookRel);

				if (axisalignedbb.isVecInside(playerPos)) {
					if (0.0D < entityDistTotal || entityDistTotal == 0.0D) {
						pointedEntity = entity;
						entityDistTotal = 0.0D;
					}
				} else if (movingobjectposition != null) {
					double entityDist = playerPos.distanceTo(movingobjectposition.hitVec);

					if (entityDist < entityDistTotal || entityDistTotal == 0.0D) {
						pointedEntity = entity;
						entityDistTotal = entityDist;
					}
				}
			}
		}

		if (pointedEntity != null && (entityDistTotal < blockDist || objHit == null)) {
			objHit = new RayTraceResult(pointedEntity);
		}
		return objHit;
	}

	@Override
	protected int getWeaponDamage(ItemStack stack) {
		return 2;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(this, "tool", "variant=spyglass");
	}
}
