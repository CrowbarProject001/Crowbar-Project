package cbproject.elements.items.weapons;

import java.util.ArrayList;
import java.util.List;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.AmmoManager;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBulletWeapon;
import cbproject.utils.weapons.InformationSet;
import net.minecraft.command.WrongUsageException;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public abstract class WeaponGeneralBullet extends WeaponGeneral {
	
	public  int reloadTime ; //Time take to reload in tick
	public  int jamTime; //sound playing gap between jams
	public  int shootTime[]; //Shoot time gap correspond to mode in WeaponGeneral
	public  double pushForce[]; //push velocity applied to hit entity
	public  int damage, offset; //damage and offset(offset: 0-100, larger the wider bullet spray)
	public  double upLiftRadius, recoverRadius; //player screen uplift radius in degree
	public  double addVelRadius; //Velocity radius add to the mob hitted;
	
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
	
	public WeaponGeneralBullet(int par1 , int par2ammoID) {
		super(par1, par2ammoID);	
		offset = 0;
		upLiftRadius = 10;
		recoverRadius = 2;
		addVelRadius = 0;
		type = 0;
		this.damage = 0;
	}
	
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	

	public void processRightClick(InformationBulletWeapon information, ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		
		Boolean canUse = information.canUse;
		Boolean isShooting = information.isShooting;
		Boolean isReloading = information.isReloading;
		
		if(!canUse && !isReloading){
			if(par1ItemStack.getItemDamage() < getMaxDamage() -1)
				canUse = true;
			else 
				isReloading = true;
		}
		
		if(canUse){
			isShooting = true;
			if(information.ticksExisted - information.lastTick >= shootTime[mode]){
				this.onBulletWpnShoot(par1ItemStack, par2World, par3EntityPlayer, information);
			}
		}
		information.canUse = canUse;
		information.isShooting = isShooting;
		information.isReloading = isReloading;
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		
	}
	
    public void onBulletWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
    	
    	if(par3Entity instanceof EntityPlayer){
    		
    		ItemStack held = ((EntityPlayer)par3Entity).getHeldItem();
    		if(held == null || !held.equals(par1ItemStack))
    			return;
    		
    	}
    	
		InformationSet inf = getInformation(par1ItemStack);
		if(inf == null){
			return;
		}
		
		if(par2World.isRemote){
			
			InformationBulletWeapon information = inf.getClientAsBullet();
			information.updateTick();
			
			int ticksExisted = information.ticksExisted;
			int lastTick = information.lastTick;
			int recoverTick =  information.recoverTick;

			Boolean isShooting = information.isShooting;
			Boolean isReloading = information.isReloading;
			Boolean canUse = information.canUse;
			Boolean isRecovering = information.isRecovering;
			
			if(isRecovering){
				par3Entity.rotationPitch += recoverRadius;
				recoverTick++;
				if(recoverTick >= (upLiftRadius / recoverRadius))
					isRecovering = false;
				information.recoverTick = recoverTick;
				information.isRecovering = isRecovering;
			}
				
			if(isShooting && canUse && ticksExisted - lastTick >= shootTime[mode]){
				
				this.onBulletWpnShoot(par1ItemStack, par2World, par3Entity, information);
				return;
			}
			
			if(isReloading && !inf.getClientAsBullet().rsp){
				int index = (int) (pathSoundReload.length * Math.random());
				par2World.playSoundAtEntity(par3Entity, pathSoundReload[index] , 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				inf.getClientAsBullet().rsp = true;
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
			
		} else {

			InformationBulletWeapon information = inf.getServerAsBullet();
			information.updateTick();
			int ticksExisted = information.ticksExisted;
			int lastTick = information.lastTick;
			int recoverTick =  information.recoverTick;
			Boolean isShooting = information.isShooting;
			Boolean isReloading = information.isReloading;
			Boolean canUse = information.canUse;
			Boolean isRecovering = information.isRecovering;
			
			if(isRecovering){
				par3Entity.rotationPitch += recoverRadius;
				recoverTick++;
				if(recoverTick >= (upLiftRadius / recoverRadius))
					isRecovering = false;
				information.recoverTick = recoverTick;
				information.isRecovering = isRecovering;
			}
				
			if(isShooting && canUse && ticksExisted - lastTick >= shootTime[mode]){
		    	int index = (int) (pathSoundShoot.length * Math.random());
				this.onBulletWpnShoot(par1ItemStack, par2World, par3Entity, information);
				return;
			}
			
			if(isReloading && !inf.getClientAsBullet().rsp){
				int index = (int) (pathSoundReload.length * Math.random());
				par2World.playSoundAtEntity(par3Entity, pathSoundReload[index] , 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
				inf.getClientAsBullet().rsp = true;
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

	}
		
		
    

    public void onBulletWpnReload(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBulletWeapon information ){

    	if(par3Entity instanceof EntityPlayer){
    		
    		int dmg = par1ItemStack.getItemDamage();
    		int maxDmg = par1ItemStack.getMaxDamage() -1;
    		if( dmg <= 0){
    			information.setLastTick();
    			information.isReloading = false;
    			information.canUse = true;
    			information.rsp = false;
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
    		information.rsp = false;
    	} else { 
    		par1ItemStack.setItemDamage( 0 );
    	}
    	
		return;
    }
    
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBulletWeapon information ){

    	int maxDmg = par1ItemStack.getMaxDamage() -1;
		if( par1ItemStack.getItemDamage() >= maxDmg ){
			
			information.canUse = false;
			information.lastTick = information.ticksExisted;
			return;
		}
		
		int index = (int) (pathSoundShoot.length * Math.random());
		par2World.playSoundAtEntity(par3Entity, pathSoundShoot[index], 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));	
		
    	CBCMod.bulletManager.Shoot( (EntityLiving) par3Entity , par2World, damage ,offset, addVelRadius);
    	//AddVelocity
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
    
    public void onBulletWpnJam(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBulletWeapon information ){
 
    	
    	int maxDmg = par1ItemStack.getMaxDamage();
		if( par1ItemStack.getItemDamage() < maxDmg){
			information.canUse = true;
			return;
		}
		int index = (int) (pathSoundJam.length * Math.random());
		par2World.playSoundAtEntity(par3Entity, pathSoundJam[index], 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));

		information.lastTick = information.ticksExisted;
		
    }
    
    
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		
		InformationSet inf = getInformation(par1ItemStack);
		if( inf == null)
			return;
		inf.getClientAsBullet().isShooting = false;
		inf.getServerAsBullet().isShooting = false;

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
		
		InformationBulletWeapon server = new InformationBulletWeapon(par2EntityPlayer, par1ItemStack);
		InformationBulletWeapon client = new InformationBulletWeapon(par2EntityPlayer, par1ItemStack);
		double uniqueID = Math.random()*65535D;
		
		inf = new InformationSet(client, server, uniqueID);
		
		int id = listItemStack.size();
		listItemStack.add(inf);
		par1ItemStack.getTagCompound().setInteger("weaponID", id);
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		
		return inf;
		
	}

}
