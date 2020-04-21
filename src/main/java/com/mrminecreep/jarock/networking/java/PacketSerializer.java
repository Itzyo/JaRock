package com.mrminecreep.jarock.networking.java;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import com.mrminecreep.jarock.Logger;
import com.mrminecreep.jarock.networking.ClientRegistry;
import com.mrminecreep.jarock.networking.java.Types.Position;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;
import net.querz.nbt.CompoundTag;

public class PacketSerializer extends MessageToMessageEncoder<ArrayList<Object>> {


	@Override
	protected void encode(ChannelHandlerContext ctx, ArrayList<Object> msg, List<Object> out) throws Exception {
		
		Logger.log_debug("Trying to serialize data");
		
		int index = 0;
		Byte type = null;
		
		if(msg.get(index) instanceof Byte) {
			type = (Byte) msg.get(index);
			index++;
		}
		
		Integer id = (Integer) msg.get(index);
		index++;
		ByteBuf buf = Unpooled.buffer();
				
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
				
				if(Integer.parseInt(ePacket.getAttribute("id")) == id &&
						Integer.parseInt(ePacket.getAttribute("state")) == ClientRegistry.getState(ctx.channel().remoteAddress().toString()) &&
						(ePacket.getAttribute("type").equals("") || Byte.parseByte(ePacket.getAttribute("type")) == (byte) type)) {
					
					Logger.log_debug("Trying to construct packet with id %d and name: %s", id, ePacket.getAttribute("name"));
					buf.writeBytes(InternalTypes.writeVarInt(id));
					Logger.log_debug("Writing PacketID: %d", id);
					
					if(packet.hasChildNodes()) {
						NodeList fields = packet.getChildNodes();
						
						for(int x = 0; x < fields.getLength(); x++) {
							Node field = fields.item(x);
							
							if(field.getNodeType() == Node.ELEMENT_NODE) {
								Element eField = (Element) field;
								
								switch(eField.getAttribute("type")) {
								case "VarInt":{
									Logger.log_debug("Writing %s to packet with value %d (Type: VarInt)", eField.getTextContent(), (Integer) msg.get(index));
									buf.writeBytes(InternalTypes.writeVarInt((Integer) msg.get(index)));
									break;
								}
								
								case "String":{
									Logger.log_debug("Writing %s to packet with value %s (Type: String)", eField.getTextContent(), (String) msg.get(index));
									buf.writeBytes(InternalTypes.writeString((String) msg.get(index)));
									break;
								}
								
								case "Int": {
									Logger.log_debug("Writing %s to packet with value %d (Type: Int)", eField.getTextContent(), (Integer) msg.get(index));
									buf.writeInt((Integer) msg.get(index));
									break;
								}
								
								case "Byte":{
									Logger.log_debug("Writing %s to packet with value %d (Type: Byte)", eField.getTextContent(), ((Byte) msg.get(index)).intValue());
									buf.writeByte(((Byte) msg.get(index)).intValue());
									break;
								}
								
								case "Boolean":{
									Logger.log_debug("Writing %s to packet with value %s (Type: Boolean)", eField.getTextContent(), ((Boolean) msg.get(index)).toString());
									buf.writeBoolean((boolean) msg.get(index));
									break;
								}
								
								case "Double":{
									Logger.log_debug("Writing %s to packet with value %.2f (Type: Double)", eField.getTextContent(), (Double) msg.get(index));
									buf.writeDouble((Double) msg.get(index));
									break;
								}
								
								case "Float":{
									Logger.log_debug("Writing %s to packet with value %.2f (Type: Float)", eField.getTextContent(), (Float) msg.get(index));
									buf.writeFloat((Float) msg.get(index));
									break;
								}
								
								case "Position":{
									Logger.log_debug("Writing %s to packet with value %d, %d, %d (Type: Position)",
											eField.getTextContent(), ((Position) msg.get(index)).getX(),((Position) msg.get(index)).getY(), ((Position) msg.get(index)).getZ());
									buf.writeLong(((Position) msg.get(index)).encode());
									break;
								}
								
								case "Long":{
									Logger.log_debug("Writing %s to packet with value %d (Type: Long)", eField.getTextContent(), (Long) msg.get(index));
									buf.writeLong((Long) msg.get(index));
									break;
								}
								
								case "NBT":{
									Logger.log_debug("Writing %s to packet (Type: NBT tag)", eField.getTextContent());
									ByteArrayOutputStream baos = new ByteArrayOutputStream();
									DataOutputStream w = new DataOutputStream(baos);
									
									((CompoundTag) msg.get(index)).serialize(w, CompoundTag.DEFAULT_MAX_DEPTH);
									byte[] out2 = baos.toByteArray();
									Logger.log_debug("Length of nbt in bytes: %d", out2.length);
									buf.writeBytes(out2);
									break;
								}
								
								case "UUID":{
									Long mostBits = (Long) msg.get(index);
									index++;
									Long leastBits = (Long) msg.get(index);
									Logger.log_debug("Writing %s to packet with value %s (Type: UUID)", eField.getTextContent(), new UUID(mostBits, leastBits));
									buf.writeLong(mostBits);
									buf.writeLong(leastBits);
									break;
								}
								
								case "ByteArray":{
									Logger.log_debug("Writing %s to packet (Type: ByteArray with length %d)", eField.getTextContent(), ((ByteBuf) msg.get(index)).readableBytes());
									buf.writeBytes((ByteBuf) msg.get(index));
									((ByteBuf) msg.get(index)).resetReaderIndex();				
									break;
								}
								
								default: Logger.log_error("Cannot encode type %s", eField.getAttribute("type")); continue;
								}
								index++;
							}
						}
						out.add(buf);
					}
					
				}
			}
		}
		is.close();		
	}
		
}
