package powercrystals.minefactoryreloaded.farmables.harvestables;

import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import java.util.List;
import java.util.Map;
import java.util.Random;

public class HarvestableVine extends HarvestableStandard
{
	public HarvestableVine(net.minecraft.block.Block vine)
	{
		super(vine, powercrystals.minefactoryreloaded.api.HarvestType.TreeFruit);
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> settings, BlockPos pos)
	{
		NonNullList<ItemStack> drops = NonNullList.create();
		drops.add(new ItemStack(getPlant()));
		return drops;
	}
}
