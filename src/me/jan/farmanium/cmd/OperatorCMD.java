package me.jan.farmanium.cmd;

import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.GameMode;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import me.jan.farmanium.Farmanium;
import me.jan.farmanium.manager.PlayerManager;

public class OperatorCMD implements CommandExecutor {

	@SuppressWarnings("unused")
	private Farmanium plugin;

	public OperatorCMD(Farmanium farmanium) {
		this.plugin = farmanium;
	}

	static int amount = getopamount();

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		// operator set <player>
		Player p = (Player) sender;
		if (p instanceof Player) {
			if (p.hasPermission("Farmanium.verwalten")) {
				amount = getopamount();
				if (args.length == 1) {
					if (args[0].equalsIgnoreCase("list")) {

						String opz = getops(p);
						if (opz == "") {
							p.sendMessage(Farmanium.prefix + "§cEs gibt keine Operatoren");
							return true;
						}
						opz = opz.substring(0, opz.length() - 2);
						p.sendMessage(Farmanium.prefix + "Operatoren(§e" + getopamount() + "§7): " + opz);
					}
				} else if (args.length == 2) {
					Player target = Bukkit.getPlayer(args[1]);
					if (target == null) {
						p.sendMessage(Farmanium.prefix + "§e" + args[1] + " §7muss online sein.");
						return true;
					}
					if (args[0].equalsIgnoreCase("set")) {
						setops(p, target);
					} else if (args[0].equalsIgnoreCase("reset")) {
						resetops(p, target);
					}
				} else {
					p.sendMessage(Farmanium.prefix + "§c/operator <set|reset|list> [<player>]");
				}
			}
		}
		return true;
	}

	private static String getops(Player p) {
		int i = 1;
		String ops = "";
		while (Farmanium.opcfg.get(String.valueOf(i)) != null) {
			String name = Bukkit.getPlayer(UUID.fromString(Farmanium.opcfg.getString(String.valueOf(i)))).getName();
			ops = ops + name + ", ";
			i++;
		}
		i--;
		return ops;
	}

	private static int getopamount() {
		int i = 1;
		while (Farmanium.opcfg.get(String.valueOf(i)) != null) {
			i++;
		}
		i--;
		return i;
	}

	private static void setops(Player p, Player target) {
		if (!PlayerManager.playerexists(target)) {
			p.sendMessage(Farmanium.prefix + "§e" + target.getName() + " §7war noch nie hier");
			return;
		}

		if (PlayerManager.hasoperator(target)) {
			p.sendMessage(Farmanium.prefix + "§e" + target.getName() + " §7ist bereits ein Operator");
			return;
		}
		int ops = amount;
		ops++;
		Farmanium.opcfg.set(String.valueOf(ops), target.getUniqueId().toString());
		Farmanium.loadopcfg();
		target.setOp(true);
		p.sendMessage(Farmanium.prefix + "§e" + target.getName() + " §7ist nun ein Operator");
	}

	private static void resetops(Player p, Player target) {
		if (!PlayerManager.hasoperator(target)) {
			p.sendMessage(Farmanium.prefix + "§e" + target.getName() + " §cist kein Operator gewesen");
			return;
		}
		int x = 0;
		for (int i = 1; i < amount + 1; i++) {
			UUID uuid = UUID.fromString(Farmanium.opcfg.getString(String.valueOf(i)));
			if (uuid.equals(target.getUniqueId())) {
				x = i;
				break;
			}
		}

		Farmanium.opcfg.set(String.valueOf(x), null);
		switchops(x);
		Farmanium.loadopcfg();
		target.setOp(false);
		target.setGameMode(GameMode.ADVENTURE);
		p.sendMessage(Farmanium.prefix + "§e" + target.getName() + " §cist nun kein Operator mehr");
	}

	// '1': 96c365f3-5599-43c6-b5cc-a39cbf578186
	private static void switchops(int x) {
		String uuid = Farmanium.opcfg.getString(String.valueOf(amount));
		Farmanium.opcfg.set(String.valueOf(amount), null);
		Farmanium.opcfg.set(String.valueOf(x), uuid);
	}
}
