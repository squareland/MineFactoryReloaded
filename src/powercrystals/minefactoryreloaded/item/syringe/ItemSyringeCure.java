package powercrystals.minefactoryreloaded.item.syringe;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityZombieVillager;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.ReflectionHelper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;
import powercrystals.minefactoryreloaded.render.ModelHelper;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nonnull;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class ItemSyringeCure extends ItemSyringe {

	public ItemSyringeCure() {

		setUnlocalizedName("mfr.syringe.cure");
		setContainerItem(MFRThings.syringeEmptyItem);
		setRegistryName(MineFactoryReloadedCore.modId, "syringe_cure");
	}

	@Override
	public boolean canInject(World world, EntityLivingBase entity, @Nonnull ItemStack syringe) {

		return entity instanceof EntityZombieVillager;
	}

	@Override
	public boolean inject(World world, EntityLivingBase entity, @Nonnull ItemStack syringe) {

		startConverting((EntityZombieVillager) entity, 300);
		return true;
	}

	private static final Method START_CONVERTING = ReflectionHelper.findMethod(EntityZombieVillager.class, "startConverting", "func_191991_a", Integer.class);
	private void startConverting(EntityZombieVillager zombieVillager, int conversionTime) {

		try {
			START_CONVERTING.invoke(zombieVillager, conversionTime);
		} catch (IllegalAccessException | InvocationTargetException e) {
			MineFactoryReloadedCore.log().error("Zombie villager conversion failed \n", e);
		}
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerModels() {

		ModelHelper.registerModel(MFRThings.syringeCureItem, "syringe", "variant=cure");
	}
}
