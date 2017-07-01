package powercrystals.minefactoryreloaded.setup.recipe.handler;

import net.minecraft.inventory.InventoryCrafting;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.ShapelessRecipes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.world.World;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.RecipeSorter;
import powercrystals.minefactoryreloaded.core.UtilInventory;
import powercrystals.minefactoryreloaded.setup.Machine;

import javax.annotation.Nonnull;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public abstract class ShapelessMachineTinker extends ShapelessRecipes
{
	protected List<List<ItemStack>> _tinkerItems;
	@Nonnull
	protected ItemStack _machine;
	
	private static @Nonnull ItemStack createMachineWithLore(Machine machine, String lore)
	{
		@Nonnull ItemStack o = machine.getItemStack();
		NBTTagCompound tag = new NBTTagCompound();
		tag.setTag("display", new NBTTagCompound());
		NBTTagList list = new NBTTagList();
		tag.getCompoundTag("display").setTag("Lore", list);
		list.appendTag(new NBTTagString(lore));
		o.setTagCompound(tag);
		return o;
	}
	
	private static List<ItemStack> createIngredientListforNEI(Machine machine, @Nonnull ItemStack... items)
	{
		List<ItemStack> r = new LinkedList<>();
		r.addAll(Arrays.asList(items));
		r.add(machine.getItemStack());
		return r;
	}

	public ShapelessMachineTinker(Machine machine, String lore, String... tinkerItems)
	{
		super(createMachineWithLore(machine, lore), null);
		_machine = machine.getItemStack();
		_tinkerItems = new LinkedList<>();
		for (String s : tinkerItems)
			_tinkerItems.add(OreDictionary.getOres(s));
		RecipeSorter.register("minefactoryreloaded:shapelessTinker", getClass(), RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
	}

	protected ShapelessMachineTinker(Machine machine, String lore, @Nonnull ItemStack... tinkerItems)
	{
		super(createMachineWithLore(machine, lore), createIngredientListforNEI(machine, tinkerItems));
		_machine = machine.getItemStack();
		_tinkerItems = new LinkedList<>();
		for (@Nonnull ItemStack s : tinkerItems)
		{
			List<ItemStack> l = new LinkedList<>();
			l.add(s);
			_tinkerItems.add(l);
		}
		RecipeSorter.register("minefactoryreloaded:shapelessTinker", getClass(), RecipeSorter.Category.SHAPELESS, "after:minecraft:shapeless");
	}
	
	protected abstract boolean isMachineTinkerable(@Nonnull ItemStack machine);

	@Nonnull
	protected abstract ItemStack getTinkeredMachine(@Nonnull ItemStack machine);

	@Override
	public boolean matches(InventoryCrafting grid, World world)
	{
		int size = grid.getSizeInventory();
		boolean foundMachine = false;
		
		List<List<ItemStack>> items = new LinkedList<>();
		items.addAll(_tinkerItems);
		
		while (size --> 0)
		{
			@Nonnull ItemStack gridItem = grid.getStackInSlot(size);
			if (gridItem.isEmpty())
				continue;
			
			if (UtilInventory.stacksEqual(_machine, gridItem, false))
				if (foundMachine || !isMachineTinkerable(gridItem))
					return false;
				else
					foundMachine = true;
			else
			lists: {
				if (foundMachine && items.isEmpty())
					return true;
				for (List<ItemStack> l : items)
					for (@Nonnull ItemStack i : l)
						if (UtilInventory.stacksEqual(gridItem, i))
						{
							items.remove(l);
							break lists;
						}
				return false;
			}
		}

		return foundMachine && items.isEmpty();
	}

	@Nonnull
	@Override
	public ItemStack getCraftingResult(InventoryCrafting grid)
	{
		int size = grid.getSizeInventory();
		
		while (size --> 0)
		{
			@Nonnull ItemStack gridItem = grid.getStackInSlot(size);
			if (UtilInventory.stacksEqual(_machine, gridItem, false))
				if (isMachineTinkerable(gridItem))
					return getTinkeredMachine(gridItem);
		}
		
		return ItemStack.EMPTY;
	}
}
