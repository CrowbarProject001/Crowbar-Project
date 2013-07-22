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
package cn.lambdacraft.deathmatch.register;

import cn.lambdacraft.deathmatch.client.HEVRenderingUtils;
import cn.lambdacraft.deathmatch.items.ArmorHEV;
import cn.lambdacraft.mob.entities.EntityBarnacle;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.EntityEvent.CanUpdate;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;

/**
 * 统一的Forge事件处理。
 * 
 * @author WeAthFolD
 */
public class DMEventHandler {

	@ForgeSubscribe
	@SideOnly(Side.CLIENT)
	public void onRenderGameOverlay(RenderGameOverlayEvent event) {

		EntityPlayer player = Minecraft.getMinecraft().thePlayer;
		ScaledResolution rs = new ScaledResolution(Minecraft.getMinecraft().gameSettings, Minecraft.getMinecraft().displayWidth, Minecraft.getMinecraft().displayHeight);
		if (event.type == ElementType.HEALTH || event.type == ElementType.ARMOR) {
			ItemStack chest = player.inventory.armorInventory[2];
			ItemStack helmet = player.inventory.armorInventory[3];
			if (chest != null && helmet != null) {
				if (chest.getItem() instanceof ArmorHEV
						&& helmet.itemID == DMItems.armorHEVHelmet.itemID) {
					ArmorHEV hev = (ArmorHEV) chest.getItem();
					if(hev.getItemCharge(chest) > 0 && hev.getItemCharge(helmet) > 0) {
						event.setCanceled(true);
						if(event.type == ElementType.HEALTH)
							HEVRenderingUtils.drawPlayerHud(player, rs);
					}
				}
			}
		}
	}
	
}
