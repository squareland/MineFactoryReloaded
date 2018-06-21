package powercrystals.minefactoryreloaded.modcompat.thermalexpansion;

import cofh.api.util.ThermalExpansionHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.api.mob.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.mob.RandomMobProvider;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static powercrystals.minefactoryreloaded.api.integration.IMFRRecipeSet.stack;
import static powercrystals.minefactoryreloaded.modcompat.Compats.ModIds.*;

@IMFRIntegrator.DependsOn(THERMAL_EXPANSION)
@IMFRIntegrator.After("Minecraft")
public class ThermalExpansion implements IMFRIntegrator, IRandomMobProvider {

	@GameRegistry.ItemStackHolder(value = THERMAL_FOUNDATION + ":material", meta = 771)
	public static final ItemStack SULFUR = ItemStack.EMPTY;

	@GameRegistry.ObjectHolder(value = MFR + ":rubber_raw")
	public static final Item rawRubberItem = Items.AIR;
	@GameRegistry.ObjectHolder(value = MFR + ":rubber_bar")
	public static final Item rubberBarItem = Items.AIR;
	@GameRegistry.ObjectHolder(value = MFR + ":decorative_stone")
	public static final Item factoryDecorativeStoneBlock = Items.AIR;
	@GameRegistry.ObjectHolder(value = MFR + ":plastic_boots")
	public static final Item plasticBootsItem = Items.AIR;

	public void load() {

		REGISTRY.registerRandomMobProvider(this);

		ThermalExpansionHelper.addSmelterRecipe(2000,
				stack(rawRubberItem, 2), SULFUR,
				stack(rubberBarItem, 4),
				stack(rubberBarItem, 1), 15);

		// Smooth Blackstone -> Cobble
		ThermalExpansionHelper.addPulverizerRecipe(3200,
				stack(factoryDecorativeStoneBlock, 1, 0),
				stack(factoryDecorativeStoneBlock, 1, 2));
		// Smooth Whitestone -> Cobble
		ThermalExpansionHelper.addPulverizerRecipe(3200,
				stack(factoryDecorativeStoneBlock, 1, 1),
				stack(factoryDecorativeStoneBlock, 1, 3));
	}

	@Override
	public List<RandomMobProvider> getRandomMobs(World w) {

		ArrayList<RandomMobProvider> mobs = new ArrayList<>();

		mobs.add(new RandomMobProvider(20, (world, pos) -> {
			EntityCreeper creeper = IRandomMobProvider.spawnMob(EntityCreeper.class, world, pos);
			Objects.requireNonNull(creeper).setCustomNameTag("Exploding Zeldo");
			creeper.setAlwaysRenderNameTag(true);
			creeper.enablePersistence();
			ItemStack armor = stack(plasticBootsItem);
			armor.setStackDisplayName("Zeldo's Ruby Slippers");
			EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(armor);
			creeper.setItemStackToSlot(slot, armor);
			creeper.setDropChance(slot, 2);
			return creeper;
		}));

		return mobs;
	}

}
