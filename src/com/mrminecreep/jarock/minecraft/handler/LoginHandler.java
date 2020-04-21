package com.mrminecreep.jarock.minecraft.handler;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.event.EventConstructor;
import com.mrminecreep.jarock.event.EventListener;
import com.mrminecreep.jarock.event.events.HandshakeEvent;
import com.mrminecreep.jarock.event.events.LoginStartEvent;
import com.mrminecreep.jarock.event.events.LoginSuccessEvent;
import com.mrminecreep.jarock.minecraft.Player;
import com.mrminecreep.jarock.minecraft.registry.EntityRegistry;
import com.mrminecreep.jarock.minecraft.registry.PlayerRegistry;
import com.mrminecreep.jarock.minecraft.world.World;
import com.mrminecreep.jarock.networking.ClientRegistry;
import com.mrminecreep.jarock.networking.java.JavaSocket;
import com.mrminecreep.jarock.networking.java.Types.Position;

public class LoginHandler {
	
	@EventListener("com.mrminecreep.jarock.event.events.HandshakeEvent")
	public void HandleHandshake(HandshakeEvent e) {
		ClientRegistry.changeClientState(e.getClient(), e.getNextState());
		Logger.log_debug("Changed state of %s to %d.", e.getClient(), e.getNextState());
	}
	
	@EventListener("com.mrminecreep.jarock.event.events.LoginStartEvent")
	public void HandleLoginStart(LoginStartEvent e) {
		
		String uuid = null;
		
		Logger.log_debug("Trying to get %s uuid", e.getUsername());
		try {
			URL url = new URL(String.format("https://api.mojang.com/users/profiles/minecraft/%s", e.getUsername()));
			HttpsURLConnection con = (HttpsURLConnection) url.openConnection();
			
			if(con.getResponseCode() == 200) {
				BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
				String line;
				StringBuffer content = new StringBuffer();
				
				while((line = in.readLine()) != null)
					content.append(line);
				in.close();
				con.disconnect();
				uuid = content.substring(7, content.indexOf(",") - 1);
				Logger.log_debug("%s uuid: %s, (%s)", e.getUsername(), uuid, content.toString());
			}
			
		} catch (IOException e1) {
			Logger.log_error("Unable to fetch UUID for player %s", e.getUsername());
			e1.printStackTrace();
			return;
		}
		
		if(uuid != null) {
			Player p = new Player(e.getClient(), e.isBedrock(), e.getUsername(), uuid);
			PlayerRegistry.registerPlayer(p);
			Logger.log_debug("Registered player %s", e.getUsername());
			EventConstructor.createLoginSuccessEvent(p);
		} else {
			Logger.log_error("Something went wrong while fetching the players uuid.");
			return;
		}
	}
	
	@EventListener("com.mrminecreep.jarock.event.events.LoginSuccessEvent")
	public void HandleLoginSuccess(LoginSuccessEvent e) throws InterruptedException {
		Position Spawn = new Position(0, 118, 0);
		ClientRegistry.changeClientState(e.getPlayer().getClient(), 3);
		Logger.log_debug("Changed state of %s to 3.", e.getPlayer().getClient());
		EventConstructor.createJoinGameEvent(EntityRegistry.getEntityID(e.getPlayer()), e.getPlayer().getGamemode(), 0, "flat", 15, false, e.getPlayer());
		EventConstructor.createHeldItemChangeEvent((byte) 8, e.getPlayer(), true);
		World.sendChunksToPlayer(e.getPlayer());
		EventConstructor.createSpawnPositionEvent(Spawn, e.getPlayer());
		Thread.currentThread();
		Thread.sleep(100);
		e.getPlayer().setX(Spawn.getX());
		e.getPlayer().setY(Spawn.getY());
		e.getPlayer().setZ(Spawn.getZ());
		EventConstructor.createPlayerPositionAndLookEvent(new Double(Spawn.getX()), new Double(Spawn.getY()), new Double(Spawn.getZ()),
				0f, 0f, (byte) 0, 0, e.getPlayer(), true);
		JavaSocket.all.add(ClientRegistry.getChannel(e.getPlayer().getClient()));
		e.getPlayer().LoginIsDone();
		Logger.log_info("%s has joined the game", e.getPlayer().getUsername());
		Logger.log_debug("---------------------------------------------------------------------------------------");
		EventConstructor.createPlayerInfoAddPlayerEvent();		
	}
}
