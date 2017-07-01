package powercrystals.minefactoryreloaded.item.base;

import cofh.core.render.IModelRegister;
import cofh.core.util.core.IInitializer;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.render.ModelHelper;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nonnull;
import java.util.List;

public class ItemFactory extends Item implements IInitializer, IModelRegister{

	protected String modelName;
	protected String variant;

	public ItemFactory() {

		setCreativeTab(MFRCreativeTab.tab);
		MFRThings.registerInitializer(this);
		MineFactoryReloadedCore.proxy.addModelRegister(this);
	}

	public void getSubItems(Item item, NonNullList<ItemStack> subTypes) {

		subTypes.add(new ItemStack(item, 1, 0));
	}

	public void addInfo(@Nonnull ItemStack stack, EntityPlayer player, List<String> infoList, boolean advancedTooltips) {

		String str = "tip.info" + getUnlocalizedName(stack).substring(4);
		str = MFRUtil.localize(str, true, null);
		if (str != null)
			infoList.add(str);
	}

	@SuppressWarnings({ "rawtypes", "unchecked" })
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(@Nonnull ItemStack stack, EntityPlayer player, List infoList, boolean advancedTooltips) {

		super.addInformation(stack, player, infoList, advancedTooltips);
		addInfo(stack, player, infoList, advancedTooltips);
	}

	@SuppressWarnings("rawtypes")
	@Override
	public void getSubItems(Item item, CreativeTabs creativeTab, NonNullList<ItemStack> subTypes) {

		getSubItems(item, subTypes);
	}

	@Override
	public String toString() {

		StringBuilder b = new StringBuilder(getClass().getName());
		b.append('@').append(System.identityHashCode(this)).append('{');
		b.append("l:").append(getUnlocalizedName());
		b.append('}');
		return b.toString();
	}

	@Override
	public boolean preInit() {

		MFRRegistry.registerItem(this);
		return true;
	}

	@Override
	public boolean initialize() {

		return true;
	}

	@Override
	public boolean postInit() {

		return true;
	}

	public ItemFactory setModelLocation(String modelName, String variant) {

		this.modelName = modelName;
		this.variant = variant;

		return this;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(this, modelName, variant);
	}
}
