package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBulletWeapon;

public class Weapon_9mmAR extends WeaponGeneralBullet {
	
	public Weapon_9mmAR(int par1) {
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID);
		setIconCoord(3,2);
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setCreativeTab(CBCMod.cct);
		setItemName("weapon_9mmar");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(3,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(51); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		shootTime = new int [1];
		shootTime[0] = 3;
		reloadTime = 60;
		jamTime = 10;
		
		pathSoundShoot = "cbc.weapons.hksa";
		pathSoundJam = "cbc.weapons.gunjam_a";
		pathSoundReload = "cbc.weapons.nmmarr";
		
		mode = 0; //子弹+自动
	}

	@Override
    public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
    }

	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		//EVENT post
		//fail:delete entity,setDead
		int id;
		int maxDmg = par1ItemStack.getMaxDamage() -1;
		
		if(par1ItemStack.getTagCompound() == null){
			id = 0;
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		} else {
			id = par1ItemStack.getTagCompound().getInteger("weaponID");
		}
		
		InformationBulletWeapon information = getBulletWpnInformation(par1ItemStack);
		if( null == information ){
			id = addBulletWpnInformation(information, par1ItemStack, par3EntityPlayer);
			information = getBulletWpnInformation(id);
		}
		
		Boolean canUse = information.canUse;
		Boolean isShooting = information.isShooting;
		Boolean isReloading = information.isReloading;
		
		if(!canUse && !isReloading){
			if(par1ItemStack.getItemDamage() < maxDmg)
				canUse = true;
			else 
				isReloading = true;
		}
		
		if(canUse)
			isShooting = true;
		
		information.canUse = canUse;
		information.isShooting = isShooting;
		information.isReloading = isReloading;
		par3EntityPlayer.setItemInUse(par1ItemStack, getMaxItemUseDuration(par1ItemStack) );
		
		return par1ItemStack;
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		//if event calcelled setdead
		int id = par1ItemStack.getTagCompound().getInteger("weaponID");
		if(id == 0 || id > listItemStack.size())
			return;
		id--;
		InformationBulletWeapon information = getBulletWpnInformation(id);
		if( information == null)
			return;
		information.isShooting = false;
		System.out.println("Setted dead");

	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200; //10s
    }

}
