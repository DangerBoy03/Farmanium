package me.jan.farmanium.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.event.player.PlayerDropItemEvent;
import org.bukkit.event.player.PlayerInteractEvent;

import me.jan.farmanium.manager.ItemManager;
import me.jan.farmanium.visuals.GUI;

public class NetherStarListener implements Listener {

	@EventHandler
	public void on(InventoryClickEvent e) {
		if (e.getClickedInventory() == null)
			return;
		if (e.getClickedInventory().getTitle() == null)
			return;
		if (e.getSlot() == -999)
			return;
		if (e.getClickedInventory().getItem(e.getSlot()) == null)
			return;
		if (e.getClickedInventory().getType() != InventoryType.PLAYER)
			return;
		if (e.getSlot() != 8)
			return;
		e.setCancelled(true);
	}

	@EventHandler
	public void on(PlayerInteractEvent e) {
		Player p = e.getPlayer();
		if (e.getAction() == Action.RIGHT_CLICK_AIR || e.getAction() == Action.RIGHT_CLICK_BLOCK) {
			if (p.getItemInHand().isSimilar(ItemManager.netherstar)) {
				GUI.createMainGUI(p);
			}
		}
	}

	@EventHandler
	public void on(PlayerDropItemEvent e) {
		if (!e.getItemDrop().getItemStack().equals(ItemManager.netherstar))
			return;
		e.setCancelled(true);
	}

}
