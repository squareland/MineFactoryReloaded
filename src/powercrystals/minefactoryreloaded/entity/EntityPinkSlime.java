package powercrystals.minefactoryreloaded.entity;

import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import net.minecraft.world.storage.loot.LootTableList;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRConfig;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nullable;

public class EntityPinkSlime extends EntitySlime
{

	public static final ResourceLocation PINK_SLIME = new ResourceLocation(MineFactoryReloadedCore.modId + ":entities/pink_slime");

	public EntityPinkSlime(World world)
	{
		super(world);
		setSlimeSize(1, true);
	}

	@Override
	protected int getJumpDelay()
	{
		return this.rand.nextInt(10) + 5;
	}

	@Nullable
	@Override
	protected ResourceLocation getLootTable() {

		boolean drop = MFRConfig.largeSlimesDrop.getBoolean() ? getSlimeSize() > 1 : getSlimeSize() == 1;
		return drop ? PINK_SLIME : LootTableList.EMPTY;
	}

	@Override
	public void setSlimeSize(int size, boolean updateHealthToMax)
	{
		if (size > 4)
		{
			world.newExplosion(this, posX, posY, posZ, 0.1F, false, true);
			this.attackEntityFrom(DamageSource.GENERIC, 50);

			if(!world.isRemote)
			{
				ItemStack meats = new ItemStack(MFRThings.meatNuggetRawItem, world.rand.nextInt(12) + size);
				EntityItem e = new EntityItem(world, posX, posY, posZ, meats);
				e.motionX = rand.nextDouble() - 0.5D;
				e.motionY = rand.nextDouble() - 0.5D;
				e.motionZ = rand.nextDouble() - 0.5D;
				world.spawnEntity(e);
			}
		}
		else
		{
			super.setSlimeSize(size, true);
		}
	}

	@Override
	protected boolean spawnCustomParticles() {
		
		return true;
	}

	@Override
	protected EntityPinkSlime createInstance()
	{
		return new EntityPinkSlime(this.world);
	}

	@Override
    public void onStruckByLightning(EntityLightningBolt par1EntityLightningBolt)
    {
        if (!this.world.isRemote)
        {
        	this.setSlimeSize(this.getSlimeSize() + 3, true);
        }
    }
}
