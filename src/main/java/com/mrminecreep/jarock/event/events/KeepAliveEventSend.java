package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class KeepAliveEventSend implements Event {
	
	private Long KeepAlive;
	private ArrayList<Object> data = new ArrayList<Object>();
	
	public KeepAliveEventSend(Long KeepAlive) {
		this.KeepAlive = KeepAlive;

		this.data.add(this.getPacketID());
		this.data.add(this.KeepAlive);
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
		return null;
	}

	@Override
	public int getPacketID() {
		return 32;
	}

	@Override
	public int getState() {
		return 3;
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
