package com.mrminecreep.jarock.minecraft;

import java.util.Random;

import com.mrminecreep.jarock.GlobalObjectRegistry;
import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.event.EventConstructor;
import com.mrminecreep.jarock.event.events.KeepAliveEventRecv;
import com.mrminecreep.jarock.event.events.KeepAliveEventSend;
import com.mrminecreep.jarock.minecraft.registry.EntityRegistry;
import com.mrminecreep.jarock.minecraft.registry.PlayerRegistry;
import com.mrminecreep.jarock.networking.ClientRegistry;
import com.mrminecreep.jarock.networking.java.JavaSocket;

import io.netty.channel.group.ChannelGroupFuture;

public class KeepAliveTicker extends Thread{
	
	private Long lastSeed = (long) 1;
	private boolean kill = false;
	
	public void kill() {
		this.kill = true;
	}
	
	public Long getLastSeed() {
		return this.lastSeed;
	}
	
	public void run() {
		Random rand = new Random();
		while(!this.kill) {
			try {
				
				PlayerRegistry.ForEachPlayer((Player p) -> {
					if(!(((KeepAliveTicker) GlobalObjectRegistry.get(1)).getLastSeed()).equals(p.getKeepAlive()) && p.isLoginDone()) {
						if(p.getLostKeepAlives() == 3) {
							ClientRegistry.getChannel(p.getClient()).close();
							Logger.log_info("%s timed out", p.getUsername());
						} else {
							Logger.log_warn("No keep alive message from %s recieved setting lost packets to %d", p.getUsername(), p.getLostKeepAlives() + 1);
							p.LostPacket();
						}
					} else {
						p.resetLostKeepAlives();
						Logger.log_debug("%s did not time out", p.getUsername());
					}
					
					return null;
				});
				
				this.lastSeed = rand.nextLong();
				EventConstructor.createKeepAliveEventSend(this.lastSeed);
				
				Thread.currentThread();
				Thread.sleep(5000);
			} catch (InterruptedException e) {
				Logger.log_fatal("KeepAliveTicker crashed");
				e.printStackTrace();
				this.kill();
			}
		}
		System.out.println("Penne");
	}

}
