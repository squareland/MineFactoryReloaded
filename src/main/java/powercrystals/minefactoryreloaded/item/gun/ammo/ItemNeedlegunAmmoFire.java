package powercrystals.minefactoryreloaded.item.gun.ammo;


import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.entity.ProjectileImpactEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.render.ModelHelper;

import javax.annotation.Nonnull;

public class ItemNeedlegunAmmoFire extends ItemNeedlegunAmmoBlock {

	public ItemNeedlegunAmmoFire() {
		super(Blocks.FIRE.getDefaultState());
		setShots(8);
		setDamage(10);
	}

	@Override
	public boolean onHitEntity(@Nonnull ItemStack stack, EntityPlayer owner, Entity hit, double distance) {
		if (!MinecraftForge.EVENT_BUS.post(new AttackEntityEvent(owner, hit))) {
			hit.setFire(10);
			super.onHitEntity(stack, owner, hit, distance);
		}
		return true;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(this, "needle_gun_ammo", "variant=fire");
	}
}
