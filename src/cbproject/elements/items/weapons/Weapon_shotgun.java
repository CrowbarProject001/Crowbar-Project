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
		
		int shootTime[] = {20, 35}, dmg[] = { 7, 7}, off[] = { 5, 10};
		double push[] ={ 1.2, 2};

		setReloadTime(7);
		setJamTime(10);
		setLiftProps(30, 5);
		
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
    	int mode = information.mode;
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
        			information.ammoManager.consumeAmmo(1);
    				par1ItemStack.setItemDamage( par1ItemStack.getItemDamage() - 1);
    		    	par2World.playSoundAtEntity(par3Entity, getSoundReload(mode), 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));	
        		} else 
        			information.isReloading = false;
    			
        		information.lastTick = information.ticksExisted;
    		}
    		
    	} else par1ItemStack.setItemDamage( 0 );
    	
    	
		return;
    }
    
	@Override
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){

    	int maxDmg = par1ItemStack.getMaxDamage() -1;
		if( par1ItemStack.getItemDamage() >= maxDmg ){
			information.lastTick = information.ticksExisted;
			return;
		}
		
		int mode = information.mode;
		for(int i=0; i<8; i++)
			BulletManager.Shoot( par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");

    	information.setLastTick();
    	par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));	
    	
    	if(par3Entity instanceof EntityPlayer){
    		
    		doRecover(information, par3Entity);
    		if(!((EntityPlayer)par3Entity).capabilities.isCreativeMode){
    				par1ItemStack.damageItem( 1 , null);
    		}
    		
    	}

		return;
		
    }

	@Override
	public String getSoundShoot(int mode) {
		return "cbc.weapons.sbarrela" ;
	}

	@Override
	public String getSoundJam(int mode) {
		// TODO Auto-generated method stub
		return "cbc.weapons.scocka";
	}

	@Override
	public String getSoundReload(int mode) {
		
		int index = (int) (3 * Math.random());
		if(index == 0)
			return  "cbc.weapons.reloada";
		if(index == 1)
			return  "cbc.weapons.reloadb";
		return  "cbc.weapons.reloadc";
					
	}

	@Override
	public int getShootTime(int mode) {
		return mode == 0? 18: 35;
	}

	@Override
	public double getPushForce(int mode) {
		return mode == 0 ? 1.2 : 2;
	}

	@Override
	public int getDamage(int mode) {
		return 7;
	}

	@Override
	public int getOffset(int mode) {
		// TODO Auto-generated method stub
		return (mode == 0) ? 5 : 10;
	}

}
