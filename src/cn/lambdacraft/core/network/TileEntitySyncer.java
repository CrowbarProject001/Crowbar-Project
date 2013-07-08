/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.IChannelProcess;
import cn.lambdacraft.core.register.SyncableField;

/**
 * @author WeAthFolD
 *
 */
public class TileEntitySyncer {

	private static HashMap<Class<? extends TileEntity>, DataInformation> informationMap = new HashMap();
	
	public static class DataInformation {
		public int packetSize;
		public List<Field> types;
		
		public DataInformation(int size, List<Field> list) {
			types = list;
			packetSize = size;
		}
	}
	
	/**
	 * From Client To Server
	 * @author WeAthFolD
	 */
	public static class NetTileSyncer_Client implements IChannelProcess {

		public static void sendPacket(TileEntity te) {
			DataInformation inf = informationMap.get(te.getClass());
			ByteArrayOutputStream bos = CBCNetHandler.getStream(GeneralProps.NET_ID_TILE_SYNCER_CLN, inf.packetSize + 10);
			DataOutputStream outputStream = new DataOutputStream(bos);
			try {
				outputStream.writeInt(te.xCoord);
				outputStream.writeShort(te.yCoord);
				outputStream.writeInt(te.zCoord);
				for(Field fi : inf.types) {
					SyncableField f = fi.getAnnotation(SyncableField.class);
					if(f.value() == Byte.class)
						outputStream.writeByte(fi.getByte(te));
					if(f.value() == Float.class)
						outputStream.writeFloat(fi.getFloat(te));
					if(f.value() == Integer.class)
						outputStream.writeInt(fi.getInt(te));
					if(f.value() == Short.class)
						outputStream.writeShort(fi.getShort(te));
					if(f.value() == Boolean.class)
						outputStream.writeBoolean(fi.getBoolean(te));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = GeneralProps.NET_CHANNEL_SERVER;
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			PacketDispatcher.sendPacketToServer(packet);
		}
		
		@Override
		public void onPacketData(DataInputStream stream, Player player) {
			try {
				World world = ((EntityPlayer)player).worldObj;
				int x = stream.readInt(), y = stream.readShort(), z = stream.readInt();
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if(te == null || !informationMap.containsKey(te.getClass()))
					return;
				DataInformation inf = informationMap.get(te.getClass());
				for(Field fi : inf.types) {
					SyncableField f = fi.getAnnotation(SyncableField.class);
					if(f.value() == Byte.class)
						fi.setByte(te, stream.readByte());
					else if(f.value() == Float.class)
						fi.setFloat(te, stream.readFloat());
					else if(f.value() == Integer.class)
						fi.setInt(te, stream.readInt());
					else if(f.value() == Short.class)
						fi.setShort(te, stream.readShort());
					else if(f.value() == Boolean.class)
						fi.setBoolean(te, stream.readBoolean());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Successfully handled a packet from client");
		}
		
	}
	
	/**
	 * From Server To Client
	 * @author WeAthFolD
	 */
	public static class NetTileSyncer_Server implements IChannelProcess {

		public static void sendPacket(TileEntity te) {
			DataInformation inf = informationMap.get(te.getClass());
			ByteArrayOutputStream bos = CBCNetHandler.getStream(GeneralProps.NET_ID_TILE_SYNCER_SVR, inf.packetSize + 10);
			DataOutputStream outputStream = new DataOutputStream(bos);
			try {
				outputStream.writeInt(te.xCoord);
				outputStream.writeShort(te.yCoord);
				outputStream.writeInt(te.zCoord);
				for(Field fi : inf.types) {
					SyncableField f = fi.getAnnotation(SyncableField.class);
					Object ob = fi.get(te);
					String s = ob.toString();
					System.out.println("Attempt syncing field " + fi.getName() + " With value " + s);
					if(f.value() == Byte.class)
						outputStream.writeByte(Byte.valueOf(s));
					if(f.value() == Float.class)
						outputStream.writeFloat(Float.valueOf(s));
					if(f.value() == Integer.class)
						outputStream.writeInt(Integer.valueOf(s));
					if(f.value() == Short.class)
						outputStream.writeShort(Short.valueOf(s));
					if(f.value() == Boolean.class)
						outputStream.writeBoolean(Boolean.valueOf(s));
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			Packet250CustomPayload packet = new Packet250CustomPayload();
			packet.channel = GeneralProps.NET_CHANNEL_SERVER;
			packet.data = bos.toByteArray();
			packet.length = bos.size();
			PacketDispatcher.sendPacketToAllAround(te.xCoord + 0.5, te.yCoord + 0.5, te.zCoord + 0.5, 32, 
					te.worldObj.getWorldInfo().getDimension(), packet);
		}
		
		@Override
		public void onPacketData(DataInputStream stream, Player player) {
			try {
				World world = Minecraft.getMinecraft().theWorld;
				int x = stream.readInt(), y = stream.readShort(), z = stream.readInt();
				TileEntity te = world.getBlockTileEntity(x, y, z);
				if(te == null || !informationMap.containsKey(te.getClass()))
					return;
				DataInformation inf = informationMap.get(te.getClass());
				for(Field fi : inf.types) {
					SyncableField f = fi.getAnnotation(SyncableField.class);
					if(f.value() == Byte.class)
						fi.setByte(te, stream.readByte());
					else if(f.value() == Float.class)
						fi.setFloat(te, stream.readFloat());
					else if(f.value() == Integer.class)
						fi.setInt(te, stream.readInt());
					else if(f.value() == Short.class)
						fi.setShort(te, stream.readShort());
					else if(f.value() == Boolean.class)
						fi.setBoolean(te, stream.readBoolean());
				}
			} catch(Exception e) {
				e.printStackTrace();
			}
			System.out.println("Successfully handled a packet from server");
		}
		
	}
	
	public static boolean register(Class<? extends TileEntity> teClass) {
		List list = new ArrayList();
		int size = 0;
		System.out.println("Attempt registering class " + teClass.getCanonicalName());
		for(Field f : teClass.getFields()) {
			SyncableField an = f.getAnnotation(SyncableField.class);
			if(an != null) {
				System.out.println("Find field : " + f.getName() + ", Annoated Type :" + an.value().getName());
				list.add(f);
				size += getSize(an);
			}
		}
		if(list.size() != 0) {
			informationMap.put(teClass, new DataInformation(size, list));
			return true;
		}
		return false;
	}
	
	private static int getSize(SyncableField f) {
		if(f.value() == Byte.class)
			return 1;
		if(f.value() == Float.class)
			return 4;
		if(f.value() == Integer.class)
			return 4;
		if(f.value() == Short.class)
			return 2;
		if(f.value() == Boolean.class)
			return 1;
		throw new UnsupportedOperationException();
	}
	
	protected static NetTileSyncer_Client syncer_client;
	protected static NetTileSyncer_Server syncer_server;
	
	public static void init() {
		CBCNetHandler.addChannel(GeneralProps.NET_ID_TILE_SYNCER_CLN, new NetTileSyncer_Client());
		CBCNetHandler.addChannel(GeneralProps.NET_ID_TILE_SYNCER_SVR, new NetTileSyncer_Server());
	}
	
	public static void updateTileEntity(TileEntity te) {
		if(te.worldObj.isRemote) {
			syncer_client.sendPacket(te);
		} else {
			syncer_server.sendPacket(te);
		}
	}

}
