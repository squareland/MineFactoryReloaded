/*
package powercrystals.minefactoryreloaded.modhelpers.backtools;

import cofh.mod.ChildMod;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.CustomProperty;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.ModContainer;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;

import java.lang.reflect.Method;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.LogManager;

import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.item.gun.ItemSafariNetLauncher;
import powercrystals.minefactoryreloaded.item.tool.ItemFactoryHammer;
import powercrystals.minefactoryreloaded.item.tool.ItemSpyglass;

@ChildMod(parent = MineFactoryReloadedCore.modId, mod = @Mod(modid = "minefactoryreloaded|compatbacktools",
		name = "MFR Compat: BackTools",
		version = MineFactoryReloadedCore.version,
		dependencies = "after:MineFactoryReloaded;after:mod_BackTools",
		customProperties = @CustomProperty(k = "cofhversion", v = "true")))
public class BackTools {

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@EventHandler
	public void load(FMLInitializationEvent e) {

		try {
			Class modBackTools = Class.forName("mod_BackTools");
			*/
/*
			 *  addBackItem(Class itemClass, int orientation, boolean flipped)
			 *  orientation is 0-3, and rotates counterclockwise by 90 deg * orientation
			 *  flipped true for vertical flipping of the texture
			 *//*

			Method addBackItem = modBackTools.getMethod("addBackItem", Class.class, int.class, boolean.class);
			if (addBackItem != null) {
				addBackItem.invoke(modBackTools, ItemSafariNetLauncher.class, 2, true);
				addBackItem.invoke(modBackTools, ItemSpyglass.class, 1, true);
				addBackItem.invoke(modBackTools, ItemFactoryHammer.class, 1, true);
			}
		} catch (Throwable $) {
			ModContainer This = FMLCommonHandler.instance().findContainerFor(this);
			LogManager.getLogger(This.getModId()).log(Level.ERROR, "There was a problem loading " + This.getName(), $);
		}
	}

}
*/
