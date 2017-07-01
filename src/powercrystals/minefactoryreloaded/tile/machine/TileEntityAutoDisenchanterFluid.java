package powercrystals.minefactoryreloaded.tile.machine;

import cofh.core.fluid.FluidTankCore;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.fluids.FluidStack;
import powercrystals.minefactoryreloaded.setup.MFRFluids;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Locale;

public class TileEntityAutoDisenchanterFluid extends TileEntityAutoDisenchanter {

	public TileEntityAutoDisenchanterFluid() {

		_tanks[0].setLock(MFRFluids.getFluid("mob_essence"));
	}

	@Override
	protected boolean incrementWorkDone() {

		if (drain(4, false, _tanks[0]) != 4)
			return false;
		drain(4, true, _tanks[0]);
		return super.incrementWorkDone();
	}

	@Override
	public String getGuiBackground() {

		if (_machine == null)
			return null;
		return _machine.getName().toLowerCase(Locale.US) + "2";
	}

	@Override
	protected FluidTankCore[] createTanks() {

		return new FluidTankCore[] { new FluidTankCore(4 * BUCKET_VOLUME) };
	}

	@Override
	public boolean allowBucketFill(EnumFacing facing, @Nonnull ItemStack stack) {

		return true;
	}

	@Override
	protected boolean canDrainTank(EnumFacing facing, int index) {

		return false;
	}

	@Nullable
	@Override
	public FluidStack drain(EnumFacing facing, FluidStack resource, boolean doDrain) {

		return null;
	}

	@Nullable
	@Override
	public FluidStack drain(EnumFacing facing, int maxDrain, boolean doDrain) {

		return null;
	}

}
