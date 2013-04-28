package cbproject.core.misc;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;

import cbproject.core.CBCMod;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.crafting.network.NetWeaponCrafter;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.items.wpns.WeaponGeneralBullet;
import cbproject.deathmatch.network.NetDeathmatch;
import cbproject.deathmatch.utils.InformationBullet;
import cbproject.deathmatch.utils.InformationSet;
import cbproject.deathmatch.utils.InformationWeapon;



import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
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
		
		if(packet.channel == "CBCWeaponMode"){ 
			EntityPlayer p = (EntityPlayer) player;
			int[] prop = NetDeathmatch.getModePacket(packet);
			ItemStack is = p.inventory.mainInventory[prop[0]];
			if(is == null ||!(is.getItem() instanceof WeaponGeneral)){
				System.err.println("Didn't find correct information for" + p.getEntityName() + " @" + prop[0]);
			} else {
				WeaponGeneral wpn = (WeaponGeneral) is.getItem();
				wpn.onModeChange(is, p.worldObj, prop[1]);
				System.out.println("Server new mode :" + prop[1]);
			}
			return;
		}
		if(packet.channel == "CBCWeaponMode"){
			NetWeaponCrafter c = new NetWeaponCrafter().getCrafterPacket(packet);
			TileEntity te = MinecraftServer.getServer().worldServerForDimension(c.dimension).getBlockTileEntity(c.blockX, c.blockY, c.blockZ);
			if(!te.worldObj.isRemote){
				if(c.id == 0)
					((TileEntityWeaponCrafter)te).addScrollFactor(c.direction);
				else ((TileEntityWeaponCrafter)te).addPage(c.direction);
			}
			return;
		}
	}

}
