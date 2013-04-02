package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityRPG;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_RPG extends WeaponGeneralBullet {

	public Weapon_RPG(int par1) {
		super(par1,CBCMod.cbcItems.itemAmmo_rpg.itemID, 1);
		
		setItemName("weapon_rpg");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(7,2);
		setCreativeTab(CBCMod.cct);
		setMaxDamage(2);
		
		String pshoot[] = {""};
		String reload[] = {""};
		String jam[] = {""};
		int shoot[] = { 0, 0 }, dmg[] = {30, 30}, off[] = { 0 , 0};
		double push[] = { 2, 2};
		
		setPathShoot(pshoot);
		setPathReload(reload);
		setPathJam(jam);
		setJamTime(0);
		
		setShootTime(shoot);
		setDamage(dmg);
		setOffset(off);
		setPushForce(push);
		
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
		InformationBullet information = (inf == null? null : inf.getProperBullet(par2World));
		if(information == null)
			return par1ItemStack;
		
		Boolean canUse = (par1ItemStack.getItemDamage() == 0), 
				isReloading = information.isReloading;
		int mode = information.mode;
		
		if(!canUse){
			
			isReloading = true;
			int index = (int) (pathSoundReload.length * Math.random());
			par2World.playSoundAtEntity(par3EntityPlayer, pathSoundReload[index] , 0.5F, 1.0F);
		} else{
				this.onBulletWpnShoot(par1ItemStack, par2World, par3EntityPlayer, information);
		}

		information.isReloading = isReloading;
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		return par1ItemStack;
		
    }
	
	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationBullet information ){
		par2World.spawnEntityInWorld(new EntityRPG(par2World, (EntityLiving) par3Entity));
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
        return 400; //20s
    }
		 

}
