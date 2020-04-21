package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class PlayerPositionUpdateEvent implements Event {
	
	private Double X, Feet_Y, Z;
	private Boolean OnGround;
	private Player p;
	
	public PlayerPositionUpdateEvent(Double X, Double Feet_Y, Double Z, Boolean OnGround, Player p) {
		this.X = X;
		this.Feet_Y = Feet_Y;
		this.Z = Z;
		this.OnGround = OnGround;
		this.p = p;
	}
	
	public Double getX() {
		return this.X;
	}
	
	public Double getFeet_Y() {
		return this.Feet_Y;
	}
	
	public Double getZ() {
		return this.Z;
	}
	
	public Boolean isOnGround() {
		return this.OnGround;
	}

	@Override
	public ArrayList<Object> data() {
		return null;
	}

	@Override
	public boolean send() {
		return false;
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
		return 17;
	}

	@Override
	public int getState() {
		return 3;
	}

}
