
package powercrystals.minefactoryreloaded.modhelpers.twilightforest;

import net.minecraft.world.World;
import powercrystals.minefactoryreloaded.api.mob.IRandomMobProvider;
import powercrystals.minefactoryreloaded.api.mob.RandomMobProvider;
import powercrystals.minefactoryreloaded.core.MFRUtil;

import java.util.ArrayList;
import java.util.List;

public class TwilightForestMobProvider implements IRandomMobProvider {

	@Override
	@SuppressWarnings("unchecked")
	public List<RandomMobProvider> getRandomMobs(World world) {

		List<RandomMobProvider> mobs = new ArrayList<>();

		try {
			Class tfBoar = Class.forName("twilightforest.entity.passive.EntityTFBoar");
			Class tfDeathTome = Class.forName("twilightforest.entity.EntityTFDeathTome");
			Class tfDeer = Class.forName("twilightforest.entity.passive.EntityTFDeer");
			Class tfMazeSlime = Class.forName("twilightforest.entity.EntityTFMazeSlime");
			Class tfPenguin = Class.forName("twilightforest.entity.passive.EntityTFPenguin");
			Class tfPinchBeetle = Class.forName("twilightforest.entity.EntityTFPinchBeetle");
			Class tfRaven = Class.forName("twilightforest.entity.passive.EntityTFRaven");
			Class tfSquirrel = Class.forName("twilightforest.entity.passive.EntityTFSquirrel");
			Class tfTinyBird = Class.forName("twilightforest.entity.passive.EntityTFTinyBird");
			Class tfWraith = Class.forName("twilightforest.entity.EntityTFWraith");

			mobs.add(new RandomMobProvider(80, MFRUtil.prepareMob(tfBoar)));
			mobs.add(new RandomMobProvider(80, MFRUtil.prepareMob(tfDeer)));
			mobs.add(new RandomMobProvider(50, MFRUtil.prepareMob(tfRaven)));
			mobs.add(new RandomMobProvider(25, MFRUtil.prepareMob(tfPenguin)));
			mobs.add(new RandomMobProvider(25, MFRUtil.prepareMob(tfSquirrel)));
			mobs.add(new RandomMobProvider(75, MFRUtil.prepareMob(tfTinyBird)));
			mobs.add(new RandomMobProvider(15, MFRUtil.prepareMob(tfMazeSlime)));
			mobs.add(new RandomMobProvider(15, MFRUtil.prepareMob(tfPinchBeetle)));
			mobs.add(new RandomMobProvider(5, MFRUtil.prepareMob(tfWraith)));
			mobs.add(new RandomMobProvider(10, MFRUtil.prepareMob(tfDeathTome)));

		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		}
		return mobs;
	}

}
