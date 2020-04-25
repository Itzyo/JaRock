package com.mrminecreep.jarock.event;

import com.mrminecreep.jarock.event.events.ChunkDataEvent;
import com.mrminecreep.jarock.event.events.Event;
import com.mrminecreep.jarock.event.events.HandshakeEvent;
import com.mrminecreep.jarock.event.events.HeldItemChangeEvent;
import com.mrminecreep.jarock.event.events.JoinGameEvent;
import com.mrminecreep.jarock.event.events.KeepAliveEventRecv;
import com.mrminecreep.jarock.event.events.KeepAliveEventSend;
import com.mrminecreep.jarock.event.events.LoginStartEvent;
import com.mrminecreep.jarock.event.events.LoginSuccessEvent;
import com.mrminecreep.jarock.event.events.PlayerInfoAddPlayerEvent;
import com.mrminecreep.jarock.event.events.PlayerInfoRemovePlayerEvent;
import com.mrminecreep.jarock.event.events.PlayerPositionAndLookEvent;
import com.mrminecreep.jarock.event.events.PlayerPositionUpdateEvent;
import com.mrminecreep.jarock.event.events.PlayerRotationUpdateEvent;
import com.mrminecreep.jarock.event.events.SpawnPositionEvent;
import com.mrminecreep.jarock.minecraft.Player;
import com.mrminecreep.jarock.util.types.Position;

import io.netty.buffer.ByteBuf;
import net.querz.nbt.CompoundTag;

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
	
	public static void createHandshakeEvent(Object ProtocolVersion, Object ServerAddress, Object ServerPort, Object NextState, String Client) {
		HandshakeEvent e = new HandshakeEvent((Integer) ProtocolVersion, (String) ServerAddress, (Integer) ServerPort, (Integer) NextState, Client);
		push(e);
	}
	
	public static void createLoginStartEvent(Object Username, String Client, boolean isBedrock) {
		LoginStartEvent e = new LoginStartEvent((String) Username, Client, isBedrock);
		push(e);
	}
	
	public static void createLoginSuccessEvent(Object Player) {
		LoginSuccessEvent e = new LoginSuccessEvent((Player) Player);
		push(e);
	}
	
	 public static void createJoinGameEvent(Object EntityID, byte Gamemode, Object Dimension, Object LevelType, Object ViewDistance, Object ReducedDebugInfo, Object Player) {
		 JoinGameEvent e = new JoinGameEvent((Integer) EntityID, Gamemode, (Integer) Dimension, (String) LevelType, (Integer) ViewDistance, (Boolean) ReducedDebugInfo, (Player) Player);
		 push(e);
	 }
	 
	 public static void createHeldItemChangeEvent(Object Slot, Player Player) {
		 HeldItemChangeEvent e = new HeldItemChangeEvent((Byte) Slot, Player);
		 push(e);
	 }
	 
	 public static void createSpawnPositionEvent(Object pos, Player p) {
		 SpawnPositionEvent e = new SpawnPositionEvent((Position) pos, p);
		 push(e);
	 }
	 
	 public static void createPlayerPositionAndLookEvent(Object X, Object Y, Object Z, Object Yaw, Object Pitch, Object Flags, Object TeleportID, Player p, Boolean send) {
		 PlayerPositionAndLookEvent e = new PlayerPositionAndLookEvent((Double) X, (Double) Y, (Double) Z, (Float) Yaw, (Float) Pitch, (Byte) Flags, (Integer) TeleportID, p, send);
		 push(e);
	 }
	 
	 public static void createKeepAliveEventRecv(Object KeepAliveID, Player Player) {
		 KeepAliveEventRecv e = new KeepAliveEventRecv((Long) KeepAliveID, Player);
		 push(e);
	 }
	 
	 public static void createKeepAliveEventSend(Object KeepAliveID) {
		 KeepAliveEventSend e = new KeepAliveEventSend((Long) KeepAliveID);
		 push(e);
	 }
	 
	 public static void createPlayerPositionUpdateEvent(Object X, Object Feet_Y, Object Z, Object OnGround, Player p) {
		 PlayerPositionUpdateEvent e = new PlayerPositionUpdateEvent((Double) X, (Double) Feet_Y, (Double) Z, (Boolean) OnGround, p);
		 push(e);
	 }
	 
	 public static void createPlayerRotationUpdateEvent(Object Yaw, Object Pitch, Object OnGround, Player p) {
		 PlayerRotationUpdateEvent e = new PlayerRotationUpdateEvent((Float) Yaw, (Float) Pitch, (Boolean) OnGround, p);
		 push(e);
	 }
	 
	 public static void createPlayerInfoAddPlayerEvent() {
		 PlayerInfoAddPlayerEvent e = new PlayerInfoAddPlayerEvent();
		 push(e);
	 }
	 
	 public static void createPlayerInfoRemovePlayerEvent(Player p) {
		 PlayerInfoRemovePlayerEvent e = new PlayerInfoRemovePlayerEvent(p);
		 push(e);
	 }
	 
	 public static void createChunkDataEvent(Object chunkX, Object chunkZ, Object FullChunk, Object PrimaryBitmask, Object Heightmaps, Object ChunkData, Player p) {
		 ChunkDataEvent e = new ChunkDataEvent((Integer) chunkX, (Integer) chunkZ, (Boolean) FullChunk, (Integer) PrimaryBitmask, (CompoundTag) Heightmaps, (ByteBuf) ChunkData, p);
		 push(e);
	 }
	
	/**
	 * Private function that is invoked by every {@code createEvent(...)} function to send the event to all listening functions (see {@link com.mrminecreep.jarock.event.EventListener} for details).
	 * 
	 * @param e Event to fire.
	 */
	private static synchronized void push(Event e) {
		Thread async = new Thread(() -> { EventRegistry.pushEvent(sec, e); });
		async.start();
	}
	
}
