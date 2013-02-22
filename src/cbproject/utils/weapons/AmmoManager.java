package cbproject.utils.weapons;

import java.util.List;

import cpw.mods.fml.common.Mod.Item;

import cbproject.CBCMod;
import cbproject.elements.items.weapons.Weapon_egon;
import cbproject.elements.items.weapons.Weapon_gauss;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

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
		type = ( par2Weapon.itemID == CBCMod.cbcItems.weapon_gauss.itemID || 
				par2Weapon.itemID == CBCMod.cbcItems.weapon_egon.itemID) ? 1 : 0 ;
		ammoList = null;
		
		Weapon = par2Weapon;
		ammoItemID = getAmmoItemIDByWeapon(par2Weapon);
		setAmmoInformation(par1Player);
		
	}
	
	private void setAmmoInformation(EntityPlayer par1Player){
		
		ItemStack itemStack;
		
		//遍历寻找对应的Ammo
		for( int i=0; i<36; i++){
			itemStack = par1Player.inventory.getStackInSlot(i);
			if( itemStack.itemID == ammoItemID){
				ammoList.add(itemStack);
			}
		}
		
		if(ammoList.size() == 0) {
			ammoCapacity =0;
			return;
		}
		
		{	
			int size = ammoList.size();
			ItemStack item = null;
			
			for(int i=0;i < size; i++)
			{
				item = (ItemStack)ammoList.get(i);
				ammoCapacity += item.stackSize;
			}
		}
	}
	
	//消耗玩家背包中的子弹
	public Boolean consumeAmmo(){
		ItemStack itemStack;
		for(int i=0; i<ammoList.size(); i++){
			
			itemStack = (ItemStack)ammoList.get(i);
			if(--itemStack.stackSize < 0){
				ammoList.remove(i);
				continue;
			}
			return true;
		}
		return false;
	}
	
	public Boolean consumeAmmo(int amount){
		
		ItemStack itemStack;
		int left = amount;
		
		if(ammoCapacity < amount)return false;
		
		for(int i=0; i<ammoList.size(); i++){
			
			itemStack = (ItemStack)ammoList.get(i);
			
			if(itemStack.stackSize > 0){
				if(itemStack.stackSize >= left){
					
					itemStack.stackSize -= left;
					if(itemStack.stackSize == 0)
						ammoList.remove(i);
					return true;
					
				} else {
					
					left -= itemStack.stackSize;
					itemStack.stackSize =0;
					ammoList.remove(i);
					
				}
				continue;
			}
		}
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
		
		return (par1Weapon.itemID == CBCMod.cbcItems.weapon_gauss.itemID || 
				par1Weapon.itemID == CBCMod.cbcItems.weapon_egon.itemID)? CBCMod.cbcItems.itemAmmo_uranium.itemID :
				((par1Weapon.itemID == CBCMod.cbcItems.weapon_9mmhandgun.itemID ||
				par1Weapon.itemID == CBCMod.cbcItems.weapon_9mmAR.itemID) ? CBCMod.cbcItems.itemAmmo_9mm.itemID: -1);
	}
	
	
	public int getAmmoCapacity(){
		return ammoCapacity;
	}

}
