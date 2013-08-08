package cn.lambdacraft.deathmatch.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import cn.lambdacraft.api.weapon.IModdable;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.IChannelProcess;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.util.StatCollector;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class NetDeathmatch implements IChannelProcess {

	public static void sendModePacket(byte stackInSlot, byte id, byte newMode) {
		ByteArrayOutputStream bos = CBCNetHandler.getStream(
				GeneralProps.NET_ID_DM, 3);
		DataOutputStream outputStream = new DataOutputStream(bos);

		try {
			outputStream.writeByte(stackInSlot);
			outputStream.writeByte(id);
			outputStream.writeByte(newMode);
		} catch (Exception ex) {
			ex.printStackTrace();
		}

		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = GeneralProps.NET_CHANNEL_SERVER;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	private static int[] getModePacket(DataInputStream inputStream) {
		int[] arr = new int[3];
		try {
			arr[0] = inputStream.readByte();
			arr[1] = inputStream.readByte();
			arr[2] = inputStream.readByte();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return arr;
	}

	@Override
	public void onPacketData(DataInputStream packet, Player player) {
		EntityPlayer p = (EntityPlayer) player;
		int[] prop = getModePacket(packet);
		ItemStack is = p.inventory.mainInventory[prop[0]];

		if (is == null)
			return;

		Item item = is.getItem();
		if (prop[1] == 1) {
			if (!(item instanceof WeaponGeneralBullet))
				return;
			((WeaponGeneralBullet)item).onSetReload(is, p);
		} else {
			if(!(item instanceof IModdable))
				return;
			IModdable moddable = (IModdable) item;
			moddable.onModeChange(is, p, prop[2]);
			((EntityPlayer) player).sendChatToPlayer(StatCollector
					.translateToLocal("mode.new")
					+ ": \u00a74"
					+ StatCollector.translateToLocal(moddable.getModeDescription(moddable.getMode(is))));
		}
		return;

	}
}
