package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class KeepAliveEventRecv implements Event {
	
	private Long KeepAlive;
	private Player p;
	
	public KeepAliveEventRecv(Long KeepAlive, Player p) {
		this.KeepAlive = KeepAlive;
		this.p = p;
	}
	
	public Long getKeepAlive() {
		return this.KeepAlive;
	}

	@Override
	public String getEventString() {
		return this.getClass().getSimpleName();
	}

	@Override
	public Player getPlayer() {
		return this.p;
	}

	@Override
	public int getPacketID() {
		return 15;
	}

	@Override
	public int getState() {
		return 3;
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
