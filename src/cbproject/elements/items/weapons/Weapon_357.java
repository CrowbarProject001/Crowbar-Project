package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBulletWeapon;
import cbproject.utils.weapons.InformationSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Weapon_357 extends WeaponGeneralBullet {

	public Weapon_357(int par1) {
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID);
		
		setItemName("weapon_357");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(4,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(7); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		shootTime = new int [1];
		pathSoundShoot = new String[2];
		pathSoundJam = new String[1];
		pathSoundReload = new String[1];
		pushForce = new double[1];
		
		shootTime[0] = 20; //1.5s
		jamTime = 20;
		reloadTime = 100; //3s
		
		this.damage = 7;
		this.offset = 3;
		
		this.upLiftRadius = 50;
		this.recoverRadius = 7;
		pushForce[0] = 1;
		
		pathSoundShoot[0] = "cbc.weapons.pyt_shota"; pathSoundShoot[1] = "cbc.weapons.pyt_shotb";
		pathSoundJam[0] = "cbc.weapons.pyt_cocka";
		pathSoundReload[0] =  "cbc.weapons.pyt_reloada";
		
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
		//EVENT post
		//fail:delete entity,setDead
		InformationSet inf = super.loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick(( par2World.isRemote? inf.getClientAsBullet() : inf.getServerAsBullet() ), 
				par1ItemStack, par2World, par3EntityPlayer);

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
        return 200; //10s
    }

}
