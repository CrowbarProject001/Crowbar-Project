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

import cn.lambdacraft.core.CBCPlayer;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.client.HEVRenderingUtils;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Satchel;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.event.ForgeSubscribe;
import net.minecraftforge.event.entity.player.EntityInteractEvent;

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
		boolean hasHEV = CBCPlayer.armorStat[2] && CBCPlayer.armorStat[3];
		if (event.type == ElementType.HEALTH || event.type == ElementType.ARMOR || event.type == ElementType.CROSSHAIRS) {
			if (hasHEV) 
				event.setCanceled(true);
		}
		if(hasHEV && event.type == ElementType.EXPERIENCE) {
				HEVRenderingUtils.drawPlayerHud(player, event.resolution, player.ticksExisted);
				HEVRenderingUtils.drawCrosshair(player.getCurrentEquippedItem(), event.resolution.getScaledWidth(), event.resolution.getScaledHeight());
		}
		else if(ClientProps.alwaysCustomCrossHair && event.type == ElementType.CROSSHAIRS) {
			event.setCanceled(true);
			HEVRenderingUtils.drawCrosshair(player.getCurrentEquippedItem(), event.resolution.getScaledWidth(), event.resolution.getScaledHeight());
		}
	}
	
	  @ForgeSubscribe
	  public void onInteract(EntityInteractEvent event)
	  {
		  ItemStack curItem = event.entityPlayer.getCurrentEquippedItem();
		  if(curItem == null)
			  return;
		  if(curItem.getItem() instanceof Weapon_Satchel) {
			  event.setCanceled(true);
		  }
	  }
	
}
