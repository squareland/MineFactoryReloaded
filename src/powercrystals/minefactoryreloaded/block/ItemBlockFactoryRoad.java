package powercrystals.minefactoryreloaded.block;

import java.util.List;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBlockFactoryRoad extends ItemBlockFactory
{
	public ItemBlockFactoryRoad(net.minecraft.block.Block blockId)
	{
		super(blockId);
		setMaxDamage(0);
		setHasSubtypes(true);
		setNames(new String[] { "default", "light.off", "light.on", "light.inverted.off", "light.inverted.on" });
	}
	
	@SuppressWarnings({ "unchecked", "rawtypes" })
	@Override
	public void getSubItems(net.minecraft.item.Item item, CreativeTabs creativeTab, NonNullList<ItemStack> subTypes)
	{
		subTypes.add(new ItemStack(item, 1, 0));
		subTypes.add(new ItemStack(item, 1, 1));
		subTypes.add(new ItemStack(item, 1, 4));
	}
}
