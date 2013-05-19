package cbproject.core.register;

import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.Player;

public interface IChannelProcess {
	public void onPacketData(Packet250CustomPayload packet, Player player);
}
