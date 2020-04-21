package com.mrminecreep.jarock.minecraft.registry;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;

import com.mrminecreep.jarock.minecraft.Player;

public class PlayerRegistry {
	
	private static List<Player> players = new CopyOnWriteArrayList<Player>();
	
	public static void registerPlayer(Player player) {
		EntityRegistry.registerEntity(player);
		players.add(player);
	}
	
	public static void unregisterPlayer(Player player) {
		EntityRegistry.unregisterEntity(player);
		players.remove(player);
	}
	
	public static Player getPlayerByClient(String client) {
		for(Player p : players) {
			if(p.getClient().equals(client))
				return p;
		}
		
		return null;
	}
	
	public static Player getPlayerByUsername(String Username) {
		for(Player p : players) {
			if(p.getUsername().equals(Username))
				return p;
		}
		
		return null;
	}
	
	public static Player getPlayerByUUID(String uuid) {
		for(Player p : players) {
			if(p.getUUID().equals(uuid))
				return p;
		}
		
		return null;
	}
	
	public static ArrayList<Player> getClientsByEdition(boolean isBedrock){
		ArrayList<Player> out = new ArrayList<Player>();
		
		for(Player p : players) {
			if(p.isBedrock() == isBedrock)
				out.add(p);
		}
		
		return out;
	}
	
	public static ArrayList<Player> getPlayers(){
		return new ArrayList<Player>(players);
	}
	
	public static void ForEachPlayer(Function <Player, Object> func) {
		for(Player q : players) {
			func.apply(q);
		}
	}

}
