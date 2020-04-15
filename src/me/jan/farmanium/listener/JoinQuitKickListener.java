package me.jan.farmanium.listener;

import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import me.jan.farmanium.Farmanium;
import me.jan.farmanium.manager.PlayerManager;

public class JoinQuitKickListener implements Listener {

//	@EventHandler
//	public void on(PlayerSpawnLocationEvent e) {
//		e.setSpawnLocation(Farmanium.spawn);
//	}
	
	@EventHandler
	public void on(PlayerJoinEvent e) {
		e.setJoinMessage(null);	
		Player p = e.getPlayer();
		PlayerManager.checklogin(p);
		if(PlayerManager.hasoperator(p)) p.setOp(true);
		if(Farmanium.spawn == null)return;
		e.getPlayer().teleport(Farmanium.spawn);
	}
}
