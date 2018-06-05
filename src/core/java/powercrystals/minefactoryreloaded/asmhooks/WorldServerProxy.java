package powercrystals.minefactoryreloaded.asmhooks;

import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldSettings;

public abstract class WorldServerProxy extends WorldServerShim {

	protected WorldServer proxiedWorld;

	private static String getWorldName(World world) {

		return world.getWorldInfo().getWorldName();
	}

	private static WorldSettings getWorldSettings(World world) {

		return new WorldSettings(world.getWorldInfo());
	}

	public WorldServerProxy(WorldServer world) {

		super(world);
		this.proxiedWorld = world;

		cofh_updateProps();
	}

	protected void cofh_updateProps() {

		cofh_updatePropsInternal(this.proxiedWorld);
	}

}
