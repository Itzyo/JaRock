package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

import io.netty.buffer.ByteBuf;
import net.querz.nbt.CompoundTag;

public class ChunkDataEvent implements Event {
	
	Player p;
	Integer chunkX, chunkZ, PrimaryBitmask;
	Boolean FullChunk;
	CompoundTag Heightmaps;
	ByteBuf ChunkData;
	ArrayList<Object> data = new ArrayList<Object>();
	
	public ChunkDataEvent(Integer chunkX, Integer chunkZ, Boolean FullChunk, Integer PrimaryBitmask, CompoundTag Heightmaps, ByteBuf ChunkData, Player p) {
		
		this.chunkX = chunkX;
		this.chunkZ = chunkZ;
		this.FullChunk = FullChunk;
		this.PrimaryBitmask = PrimaryBitmask;
		this.Heightmaps = Heightmaps;
		this.ChunkData = ChunkData;
		this.p = p;
		
		this.data.add(this.getPacketID());
		this.data.add(this.chunkX);
		this.data.add(this.chunkZ);
		this.data.add(this.FullChunk);
		this.data.add(this.PrimaryBitmask);
		this.data.add(this.Heightmaps);
		this.data.add(this.ChunkData.readableBytes());
		this.data.add(this.ChunkData);
		this.data.add(0);
	}
	
	public Integer getChunkX() {
		return this.chunkX;
	}
	
	public Integer getChunkZ() {
		return this.chunkZ;
	}
	
	public Boolean isFullChunk() {
		return this.FullChunk;
	}
	
	public Integer getPrimaryBitMask() {
		return this.PrimaryBitmask;
	}
	
	public CompoundTag getHeightmaps() {
		return this.Heightmaps;
	}
	
	public ByteBuf getChunkData() {
		return this.ChunkData;
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
		return this.p;
	}

	@Override
	public int getPacketID() {
		return 33;
	}

	@Override
	public int getState() {
		return 3;
	}

}
