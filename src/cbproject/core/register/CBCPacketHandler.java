package cbproject.core.register;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.util.HashMap;
import cbproject.core.network.IChannelProcess;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class CBCPacketHandler implements IPacketHandler {

	private static HashMap<String, IChannelProcess> channels = new HashMap();
	
	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		IChannelProcess p = channels.get(packet.channel);
		System.out.println(p);
		if(p != null)
			p.onPacketData(packet, player);

	}
	
	public static void addChannel(String channel, IChannelProcess process){
		channels.put(channel, process);
	}

}
