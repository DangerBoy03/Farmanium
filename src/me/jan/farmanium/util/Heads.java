package me.jan.farmanium.util;

import org.bukkit.inventory.ItemStack;

public enum Heads {
	RightArrow("MTliZjMyOTJlMTI2YTEwNWI1NGViYTcxM2FhMWIxNTJkNTQxYTFkODkzODgyOWM1NjM2NGQxNzhlZDIyYmYifX19", "ra",""),
	LeftArrow("YmQ2OWUwNmU1ZGFkZmQ4NGU1ZjNkMWMyMTA2M2YyNTUzYjJmYTk0NWVlMWQ0ZDcxNTJmZGM1NDI1YmMxMmE5In19fQ==", "la",""),
	Rufzeichen("MmUzZjUwYmE2MmNiZGEzZWNmNTQ3OWI2MmZlZGViZDYxZDc2NTg5NzcxY2MxOTI4NmJmMjc0NWNkNzFlNDdjNiJ9fX0=","rufzeichen","");

	private ItemStack item;
	private String idTag;
	private String prefix = "eyJ0ZXh0dXJlcyI6eyJTS0lOIjp7InVybCI6Imh0dHA6Ly90ZXh0dXJlcy5taW5lY3JhZnQubmV0L3RleHR1cmUv";

	Heads(String texture, String id, String disname) {
		
		item = HeadMain.createSkull(prefix + texture, id, disname);
		idTag = id;

	}

	public ItemStack getItemStack() {
		return item;
	}

	public String getName() {
		return idTag;
	}

}
