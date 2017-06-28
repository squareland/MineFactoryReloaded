package powercrystals.minefactoryreloaded.farmables.drinkhandlers;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityXPOrb;

import net.minecraftforge.fluids.FluidStack;
import powercrystals.minefactoryreloaded.api.ILiquidDrinkHandler;

public class DrinkHandlerMobEssence implements ILiquidDrinkHandler {

	@Override
	public void onDrink(EntityLivingBase player, FluidStack fluid) {

		player.world.spawnEntityInWorld(new EntityXPOrb(player.world,
				player.posX, player.posY, player.posZ,
				player.world.rand.nextInt(6) + 10));
	}

}
