package powercrystals.minefactoryreloaded.modcompat.forgemultiparts;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import powercrystals.minefactoryreloaded.api.integration.IMFRIntegrator;
import powercrystals.minefactoryreloaded.block.ItemBlockFactory;
import powercrystals.minefactoryreloaded.gui.MFRCreativeTab;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import javax.annotation.Nonnull;

import static powercrystals.minefactoryreloaded.modcompat.Compats.ModIds.MULTIPARTCBE;

@IMFRIntegrator.DependsOn(MULTIPARTCBE)
public class MultipartCBE implements IMFRIntegrator {

	public void load() {

		addSubtypes((ItemBlockFactory) Item.getItemFromBlock(MFRThings.factoryDecorativeBrickBlock));
		addSubtypes((ItemBlockFactory) Item.getItemFromBlock(MFRThings.factoryDecorativeStoneBlock));
		addSubtypes((ItemBlockFactory) Item.getItemFromBlock(MFRThings.factoryGlassBlock));
		addSubtypes((ItemBlockFactory) Item.getItemFromBlock(MFRThings.rubberLeavesBlock));
		addSubtypes((ItemBlockFactory) Item.getItemFromBlock(MFRThings.factoryRoadBlock));
		for (Block block : MFRThings.machineBlocks.valueCollection())
			addSubtypes((ItemBlockFactory) Item.getItemFromBlock(block));
		sendComm(new ItemStack(MFRThings.rubberWoodBlock, 1, 0));
	}

	private void addSubtypes(ItemBlockFactory item) {

		NonNullList<ItemStack> items = NonNullList.create();
		item.getSubItems(MFRCreativeTab.tab, items);
		for (int i = items.size(); i-- > 0; )
			sendComm(items.get(i));
	}

	private void sendComm(@Nonnull ItemStack data) {

		FMLInterModComms.sendMessage(MULTIPARTCBE, "microMaterial", data);
	}

}
