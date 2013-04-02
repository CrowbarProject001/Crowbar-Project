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
	public  int shootTime[]; //Shoot time gap correspond to mode in WeaponGeneral
	public String pathSoundShoot[],pathSoundJam[],pathSoundReload[];
	
	/*Local Variables in ItemStack InformationBulletWeapon
	 * 
	 * canUse(Boolean) : To flag whether the gun is available for shoot.
	 * isShooting(Boolean) : To flag whether the gun is shooting or not.
	 * isReloading(Boolean) : To flag whether the gun is reloading or not.
	 * isAlive(Boolean) : To flag whether the itemEntity exists or not.
	 * @See:cbproject.utils.weapons.InformationiBulletWeapon.java
	 * 
	 */
	
	public WeaponGeneralBullet(int par1 , int par2ammoID, int par3maxModes) {
		
		super(par1, par2ammoID, par3maxModes);	
		maxModes = par3maxModes;
		
		upLiftRadius = 10;
		recoverRadius = 2;
		type = 0;
		
	}
	
	public final void setShootTime(int[] par1){
		shootTime = par1;
	}

	
	public final void setPathShoot(String[] par1){
		pathSoundShoot = par1;
	}
	
	public final void setPathJam(String[] par1){
		pathSoundJam = par1;
	}
	
	public final void setPathReload(String[] par1){
		pathSoundReload = par1;
	}
	
	public final void setReloadTime(int par1){
		reloadTime = par1;
	}
	
	public final void setJamTime(int par1){
		jamTime = par1;
	}
	
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	

	public void processRightClick(InformationBullet information, ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
	
		Boolean canUse = (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() -1 > 0);
		Boolean isShooting = information.isShooting;
		Boolean isReloading = information.isReloading;
		int mode = information.mode;
		
		if(!canUse && !isReloading){
			if(par1ItemStack.getItemDamage() < getMaxDamage() -1)
				canUse = true;
			else {
				isReloading = true;
				int index = (int) (pathSoundReload.length * Math.random());
				par2World.playSoundAtEntity(par3EntityPlayer, pathSoundReload[index] , 0.5F, 1.0F);
			}
		}
		
		if(canUse){
			isShooting = true;
			if(information.ticksExisted - information.lastTick >= shootTime[mode]){
				this.onBulletWpnShoot(par1ItemStack, par2World, par3EntityPlayer, information);
			}
		}
		information.isShooting = isShooting;
		information.isReloading = isReloading;
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		
	}
	
    public void onBulletWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
    	
    	super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    	if(par3Entity instanceof EntityPlayer){
    		
    		ItemStack held = ((EntityPlayer)par3Entity).getHeldItem();
    		if(held == null || !held.equals(par1ItemStack))
    			return;
    		
    	}
    	
		InformationSet inf = getInformation(par1ItemStack);
		if(inf == null){
			return;
		}
		
		InformationBullet information = inf.getProperBullet(par2World);
		information.updateTick();
		
		int ticksExisted = information.ticksExisted;
		int lastTick = information.lastTick;
		int recoverTick =  information.recoverTick;
		int mode = information.mode;

		Boolean isShooting = information.isShooting;
		Boolean isReloading = information.isReloading;
		Boolean canUse = (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() -1 > 0);
			
		if(isShooting && canUse && ticksExisted - lastTick >= shootTime[mode]){
			this.onBulletWpnShoot(par1ItemStack, par2World, par3Entity, information);
			return;
		}

		if( isReloading && ticksExisted - lastTick >= reloadTime ){
			this.onBulletWpnReload(par1ItemStack, par2World, par3Entity, information);
			return;
		}
		
		if( isShooting && !canUse && ticksExisted - lastTick >= jamTime ){
			this.onBulletWpnJam(par1ItemStack, par2World, par3Entity, information);
			return;
		}

	}
		
		
    

    public void onBulletWpnReload(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){

    	if(par3Entity instanceof EntityPlayer){
    		
    		int dmg = par1ItemStack.getItemDamage();
    		int maxDmg = par1ItemStack.getMaxDamage() -1;
    		if( dmg <= 0){
    			information.setLastTick();
    			information.isReloading = false;
    			return;
    		}
    		
    		information.ammoManager.setAmmoInformation((EntityPlayer)par3Entity);
    		int cap = information.ammoManager.ammoCapacity;

    		if( dmg >= cap ){
    			information.ammoManager.clearAmmo( (EntityPlayer)par3Entity );
				par1ItemStack.setItemDamage( par1ItemStack.getItemDamage() - cap);
    		} else {
    			if( information.ammoManager.consumeAmmo(dmg, (EntityPlayer)par3Entity) )
    				par1ItemStack.setItemDamage( 0 );
    		}
		
    		information.isReloading = false;
    		information.lastTick = information.ticksExisted;

    	} else { 
    		par1ItemStack.setItemDamage( 0 );
    	}
    	
		return;
    }
    
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){
		
		int index = (int) (pathSoundShoot.length * Math.random());
		par2World.playSoundAtEntity(par3Entity, pathSoundShoot[index], 0.5F, 1.0F);	
		
		int mode = information.mode;
		BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");

    	information.setLastTick();

    	if(par3Entity instanceof EntityPlayer){
    		if(!information.isRecovering)
    			information.originPitch = par3Entity.rotationPitch;
    		par3Entity.rotationPitch -= upLiftRadius;
    		information.isRecovering = true;
    		information.recoverTick = 0;
    		if(!((EntityPlayer)par3Entity).capabilities.isCreativeMode ){
    				par1ItemStack.damageItem( 1 , null);
    		}
    	}

		
		return;
		
    }
    
    public void onBulletWpnJam(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){
 
    	if(jamTime == 0)
			return;
    	
    	int maxDmg = par1ItemStack.getMaxDamage();
		if( par1ItemStack.getItemDamage() < maxDmg){
			return;
		}
		int index = (int) (pathSoundJam.length * Math.random());
		par2World.playSoundAtEntity(par3Entity, pathSoundJam[index], 0.5F, 1.0F);

		information.setLastTick();
		
    }
    
    
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		
		InformationSet inf = getInformation(par1ItemStack);
		if( inf == null)
			return;
		inf.getProperBullet(par2World).isShooting = false;

	}
	
	@Override
	public InformationSet getInformation(ItemStack itemStack){
		  	if(itemStack.getTagCompound() == null)
		  		itemStack.stackTagCompound = new NBTTagCompound();
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
		par1ItemStack.getTagCompound().setInteger("weaponID", id);
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		
		return inf;
		
	}

}
