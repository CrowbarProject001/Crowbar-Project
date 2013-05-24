package cbproject.core.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.client.Minecraft;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.world.World;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCNetHandler;
import cbproject.core.register.IChannelProcess;
import cbproject.deathmatch.utils.BulletManager;

public class NetExplosion implements IChannelProcess {

	public static void sendNetPacket(World world, float bx, float by, float bz, float strengh){
		ByteArrayOutputStream bos = CBCNetHandler.getStream(GeneralProps.NET_ID_EXPLOSION, 16);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		int dimension = world.getWorldInfo().getDimension();
		try {
	        outputStream.writeFloat(bx);
	        outputStream.writeFloat(by);
	        outputStream.writeFloat(bz);
	        outputStream.writeFloat(strengh);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = GeneralProps.NET_CHANNEL_CLIENT;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToAllInDimension(packet, dimension);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void onPacketData(DataInputStream inputStream, Player player) {
		float bx = 0, by = 0, bz = 0, st = 0;
		System.out.println("get packet");
		try {
	        bx = inputStream.readFloat();
	        by = inputStream.readFloat();
	        bz = inputStream.readFloat();
	        st = inputStream.readFloat();
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		BulletManager.clientExplode(Minecraft.getMinecraft().theWorld,st, bx, by, bz);
	}

}
