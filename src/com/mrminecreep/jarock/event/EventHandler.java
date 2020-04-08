package com.mrminecreep.jarock.event;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Objects;

import com.mrminecreep.jarock.event.events.Event;

/**
 * 
 * @author MrMinecreep
 *
 */
public class EventHandler {
	
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
