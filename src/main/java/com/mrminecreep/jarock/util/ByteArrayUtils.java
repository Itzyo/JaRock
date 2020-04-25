package com.mrminecreep.jarock.util;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;

import com.mrminecreep.jarock.util.types.Position;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.ByteBufInputStream;
import io.netty.buffer.Unpooled;
import net.querz.nbt.CompoundTag;

public final class ByteArrayUtils {
	
	public static Boolean readBoolean(ByteBuf buf) {
		return buf.readBoolean();
	}
	
	public static void writeBoolean(ByteBuf buf, Boolean var) {
		buf.writeBoolean(var);
	}
	
	public static Byte readByte(ByteBuf buf) {
		return buf.readByte();
	}
	
	public static void writeByte(ByteBuf buf, Byte var) {
		buf.writeByte(var.intValue());
	}
	
	public static Short readUnsignedByte(ByteBuf buf) {
		return buf.readUnsignedByte();
	}
	
	public static void writeUnsignedByte(ByteBuf buf, Short var) {
		Byte temp = (byte) (var & 0xFF);
		buf.writeByte(temp);
	}
	
	public static Short readShort(ByteBuf buf) {
		return buf.readShort();
	}
	
	public static void writeShort(ByteBuf buf, Short var) {
		buf.writeShort(var);
	}
	
	public static Integer readUnsignedShort(ByteBuf buf) {
		return buf.readUnsignedShort();
	}
	
	public static void writeUnsignedShort(ByteBuf buf, Integer var) {
		Short temp = (short) (var & 0xFFFF);
		buf.writeShort(temp);
	}
	
	public static Integer readInteger(ByteBuf buf) {
		return buf.readInt();
	}
	
	public static void writeInteger(ByteBuf buf, Integer var) {
		buf.writeInt(var);
	}
	
	public static Long readLong(ByteBuf buf) {
		return buf.readLong();
	}
	
	public static void writeLong(ByteBuf buf, Long var) {
		buf.writeLong(var);
	}
	
	public static Float readFloat(ByteBuf buf) {
		return buf.readFloat();
	}
	
	public static void writeFloat(ByteBuf buf, Float var) {
		buf.writeFloat(var);
	}
	
	public static Double readDouble(ByteBuf buf) {
		return buf.readDouble();
	}
	
	public static void writeDouble(ByteBuf buf, Double var) {
		buf.writeDouble(var);
	}
	
	public static Integer readVarInt(ByteBuf buf) {
		int numRead = 0;
		int result = 0;
		byte read;
		
		do {
			read = buf.readByte();
			int value = (read & 0b01111111);
			result |= (value << (7 * numRead));
			
			numRead++;
			if(numRead > 5) {
				throw new RuntimeException("Unable to decode VarInt");
			}
		}while((read & 0b10000000) != 0);
		
		return result;
	}
	
	public static void writeVarInt(ByteBuf buf, Integer var) {
		do {
			byte temp = (byte)(var & 0b01111111);
		  
		    var >>>= 7;
		    if (var != 0) {
		    	temp |= 0b10000000;
		    }
		    	buf.writeByte(temp);
		    } while (var != 0);
	}
	
	public static String readString(ByteBuf buf) {
		Integer len = ByteArrayUtils.readVarInt(buf);
		byte[] bytes = new byte[len];
		buf.readBytes(bytes, 0, len);
		return new String(bytes, StandardCharsets.UTF_8);
		
	}
	
	public static void writeString(ByteBuf buf, String var) {
		byte[] str = var.getBytes(StandardCharsets.UTF_8);
		ByteArrayUtils.writeVarInt(buf, str.length);
		buf.writeBytes(str);
	}
	
	public static Position readPosition(ByteBuf buf) {
		Long pos = buf.readLong();
		return Position.decode(pos);
	}
	
	public static void writePosition(ByteBuf buf, Position var) {
		buf.writeLong(var.encode());
	}
	
	public static CompoundTag readNBT(ByteBuf buf) {
		
		DataInputStream dis = new DataInputStream(new ByteBufInputStream(buf));
		CompoundTag nbt = null;
		
		try {
			nbt = (CompoundTag) CompoundTag.deserialize(dis, CompoundTag.DEFAULT_MAX_DEPTH);
			buf.skipBytes(buf.readableBytes() - dis.available());
		} catch (IOException e) {
			e.printStackTrace();
		}	
		return nbt;
	}
	
	public static void writeNBT(ByteBuf buf, CompoundTag var) {
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		DataOutputStream w = new DataOutputStream(baos);
		
		try {
			var.serialize(w, CompoundTag.DEFAULT_MAX_DEPTH);
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		byte[] out = baos.toByteArray();
		buf.writeBytes(out);
	}
	
	public static UUID readUUID(ByteBuf buf) {
		Long mostSignificant = buf.readLong();
		Long leastSignificant = buf.readLong();
		
		return new UUID(mostSignificant, leastSignificant);
	}
	
	public static void writeUUID(ByteBuf buf, UUID uuid) {
		buf.writeLong(uuid.getMostSignificantBits());
		buf.writeLong(uuid.getLeastSignificantBits());
	}
	
	public static ByteBuf readByteArray(ByteBuf buf, Integer length) {
		ByteBuf out = Unpooled.buffer();
		buf.readBytes(out, length);
		return out;
	}
	
	public static void writeByteArray(ByteBuf buf, ByteBuf var) {
		buf.writeBytes(var);
		var.resetReaderIndex();
	}
}
