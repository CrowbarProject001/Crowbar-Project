package cbproject.misc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.weapons.InformationWeapon;

import net.minecraft.item.Item;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import cpw.mods.fml.common.network.IPacketHandler;
import cpw.mods.fml.common.network.Player;

public class CBCPacketHandler implements IPacketHandler {

	@Override
	public void onPacketData(INetworkManager manager,
			Packet250CustomPayload packet, Player player) {
		
		DataInputStream inputStream = new DataInputStream(new ByteArrayInputStream(packet.data));
		
		switch(packet.channel){
		case "CBCWeaponMode" :
			
			try {
				
				int itemID = inputStream.readInt(),
				weaponID = inputStream.readInt(),
				mode = inputStream.readInt();

				WeaponGeneral wpn = (WeaponGeneral) Item.itemsList[itemID];
				System.out.println(wpn.getItemName());
				InformationWeapon inf = wpn.getInformation(weaponID).clientReference;
				inf.mode = mode;
				System.out.println("Client mode : " + inf.mode);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			
		default:
			break;
		}
	}

}
