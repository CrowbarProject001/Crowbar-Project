package cbproject.deathmatch.network;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;
import cbproject.core.network.IChannelProcess;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;

public class NetDeathmatch implements IChannelProcess{
	
	public static void sendModePacket(int stackInSlot,short id, int newMode){
		ByteArrayOutputStream bos = new ByteArrayOutputStream(10);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
	        outputStream.writeInt(stackInSlot);
	        outputStream.writeShort(id);
	        outputStream.writeInt(newMode);
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = "CBCWeapons";
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}
	
	private static int[] getModePacket(Packet250CustomPayload packet){
		int[] arr = new int[3];
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		try {
	        arr[0] = inputStream.readInt();
	        arr[1] = inputStream.readShort();
	        arr[2] = inputStream.readInt();
		} catch (Exception ex) {
	        ex.printStackTrace();
		}
		return arr;
	}

	@Override
	public void onPacketData(Packet250CustomPayload packet, Player player) {
		System.out.println("process called");
		EntityPlayer p = (EntityPlayer) player;
		int[] prop = getModePacket(packet);
		ItemStack is = p.inventory.mainInventory[prop[0]];
		
		if(is == null ||!(is.getItem() instanceof WeaponGeneral)){
			System.err.println("Didn't find correct information for" + p.getEntityName() + " @" + prop[0]);
		} else {
			WeaponGeneral wpn = (WeaponGeneral) is.getItem();
			if(prop[1] == 1){
				if(!(wpn instanceof WeaponGeneralBullet))
					return;
				((WeaponGeneralBullet)wpn).onSetReload(is, p);
			} else {
				wpn.onModeChange(is, p, prop[2]);
				System.out.println("Server new mode :" + prop[2]);
			}
		}
		return;
		
	}
}
