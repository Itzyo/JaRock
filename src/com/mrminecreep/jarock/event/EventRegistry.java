package com.mrminecreep.jarock.event;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Objects;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.event.events.Event;
import com.mrminecreep.jarock.minecraft.Player;
import com.mrminecreep.jarock.minecraft.registry.PlayerRegistry;
import com.mrminecreep.jarock.networking.ClientRegistry;

import io.netty.channel.Channel;

/**
 * Registry for events implementing {@link com.mrminecreep.jarock.event.events.Event}. <br>
 * To register an event call {@link com.mrminecreep.jarock.event.EventRegistry#registerEvent(Class)}, <br>
 * after that event listeners (as described in {@link com.mrminecreep.jarock.event.EventHandler}) can be added. <br>
 * To unregister an event call {@link com.mrminecreep.jarock.event.EventRegistry#unregisterEvent(Class)}, <br>
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
	 * This field is altered by {@link com.mrminecreep.jarock.event.EventRegistry#registerEvent(Class)} and {@link com.mrminecreep.jarock.event.EventRegistry#unregisterEvent(Class)}.
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
	 * @param sec2 "Key" from {@link com.mrminecreep.jarock.event.EventConstructor}.
	 * @param e Event object to pass on.
	 */
	public static void pushEvent(EventConstructor.EventConstructorSec sec2, Event e) {
		//Require key
		Objects.requireNonNull(sec2);
		
		//Check if event has been registered.
		if(Events.contains(e.getClass())) {
			try {
				if(e.getPacketID() >= 0 && e.getState() >= 0 && e.send()) {

					if(e.getPlayer() == null) {
						for(Player p : PlayerRegistry.getPlayers()) {
							Channel c = ClientRegistry.getChannel(p.getClient());
							
							while(!c.isWritable() && c.isActive()) {
								Thread.currentThread();
								Thread.sleep(50);
								Logger.log_warn("Channel for client %s is not writable!", c.remoteAddress().toString());
							}
							
							Logger.log_debug("Entering pipeline for packet with id %d (Sending to player %s)", e.getPacketID(), p.getUsername());
							if(c.isWritable()) {
								c.writeAndFlush(e.data()).sync();
							}
						}
					} else {
						Channel c = ClientRegistry.getChannel(e.getPlayer().getClient());
						
						while(!c.isWritable() && c.isActive()) {
							Thread.currentThread();
							Thread.sleep(50);
							Logger.log_warn("Channel for client %s is not writable!", c.remoteAddress().toString());
						}
						
						Logger.log_debug("Entering pipeline for packet with id %d", e.getPacketID());
						if(c.isWritable()) {
							c.writeAndFlush(e.data()).sync();
						}
					}
				}
				
				//Pass event to the EventHandler
				EventHandler.HandleEventPush(sec, e);
				
				
			} catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException | InterruptedException e1) {
				e1.printStackTrace();
			}
		}
	}
	
}
