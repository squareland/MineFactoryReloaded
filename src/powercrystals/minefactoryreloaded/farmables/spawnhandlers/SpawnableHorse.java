package powercrystals.minefactoryreloaded.farmables.spawnhandlers;

import java.lang.reflect.Method;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.AbstractHorse;
import net.minecraft.entity.passive.EntityHorse;

import net.minecraft.entity.passive.HorseArmorType;
import net.minecraftforge.fml.common.ObfuscationReflectionHelper;
import powercrystals.minefactoryreloaded.api.IMobSpawnHandler;

public class SpawnableHorse implements IMobSpawnHandler {

	@Override
	public Class<? extends EntityLivingBase> getMobClass() {

		return AbstractHorse.class;
	}

	@Override
	public void onMobSpawn(EntityLivingBase entity) {

	}

	@Override
	public void onMobExactSpawn(EntityLivingBase entity) {

		AbstractHorse ent = (AbstractHorse) entity;

		try {
			ObfuscationReflectionHelper.setPrivateValue(AbstractHorse.class, ent, null, "horseChest", "field_110296_bG");
			Method m = EntityHorse.class.getDeclaredMethod("func_110226_cD");
			m.setAccessible(true);
			m.invoke(entity);
		} catch (Throwable e) {
			e.printStackTrace();
			if ((ent instanceof AbstractChestHorse && ((AbstractChestHorse)ent).hasChest())
					|| ent.isHorseSaddled()
					|| (ent instanceof EntityHorse && ((EntityHorse) ent).getHorseArmorType() != HorseArmorType.NONE))
				entity.setDead();
		}
	}

}
