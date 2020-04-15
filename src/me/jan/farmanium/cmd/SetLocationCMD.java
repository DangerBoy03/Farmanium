package me.jan.farmanium.cmd;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import me.jan.farmanium.Farmanium;

public class SetLocationCMD implements CommandExecutor {

	@SuppressWarnings("unused")
	private Farmanium plugin;

	public SetLocationCMD(Farmanium farmanium) {
		this.plugin = farmanium;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		Player p = (Player) sender;

		if (p instanceof Player) {
			if (p.hasPermission("Farmanium.verwalten")) {
//location set <loc>
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("list")) {
						listloc(p);
					} else if (args[0].equalsIgnoreCase("set")) {

						ItemStack is = p.getItemInHand();

						if (is.getAmount() == 0) {
							p.sendMessage(Farmanium.prefix + "§cHalte ein Item in deiner Hand");
							return true;
						}

						String isd = is.getItemMeta().getDisplayName();

						if (isd == null) {
							p.sendMessage(Farmanium.prefix + "§cHalte ein Item mit Displayname in deiner Hand");
							return true;
						}

						isd = isd.replace(" ", "_");
						isd = ChatColor.stripColor(isd);
						
						setloc(p, "loc." + isd);
					}else {
						p.sendMessage(Farmanium.prefix + "§cMögliche Eingabe: List, Set");
					}
				} else if (args.length == 2) {

					if (args[0].equalsIgnoreCase("reset")) {
						resetloc(p, "loc." + args[1]);
					}else {
						p.sendMessage(Farmanium.prefix + "§cMögliche Eingabe: Reset");
					}
				} else {
					p.sendMessage(Farmanium.prefix + "/loc <set|list|reset> [<loc>]");
				}
			} else {
				p.sendMessage(Farmanium.prefix + Farmanium.noright);
			}

		}
		return true;
	}

	private static void listloc(Player p) {
		String msg = Farmanium.loccfg.getConfigurationSection("loc").getKeys(false).toString().replace("[", "")
				.replace("]", "");
		p.sendMessage(Farmanium.prefix + msg.substring(0, msg.length()));
	}

	public static void setloc(Player p, String path) {

		Farmanium.loccfg.set(path + ".item", p.getItemInHand());
		Farmanium.loccfg.set(path + ".world", p.getWorld().getName());
		Farmanium.loccfg.set(path + ".x", (int) p.getLocation().getX());
		Farmanium.loccfg.set(path + ".y", (int) p.getLocation().getY());
		Farmanium.loccfg.set(path + ".z", (int) p.getLocation().getZ());
		Farmanium.loccfg.set(path + ".pitch", (int) p.getLocation().getPitch());
		Farmanium.loccfg.set(path + ".yaw", (int) p.getLocation().getYaw());

		Farmanium.loadloccfg();
		messagevalues(p, path);
	}

	public static void resetloc(Player p, String path) {
		if (Farmanium.loccfg.get(path) != null) {
			Farmanium.loccfg.set(path, null);
			
			if(Farmanium.loccfg.getConfigurationSection("loc").getKeys(false).size() == 0) {
				Farmanium.locfile.delete();
				p.sendMessage(Farmanium.prefix + "§4GESAMTE LOC-DATEI GELÖSCHT");
				return;
			}
			
			Farmanium.loadloccfg();
			p.sendMessage(Farmanium.prefix + "§aLocation erfolgreich gelöscht");
		} else {
			p.sendMessage(Farmanium.prefix + "§cLocation existiert nicht");
		}
	}

	public static void messagevalues(Player p, String path) {
		p.sendMessage(Farmanium.feldgesetztseperateline);
		p.sendMessage(Farmanium.prefix + Farmanium.feldinfos.replace("%feld%", path));
		p.sendMessage(Farmanium.prefix
				+ Farmanium.worldfeldgesetzt.replace("%world%", Farmanium.loccfg.getString(path + ".world")));
		p.sendMessage(
				Farmanium.prefix + Farmanium.xfeldgesetzt.replace("%x%", Farmanium.loccfg.getString(path + ".x")));
		p.sendMessage(
				Farmanium.prefix + Farmanium.yfeldgesetzt.replace("%y%", Farmanium.loccfg.getString(path + ".y")));
		p.sendMessage(
				Farmanium.prefix + Farmanium.zfeldgesetzt.replace("%z%", Farmanium.loccfg.getString(path + ".z")));
		p.sendMessage(Farmanium.prefix
				+ Farmanium.pitchfeldgesetzt.replace("%pitch%", Farmanium.loccfg.getString(path + ".pitch")));
		p.sendMessage(Farmanium.prefix
				+ Farmanium.yawfeldgesetzt.replace("%yaw%", Farmanium.loccfg.getString(path + ".yaw")));
		p.sendMessage(Farmanium.feldgesetztseperateline);
	}

	public static Location buildlocation(String path) {

		String world = Farmanium.loccfg.getString(path + ".world");
		if (world == null)
			return null;
		double x = Farmanium.loccfg.getDouble(path + ".x");
		double y = Farmanium.loccfg.getDouble(path + ".y");
		double z = Farmanium.loccfg.getDouble(path + ".z");
		float pitch = (float) Farmanium.loccfg.getDouble(path + ".pitch");
		float yaw = (float) Farmanium.loccfg.getDouble(path + ".yaw");

		Location loc = new Location(Bukkit.getWorld(world), x, y, z, yaw, pitch);
		return loc;

	}
}
