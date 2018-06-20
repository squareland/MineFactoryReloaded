package powercrystals.minefactoryreloaded.modcompat.thermalexpansion;

import cofh.api.util.ThermalExpansionHelper;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.GameRegistry;
import powercrystals.minefactoryreloaded.MFRRegistry;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.api.mob.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.mob.RandomMobProvider;
import powercrystals.minefactoryreloaded.core.MFRUtil;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static powercrystals.minefactoryreloaded.api.integration.IMFRRecipeSet.stack;
import static powercrystals.minefactoryreloaded.modcompat.Compats.ModIds.THERMAL_EXPANSION;
import static powercrystals.minefactoryreloaded.setup.MFRThings.rawRubberItem;
import static powercrystals.minefactoryreloaded.setup.MFRThings.rubberBarItem;

@IMFRIntegrator.DependsOn(THERMAL_EXPANSION)
@IMFRIntegrator.After("Minecraft")
public class ThermalExpansion implements IMFRIntegrator, IRandomMobProvider {

	@GameRegistry.ItemStackHolder(value = "thermalfoundation:material", meta = 771)
	public static final ItemStack SULFUR = ItemStack.EMPTY;

	public void load() {

		MFRRegistry.registerRandomMobProvider(this);

		ThermalExpansionHelper.addSmelterRecipe(2000,
				stack(rawRubberItem, 2), SULFUR,
				stack(rubberBarItem, 4),
				stack(rubberBarItem, 1), 15);

		// Smooth Blackstone -> Cobble
		ThermalExpansionHelper.addPulverizerRecipe(3200,
				new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 0),
				new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 2));
		// Smooth Whitestone -> Cobble
		ThermalExpansionHelper.addPulverizerRecipe(3200,
				new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 1),
				new ItemStack(MFRThings.factoryDecorativeStoneBlock, 1, 3));
	}

	@Override
	public List<RandomMobProvider> getRandomMobs(World w) {

		ArrayList<RandomMobProvider> mobs = new ArrayList<>();

		mobs.add(new RandomMobProvider(20, (world, pos) -> {
			EntityCreeper creeper = MFRUtil.spawnMob(EntityCreeper.class, world, pos);
			Objects.requireNonNull(creeper).setCustomNameTag("Exploding Zeldo");
			creeper.setAlwaysRenderNameTag(true);
			creeper.enablePersistence();
			ItemStack armor = new ItemStack(MFRThings.plasticBootsItem);
			armor.setStackDisplayName("Zeldo's Ruby Slippers");
			EntityEquipmentSlot slot = EntityLiving.getSlotForItemStack(armor);
			creeper.setItemStackToSlot(slot, armor);
			creeper.setDropChance(slot, 2);
			return creeper;
		}));

		return mobs;
	}

}
