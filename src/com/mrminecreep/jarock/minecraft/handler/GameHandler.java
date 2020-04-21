package com.mrminecreep.jarock.minecraft.handler;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.event.EventListener;
import com.mrminecreep.jarock.event.events.PlayerPositionUpdateEvent;
import com.mrminecreep.jarock.event.events.PlayerRotationUpdateEvent;
import com.mrminecreep.jarock.minecraft.Player;

public class GameHandler {
	
	@EventListener("com.mrminecreep.jarock.event.events.PlayerPositionUpdateEvent")
	public void HandlePositionUpdate(PlayerPositionUpdateEvent e) {
		Player p = e.getPlayer();
		p.setX(e.getX().intValue());
		p.setY(e.getFeet_Y().intValue());
		p.setZ(e.getZ().intValue());
		Logger.log_debug("Update position of %s to %d, %d, %d", p.getUsername(), e.getX().intValue(), e.getFeet_Y().intValue(), e.getZ().intValue());
	}
	
	@EventListener("com.mrminecreep.jarock.event.events.PlayerRotationUpdateEvent")
	public void HandleRotationUpdate(PlayerRotationUpdateEvent e) {
		Player p = e.getPlayer();
		p.setYaw(e.getYaw());
		p.setPitch(e.getPitch());
		Logger.log_debug("Update rotation of %s to %.2f, %.2f", p.getUsername(), e.getYaw(), e.getPitch());
	}

}
