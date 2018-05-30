package powercrystals.minefactoryreloaded;

import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.datafix.IFixType;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import powercrystals.minefactoryreloaded.setup.datafix.TileIdFix;

public class DataFixer {

	private static final IFixType TYPE = null;

	static void init() {
		ModFixs fixer = FMLCommonHandler.instance().getDataFixer().init(MFRProps.MOD_ID, MFRProps.DATA_VERSION);
		fixer.registerFix(FixTypes.BLOCK_ENTITY, new TileIdFix());
	}

}
