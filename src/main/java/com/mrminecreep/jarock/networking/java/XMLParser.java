package com.mrminecreep.jarock.networking.java;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import com.mrminecreep.jarock.Jarock;
import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.minecraft.Player;
import com.mrminecreep.jarock.networking.ClientRegistry;
import com.mrminecreep.jarock.util.ByteArrayUtils;
import com.mrminecreep.jarock.util.types.Position;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import net.querz.nbt.CompoundTag;

public class XMLParser {
	
	public static HashMap<String, Object> deserialize(ByteBuf buf, String client) throws InterruptedException{
		Logger.log_debug("Got packet of length: %d.", buf.readableBytes());
		HashMap<String, Object> parsedPacket = new HashMap<String, Object>();
		NodeList packets = null;
		
		Integer PacketID = ByteArrayUtils.readVarInt(buf);
		parsedPacket.put("id", PacketID);		
		
		Logger.log_debug("Packet id is %d while client state is %d.", PacketID, ClientRegistry.getState(client));
		
		try {
			packets = XMLParser.getPacketList();
		} catch (Exception e) {
			Logger.log_fatal("Error on reading JavaPackets.xml! Shutting down.");
			e.printStackTrace();
			Jarock.shutdown();
		}
		
		for(int i = 0; i < packets.getLength(); i++) {
			Node packet = packets.item(i);
			
			if(packet.getNodeType() == Node.ELEMENT_NODE) {
				Element ePacket = (Element) packet;
				
				if(ClientRegistry.getState(client) == 0 || ClientRegistry.getState(client) == 2) {
					Thread.currentThread();
					Thread.sleep(100);
				}
				
				if(XMLParser.matchPacket(ePacket, client, null, PacketID) && packet.hasChildNodes()) {
					HashMap<String, Object> data = XMLParser.readDataPacket(buf, packet.getChildNodes());
					parsedPacket.putAll(data);
					
					break;
				}
				
			}
		}
		
		return parsedPacket;
	}

	public static ByteBuf serialize(ArrayList<Object> msg, String client) throws InterruptedException {
		
		Logger.log_debug("Trying to serialize data");
		
		int index = 0;
		Byte type = XMLParser.getType(msg.get(index));
		Integer PacketID = 0;
		ByteBuf buf = Unpooled.buffer();
		NodeList packets = null;
		
		if(type != null)
			index++;
		
		PacketID = (Integer) msg.get(index);
		index++;
		
		try {
			packets = XMLParser.getPacketList();
		} catch (Exception e) {
			Logger.log_fatal("Error on reading JavaPackets.xml! Shutting down.");
			e.printStackTrace();
			Jarock.shutdown();
		}
		
		for(int i = 0; i < packets.getLength(); i++) {
			Node packet = packets.item(i);
			
			if(packet.getNodeType() == Node.ELEMENT_NODE) {
				Element ePacket = (Element) packet;
				
				if(XMLParser.matchPacket(ePacket, client, type, PacketID) && packet.hasChildNodes()) {
					Logger.log_debug("Trying to construct packet with id %d and name: %s", PacketID, ePacket.getAttribute("name"));
					
					ByteArrayUtils.writeVarInt(buf, PacketID);
					Logger.log_debug("Writing PacketID: %d", PacketID);
					
					XMLParser.writeDataPacket(msg, buf, packet.getChildNodes(), index);
					break;
				}
			}
			
		}
		
		return buf;
	}
	
	private static HashMap<String, Object> readDataPacket(ByteBuf buf, NodeList fields) {
		HashMap<String, Object> data = new HashMap<String, Object>();
		
		for(int x = 0; x < fields.getLength(); x++) {
			Node field = fields.item(x);
			
			if(field.getNodeType() == Node.ELEMENT_NODE) {
				Element eField = (Element) field;
				Object o = null;
				
				switch(eField.getAttribute("type")) {
				case "VarInt":{
					o = ByteArrayUtils.readVarInt(buf);
					break;
				}
				
				case "String":{
					o = ByteArrayUtils.readString(buf);
					break;
				}
				
				case "UnsignedShort":{
					o = ByteArrayUtils.readUnsignedShort(buf);
					break;
				}
				
				case "Long":{
					o = ByteArrayUtils.readLong(buf);
					break;
				}
				
				case "Double":{
					o = ByteArrayUtils.readDouble(buf);
					break;
				}
				
				case "Boolean":{
					o = ByteArrayUtils.readBoolean(buf);
					break;
				}
				
				case "Float":{
					o = ByteArrayUtils.readFloat(buf);
					break;
				}
				
				default: continue;
				}
				
				if(o != null) {
					data.put(eField.getTextContent(), o);
				}
			}
		}
		
		return data;
	}
	
	private static int writeDataPacket(ArrayList<Object> data, ByteBuf buf, NodeList fields, int index) {	
		for(int i = 0; i < fields.getLength(); i++) {
			Node field = fields.item(i);
			
			if(field.getNodeType() == Node.ELEMENT_NODE) {
				Element eField = (Element) field;
				
				if(eField.getAttribute("optional").equals("true")) {
					if(!(boolean) data.get(index)) {
						index++;
						continue;
					} else
						index++;
				}
				
				switch(eField.getAttribute("type")) {
				case "VarInt":{
					Logger.log_debug("Writing %s to packet with value %d (Type: VarInt)", eField.getTextContent(), (Integer) data.get(index));
					ByteArrayUtils.writeVarInt(buf, (Integer) data.get(index));
					break;
				}
				
				case "String":{
					Logger.log_debug("Writing %s to packet with value %s (Type: String)", eField.getTextContent(), (String) data.get(index));
					ByteArrayUtils.writeString(buf, (String) data.get(index));
					break;
				}
				
				case "Int": {
					Logger.log_debug("Writing %s to packet with value %d (Type: Int)", eField.getTextContent(), (Integer) data.get(index));
					buf.writeInt((Integer) data.get(index));
					break;
				}
				
				case "Byte":{
					Logger.log_debug("Writing %s to packet with value %d (Type: Byte)", eField.getTextContent(), ((Byte) data.get(index)).intValue());
					ByteArrayUtils.writeByte(buf, (Byte) data.get(index));
					break;
				}
				
				case "Boolean":{
					Logger.log_debug("Writing %s to packet with value %s (Type: Boolean)", eField.getTextContent(), ((Boolean) data.get(index)).toString());
					ByteArrayUtils.writeBoolean(buf, (Boolean) data.get(index));
					break;
				}
				
				case "Double":{
					Logger.log_debug("Writing %s to packet with value %.2f (Type: Double)", eField.getTextContent(), (Double) data.get(index));
					buf.writeDouble((Double) data.get(index));
					break;
				}
				
				case "Float":{
					Logger.log_debug("Writing %s to packet with value %.2f (Type: Float)", eField.getTextContent(), (Float) data.get(index));
					buf.writeFloat((Float) data.get(index));
					break;
				}
				
				case "Position":{
					Logger.log_debug("Writing %s to packet with value %d, %d, %d (Type: Position)",
							eField.getTextContent(), ((Position) data.get(index)).getX(),((Position) data.get(index)).getY(), ((Position) data.get(index)).getZ());
					ByteArrayUtils.writePosition(buf, (Position) data.get(index));
					break;
				}
				
				case "Long":{
					Logger.log_debug("Writing %s to packet with value %d (Type: Long)", eField.getTextContent(), (Long) data.get(index));
					ByteArrayUtils.writeLong(buf, (Long) data.get(index));
					break;
				}
				
				case "NBT":{
					Logger.log_debug("Writing %s to packet (Type: NBT tag)", eField.getTextContent());
					ByteArrayUtils.writeNBT(buf, (CompoundTag) data.get(index));
					break;
				}
				
				case "UUID":{
					Logger.log_debug("Writing %s to packet with value %s (Type: UUID)", eField.getTextContent(), ((UUID) data.get(index)).toString());
					ByteArrayUtils.writeUUID(buf, (UUID) data.get(index));
					break;
				}
				
				case "ByteArray":{
					Logger.log_debug("Writing %s to packet (Type: ByteArray with length %d)", eField.getTextContent(), ((ByteBuf) data.get(index)).readableBytes());
					ByteArrayUtils.writeByteArray(buf, (ByteBuf) data.get(index));				
					break;
				}
				
				case "ARRAYSTART":{
					Integer elements = (Integer) data.get(index);
					
					if(field.hasChildNodes()) {
						NodeList arrayElements = field.getChildNodes();
						
						for(int x = 0; x < elements; x++) 
							index += XMLParser.writeDataPacket(data, buf, arrayElements, index + 1);
					}
					
				}
				
				default: Logger.log_error("Cannot encode type %s", eField.getAttribute("type")); continue;
				}
				index++;
			}
		}
		
		return index;
	}
	
	private static Boolean matchPacket(Element ePacket, String client, Byte type, Integer id) {
		if(Integer.parseInt(ePacket.getAttribute("id")) == id &&
		   Integer.parseInt(ePacket.getAttribute("state")) == ClientRegistry.getState(client) &&
		   (ePacket.getAttribute("type").equals("") || Byte.parseByte(ePacket.getAttribute("type")) == type)) 
			return true;
		else
			return false;
		
	}
	
	private static Byte getType(Object o) {
		if(o instanceof Byte)
			return (Byte) o;
		else
			return null;
	}
	
	private static NodeList getPacketList() throws ParserConfigurationException, SAXException, IOException {
		InputStream is = new XMLParser().getClass().getResourceAsStream("/JavaPackets.xml");
		
		DocumentBuilderFactory dbFac = DocumentBuilderFactory.newInstance();
		DocumentBuilder Builder = dbFac.newDocumentBuilder();
		Document doc = Builder.parse(is);
		doc.getDocumentElement().normalize();
		
		is.close();
		
		return doc.getElementsByTagName("packet");
	}
	
}
