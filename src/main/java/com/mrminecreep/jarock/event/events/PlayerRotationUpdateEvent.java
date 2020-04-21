package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class PlayerRotationUpdateEvent implements Event {
	
	private Float Yaw, Pitch;
	private Boolean OnGround;
	private Player p;
	
	public PlayerRotationUpdateEvent(Float Yaw, Float Pitch, Boolean OnGround, Player p) {
		this.Yaw = Yaw;
		this.Pitch = Pitch;
		this.OnGround = OnGround;
		this.p = p;
	}
	
	public Float getYaw() {
		return this.Yaw;
	}
	
	public Float getPitch() {
		return this.Pitch;
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
		return 19;
	}

	@Override
	public int getState() {
		return 3;
	}

}
