package powercrystals.minefactoryreloaded.item.gun.ammo;

import codechicken.lib.model.ModelRegistryHelper;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.item.base.ItemMulti;
import powercrystals.minefactoryreloaded.render.ModelHelper;
import powercrystals.minefactoryreloaded.render.item.RocketItemRenderer;

public class ItemRocket extends ItemMulti {

	public ItemRocket() {

		setNames(new String[] {"smart", null});
		setUnlocalizedName("mfr.rocket");
		setMaxStackSize(16);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(this, "rocket");
		ModelHelper.registerModel(this, 1, "rocket");
		ModelRegistryHelper.register(new ModelResourceLocation(MineFactoryReloadedCore.modId + ":rocket", "inventory"), new RocketItemRenderer());

	}
}
