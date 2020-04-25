package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class PlayerInfoRemovePlayerEvent implements Event {

	private Player p;
	private ArrayList<Object> data = new ArrayList<Object>(); 
	
	public PlayerInfoRemovePlayerEvent(Player p) {
		this.p = p;
		
		this.data.add(new Byte((byte) 4));
		this.data.add(this.getPacketID());
		this.data.add(4);
		this.data.add(1);
		
		this.data.add(p.getUUID());
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
