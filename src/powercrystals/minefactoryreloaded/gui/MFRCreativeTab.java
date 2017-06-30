package powercrystals.minefactoryreloaded.gui;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;

import powercrystals.minefactoryreloaded.setup.MFRThings;

public class MFRCreativeTab extends CreativeTabs
{
	public static final MFRCreativeTab tab = new MFRCreativeTab("MineFactory Reloaded");

	public MFRCreativeTab(String label)
	{
		super(label);
	}

	@Override
	public ItemStack getIconItemStack()
	{
		return new ItemStack(MFRThings.conveyorBlock, 1, 16);
	}

	@Override
	public String getTranslatedTabLabel()
	{
		return this.getTabLabel();
	}

	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack getTabIconItem()
	{
		return null;
	}
}
