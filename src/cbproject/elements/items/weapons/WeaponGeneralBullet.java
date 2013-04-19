package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.utils.CBCWeaponInformation;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

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
		InformationSet inf = loadInformation(par1ItemStack, par3EntityPlayer);
		if(inf == null)
			return null;
		
		InformationBullet information = inf.getProperBullet(par2World);
		Boolean canUse = (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() -1 > 0);
		int mode = information.mode;
		
		if(canUse){
			if(information.getDeltaTick() >= getShootTime(mode)){
				this.onBulletWpnShoot(par1ItemStack, par2World, par3EntityPlayer, information);
			}
			information.isShooting = true;
		} else  {
			information.isReloading = true;
			par2World.playSoundAtEntity(par3EntityPlayer, getSoundReload(mode), 0.5F, 1.0F);
		}
		
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		par3EntityPlayer.setEating(false);
		return information;
	}
	
    public void onBulletWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
    	   	
    	InformationBullet information = (InformationBullet) super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    	if(information == null){
    		InformationSet inf = loadInformation(par1ItemStack, (EntityPlayer) par3Entity);	
    		if(inf == null)
    			return;
    		
    		information = inf.getProperBullet(par2World);
    		information.isShooting = false;
    		information.isReloading = false;
    		return;
    	}
    	information.updateTick();
    	
		if(doesShoot(information, par1ItemStack))
			this.onBulletWpnShoot(par1ItemStack, par2World, (EntityPlayer) par3Entity, information);
		
		if(doesReload(information, par1ItemStack))
			this.onBulletWpnReload(par1ItemStack, par2World, par3Entity, information);
		
		if(doesReload(information, par1ItemStack))
			this.onBulletWpnJam(par1ItemStack, par2World, par3Entity, information);

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
    				par1ItemStack.damageItem( 1 , null);
    		}
    	}
		return;	
    }
    
    public void onBulletWpnJam(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){ 	 
		par2World.playSoundAtEntity(par3Entity, getSoundJam(information.mode), 0.5F, 1.0F);
		information.setLastTick();
    }

    public void onBulletWpnReload(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){

    	EntityPlayer ent = (EntityPlayer) par3Entity;
    	
    	int dmg = par1ItemStack.getItemDamage();
    	if( dmg <= 0){
    		information.setLastTick();
    		information.isReloading = false;
    		return;
    	}
    		
    	information.ammoManager.setAmmoInformation(ent);
    	par1ItemStack.setItemDamage(information.ammoManager.consumeAmmo(dmg));
		
    	information.isReloading = false;
    	information.setLastTick();
    	
		return;
    }
    
    
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		InformationSet inf = getInformation(par1ItemStack);
		inf.getProperBullet(par2World).isShooting = false;
	}
    
	/**
	 * Get the shoot sound path corressponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundShoot(int mode);
	
	/**
	 * Get the gun jamming sound path corressponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundJam(int mode);
	
	/**
	 * Get the reload sound path corressponding to the mode.
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundReload(int mode);
	
	/**
	 * Get the shoot time corressponding to the mode.
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
	public InformationSet getInformation(ItemStack itemStack){	
	    return CBCWeaponInformation.getInformation(itemStack);
	}
	    
	@Override
	public InformationSet loadInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer){
		
		InformationSet inf = getInformation(par1ItemStack);
		if(inf != null)
			return inf;
		
		double uniqueID = par1ItemStack.stackTagCompound.getDouble("uniqueID");
		
		if(!par2EntityPlayer.worldObj.isRemote){
			/*
			if(uniqueID != 0){
				inf = createInformation(par1ItemStack, par2EntityPlayer);
				System.out.println(par2EntityPlayer.worldObj.isRemote + " id :" + uniqueID);
				CBCMod.wpnInformation.addToList(uniqueID, inf);
			}*/
			return inf;
		}
		
		if(uniqueID == 0)
			uniqueID = Math.random()*65535D;
		inf = createInformation(par1ItemStack, par2EntityPlayer);
		System.out.println(par2EntityPlayer.worldObj.isRemote + " id :" + uniqueID);
		CBCMod.wpnInformation.addToList(uniqueID, inf);
		
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;
		
	}
	
	private InformationSet createInformation(ItemStack is, EntityPlayer player){
		InformationBullet server = new InformationBullet(player, is);
		InformationBullet client = new InformationBullet(player, is);
		return new InformationSet(client, server);
	}

}
