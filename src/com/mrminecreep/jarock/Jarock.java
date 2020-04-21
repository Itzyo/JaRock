package com.mrminecreep.jarock;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;
import java.util.Date;

import com.mrminecreep.jarock.event.EventHandler;
import com.mrminecreep.jarock.event.EventRegistry;
import com.mrminecreep.jarock.event.events.HandshakeEvent;
import com.mrminecreep.jarock.event.events.HeldItemChangeEvent;
import com.mrminecreep.jarock.event.events.JoinGameEvent;
import com.mrminecreep.jarock.event.events.KeepAliveEventRecv;
import com.mrminecreep.jarock.event.events.KeepAliveEventSend;
import com.mrminecreep.jarock.event.events.LoginStartEvent;
import com.mrminecreep.jarock.event.events.LoginSuccessEvent;
import com.mrminecreep.jarock.event.events.PlayerInfoAddPlayerEvent;
import com.mrminecreep.jarock.event.events.PlayerInfoRemovePlayerEvent;
import com.mrminecreep.jarock.event.events.PlayerInfoUpdateLatencyEvent;
import com.mrminecreep.jarock.event.events.PlayerPositionAndLookEvent;
import com.mrminecreep.jarock.event.events.PlayerPositionUpdateEvent;
import com.mrminecreep.jarock.event.events.PlayerRotationUpdateEvent;
import com.mrminecreep.jarock.event.events.SpawnPositionEvent;
import com.mrminecreep.jarock.minecraft.KeepAliveTicker;
import com.mrminecreep.jarock.minecraft.handler.GameHandler;
import com.mrminecreep.jarock.minecraft.handler.GeneralPurposeHandler;
import com.mrminecreep.jarock.minecraft.handler.LoginHandler;
import com.mrminecreep.jarock.networking.java.JavaSocket;

public class Jarock {

	public static void main(String[] args) throws Exception {
		preInit();
		Init();
		postInit();
		afterInit();
		((KeepAliveTicker) GlobalObjectRegistry.get(1)).join();
		shutdown();
	}
	
	public static void preInit() throws FileNotFoundException {
		
		Date date = new Date();
		File logFolder = new File("Log");
		if(!logFolder.exists() || !logFolder.isDirectory()) {
			logFolder.mkdir();
		}
		PrintStream o = new PrintStream(new File(String.format("Log/log-%d", date.getTime())));
		System.setOut(o);
		
		EventRegistry.registerEvent(HandshakeEvent.class);
		EventRegistry.registerEvent(LoginStartEvent.class);
		EventRegistry.registerEvent(LoginSuccessEvent.class);
		EventRegistry.registerEvent(JoinGameEvent.class);
		EventRegistry.registerEvent(HeldItemChangeEvent.class);
		EventRegistry.registerEvent(SpawnPositionEvent.class);
		EventRegistry.registerEvent(PlayerPositionAndLookEvent.class);
		EventRegistry.registerEvent(KeepAliveEventRecv.class);
		EventRegistry.registerEvent(KeepAliveEventSend.class);
		EventRegistry.registerEvent(PlayerPositionUpdateEvent.class);
		EventRegistry.registerEvent(PlayerRotationUpdateEvent.class);
		EventRegistry.registerEvent(PlayerInfoUpdateLatencyEvent.class);
		EventRegistry.registerEvent(PlayerInfoAddPlayerEvent.class);
		EventRegistry.registerEvent(PlayerInfoRemovePlayerEvent.class);
		EventHandler.registerHandler(new LoginHandler());
		EventHandler.registerHandler(new GeneralPurposeHandler());
		EventHandler.registerHandler(new GameHandler());
	}
	
	public static void Init() throws Exception {
		 int port = 25565;
		 Logger.log_info("Starting Java server");
	     new Thread(() -> {try {
			GlobalObjectRegistry.RegisterObject(new JavaSocket(port), 0);
			((JavaSocket) GlobalObjectRegistry.get(0)).run();
	     } catch (Exception e) {
			Logger.log_fatal("Could not start Java server");
			e.printStackTrace();
	     }}).start();
	     Logger.log_info("Java server started successfully");
	}
	
	public static void postInit() {
		KeepAliveTicker t = new KeepAliveTicker();
		GlobalObjectRegistry.RegisterObject(t, 1);
		t.start();
	}
	
	public static void afterInit() {
		
	}
	
	public static void shutdown() throws InterruptedException {
		((KeepAliveTicker) GlobalObjectRegistry.get(1)).kill();
		((KeepAliveTicker) GlobalObjectRegistry.get(1)).join();
		GlobalObjectRegistry.unregister(1);
	}
	
}
