
package powercrystals.minefactoryreloaded.modcompat.backtools;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;

import static powercrystals.minefactoryreloaded.modcompat.Compats.ModIds.BACKTOOLS;
import static powercrystals.minefactoryreloaded.modcompat.Compats.ModIds.MFR;

@IMFRIntegrator.DependsOn(BACKTOOLS)
public class BackTools implements IMFRIntegrator {


	@GameRegistry.ObjectHolder(value = MFR + ":safarinet_launcher")
	public static final Item safariNetLauncherItem = Items.AIR;
	@GameRegistry.ObjectHolder(value = MFR + ":spyglass")
	public static final Item spyglassItem = Items.AIR;
	@GameRegistry.ObjectHolder(value = MFR + ":hammer")
	public static final Item factoryHammerItem = Items.AIR;

	public void load() {
		/*
		 *  orientation is 0-3, and rotates counterclockwise by 90 deg * orientation
		 *  flipped true for vertical flipping of the texture
		 */

		FMLInterModComms.sendMessage(BACKTOOLS, "backtool", new ItemStack(safariNetLauncherItem, 2)); // flip?
		FMLInterModComms.sendMessage(BACKTOOLS, "backtool", new ItemStack(spyglassItem, 1)); // flip?
		FMLInterModComms.sendMessage(BACKTOOLS, "backtool", new ItemStack(factoryHammerItem, 1)); // flip?
	}

}

