package com.mrminecreep.jarock.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

import com.mrminecreep.jarock.event.events.Event;

/**
 * Registry for objects that have event listening functions in it. See {@link com.mrminecreep.jarock.event.EventListener} for details. <br>
 * To register an event listener call {@link com.mrminecreep.jarock.event.EventHandler#registerHandler(Object)}. <br>
 * To unregister an event listener call {@link com.mrminecreep.jarock.event.EventHandler#removeHandler(Object)}.
 * 
 * @author MrMinecreep
 *
 */
public class EventHandler {
	
	/**
	 * Storage for objects that implement functions for listening to events. <br>
	 * This field is altered by {@link com.mrminecreep.jarock.event.EventHandler#registerHandler(Object)} and {@link com.mrminecreep.jarock.event.EventHandler#removeHandler(Object)}.
	 * 
	 */
	private static ArrayList<Object> handlers = new ArrayList<Object>();
	
	/**
	 * Register an object which has event listening functions in it. See {@link com.mrminecreep.jarock.event.EventListener} for details.
	 * 
	 * @param Handler Object that will be registered as a Handler.
	 */
	public static void registerHandler(Object Handler) {
		handlers.add(Handler);
	}
	
	/**
	 * Unregister an object which has been registered by {@link com.mrminecreep.jarock.event.EventHandler#registerHandler(Object)}. See {@link com.mrminecreep.jarock.event.EventListener} for details.
	 * 
	 * @param Handler Object that will be removed.
	 */
	public static void removeHandler(Object Handler) {
		handlers.remove(Handler);
	}
	
	/**
	 * Finally call the event listening functions (see {@link com.mrminecreep.jarock.event.EventListener}. <br>
	 * It can only be called by {@link com.mrminecreep.jarock.event.EventRegistry}.
	 * 
	 * @param sec "Key" from {@link com.mrminecreep.jarock.event.EventRegistry}.
	 * @param e Event to pass to the listeners.
	 * @throws IllegalAccessException Auto-generated
	 * @throws IllegalArgumentException Auto-generated
	 * @throws InvocationTargetException Auto-generated
	 */
	public static void HandleEventPush(EventRegistry.EventRegistrySec sec, Event e) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		//Require key
		Objects.requireNonNull(sec);
		
		//loop over every handler
		for(Object h : handlers) {
			Class<?> clazz = h.getClass();
			
			//Loop over its functions
			for(Method m : clazz.getDeclaredMethods()) {
				
				//Check for annotations
				if(m.isAnnotationPresent(EventListener.class)) {
					m.setAccessible(true); //access private members
					EventListener l = m.getAnnotation(EventListener.class);
					
					//Check if the function subscribed to the current event
					if(l.value().equals(e.getClass().getName())) {
						m.invoke(h, e);
					}
				}
			}
			
		}
	}

}
