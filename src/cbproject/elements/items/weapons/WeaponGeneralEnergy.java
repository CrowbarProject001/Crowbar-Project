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

public abstract class WeaponGeneralEnergy extends WeaponGeneral {

	public String pathSoundShoot[], pathSoundSpecial[], pathSoundJam[];
	public int jamTime, shootTime[], damage[], 
		offset[];
	
	
	public WeaponGeneralEnergy(int par1, int par2AmmoID, int par3MaxModes) {
		super(par1, par2AmmoID, par3MaxModes);
		type = 1;
		// TODO Auto-generated constructor stub
	}

	public final void setShootTime(int... par1){
		shootTime = par1;
	}
	
	public final void setPathShoot(String... par1){
		pathSoundShoot = par1;
	}
	
	public final void setPathJam(String... par1){
		pathSoundJam = par1;
	}
	
	public final void setPathSpecial(String... par1){
		pathSoundSpecial = par1;
	}
	
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
			if( shootTime[mode] != 0 && information.ticksExisted - information.lastTick >= shootTime[mode]){
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
    	
		InformationSet inf = getInformation(par1ItemStack);
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
			
		if(shootTime[mode] > 0 && isShooting && canUse && ticksExisted - lastTick >= shootTime[mode]){
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
		
		par2World.playSoundAtEntity(par3Entity, pathSoundShoot[mode], 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));	
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
		if( par1ItemStack.getItemDamage() < maxDmg){
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
		inf.getProperEnergy(par2World).isShooting = false;

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
		
		InformationEnergy server = new InformationEnergy(par1ItemStack, par2EntityPlayer);
		InformationEnergy client = new InformationEnergy(par1ItemStack, par2EntityPlayer);
		double uniqueID = Math.random()*65535D;
		
		inf = new InformationSet(client, server, uniqueID);
		
		int id = listItemStack.size();
		listItemStack.add(inf);
		par1ItemStack.getTagCompound().setInteger("weaponID", id);
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		
		return inf;
		
	}


}
