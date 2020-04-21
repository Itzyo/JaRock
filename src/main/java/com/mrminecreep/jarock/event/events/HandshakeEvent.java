package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

/**
 * This event will be fired when the first packet in the handshake is called. <br>
 * 
 * @see <a href="https://wiki.vg/Protocol#Handshaking">Unofficial wiki</a>
 * 
 * @author MrMinecreep
 *
 */
public class HandshakeEvent implements Event{
	
	private Integer protocolVersion, ServerPort, nextState;
	private String ServerAdress, Client;
	
	public HandshakeEvent(Integer ProtocolVersion, String ServerAdress, Integer ServerPort, Integer nextState, String Client) {
		this.protocolVersion = ProtocolVersion;
		this.ServerPort = ServerPort;
		this.nextState = nextState;
		this.ServerAdress = ServerAdress;
		this.Client = Client;
	}

	@Override
	public String getEventString() {
		return this.getClass().getSimpleName();
	}

	public int getPacketID() {
		return 0;
	}

	public int getState() {
		return 0;
	}
	
	public Integer getProtocolVersion() {
		return this.protocolVersion;
	}
	
	public Integer getServerPort() {
		return this.ServerPort;
	}
	
	public Integer getNextState() {
		return this.nextState;
	}
	
	public String getServerAdress() {
		return this.ServerAdress;
	}
	
	public String getClient() {
		return this.Client;
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
