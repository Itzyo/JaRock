package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;
import java.util.UUID;

import com.mrminecreep.jarock.minecraft.Player;
import com.mrminecreep.jarock.minecraft.registry.PlayerRegistry;
import com.mrminecreep.jarock.networking.java.InternalTypes;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class PlayerInfoAddPlayerEvent implements Event {
	
	private ArrayList<Object> data = new ArrayList<Object>(); 
	
	public PlayerInfoAddPlayerEvent() {
		
		ArrayList<Player> players = PlayerRegistry.getPlayers();
		
		this.data.add(new Byte((byte) 0));
		this.data.add(this.getPacketID());
		this.data.add(0);
		this.data.add(players.size());
		
		ByteBuf buf = Unpooled.buffer();
		
		for(Player p: players) {
			
			String uuid = p.getUUID();
			uuid = uuid.substring(0, 8) + "-" + uuid.substring(8, 12) + "-" + uuid.substring(12, 16) + "-" + uuid.substring(16, 20) + "-" + uuid.substring(20, 32);
			buf.writeLong(UUID.fromString(uuid).getMostSignificantBits());
			buf.writeLong(UUID.fromString(uuid).getLeastSignificantBits());
			
			buf.writeBytes(InternalTypes.writeString(p.getUsername()));
			buf.writeBytes(InternalTypes.writeVarInt(0));
			
			buf.writeBytes(InternalTypes.writeVarInt(p.getGamemode().intValue()));
			buf.writeBytes(InternalTypes.writeVarInt(2));
			buf.writeBoolean(false);
		}
		
		//buf.retain(players.size());
		this.data.add(buf);
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
