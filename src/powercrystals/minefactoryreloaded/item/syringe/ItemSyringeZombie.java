package powercrystals.minefactoryreloaded.item.syringe;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.*;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.render.ModelHelper;
import powercrystals.minefactoryreloaded.setup.MFRThings;

public class ItemSyringeZombie extends ItemSyringe
{
	public ItemSyringeZombie()
	{
		setUnlocalizedName("mfr.syringe.zombie");
		setContainerItem(MFRThings.syringeEmptyItem);
		setRegistryName(MineFactoryReloadedCore.modId, "syringe_zombie");
	}

	@Override
	public boolean canInject(World world, EntityLivingBase entity, ItemStack syringe)
	{
		return entity instanceof EntityAgeable && ((EntityAgeable)entity).getGrowingAge() < 0;
	}
	
	@Override
	public boolean inject(World world, EntityLivingBase entity, ItemStack syringe)
	{
		((EntityAgeable)entity).setGrowingAge(0);
		if(world.rand.nextInt(100) < 5)
		{
			Entity e = null;
			if (entity instanceof EntityPig)
			{
				e = new EntityPigZombie(world);
			}
			else if (entity instanceof AbstractHorse)
			{
				if (entity instanceof EntityHorse) {
					e = new EntityZombieHorse(world);
					copyHorseAttributes((AbstractHorse) entity, (AbstractHorse) e);
				} else if (entity instanceof EntityZombieHorse) {
					e = new EntitySkeletonHorse(world);
					copyHorseAttributes((AbstractHorse) entity, (AbstractHorse) e);
				}
			}
			else
			{
				e = new EntityZombie(world);
			}
			
			if (e != null)
			{
				e.setLocationAndAngles(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
				world.spawnEntity(e);
				entity.setDead();
			}
		}
		return true;
	}

	private void copyHorseAttributes(AbstractHorse originalHorse, AbstractHorse newHorse) {

		newHorse.copyLocationAndAnglesFrom(originalHorse);
		newHorse.setHorseSaddled(originalHorse.isHorseSaddled());
		newHorse.jumpPower = originalHorse.jumpPower;
		newHorse.setTemper(originalHorse.getTemper());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(MFRThings.syringeZombieItem, "syringe", "variant=zombie");
	}
}
