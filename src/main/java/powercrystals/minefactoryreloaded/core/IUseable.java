package powercrystals.minefactoryreloaded.core;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public interface IUseable
{
	RayTraceResult rayTrace(World world, EntityLivingBase entity, boolean adjacent);
}
