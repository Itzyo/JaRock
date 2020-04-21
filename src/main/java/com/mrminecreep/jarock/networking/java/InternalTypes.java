package com.mrminecreep.jarock.networking.java;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;

import com.mrminecreep.jarock.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;

public class InternalTypes {
	
	public static Integer readVarInt(ByteBuf in) throws Exception{
		int numRead = 0;
		int result = 0;
		byte read;
		
		do {
			read = in.readByte();
			int value = (read & 0b01111111);
			result |= (value << (7 * numRead));
			
			numRead++;
			if(numRead > 5) {
				throw new RuntimeException("Unable to decode VarInt");
			}
		}while((read & 0b10000000) != 0);
		
		return result;
	}
	
	public static Integer getVarInt(ByteBuf in, int index) throws Exception{
		int numRead = 0;
		int result = 0;
		byte read;
		
		do {
			read = in.getByte(index + numRead);
			int value = (read & 0b01111111);
			result |= (value << (7 * numRead));
			
			numRead++;
			if(numRead > 5) {
				throw new RuntimeException("Unable to decode VarInt");
			}
		}while((read & 0b10000000) != 0);
		
		return result;
	}
	
	public static Long readVarLong(ByteBuf in) throws Exception{
	    int numRead = 0;
	    long result = 0;
	    byte read;
	    do {
	        read = in.readByte();
	        int value = (read & 0b01111111);
	        result |= (value << (7 * numRead));

	        numRead++;
	        if (numRead > 10) {
	            throw new RuntimeException("Unable to decode VarLong");
	        }
	    } while ((read & 0b10000000) != 0);

	    return result;
	}
	
	public static byte[] writeVarInt(int value) {
		byte[] container = new byte[4];
		int index = 0;
		
		do {
	        byte temp = (byte)(value & 0b01111111);

	        value >>>= 7;
	        if (value != 0) {
	            temp |= 0b10000000;
	        }
	        container[index] = temp;
	        index++;
	    } while (value != 0 && index < 4);
		
		byte[] out = new byte[index];
		System.arraycopy(container, 0, out, 0, index);
		return out;
	}
	
	public static String readString(ByteBuf in) throws Exception {
		Logger.log_debug("Trying to decode string with %d readable bytes left", in.readableBytes());
		Integer len = InternalTypes.readVarInt(in);
		Logger.log_debug("String length is %d, while %d readable bytes left", len, in.readableBytes());
		byte[] bytes = new byte[len];
		in.readBytes(bytes, 0, len);
		return new String(bytes, StandardCharsets.UTF_8);
	}
	
	public static byte[] writeString(String value) {
		byte[] str = value.getBytes(StandardCharsets.UTF_8);
		byte[] len = InternalTypes.writeVarInt(str.length);
		byte[] out = new byte[len.length + str.length];
		System.arraycopy(len, 0, out, 0, len.length);
		System.arraycopy(str, 0, out, len.length, str.length);
		return out;
	}


}
