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
	
	public static void registerHandler(Object Handler) {
		handlers.add(Handler);
	}
	
	public static void removeHandler(Object Handler) {
		handlers.remove(Handler);
	}
	
	public static void HandleEventPush(EventRegistry.EventRegistrySec sec, Event e) throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		Objects.requireNonNull(sec);
		
		for(Object h : handlers) {
			Class<?> clazz = h.getClass();
			
			for(Method m : clazz.getDeclaredMethods()) {
				if(m.isAnnotationPresent(EventListener.class)) {
					m.setAccessible(true);
					EventListener l = m.getAnnotation(EventListener.class);
					
					if(l.value().equals(e.getClass().getName())) {
						m.invoke(h, e);
					}
				}
			}
			
		}
	}

}
