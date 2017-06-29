package powercrystals.minefactoryreloaded.world;

import java.util.ArrayList;

import cofh.asmhooks.world.WorldServerProxy;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.item.EntityXPOrb;
import net.minecraft.item.ItemStack;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.WorldServer;

import powercrystals.minefactoryreloaded.tile.base.TileEntityFactoryPowered;
import powercrystals.minefactoryreloaded.tile.machine.TileEntityGrinder;

public class GrindingWorldServer extends WorldServerProxy {

	protected TileEntityFactoryPowered grinder;
	protected boolean allowSpawns;
	protected ArrayList<Entity> entitiesToGrind = new ArrayList<Entity>();

	public GrindingWorldServer(WorldServer world, TileEntityFactoryPowered grinder) {

		this(world, grinder, false);
	}

	public GrindingWorldServer(WorldServer world, TileEntityFactoryPowered grinder, boolean allowSpawns) {

		super(world);
		this.grinder = grinder;
		this.allowSpawns = allowSpawns;
	}

	public void setAllowSpawns(boolean allow) {

		this.allowSpawns = allow;
	}

	public void setMachine(TileEntityFactoryPowered machine) {

		this.grinder = machine;
	}

	@Override
	public boolean spawnEntity(Entity entity) {

		if (grinder != null) {
			if (entity instanceof EntityItem) {
				if (grinder.manageSolids()) {
					ItemStack drop = ((EntityItem) entity).getEntityItem();
					if (drop != null)
						grinder.doDrop(drop);
				}
				entity.setDead();
				return true;
			} else if (entity instanceof EntityXPOrb) {
				EntityXPOrb orb = (EntityXPOrb) entity;
				if (grinder instanceof TileEntityGrinder) {
					((TileEntityGrinder) grinder).acceptXPOrb(orb);
				}
				entity.setDead();
				return true; // consume any orbs not made into essence
			}
		}

		if (allowSpawns) {
			entity.world = this.proxiedWorld;
			return super.spawnEntity(entity);
		}
		entity.setDead();
		return true;
	}

	public EnumDifficulty getDifficulty() {

		return super.getDifficulty() == EnumDifficulty.PEACEFUL ? EnumDifficulty.EASY : super.getDifficulty();
	}

	public boolean addEntityForGrinding(Entity entity) {

		cofh_updateProps();
		if (entity.world == this)
			return true;
		if (entity.world == this.proxiedWorld) {
			entity.world = this;
			entitiesToGrind.add(entity);
			return true;
		}
		return false;
	}

	public void clearReferences() {

		for (Entity ent : entitiesToGrind) {
			if (ent.world == this)
				ent.world = this.proxiedWorld;
		}
		entitiesToGrind.clear();
	}

	public void cleanReferences() {

		for (int i = entitiesToGrind.size(); i-- > 0; ) {
			Entity ent = entitiesToGrind.get(i);
			if (ent.isDead)
				entitiesToGrind.remove(ent);
		}
	}

}
