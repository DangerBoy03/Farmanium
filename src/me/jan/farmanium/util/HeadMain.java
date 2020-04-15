package me.jan.farmanium.util;

import java.lang.reflect.Field;
import java.util.UUID;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.inventory.meta.SkullMeta;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;

public class HeadMain {

	public static ItemStack getHead(String name, String disname) {
		for (Heads head : Heads.values()) {
			if (head.getName().equalsIgnoreCase(name)) {	
				ItemStack is = head.getItemStack();
				ItemMeta im = is.getItemMeta();
				im.setDisplayName(disname);
				is.setItemMeta(im);
				return is;
			}
		}
		return null;
	}

	public static ItemStack createSkull(String url, String name, String disname) {
		ItemStack head = new ItemStack(Material.SKULL_ITEM, 1, (short) 3);
		if (url.isEmpty())
			return head;

		SkullMeta headMeta = (SkullMeta) head.getItemMeta();
		GameProfile profile = new GameProfile(UUID.randomUUID(), null);

		profile.getProperties().put("textures", new Property("textures", url));

		try {
			Field profileField = headMeta.getClass().getDeclaredField("profile");
			profileField.setAccessible(true);
			profileField.set(headMeta, profile);

		} catch (IllegalArgumentException | NoSuchFieldException | SecurityException | IllegalAccessException error) {
			error.printStackTrace();
		}
		
		headMeta.setDisplayName(disname);
		head.setItemMeta(headMeta);

		return head;
	}


}