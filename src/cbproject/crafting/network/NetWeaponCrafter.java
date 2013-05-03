package cbproject.crafting.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;

import cbproject.core.register.IChannelProcess;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class NetWeaponCrafter implements IChannelProcess{
	
	public short dimension, id;
	public int blockX, blockY, blockZ;
	public boolean direction;
	
	public NetWeaponCrafter(){}
	
	public static void sendCrafterPacket(short d, short i, int x, int y, int z, boolean dir){
		
		ByteArrayOutputStream bos = new ByteArrayOutputStream(17);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
			outputStream.writeShort(d);
			outputStream.writeShort(i);
			outputStream.writeInt(x);
			outputStream.writeInt(y);
			outputStream.writeInt(z);
	        outputStream.writeBoolean(dir);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "CBCCrafter";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
		
	}
	
	public NetWeaponCrafter getCrafterPacket(Packet250CustomPayload packet){
		
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
			dimension = inputStream.readShort();
			id = inputStream.readShort();
			blockX = inputStream.readInt();
			blockY = inputStream.readInt();
			blockZ = inputStream.readInt();
			direction = inputStream.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
		
	}

	@Override
	public void onPacketData(Packet250CustomPayload packet, Player player) {
		NetWeaponCrafter c = new NetWeaponCrafter().getCrafterPacket(packet);
		TileEntity te = MinecraftServer.getServer().worldServerForDimension(c.dimension).getBlockTileEntity(c.blockX, c.blockY, c.blockZ);
		if(!te.worldObj.isRemote){
			if(c.id == 0)
				((TileEntityWeaponCrafter)te).addScrollFactor(c.direction);
			else ((TileEntityWeaponCrafter)te).addPage(c.direction);
		}
		return;
	}
	
	
}
