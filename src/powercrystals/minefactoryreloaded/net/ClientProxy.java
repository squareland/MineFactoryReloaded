package powercrystals.minefactoryreloaded.net;

import net.minecraft.block.Block;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fluids.FluidRegistry;
import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;
import powercrystals.minefactoryreloaded.MineFactoryReloadedCore;

public class ClientProxy implements IMFRProxy
{
	@Override
	public void init()
	{
		MineFactoryReloadedClient.init();
	}
	
	@Override
	public void movePlayerToCoordinates(EntityPlayer e, double x, double y, double z)
	{
		e.setPosition(x, y, z);
	}
	
	@Override
	@ForgeSubscribe
	public void onPostTextureStitch(TextureStitchEvent.Post e)
	{
		setIcons("milk", MineFactoryReloadedCore.milkLiquid);
		setIcons("sludge", MineFactoryReloadedCore.sludgeLiquid);
		setIcons("sewage", MineFactoryReloadedCore.sewageLiquid);
		setIcons("mobessence", MineFactoryReloadedCore.essenceLiquid);
		setIcons("biofuel", MineFactoryReloadedCore.biofuelLiquid);
		setIcons("meat", MineFactoryReloadedCore.meatLiquid);
		setIcons("pinkslime", MineFactoryReloadedCore.pinkSlimeLiquid);
		setIcons("chocolatemilk", MineFactoryReloadedCore.chocolateMilkLiquid);
		setIcons("mushroomsoup", MineFactoryReloadedCore.mushroomSoupLiquid);
	}
	
	private void setIcons(String name, Block block)
	{
		Fluid fluid = FluidRegistry.getFluid(name);
		if (fluid.getBlockID() == block.blockID)
		{
			fluid.setIcons(block.getIcon(1, 0), block.getIcon(2, 0));
		}
	}
}
