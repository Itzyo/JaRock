package com.mrminecreep.jarock.event;

import com.mrminecreep.jarock.event.events.Event;

/**
 * Constructor class that provides functions for creating events with parameters. <br>
 * The basic usage is that first a class that implements {@link com.mrminecreep.jarock.event.events.Event} interface has to be created. <br>
 * Then the event has to be registered with a call to {@link com.mrminecreep.jarock.event.EventRegistry#registerEvent(Class)}. <br>
 * The third step is to create a handler function which is marked by {@link com.mrminecreep.jarock.event.EventListener}. <br>
 * The objects containing this function have to be registered with a call {@link com.mrminecreep.jarock.event.EventHandler#registerHandler(Object)} <br>
 * Lastly a function creating the event have to be implemented here and it has to call {@link com.mrminecreep.jarock.event.EventConstructor#push(Event)} <br>
 * Finally this function can be invoked from everywhere and every listener function will listen.
 * 
 * @author MrMinecreep
 *
 */
public class EventConstructor {
	
	/**
	 * Class used as a "key" for function calls that can only be executed by the {@link com.mrminecreep.jarock.event.EventConstructor} class.
	 * 
	 * @author MrMinecreep
	 *
	 */
	public static final class EventConstructorSec{ private EventConstructorSec() {} }
	
	/**
	 * Private "key" field. See {@link com.mrminecreep.jarock.event.EventConstructor.EventConstructorSec} for details.
	 */
	public static final EventConstructorSec sec = new EventConstructorSec();
	
	/**
	 * Private function that is invoked by every {@code createEvent(...)} function to send the event to all listening functions (see {@link com.mrminecreep.jarock.event.EventListener} for details).
	 * 
	 * @param e Event to fire.
	 */
	@SuppressWarnings("unused")
	private static void push(Event e) {
		EventRegistry.pushEvent(sec, e);
	}
	
}
