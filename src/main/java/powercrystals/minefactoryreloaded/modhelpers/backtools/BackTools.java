
package powercrystals.minefactoryreloaded.modhelpers.backtools;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import static powercrystals.minefactoryreloaded.modhelpers.Compats.ModIds.BACKTOOLS;

@IMFRIntegrator.DependsOn(BACKTOOLS)
public class BackTools implements IMFRIntegrator {

	public void load() {
		/*
		 *  orientation is 0-3, and rotates counterclockwise by 90 deg * orientation
		 *  flipped true for vertical flipping of the texture
		 */

		FMLInterModComms.sendMessage(BACKTOOLS, "backtool", new ItemStack(MFRThings.safariNetLauncherItem, 2)); // flip?
		FMLInterModComms.sendMessage(BACKTOOLS, "backtool", new ItemStack(MFRThings.spyglassItem, 1)); // flip?
		FMLInterModComms.sendMessage(BACKTOOLS, "backtool", new ItemStack(MFRThings.factoryHammerItem, 1)); // flip?
	}

}

