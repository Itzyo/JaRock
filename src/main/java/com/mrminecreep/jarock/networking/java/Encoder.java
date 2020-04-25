package com.mrminecreep.jarock.networking.java;

import java.util.ArrayList;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.minecraft.registry.PlayerRegistry;
import com.mrminecreep.jarock.util.ByteArrayUtils;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<ArrayList<Object>>{

	@Override
	protected void encode(ChannelHandlerContext ctx, ArrayList<Object> msg, ByteBuf out) throws Exception {
		Logger.log_debug("Completing packet header");
			
		ByteBuf transfer = XMLParser.serialize(msg, ctx.channel().remoteAddress().toString());
		int index = out.writerIndex();
		
		ByteArrayUtils.writeVarInt(out, transfer.readableBytes());
		Logger.log_debug("Writing packet length %d, writer index started at %d and is now at %d", transfer.readableBytes(), index, out.writerIndex());
		index = out.writerIndex();
		out.writeBytes(transfer);
		Logger.log_debug("Writing packet data to packet, writer index started at %d and is now at %d", index, out.writerIndex());
	}

}
