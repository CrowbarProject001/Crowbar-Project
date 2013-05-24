package cbproject.crafting.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;

import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.IChannelProcess;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class NetCrafterClient implements IChannelProcess{
	
	public short dimension, id;
	public int blockX, blockY, blockZ;
	public boolean direction;
	
	public NetCrafterClient(){}
	
	/**
	 * Sends the WeaponCrafter information packet.
	 * @param d dimension
	 * @param i id(0 = factor, 1 = page)
	 * @param x 方块X
	 * @param y 方块Y
	 * @param z 方块Z
	 * @param dir 方向（true=下，false=上）
	 */
	public static void sendCrafterPacket(short d, short i, int x, int y, int z, boolean dir){
		
		ByteArrayOutputStream bos = CBCNetHandler.getStream(GeneralProps.NET_ID_CRAFTER_CL, 17);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
			outputStream.writeShort(d);
			outputStream.writeShort(i);
			outputStream.writeInt(x);
			outputStream.writeShort(y);
			outputStream.writeInt(z);
	        outputStream.writeBoolean(dir);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = GeneralProps.NET_CHANNEL_SERVER;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
		
	}
	
	public NetCrafterClient getCrafterPacket(DataInputStream inputStream){
		
		try {
			dimension = inputStream.readShort();
			id = inputStream.readShort();
			blockX = inputStream.readInt();
			blockY = inputStream.readShort();
			blockZ = inputStream.readInt();
			direction = inputStream.readBoolean();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return this;
		
	}

	@Override
	public void onPacketData(DataInputStream packet, Player player) {
		NetCrafterClient c = new NetCrafterClient().getCrafterPacket(packet);
		TileEntity te = MinecraftServer.getServer().worldServerForDimension(c.dimension).getBlockTileEntity(c.blockX, c.blockY, c.blockZ);
		if(te != null && !te.worldObj.isRemote){
			if(c.id == 0)
				((TileEntityWeaponCrafter)te).addScrollFactor(c.direction);
			else ((TileEntityWeaponCrafter)te).addPage(c.direction);
		}
		return;
	}
	
	
}
