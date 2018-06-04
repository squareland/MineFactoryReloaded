package powercrystals.minefactoryreloaded;

import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IFixType;
import net.minecraftforge.common.util.CompoundDataFixer;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import powercrystals.minefactoryreloaded.setup.datafix.FixMFRInventories;
import powercrystals.minefactoryreloaded.setup.datafix.FixSafariNet;
import powercrystals.minefactoryreloaded.setup.datafix.TileIdFix;

public class DataFixer {

	private static final IFixType TYPE = null;

	@SuppressWarnings("deprecated")
	static void init() {

		CompoundDataFixer datafixer = FMLCommonHandler.instance().getDataFixer();
		ModFixs fixer = datafixer.init(MFRProps.MOD_ID, MFRProps.DATA_VERSION);
		fixer.registerFix(FixTypes.BLOCK_ENTITY, new TileIdFix());
		fixer.registerFix(FixTypes.ITEM_INSTANCE, new FixSafariNet());

		// this method name implies something else
		datafixer.registerVanillaWalker(FixTypes.BLOCK_ENTITY, new FixMFRInventories());
		datafixer.registerVanillaWalker(FixTypes.ITEM_INSTANCE, new FixSafariNet());
	}

}
