package com.mrminecreep.jarock.minecraft;

import java.util.UUID;

public class Player extends Entity{
	
	private String client, Username;
	private UUID uuid;
	private boolean isBedrock;
	private boolean LoginDone = false;
	private Long lastKeepAlive = null;
	private Integer lostKeepAlives = 0;
	private Long ping = (long) 0;
	private Byte gamemode = 0;
	
	public Player(String client, boolean isBedrock, String Username, UUID uuid) {
		this.client = client;
		this.Username = Username;
		this.uuid = uuid;
		this.isBedrock = isBedrock;
	}
	
	public Byte getGamemode() {
		return this.gamemode;
	}
	
	public void setGamemode(Byte gm) {
		this.gamemode = gm;
	}
	
	public Long getPing() {
		return this.ping;
	}
	
	public void setPing(Long ping) {
		this.ping = ping;
	}
	
	public Integer getLostKeepAlives() {
		return this.lostKeepAlives;
	}
	
	public void LostPacket() {
		this.lostKeepAlives++;
	}
	
	public void resetLostKeepAlives() {
		this.lostKeepAlives = 0;
	}
	
	public boolean isLoginDone() {
		return this.LoginDone;
	}
	
	public void LoginIsDone() {
		this.LoginDone = true;
	}
	
	public void setKeepAlive(Long id) {
		this.lastKeepAlive = id;
	}
	
	public Long getKeepAlive() {
		return this.lastKeepAlive;
	}
	
	public String getClient() {
		return this.client;
	}
	
	public String getUsername() {
		return this.Username;
	}
	
	public UUID getUUID() {
		return this.uuid;
	}
	
	public boolean isBedrock() {
		return this.isBedrock;
	}

}
