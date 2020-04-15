package me.jan.farmanium.util;

public enum Quest {

	FIRSTWOOD("§cFirstWood", "lore,tes", Reward.MONEY, 10, 0, 0),
	FIRSTSTONE("§cFirstStone", "lore,tes", Reward.MONEY, 10, 0, 0),
	FIRSTFISH("§cFirstFish", "lore,tes", Reward.MONEY, 20, 0, 0);
	
	Quest(String disname, String lore, Reward reward,int moneyreward, int price, int lvl) {
		
	}	
}
