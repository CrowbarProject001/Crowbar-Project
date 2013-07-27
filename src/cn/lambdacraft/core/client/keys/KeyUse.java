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
package cn.lambdacraft.core.client.keys;

import cn.lambdacraft.core.network.NetKeyUsing;
import cn.lambdacraft.core.register.IKeyProcess;
import cn.lambdacraft.deathmatch.flashlight.ClientTickHandler;
import cn.lambdacraft.deathmatch.proxy.ClientProxy;
import cn.lambdacraft.deathmatch.register.DMItems;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * 使用按键的处理类，负责发包和功能性函数。
 * 
 * @see cn.lambdacraft.core.network.NetKeyUsing
 * @see cn.lambdacraft.api.tile.IUseable
 * @author WeAthFolD
 * 
 */
public class KeyUse implements IKeyProcess {

	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.lambdacraft.core.register.IKeyProcess#onKeyDown()
	 */
	@Override
	public void onKeyDown() {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (player == null)
			return;
		NetKeyUsing.sendUsingPacket(true);
	}

	
	/*
	 * (non-Javadoc)
	 * 
	 * @see cn.lambdacraft.core.register.IKeyProcess#onKeyUp()
	 */
	@Override
	public void onKeyUp() {
		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		if (player == null)
			return;
		NetKeyUsing.sendUsingPacket(false);
		ItemStack armorStack = player.inventory.armorInventory[3];
		if (armorStack == null)
			return;
		if (Minecraft.getMinecraft().theWorld.isRemote) {
			ClientProxy.cth.flag = !ClientProxy.cth.flag;
			if (armorStack.itemID == DMItems.armorHEVHelmet.itemID)
				player.sendChatToPlayer(StatCollector.translateToLocal("flashlight.status.name") + (ClientProxy.cth.flag ? StatCollector.translateToLocal("flashlight.status.on.name") : StatCollector.translateToLocal("flashlight.status.off.name")));
		}
	}

}
