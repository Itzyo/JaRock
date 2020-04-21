package com.mrminecreep.jarock.networking.java;

import java.util.HashMap;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.event.EventConstructor;
import com.mrminecreep.jarock.minecraft.registry.EntityRegistry;
import com.mrminecreep.jarock.minecraft.registry.PlayerRegistry;
import com.mrminecreep.jarock.networking.ClientRegistry;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class EndpointIn extends ChannelInboundHandlerAdapter{

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		if(msg instanceof HashMap) {
			
			@SuppressWarnings("unchecked")
			HashMap<String, Object> p = (HashMap<String, Object>) msg;
			
			switch(Integer.decode(p.get("Id").toString())) {
			case 0:{
				if(ClientRegistry.getState(ctx.channel().remoteAddress().toString()) == 0) {
					EventConstructor.createHandshakeEvent(p.get("ProtocolVersion"), p.get("ServerAddress"), p.get("ServerPort"),
							p.get("NextState"), ctx.channel().remoteAddress().toString());
				} else if(ClientRegistry.getState(ctx.channel().remoteAddress().toString()) == 2) {
					EventConstructor.createLoginStartEvent(p.get("Username"), ctx.channel().remoteAddress().toString(), false);
				}
				break;
			}
			
			case 15:{
				EventConstructor.createKeepAliveEventRecv(p.get("KeepAliveID"), PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
				break;
			}
			
			case 17:{
				EventConstructor.createPlayerPositionUpdateEvent(p.get("X"), p.get("Y"), p.get("Z"), p.get("OnGround"),
						PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
				break;
			}
			
			case 19:{
				EventConstructor.createPlayerRotationUpdateEvent(p.get("Yaw"), p.get("Pitch"), p.get("OnGround"),
						PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
				break;
			}
			}
		} else {
			Logger.log_error("Got corrupted packet");
		}
	}
	
	@Override
	public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
		Logger.log_error("An error occured during receiving or parsing a packet:");
		cause.printStackTrace();
	}
	
	@Override
	public void channelInactive(ChannelHandlerContext ctx) throws Exception {
		EventConstructor.createPlayerInfoRemovePlayerEvent(PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
		Logger.log_info("%s has left the game", PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()).getUsername());
		JavaSocket.all.remove(ctx.channel());
		ClientRegistry.removeClient(ctx.channel().remoteAddress().toString());
		EntityRegistry.unregisterEntity(PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
		PlayerRegistry.unregisterPlayer(PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
	}
}
