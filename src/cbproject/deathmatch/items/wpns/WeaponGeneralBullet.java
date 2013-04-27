package cbproject.deathmatch.items.wpns;


import org.lwjgl.input.Keyboard;

import cbproject.core.CBCMod;
import cbproject.core.misc.CBCKeyProcess;
import cbproject.core.utils.CBCWeaponInformation;
import cbproject.deathmatch.utils.AmmoManager;
import cbproject.deathmatch.utils.BulletManager;
import cbproject.deathmatch.utils.InformationBullet;
import cbproject.deathmatch.utils.InformationEnergy;
import cbproject.deathmatch.utils.InformationSet;
import cbproject.deathmatch.utils.InformationWeapon;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * General bullet weapon class, including 9mmhandgun, rpg, etc...
 * @author WeAthFolD
 *
 */
public abstract class WeaponGeneralBullet extends WeaponGeneral {
	
	public  int reloadTime;
	public  int jamTime;
	
	public WeaponGeneralBullet(int par1 , int par2ammoID, int par3maxModes) {
		
		super(par1, par2ammoID, par3maxModes);	
		maxModes = par3maxModes;
		
		upLiftRadius = 10;
		recoverRadius = 2;
		type = 0;
		
	}

	
	/**
	 * Generally do the itemRightClick processing.
	 */
	public InformationBullet processRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){

		InformationBullet information = loadInformation(par1ItemStack, par3EntityPlayer);
		Boolean canUse = (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() -1 > 0);
		int mode = information.mode;
		
		if(canUse){
			if(information.getDeltaTick() >= getShootTime(mode)){
				this.onBulletWpnShoot(par1ItemStack, par2World, par3EntityPlayer, information);
			}
			information.isShooting = true;
		} else  {
			if(!information.isReloading)
				par2World.playSoundAtEntity(par3EntityPlayer, getSoundReload(mode), 0.5F, 1.0F);
			information.isReloading = true;
		}
		
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		par3EntityPlayer.setEating(false);
		return information;
	}
	
    public void onBulletWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
    	   	
    	InformationBullet information = (InformationBullet) super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);

    	if(information == null){
    		information = getInformation(par1ItemStack, par2World);
    		if(information == null)
    			return;
    		
    		information.isShooting = false;
    		information.isReloading = false;
    		return;
    	}
    	information.updateTick();
    	/*
    	if(Keyboard.isKeyDown(CBCKeyProcess.Key_Reload)){
    		System.out.println(par2World.isRemote);
    		if(!information.isReloading)
    			par2World.playSoundAtEntity(par3Entity, getSoundReload(information.mode), 0.5F, 1.0F);
    		information.isReloading = true;
    	}
    	*/
    	EntityPlayer player = (EntityPlayer) par3Entity;
    	if(!player.isUsingItem())
    		information.isShooting  = false;
    	
		if(doesShoot(information, par1ItemStack))
			this.onBulletWpnShoot(par1ItemStack, par2World, player, information);
		
		if(doesReload(information, par1ItemStack))
			this.onBulletWpnReload(par1ItemStack, par2World, player, information);
		
		if(doesReload(information, par1ItemStack))
			this.onBulletWpnJam(par1ItemStack, par2World, player, information);

	}
    
    public Boolean canShoot(EntityPlayer player, ItemStack is){
    	return (is.getMaxDamage() - is.getItemDamage() -1 > 0) || player.capabilities.isCreativeMode;
    }
    
    /**
     * Determine if the shoot method should be called this tick.
     * @return If the shoot method should be called in this tick or not.
     */
	public Boolean doesShoot(InformationBullet inf, ItemStack itemStack){
		Boolean canUse;
		canUse = (itemStack.getMaxDamage() - itemStack.getItemDamage() -1 > 0);
		return (getShootTime(inf.mode) != 0 && inf.isShooting && canUse && inf.getDeltaTick() >= getShootTime(inf.mode) );
	}
	
    /**
     * Determine if the Reload method should be called this tick.
     * @return If the Reload method should be called in this tick or not.
     */
	public Boolean doesReload(InformationBullet inf, ItemStack itemStack){
		return ( inf.isReloading && inf.getDeltaTick() >= this.reloadTime);
	}
	
    /**
     * Determine if the onBulletWpnJam() method should be called this tick.
     * @return If the jam method should be called in this tick or not.
     */
	public Boolean doesJam(InformationBullet inf, ItemStack itemStack){
		Boolean canUse;
		canUse = (itemStack.getMaxDamage() - itemStack.getItemDamage() -1 > 0);
		return ( jamTime != 0 && inf.isShooting && !canUse );
	}
	
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){
		
		int mode = information.mode;
		information.setLastTick();
		
		par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 1.0F);	
		BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");
    	if(par3Entity instanceof EntityPlayer){
    		doUplift(information, par3Entity);
    		if(!((EntityPlayer)par3Entity).capabilities.isCreativeMode ){
    				par1ItemStack.damageItem( 1 , par3Entity);
    		}
    	}
		return;	
    }
    
    public void onBulletWpnJam(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){ 	 
		par2World.playSoundAtEntity(par3Entity, getSoundJam(information.mode), 0.5F, 1.0F);
		information.setLastTick();
    }

    public void onBulletWpnReload(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){

    	EntityPlayer player = (EntityPlayer) par3Entity;
    	
    	int dmg = par1ItemStack.getItemDamage();
    	if( dmg <= 0){
    		information.setLastTick();
    		information.isReloading = false;
    		return;
    	}
    		
    	par1ItemStack.setItemDamage(AmmoManager.consumeAmmo(player, this, par1ItemStack.getItemDamage()));
		
    	information.isReloading = false;
    	information.setLastTick();
    	
		return;
    }
    
    
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		InformationBullet inf = getInformation(par1ItemStack, par2World);
		inf.isShooting = false;
	}
    
	/**
	 * Get the shoot sound path corresponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundShoot(int mode);
	
	/**
	 * Get the gun jamming sound path corresponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundJam(int mode);
	
	/**
	 * Get the reload sound path corresponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundReload(int mode);
	
	/**
	 * Get the shoot time corresponding to the mode.
	 * @param mode
	 * @return shoot time
	 */
	public abstract int getShootTime(int mode);
	
	/**
	 * Set the reload time.
	 * @param reloadTime
	 */
	public final void setReloadTime(int par1){
		reloadTime = par1;
	}
	
	/**
	 * Set the gun jamming time.
	 * @param jamTime
	 */
	public final void setJamTime(int par1){
		jamTime = par1;
	}
	
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	
	@Override
	public InformationBullet getInformation(ItemStack itemStack, World world){	
	   InformationSet set = CBCWeaponInformation.getInformation(itemStack);
	   return set == null ? null : set.getProperBullet(world);
	}
	    
	@Override
	public InformationBullet loadInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer){
		
		InformationBullet inf = getInformation(par1ItemStack, par2EntityPlayer.worldObj);

		if(inf != null)
			return inf;
		
		double uniqueID = Math.random()*65535D;
		CBCMod.wpnInformation.addToList(uniqueID, createInformation(par1ItemStack, par2EntityPlayer));
		
		if(par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;
		
	}

	private InformationSet createInformation(ItemStack is, EntityPlayer player){
		InformationBullet inf = new InformationBullet(is, player);
		InformationBullet inf2 = new InformationBullet(is, player);
		return new InformationSet(inf, inf2);
	}

}
