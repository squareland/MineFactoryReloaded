package powercrystals.minefactoryreloaded.modhelpers;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.plant.ReplacementBlock;

import javax.annotation.Nonnull;

public class EmptyReplacement extends ReplacementBlock
{
	public static final EmptyReplacement INSTANCE = new EmptyReplacement();

	public EmptyReplacement()
	{
		super((Block)null);
	}

	@Override
	public boolean replaceBlock(World world, BlockPos pos, @Nonnull ItemStack stack) {
		return true;
	}
}
