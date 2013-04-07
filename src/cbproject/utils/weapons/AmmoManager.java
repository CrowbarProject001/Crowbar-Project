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
	public int type;
	public EntityPlayer player;
	public ItemStack Weapon;
	public List ammoList;
	public int ammoItemID;
	public int ammoCapacity;
	//加载基本的玩家背包子弹信息xD
	public AmmoManager(EntityPlayer par1Player,ItemStack par2Weapon) {
		
		// TODO Auto-generated constructor stub
		//type = ( par2Weapon.itemID == CBCMod.cbcItems.weapon_gauss.itemID || 
		//		par2Weapon.itemID == CBCMod.cbcItems.weapon_egon.itemID) ? 1 : 0 ;
		WeaponGeneral wpn = (WeaponGeneral)par2Weapon.getItem();
		type = wpn.type;
		ammoList = new ArrayList();
		player = par1Player;
		Weapon = par2Weapon;
		ammoItemID = getAmmoItemIDByWeapon(par2Weapon);
		setAmmoInformation(par1Player);
		System.out.println("Player Ammo Capacity : " + this.ammoCapacity);
		
	}
	
	public boolean consumeOtherStack(int amount, ItemStack itemStack){
		Weapon = itemStack;
		int left = amount;
		for(int i = 0; i < ammoList.size(); i++){
			ItemStack is = (ItemStack) ammoList.get(i);
			if(!is.equals(Weapon)){
				if(itemStack.getMaxStackSize() == 1){
					
				} else {
					
				}
			}
		}
		return false;
	}
	
	public Boolean consumeAmmo(int amount){
		
		ItemStack itemStack;
		int left = amount;
		updateAmmoCapacity();
		if(ammoCapacity < amount)return false;
		
		if(((ItemStack)ammoList.get(0)).getMaxStackSize() == 1){
			for(int i=0; i<ammoList.size(); i++){
				itemStack = (ItemStack)ammoList.get(i);
				int cap = itemStack.getMaxDamage() - itemStack.getItemDamage() -1;
				if(cap >0){
					if(cap >= left){
					
						itemStack.damageItem(left, null);
						ammoCapacity -= left;
						return true;
					
					}			
					left -= cap;
					
					itemStack.damageItem(cap, null);
				} 
			}
			
		} else {
				for( ; left>0; left--){
						if(!player.inventory.consumeInventoryItem(ammoItemID)){
							updateAmmoCapacity();
							return true;
						}
						else ammoCapacity--;
				}
				return true;	
		}
		
		System.out.println("It wasnt suppose to happen!");
		return false; //should never happen
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
