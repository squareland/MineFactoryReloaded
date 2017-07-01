package powercrystals.minefactoryreloaded.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;

public class ItemBlockConveyor extends ItemBlockFactory
{
	public ItemBlockConveyor(Block block, String[] names)
	{
		super(block, names);
	}

	@Override
	public void getSubItems(Item itemId, NonNullList<ItemStack> subTypes)
	{
		subTypes.add(new ItemStack(itemId, 1, 16));
		for(int i = 0, e = 16; i < e; i++)
			subTypes.add(new ItemStack(itemId, 1, i));
	}
}
