package powercrystals.minefactoryreloaded.farmables.grindables;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

import powercrystals.minefactoryreloaded.api.IFactoryGrindable;
import powercrystals.minefactoryreloaded.api.MobDrop;

public class GrindableEnderman implements IFactoryGrindable
{
	@Override
	public Class<? extends EntityLivingBase> getGrindableEntity()
	{
		return EntityEnderman.class;
	}

	@Override
	public List<MobDrop> grind(World world, EntityLivingBase entity, Random random)
	{
		List<MobDrop> drops = new LinkedList<MobDrop>();
		IBlockState state = ((EntityEnderman)entity).getHeldBlockState();
		Block block = null;
		if (state != null)
			state.getBlock();
		if (block != null && !block.equals(Blocks.AIR))
			drops.add(new MobDrop(10, new ItemStack(block, 1, block.damageDropped(state))));
		return drops;
	}

	@Override
	public boolean processEntity(EntityLivingBase entity)
	{
		return false;
	}

}
