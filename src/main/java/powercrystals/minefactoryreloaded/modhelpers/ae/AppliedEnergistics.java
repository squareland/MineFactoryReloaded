package powercrystals.minefactoryreloaded.modhelpers.ae;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.setup.Machine;

import static powercrystals.minefactoryreloaded.modhelpers.Compats.ModIds.APP_ENG;

@IMFRIntegrator.DependsOn(APP_ENG)
public class AppliedEnergistics implements IMFRIntegrator {

	@Override
	public void load() {

		FMLInterModComms.sendMessage(APP_ENG, "whitelist-spatial",
			"powercrystals.minefactoryreloaded.tile.base.TileEntityBase");

		FMLInterModComms.sendMessage(APP_ENG, "add-p2p-attunement-redstone",
			new ItemStack(MFRThings.rednetCableBlock, 1, 0));
		FMLInterModComms.sendMessage(APP_ENG, "add-p2p-attunement-redstone",
			new ItemStack(MFRThings.rednetCableBlock, 1, 1));
		FMLInterModComms.sendMessage(APP_ENG, "add-p2p-attunement-rf-power",
			new ItemStack(MFRThings.rednetCableBlock, 1, 2));
		FMLInterModComms.sendMessage(APP_ENG, "add-p2p-attunement-rf-power",
			new ItemStack(MFRThings.rednetCableBlock, 1, 3));
		FMLInterModComms.sendMessage(APP_ENG, "add-p2p-attunement-fluid",
			new ItemStack(MFRThings.plasticPipeBlock, 1, 0));
		FMLInterModComms.sendMessage(APP_ENG, "add-p2p-attunement-fluid",
			Machine.LiquidRouter.getItemStack());
		FMLInterModComms.sendMessage(APP_ENG, "add-p2p-attunement-item",
			Machine.ItemRouter.getItemStack());
		for (int i = 17; i-- > 0;)
			FMLInterModComms.sendMessage(APP_ENG, "add-p2p-attunement-item",
				new ItemStack(MFRThings.conveyorBlock, 1, i));
	}

}
