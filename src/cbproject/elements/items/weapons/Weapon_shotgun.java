package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBulletWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Weapon_shotgun extends WeaponGeneralBullet {

	public Weapon_shotgun(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemBullet_Shotgun.itemID);
		
		setItemName("weapon_shotgun");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(5,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(9); 
		setNoRepair(); //不可修补
		
		shootTime = new int [2];
		shootTime[0] = 10;
		shootTime [1]= 20;
		jamTime = 10;
		reloadTime = 7;
		this.damage = 3;
		this.offset = 0;
		pathSoundShoot = new String[1];
		pathSoundJam = new String[1];
		pathSoundReload = new String[3];
		
		pathSoundShoot[0] = "cbc.weapons.sbarrela";
		pathSoundJam[0] = "cbc.weapons.scocka";
		pathSoundReload[0] =  "cbc.weapons.reloada";
		pathSoundReload[1] = "cbc.weapons.reloadb";
		pathSoundReload[2] = "cbc.weapons.reloadc";
		
		
		mode = 0; //低速
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {

		InformationBulletWeapon information = super.loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick(information, par1ItemStack, par2World, par3EntityPlayer);

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

    		if( dmg > 0 ){
    			information.ammoManager.clearAmmo( (EntityPlayer)par3Entity );
				par1ItemStack.setItemDamage( par1ItemStack.getItemDamage() - cap);
    		} else {
    			information.ammoManager.consumeAmmo( dmg );
    			cap -= dmg;
    			par1ItemStack.setItemDamage( 0 );
    		}
		
    		if( par1ItemStack.getItemDamage() >=maxDmg )
    			information.canUse = false;
		
    		information.isReloading = false;
    		information.lastTick = information.ticksExisted;
    		information.rsp = false;
    	} else { 
    		par1ItemStack.setItemDamage( 0 );
    	}
    	
		return;
    }
    
	@Override
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBulletWeapon information ){

    	int maxDmg = par1ItemStack.getMaxDamage() -1;
		if( par1ItemStack.getItemDamage() >= maxDmg ){
			
			information.canUse = false;
			information.lastTick = information.ticksExisted;
			return;
		}
		
    	CBCMod.bulletManager.Shoot( (EntityLiving) par3Entity , par2World, damage ,offset);
    	//AddVelocity
    	information.setLastTick();
    	int index = (int) (pathSoundShoot.length * Math.random());
    	System.out.println("index: " + index);
    	serverReference.playSoundAtEntity(par3Entity, pathSoundShoot[index], 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));	
    	
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
