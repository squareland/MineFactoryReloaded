package powercrystals.minefactoryreloaded.net;

import cofh.core.render.IModelRegister;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;

import powercrystals.minefactoryreloaded.MineFactoryReloadedClient;
import powercrystals.minefactoryreloaded.render.IColorRegister;

import java.util.ArrayList;
import java.util.List;

public class ClientProxy extends CommonProxy
{
	private List<IModelRegister> modelRegistry = new ArrayList<>();
	private List<IColorRegister> colorRegistry = new ArrayList<>();

	@Override
	public void addModelRegister(IModelRegister register) {

		modelRegistry.add(register);
	}

	@Override
	public void addColorRegister(IColorRegister register) {
		
		colorRegistry.add(register);
	}

	@Override
	public void preInit() {

		for(IModelRegister register : modelRegistry) {
			register.registerModels();
		}

		MineFactoryReloadedClient.preInit();
	}

	@Override
	public void init()
	{
		super.init();
		
		for(IColorRegister register : colorRegistry) {
			register.registerColorHandlers();
		}
		
		MineFactoryReloadedClient.init();
	}

	@Override
	public EntityPlayer getPlayer() {

		return Minecraft.getMinecraft().player;
	}

	@Override
	public void movePlayerToCoordinates(EntityLivingBase e, double x, double y, double z)
	{
		e.setPositionAndUpdate(x, y, z);
	}
}
