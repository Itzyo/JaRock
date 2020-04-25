package com.mrminecreep.jarock.networking.java;

import java.util.HashMap;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.event.EventConstructor;
import com.mrminecreep.jarock.minecraft.registry.EntityRegistry;
import com.mrminecreep.jarock.minecraft.registry.PlayerRegistry;
import com.mrminecreep.jarock.networking.ClientRegistry;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;

public class Decoder extends ChannelInboundHandlerAdapter {

	@Override
	public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
		
		
		HashMap<String, Object> packet = XMLParser.deserialize((ByteBuf) msg, ctx.channel().remoteAddress().toString());
		
		switch(Integer.decode(packet.get("id").toString())) {
		case 0:{
			if(ClientRegistry.getState(ctx.channel().remoteAddress().toString()) == 0) {
				EventConstructor.createHandshakeEvent(packet.get("ProtocolVersion"), packet.get("ServerAddress"), packet.get("ServerPort"),
						packet.get("NextState"), ctx.channel().remoteAddress().toString());
			} else if(ClientRegistry.getState(ctx.channel().remoteAddress().toString()) == 2) {
				EventConstructor.createLoginStartEvent(packet.get("Username"), ctx.channel().remoteAddress().toString(), false);
			}
			break;
		}
		
		case 15:{
			EventConstructor.createKeepAliveEventRecv(packet.get("KeepAliveID"), PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
			break;
		}
		
		case 17:{
			EventConstructor.createPlayerPositionUpdateEvent(packet.get("X"), packet.get("Y"), packet.get("Z"), packet.get("OnGround"),
					PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
			break;
		}
		
		case 19:{
			EventConstructor.createPlayerRotationUpdateEvent(packet.get("Yaw"), packet.get("Pitch"), packet.get("OnGround"),
					PlayerRegistry.getPlayerByClient(ctx.channel().remoteAddress().toString()));
			break;
		}
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
