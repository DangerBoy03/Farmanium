package me.jan.farmanium.cmd;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jan.farmanium.Farmanium;
import me.jan.farmanium.manager.PlayerManager;
import me.jan.farmanium.util.Perk;
import me.jan.farmanium.visuals.GUI;

public class PerkCMD implements CommandExecutor {

	@SuppressWarnings("unused")
	private Farmanium plugin;

	public PerkCMD(Farmanium farmanium) {
		this.plugin = farmanium;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// perks set <player> <perk>
		Player p = (Player) sender;
		if (p instanceof Player) {
			if (p.hasPermission("Farmanium.verwalten")) {			
				if (args.length == 2) {
					Player target = Bukkit.getPlayer(args[1]);
					if (!PlayerManager.playerexists(target)) {
						p.sendMessage(Farmanium.prefix + "Der Spieler §e" + args[1] + " §7muss online sein");
						return true;
					}
					if (!args[0].equalsIgnoreCase("list")) {
						p.sendMessage(Farmanium.prefix + "§c/perks <set|reset|list> <player [<perk>]");
						return true;
					}
					listperks(p, target);
				} else if (args.length == 3) {
					Player target = Bukkit.getPlayer(args[1]);
					args[2] = args[2].toUpperCase();				
					if (PlayerManager.playerexists(target)) {
						try {
							if (args[0].equalsIgnoreCase("set")) {
								if(args[2].equalsIgnoreCase("*")) {
									allperks(p, target, true);
									return true;
								}
								setperk(p, target, Perk.valueOf(args[2]));
							} else if (args[0].equalsIgnoreCase("reset")) {
								if(args[2].equalsIgnoreCase("*")) {
									allperks(p, target, false);
									return true;
								}
								resetperk(p, target, Perk.valueOf(args[2]));
							}
						} catch (IllegalArgumentException e) {
							p.sendMessage(Farmanium.prefix + getperks());
						}
					} else {
						p.sendMessage(Farmanium.prefix + "Der Spieler §e" + args[1] + " §7muss online sein");
					}
				} else {
					p.sendMessage(Farmanium.prefix + "§c/perks <set|reset|list> <player [<perk>]");
				}
			} else {
				p.sendMessage(Farmanium.prefix + Farmanium.noright);
			}
		}
		return true;
	}

	private static void setperk(Player p, Player target, Perk Perk) {
		if(PlayerManager.playercfg.getBoolean(target.getUniqueId() + ".perk." + Perk.toString() + ".b")) {
			p.sendMessage(Farmanium.prefix + "Der Spieler §e" + target.getName() + " �7hat dieses Perk bereits");
			return;
		}
		PlayerManager.playercfg.set(target.getUniqueId() + ".perk." + Perk.toString() + ".b", true);
		p.sendMessage(Farmanium.prefix + "§aDer Spieler §e" + target.getName() + " �ahat das Perk erhalten");
			
	}
	
	private static void allperks(Player p, Player target, boolean setall) {
		for(Perk perk : Perk.values()) {
			PlayerManager.playercfg.set(target.getUniqueId() + ".perk." + perk.toString() + ".b", setall);
			PlayerManager.playercfg.set(target.getUniqueId() + ".perk." + perk.toString() + ".s", setall);
		}
		if(setall) {
			p.sendMessage(Farmanium.prefix + "§aDu hast §e" + target.getName() + " §aalle Perks gegeben");
			return;
		}
		p.sendMessage(Farmanium.prefix + "§cDu hast §e" + target.getName() + " §calle Perks entzogen");		
	}

	private static void resetperk(Player p, Player target, Perk Perk) {
		if(!PlayerManager.playercfg.getBoolean(target.getUniqueId() + ".perk." + Perk.toString() + ".b")) {
			p.sendMessage(Farmanium.prefix + "§cDer Spieler §e" + target.getName() + " §chat dieses Perk nicht");
			return;
		}
		PlayerManager.playercfg.set(target.getUniqueId() + ".perk." + Perk.toString() + ".b", false);
		PlayerManager.playercfg.set(target.getUniqueId() + ".perk." + Perk.toString() + ".s", false);
		p.sendMessage(Farmanium.prefix + "§cDu hast §e" + target.getName() + " §cdas Perk entzogen");
	}

	private static void listperks(Player p, Player target) {
		GUI.createPerkGUI(p, target);
	}

	private static String getperks() {
		String perks = "";
		for (Perk p : Perk.values()) {
			perks = perks + p + ", ";
		}
		perks = perks.substring(0, perks.length()-2);
		return perks;
	}
}
