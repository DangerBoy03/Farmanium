
package me.jan.farmanium.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map.Entry;

import org.bukkit.Color;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.LeatherArmorMeta;
import org.bukkit.inventory.meta.SkullMeta;
import org.bukkit.material.MaterialData;
import org.bukkit.potion.Potion;

/**
 * The ItemBuilder class is a class to create items very easily.
 * 
 * <pre>
 * ItemStack item = new ItemBuilder(Material.IRON_SWORD).withEnchantment(Enchantment.DAMAGE_ALL, 1, true)
 * 		.withLore("First line!").withLores("Second line!", "Thrid line!").withAmount(2).withDurability(5)
 * 		.toItemStack();
 * </pre>
 * 
 * Credits to: TheKomputerKing (Original Author)
 *
 * @author CraftThatBlock
 */
public class ItemBuilder {
	private Material material;
	private int amount = 1;
	private String name;
	private List<String> lores = new ArrayList<String>();
	private short durability = -101;
	private MaterialData data;
	private Potion potion;
	private Color color;
	private HashMap<Enchantment, Integer> enchants = new HashMap<>();

	public ItemBuilder(Material material) {
		this.material = material;
	}

	public ItemBuilder(Material material, int amount) {
		this.material = material;
		this.amount = amount;
	}

	/**
	 * Set the amount of items
	 *
	 * @param amount Amount
	 * @return ItemBuilder
	 */
	public ItemBuilder withAmount(int amount) {
		this.amount = amount;
		return this;
	}

	/**
	 * Set the name of the item
	 *
	 * @param name Name
	 * @return ItemBuilder
	 */
	public ItemBuilder withName(String name) {
		this.name = name;
		return this;
	}

	/**
	 * Add multiple lore lines
	 *
	 * @param lores Lores
	 * @return ItemBuilder
	 */
	public ItemBuilder withLores(String... lores) {
		Collections.addAll(this.lores, lores);
		return this;
	}

	/**
	 * Add multiple lore lines
	 *
	 * @param lores Lores
	 * @return ItemBuilder
	 */
	public ItemBuilder withLores(List<String> lores) {
		for (String lore : lores) {
			this.lores.add(lore);
		}
		return this;
	}

	/**
	 * Add a single lore line
	 *
	 * @param lore Lore
	 * @return ItemBuilder
	 */
	public ItemBuilder withLore(String lore) {
		lores.add(lore);
		return this;
	}

	/**
	 * Add a enchantment
	 *
	 * @param enchantment Enchantment type
	 * @param level       Level of enchantment
	 * @param glow        Item Glow
	 * @return ItemBuilder
	 */

	/**
	 * Set the durability (little bar under item)
	 *
	 * @param durability Durability
	 * @return ItemBuilder
	 */
	public ItemBuilder withDurability(short durability) {
		this.durability = durability;
		return this;
	}

	/**
	 * Set the MaterialData of the item
	 *
	 * @param data Data
	 * @return ItemBuilder
	 */
	public ItemBuilder withData(MaterialData data) {
		this.data = data;
		return this;
	}

	/**
	 * Set the potion to be apply to the item. The Item material must be a Potion so
	 * it applies.
	 *
	 * @param potion Potion
	 * @return ItemBuilder
	 */
	public ItemBuilder withPotion(Potion potion) {
		this.potion = potion;
		return this;
	}

	/**
	 * Set the color of the item. Only works with Leather armor.
	 *
	 * @param color Color
	 * @return ItemBuilder
	 */
	public ItemBuilder withColor(Color color) {
		this.color = color;
		return this;
	}

	public ItemBuilder withEnchantment(Enchantment enchantment, int x) {
		enchants.put(enchantment, x);
		return this;
	}

	/**
	 * Create the ItemStack!
	 *
	 * @return ItemBuilder
	 */
	public ItemStack toItemStack(boolean glow) {
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);

		ItemMeta meta = item.getItemMeta();
		if (name != null && name != "") {
			meta.setDisplayName(name);
		}
		if (!lores.isEmpty()) {
			meta.setLore(lores);
		}
		if (durability != -101) {
			item.setDurability(durability);
		}
		if (data != null) {
			item.setData(data);
		}
		if (potion != null && material == Material.POTION) {
			potion.apply(item);
		}
		if (color != null && meta instanceof LeatherArmorMeta) {
			((LeatherArmorMeta) meta).setColor(color);
		}
		if (glow) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addEnchant(Enchantment.WATER_WORKER, 1, false);
		}

		for (Entry<Enchantment, Integer> set : enchants.entrySet()) {
			meta.addEnchant(set.getKey(), set.getValue(), true);
		}

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack toItemStack() {
		ItemStack item = new ItemStack(material);
		item.setAmount(amount);

		ItemMeta meta = item.getItemMeta();
		if (name != null && name != "") {
			meta.setDisplayName(name);
		}
		if (!lores.isEmpty()) {
			meta.setLore(lores);
		}
		if (durability != -101) {
			item.setDurability(durability);
		}
		if (data != null) {
			item.setData(data);
		}
		if (potion != null && material == Material.POTION) {
			potion.apply(item);
		}
		if (color != null && meta instanceof LeatherArmorMeta) {
			((LeatherArmorMeta) meta).setColor(color);
		}

		for (Entry<Enchantment, Integer> set : enchants.entrySet()) {
			meta.addEnchant(set.getKey(), set.getValue(), true);
		}

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack toItemStack(byte b) {
		ItemStack item = new ItemStack(material, 1, b);
		item.setAmount(amount);

		ItemMeta meta = item.getItemMeta();
		if (name != null && name != "") {
			meta.setDisplayName(name);
		}
		if (!lores.isEmpty()) {
			meta.setLore(lores);
		}
		if (durability != -101) {
			item.setDurability(durability);
		}
		if (data != null) {
			item.setData(data);
		}
		if (potion != null && material == Material.POTION) {
			potion.apply(item);
		}
		if (color != null && meta instanceof LeatherArmorMeta) {
			((LeatherArmorMeta) meta).setColor(color);
		}

		for (Entry<Enchantment, Integer> set : enchants.entrySet()) {
			meta.addEnchant(set.getKey(), set.getValue(), true);
		}

		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack toItemStack(boolean glow, byte b) {
		ItemStack item = new ItemStack(material, 1, b);
		item.setAmount(amount);

		ItemMeta meta = item.getItemMeta();
		if (name != null && name != "") {
			meta.setDisplayName(name);
		}
		if (!lores.isEmpty()) {
			meta.setLore(lores);
		}
		if (durability != -101) {
			item.setDurability(durability);
		}
		if (data != null) {
			item.setData(data);
		}
		if (potion != null && material == Material.POTION) {
			potion.apply(item);
		}
		if (color != null && meta instanceof LeatherArmorMeta) {
			((LeatherArmorMeta) meta).setColor(color);
		}
		if (glow) {
			meta.addItemFlags(ItemFlag.HIDE_ENCHANTS);
			meta.addEnchant(Enchantment.WATER_WORKER, 1, false);
		}

		for (Entry<Enchantment, Integer> set : enchants.entrySet()) {
			meta.addEnchant(set.getKey(), set.getValue(), true);
		}
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}

	public ItemStack toPlayerHead(String ownername) {
		ItemStack item = new ItemStack(material, 1, (short) 3);
		item.setAmount(amount);

		ItemMeta meta = item.getItemMeta();
		if (name != null && name != "") {
			meta.setDisplayName(name);
		}
		if (!lores.isEmpty()) {
			meta.setLore(lores);
		}
		if (durability != -101) {
			item.setDurability(durability);
		}
		if (data != null) {
			item.setData(data);
		}
		if (potion != null && material == Material.POTION) {
			potion.apply(item);
		}
		if (color != null && meta instanceof LeatherArmorMeta) {
			((LeatherArmorMeta) meta).setColor(color);
		}
		((SkullMeta) meta).setOwner(ownername);
		for (Entry<Enchantment, Integer> set : enchants.entrySet()) {
			meta.addEnchant(set.getKey(), set.getValue(), true);
		}
		meta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES);
		item.setItemMeta(meta);
		return item;
	}

}
