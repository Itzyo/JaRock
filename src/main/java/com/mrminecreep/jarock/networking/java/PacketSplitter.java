package com.mrminecreep.jarock.networking.java;

import java.io.File;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mrminecreep.jarock.Jarock;
import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.networking.ClientRegistry;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ReplayingDecoder;

public class PacketSplitter extends ReplayingDecoder<Void>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {

		if(ctx.channel().isActive()) {
			while(in.isReadable()) {
				
				Integer len = InternalTypes.readVarInt(in);
				
				ByteBuf output = Unpooled.buffer(len);
				
				in.readBytes(output);
				out.add(output);
			}
			
		} else {
			in.skipBytes(in.readableBytes());
		}
	}
}
