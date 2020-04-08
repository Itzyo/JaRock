package com.mrminecreep.jarock.event.events;

/**
 * This interface is for events (See {@link com.mrminecreep.jarock.event.EventConstructor} for details).
 * 
 * @author MrMinecreep
 *
 */
public interface Event {
	
	/**
	 * Function to get the name of the event.
	 * 
	 * @return The name of the event.
	 */
	public String getEventString();
	
}
