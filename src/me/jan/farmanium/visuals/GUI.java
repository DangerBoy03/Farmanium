package me.jan.farmanium.visuals;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import me.jan.farmanium.Farmanium;
import me.jan.farmanium.manager.ItemManager;
import me.jan.farmanium.manager.PlayerManager;
import me.jan.farmanium.util.HeadMain;
import me.jan.farmanium.util.Perk;

public class GUI {

	public static Inventory perkinv = Bukkit.createInventory(null, 54, Farmanium.perkGUI);
	public static Inventory tpinv = Bukkit.createInventory(null, 54, Farmanium.tpGUI);

	static String[] tploc;
	static int tppages;
	static int tpstartfillonlast;
	static int tpitemsonlastpage;

	public static void createMainGUI(Player p) {
		Inventory maininv = Bukkit.createInventory(null, 54, Farmanium.mainGUI);
		fill(maininv, 0, 54, ItemManager.glass);
		maininv.setItem(10, ItemManager.cosmetics);
		maininv.setItem(16, ItemManager.crafting);
		maininv.setItem(22, ItemManager.teleport);
		maininv.setItem(29, ItemManager.bank);
		maininv.setItem(33, ItemManager.feather);
		maininv.setItem(40, ItemManager.quest);
		maininv.setItem(45, ItemManager.minigames);
		maininv.setItem(53, ItemManager.collection);
		p.openInventory(maininv);
	}

	public static void createTPGUI(Player p) {

		if (!Farmanium.loccfg.isConfigurationSection("loc")) {
			p.sendMessage(Farmanium.prefix + "§cACHTUNG! Noch keine Location gesetzt! /loc");
			return;
		}

		String locs = Farmanium.loccfg.getConfigurationSection("loc").getKeys(false).toString();

		tploc = locs.replace("[", "").replace("]", "").replace(" ", "").split(",");

		int slots = tploc.length;
		tppages = slots / 36 + 1;
		if (slots % 36 == 0)
			tppages--;
		tpitemsonlastpage = slots % 36;
		boolean extrapage = false;
		int startfill = 0;
		if (tppages > 1) {
			slots = 36;
			extrapage = true;
			startfill = 0;
			tpstartfillonlast = tpitemsonlastpage;
		} else {
			startfill = slots;
		}

		normalpage(tpinv, startfill, extrapage);

		for (int i = 0; i < slots; i++) {
			tpinv.setItem(i, Farmanium.loccfg.getItemStack("loc." + tploc[i] + ".item"));
		}

		p.openInventory(tpinv);
	}

	public static void newPageTpGUI(int page) {
		
		fill(tpinv, tpstartfillonlast, 36, ItemManager.glass);
		tpinv.setItem(49, HeadMain.getHead("rufzeichen", "§cSeite " + page));
		tpinv.getItem(49).setAmount(page);

		int lenght;
		
		if(page != tppages) {	
			lenght = 36;
			tpinv.setItem(53, HeadMain.getHead("ra", "§cWeiter"));
		}else {
			tpinv.setItem(53, ItemManager.barrier);	
			lenght = tploc.length-36*(page-1);	
		}
			
		for (int i = 0; i < lenght; i++) {
			int x = i + (page-1)*36;		
			tpinv.setItem(i, Farmanium.loccfg.getItemStack("loc." + tploc[x] + ".item"));
		}
	
	}

	public static void createPerkGUI(Player p, Player target) {
		perkinv = Bukkit.createInventory(null, 54, Farmanium.perkGUI);

		normalpage(perkinv, 0, false);

		int x = 0;
		for (Perk perk : Perk.values()) {
			perkinv.setItem(x, perk.getItemStack());
			x++;
			if (x % 9 == 0)
				x = x + 9;
		}

		int i = 9;
		for (Perk perk : Perk.values()) {
			perkinv.setItem(i, checkperk(target, perk));
			i++;
			if (i % 18 == 0)
				i = i + 9;
		}
		p.openInventory(perkinv);
	}

	public static void createQuestGUI(Player p) {
		Inventory questinv = Bukkit.createInventory(null, 54, Farmanium.questGUI);
		normalpage(questinv, 0, false);
		p.openInventory(questinv);
	}

	private static void fill(Inventory inv, int minslot, int maxslot, ItemStack item) {
		for (int i = minslot; i < maxslot; i++) {
			inv.setItem(i, item);
		}
	}

	private static void normalpage(Inventory inv, int fill, boolean nextpage) {
		fill(inv, fill, 36, ItemManager.glass);
		fill(inv, 36, 45, ItemManager.greenglass);
		inv.setItem(45, HeadMain.getHead("la", "§cZurück"));
		inv.setItem(49, HeadMain.getHead("rufzeichen", "§cSeite 1"));
		if (nextpage) {
			inv.setItem(53, HeadMain.getHead("ra", "§cWeiter"));
			return;
		}
		inv.setItem(53, ItemManager.barrier);
	}

	private static ItemStack checkperk(Player p, Perk perk) {
		if (PlayerManager.playercfg.getBoolean(p.getUniqueId() + ".perk." + perk.toString() + ".b") == false)
			return ItemManager.notbought;
		if (PlayerManager.playercfg.getBoolean(p.getUniqueId() + ".perk." + perk.toString() + ".s"))
			return ItemManager.on;
		return ItemManager.off;
	}

	public static void switchperkstate(int slot, Player p, boolean on, ItemStack is) {

		GUI.perkinv.setItem(slot, is);

		slot = slot - 9;
		ItemStack perk = GUI.perkinv.getItem(slot);
		String perkname = perk.getItemMeta().getDisplayName();
		perkname = perkname.substring(2).toUpperCase();

		PlayerManager.playercfg.set(p.getUniqueId() + ".perk." + perkname + ".s", on);
		Farmanium.loadplayercfg();
	}

	public static int getpage(Inventory inv) {
		String name = inv.getItem(49).getItemMeta().getDisplayName();
		name = ChatColor.stripColor(name);
		name = name.replace("Seite ", "");
		return Integer.parseInt(name);
	}
}
