package powercrystals.minefactoryreloaded.farmables.plantables;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.plant.ReplacementBlock;

import javax.annotation.Nonnull;

public class PlantableCropPlant extends PlantableStandard {

	public PlantableCropPlant(Item seed, Block plant) {

		this(seed, plant, WILDCARD);
	}
	
	public PlantableCropPlant(Item seed, Block plant, int meta) {

		super(seed, plant, meta, new ReplacementBlock(plant) {

			@Override
			public boolean replaceBlock(World world, BlockPos pos, @Nonnull ItemStack stack) {

				Block ground = world.getBlockState(pos.down()).getBlock();
				if (ground.equals(Blocks.GRASS) || ground.equals(Blocks.DIRT)) {
					world.setBlockState(pos.down(), Blocks.FARMLAND.getDefaultState());
				}
				return super.replaceBlock(world, pos, stack);
			}

		});
	}
	
	@Override
	public boolean canBePlantedHere(World world, BlockPos pos, @Nonnull ItemStack stack) {

		if(!world.isAirBlock(pos))
			return false;
		
		Block ground = world.getBlockState(pos.down()).getBlock();
		return ground.equals(Blocks.FARMLAND) ||
				ground.equals(Blocks.GRASS) ||
				ground.equals(Blocks.DIRT) ||
				super.canBePlantedHere(world, pos, stack);
	}

}
