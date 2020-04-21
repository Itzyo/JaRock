package com.mrminecreep.jarock.minecraft.registry;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Entity;

public class EntityRegistry {
	
	private static ArrayList<Entity> entities = new ArrayList<Entity>();
	
	public static void registerEntity(Entity e) {
		entities.add(e);
	}
	
	public static void unregisterEntity(Entity e) {
		entities.remove(e);
	}
	
	public static int getEntityID(Entity e) {
		return entities.indexOf(e);
	}
	
	public static Entity getEntityByID(int id) {
		return entities.get(id);
	}

}
