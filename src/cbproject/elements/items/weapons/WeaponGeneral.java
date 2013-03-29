package cbproject.elements.items.weapons;

import java.util.ArrayList;
import java.util.List;

import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public abstract class WeaponGeneral extends Item {
	
	protected List listItemStack;
	public int maxModes, type;
	public int ammoID;
	public  double upLiftRadius, recoverRadius; //player screen uplift radius in degree
	
	public void setLiftProps(double uplift, double recover){
		upLiftRadius = uplift;
		recoverRadius = recover;
	}
	
	public WeaponGeneral(int par1, int par2AmmoID,int par3MaxModes) {
		
		super(par1);
		maxModes = par3MaxModes;
		listItemStack = new ArrayList();
		ammoID = par2AmmoID;

	}
	
	public void onWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		InformationSet inf = getInformation(par1ItemStack);
		if(inf == null)
			return;
		InformationWeapon information = inf.getProperInf(par2World);
		
		//枪口上抬处理
		if(information.isRecovering){
			par3Entity.rotationPitch += recoverRadius;
			information.recoverTick++;
			if(information.recoverTick >= (upLiftRadius / recoverRadius))
				information.isRecovering = false;
		}
		
	}
	
	
	public abstract InformationSet loadInformation(ItemStack par1Itack, EntityPlayer entityPlayer);
	public abstract InformationSet getInformation(ItemStack itemStack);
	
	public InformationWeapon getSpecInformation(ItemStack itemStack,World world){
		InformationSet inf =  getInformation(itemStack);
		if(inf != null)
			return (world.isRemote? inf.getClientAsBullet(): inf.getServerAsBullet());
		return null;
	}

}
