package me.jan.farmanium.manager;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

import me.jan.farmanium.util.ItemBuilder;

public class ItemManager {

	public static ItemStack netherstar, glass, teleport,wood, stone, fish, wheat, sword, redstone, sand, beacon,
			painting, feather, greenglass, barrier, notbought, quest, off, on, bank, crafting, cosmetics, collection,
			minigames;
	static {
		netherstar = new ItemBuilder(Material.NETHER_STAR).withName("§cFarmanium Menü").toItemStack();
		glass = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability((short) 7).withName(" ").toItemStack();
		greenglass = new ItemBuilder(Material.STAINED_GLASS_PANE).withDurability((short) 5).withName(" ").toItemStack();
		teleport = new ItemBuilder(Material.EYE_OF_ENDER).withName("§cTeleport-Menü").toItemStack();
		feather = new ItemBuilder(Material.FEATHER).withName("§cPerks").toItemStack();
		quest = new ItemBuilder(Material.QUARTZ_ORE).withName("§cQuest").toItemStack();
		bank = new ItemBuilder(Material.GOLD_BLOCK).withName("§cBank").toItemStack();
		crafting = new ItemBuilder(Material.WORKBENCH).withName("§cCrafting").toItemStack();
		cosmetics = new ItemBuilder(Material.FIREWORK).withName("§cCosmetics").toItemStack();
		collection = new ItemBuilder(Material.PAINTING).withName("§cCollection").toItemStack();
		minigames = new ItemBuilder(Material.DIAMOND_BOOTS).withName("§cMinigames").toItemStack();
		barrier = new ItemBuilder(Material.BARRIER).withName("§7[§cX§7]").toItemStack();
		notbought = new ItemBuilder(Material.INK_SACK).withDurability((short) 8).withName("§cNicht gekauft")
				.toItemStack();
		off = new ItemBuilder(Material.INK_SACK).withDurability((short) 1).withName("§cDeaktiviert").toItemStack();
		on = new ItemBuilder(Material.INK_SACK).withDurability((short) 10).withName("§aAktiviert").toItemStack();
	}
}
