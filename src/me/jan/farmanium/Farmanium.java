package me.jan.farmanium;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import com.google.common.reflect.ClassPath;

import me.jan.farmanium.cmd.BroadcastCMD;
import me.jan.farmanium.cmd.ItemCMD;
import me.jan.farmanium.cmd.OperatorCMD;
import me.jan.farmanium.cmd.PerkCMD;
import me.jan.farmanium.cmd.SetLocationCMD;
import me.jan.farmanium.manager.PlayerManager;

public class Farmanium extends JavaPlugin {

	public static Farmanium plugin;

	public static Location spawn = new Location(Bukkit.getWorld("world"), 0, 0, 0);

	public static String prefix = "§3Farmanium §7§ ";
	
	public static String noright = "§cDu hast diese Permisson nicht";
	
	public static String feldgesetztseperateline = "§7===================================";
	public static String feldinfos = "§7Name: §6%feld%";
	public static String worldfeldgesetzt = "§7Welt: §6%world%";
	public static String xfeldgesetzt = "§7X: §6%x%";
	public static String yfeldgesetzt = "§7Y: §6%y%";
	public static String zfeldgesetzt = "§7Z: §6%z%";
	public static String pitchfeldgesetzt = "§7Pitch: §6%pitch%";
	public static String yawfeldgesetzt = "§7Yaw: §6%yaw%";
	public static String mainGUI = "§2Farmanium Menü";
	public static String tpGUI = "§cTeleport Menü";
	public static String perkGUI = "§cPerk Menü";
	public static String questGUI = "§cQuest Menü";
	
	public static File locfile = new File("plugins/BetterFarmanium", "locations.yml");
	public static FileConfiguration loccfg = YamlConfiguration.loadConfiguration(locfile);
	
	public static File opfile = new File("plugins/BetterFarmanium", "operatoren.yml");
	public static FileConfiguration opcfg = YamlConfiguration.loadConfiguration(opfile);

	
	@Override
	public void onEnable() {
		plugin = this;

		registerListener(this, this.getClassLoader(), "me.jan.farmanium.listener");

		loadConfig();
		registerCommands();
		loadcfg();
		
		loadloccfg();
		loadplayercfg();
		
		if(!Farmanium.opfile.exists()) {
			try {
				Farmanium.opfile.createNewFile();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	@Override
	public void onDisable() {

	}

	public static Plugin getPlugin() {
		return plugin;
	}

	public void registerCommands() {
		getCommand("location").setExecutor(new SetLocationCMD(this));
		getCommand("perks").setExecutor(new PerkCMD(this));
		getCommand("operator").setExecutor(new OperatorCMD(this));
		getCommand("item").setExecutor(new ItemCMD(this));
		getCommand("broadcast").setExecutor(new BroadcastCMD(this));
	}

	public void registerListener(JavaPlugin javaPlugin, ClassLoader classLoader, String topLevelClass) {
		PluginManager pluginManager = Bukkit.getPluginManager();
		System.out.println("Searching for listener's...");
		try {
			for (ClassPath.ClassInfo classInfo : ClassPath.from(classLoader).getTopLevelClasses(topLevelClass)) {
				@SuppressWarnings("rawtypes")
				Class clazz = Class.forName(classInfo.getName());
				if (Listener.class.isAssignableFrom(clazz)) {
					pluginManager.registerEvents((Listener) clazz.newInstance(), javaPlugin);
					System.out.println(clazz.getName().replace("me.jan.farmanium.listener.", "") + " registered successfully!");
				}
			}
		} catch (IOException | ClassNotFoundException | IllegalAccessException | InstantiationException e) {
			e.printStackTrace();
		}
	}

	public void loadConfig() {
		getConfig().options().copyDefaults(true);
		saveConfig();
	}

	private static void loadcfg() {

		spawn = SetLocationCMD.buildlocation("spawn");

	}

	public static void loadloccfg() {
		loccfg.options().copyDefaults(true);
		try {
			loccfg.save(locfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static void loadopcfg() {
		opcfg.options().copyDefaults(true);
		try {
			opcfg.save(opfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	
	public static void loadplayercfg() {
		PlayerManager.playercfg.options().copyDefaults(true);
		try {
			PlayerManager.playercfg.save(PlayerManager.playerfile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
