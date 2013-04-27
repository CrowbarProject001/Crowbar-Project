package cbproject.core.misc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import cbproject.core.CBCMod;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cbproject.deathmatch.utils.InformationBullet;
import cbproject.deathmatch.utils.InformationSet;
import cbproject.deathmatch.utils.InformationWeapon;



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
				if(inf == null)
					return;
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
			try {
				short dimention = inputStream.readShort();
				int x = inputStream.readInt(),
						y = inputStream.readInt(),
						z = inputStream.readInt();
				boolean direction = inputStream.readBoolean();

				System.out.println("Dimension : " + dimention);
				TileEntity te = MinecraftServer.getServer().worldServerForDimension(dimention).getBlockTileEntity(x, y, z);
				if(te == null || !(te instanceof TileEntityWeaponCrafter)){
					if(te != null)
						te.validate();
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
