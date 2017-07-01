package powercrystals.minefactoryreloaded.item.gun.ammo;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import powercrystals.minefactoryreloaded.api.INeedleAmmo;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.item.base.ItemFactory;

import javax.annotation.Nonnull;
import java.util.List;

public abstract class ItemNeedlegunAmmo extends ItemFactory implements INeedleAmmo {

	public ItemNeedlegunAmmo() {
		setHasSubtypes(false);
	}

	@Override
	public void addInfo(@Nonnull ItemStack stack, EntityPlayer player, List<String> infoList, boolean advancedTooltips) {
		super.addInfo(stack, player, infoList, advancedTooltips);
		infoList.add(String.format(MFRUtil.localize("tip.info.mfr.needlegun.ammo", true),
				(stack.getMaxDamage() - stack.getItemDamage() + 1)));
	}

}
