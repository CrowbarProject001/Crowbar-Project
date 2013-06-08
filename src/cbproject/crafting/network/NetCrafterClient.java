package cbproject.crafting.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.IChannelProcess;
import cbproject.crafting.blocks.TileWeaponCrafter;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;

public class NetCrafterClient implements IChannelProcess{
	
	public short id;
	public int blockX, blockY, blockZ;
	public boolean direction;
	
	public NetCrafterClient(){}
	
	/**
	 * Sends the WeaponCrafter information packet.
	 * @param i id(0 = factor, 1 = page)
	 * @param dir 方向（true=下，false=上）
	 */
	public static void sendCrafterPacket(TileWeaponCrafter te, int id, boolean isForward){
		
		ByteArrayOutputStream bos = CBCNetHandler.getStream(GeneralProps.NET_ID_CRAFTER_CL, 12);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
			outputStream.writeByte((byte)id);
			outputStream.writeInt(te.xCoord);
			outputStream.writeShort(te.yCoord);
			outputStream.writeInt(te.zCoord);
	        outputStream.writeBoolean(isForward);
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
			id = inputStream.readByte();
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
		EntityPlayer p = (EntityPlayer) player;
		TileEntity te = p.worldObj.getBlockTileEntity(c.blockX, c.blockY, c.blockZ);
		if(te != null && !te.worldObj.isRemote){
			if(c.id == 0)
				((TileWeaponCrafter)te).addScrollFactor(c.direction);
			else ((TileWeaponCrafter)te).addPage(c.direction);
		}
		return;
	}
	
	
}
