package powercrystals.minefactoryreloaded.modcompat.buildcraft;

import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;

import static powercrystals.minefactoryreloaded.modcompat.Compats.ModIds.BUILDCRAFT;

@IMFRIntegrator.DependsOn(BUILDCRAFT)
public class Buildcraft implements IMFRIntegrator {

	@Override
	public void load() {

	}

	@Override
	public void postLoad() {

		//if (BuildcraftFuelRegistry.fuel != null)
			//BuildcraftFuelRegistry.fuel.addFuel(MFRFluids.getFluid("biofuel"), 40, 15000);
	}

}
