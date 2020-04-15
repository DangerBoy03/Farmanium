package me.jan.farmanium.util;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;

public enum Perk {

	DOUBLEJUMP(new ItemBuilder(Material.WOOD_PLATE).withName("§cDoubleJump").toItemStack()),
	HEALTH(new ItemBuilder(Material.IRON_CHESTPLATE).withName("§cHealth").toItemStack()),
	SPEED(new ItemBuilder(Material.IRON_BOOTS).withName("§cSpeed").toItemStack()),
	STRENGH(new ItemBuilder(Material.IRON_SWORD).withName("§cStrengh").toItemStack()),
	FLY(new ItemBuilder(Material.FEATHER).withName("§cFly").toItemStack()),
	NOFOOD(new ItemBuilder(Material.COOKED_BEEF).withName("§cNoFood").toItemStack()),
	HASTE(new ItemBuilder(Material.GOLD_PICKAXE).withName("§cHaste").toItemStack());
//	HASTE1(new ItemBuilder(Material.GOLD_PICKAXE).withName("§cHaste").toItemStack()),
//	HASTE2(new ItemBuilder(Material.GOLD_PICKAXE).withName("§cHaste").toItemStack()),
//	HASTE3(new ItemBuilder(Material.GOLD_PICKAXE).withName("§cHaste").toItemStack()),
//	HASTE4(new ItemBuilder(Material.GOLD_PICKAXE).withName("§cHaste").toItemStack()),
//	HASTE5(new ItemBuilder(Material.GOLD_PICKAXE).withName("§cHaste").toItemStack()),
//	HASTE6(new ItemBuilder(Material.GOLD_PICKAXE).withName("§cHaste").toItemStack()),
//	HASTE7(new ItemBuilder(Material.GOLD_PICKAXE).withName("§cHaste").toItemStack());

	private ItemStack item;

	Perk(ItemStack is) {
		item = is;
	}	
	public ItemStack getItemStack() {
		return item;
	}
}
