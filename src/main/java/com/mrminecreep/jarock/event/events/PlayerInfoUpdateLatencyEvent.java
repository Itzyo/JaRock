package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;
import java.util.UUID;

import com.mrminecreep.jarock.minecraft.Player;

public class PlayerInfoUpdateLatencyEvent implements Event {
	
	private Player p;
	private ArrayList<Object> data = new ArrayList<Object>();
	
	public PlayerInfoUpdateLatencyEvent(Player p) {
		this.p = p;
		
		this.data.add(new Byte((byte) 2));
		this.data.add(this.getPacketID());
		this.data.add(2);
		this.data.add(1);
		String uuid = p.getUUID();
		uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
		this.data.add(UUID.fromString(uuid).getMostSignificantBits());
		this.data.add(UUID.fromString(uuid).getLeastSignificantBits());
		this.data.add(p.getPing().intValue());
	}

	@Override
	public ArrayList<Object> data() {
		return this.data;
	}

	@Override
	public boolean send() {
		return true;
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
		return 51;
	}

	@Override
	public int getState() {
		return 3;
	}

}
