package com.mrminecreep.jarock.event;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;

import com.mrminecreep.jarock.event.events.Event;

/**
 * Registry for events implementing {@link com.mrminecreep.jarock.event.events.Event}. <br>
 * To register an event call {@link EventRegistry#registerEvent(Event)}, <br>
 * after that event listeners (as described in {@link com.mrminecreep.jarock.event.EventHandler}) can be added. <br>
 * To unregister an event call {@link EventRegistry#unregisterEvent(Event)}, <br>
 * after that the specified event fired by {@link com.mrminecreep.jarock.event.EventConstructor} will not be working.
 * 
 * @author MrMinecreep
 *
 */
public class EventRegistry {
	
	/**
	 * Class used as a "key" for function calls that can only be executed by the {@link com.mrminecreep.jarock.event.EventRegistry} class.
	 * 
	 * @author MrMinecreep
	 *
	 */
	public static final class EventRegistrySec{ private EventRegistrySec() {} }
	
	/**
	 * Private "key" field. See {@link com.mrminecreep.jarock.event.EventRegistry.EventRegistrySec} for details.
	 */
	private static final EventRegistrySec sec = new EventRegistrySec();
	
	/** 
	 * Storage for registered events.<br>
	 * This field is altered by {@link EventRegistry#registerEvent(Event)} and {@link EventRegistry#unregisterEvent(Event)}.
	 */
	private static ArrayList<Class<?>> Events = new ArrayList<Class<?>>();
	
	/**
	 * Registers an event, so that it can be subscribed to.
	 * 
	 * @param Event Event class of the event to register.
	 */
	public static void registerEvent(Class<? extends Event> Event) {
		Events.add(Event);
	}
	
	/**
	 * Unregisters an event.
	 * 
	 * @param Event Event class to unregister.
	 */
	public static void unregisterEvent(Class<? extends Event> Event) {
		Events.remove(Event);
	}
	
	/**
	 * Pass event to the {@link com.mrminecreep.jarock.event.EventHandler} and checks if Event was registered.
	 * 
	 * @param sec "Key" from {@link com.mrminecreep.jarock.event.EventConstructor}.
	 * @param e Event object to pass on.
	 */
	public static void pushEvent(EventConstructor.EventConstructorSec sec2, Event e) {
		Objects.requireNonNull(sec2);
		if(Events.contains(e.getClass())) {
			try {
				EventHandler.HandleEventPush(sec, e);
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
