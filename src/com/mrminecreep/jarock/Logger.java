package com.mrminecreep.jarock;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * This class provides the functions for printing to the server console<br>
 * <b> Use this instead of System.out.println, ...</b>
 * 
 * @author MrMinecreep
 *
 */
public class Logger {
	
	/**
	 * This enumeration provides the definition of the log levels.<br><br>
	 * 
	 * Possible values:<br>
	 * <ul>
	 * 	<li>DEBUG</li>
	 * 	<li>INFO</li>
	 * 	<li>WARN</li>
	 * 	<li>ERROR</li>
	 * 	<li>FATAL</li>
	 * </ul>
	 * 
	 * @author MrMinecreep
	 */
	public static enum LogLevels {
		DEBUG,
		INFO,
		WARN,
		ERROR,
		FATAL
	}
	
	/**
	 * Current log level.
	 */
	private static LogLevels LogLevel = LogLevels.DEBUG;
	
	/**
	 * Generic logging function<br>
	 * Prints a message of the following format to stderr: {@code [TIME LOGLEVEL] MESSAGE}<br>
	 * <b>This function should not be called directly!</b>
	 * @param msg actual message to print.
	 * @param level the severity of the message, see {@link com.mrminecreep.jarock.Logger.LogLevels} for details.
	 */
	private static void log_log(String msg, LogLevels level) {
		
		if(level.compareTo(LogLevel) >= 0) { //if level >= LogLevel
			
			//Get time
			DateFormat dateF = new SimpleDateFormat("H:m:s");	
			Date date = new Date();
			
			//Print msg
			System.err.println(String.format("[%s %s] %s", dateF.format(date), level, msg));
		}
	}
	
	/**
	 * Sets the global log level.
	 * @param level the severity of the level, see {@link com.mrminecreep.jarock.Logger.LogLevels} for details.
	 */
	public static void set_log_level(LogLevels level) {
		LogLevel = level;
	}
	
	/**
	 * Gets the global log level.
	 * @return the current log level, see {@link com.mrminecreep.jarock.Logger.LogLevels} for details.
	 */
	public static LogLevels get_log_level() {
		return LogLevel;
	}
	
	/**
	 * Log function for logging debug output.<br>
	 * See {@link Logger#log_log} for details.
	 * @param msg actual message to print 
	 * @param params format strings i.e for %d,...
	 */
	public static void log_debug(String msg, Object... params) {
		log_log(String.format(msg, params), LogLevels.DEBUG);
	}
	
	/**
	 * Log function for logging info output.<br>
	 * See {@link Logger#log_log} for details.
	 * @param msg actual message to print 
	 * @param params format strings i.e for %d,...
	 */
	public static void log_info(String msg, Object... params) {
		log_log(String.format(msg, params), LogLevels.INFO);
	}
	
	/**
	 * Log function for logging warning output.<br>
	 * See {@link Logger#log_log} for details.
	 * @param msg actual message to print 
	 * @param params format strings i.e for %d,...
	 */
	public static void log_warn(String msg, Object... params) {
		log_log(String.format(msg, params), LogLevels.WARN);
	}
	
	/**
	 * Log function for logging error output (Severe but not fatal incident).<br>
	 * See {@link Logger#log_log} for details.
	 * @param msg actual message to print 
	 * @param params format strings i.e for %d,...
	 */
	public static void log_error(String msg, Object... params) {
		log_log(String.format(msg, params), LogLevels.ERROR);
	}
	
	/**
	 * Log function for logging fatal output (Only if server exits).<br>
	 * See {@link Logger#log_log} for details.
	 * @param msg actual message to print 
	 * @param params format strings i.e for %d,...
	 */
	public static void log_fatal(String msg, Object... params) {
		log_log(String.format(msg, params), LogLevels.FATAL);
	}

}
