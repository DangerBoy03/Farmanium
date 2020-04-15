package me.jan.farmanium.manager;

import java.io.File;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.jan.farmanium.Farmanium;
import me.jan.farmanium.util.Perk;

public class PlayerManager {

	public static File playerfile = new File("plugins/BetterFarmanium", "player.yml");
	public static FileConfiguration playercfg = YamlConfiguration.loadConfiguration(playerfile);
	
	public static void checklogin(Player p) {

		if(playercfg.get(p.getUniqueId() + ".login") != null) {
			plusone(p.getUniqueId() + ".login");
		}else {
			isnew(p);
		}
		Farmanium.loadplayercfg();
	}
	private static void isnew(Player p) {
		Bukkit.broadcastMessage(Farmanium.prefix + "§e" + p.getName() + " §7ist neu!");
		p.getInventory().setItem(8, ItemManager.netherstar);
		
		UUID uuid = p.getUniqueId();
		playercfg.set(uuid + ".login", 1);
		
		for(Perk perk : Perk.values()) {
			playercfg.set(uuid + ".perk." + perk.toString(), false);
			playercfg.set(uuid + ".perk." + perk.toString() + ".b", false);
			playercfg.set(uuid + ".perk." + perk.toString() + ".s", false);
		}
		
		Farmanium.loadplayercfg();
	}
	
	public static boolean playerexists(Player p) {
		try {
			if(playercfg.get(p.getUniqueId().toString()) != null) {
				return true;
			}
		}catch(NullPointerException e) {
			return false;
		}
		return false;

	}
	
	public static boolean hasoperator(Player p) {
		int i = 1;
		String uuid = p.getUniqueId().toString();
		while (Farmanium.opcfg.get(String.valueOf(i)) != null) {
			if(Farmanium.opcfg.get(String.valueOf(i)).equals(uuid)) return true;
			i++;
		}
		return false;
	}
	
	private static void plusone(String path) {
		int x = playercfg.getInt(path);
		x++;
		playercfg.set(path, x);
		Farmanium.loadplayercfg();
	}
}
