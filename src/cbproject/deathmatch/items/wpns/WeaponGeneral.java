package cbproject.deathmatch.items.wpns;

import java.util.ArrayList;
import java.util.List;


import org.lwjgl.input.Keyboard;

import cbproject.core.CBCMod;
import cbproject.core.misc.CBCKeyProcess;
import cbproject.core.utils.CBCWeaponInformation;
import cbproject.deathmatch.utils.BulletManager;
import cbproject.deathmatch.utils.InformationSet;
import cbproject.deathmatch.utils.InformationWeapon;

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
		
		InformationWeapon information = loadInformation(par1ItemStack, (EntityPlayer) par3Entity);
		
		ItemStack currentItem = ((EntityPlayer)par3Entity).inventory.getCurrentItem();
		if(currentItem == null || !currentItem.equals(par1ItemStack))
			return null;	
		
		if(information == null){
			System.err.println(par2World.isRemote + " side is null");
			return null;
		}
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
	 * @return required InformationWeapon
	 */
	public abstract InformationWeapon loadInformation(ItemStack par1Itack, EntityPlayer entityPlayer);
	
	/**
	 * get the WeaponInformation for the itemStack.
	 * @return required InformationSet, could be null
	 */
	public InformationWeapon getInformation(ItemStack itemStack, World world){
	    return CBCWeaponInformation.getInformation(itemStack).getProperInf(world);
	}
	
	public abstract double getPushForce(int mode);
	public abstract int getDamage(int mode);
	public abstract int getOffset(int mode);
	
	/**
	 * Only called in server, set the mode to required on client's will.
	 */
	public void onModeChange(ItemStack itemStack, EntityPlayer player, int newMode){
		InformationWeapon inf = loadInformation(itemStack, player);
		System.out.println(player.worldObj.isRemote);
		inf.mode = newMode;
	}
	

}
