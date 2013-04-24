package cbproject.misc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import cbproject.CBCMod;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.elements.items.weapons.WeaponGeneralBullet;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;



import net.minecraft.client.Minecraft;
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
				
				double weaponID = inputStream.readDouble();
				int mode = inputStream.readInt();

				InformationSet inf = CBCMod.wpnInformation.getInformation(weaponID);
				InformationWeapon information = inf.serverReference;
				WeaponGeneral item = (WeaponGeneral) information.itemStack.getItem();
				item.onModeChange(information.itemStack, information.player.worldObj, mode);
				
				System.out.println("Server mode : " + information.mode);
				System.out.println("Get ID : " + weaponID);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
			
			/*
		case "CBCReload":
			try {
				
				double id = inputStream.readDouble();

				InformationSet inf = CBCMod.wpnInformation.getInformation(id);
				InformationBullet information = inf.getServerAsBullet();
				WeaponGeneralBullet item = (WeaponGeneralBullet) information.itemStack.getItem();
				if(!information.isReloading){
					System.out.println(information.player.worldObj.isRemote);
					information.player.worldObj.playSoundAtEntity(information.player, item.getSoundReload(information.mode), 0.5F, 1.0F);
				}
				information.isReloading = true;
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			*/
		default:
			break;
		}
	}

}
