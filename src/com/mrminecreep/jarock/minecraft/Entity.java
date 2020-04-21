package com.mrminecreep.jarock.minecraft;

public class Entity {
	
	private int x, y, z;
	private float yaw, pitch;
	private boolean OnGround;
	
	public boolean OnGround() {
		return this.OnGround;
	}
	
	public void setOnGround(boolean OnGround) {
		this.OnGround = OnGround;
	}
	
	public float getYaw() {
		return this.yaw;
	}
	
	public float getPitch() {
		return this.pitch;
	}
	
	public void setYaw(float yaw) {
		this.yaw = yaw;
	}
	
	public void setPitch(float pitch) {
		this.pitch = pitch;
	}
	
	public int getX() {
		return this.x;
	}
	
	public int getY() {
		return this.y;
	}
	
	public int getZ() {
		return this.z;
	}
	
	public void setX(int X) {
		this.x = X;
	}
	
	public void setY(int y) {
		this.y = y;
	}
	
	public void setZ(int z) {
		this.z = z;
	}

}
