package powercrystals.minefactoryreloaded.net;

import static powercrystals.minefactoryreloaded.setup.MFRThings.*;

import net.minecraft.entity.item.EntityMinecart;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import net.minecraft.entity.item.EntityMinecartMobSpawner;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.item.ItemExpireEvent;
import net.minecraftforge.event.entity.minecart.MinecartInteractEvent;

import powercrystals.minefactoryreloaded.item.ItemPortaSpawner;
import powercrystals.minefactoryreloaded.setup.MFRConfig;

public class EntityHandler {

	@SubscribeEvent
	public void onEntityJoinWorldEvent(EntityJoinWorldEvent evt) {

		if (evt.getWorld().isRemote || !(evt.getEntity() instanceof EntitySkeleton))
			return;
	}

	@SubscribeEvent
	public void onMinecartInteract(MinecartInteractEvent e) {

		if (e.getPlayer().world.isRemote)
			return;
		if (!MFRConfig.enableSpawnerCarts.getBoolean(true))
			return;
		if (e.getMinecart() != null && !e.getMinecart().isDead) {
			ItemStack item = e.getPlayer().getHeldItem(e.getHand());
			if (item != null && item.getItem().equals(portaSpawnerItem) &
					e.getMinecart().getRidingEntity() == null &
					!e.getMinecart().isBeingRidden()) {
				if (e.getMinecart().getType() == EntityMinecart.Type.RIDEABLE) {
					if (ItemPortaSpawner.hasData(item)) {
						e.setCanceled(true);
						NBTTagCompound tag = ItemPortaSpawner.getSpawnerTag(item);
						e.getPlayer().setHeldItem(e.getHand(), null);
						e.getMinecart().writeToNBT(tag);
						e.getMinecart().setDead();
						EntityMinecartMobSpawner ent = new EntityMinecartMobSpawner(e.getMinecart().world);
						ent.readFromNBT(tag);
						ent.world.spawnEntity(ent);
						ent.world.playEvent(null, 2004, ent.getPosition(), 0); // particles
					}
				}
				else if (e.getMinecart().getType() == EntityMinecart.Type.SPAWNER) {
					// maybe
				}
			}
		}
	}

	@SubscribeEvent
	public void onItemExpire(ItemExpireEvent e) {

		ItemStack stack = e.getEntityItem().getEntityItem();
		if (stack.getItem().equals(rubberLeavesItem) && stack.getItemDamage() == 0) {
			e.setCanceled(true);
			e.setExtraLife(e.getEntityItem().lifespan);
			e.getEntityItem().setEntityItemStack(new ItemStack(stack.getItem(), stack.getCount(), 1));
		}
	}

}
