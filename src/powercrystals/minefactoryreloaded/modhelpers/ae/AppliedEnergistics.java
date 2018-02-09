package powercrystals.minefactoryreloaded.modhelpers.ae;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import powercrystals.minefactoryreloaded.setup.MFRThings;
import powercrystals.minefactoryreloaded.setup.Machine;

/*@ChildMod(parent = MineFactoryReloadedCore.modId, mod = @Mod(modid = "minefactoryreloaded_compatappliedenergistics",
		name = "MFR Compat: Applied Energistics",
		version = MineFactoryReloadedCore.version,
		dependencies = "after:MineFactoryReloaded;after:appliedenergistics2",
		customProperties = @CustomProperty(k = "cofhversion", v = "true")))*/
public class AppliedEnergistics {

	@EventHandler
	public static void load(FMLInitializationEvent e) {

		FMLInterModComms.sendMessage("appliedenergistics2", "whitelist-spatial",
			"powercrystals.minefactoryreloaded.tile.base.TileEntityBase");

		FMLInterModComms.sendMessage("appliedenergistics2", "add-p2p-attunement-redstone",
			new ItemStack(MFRThings.rednetCableBlock, 1, 0));
		FMLInterModComms.sendMessage("appliedenergistics2", "add-p2p-attunement-redstone",
			new ItemStack(MFRThings.rednetCableBlock, 1, 1));
		FMLInterModComms.sendMessage("appliedenergistics2", "add-p2p-attunement-rf-power",
			new ItemStack(MFRThings.rednetCableBlock, 1, 2));
		FMLInterModComms.sendMessage("appliedenergistics2", "add-p2p-attunement-rf-power",
			new ItemStack(MFRThings.rednetCableBlock, 1, 3));
		FMLInterModComms.sendMessage("appliedenergistics2", "add-p2p-attunement-fluid",
			new ItemStack(MFRThings.plasticPipeBlock, 1, 0));
		FMLInterModComms.sendMessage("appliedenergistics2", "add-p2p-attunement-fluid",
			Machine.LiquidRouter.getItemStack());
		FMLInterModComms.sendMessage("appliedenergistics2", "add-p2p-attunement-item",
			Machine.ItemRouter.getItemStack());
		for (int i = 17; i-- > 0;)
			FMLInterModComms.sendMessage("appliedenergistics2", "add-p2p-attunement-item",
				new ItemStack(MFRThings.conveyorBlock, 1, i));
	}

}
