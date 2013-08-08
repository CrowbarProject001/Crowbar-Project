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
package cn.lambdacraft.deathmatch.utils;

import java.util.EnumSet;
import java.util.HashMap;
import java.util.Map;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

import cn.lambdacraft.api.weapon.ISpecialUseable;
import cn.lambdacraft.deathmatch.network.NetClicking;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class ItemHelper implements ITickHandler {

	protected static class UsingStatus {
		
		int useDuration;
		
		/**
		 * Using tick left for current ItemStack, stop using once the duration reaches 0.
		 */
		int useDurationLeft;
		/**
		 * true for left, false for right.
		 */
		boolean usingSide;
		
		ItemStack stack;
		
		public UsingStatus() {}
		
		public UsingStatus(int maxUse, boolean side, ItemStack stack) {
			resetStatus(maxUse, side, stack);
		}
		
		public boolean handleUpdate(EntityPlayer player) {
			ItemStack curItem = player.getCurrentEquippedItem(); 
			return --useDurationLeft > 0 && stack != null && curItem != null && stack.itemID == curItem.itemID;
		}
		
		public boolean isUsing(EntityPlayer player) {
			ItemStack curItem = player.getCurrentEquippedItem(); 
			return useDurationLeft > 0 && stack != null && curItem != null && stack.itemID == curItem.itemID;
		}
		
		public void resetStatus(int maxUse, boolean side, ItemStack is) {
			useDuration = useDurationLeft = maxUse;
			usingSide = side;
			stack = is;
		}
		
		public void stopUsing(EntityPlayer player) {
			if(stack != null) {
				Item instance = stack.getItem();
				instance.onPlayerStoppedUsing(stack, player.worldObj, player, useDuration - useDurationLeft);
			}
			useDurationLeft = 0;
			stack = null;
		}
 	}
	
	/**
	 * 特殊的使用函数中会用到的，玩家和使用状态的映射表。
	 */
	protected static HashMap<EntityPlayer, UsingStatus> usingPlayerMap_server = new HashMap(), usingPlayerMap_client = new HashMap();
	
	public static void setItemInUse(EntityPlayer player, ItemStack currentStack, int maxCount, boolean side) {
		//Put the usingStatus into map, or reuse it if exists
		UsingStatus stat;
		Map<EntityPlayer, UsingStatus> usingPlayerMap = player.worldObj.isRemote ? usingPlayerMap_client : usingPlayerMap_server;
		if(!usingPlayerMap.containsKey(player)) {
			stat = new UsingStatus();
			usingPlayerMap.put(player, stat);
		} else stat = usingPlayerMap.get(player);
		stat.resetStatus(maxCount, side, currentStack);
	}

	
	/**
	 * 用来确定是否在【特殊使用】一个物品。
	 * @param player 需要确认的玩家
	 * @return 如果为0则未使用，否则是剩余的usingTick
	 */
	public static int getUsingTickLeft(EntityPlayer player) {
		Map<EntityPlayer, UsingStatus> usingPlayerMap = player.worldObj.isRemote ? usingPlayerMap_client : usingPlayerMap_server;
		UsingStatus stat = usingPlayerMap.get(player);
		if(stat == null)
			return 0;
		return stat.isUsing(player) ? stat.useDurationLeft : 0;
	}
	
	/**
	 * 用来确定物品是因左键还是右键开始使用。
	 * @param player
	 * @return 未使用则false，否则左键true,右键false.
	 */
	public static boolean getUsingSide(EntityPlayer player) {
		Map<EntityPlayer, UsingStatus> usingPlayerMap = player.worldObj.isRemote ? usingPlayerMap_client : usingPlayerMap_server;
		UsingStatus stat = usingPlayerMap.get(player);
		if(stat == null)
			return false;
		return stat.usingSide;
	}
	
	public static void stopUsingItem(EntityPlayer player) {
		Map<EntityPlayer, UsingStatus> usingPlayerMap = player.worldObj.isRemote ? usingPlayerMap_client : usingPlayerMap_server;
		UsingStatus stat = usingPlayerMap.get(player);
		if(stat != null) {
			stat.stopUsing(player);
			if(player.worldObj.isRemote)
				NetClicking.sendPacketData(stat.usingSide ? 2 : -2);
		}
	}


	@Override
	public void tickStart(EnumSet<TickType> type, Object... tickData) {
		if(type.contains(TickType.PLAYER)) {
			EntityPlayer player = (EntityPlayer) tickData[0];
			World world = player.worldObj;
			Map<EntityPlayer, UsingStatus> usingPlayerMap = world.isRemote ? usingPlayerMap_client : usingPlayerMap_server;
			UsingStatus stat = usingPlayerMap.get(player);
			if(stat != null && stat.isUsing(player)) {
				if(!stat.handleUpdate(player)) {
					stat.stopUsing(player);
				} else {
					Item item = stat.stack.getItem();
					if(item instanceof ISpecialUseable) {
						((ISpecialUseable)item).onItemUsingTick(world, player, stat.stack, stat.usingSide, stat.useDurationLeft);
					}
				}
			}
		}
	}


	@Override
	public void tickEnd(EnumSet<TickType> type, Object... tickData) {}


	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.PLAYER);
	}


	@Override
	public String getLabel() {
		return "LambdaCraft Special Using";
	}

}
