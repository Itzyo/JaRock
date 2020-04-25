package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class LoginSuccessEvent implements Event{

	private Player p;
	private ArrayList<Object> data = new ArrayList<Object>();
	
	public LoginSuccessEvent(Player p) {
		this.p = p;
		
		this.data.add(this.getPacketID());
		String uuid = this.p.getUUID().toString();
		this.data.add(uuid);
		this.data.add(this.p.getUsername());
	}
	
	public int getPacketID() {
		return 2;
	}
	
	public int getState() {
		return 2;
	}
	
	public Player getPlayer() {
		return this.p;
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
