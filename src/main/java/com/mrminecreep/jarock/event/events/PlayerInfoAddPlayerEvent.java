package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;
import com.mrminecreep.jarock.minecraft.registry.PlayerRegistry;

public class PlayerInfoAddPlayerEvent implements Event {
	
	private ArrayList<Object> data = new ArrayList<Object>(); 
	
	public PlayerInfoAddPlayerEvent() {
		
		ArrayList<Player> players = PlayerRegistry.getPlayers();
		
		this.data.add(new Byte((byte) 0));
		this.data.add(this.getPacketID());
		this.data.add(0);
		this.data.add(players.size());
		this.data.add(players.size());
		
		for(Player p : players) {
			this.data.add(p.getUUID());
			this.data.add(p.getUsername());
			
			this.data.add(0);
			this.data.add(false);
			this.data.add(false);
			this.data.add(false);
			this.data.add(false);
			
			this.data.add(0);
			this.data.add(1);
			this.data.add(false);
			this.data.add(false);
		}
	}

	@Override
	public ArrayList<Object> data() {
		return this.data;
	}

	@Override
	public boolean send() {
		return true;
	}

	@Override
	public String getEventString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Player getPlayer() {
		return null;
	}

	@Override
	public int getPacketID() {
		return 51;
	}

	@Override
	public int getState() {
		return 3;
	}

}
