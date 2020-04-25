package com.mrminecreep.jarock.networking.java;

import java.util.List;

import com.mrminecreep.jarock.util.ByteArrayUtils;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class PacketSplitter extends ReplayingDecoder<Void>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		if(ctx.channel().isActive()) {
			while(in.isReadable()) {
				
				Integer len = ByteArrayUtils.readVarInt(in);
				
				ByteBuf output = Unpooled.buffer(len);
				
				in.readBytes(output);
				out.add(output);
			}
			
		} else {
			in.skipBytes(in.readableBytes());
		}
	}
}
