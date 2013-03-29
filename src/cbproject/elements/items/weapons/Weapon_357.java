package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class Weapon_357 extends WeaponGeneralBullet {

	public Weapon_357(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID, 1);

		setItemName("weapon_357");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(4,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(7); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		String[] shoot  = { "cbc.weapons.pyt_shota", "cbc.weapons.pyt_shotb"};
		String[] reload = { "cbc.weapons.pyt_reloada" };
		String[] jam = { "cbc.weapons.pyt_cock"};
		int shootTime[] = {20}, dmg[] = {7}, off[] = {2};
		double push[] = { 3 };
		
		setPathShoot(shoot);
		setPathJam(jam);
		setPathReload(reload);
		
		setShootTime(shootTime);
		setReloadTime(100);
		setJamTime(20);
		
		setPushForce(push);
		setDamage(dmg);
		setOffset(off);
		
		maxModes = 2; //低速
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
