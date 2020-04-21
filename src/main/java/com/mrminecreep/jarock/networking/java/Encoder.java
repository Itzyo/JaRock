package com.mrminecreep.jarock.networking.java;

import com.mrminecreep.jarock.Logger;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

public class Encoder extends MessageToByteEncoder<Object>{

	@Override
	protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
		Logger.log_debug("Completing packet header");
		if(msg instanceof ByteBuf) {
			
			ByteBuf transfer = (ByteBuf) msg;
			
			int index = out.writerIndex();
			out.writeBytes(InternalTypes.writeVarInt(transfer.readableBytes()));
			Logger.log_debug("Writing packet length %d, writer index started at %d and is now at %d", transfer.readableBytes(), index, out.writerIndex());
			index = out.writerIndex();
			out.writeBytes(transfer);
			Logger.log_debug("Writing packet data to packet, writer index started at %d and is now at %d", index, out.writerIndex());
		} else {
			Logger.log_debug("Pipeline failiure, passed Object is not a ByteBuf but a %s", msg.toString());
		}
	}

}
