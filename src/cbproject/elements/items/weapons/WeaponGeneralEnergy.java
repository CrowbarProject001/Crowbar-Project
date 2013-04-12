package cbproject.elements.items.weapons;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationEnergy;
import cbproject.utils.weapons.InformationSet;

public abstract  class WeaponGeneralEnergy extends WeaponGeneral {

	public int jamTime;

	public WeaponGeneralEnergy(int par1, int par2AmmoID, int par3MaxModes) {
		super(par1, par2AmmoID, par3MaxModes);
		type = 1;
		// TODO Auto-generated constructor stub
	}

	public abstract int getShootTime(int mode);
	public abstract String getSoundShoot(int mode);
	public abstract String getSoundJam(int mode);
	public abstract int getDamage(int mode);

	public final void setJamTime(int par1){
		jamTime = par1;
	}
	
	
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	
	public void processRightClick(InformationEnergy information, ItemStack par1ItemStack, 
			World par2World, EntityPlayer par3EntityPlayer){
		
		Boolean canUse = (information.ammoManager.getAmmoCapacity() > 0 );
		Boolean isShooting = information.isShooting;
		int mode = information.mode;
		information.ammoManager.setAmmoInformation(par3EntityPlayer);
		if(canUse && mode == 0){
			isShooting = true;
			if( getShootTime(mode) != 0 && information.ticksExisted - information.lastTick >= getShootTime(mode)){
				onEnergyWpnShoot(par1ItemStack, par2World, par3EntityPlayer, information);
			}
		}
		
		information.isShooting = isShooting;
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		
	}
	
	public InformationEnergy onEnergyWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    	if(par3Entity instanceof EntityPlayer){
    		
    		ItemStack held = ((EntityPlayer)par3Entity).getHeldItem();
    		if(held == null || !held.equals(par1ItemStack))
    			return null;
    		
    	}
    	
		InformationSet inf = getInformation(par1ItemStack, par2World);
		if(inf == null){
			return null;
		}
		
		InformationEnergy information = inf.getProperEnergy(par2World);
		information.updateTick();
		
		int ticksExisted = information.ticksExisted;
		int lastTick = information.lastTick;
		int mode = information.mode;

		Boolean isShooting = information.isShooting;

		Boolean canUse = information.ammoManager.getAmmoCapacity() > 0;
			
		if(getShootTime(mode) > 0 && isShooting && canUse && ticksExisted - lastTick >= getShootTime(mode)){
			this.onEnergyWpnShoot(par1ItemStack, par2World, par3Entity, information);
			return information;
		}

		
		if( isShooting && !canUse && ticksExisted - lastTick >= jamTime ){
			this.onEnergyWpnJam(par1ItemStack, par2World, par3Entity, information);
			return information;
		}
		return information;
		
	}
	
	//正常的、子弹式的射击，仅用于高斯枪第一模式和Egon
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationEnergy information ){
		
		int mode = information.mode;
		
		par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));	
		BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");
    	information.setLastTick();

    	if(par3Entity instanceof EntityPlayer){
    		doRecover(information, (EntityPlayer) par3Entity);
    		if(!((EntityPlayer)par3Entity).capabilities.isCreativeMode ){
    				information.ammoManager.consumeAmmo(1);
    		}
    	}
    	
		return;
	}
	
	public void onEnergyWpnJam(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationEnergy information ){
		
    	int maxDmg = par1ItemStack.getMaxDamage();
    	int mode = information.mode;
		if( par1ItemStack.getItemDamage() < maxDmg){
			return;
		}
		
		par2World.playSoundAtEntity(par3Entity, getSoundJam(mode), 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		information.setLastTick();
		
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		
		InformationSet inf = getInformation(par1ItemStack, par2World);
		inf.getProperEnergy(par2World).isShooting = false;

	}

	@Override
	public InformationSet loadInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer){
		
		InformationSet inf = getInformation(par1ItemStack, par2EntityPlayer.worldObj);
		if(inf != null)
			return inf;
		
		InformationEnergy server = new InformationEnergy(par1ItemStack, par2EntityPlayer);
		InformationEnergy client = new InformationEnergy(par1ItemStack, par2EntityPlayer);
		
		double uniqueID = Math.random()*65535D;
		inf = new InformationSet(client, server, uniqueID);
		int id = CBCMod.wpnInformation.addToList(inf);
		
		if(par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setInteger("weaponID", id);
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;
		
	}

	@Override
	public InformationSet getInformation(ItemStack itemStack, World world) {
		InformationSet inf = CBCMod.wpnInformation.getInformation(itemStack, world);   	
	    return inf;
	}


}
