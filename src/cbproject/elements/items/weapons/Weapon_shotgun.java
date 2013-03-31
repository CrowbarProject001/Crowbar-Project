package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Weapon_shotgun extends WeaponGeneralBullet {

	public Weapon_shotgun(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemBullet_Shotgun.itemID, 2);
		
		setItemName("weapon_shotgun");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(5,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(9); 
		setNoRepair(); //不可修补
		
		String[] shoot  = { "cbc.weapons.sbarrela", "cbc.weapons.sbarrela"};
		String[] reload = { "cbc.weapons.reloada", "cbc.weapons.reloadb", "cbc.weapons.reloadc" };
		String[] jam = { "cbc.weapons.scocka" , "cbc.weapons.scocka"};
		int shootTime[] = {20, 35}, dmg[] = { 7, 7}, off[] = { 5, 10};
		double push[] ={ 1.2, 2};
		
		setPathShoot(shoot);
		setPathJam(jam);
		setPathReload(reload);
		
		setShootTime(shootTime);
		setReloadTime(7);
		setJamTime(10);
		
		setPushForce(push);
		setDamage(dmg);
		setOffset(off);
		
		this.setLiftProps(30, 5);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		
		InformationSet inf = loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick( inf.getProperBullet(par2World), par1ItemStack, par2World, par3EntityPlayer);

		return par1ItemStack;
		
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 400; //20s
    }

	@Override
    public void onBulletWpnReload(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){
    	int var1 = 10; //上弹时间

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
    		int ticksPassed = information.ticksExisted - information.lastTick;
    		if( ticksPassed >= var1){
    			if( cap > 0 ){
        			information.ammoManager.consumeAmmo(1, (EntityPlayer)par3Entity);
    				par1ItemStack.setItemDamage( par1ItemStack.getItemDamage() - 1);
    		    	int index = (int) (pathSoundReload.length * Math.random());
    		    	par2World.playSoundAtEntity(par3Entity, pathSoundReload[index], 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));	
        		} else 
        			information.isReloading = false;
    			
        		information.lastTick = information.ticksExisted;
    		}
    		
    	} else par1ItemStack.setItemDamage( 0 );
    	
    	
		return;
    }
    
	@Override
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){

    	int maxDmg = par1ItemStack.getMaxDamage() -1;
		if( par1ItemStack.getItemDamage() >= maxDmg ){
			information.lastTick = information.ticksExisted;
			return;
		}
		
		int mode = information.mode;
		for(int i=0; i<8; i++)
			BulletManager.Shoot( (EntityLiving) par3Entity , par2World, damage[mode] , offset[mode], pushForce[mode]);

    	information.setLastTick();
    	int index = (int) (pathSoundShoot.length * Math.random());
    	par2World.playSoundAtEntity(par3Entity, pathSoundShoot[index], 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));	
    	
    	if(par3Entity instanceof EntityPlayer){
    		if(!information.isRecovering)
    			information.originPitch = par3Entity.rotationPitch;
    		par3Entity.rotationPitch -= upLiftRadius;
    		information.isRecovering = true;
    		information.recoverTick = 0;
    		if(!((EntityPlayer)par3Entity).capabilities.isCreativeMode){
    				par1ItemStack.damageItem( 1 , null);
    		}
    	}

		
		return;
		
    }

}
