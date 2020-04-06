package com.mrminecreep.jarock;

import java.util.HashMap;

/**
 * Registry for referencing globally used objects.
 * 
 * @author MrMinecreep
 *
 */
public class GlobalObjectRegistry {
	
	/**
	 * Container for global Objects.
	 */
	private static HashMap<Integer, Object> objs = new HashMap<Integer, Object>();
	
	/**
	 * Register a new global object with an <b>unique</b> id.
	 * 
	 * @param obj object to register.
	 * @param id id to link with the object.
	 */
	public static void RegisterObject(Object obj, int id) {
		objs.put(id, obj);
	}
	
	/**
	 * Get the Object linked with an id.
	 * @param id the id the object was linked with.
	 * @return the requested object.
	 */
	public static Object get(int id) {
		return objs.get(id);
	}
	
	/**
	 * Unregisters a global object.
	 * @param id the id of the object to unregister.
	 */
	public static void unregister(int id) {
		objs.remove(id);
	}
		
}
