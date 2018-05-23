package powercrystals.minefactoryreloaded;

import gnu.trove.map.hash.TObjectIntHashMap;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.WeightedRandom;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizable;
import powercrystals.minefactoryreloaded.api.IFactoryFertilizer;
import powercrystals.minefactoryreloaded.api.IFactoryFruit;
import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.api.IFactoryHarvestable;
import powercrystals.minefactoryreloaded.api.IFactoryPlantable;
import powercrystals.minefactoryreloaded.api.IFactoryRanchable;
import powercrystals.minefactoryreloaded.api.ILiquidDrinkHandler;
import powercrystals.minefactoryreloaded.api.IMobEggHandler;
import powercrystals.minefactoryreloaded.api.IMobSpawnHandler;
import powercrystals.minefactoryreloaded.api.INeedleAmmo;
import powercrystals.minefactoryreloaded.api.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.ISafariNetHandler;
import powercrystals.minefactoryreloaded.api.rednet.IRedNetLogicCircuit;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.core.UtilInventory;
import powercrystals.minefactoryreloaded.core.WeightedRandomItemStack;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;

public abstract class MFRRegistry {

	private static Map<Item, IFactoryPlantable> _plantables = new HashMap<Item, IFactoryPlantable>();

	private static Map<Block, IFactoryHarvestable> _harvestables = new HashMap<Block, IFactoryHarvestable>();

	private static Map<Item, IFactoryFertilizer> _fertilizers = new HashMap<Item, IFactoryFertilizer>();

	private static Map<Block, IFactoryFertilizable> _fertilizables = new HashMap<Block, IFactoryFertilizable>();

	private static Map<Class<? extends EntityLivingBase>, IFactoryRanchable> _ranchables =
			new HashMap<Class<? extends EntityLivingBase>, IFactoryRanchable>();

	private static Map<String, ILiquidDrinkHandler> _liquidDrinkHandlers =
			new HashMap<String, ILiquidDrinkHandler>();

	private static Map<Item, INeedleAmmo> _needleAmmoTypes = new HashMap<Item, INeedleAmmo>();

	private static List<Block> _fruitLogBlocks = new ArrayList<Block>();
	private static Map<Block, IFactoryFruit> _fruitBlocks = new HashMap<Block, IFactoryFruit>();

	private static List<WeightedRandom.Item> _sludgeDrops = new ArrayList<WeightedRandom.Item>();

	private static List<String> _rubberTreeBiomes = new ArrayList<String>();

	private static List<IRedNetLogicCircuit> _redNetLogicCircuits = new ArrayList<IRedNetLogicCircuit>();

	private static Map<Class<? extends EntityLivingBase>, IFactoryGrindable> _grindables =
			new HashMap<Class<? extends EntityLivingBase>, IFactoryGrindable>();
	private static List<Class<?>> _grindableBlacklist = new ArrayList<Class<?>>();

	private static List<Class<?>> _safariNetBlacklist = new ArrayList<Class<?>>();
	private static List<IMobEggHandler> _eggHandlers = new ArrayList<IMobEggHandler>();
	private static List<ISafariNetHandler> _safariNetHandlers = new ArrayList<ISafariNetHandler>();
	private static List<IRandomMobProvider> _randomMobProviders = new ArrayList<IRandomMobProvider>();

	private static Map<Class<? extends EntityLivingBase>, IMobSpawnHandler> _spawnHandlers =
			new HashMap<Class<? extends EntityLivingBase>, IMobSpawnHandler>();
	private static List<String> _autoSpawnerBlacklist = new ArrayList<String>();
	private static List<Class<?>> _autoSpawnerClassBlacklist = new ArrayList<Class<?>>();
	private static TObjectIntHashMap<String> _autoSpawnerCostMap = new TObjectIntHashMap<String>(10, 0.5f, 0);

	private static List<Class<?>> _slaughterhouseBlacklist = new ArrayList<Class<?>>();

	private static List<Class<? extends Entity>> _conveyerBlacklist =
			new ArrayList<Class<? extends Entity>>();

	private static Map<String, Boolean> _unifierBlacklist = new TreeMap<String, Boolean>();

	private static List<WeightedRandom.Item> _laserOres = new ArrayList<WeightedRandom.Item>();
	private static Map<Integer, List<ItemStack>> _laserPreferredOres = new HashMap<Integer, List<ItemStack>>(16);

	public static void registerPlantable(IFactoryPlantable plantable) {

		_plantables.put(plantable.getSeed(), plantable);
	}

	public static Map<Item, IFactoryPlantable> getPlantables() {

		return _plantables;
	}

	public static void registerHarvestable(IFactoryHarvestable harvestable) {

		_harvestables.put(harvestable.getPlant(), harvestable);
	}

	public static Map<Block, IFactoryHarvestable> getHarvestables() {

		return _harvestables;
	}

	public static void registerFertilizable(IFactoryFertilizable fertilizable) {

		_fertilizables.put(fertilizable.getPlant(), fertilizable);
	}

	public static Map<Block, IFactoryFertilizable> getFertilizables() {

		return _fertilizables;
	}

	public static void registerFertilizer(IFactoryFertilizer fertilizer) {

		_fertilizers.put(fertilizer.getFertilizer(), fertilizer);
	}

	public static Map<Item, IFactoryFertilizer> getFertilizers() {

		return _fertilizers;
	}

	public static void registerRanchable(IFactoryRanchable ranchable) {

		_ranchables.put(ranchable.getRanchableEntity(), ranchable);
	}

	public static Map<Class<? extends EntityLivingBase>, IFactoryRanchable> getRanchables() {

		return _ranchables;
	}

	public static void registerGrindable(IFactoryGrindable grindable) {

		_grindables.put(grindable.getGrindableEntity(), grindable);
	}

	public static Map<Class<? extends EntityLivingBase>, IFactoryGrindable> getGrindables() {

		return _grindables;
	}

	public static void registerGrinderBlacklist(Class<?> ungrindable) {

		_grindableBlacklist.add(ungrindable);
		if (MFRRegistry._safariNetBlacklist.contains(ungrindable))
			_slaughterhouseBlacklist.add(ungrindable);
	}

	public static List<Class<?>> getGrinderBlacklist() {

		return _grindableBlacklist;
	}

	public static List<Class<?>> getSlaughterhouseBlacklist() {

		return _slaughterhouseBlacklist;
	}

	public static void registerSludgeDrop(int weight, @Nonnull ItemStack drop) {

		_sludgeDrops.add(new WeightedRandomItemStack(drop.copy(), weight));
	}

	public static List<WeightedRandom.Item> getSludgeDrops() {

		return _sludgeDrops;
	}

	public static void registerMobEggHandler(IMobEggHandler handler) {

		_eggHandlers.add(handler);
	}

	public static List<IMobEggHandler> getModMobEggHandlers() {

		return _eggHandlers;
	}

	public static void registerSafariNetHandler(ISafariNetHandler handler) {

		_safariNetHandlers.add(handler);
	}

	public static List<ISafariNetHandler> getSafariNetHandlers() {

		return _safariNetHandlers;
	}

	public static void registerRubberTreeBiome(String biome) {

		_rubberTreeBiomes.add(biome);
	}

	public static List<String> getRubberTreeBiomes() {

		return _rubberTreeBiomes;
	}

	public static void registerSafariNetBlacklist(Class<?> entityClass) {

		_safariNetBlacklist.add(entityClass);
		if (MFRRegistry._grindableBlacklist.contains(entityClass))
			_slaughterhouseBlacklist.add(entityClass);
	}

	public static List<Class<?>> getSafariNetBlacklist() {

		return _safariNetBlacklist;
	}

	public static void registerRandomMobProvider(IRandomMobProvider mobProvider) {

		_randomMobProviders.add(mobProvider);
	}

	public static List<IRandomMobProvider> getRandomMobProviders() {

		return _randomMobProviders;
	}

	public static void registerLiquidDrinkHandler(String liquidId, ILiquidDrinkHandler liquidDrinkHandler) {

		_liquidDrinkHandlers.put(liquidId, liquidDrinkHandler);
	}

	public static Map<String, ILiquidDrinkHandler> getLiquidDrinkHandlers() {

		return _liquidDrinkHandlers;
	}

	public static void registerRedNetLogicCircuit(IRedNetLogicCircuit circuit) {

		_redNetLogicCircuits.add(circuit);
	}

	public static List<IRedNetLogicCircuit> getRedNetLogicCircuits() {

		return _redNetLogicCircuits;
	}

	public static void registerLaserOre(int weight, @Nonnull ItemStack ore) {

		for (WeightedRandom.Item item : _laserOres)
			if (UtilInventory.stacksEqual(((WeightedRandomItemStack) item).getStack(), ore)) {
				item.itemWeight += weight;
				item.itemWeight /= 2;
				return;
			}
		_laserOres.add(new WeightedRandomItemStack(ore.copy(), weight));
	}

	public static List<WeightedRandom.Item> getLaserOres() {

		return _laserOres;
	}

	public static void registerFruitLogBlock(Block fruitLogBlock) {

		_fruitLogBlocks.add(fruitLogBlock);
	}

	public static List<Block> getFruitLogBlocks() {

		return _fruitLogBlocks;
	}

	public static void registerFruit(IFactoryFruit fruit) {

		_fruitBlocks.put(fruit.getPlant(), fruit);
	}

	public static Map<Block, IFactoryFruit> getFruits() {

		return _fruitBlocks;
	}

	public static void registerAutoSpawnerBlacklistClass(Class<? extends EntityLivingBase> entityClass) {

		_autoSpawnerClassBlacklist.add(entityClass);
	}

	public static List<Class<?>> getAutoSpawnerClassBlacklist() {

		return _autoSpawnerClassBlacklist;
	}

	public static void registerAutoSpawnerBlacklist(String entityString) {

		_autoSpawnerBlacklist.add(entityString);
	}

	public static List<String> getAutoSpawnerBlacklist() {

		return _autoSpawnerBlacklist;
	}

	public static void registerSpawnHandler(IMobSpawnHandler spawnHandler) {

		_spawnHandlers.put(spawnHandler.getMobClass(), spawnHandler);
	}

	public static Map<Class<? extends EntityLivingBase>, IMobSpawnHandler> getSpawnHandlers() {

		return _spawnHandlers;
	}

	public static void setBaseSpawnCost(String id, int cost) {

		_autoSpawnerCostMap.put(id, cost);
	}

	public static int getBaseSpawnCost(String id) {

		return _autoSpawnerCostMap.get(id);
	}

	public static void registerUnifierBlacklist(String string) {

		_unifierBlacklist.put(string, null);
	}

	public static Map<String, Boolean> getUnifierBlacklist() {

		return _unifierBlacklist;
	}

	public static void registerConveyerBlacklist(Class<? extends Entity> entityClass) {

		_conveyerBlacklist.add(entityClass);
	}

	public static List<Class<? extends Entity>> getConveyerBlacklist() {

		return _conveyerBlacklist;
	}

	public static void addLaserPreferredOre(int color, @Nonnull ItemStack ore) {

		if (color < 0 || 16 <= color) return;

		List<ItemStack> oresForColor = _laserPreferredOres.get(color);

		if (oresForColor == null) {
			NonNullList<ItemStack> oresList = NonNullList.create();
			oresList.add(ore);
			_laserPreferredOres.put(color, oresList);
		} else {
			for (@Nonnull ItemStack registeredOre : oresForColor) {
				if (UtilInventory.stacksEqual(registeredOre, ore)) {
					return;
				}
			}
			oresForColor.add(ore);
		}
	}

	public static List<ItemStack> getLaserPreferredOres(int color) {

		return _laserPreferredOres.get(color);
	}

	public static void registerNeedleAmmoType(Item item, INeedleAmmo ammo) {

		_needleAmmoTypes.put(item, ammo);
	}

	public static Map<Item, INeedleAmmo> getNeedleAmmoTypes() {

		return _needleAmmoTypes;
	}

	// INTERNAL ONLY

	private static Map<String, String> remaps = new HashMap<String, String>();
	private static Map<String, Block> blocks = new HashMap<String, Block>();
	private static Map<String, Item> items = new HashMap<String, Item>();
	static {
		//This is where remaps should go if needed in the future
		//remaps.put("tile.mfr.decorativebrick", "brick");
	}

	private static String remapPhrase(String s) {

		if (!remaps.containsKey(s))
			return s;
		return remaps.get(s);
	}

	private static String remapName(String s) {

		String name = remaps.get(s);

		if (name == null) {
			String[] v = s.split("\\.");
			if (v.length < 3)
				return name;
			name = remapPhrase(v[2]);
			if (name == null)
				name = "";
			else
				name = "." + name;

			for (int i = 3, e = v.length; i < e; ++i)
				v[i - 2] = remapPhrase(v[i]);
			for (int i = 1, e = v.length - 2; i < e; ++i)
				if (v[i] != null)
					name += '.' + v[i];

			name = name.substring(1);
		}

		return name;
	}

	static Block remapBlock(String id) {

		Block block = blocks.get(id);
		if (block == null) {
			id = remapName(id);
			if (id != null)
				block = MFRUtil.findBlock(MineFactoryReloadedCore.modId, id);
		}

		return block;
	}

	static Item remapItem(String id) {

		Item item = items.get(id);
		if (item == null) {
			id = remapName(id);
			if (id != null)
				item = MFRUtil.findItem(MineFactoryReloadedCore.modId, id);
		}
		return item;
	}

	public static void registerBlock(Block block, ItemBlock itemBlock) {

		//TODO this should get refactored into block registry event
		String name = block.getRegistryName().getResourcePath();
		blocks.put(name, block);

		ForgeRegistries.BLOCKS.register(block);
		if (itemBlock != null) {
			ForgeRegistries.ITEMS.register(itemBlock.setRegistryName(block.getRegistryName()));
			items.put(name, Item.getItemFromBlock(block));
		}
	}
	
	public static void registerItem(Item item) {

		//TODO this should get refactored into item registry event
		items.put(item.getRegistryName().getResourcePath(), item);

		ForgeRegistries.ITEMS.register(item);
	}
}
