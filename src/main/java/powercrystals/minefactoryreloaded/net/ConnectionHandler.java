package powercrystals.minefactoryreloaded.net;

import net.minecraftforge.common.ForgeChunkManager.Ticket;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import powercrystals.minefactoryreloaded.core.IDelayedValidate;
import powercrystals.minefactoryreloaded.setup.MFRThings;

import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashSet;

public class ConnectionHandler {

	public static HashMap<String, Boolean> onlinePlayerMap = new HashMap<String, Boolean>();

	private static LinkedHashSet<IDelayedValidate> nodes = new LinkedHashSet<IDelayedValidate>();
	private static LinkedHashSet<IDelayedValidate> nodesToAdd = new LinkedHashSet<IDelayedValidate>();

	public static void update(IDelayedValidate node) {

		nodesToAdd.add(node);
	}

	@SubscribeEvent
	public void tick(ServerTickEvent evt) {
		// TODO: this needs split up into groups per-world when worlds are threaded
		l:
		if (evt.phase != Phase.START) {
			if (nodesToAdd.isEmpty())
				break l;
			synchronized (nodesToAdd) {
				nodes.addAll(nodesToAdd);
				nodesToAdd.clear();
			}
			for (IDelayedValidate n : nodes)
				if (!n.isNotValid())
					n.firstTick();
			nodes.clear();
		}
	}

	@SubscribeEvent
	public void onPlayerLogin(PlayerLoggedInEvent player) {

		String name = player.player.getName();
		onlinePlayerMap.put(name, Boolean.TRUE);
		Iterator<Ticket> i = CommonProxy.ticketsInLimbo.iterator();
		while (i.hasNext()) {
			Ticket ticket = i.next();
			if (ticket.getPlayerName().equals(name) && CommonProxy.loadTicket(ticket, false))
				i.remove();
		}
	}

	@SubscribeEvent
	public void onPlayerLogout(PlayerLoggedOutEvent player) {

		onlinePlayerMap.remove(player.player.getName());
	}

	@SubscribeEvent
	public void onItemPickUp(ItemPickupEvent evt) {

		if (evt.pickedUp.getItem().getItem() == MFRThings.rubberWoodItem) {
			// TODO: give player a book
			// TODO: advancements? Used to give mine_wood, but that no longer exists
		}
	}
}
