package powercrystals.minefactoryreloaded.modhelpers.vanilla;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackMelee;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAITasks.EntityAITaskEntry;
import net.minecraft.entity.item.*;
import net.minecraft.entity.monster.*;
import net.minecraft.entity.passive.*;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityEvokerFangs;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.mob.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.mob.RandomMobProvider;
import powercrystals.minefactoryreloaded.core.AutoEnchantmentHelper;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.farmables.grindables.GrindableZombiePigman;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class VanillaMobProvider implements IRandomMobProvider {

	private static Class<? extends Entity>[] PACK_ANIMALS = new Class[] {
			EntityHorse.class,
			EntityLlama.class,
			EntityDonkey.class,
			EntitySkeletonHorse.class,
			EntityMule.class,
			EntityHorse.class,
			EntityLlama.class,
			EntityZombieHorse.class,
			EntityDonkey.class,
			EntityHorse.class,
			EntityLlama.class
	};

	@SuppressWarnings("unchecked")
	@Override
	public List<RandomMobProvider> getRandomMobs(World theWorld) {

		List<RandomMobProvider> mobs = new ArrayList<RandomMobProvider>();

		mobs.add(new RandomMobProvider(100, MFRUtil.prepareMob(EntitySheep.class)));
		mobs.add(new RandomMobProvider(100, MFRUtil.prepareMob(EntityPig.class)));
		mobs.add(new RandomMobProvider(100, MFRUtil.prepareMob(EntityCow.class)));
		mobs.add(new RandomMobProvider(60, (world, pos) -> {
			Entity e = MFRUtil.spawnMob(PACK_ANIMALS[world.rand.nextInt(PACK_ANIMALS.length)], world, pos);
			return e;
		}));
		mobs.add(new RandomMobProvider(50, MFRUtil.prepareMob(EntityRabbit.class)));
		mobs.add(new RandomMobProvider(50, MFRUtil.prepareMob(EntityParrot.class)));
		mobs.add(new RandomMobProvider(40, MFRUtil.prepareMob(EntityVex.class)));
		mobs.add(new RandomMobProvider(35, MFRUtil.prepareMob(EntityBat.class)));
		mobs.add(new RandomMobProvider(35, MFRUtil.prepareMob(EntityEndermite.class)));
		mobs.add(new RandomMobProvider(30, MFRUtil.prepareMob(EntitySquid.class)));
		mobs.add(new RandomMobProvider(25, MFRUtil.prepareMob(EntityGuardian.class)));
		mobs.add(new RandomMobProvider(25, MFRUtil.prepareMob(EntityCreeper.class)));
		mobs.add(new RandomMobProvider(20, MFRUtil.prepareMob(EntityMooshroom.class)));
		mobs.add(new RandomMobProvider(20, MFRUtil.prepareMob(EntitySlime.class)));
		mobs.add(new RandomMobProvider(20, MFRUtil.prepareMob(EntityOcelot.class)));
		mobs.add(new RandomMobProvider(20, MFRUtil.prepareMob(EntityWolf.class)));
		mobs.add(new RandomMobProvider(15, MFRUtil.prepareMob(EntityMinecartHopper.class)));
		mobs.add(new RandomMobProvider(15, MFRUtil.prepareMob(EntityArmorStand.class)));
		mobs.add(new RandomMobProvider(15, MFRUtil.prepareMob(EntityPolarBear.class)));
		mobs.add(new RandomMobProvider(15, MFRUtil.prepareMob(EntityGhast.class)));
		mobs.add(new RandomMobProvider(10, MFRUtil.prepareMob(EntityWitch.class)));
		mobs.add(new RandomMobProvider(10, MFRUtil.prepareMob(EntityZombieVillager.class)));
		mobs.add(new RandomMobProvider(5, MFRUtil.prepareMob(EntityWitherSkeleton.class)));
		mobs.add(new RandomMobProvider(5, MFRUtil.prepareMob(EntityEvokerFangs.class)));
		mobs.add(new RandomMobProvider(55, (world, pos) -> {
			EntityXPOrb batJockey = prepareXPOrb(world, pos);
			EntityBat invisibat = MFRUtil.spawnMob(EntityBat.class, world, pos);
			invisibat.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, Short.MAX_VALUE));
			batJockey.startRiding(invisibat);
			return invisibat;
		}));
		mobs.add(new RandomMobProvider(10, (world, pos) -> {
			EntityPig sheep = MFRUtil.spawnMob(EntityPig.class, world, pos);
			for (EntityAITaskEntry a : sheep.tasks.taskEntries)
				if (a.action instanceof EntityAIPanic) {
					sheep.tasks.removeTask(a.action);
					break;
				}
			sheep.tasks.addTask(1, new EntityAIAttackMelee(sheep, 1.5D, true));
			sheep.targetTasks.addTask(1, new EntityAIHurtByTarget(sheep, false));
			sheep.setHeldItem(EnumHand.MAIN_HAND, new ItemStack(Items.GOLDEN_AXE, 1, 5));
			sheep.setDropChance(EntityEquipmentSlot.MAINHAND, Float.NEGATIVE_INFINITY);
			sheep.setCustomNameTag("SHEEP");
			sheep.setAlwaysRenderNameTag(true);
			return sheep;
		}));
		mobs.add(new RandomMobProvider(5, (world, pos) -> {
			EntityCreeper chargedCreeper = MFRUtil.spawnMob(EntityCreeper.class, world, pos);
			NBTTagCompound creeperNBT = new NBTTagCompound();
			chargedCreeper.writeToNBT(creeperNBT);
			creeperNBT.setBoolean("powered", true);
			creeperNBT.setShort("Fuse", (short) 120);
			chargedCreeper.readFromNBT(creeperNBT);
			return chargedCreeper;
		}));
		mobs.add(new RandomMobProvider(5, (world, pos) -> {
			EntityTNTPrimed armedTNT = MFRUtil.spawnMob(EntityTNTPrimed.class, world, pos);
			armedTNT.setFuse(120);
			return armedTNT;
		}));
		mobs.add(new RandomMobProvider(5, (world, pos) -> {
			Class<? extends EntitySlime> clazz = world.rand.nextBoolean() ? EntitySlime.class : EntityMagmaCube.class;
			EntitySlime invisislime = MFRUtil.spawnMob(clazz, world, pos);
			invisislime.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 120 * 20));
			return invisislime;
		}));
		mobs.add(new RandomMobProvider(5, (world, pos) -> {
			EntityMooshroom invisishroom = MFRUtil.spawnMob(EntityMooshroom.class, world, pos);
			invisishroom.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 120 * 20));
			return invisishroom;
		}));
		mobs.add(new RandomMobProvider(5, (world, pos) -> {
			EntityWolf invisiwolf = MFRUtil.spawnMob(EntityWolf.class, world, pos);
			invisiwolf.addPotionEffect(new PotionEffect(MobEffects.INVISIBILITY, 120 * 20));
			invisiwolf.setAngry(true);
			return invisiwolf;
		}));
		mobs.add(new RandomMobProvider(2, (world, pos) -> {
			EntityTNTPrimed tntJockey = MFRUtil.spawnMob(EntityTNTPrimed.class, world, pos);
			EntityBat tntMount = MFRUtil.spawnMob(EntityBat.class, world, pos);
			tntJockey.setFuse(120);
			tntJockey.startRiding(tntMount);
			return tntMount;
		}));
		mobs.add(new RandomMobProvider(2, (world, pos) -> {
			EntitySkeleton skeleton1 = MFRUtil.spawnMob(EntitySkeleton.class, world, pos);
			EntitySkeleton skeleton2 = MFRUtil.spawnMob(EntitySkeleton.class, world, pos);
			EntitySkeleton skeleton3 = MFRUtil.spawnMob(EntitySkeleton.class, world, pos);
			EntitySkeleton skeleton4 = MFRUtil.spawnMob(EntitySkeleton.class, world, pos);
			skeleton4.startRiding(skeleton3);
			skeleton3.startRiding(skeleton2);
			skeleton2.startRiding(skeleton1);
			return skeleton1;
		}));
		mobs.add(new RandomMobProvider(2, (world, pos) -> {
			EntityBlaze blazeJockey = MFRUtil.spawnMob(EntityBlaze.class, world, pos);
			EntityGhast blazeMount = MFRUtil.spawnMob(EntityGhast.class, world, pos);
			blazeJockey.startRiding(blazeMount);
			return blazeMount;
		}));
		mobs.add(new RandomMobProvider(2, (world, pos) -> {
			EntityCreeper creeperJockey = MFRUtil.spawnMob(EntityCreeper.class, world, pos);
			EntityCaveSpider creeperMount = MFRUtil.spawnMob(EntityCaveSpider.class, world, pos);
			creeperJockey.startRiding(creeperMount);
			return creeperMount;
		}));
		mobs.add(new RandomMobProvider(2, (world, pos) -> {
			EntityTNTPrimed tntJockey = MFRUtil.spawnMob(EntityTNTPrimed.class, world, pos);
			EntityXPOrb tntMount = prepareXPOrb(world, pos);
			tntJockey.setFuse(120);
			tntJockey.startRiding(tntMount);
			return tntMount;
		}));
		mobs.add(new RandomMobProvider(1, (world, pos) -> {
			EntityItem e = new EntityItem(world, pos.x, pos.y, pos.z);
			e.setItem(new ItemStack(Items.DIAMOND, 1));
			e.setAgeToCreativeDespawnTime();
			e.setInfinitePickupDelay();
			return e;
		}));
		mobs.add(new RandomMobProvider(1, (world, pos) -> {
			EntityPigZombie derp = MFRUtil.spawnMob(EntityPigZombie.class, world, pos);
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
				armor.setStackDisplayName(new String(new char[] { 77, 97, 110, 32, 80, 97, 110, 116, 115 }));
				if (world.rand.nextBoolean()) {
					derp.setCustomNameTag("Super " + new String(new char[] { 90, 105, 115, 116, 101, 97, 117 }));
					armor = AutoEnchantmentHelper.addRandomEnchantment(derp.getRNG(), armor, 60000, true);
					derp.setItemStackToSlot(slot, armor);
					derp.setDropChance(slot, 0.01F);
					armor = derp.getRNG().nextInt(10) == 0 ? new ItemStack(Items.LAVA_BUCKET) : GrindableZombiePigman.sign.copy();
					derp.setHeldItem(EnumHand.MAIN_HAND, armor);
					derp.setDropChance(EntityEquipmentSlot.MAINHAND, 2.0F);
				} else {
					derp.setCustomNameTag(new String(new char[] { 80, 105, 103, 68, 101, 114, 112 }));
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
			return derp;
		}));
		mobs.add(new RandomMobProvider(1, (world, pos) -> {
			EntityCreeper creeperJockey = MFRUtil.spawnMob(EntityCreeper.class, world, pos);
			EntityXPOrb creeperMount = prepareXPOrb(world, pos);
			creeperJockey.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 20));
			creeperJockey.onStruckByLightning(null);
			creeperJockey.startRiding(creeperMount);
			return creeperMount;
		}));
		mobs.add(new RandomMobProvider(1, (world, pos) -> {
			EntityEnderman direBane = MFRUtil.spawnMob(EntityEnderman.class, world, pos);
			direBane.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 120 * 20));
			direBane.addPotionEffect(new PotionEffect(MobEffects.FIRE_RESISTANCE, 120 * 20));
			direBane.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(120);
			direBane.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.7);
			direBane.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(15);
			direBane.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(32);
			direBane.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1);
			direBane.stepHeight = 2;
			EntityPlayer player = world.getPlayerEntityByName("direwolf20");
			if (player != null) {
				direBane.setCustomNameTag("Bane of direwolf");
				direBane.setAlwaysRenderNameTag(true);
				direBane.enablePersistence();
				@Nonnull ItemStack armor = new ItemStack(Items.GOLDEN_CHESTPLATE);
				armor = AutoEnchantmentHelper.addRandomEnchantment(direBane.getRNG(), armor, 60, true);
				EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(armor);
				direBane.setItemStackToSlot(slot, armor);
				direBane.setDropChance(slot, 2.0F);
			}
			return direBane;
		}));
		// adding high-weight at the end, hoping to increase the likely hood we select the extremely low-weight entries
		mobs.add(new RandomMobProvider(130, MFRUtil.prepareMob(EntityChicken.class)));

		return mobs;
	}

	private EntityXPOrb prepareXPOrb(World world, Vec3d pos) {

		EntityXPOrb orb = MFRUtil.spawnMob(EntityXPOrb.class, world, pos);
		orb.xpValue = 1;
		orb.xpOrbAge = Short.MIN_VALUE + 6001;
		orb.delayBeforeCanPickup = Short.MAX_VALUE;
		return orb;
	}
}
