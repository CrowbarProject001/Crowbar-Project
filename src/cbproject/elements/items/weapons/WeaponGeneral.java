package cbproject.elements.items.weapons;

import java.util.ArrayList;
import java.util.List;

import org.lwjgl.input.Keyboard;

import cbproject.CBCMod;
import cbproject.misc.CBCKeyProcess;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
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
	
	public InformationWeapon onWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		
		if(!(par3Entity instanceof EntityPlayer))
			return null;
		
		InformationSet inf = loadInformation(par1ItemStack, (EntityPlayer) par3Entity);
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
	
	public void onModeChange(ItemStack item, InformationWeapon inf, EntityPlayer player){
		
		inf.mode = (maxModes -1 == inf.mode) ? 0 : inf.mode +1;
		player.sendChatToPlayer("New Mode : " + inf.mode);
		item.getTagCompound().setInteger("mode", inf.mode);
		
	}
	
	public void doRecover(InformationWeapon information, EntityPlayer entityPlayer){
		
		if(!information.isRecovering)
			information.originPitch = entityPlayer.rotationPitch;
		entityPlayer.rotationPitch -= upLiftRadius;
		information.isRecovering = true;
		information.recoverTick = 0;
		
	}
	
	
	public abstract InformationSet loadInformation(ItemStack par1Itack, EntityPlayer entityPlayer);
	public abstract InformationSet getInformation(ItemStack itemStack);
	
	public abstract double getPushForce(int mode);
	public abstract int getDamage(int mode);
	public abstract int getOffset(int mode);
	
	public InformationSet getInformation(int weaponID){
		InformationSet inf = (InformationSet) listItemStack.get(weaponID);
		return inf;
	}
	
	public InformationWeapon getSpecInformation(ItemStack itemStack,World world){
		InformationSet inf =  getInformation(itemStack);
		if(inf != null)
			return inf.getProperInf(world);
		return null;
	}

}
