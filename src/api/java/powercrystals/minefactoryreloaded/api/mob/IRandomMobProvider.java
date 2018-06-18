package powercrystals.minefactoryreloaded.api.mob;

import net.minecraft.world.World;

import java.util.List;

public interface IRandomMobProvider {

	/**
	 * Called to provide random entities to be spawned by mystery SafariNets
	 *
	 * @param world
	 *            The world object the entities will be spawned in.
	 * @return A list of RandomMobProvider instances of entities that are all ready to
	 *         be spawned in the world with no additional method calls.
	 */
	// TODO: docs
	List<RandomMobProvider> getRandomMobs(World world);

}
