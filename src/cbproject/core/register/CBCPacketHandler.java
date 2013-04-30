package cbproject.core.register;

import java.io.ByteArrayInputStream;
import java.io.DataInputStream;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;

import cbproject.core.CBCMod;
import cbproject.core.network.IChannelProcess;
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
