package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

public class JoinGameEvent implements Event{

	private Integer EntityID, Dimension, ViewDistance;
	private Byte Gamemode;
	private String levelType;
	private Boolean ReducedDebugInfo;
	private Player p;
	private ArrayList<Object> data = new ArrayList<Object>();
	
	public JoinGameEvent(Integer EntityID, Byte Gamemode, Integer Dimension, String LevelType, Integer ViewDistance, Boolean ReducedDebugInfo, Player p) {
		this.EntityID = EntityID;
		this.Gamemode = Gamemode;
		this.Dimension = Dimension;
		this.levelType = LevelType;
		this.ViewDistance = ViewDistance;
		this.ReducedDebugInfo = ReducedDebugInfo;
		this.p = p;
		
		this.data.add(this.getPacketID());
		this.data.add(this.EntityID);
		this.data.add(this.Gamemode);
		this.data.add(this.Dimension);
		this.data.add((byte) 0);
		this.data.add(this.levelType);
		this.data.add(this.ViewDistance);
		this.data.add(this.ReducedDebugInfo);
	}
	
	public Integer getEntityID() {
		return this.EntityID;
	}
	
	public Integer getDimension() {
		return this.Dimension;
	}
	
	public Integer getViewDistance() {
		return this.ViewDistance;
	}
	
	public Byte getGamemode() {
		return this.Gamemode;
	}
	
	public String getLevelType() {
		return this.levelType;
	}
	
	public Boolean ReducedDebugInfo() {
		return this.ReducedDebugInfo;
	}
	
	public Player getPlayer() {
		return this.p;
	}
	
	public int getPacketID() {
		return 37;
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
