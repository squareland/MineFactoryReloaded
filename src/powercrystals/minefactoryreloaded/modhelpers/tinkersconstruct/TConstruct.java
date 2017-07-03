package powercrystals.minefactoryreloaded.modhelpers.tinkersconstruct;

import static cofh.lib.util.helpers.ItemHelper.stack;

import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.CustomProperty;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;

import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.text.TextFormatting;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.setup.MFRThings;

/*@ChildMod(parent = MineFactoryReloadedCore.modId, mod = @Mod(modid = "minefactoryreloaded|compattconstruct",
		name = "MFR Compat: Tinkers' Construct",
		version = MineFactoryReloadedCore.version,
		dependencies = "after:MineFactoryReloaded;after:TConstruct",
		customProperties = @CustomProperty(k = "cofhversion", v = "true")))*/
public class TConstruct {

	@EventHandler
	public static void load(FMLInitializationEvent e) {

		NBTTagCompound tag = new NBTTagCompound();
		tag.setInteger("Id", 1000);
		tag.setString("Name", "Plastic");
		tag.setString("localizationString", "item.mfr.plastic");
		tag.setInteger("Durability", 1500);
		tag.setInteger("MiningSpeed", 600);
		tag.setInteger("HarvestLevel", 1);
		tag.setInteger("Attack", -1);
		tag.setFloat("HandleModifier", 0.1f);
		tag.setFloat("Bow_ProjectileSpeed", 4.2f);
		tag.setInteger("Bow_DrawSpeed", 20);
		tag.setFloat("Projectile_Mass", 0.25f);
		tag.setFloat("Projectile_Fragility", 0.5f);
		tag.setString("Style", TextFormatting.GRAY.toString());
		tag.setInteger("Color", 0xFFADADAD);
		FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

		tag = new NBTTagCompound();
		tag.setInteger("MaterialId", 1000);
		tag.setTag("Item", stack(MFRThings.factoryPlasticBlock).writeToNBT(new NBTTagCompound()));
		tag.setTag("Shard", stack(MFRThings.plasticSheetItem).writeToNBT(new NBTTagCompound()));
		tag.setInteger("Value", 4);
		FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);

		tag = new NBTTagCompound();
		tag.setInteger("Id", 1001);
		tag.setString("Name", "Pink Slime");
		tag.setString("localizationString", "item.mfr.pinkslime");
		tag.setInteger("Durability", 2000);
		tag.setInteger("MiningSpeed", 300);
		tag.setInteger("HarvestLevel", 1);
		tag.setInteger("Attack", 1);
		tag.setFloat("HandleModifier", 2.5f);
		tag.setFloat("Bow_ProjectileSpeed", 4.7f);
		tag.setInteger("Bow_DrawSpeed", 15);
		tag.setFloat("Projectile_Mass", 0.20f);
		tag.setFloat("Projectile_Fragility", 0.0f);
		tag.setString("Style", TextFormatting.LIGHT_PURPLE.toString());
		tag.setInteger("Color", 0xFFF3AEC6);
		FMLInterModComms.sendMessage("TConstruct", "addMaterial", tag);

		tag = new NBTTagCompound();
		tag.setInteger("MaterialId", 1001);
		tag.setTag("Item", stack(MFRThings.pinkSlimeItem, 1, 1).writeToNBT(new NBTTagCompound()));
		//tag.setTag("Shard", stack(MFRThings.plasticSheetItem).writeToNBT(new NBTTagCompound()));
		tag.setInteger("Value", 2);
		FMLInterModComms.sendMessage("TConstruct", "addPartBuilderMaterial", tag);
		return;
	}

}
