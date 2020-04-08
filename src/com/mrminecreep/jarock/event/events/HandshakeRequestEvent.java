package com.mrminecreep.jarock.event.events;

public class HandshakeRequestEvent implements Event{
	
	private int protocolVersion, ServerPort, nextState;
	private String ServerAdress;
	
	public HandshakeRequestEvent(int protocolVersion, String ServerAdress, int ServerPort, int nextState) {
		this.protocolVersion = protocolVersion;
		this.ServerPort = ServerPort;
		this.nextState = nextState;
		this.ServerAdress = ServerAdress;
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
	
	public int getProtocolVersion() {
		return this.protocolVersion;
	}
	
	public int getServerPort() {
		return this.ServerPort;
	}
	
	public int getNextState() {
		return this.nextState;
	}
	
	public String getServerAdress() {
		return this.ServerAdress;
	}

}
