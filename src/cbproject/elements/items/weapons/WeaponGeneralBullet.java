package cbproject.elements.items.weapons;

import java.util.ArrayList;
import java.util.List;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.AmmoManager;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBulletWeapon;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class WeaponGeneralBullet extends Item {
	
	
	public List listItemStack;
	public int mode;
	public int ammoID;
	
	public  int reloadTime ;
	public  int jamTime;
	public  int shootTime[];
	
	public String pathSoundShoot,pathSoundJam,pathSoundReload;
	
	/*Local Variables in ItemStack InformationBulletWeapon
	 * 
	 * canUse(Boolean) : To flag whether the gun is available for shoot.
	 * isShooting(Boolean) : To flag whether the gun is shooting or not.
	 * isReloading(Boolean) : To flag whether the gun is reloading or not.
	 * isAlive(Boolean) : To flag whether the itemEntity exists or not.
	 * @See:cbproject.utils.weapons.InformationiBulletWeapon.java
	 * 
	 */
	
	public WeaponGeneralBullet(int par1 , int par2ammoID) {
		
		super(par1);
		
		listItemStack = new ArrayList();
		ammoID = par2ammoID;
		
	}
	
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	
    public void onBulletWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {

		if(par2World.isRemote || par1ItemStack.getTagCompound() == null)
			return;
		int id = par1ItemStack.getTagCompound().getInteger("weaponID");
		if(id == 0 || id > listItemStack.size())
			return;
		id--;
		InformationBulletWeapon information = this.getBulletWpnInformation(id);
		if( information.signID != par1ItemStack.getTagCompound().getDouble("uniqueID") || information == null)
			return;

		information.updateTick();
		int ticksExisted = information.ticksExisted;
		int lastTick = information.lastTick;
		
		Boolean isShooting = information.isShooting;
		Boolean isReloading = information.isReloading;
		Boolean canUse = information.canUse;
		
		if(isReloading && !information.rsp){
			par2World.playSoundAtEntity(par3Entity, pathSoundReload , 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			information.rsp = true;
			return;
		}
		
		if( isReloading && ticksExisted - lastTick >= reloadTime ){
			
			this.onBulletWpnReload(par1ItemStack, par2World, par3Entity, information);
			return;
		}
		
		if(isShooting && canUse && ticksExisted - lastTick >= shootTime[mode] ){
			this.onBulletWpnShoot(par1ItemStack, par2World, par3Entity, information);
			return;
		}
        
		if( isShooting && !canUse && ticksExisted - lastTick >= jamTime ){
			this.onBulletWpnJam(par1ItemStack, par2World, par3Entity, information);
			return;
		}
		
    }

    public void onBulletWpnReload(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBulletWeapon information ){

		int dmg = par1ItemStack.getItemDamage();
		int maxDmg = par1ItemStack.getMaxDamage() -1;
		if( dmg <= 0){
			information.setLastTick();
			information.isReloading = false;
			information.canUse = true;
			information.rsp = false;
			return;
		}
		
		//int cap = information.ammoManager.ammoCapacity;
		int cap = 33333;
		if( dmg >= cap ){
			//ammoManager.clearAmmo( entityPlayer );
			cap = 0;
			par1ItemStack.setItemDamage( par1ItemStack.getItemDamage() - cap);
		} else {
			//ammoManager.consumeAmmo( dmg );
			cap -= dmg;
			par1ItemStack.setItemDamage( 0 );
		}
		
		if( par1ItemStack.getItemDamage() >=maxDmg )
			information.canUse = false;
		
		information.isReloading = false;
		information.setLastTick();
		information.rsp = false;
		
		return;
    }
    
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBulletWeapon information ){
    	int maxDmg = par1ItemStack.getMaxDamage() -1;
		if( par1ItemStack.getItemDamage() >= maxDmg ){
			
			information.canUse = false;
			information.setLastTick();
			return;
		}
		
		CBCMod.bulletManager.Shoot( (EntityPlayer) par3Entity , par2World);
		par2World.playSoundAtEntity(par3Entity, pathSoundShoot, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		par1ItemStack.damageItem( 1 , null);
		
		information.setLastTick();
		return;
    }
    
    public void onBulletWpnJam(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBulletWeapon information ){
    	
    	int maxDmg = par1ItemStack.getMaxDamage();
		if( par1ItemStack.getItemDamage() < maxDmg){
			information.canUse = true;
			return;
		}
		par2World.playSoundAtEntity(par3Entity, pathSoundJam, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		information.setLastTick();
		
    }
    
    public final InformationBulletWeapon getBulletWpnInformation(ItemStack par1ItemStack){
    	
    	int id;
    	double uniqueID;
    	
		if(par1ItemStack.getTagCompound() == null){
			par1ItemStack.stackTagCompound = new NBTTagCompound();
			return null;
		}
		
		id = par1ItemStack.getTagCompound().getInteger("weaponID");
		
		InformationBulletWeapon information;

		if( id == 0 || id>= listItemStack.size()){
			return null;
		}

		uniqueID = par1ItemStack.getTagCompound().getDouble("uniqueID");
		information = (InformationBulletWeapon)listItemStack.get( --id );
		if( uniqueID != information.signID )
			return null;
		
		return information;
    }
    
    public final int addBulletWpnInformation( InformationBulletWeapon par1Inf , ItemStack par2Item){
    	
		int id;
		
		if( par2Item.getTagCompound() == null)
			par2Item.stackTagCompound = new NBTTagCompound();
		double uniqueID = Math.random();
		
		par1Inf = new InformationBulletWeapon( uniqueID, false, false, false, 0);
		listItemStack.add(par1Inf);
		par2Item.getTagCompound().setInteger("weaponID", listItemStack.size()); //这里的值是物品索引+1 为了令出初始索引为0帮助判断
		par2Item.getTagCompound().setDouble("uniqueID", uniqueID);
		id = listItemStack.size()-1;
		
		return id;
			
    }
    
    public final InformationBulletWeapon getBulletWpnInformation(int id){
    	return (InformationBulletWeapon)listItemStack.get(id);
    }
    

}
