package powercrystals.minefactoryreloaded.modhelpers.forestry;

import forestry.api.arboriculture.EnumGermlingType;
import forestry.api.arboriculture.ITree;
import forestry.api.arboriculture.ITreeRoot;
import forestry.api.genetics.AlleleManager;
import forestry.api.genetics.IFruitBearer;
import forestry.api.genetics.IIndividual;
import forestry.api.genetics.IPollinatable;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.FertilizerType;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;
import powercrystals.minefactoryreloaded.api.ReplacementBlock;
import powercrystals.minefactoryreloaded.farmables.harvestables.HarvestableTreeLeaves;
import powercrystals.minefactoryreloaded.modhelpers.EmptyReplacement;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

public class ForestryLeaf extends HarvestableTreeLeaves implements IFactoryFruit, IFactoryFertilizable
{
	private ITreeRoot root;
	private ReplacementBlock repl;
	protected Item _item;

	public ForestryLeaf(Block block)
	{
		super(block);
		root = (ITreeRoot)AlleleManager.alleleRegistry.getSpeciesRoot("rootTrees");
		repl = EmptyReplacement.INSTANCE;
		_item = Item.getItemFromBlock(block);
	}

	@Override
	public boolean canBePicked(World world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof IFruitBearer)
		{
			IFruitBearer fruit = (IFruitBearer)te;
			return fruit.getRipeness() >= 0.99f;
		}
		return false;
	}

	@Override
	public boolean canFertilize(World world, BlockPos pos, FertilizerType fertilizerType)
	{
		return !canBePicked(world, pos);
	}

	@Override
	public boolean fertilize(World world, Random rand, BlockPos pos, FertilizerType fertilizerType)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof IFruitBearer)
		{
			IFruitBearer fruit = (IFruitBearer)te;
			fruit.addRipeness(1f);
			return true;
		}
		return false;
	}

	@Override
	public ReplacementBlock getReplacementBlock(World world, BlockPos pos)
	{
		return repl;
	}

	@Override // HARVESTER
	public List<ItemStack> getDrops(World world, Random rand, Map<String, Boolean> settings, BlockPos pos)
	{
		ITree tree = getTree(world, pos);
		if (tree == null)
			return null;

		ArrayList<ItemStack> prod = new ArrayList<ItemStack>();

		float modifier = 1f;
		if (settings.get("silkTouch") == Boolean.TRUE)
		{
			@Nonnull ItemStack item = new ItemStack(_item);
			NBTTagCompound tag = new NBTTagCompound();
			tree.writeToNBT(tag);
			item.setTagCompound(tag);
			prod.add(item);
		}
		else
		{
			boolean hasMate = tree.getMate() != null;
			for (ITree s : getSaplings(tree, world, pos, modifier))
				if (s != null) {
					if ((hasMate && !s.isGeneticEqual(tree)) || rand.nextInt(32) == 0)
						if (rand.nextBoolean())
							prod.add(root.getMemberStack(s, EnumGermlingType.POLLEN));

					prod.add(root.getMemberStack(s, EnumGermlingType.SAPLING));
				}

			getFruits(world, pos, tree, prod);
		}

		return prod;
	}

	private static List<ITree> getSaplings(ITree tree, World world, BlockPos pos, float modifier) {
		return tree.getSaplings(world, null, pos, modifier);
	}

	@Override // FRUIT PICKER
	public List<ItemStack> getDrops(World world, Random rand, BlockPos pos)
	{
		ITree tree = getTree(world, pos);
		if (tree == null)
			return null;

		ArrayList<ItemStack> prod = new ArrayList<ItemStack>();
		getFruits(world, pos, tree, prod);
		return prod;
	}

	private ITree getTree(World world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof IPollinatable) {
			IIndividual t = ((IPollinatable)te).getPollen();
			if (t instanceof ITree)
				return (ITree)t;
		}
		return null;
	}

	private void getFruits(World world, BlockPos pos, ITree tree, ArrayList<ItemStack> prod)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof IFruitBearer)
		{
			IFruitBearer fruit = (IFruitBearer)te;
			if (fruit.hasFruit())
			{
				//int period = tree.getGenome().getFruitProvider().getRipeningPeriod();
				//@Nonnull ItemStack[] o = tree.produceStacks(world, pos, (int)(fruit.getRipeness() * period + 0.1f));
				prod.addAll(fruit.pickFruit(ItemStack.EMPTY));
			}
		}
	}

/* TODO: take care of this now that there's no postPick
	@Override
	public void postPick(World world, BlockPos pos)
	{
		TileEntity te = world.getTileEntity(pos);
		if (te instanceof IFruitBearer)
		{
			IFruitBearer fruit = (IFruitBearer)te;
			fruit.addRipeness(-fruit.getRipeness());
		}
	}
*/
}
