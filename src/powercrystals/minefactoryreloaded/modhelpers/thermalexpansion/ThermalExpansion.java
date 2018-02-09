package powercrystals.minefactoryreloaded.modhelpers.thermalexpansion;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.RandomMob;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

/*@ChildMod(parent = MineFactoryReloadedCore.modId, mod = @Mod(modid = "minefactoryreloaded_compatthermalexpansion",
		name = "MFR Compat: ThermalExpansion",
		version = MineFactoryReloadedCore.version,
		dependencies = "after:MineFactoryReloaded;after:ThermalExpansion",
		customProperties = @CustomProperty(k = "cofhversion", v = "true")))*/
public class ThermalExpansion implements IRandomMobProvider {

	@EventHandler
	public void init(FMLInitializationEvent e) {

		try {
			MFRRegistry.registerRandomMobProvider(this);

			// Smooth Blackstone -> Cobble
			sendPulv(new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 0),
				new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 2));
			// Smooth Whitestone -> Cobble
			sendPulv(new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 1),
				new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 3));
		} catch (Throwable $) {
			ModContainer This = FMLCommonHandler.instance().findContainerFor(this);
			LogManager.getLogger(This.getModId()).log(Level.ERROR, "There was a problem loading " + This.getName(), $);
		}
	}

	private static void sendPulv(@Nonnull ItemStack input, @Nonnull ItemStack output) {

		NBTTagCompound toSend = new NBTTagCompound();
		toSend.setInteger("energy", 3200);
		toSend.setTag("input", input.writeToNBT(new NBTTagCompound()));
		toSend.setTag("primaryOutput", output.writeToNBT(new NBTTagCompound()));
		sendComm("PulverizerRecipe", toSend);
	}

	private static void sendComm(String type, NBTTagCompound msg) {

		FMLInterModComms.sendMessage("ThermalExpansion", type, msg);
	}

	@Override
	public List<RandomMob> getRandomMobs(World world) {

		ArrayList<RandomMob> mobs = new ArrayList<RandomMob>();

		EntityCreeper creeper = MFRUtil.prepareMob(EntityCreeper.class, world);
		creeper.setCustomNameTag("Exploding Zeldo");
		creeper.setAlwaysRenderNameTag(true);
		creeper.enablePersistence();
		@Nonnull ItemStack armor = new ItemStack(MFRThings.plasticBootsItem);
		armor.setStackDisplayName("Zeldo's Ruby Slippers");
		EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(armor);
		creeper.setItemStackToSlot(slot, armor);
		creeper.setDropChance(slot, 2);
		mobs.add(new RandomMob(creeper, 20));

		return mobs;
	}

}
