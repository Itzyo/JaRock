package com.mrminecreep.jarock.event;

import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;


/**
 * Simple annotation for marking event listening functions. <br>
 * It has to be provided with the (full package) path of a class that implements the {@link com.mrminecreep.jarock.event.events.Event} interface. <br>
 * For usage in terms of subscribing to an event see {@link com.mrminecreep.jarock.event.EventHandler}.
 * 
 * @author MrMinecreep
 *
 */
@Retention(RUNTIME)
@Target(METHOD)
public @interface EventListener {
	public String value() default "";
}
