package powercrystals.minefactoryreloaded.gui;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nonnull;

public class MFRCreativeTab extends CreativeTabs
{
	public static final MFRCreativeTab tab = new MFRCreativeTab("MineFactory Reloaded");

	public MFRCreativeTab(String label)
	{
		super(label);
	}

	@Nonnull
	@Override
	public ItemStack getIcon()
	{
		return new ItemStack(MFRThings.conveyorBlock, 1, 16);
	}

	@Override
	public String getTranslationKey()
	{
		return this.getTabLabel();
	}

	@Nonnull
	@Override
	@SideOnly(Side.CLIENT)
	public ItemStack createIcon()
	{
		return ItemStack.EMPTY;
	}
}
