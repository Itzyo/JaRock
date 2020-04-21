package com.mrminecreep.jarock.event.events;

import java.util.ArrayList;

import com.mrminecreep.jarock.minecraft.Player;

/**
 * This interface is for events (See {@link com.mrminecreep.jarock.event.EventConstructor} for details).
 * 
 * @author MrMinecreep
 *
 */
public interface Event {
	
	public ArrayList<Object> data();
	
	public boolean send();
	
	/**
	 * Function to get the name of the event.
	 * 
	 * @return The name of the event.
	 */
	public String getEventString();
	
	public Player getPlayer();
	
	public int getPacketID();
	
	public int getState();
	
}
