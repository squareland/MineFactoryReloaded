package powercrystals.minefactoryreloaded.modhelpers.thermalexpansion;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.api.mob.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.mob.RandomMobProvider;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class ThermalExpansion implements IMFRIntegrator, IRandomMobProvider {

	public void load() {

		MFRRegistry.registerRandomMobProvider(this);

		// Smooth Blackstone -> Cobble
		sendPulv(new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 0),
				new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 2));
		// Smooth Whitestone -> Cobble
		sendPulv(new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 1),
				new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 3));
	}

	private static void sendPulv(@Nonnull ItemStack input, @Nonnull ItemStack output) {

		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", 3200);
		toSend.setTag("input", input.writeToNBT(new NBTTagCompound()));
		toSend.setTag("primaryOutput", output.writeToNBT(new NBTTagCompound()));
		sendComm("PulverizerRecipe", toSend);
	}

	private static void sendComm(String type, NBTTagCompound msg) {

		FMLInterModComms.sendMessage("thermalexpansion", type, msg);
	}

	@Override
	public List<RandomMobProvider> getRandomMobs(World w) {

		ArrayList<RandomMobProvider> mobs = new ArrayList<RandomMobProvider>();

		mobs.add(new RandomMobProvider(20, (world, pos) -> {
			EntityCreeper creeper = MFRUtil.spawnMob(EntityCreeper.class, world, pos);
			creeper.setCustomNameTag("Exploding Zeldo");
			creeper.setAlwaysRenderNameTag(true);
			creeper.enablePersistence();
			ItemStack armor = new ItemStack(MFRThings.plasticBootsItem);
			armor.setStackDisplayName("Zeldo's Ruby Slippers");
			EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(armor);
			creeper.setItemStackToSlot(slot, armor);
			creeper.setDropChance(slot, 2);
			return creeper;
		}));

		return mobs;
	}

}
