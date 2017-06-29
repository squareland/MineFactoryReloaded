package powercrystals.minefactoryreloaded.modhelpers.ic2;

import ic2.api.crops.CropCard;
import ic2.api.crops.Crops;
import ic2.api.crops.ICropTile;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.HarvestType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;
import powercrystals.minefactoryreloaded.modhelpers.EmptyReplacement;

public class IC2Crop implements IFactoryHarvestable, IFactoryFertilizable, IFactoryFruit
{
	private Block _block;

	public IC2Crop(Block block)
	{
		_block = block;
	}

	@Override
	public Block getPlant()
	{
		return _block;
	}

	@Override
	public boolean canFertilize(World world, BlockPos pos, FertilizerType fertilizerType)
	{
		return fertilizerType != FertilizerType.Grass && canFert(world, pos);
	}

	private boolean canFert(World world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te == null || !(te instanceof ICropTile))
			return false;
		ICropTile tec = (ICropTile)te;

		return tec.getStorageNutrients() < 15;
	}

	@Override
	public boolean fertilize(World world, Random rand, BlockPos pos, FertilizerType fertilizerType)
	{
		ICropTile tec = (ICropTile)world.getTileEntity(pos);
		tec.setStorageNutrients(100);
		tec.updateState();
		return tec.getStorageNutrients() == 100;
	}

	@Override
	public HarvestType getHarvestType()
	{
		return HarvestType.Normal;
	}

	@Override
	public boolean breakBlock()
	{
		return false;
	}

	@Override
	public boolean canBeHarvested(World world, Map<String, Boolean> harvesterSettings, BlockPos pos)
	{
		return canHarvest(world, pos);
	}

	@Override
	public boolean canBePicked(World world, BlockPos pos)
	{
		return canHarvest(world, pos);
	}

	private boolean canHarvest(World world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		if(te == null || !(te instanceof ICropTile))
			return false;

		ICropTile tec = (ICropTile)te;
		CropCard crop;
		try
		{
			crop = tec.getCrop();
			if (crop == null)
				return false;
			if(!crop.canBeHarvested(tec) || crop.canGrow(tec))
			{
				return false;
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}

		return true;
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> harvesterSettings, BlockPos pos)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		getDrops(drops, world, rand, pos);
		return drops;
	}

	@Override
	public List<ItemStack> getDrops(World world, Random rand, BlockPos pos)
	{
		List<ItemStack> drops = new ArrayList<ItemStack>();
		getDrops(drops, world, rand, pos);
		return drops;
	}

	private void getDrops(List<ItemStack> drops, World world, Random rand, BlockPos pos)
	{
		ICropTile tec = (ICropTile)world.getTileEntity(pos);
		CropCard crop;
		try
		{
			crop = tec.getCrop();

			double chance = crop.dropGainChance();
			for (int i = 0; i < tec.getStatGain(); i++)
			{
				chance *= 1.03F;
			}

			chance -= rand.nextFloat();
			int numDrops = 0;
			while (chance > 0.0F)
			{
				numDrops++;
				chance -= rand.nextFloat();
			}
			ItemStack[] cropDrops = new ItemStack[numDrops];
			for (int i = 0; i < numDrops; i++)
			{
				cropDrops[i] = crop.getGain(tec);
				if((cropDrops[i] != null) && (rand.nextInt(100) <= tec.getStatGain()))
				{
					cropDrops[i].grow(1);
				}
			}

			tec.setCurrentSize(crop.getSizeAfterHarvest(tec));
			tec.updateState();

			for(ItemStack s : cropDrops)
			{
				drops.add(s);
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}

	@Override
	public ReplacementBlock getReplacementBlock(World world, BlockPos pos)
	{
		return EmptyReplacement.INSTANCE;
	}

	@Override
	public void preHarvest(World world, BlockPos pos)
	{
	}

	@Override
	public void postHarvest(World world, BlockPos pos)
	{
	}
}
