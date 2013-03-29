package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationEnergy;
import cbproject.utils.weapons.InformationSet;

public class Weapon_gauss extends WeaponGeneralEnergy {

	public Weapon_gauss(int par1) {
		
		super(par1, CBCMod.cbcItems.itemAmmo_uranium.itemID, 2);
		
		pathSoundShoot = new String[2];
		pathSoundJam = new String[2];
		pathSoundSpecial = new String[1];
		
		setCreativeTab(CBCMod.cct);
		setItemName("Gauss");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(6,2);
		
		String pshoot[] = {"cbc.weapons.gaussb", "cbc.weapons.gauss_chargea"};
		String special[] = {"cbc.weapons.gauss_chargeb"};
		int shoot[] = { 5 , 0}, dmg[] = {8}, off[] = { 0 };
		double push[] = { 1, 1};
		
		setJamTime(0);
		setPathShoot(pshoot);
		setPathSpecial(special);
		setShootTime(shoot);
		setDamage(dmg);
		setOffset(off);
		setPushForce(push);
		
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		InformationSet inf = getInformation(par1ItemStack);
		
		if(inf == null)
			return;
		InformationEnergy information = inf.getProperEnergy(par2World);
		if(information.mode == 0){
			this.onEnergyWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
			return;
		}
		
		onChargeModeUpdate(information, par1ItemStack, par2World, par3Entity, par4, par5);
		
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		
		InformationEnergy inf = loadInformation(par1ItemStack, par3EntityPlayer).getProperEnergy(par2World);
		inf.mode = 1;
		if( inf.mode == 0)
			processRightClick( inf, par1ItemStack, par2World, par3EntityPlayer);
		else {
			inf.isShooting = true;
			par2World.playSoundAtEntity(par3EntityPlayer, "cbc.weapons.gauss_chargea",  
					0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
			par3EntityPlayer.setItemInUse(par1ItemStack,this.getMaxItemUseDuration(par1ItemStack));
			
		}
		return par1ItemStack;
		
    }
	
	public  void onChargeModeUpdate(InformationEnergy inf, ItemStack par1ItemStack, World par2World, 
			Entity par3Entity, int par4, boolean par5){
		
		int var1 = 40;
		int var2 = 200;
		int var3 = 60;
		int var4 = 100;
		Boolean isShooting = inf.isShooting;
		inf.updateTick();
		
		if(isShooting){
			int ticksChange = inf.ticksExisted - inf.lastTick;
			if(inf.ammoManager.ammoCapacity > 0)
				inf.charge ++;
			if(ticksChange <= var3 && ticksChange%6 == 0)
				inf.ammoManager.consumeAmmo(1, (EntityPlayer)par3Entity);
		}
			
		if(inf.charge > var1 && inf.ticksExisted - inf.lastTick > var1){
			inf.setLastTick();
			par2World.playSoundAtEntity(par3Entity, pathSoundSpecial[0], 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		}
		if(inf.charge > var2){
			isShooting = false;
			inf.charge = 0;
			par3Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLiving) (par3Entity)), 19);
		}
		
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		System.out.println("called stop");
		InformationEnergy inf = getInformation(par1ItemStack).getProperEnergy(par2World);
		if(inf.mode == 0){
			super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
			if(inf == null || inf.charge == 0){
				return;
			}
		}
		int charge = (inf.charge > 60? 60 : inf.charge); //最大蓄力3秒(10点)
		int damage = charge * 2/3; //最大为40
		double vel = charge / 15; //最大为4
		System.out.println("Charge: " + charge);
		par2World.playSoundAtEntity(par3EntityPlayer, "cbc.weapons.gaussb",  
				0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		CBCMod.bulletManager.Shoot(par3EntityPlayer, par2World, damage, offset[0], vel);
		
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200; //10s
    }


}
