package cbproject.utils.weapons;

import java.util.ArrayList;
import java.util.List;

import cbproject.CBCMod;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.elements.items.weapons.Weapon_egon;
import cbproject.elements.items.weapons.Weapon_gauss;


import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;
import net.minecraft.nbt.NBTTagCompound;

public class AmmoManager {
	
	//type:0 Bullet Weapon ; 1 Energy Weapon
	//Stacksize=1:Damage型；StackSize>1:stack型
	public EntityPlayer player;
	public ItemStack Weapon;
	public int ammoItemID;
	public int ammoCapacity;
	
	private List<ItemStack> ammoList;
	
	//加载基本的玩家背包子弹信息xD
	public AmmoManager(EntityPlayer par1Player,ItemStack par2Weapon) {
		
		WeaponGeneral wpn = (WeaponGeneral)par2Weapon.getItem();
		ammoList = new ArrayList<ItemStack>();
		player = par1Player;
		Weapon = par2Weapon;
		ammoItemID = getAmmoItemIDByWeapon(par2Weapon);
		setAmmoInformation(par1Player);
		
	}
	
	
	public int consumeAmmo(int amount){
		
		int left = amount;
		updateAmmoCapacity();
		
		if(Item.itemsList[ammoItemID].getItemStackLimit() == 1){
			
			if(ammoList.isEmpty())
				return left;
			for(ItemStack itemStack : ammoList){
				int cap = itemStack.getMaxDamage() - itemStack.getItemDamage() -1;
				if(cap >0){
					if(cap >= left){
						itemStack.damageItem(left, player);
						ammoCapacity -= left;
						return 0;
					}			
					left -= cap;
					itemStack.damageItem(cap, player);
				} 
			}
			return left;
			
		} else {
			
				return left - tryConsume(player, ammoItemID, amount);
				
		}
		
	}
	
	public int getAmmoCapacity(){
		updateAmmoCapacity();
		return ammoCapacity;
	}
	
	public void clearAmmo(EntityPlayer par1Player){
		
		if(ammoList.size() == 0 || ((ItemStack)ammoList.get(0)).getMaxStackSize() == 1){
			
			for(int i=0;i<ammoList.size();i++){
				ItemStack item = (ItemStack)ammoList.get(i);
				item.setItemDamage(item.getMaxDamage() -1);
			}
			
		} else {
			
			updateAmmoCapacity();
			for(int i=0; i < ammoCapacity; i++)
				player.inventory.consumeInventoryItem(ammoItemID);
			
		}
		ammoList.clear();
		setAmmoInformation(par1Player);
		
	}
	
	public void setAmmoInformation(EntityPlayer par1Player){
		
		ItemStack itemStack;
		ammoList.clear();
		//遍历寻找对应的Ammo
		for( int i=0; i<36; i++){
			itemStack = par1Player.inventory.getStackInSlot(i);
			if( itemStack != null && itemStack.itemID == ammoItemID) {
				ammoList.add(itemStack);
			}
			
		}
		updateAmmoCapacity();
		
	}
	
	/**
	 * Tries to consume one specific item in player's inventory.
	 *  ** Stackable only.
	 * @return how many of the stack consumed
	 */
	public int tryConsume(EntityPlayer player, int itemID, int amount){	
		if(!player.inventory.hasItem(itemID))
			return 0;
		int i;
		for(i = 1; i <= amount; i++)
			if(!player.inventory.consumeInventoryItem(itemID)){
				i--;
				break;
			}
		return i;
	}
	

	private int getAmmoItemIDByWeapon(ItemStack par1Weapon){
	
		WeaponGeneral item =  (WeaponGeneral) par1Weapon.getItem();
		return item.ammoID;
		
	}
	

	private void updateAmmoCapacity(){
		
		int size = ammoList.size();
		ItemStack item = null;
		ammoCapacity = 0;
		if( size == 0) 
			return;
		
		for(int i=0;i < size; i++)
		{
			item = (ItemStack)ammoList.get(i);
			if(item.getMaxStackSize() == 1){
				int a = item.getMaxDamage() - item.getItemDamage() -1; 
				ammoCapacity += a;
			} else {
				int a = item.stackSize;
				ammoCapacity += a;
			}
		}

	}
}
