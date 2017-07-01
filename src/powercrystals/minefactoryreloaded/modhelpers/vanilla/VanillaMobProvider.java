package powercrystals.minefactoryreloaded.modhelpers.vanilla;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.EntityArmorStand;
import net.minecraft.entity.item.EntityMinecartHopper;
import net.minecraft.entity.item.EntityTNTPrimed;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.RandomMob;
import powercrystals.minefactoryreloaded.core.AutoEnchantmentHelper;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableZombiePigman;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class VanillaMobProvider implements IRandomMobProvider
{
	@SuppressWarnings("unchecked")
	@Override
	public List<RandomMob> getRandomMobs(World world)
	{
		List<RandomMob> mobs = new ArrayList<RandomMob>();
		
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityChicken.class,   world), 130));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntitySheep.class,     world), 100));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityCow.class,       world), 100));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityPig.class,       world), 100));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityRabbit.class,    world),  50));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityBat.class,       world),  35));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityEndermite.class, world),  35));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntitySquid.class,     world),  30));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityGuardian.class,  world),  30));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityCreeper.class,   world),  25));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityMooshroom.class, world),  20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntitySlime.class,     world),  20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityOcelot.class,    world),  20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityWolf.class,      world),  20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityHorse.class,     world),  20));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityPolarBear.class, world),  15));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityGhast.class,     world),  15));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityWitch.class,     world),  10));
		
		EntityXPOrb batJockey = prepareXPOrb(world);
		EntityBat invisibat = MFRUtil.prepareMob(EntityBat.class, world);
		invisibat.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, Short.MAX_VALUE));
		batJockey.startRiding(invisibat);
		mobs.add(new RandomMob(invisibat, 55));
		
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityMinecartHopper.class, world), 15));
		mobs.add(new RandomMob(MFRUtil.prepareMob(EntityArmorStand.class, world), 15));
		
		EntityPig sheep = MFRUtil.prepareMob(EntityPig.class, world);
		for (EntityAITaskEntry a : sheep.tasks.taskEntries)
			if (a.action instanceof EntityAIPanic)
			{
				sheep.tasks.removeTask(a.action);
				break;
			}
		sheep.tasks.addTask(1, new EntityAIAttackMelee(sheep, 1.5D, true));
		sheep.targetTasks.addTask(1, new EntityAIHurtByTarget(sheep, false));
		sheep.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.GOLDEN_AXE, 1, 5));
		sheep.setDropChance(EntityEquipmentSlot.MAINHAND, Float.NEGATIVE_INFINITY);
		sheep.setCustomNameTag("SHEEP");
		sheep.setAlwaysRenderNameTag(true);
		mobs.add(new RandomMob(sheep, 10));
		
		EntityCreeper chargedCreeper = MFRUtil.prepareMob(EntityCreeper.class, world);
		NBTTagCompound creeperNBT = new NBTTagCompound(); 
		chargedCreeper.writeToNBT(creeperNBT);
		creeperNBT.setBoolean("powered", true);
		creeperNBT.setShort("Fuse", (short)120);
		chargedCreeper.readFromNBT(creeperNBT);
		mobs.add(new RandomMob(chargedCreeper, 5));
		
		EntityTNTPrimed armedTNT = MFRUtil.prepareMob(EntityTNTPrimed.class, world);
		armedTNT.setFuse(120);
		mobs.add(new RandomMob(armedTNT, 5));
		
		EntitySlime invisislime = MFRUtil.prepareMob(EntitySlime.class, world);
		invisislime.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 120 * 20));
		mobs.add(new RandomMob(invisislime, 5));
		
		EntityMooshroom invisishroom = MFRUtil.prepareMob(EntityMooshroom.class, world);
		invisishroom.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 120 * 20));
		mobs.add(new RandomMob(invisishroom, 5));
		
		EntityWolf invisiwolf = MFRUtil.prepareMob(EntityWolf.class, world);
		invisiwolf.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 120 * 20));
		invisiwolf.setAngry(true);
		mobs.add(new RandomMob(invisiwolf, 5));

		EntityTNTPrimed tntJockey = MFRUtil.prepareMob(EntityTNTPrimed.class, world);
		EntityBat tntMount = MFRUtil.prepareMob(EntityBat.class, world);
		tntJockey.setFuse(120);
		tntJockey.startRiding(tntMount);
		mobs.add(new RandomMob(tntMount, 2));
		
		EntitySkeleton skeleton1 = MFRUtil.prepareMob(EntitySkeleton.class, world);
		EntitySkeleton skeleton2 = MFRUtil.prepareMob(EntitySkeleton.class, world);
		EntitySkeleton skeleton3 = MFRUtil.prepareMob(EntitySkeleton.class, world);
		EntitySkeleton skeleton4 = MFRUtil.prepareMob(EntitySkeleton.class, world);
		skeleton4.startRiding(skeleton3);
		skeleton3.startRiding(skeleton2);
		skeleton2.startRiding(skeleton1);
		mobs.add(new RandomMob(skeleton1, 2));
		
		EntityBlaze blazeJockey = MFRUtil.prepareMob(EntityBlaze.class, world);
		EntityGhast blazeMount = MFRUtil.prepareMob(EntityGhast.class, world);
		blazeJockey.startRiding(blazeMount);
		mobs.add(new RandomMob(blazeMount, 2));
		
		EntityCreeper creeperJockey = MFRUtil.prepareMob(EntityCreeper.class, world);
		EntityCaveSpider creeperMount = MFRUtil.prepareMob(EntityCaveSpider.class, world);
		creeperJockey.startRiding(creeperMount);
		mobs.add(new RandomMob(creeperMount, 2));

		tntJockey = MFRUtil.prepareMob(EntityTNTPrimed.class, world);
		EntityXPOrb tntMount2 = prepareXPOrb(world);
		tntJockey.setFuse(120);
		tntJockey.startRiding(tntMount2);
		mobs.add(new RandomMob(tntMount2, 2));

		EntityPigZombie derp = MFRUtil.prepareMob(EntityPigZombie.class, world);
		derp.onInitialSpawn(world.getDifficultyForLocation(derp.getPosition()), null);
		derp.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 120 * 20));
		derp.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1.1);
		derp.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(18);
		derp.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		derp.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
		derp.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(50);
		derp.stepHeight = 2;
		{
			@Nonnull ItemStack armor = new ItemStack(Items.LEATHER_LEGGINGS);
			EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(armor);
			armor.setStackDisplayName(new String(new char[]{77, 97, 110, 32, 80, 97, 110, 116, 115}));
			if (world.rand.nextBoolean()) {
				derp.setCustomNameTag("Super " + new String(new char[]{90, 105, 115, 116, 101, 97, 117}));
				armor = AutoEnchantmentHelper.addRandomEnchantment(derp.getRNG(), armor, 60000, true);
				derp.setItemStackToSlot(slot, armor);
				derp.setDropChance(slot, 0.01F);
				armor = derp.getRNG().nextInt(10) == 0 ? new ItemStack(Items.LAVA_BUCKET) : GrindableZombiePigman.sign.copy();
				derp.setHeldItem(EnumHand.MAIN_HAND, armor);
				derp.setDropChance(EntityEquipmentSlot.MAINHAND, 2.0F);
			} else {
				derp.setCustomNameTag(new String(new char[]{80, 105, 103, 68, 101, 114, 112}));
				armor = AutoEnchantmentHelper.addRandomEnchantment(derp.getRNG(), armor, 90, true);
				derp.setItemStackToSlot(slot, armor);
				derp.setDropChance(slot, 0.05F);
				armor = new ItemStack(Items.LAVA_BUCKET);
				derp.setHeldItem(EnumHand.MAIN_HAND, armor);
				derp.setDropChance(EntityEquipmentSlot.MAINHAND, 0.5F);
			}
			derp.setAlwaysRenderNameTag(true);
			derp.enablePersistence();
		}
		mobs.add(new RandomMob(derp, 1, false));
		
		creeperJockey = MFRUtil.prepareMob(EntityCreeper.class, world);
		EntityXPOrb creeperMount2 = prepareXPOrb(world);
		creeperJockey.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20));
		creeperJockey.onStruckByLightning(null);
		creeperJockey.startRiding(creeperMount2);
		mobs.add(new RandomMob(creeperMount2, 1));

		EntityEnderman direBane = MFRUtil.prepareMob(EntityEnderman.class, world);
		direBane.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 120 * 20));
		direBane.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 120 * 20));
		direBane.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120);
		direBane.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7);
		direBane.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15);
		direBane.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
		direBane.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
		direBane.stepHeight = 2;
		EntityPlayer player = world.getPlayerEntityByName("direwolf20");
		if (player != null)
		{
			direBane.setCustomNameTag("Bane of direwolf");
			direBane.setAlwaysRenderNameTag(true);
			direBane.enablePersistence();
			@Nonnull ItemStack armor = new ItemStack(Items.GOLDEN_CHESTPLATE);
			armor = AutoEnchantmentHelper.addRandomEnchantment(direBane.getRNG(), armor, 60, true);
			EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(armor);
			direBane.setItemStackToSlot(slot, armor);
			direBane.setDropChance(slot, 2.0F);
		}
		mobs.add(new RandomMob(direBane, 1));
		
		return mobs;
	}
	
	private EntityXPOrb prepareXPOrb(World world)
	{
		EntityXPOrb orb = MFRUtil.prepareMob(EntityXPOrb.class, world);
		orb.xpValue = 1;
		orb.xpOrbAge = Short.MIN_VALUE + 6001;
		orb.delayBeforeCanPickup = Short.MAX_VALUE;
		return orb;
	}
}
