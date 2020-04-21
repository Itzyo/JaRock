package com.mrminecreep.jarock.minecraft.handler;

import java.io.IOException;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.Date;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.event.EventConstructor;
import com.mrminecreep.jarock.event.EventListener;
import com.mrminecreep.jarock.event.events.KeepAliveEventRecv;
import com.mrminecreep.jarock.networking.ClientRegistry;

public class GeneralPurposeHandler {
	
	 @EventListener("com.mrminecreep.jarock.event.events.KeepAliveEventRecv")
	 public void CheckKeepAlive(KeepAliveEventRecv e) {
		 Logger.log_debug("Updating %s keepalive to %d, was %d", e.getPlayer().getUsername(), e.getKeepAlive(), e.getPlayer().getKeepAlive());
		 e.getPlayer().setKeepAlive(e.getKeepAlive());
		 
		 Date start, stop;
		 Long ping;
		 InetAddress clientAddr = ((InetSocketAddress) ClientRegistry.getChannel(e.getPlayer().getClient()).remoteAddress()).getAddress();
		 
		 start = new Date();
		 try {
			if(clientAddr.isReachable(5000)) {
				 stop = new Date();
				 ping = (stop.getTime() - start.getTime());
			 } else {
				 ping = (long) -1;
			 }
		} catch (IOException e1) {
			e1.printStackTrace();
			ping = (long) -1;
		}
		 
		 Logger.log_debug("%s has a ping of %d ms", e.getPlayer().getUsername(), ping);
		 e.getPlayer().setPing(ping);
		 
		 //EventConstructor.createPlayerInfoUpdateLatencyEvent(e.getPlayer());
	 }
}
