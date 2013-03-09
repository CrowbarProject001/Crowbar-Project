package cbproject.utils.weapons;

import java.util.ArrayList;
import java.util.List;

import cbproject.CBCMod;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.elements.items.weapons.Weapon_egon;
import cbproject.elements.items.weapons.Weapon_gauss;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;

public class AmmoManager {
	
	//type:0 Bullet Weapon ; 1 Energy Weapon
	public int type;
	
	public ItemStack Weapon;
	public List ammoList;
	public int ammoItemID;
	public int ammoCapacity;
	//加载基本的玩家背包子弹信息xD
	public AmmoManager(EntityPlayer par1Player,ItemStack par2Weapon) {
		
		// TODO Auto-generated constructor stub
		//type = ( par2Weapon.itemID == CBCMod.cbcItems.weapon_gauss.itemID || 
		//		par2Weapon.itemID == CBCMod.cbcItems.weapon_egon.itemID) ? 1 : 0 ;
		type = 1;
		ammoList = new ArrayList();
		
		Weapon = par2Weapon;
		ammoItemID = getAmmoItemIDByWeapon(par2Weapon);
		setAmmoInformation(par1Player);
		System.out.println("Player Ammo Capacity : " + this.ammoCapacity);
		
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
	
	//消耗一颗子弹
	public Boolean consumeAmmo(){
		ItemStack itemStack;
		for(int i=0; i<ammoList.size(); i++){
			
			itemStack = (ItemStack)ammoList.get(i);
			itemStack.damageItem(1, null);
			if(itemStack.getMaxDamage() <= itemStack.getItemDamage()){
				itemStack.stackSize = 0;
				ammoList.remove(i);
				continue;
			}
			return true;
		}
		updateAmmoCapacity();
		return false;
	}
	
	public Boolean consumeAmmo(int amount){
		
		ItemStack itemStack;
		int left = amount;
		updateAmmoCapacity();
		if(ammoCapacity < amount)return false;
		
		for(int i=0; i<ammoList.size(); i++){
			
			itemStack = (ItemStack)ammoList.get(i);
			
			if(itemStack.getItemDamage() > 0){
				int cap = itemStack.getMaxDamage() - itemStack.getItemDamage();
				if(cap >= left){
					
					itemStack.damageItem(left, null);
					ammoCapacity -= left;
					if(itemStack.stackSize <= 0){
						itemStack.stackSize = 0;
						ammoList.remove(i);
					}
					
					updateAmmoCapacity();
					return true;
					
				} else {
					
					left -= cap;
					itemStack.stackSize = 0;
					ammoList.remove(i);
					
				}
			}
		}
		
		updateAmmoCapacity();
		return false; //should never happen
	}
	
	public void clearAmmo(EntityPlayer par1Player){
		
		for(int i=0;i<ammoList.size();i++){
			ItemStack item = (ItemStack)ammoList.get(i);
			item.stackSize = 0;
		}
		
		ammoList.clear();
		setAmmoInformation(par1Player);
		
	}
	
	private int getAmmoItemIDByWeapon(ItemStack par1Weapon){
		
		Item item =  (Item) par1Weapon.getItem();
		if( item instanceof WeaponGeneral ){
			return ((WeaponGeneral)item).ammoID;
		}
		return -1;
		
	}
	
	
	public int getAmmoCapacity(){
		updateAmmoCapacity();
		return ammoCapacity;
	}

	public void updateAmmoCapacity(){
		
		int size = ammoList.size();
		ItemStack item = null;
		ammoCapacity = 0;
		if( size == 0) 
			return;
		
		for(int i=0;i < size; i++)
		{
			item = (ItemStack)ammoList.get(i);
			int a = item.getMaxDamage() - item.getItemDamage();
			System.out.println("Cur ammo cap : " + a);
			ammoCapacity += a;
		}
	}
}
