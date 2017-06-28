package powercrystals.minefactoryreloaded.tile.machine;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumFacing;

import powercrystals.minefactoryreloaded.setup.Machine;
import powercrystals.minefactoryreloaded.tile.base.TileEntityFactory;

public class TileEntityMobCounter extends TileEntityFactory {

	private int _lastMobCount;

	public TileEntityMobCounter() {

		super(Machine.MobCounter);
		createEntityHAM(this);
		setCanRotate(true);
	}

	@Override
	public void update() {

		super.update();

		if (world == null) {
			return;
		}

		int mobCount = world.getEntitiesWithinAABB(EntityLivingBase.class, _areaManager.getHarvestArea().toAxisAlignedBB()).size();
		if (mobCount != _lastMobCount) {
			_lastMobCount = mobCount;
			if (!world.isRemote) {
				world.notifyNeighborsOfStateChange(pos, this._machine.getBlock());
			}
		}
	}

	@Override
	public int getRedNetOutput(EnumFacing side) {

		return _lastMobCount;
	}

}
