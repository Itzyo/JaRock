package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class PlayerPositionAndLookEvent implements Event {
	
	private Double X, Y, Z;
	private Float Yaw, Pitch;
	private Byte Flags;
	private Integer TeleportID;
	private Player p;
	private ArrayList<Object> data = new ArrayList<Object>();
	private Boolean send;
	
	public PlayerPositionAndLookEvent(Double X, Double Y, Double Z, Float Yaw, Float Pitch, Byte Flags, Integer TeleportID, Player p, Boolean send) {
		this.X = X;
		this.Y = Y;
		this.Z = Z;
		this.Yaw = Yaw;
		this.Pitch = Pitch;
		this.Flags = Flags;
		this.TeleportID = TeleportID;
		this.p = p;
		this.send = send;
		
		this.data.add(this.getPacketID());
		this.data.add(this.X);
		this.data.add(this.Y);
		this.data.add(this.Z);
		this.data.add(this.Yaw);
		this.data.add(this.Pitch);
		this.data.add(this.Flags);
		this.data.add(this.TeleportID);
	}
	
	public int getPacketID() {
		return 53;
	}
	
	public int getState() {
		return 3;
	}
	
	public Double getX() {
		return this.X;
	}
	
	public Double getY() {
		return this.Y;
	}
	
	public Double getZ() {
		return this.Z;
	}
	
	public Float getYaw() {
		return this.Yaw;
	}
	
	public Float getPitch() {
		return this.Pitch;
	}
	
	public Byte getFlags() {
		return this.Flags;
	}
	
	public Integer getTeleportID() {
		return this.TeleportID;
	}
	
	public Player getPlayer() {
		return this.p;
	}

	@Override
	public String getEventString() {
		return this.getClass().getName();
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
