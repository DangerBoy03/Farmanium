package me.jan.farmanium.listener;

import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;

import me.jan.farmanium.Farmanium;
import me.jan.farmanium.cmd.SetLocationCMD;
import me.jan.farmanium.manager.ItemManager;
import me.jan.farmanium.util.HeadMain;
import me.jan.farmanium.visuals.GUI;

public class GUIListener implements Listener {

	@EventHandler
	public void on(InventoryClickEvent e) {
		Player p = (Player) e.getWhoClicked();
		if (e.getClickedInventory() == null)
			return;
		if (e.getClickedInventory().getTitle() == null)
			return;
		if (e.getSlot() == -999)
			return;
		if (e.getClickedInventory().getItem(e.getSlot()) == null)
			return;
		ItemStack cItem = e.getClickedInventory().getItem(e.getSlot());

		if (e.getInventory().getTitle() == Farmanium.mainGUI) {
			e.setCancelled(true);
			if (cItem.isSimilar(ItemManager.teleport)) {
				GUI.createTPGUI(p);
			} else if (cItem.isSimilar(ItemManager.feather)) {
				GUI.createPerkGUI(p, p);
			} else if (cItem.isSimilar(ItemManager.crafting)) {
				p.openWorkbench(p.getLocation(), true);
			}
		} else if (e.getInventory().getTitle() == Farmanium.tpGUI) {
			e.setCancelled(true);
			if (cItem.isSimilar(HeadMain.getHead("la", "§cZurück"))) {
				if (GUI.getpage(GUI.tpinv) == 1) {
					GUI.createMainGUI(p);
					return;
				}
				int page = GUI.getpage(GUI.tpinv);
				page--;
				GUI.newPageTpGUI(page);
				return;
			} else if (cItem.isSimilar(HeadMain.getHead("ra", "§cWeiter"))) {
				int page = GUI.getpage(GUI.tpinv);
				page++;
				GUI.newPageTpGUI(page);
				return;
			} else if (cItem.isSimilar(ItemManager.glass) | cItem.isSimilar(ItemManager.greenglass)
					| cItem.isSimilar(ItemManager.barrier)	| e.getSlot() == 49) {
				return;
			}
			String name = cItem.getItemMeta().getDisplayName();
			name = ChatColor.stripColor(name);
			name = name.replace(" ", "_");

			Location loc = SetLocationCMD.buildlocation("loc." + name);
			if (loc == null) {
				p.sendMessage(Farmanium.prefix + "§cFehler! Location nicht gesetzt.");
				return;
			}

			p.sendMessage(Farmanium.prefix + "Teleport erfolgreich");
			p.teleport(loc);

		} else if (e.getInventory().getTitle() == Farmanium.perkGUI) {
			e.setCancelled(true);
			if (cItem.isSimilar(HeadMain.getHead("la", "§cZurück"))) {
				GUI.createMainGUI(p);
				return;
			} else if (cItem.isSimilar(ItemManager.on)) {
				GUI.switchperkstate(e.getSlot(), p, false, ItemManager.off);
			} else if (cItem.isSimilar(ItemManager.off)) {
				GUI.switchperkstate(e.getSlot(), p, true, ItemManager.on);
			}
		} else if (e.getInventory().getTitle() == Farmanium.questGUI) {
			e.setCancelled(true);
			if (cItem.isSimilar(HeadMain.getHead("la", "§cZurück"))) {
				GUI.createMainGUI(p);
				return;
			}
		}
	}
}
