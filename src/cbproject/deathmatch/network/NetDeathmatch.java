package cbproject.deathmatch.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.network.packet.Packet250CustomPayload;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.utils.InformationWeapon;
import cpw.mods.fml.common.network.PacketDispatcher;

public class NetDeathmatch {
	
	public static void sendModePacket(int stackInSlot, int newMode){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(8);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
	        outputStream.writeInt(stackInSlot);
	        outputStream.writeInt(newMode);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "CBCWeaponMode";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}
	
	public static int[] getModePacket(Packet250CustomPayload packet){
		int[] arr = new int[2];
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
	        arr[0] = inputStream.readInt();
	        arr[1] = inputStream.readInt();
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		return arr;
	}
}
