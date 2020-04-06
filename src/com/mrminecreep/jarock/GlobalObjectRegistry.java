package com.mrminecreep.jarock;

import java.util.HashMap;
//1234
public class GlobalObjectRegistry {
	
	private static HashMap<Integer, Object> objs = new HashMap<Integer, Object>();
	
	public static void RegisterObject(Object obj, int id) {
		objs.put(id, obj);
	}
	
	public static Object get(int id) {
		return objs.get(id);
	}
	
	public static void unregister(int id) {
		objs.remove(id);
	}
		
}
