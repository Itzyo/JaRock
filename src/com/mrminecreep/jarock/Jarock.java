package com.mrminecreep.jarock;

import com.mrminecreep.jarock.event.EventRegistry;
import com.mrminecreep.jarock.event.events.HandshakeRequestEvent;

public class Jarock {

	public static void main(String[] args) {
		preInit();
		Init();
		postInit();
		afterInit();
		shutdown();
	}
	
	public static void preInit() {
		EventRegistry.registerEvent(HandshakeRequestEvent.class);
	}
	
	public static void Init() {
		
	}
	
	public static void postInit() {
		
	}
	
	public static void afterInit() {
		
	}
	
	public static void shutdown() {
		
	}
	
}
