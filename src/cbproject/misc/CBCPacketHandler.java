package cbproject.misc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import cbproject.CBCMod;
import cbproject.elements.blocks.BlockWeaponCrafter.TileEntityWeaponCrafter;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.elements.items.weapons.WeaponGeneralBullet;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;



import net.minecraft.client.Minecraft;
import net.minecraft.item.Item;
import net.minecraft.network.INetworkManager;
import net.minecraft.network.packet.Packet250CustomPayload;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
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
			
		case "CBCCrafterScroll":
			System.out.println("recieved packet");
			try {
				
				int x = inputStream.readInt(),
						y = inputStream.readInt(),
						z = inputStream.readInt();
				boolean direction = inputStream.readBoolean();

				TileEntity te = Minecraft.getMinecraft().theWorld.getBlockTileEntity(x, y, z);
				MinecraftServer.getServer().worldServerForDimension(1);
				if(te == null || !(te instanceof TileEntityWeaponCrafter)){
					System.err.println("Didn't get the right tileentity...");
					return;
				}
				if(!te.worldObj.isRemote)
					((TileEntityWeaponCrafter)te).addScrollFactor(direction);
				
			} catch (IOException e) {
				e.printStackTrace();
			}
			break;
		default:
			break;
		}
	}

}
