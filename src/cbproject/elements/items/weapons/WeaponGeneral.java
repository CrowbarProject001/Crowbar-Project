package cbproject.elements.items.weapons;

import java.util.ArrayList;
import java.util.List;


import org.lwjgl.input.Keyboard;

import cbproject.CBCMod;
import cbproject.misc.CBCKeyProcess;
import cbproject.utils.CBCWeaponInformation;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * CBC General Weapon class.
 * @author WeAthFolD
 *
 */
public abstract class WeaponGeneral extends Item {

	public int maxModes, type;
	public int ammoID;
	public  double upLiftRadius, recoverRadius;
	
	public WeaponGeneral(int par1, int par2AmmoID,int par3MaxModes) {
		
		super(par1);
		maxModes = par3MaxModes;
		ammoID = par2AmmoID;

	}
	

	
	public void setLiftProps(double uplift, double recover){
		upLiftRadius = uplift;
		recoverRadius = recover;
	}
	
	public InformationWeapon onWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		
		if(!(par3Entity instanceof EntityPlayer))
			return null;
		
		InformationSet inf = loadInformation(par1ItemStack, (EntityPlayer) par3Entity);
		if(inf == null)
			return null;
		
		InformationWeapon information = inf.getProperInf(par2World);
		
		ItemStack currentItem = ((EntityPlayer)par3Entity).inventory.getCurrentItem();
		if(currentItem == null || !currentItem.equals(par1ItemStack))
			return null;	
		

		if(CBCKeyProcess.modeChange)
			CBCKeyProcess.onModeChange(par1ItemStack ,inf, (EntityPlayer) par3Entity, maxModes);	
		
		if(information.isRecovering){
			par3Entity.rotationPitch += recoverRadius;
			information.recoverTick++;
			if(information.recoverTick >= (upLiftRadius / recoverRadius))
				information.isRecovering = false;
		}		
		return information;

	}
	
	/**
	 * Do the screen uplift when shoot or stuffs.>)
	 * 
	 */
	public void doUplift(InformationWeapon information, EntityPlayer entityPlayer){
		
		if(!information.isRecovering)
			information.originPitch = entityPlayer.rotationPitch;
		entityPlayer.rotationPitch -= upLiftRadius;
		information.isRecovering = true;
		information.recoverTick = 0;
		
	}
	
	/**
	 * Get the description for the mode.
	 * @param mode
	 * @return mode description.
	 */
	public abstract String getModeDescription(int mode);
	
	/**
	 * get and load the WeaponInformation for the itemStack.
	 * @return required InformationSet
	 */
	public abstract InformationSet loadInformation(ItemStack par1Itack, EntityPlayer entityPlayer);
	
	/**
	 * get the WeaponInformation for the itemStack.
	 * @return required InformationSet, could be null
	 */
	public InformationSet getInformation(ItemStack itemStack){
	    return CBCWeaponInformation.getInformation(itemStack);
	}
	
	public abstract double getPushForce(int mode);
	public abstract int getDamage(int mode);
	public abstract int getOffset(int mode);
	
	/**
	 * Only called in server, set the mode to required on client's will.
	 */
	public void onModeChange(ItemStack itemStack, World world, int newMode){
		CBCMod.wpnInformation.getInformation(itemStack).serverReference.mode = newMode;
	}
	
	/**
	 * get the InformationWeapon correspond to the world.
	 */
	public InformationWeapon getSpecInformation(ItemStack itemStack,World world){
		InformationSet inf =  getInformation(itemStack);
		if(inf != null)
			return inf.getProperInf(world);
		return null;
	}
	

}
