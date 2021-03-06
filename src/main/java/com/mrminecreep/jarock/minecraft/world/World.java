package com.mrminecreep.jarock.minecraft.world;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.event.EventConstructor;
import com.mrminecreep.jarock.minecraft.Player;
import com.mrminecreep.jarock.util.ByteArrayUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.querz.nbt.CompoundTag;
import net.querz.nbt.LongArrayTag;

public class World {
	
	public static void sendChunksToPlayer(Player p) {
		Logger.log_debug("Constructing chunks for %s", p.getUsername());
		int chunkX = p.getX() / 16;
		int chunkZ = p.getZ() / 16;
		
		for(int i = chunkX - 3; i <= chunkX + 3; i++) {
			for(int x = chunkZ - 3; x <= chunkZ + 3; x++) {
				EventConstructor.createChunkDataEvent(i, x, true, 128, World.genHeightmaps(), World.genChunkData(i, x), null);
			}
		}
	}
	
	/*
	    0 - 15: 1
		16 - 31: 2
		32 - 47: 3
		48 - 63: 4
		64 - 79: 5
		80 - 95: 6
		96 - 111: 7
		112 - 127: 8
		
		128 - 143: 9
		144 - 159: 10
		160 - 175: 11
		176 - 191: 12
		192 - 207: 13
		208 - 223: 14
		224 - 239: 15
		240 - 255: 16
	 */
	private static ByteBuf genChunkData(int x, int z) {
		
		ByteBuf buf = Unpooled.buffer();
		ByteArrayUtils.writeShort(buf, (short) 256);
		ByteArrayUtils.writeByte(buf, (byte) 14);
		ByteArrayUtils.writeVarInt(buf, 16 * 16 * 16 * 14 / 64);
		
		long[] data = new long[16 * 16 * 16 * 14 / 64];
		
		int individualValueMask = (int)((1 << 14) - 1);
		
		for(int Y = 0; Y < 16; Y++) {
			for(int Z = 0; Z < 16; Z++) {
				for(int X = 0; X < 16; X++) {
					int blockNumber = (((Y * 16) + Z) * 16) + X;
					int startLong = (blockNumber * 14) / 64;
	                int startOffset = (blockNumber * 14) % 64;
	                int endLong = ((blockNumber + 1) * 14 - 1) / 64;
	                
	                long v = 0;
	                if(Y == 5)
	                	v = 9;
	                
	                v &= individualValueMask;
	                data[startLong] |= (v << startOffset);

	                if (startLong != endLong) {
	                    data[endLong] = (v >> (64 - startOffset));
	                }
				}
			}
		}
		
		for(long l : data) {
			ByteArrayUtils.writeLong(buf, l);
		}
		
		for(int zt = 0; zt < 16; zt++) {
			for(int xs = 0; xs < 16; xs++) {
				ByteArrayUtils.writeInteger(buf, 127);
			}
		}
		
		return buf;
	}
	
	private static CompoundTag genHeightmaps() {
		CompoundTag nbt = new CompoundTag();
		long[] heightmap = new long[36];
		int c = 0;
		int mask = (int)((1 << 9) - 1);
		
		for(int i = 0; i < 16; i++) {
			for(int q = 0; q < 16; q++) {
				int startLong = (c * 9) / 64;
	            int startOffset = (c * 9) % 64;
	            int endLong = ((c + 1) * 9 - 1) / 64;
	            
	            long v = 100;
	            
	            v &= mask;
	            heightmap[startLong] |= (v << startOffset);
	
	            if (startLong != endLong) {
	                heightmap[endLong] = (v >> (64 - startOffset));
	            }
	            c++;
			}
		}
		nbt.put("MOTION_BLOCKING", new LongArrayTag(heightmap));
		
		return nbt;
	}

}
