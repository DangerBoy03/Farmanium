package me.jan.farmanium.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jan.farmanium.Farmanium;

public class BroadcastCMD implements CommandExecutor {

	@SuppressWarnings("unused")
	private Farmanium plugin;

	public BroadcastCMD(Farmanium farmanium) {
		this.plugin = farmanium;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {

		if (!sender.hasPermission("Farmanium.verwalten")) {
			sender.sendMessage(Farmanium.prefix + Farmanium.noright);
		}

		if (args.length == 0) {
			sender.sendMessage(Farmanium.prefix + "§c/bc <msg>");
			return true;
		}

		if (args[0].equalsIgnoreCase("player")) {
			if (args.length <= 2) {
				sender.sendMessage(Farmanium.prefix + "§c/bc Player <player> <msg>");
				return true;
			}
			Player target = Bukkit.getPlayer(args[1]);
			if (target == null) {
				sender.sendMessage(Farmanium.prefix + "Der Spieler §e" + args[1] + " §7muss online sein");
				return true;
			}
			broadcast(target, args, 2);
			return true;
		}

		broadcast(null, args, 0);

		return true;
	}

	private static void broadcast(Player p, String[] args, int start) {
		String msg = "";
		for (int i = start; i < args.length; i++) {
			msg = msg + " " + args[i];
		}
		if(start == 0) {
			Bukkit.broadcastMessage(Farmanium.prefix + ChatColor.translateAlternateColorCodes('&', msg).substring(1));
			return;
		}		
		p.sendMessage(Farmanium.prefix + ChatColor.translateAlternateColorCodes('&', msg).substring(1));
	}

}
