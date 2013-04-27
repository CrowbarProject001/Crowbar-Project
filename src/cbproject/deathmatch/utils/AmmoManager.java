package cbproject.deathmatch.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import cbproject.core.CBCMod;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.items.wpns.Weapon_egon;
import cbproject.deathmatch.items.wpns.Weapon_gauss;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;

public class AmmoManager {
	
	/**
	 * Tries to consume a specific amount of ammo  in player's inventory.
	 * @return how many are left to be consumed
	 */
	public static int consumeAmmo(EntityPlayer player, WeaponGeneral item, int amount){
		
		return tryConsume(player, item.ammoID, amount);
		
	}
	
	/**
	 * Tries to consume one specific item in player's inventory.
	 * @return how many are left to be consumed
	 */
	public static int tryConsume(EntityPlayer player, int itemID, int amount){	
		
		int left = amount;
		ItemStack is;
		if(Item.itemsList[itemID].getItemStackLimit() > 1){
			
			for(int i = 0; i < player.inventory.mainInventory.length; i++){
				is = player.inventory.mainInventory[i];
				if(is != null && is.itemID == itemID){
					if(is.stackSize > left){
						player.inventory.decrStackSize(i, left);
						return 0;
					} else {
						left -= is.stackSize;
						player.inventory.setInventorySlotContents(i, null);
					}
				}
			}
			return left;
			
		} else {
			
			for(int i=0; i< player.inventory.mainInventory.length; i++){
				is = player.inventory.mainInventory[i];
				int stackCap;
				if(is != null && is.itemID == itemID){
					stackCap = is.getMaxDamage() - is.getItemDamage() - 1;
					if(stackCap > left){
						is.damageItem(left, player);
						return 0;
					} else {
						left -= stackCap;
						is.setItemDamage(is.getMaxDamage() - 1);
					}
				}
			}
			return left;
			
		}
	}
	
	/**
	 * determine if player have any ammo for reloading/energy weapon shooting.
	 */
	public static Boolean hasAmmo(WeaponGeneral is, EntityPlayer player){
		return player.inventory.hasItem(is.ammoID);
	}
}
