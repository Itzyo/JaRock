package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;
import com.mrminecreep.jarock.util.types.Position;

public class SpawnPositionEvent implements Event {

	private Position pos;
	private Player p;
	private ArrayList<Object> data = new ArrayList<Object>();
	
	public SpawnPositionEvent(Position pos, Player p) {
		this.pos = pos;
		this.p = p;
		
		this.data.add(this.getPacketID());
		this.data.add(this.pos);
	}
	
	public Position getPosition() {
		return this.pos;
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	public int getPacketID() {
		return 77;
	}
	
	public int getState() {
		return 3;
	}
	
	@Override
	public String getEventString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public ArrayList<Object> data() {
		return this.data;
	}

	@Override
	public boolean send() {
		return true;
	}

}
