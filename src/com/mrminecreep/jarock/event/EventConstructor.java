package com.mrminecreep.jarock.event;

import com.mrminecreep.jarock.event.events.Event;

public class EventConstructor {
	
	public static final class EventConstructorSec{ private EventConstructorSec() {} }
	public static final EventConstructorSec sec = new EventConstructorSec();
		
	private static void push(Event e) {
		EventRegistry.pushEvent(sec, e);
	}
	
}
