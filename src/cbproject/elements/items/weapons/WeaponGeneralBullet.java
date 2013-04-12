package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;

public abstract class WeaponGeneralBullet extends WeaponGeneral {
	
	public  int reloadTime ; //Time take to reload in tick
	public  int jamTime; //sound playing gap between jams
	
	public WeaponGeneralBullet(int par1 , int par2ammoID, int par3maxModes) {
		
		super(par1, par2ammoID, par3maxModes);	
		maxModes = par3maxModes;
		
		upLiftRadius = 10;
		recoverRadius = 2;
		type = 0;
		
	}
	
	
	public abstract String getSoundShoot(int mode);
	public abstract String getSoundJam(int mode);
	public abstract String getSoundReload(int mode);
	public abstract int getShootTime(int mode);
	
	public final void setReloadTime(int par1){
		reloadTime = par1;
	}
	
	public final void setJamTime(int par1){
		jamTime = par1;
	}
	
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	

	public void processRightClick(InformationBullet information, ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
	
		Boolean canUse = (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() -1 > 0);
		int mode = information.mode;
		
		if(canUse){
			if(information.getDeltaTick() >= getShootTime(mode)){
				this.onBulletWpnShoot(par1ItemStack, par2World, par3EntityPlayer, information);
			}
			information.isShooting = true;
		} else information.isReloading = true;
		
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		
	}
	
    public void onBulletWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
    	   	
    	InformationBullet information = (InformationBullet) super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    	if(information == null)
    		return;
    	
		information.updateTick();

		if(doesShoot(information, par1ItemStack))
			this.onBulletWpnShoot(par1ItemStack, par2World, (EntityPlayer) par3Entity, information);
		
		if(doesReload(information, par1ItemStack))
			this.onBulletWpnReload(par1ItemStack, par2World, par3Entity, information);
		
		if(doesReload(information, par1ItemStack))
			this.onBulletWpnJam(par1ItemStack, par2World, par3Entity, information);

	}
		
	public Boolean doesShoot(InformationBullet inf, ItemStack itemStack){
		Boolean canUse;
		canUse = (itemStack.getMaxDamage() - itemStack.getItemDamage() -1 > 0);
		return (getShootTime(inf.mode) != 0 && inf.isShooting && canUse && inf.getDeltaTick() >= getShootTime(inf.mode) );
	}
	
	public Boolean doesReload(InformationBullet inf, ItemStack itemStack){
		return ( inf.isReloading && inf.getDeltaTick() >= this.reloadTime);
	}
	
	public Boolean doesJam(InformationBullet inf, ItemStack itemStack){
		Boolean canUse;
		canUse = (itemStack.getMaxDamage() - itemStack.getItemDamage() -1 > 0);
		return ( jamTime != 0 && inf.isShooting && !canUse );
	}

    public void onBulletWpnReload(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){

    	EntityPlayer ent = (EntityPlayer) par3Entity;
    	
    	int dmg = par1ItemStack.getItemDamage();
    	int maxDmg = par1ItemStack.getMaxDamage() -1;
    	if( dmg <= 0){
    		information.setLastTick();
    		information.isReloading = false;
    		return;
    	}
    		
    	information.ammoManager.setAmmoInformation(ent);
    	int cap = information.ammoManager.ammoCapacity;
    	if( dmg >= cap ){
    		information.ammoManager.clearAmmo( (EntityPlayer)par3Entity );
			par1ItemStack.setItemDamage( par1ItemStack.getItemDamage() - cap);
    	} else {
    		if( information.ammoManager.consumeAmmo(dmg));
    			par1ItemStack.setItemDamage( 0 );
    	}
		
    	information.isReloading = false;
    	information.setLastTick();
    	
		return;
    }
    
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){
		
		int mode = information.mode;
		information.setLastTick();
		
		par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 1.0F);	
		BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");
    	if(par3Entity instanceof EntityPlayer){
    		doRecover(information, par3Entity);
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
    
    
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		
		InformationSet inf = getInformation(par1ItemStack);
		inf.getProperBullet(par2World).isShooting = false;

	}
	
	@Override
	public InformationSet getInformation(ItemStack itemStack){
		
		  	if(itemStack.getTagCompound() == null)
		  		return null;
		  	
	    	int id = itemStack.getTagCompound().getInteger("weaponID");
	    	double uniqueID = itemStack.getTagCompound().getDouble("uniqueID");
	    	
	    	if(id == 0 || id >= listItemStack.size())
	    		return null;
	    	
	    	InformationSet inf = (InformationSet) listItemStack.get(id);
	    	
	    	if(inf.signID != uniqueID)
	    		return null;
	    	
	    	return inf;
	    	
	}
	    
	@Override
	public InformationSet loadInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer){
		
		InformationSet inf = getInformation(par1ItemStack);
		if(inf != null)
			return inf;
		
		InformationBullet server = new InformationBullet(par2EntityPlayer, par1ItemStack);
		InformationBullet client = new InformationBullet(par2EntityPlayer, par1ItemStack);
		double uniqueID = Math.random()*65535D;
		
		inf = new InformationSet(client, server, uniqueID);
		
		int id = listItemStack.size();
		listItemStack.add(inf);
		if(par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		
		par1ItemStack.getTagCompound().setInteger("weaponID", id);
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		
		return inf;
		
	}

}
