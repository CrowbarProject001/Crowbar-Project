package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityCrossbowArrow;
import cbproject.elements.entities.weapons.EntityRocket;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_crossbow extends WeaponGeneralBullet {

	public Weapon_crossbow(int par1) {
		super(par1 , CBCMod.cbcItems.itemAmmo_bow.itemID, 2);
		
		setItemName("weapon_crossbow");
		
		//setTextureFile(ClientProxy.ITEMS_MOTION1_PATH);
		setTextureFile(ClientProxy.ITEMS_MOTION1_PATH);
		//setIconCoord(0, 0);
		setIconIndex(0);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(6);
		setNoRepair(); //不可修补
		
		String[] shoot  = { "cbc.weapons.xbow_fire"};
		String[] reload = { "cbc.weapons.xbow_reload" };
		String[] jam = { ""};
		int shootTime[] = {20, 20}, dmg[] = { 7, 7}, off[] = { 2, 2};
		double push[] = {2, 2};
		
		setPathShoot(shoot);
		setPathJam(jam);
		setPathReload(reload);
		
		setShootTime(shootTime);
		setReloadTime(7);
		setJamTime(0);
		
		setPushForce(push);
		setDamage(dmg);
		setOffset(off);
		
		setLiftProps(10, 5);
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		
		InformationSet inf = super.loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick(( par2World.isRemote? inf.getClientAsBullet() : inf.getServerAsBullet() ), 
				par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
    }
	
	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){
		
		Boolean canUse = (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() -1 > 0);
		
		if(!canUse){
			information.isReloading = true;
			return;
		}

		par2World.playSoundAtEntity(par3Entity, pathSoundShoot[0], 0.5F, 1.0F);	
		switch(information.mode){
		case 0:
			BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");
			break;
		case 1:
			par2World.spawnEntityInWorld(new EntityCrossbowArrow(par2World, (EntityLiving) par3Entity));
			break;
		default:
			break;
		}
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
		
	}
	
	@Override
	  public void onBulletWpnJam(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){
		
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
		
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200; //10s
    }

	@Override
	public String getSoundShoot(int mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSoundJam(int mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getSoundReload(int mode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public int getShootTime(int mode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public double getPushForce(int mode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getDamage(int mode) {
		// TODO Auto-generated method stub
		return 0;
	}

	@Override
	public int getOffset(int mode) {
		// TODO Auto-generated method stub
		return 0;
	}
}
