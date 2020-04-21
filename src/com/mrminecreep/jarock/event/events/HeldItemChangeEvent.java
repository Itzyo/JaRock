package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class HeldItemChangeEvent implements Event {
	
	private Byte Slot;
	private Player p;
	private ArrayList<Object> data = new ArrayList<Object>();
	private Boolean send;
	
	public HeldItemChangeEvent(Byte Slot, Player p, Boolean send) {
		this.Slot = Slot;
		this.p = p;
		this.send = send;
		
		this.data.add(this.getPacketID());
		this.data.add(this.Slot);
	}
	
	public Byte getSlot(){
		return this.Slot;
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	public int getPacketID() {
		return 63;
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
		return this.send;
	}

}
