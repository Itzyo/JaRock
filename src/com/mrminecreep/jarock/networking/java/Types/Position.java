package com.mrminecreep.jarock.networking.java.Types;

public class Position {
	
	private Integer x, y, z;
	
	public Position(Integer x, Integer y, Integer z) {
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Integer getX() {
		return this.x;
	}
	
	public Integer getY() {
		return this.y;
	}
	
	public Integer getZ() {
		return this.z;
	}
	
	public Long encode() {
		return (long) (((x & 0x3FFFFFF) << 38) | ((z & 0x3FFFFFF) << 12) | (y & 0xFFF));
	}
	
	public static Position decode(Long position) {
		int x = (int) (position >> 38);
		int y = (int) (position & 0xFFF);
		int z = (int) (position << 26 >> 38);
		
		return new Position(x, y, z);
	}

}
