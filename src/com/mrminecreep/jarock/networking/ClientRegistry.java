package com.mrminecreep.jarock.networking;

import java.util.HashMap;

import io.netty.channel.Channel;

public class ClientRegistry {
	
	private static HashMap<String, Integer> clients = new HashMap<String, Integer>();
	private static HashMap<String, Channel> channels = new HashMap<String, Channel>();
	
	public static void registerClient(String client, Channel channel) {
		clients.put(client, 0);
		channels.put(client, channel);
	}
	
	public static void changeClientState(String client, Integer newState) {
		clients.replace(client, newState);
	}
	
	public static void removeClient(String client) {
		clients.remove(client);
		clients.remove(client);
	}
	
	public static boolean isConnected(String client) {
		return clients.containsKey(client);
	}
	
	public static Integer getState(String client) {
		return clients.get(client);
	}
	
	public static Channel getChannel(String client) {
		return channels.get(client);
	}
}
