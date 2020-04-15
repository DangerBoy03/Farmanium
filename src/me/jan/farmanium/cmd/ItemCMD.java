package me.jan.farmanium.cmd;

import java.util.ArrayList;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import me.jan.farmanium.Farmanium;
import me.jan.farmanium.manager.ItemManager;

public class ItemCMD implements CommandExecutor {

	@SuppressWarnings("unused")
	private Farmanium plugin;

	public ItemCMD(Farmanium farmanium) {
		this.plugin = farmanium;
	}

	static ArrayList<String> lore = new ArrayList<>();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// item <name> <lore>
		Player p = (Player) sender;
		if (p instanceof Player) {
			if (p.hasPermission("Farmanium.verwalten")) {
				lore.clear();
				if (args.length >= 2) {

					if (p.getItemInHand().getAmount() == 0) {
						p.sendMessage(Farmanium.prefix + "§cHalte ein Item in der Hand");
						return true;
					}

					if (p.getItemInHand().equals(ItemManager.netherstar)) {
						p.sendMessage(Farmanium.prefix + "§cDiese Item darf nicht verändert werden");
						return true;
					}
					
					for (int i = 2; i < args.length; i++) {
						args[1] = args[1] + " " + args[i];
					}

					if (args.length == 2) {
						if (args[0].equalsIgnoreCase("removelore")) {
							removelore(p, args[1]);
							return true;
						}

					}
					args[1] = args[1].replace("&", "§");
					if (args[0].equalsIgnoreCase("rename")) {
						rename(p, args[1]);
					} else if (args[0].equalsIgnoreCase("addlore")) {
						addlore(p, args[1]);
					}
				} else {
					p.sendMessage(Farmanium.prefix + "§c/item <addlore|removelore|rename> <string>");
				}
			}
		}
		return true;
	}

	private static void removelore(Player p, String args) {
		int ilore = 0;
		try {
			ilore = Integer.parseInt(args);
		} catch (NumberFormatException e) {
			p.sendMessage(Farmanium.prefix + "§e" + args + " §7ist ungültig.");
			return;
		}

		if (ilore <= 0) {
			p.sendMessage(Farmanium.prefix + "§e" + args + " §7ist zu klein");
			return;
		}

		ItemStack is = p.getItemInHand();
		ItemMeta im = is.getItemMeta();
		ilore--;
		if (im.getLore() == null) {
			p.sendMessage(Farmanium.prefix + "§cDieses Item hat keine Lore");
			return;
		}
		lore = (ArrayList<String>) im.getLore();
		if (ilore > lore.size()) {
			p.sendMessage(Farmanium.prefix + "§cLore §e" + args + " §cexistier nicht");
			return;
		}
		lore.remove(ilore);
		im.setLore(lore);
		is.setItemMeta(im);
		p.sendMessage(Farmanium.prefix + "§aLore §e" + args + " §aerfolgreich geglöscht");
	}

	private static void rename(Player p, String args) {
		ItemStack is = p.getInventory().getItemInHand();
		ItemMeta im = is.getItemMeta();
		im.setDisplayName(args);
		is.setItemMeta(im);
		p.sendMessage(Farmanium.prefix + "§aItemname: §e" + args);
	}

	private static void addlore(Player p, String args) {
		ItemStack is = p.getItemInHand();
		ItemMeta im = is.getItemMeta();
		if (im.getLore() != null) {
			lore = (ArrayList<String>) im.getLore();
			lore.add(args);
		} else {
			lore.add(args);
		}
		im.setLore(lore);
		is.setItemMeta(im);
		p.sendMessage(Farmanium.prefix + "§aLore erfolgreich hinzugefügt.");
	}
}
