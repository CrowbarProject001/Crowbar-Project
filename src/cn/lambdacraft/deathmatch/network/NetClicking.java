/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.network;

import java.io.ByteArrayOutputStream;
import java.io.DataInputStream;
import java.io.DataOutputStream;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.Packet250CustomPayload;

import cpw.mods.fml.common.network.PacketDispatcher;
import cpw.mods.fml.common.network.Player;
import cn.lambdacraft.api.weapon.ISpecialUseable;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.register.CBCNetHandler;
import cn.lambdacraft.core.register.IChannelProcess;
import cn.lambdacraft.deathmatch.utils.ItemHelper;

/**
 * @author WeAthFolD
 *
 */
public class NetClicking implements IChannelProcess {

	
	/**
	 * 1 : leftUse, 2 : leftStop
	 * -1 : rightUse, -2 : rightStop
	 * @param type
	 * @param additional
	 */
	public static void sendPacketData(int type) {
		ByteArrayOutputStream bos = CBCNetHandler.getStream(
				GeneralProps.NET_ID_PRIMSHOOT, 1);
		DataOutputStream outputStream = new DataOutputStream(bos);
		
		try {
			outputStream.writeByte(type);
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		Packet250CustomPayload packet = new Packet250CustomPayload();
		packet.channel = GeneralProps.NET_CHANNEL_SERVER;
		packet.data = bos.toByteArray();
		packet.length = bos.size();
		PacketDispatcher.sendPacketToServer(packet);
	}

	/* (non-Javadoc)
	 * @see cn.lambdacraft.core.register.IChannelProcess#onPacketData(java.io.DataInputStream, cpw.mods.fml.common.network.Player)
	 */
	@Override
	public void onPacketData(DataInputStream stream, Player dummyPlayer) {
		try {
			int type = stream.readByte();
			EntityPlayer player = (EntityPlayer) dummyPlayer;
			boolean wrong = false;
			
			if(type == 1 || type == -1) { //use
				boolean left = type > 0;
				ItemStack stack = player.getCurrentEquippedItem();
				if(stack != null) {
					Item item = stack.getItem();
					if(item instanceof ISpecialUseable) {
						((ISpecialUseable)item).onItemClick(player.worldObj, player, stack, left);
					} else wrong = true;
				} else wrong = true;
				
			} else { //stop
				
				ItemHelper.stopUsingItem(player);
				
			}
			if(wrong)
				CBCMod.log.severe("Coudn't find the right left clicking Item instance...");
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

}
