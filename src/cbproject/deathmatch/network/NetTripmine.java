package cbproject.deathmatch.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cbproject.core.register.IChannelProcess;
import cbproject.deathmatch.utils.BulletManager;

public class NetTripmine implements IChannelProcess {

	public static void sendNetPacket(int dimension, int bx, int by, int bz){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(14);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
	        outputStream.writeInt(bx);
	        outputStream.writeInt(by);
	        outputStream.writeInt(bz);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "CBCTripmine";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		
		PacketDispatcher.sendPacketToAllInDimension(packet, dimension);
	}

	@Override
	public void onPacketData(Packet250CustomPayload packet, Player player) {
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		int bx = 0, by = 0, bz = 0;
		try {
	        bx = inputStream.readInt();
	        by = inputStream.readInt();
	        bz = inputStream.readInt();
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		BulletManager.Explode(Minecraft.getMinecraft().theWorld, null, 1.0F, 4.0, bx, by, bz, 40, 0.5, 1.0F);
	}

}
