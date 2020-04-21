package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class LoginStartEvent implements Event {

	private String Username, client;
	private Boolean isBedrock;
	
	public LoginStartEvent(String Username, String client, Boolean isBedrock) {
		this.client = client;
		this.Username = Username;
		this.isBedrock = isBedrock;
	}
	
	public int getPacketID() {
		return 0;
	}
	
	public int getState() {
		return 2;
	}
	
	public String getUsername() {
		return this.Username;
	}
	
	public String getClient() {
		return this.client;
	}
	
	public Boolean isBedrock() {
		return this.isBedrock;
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
	public ArrayList<Object> data() {
		return null;
	}

	@Override
	public boolean send() {
		return false;
	}

}
