package com.mrminecreep.jarock.networking.java;

import java.io.File;
import java.io.InputStream;
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
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;

public class PipelineDecoder extends MessageToMessageDecoder<ByteBuf>{

	@Override
	protected void decode(ChannelHandlerContext ctx, ByteBuf msg, List<Object> out) throws Exception {
		Logger.log_debug("Got packet of length: %d.", msg.readableBytes());
		HashMap<String, Object> parsedPacket = new HashMap<String, Object>();
		Integer pId = InternalTypes.readVarInt(msg);
		Logger.log_debug("Packet id is %d while client state is %d.", pId, ClientRegistry.getState(ctx.channel().remoteAddress().toString()));
		parsedPacket.put("Id", pId);
		
		InputStream is = this.getClass().getResourceAsStream("/JavaPackets.xml");
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder Builder = dbFac.newDocumentBuilder();
		Document doc = Builder.parse(is);
		doc.getDocumentElement().normalize();
		
		NodeList packets = doc.getElementsByTagName("packet");
		
		for(int i = 0; i < packets.getLength(); i++) {
			Node packet = packets.item(i);
			
			if(packet.getNodeType() == Node.ELEMENT_NODE) {
				Element ePacket = (Element) packet;
				
				if(ClientRegistry.getState(ctx.channel().remoteAddress().toString()) == 0 ||
						ClientRegistry.getState(ctx.channel().remoteAddress().toString()) == 2) {
					Thread.currentThread();
					Thread.sleep(100);
				}
				
				if(Integer.parseInt(ePacket.getAttribute("id")) == pId &&
						Integer.parseInt(ePacket.getAttribute("state")) == ClientRegistry.getState(ctx.channel().remoteAddress().toString())) {
					
					if(packet.hasChildNodes()) {
						NodeList fields = packet.getChildNodes();
						
						for(int x = 0; x < fields.getLength(); x++) {
							Node field = fields.item(x);
							
							if(field.getNodeType() == Node.ELEMENT_NODE) {
								Element eField = (Element) field;
								Object o = null;
								
								switch(eField.getAttribute("type")) {
								case "VarInt":{
									o = InternalTypes.readVarInt(msg);
									break;
								}
								
								case "String":{
									o = InternalTypes.readString(msg);
									break;
								}
								
								case "UnsignedShort":{
									o = msg.readUnsignedShort();
									break;
								}
								
								case "Long":{
									o = msg.readLong();
									break;
								}
								
								case "Double":{
									o = msg.readDouble();
									break;
								}
								
								case "Boolean":{
									o = msg.readBoolean();
									break;
								}
								
								case "Float":{
									o = msg.readFloat();
									break;
								}
								
								default: continue;
								}
								
								if(o != null) {
									parsedPacket.put(eField.getTextContent(), o);
								}
							}
						}
					}
				}
			}
		}
		out.add(parsedPacket);
		is.close();
	}

}
