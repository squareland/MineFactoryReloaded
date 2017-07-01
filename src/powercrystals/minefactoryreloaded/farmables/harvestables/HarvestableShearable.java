package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import powercrystals.minefactoryreloaded.api.HarvestType;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class HarvestableShearable extends HarvestableStandard
{
	public HarvestableShearable(Block block, HarvestType harvestType)
	{
		super(block, harvestType);
	}

	public HarvestableShearable(Block block)
	{
		super(block);
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> settings, BlockPos pos)
	{
		IBlockState state = world.getBlockState(pos);
		Block block = state.getBlock();
		if (settings.get("silkTouch") == Boolean.TRUE)
		{
			if (block instanceof IShearable)
			{
				@Nonnull ItemStack stack = new ItemStack(Items.SHEARS, 1, 0);
				if (((IShearable)block).isShearable(stack, world, pos))
				{
					return ((IShearable)block).onSheared(stack, world, pos, 0);
				}
			}
			if (Item.getItemFromBlock(block) != null)
			{
				ArrayList<ItemStack> drops = new ArrayList<ItemStack>();
				drops.add(block.getItem(world, pos, state));
				return drops;
			}
		}

		return block.getDrops(world, pos, state, 0);
	}
}
