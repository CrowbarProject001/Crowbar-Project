/**
 * 
 */
package cn.lambdacraft.deathmatch.item.weapon;

import java.lang.reflect.Constructor;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.information.InformationSet;
import cn.weaponmod.api.information.InformationWeapon;
import cn.weaponmod.api.weapon.WeaponGeneral;
import cn.weaponmod.entities.EntityBullet;
import cn.weaponmod.events.ItemHelper;

/**
 * @author Administrator
 *
 */
public class WeaponGeneralEnergy_LC extends WeaponGeneral {

	public int jamTime;
	
	private Constructor<? extends Entity> vaildConstructor;
	private byte constructorType = -1;
	
	/**
	 * The bulletEntity class used to detonate automatically when shoot.
	 * The offered Entity must have a constructor with following PARAMETERS:
	 * BulletEntity( World, Entity, int , boolean)
	 * in which the 2rd parameter represents the shooter(Mostly EntityPlayer), 3rd parameter represents the DAMAGE value, 4rd represents the UsingSide..
	 * or following :
	 * BulletEntity( World, Entity, ItemStack)
	 * in which the ItemStack represents the current Item for further calculation.
	 */
	public WeaponGeneralEnergy_LC setBulletClass(Class<? extends Entity> ent) {
		try {
			Constructor<? extends Entity> c = ent.getConstructor(World.class, Entity.class, ItemStack.class, Boolean.TYPE);
			vaildConstructor = c;
			constructorType = 1;
		} catch(NoSuchMethodException e) {
			try {
				Constructor<? extends Entity> c = ent.getConstructor(World.class, Entity.class, Integer.TYPE);
				vaildConstructor = c;
				constructorType = 0;
			} catch(Exception e2) {
				constructorType = -1;
				e2.printStackTrace();
			}
		} catch(Exception e) {
			constructorType = -1;
			e.printStackTrace();
		} finally {
			
		}
		return this;
	}
	
	/**
	 * @param par1
	 * @param par2AmmoID
	 */
	public WeaponGeneralEnergy_LC(int par1, int par2AmmoID) {
		super(par1, par2AmmoID);
	}

	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, boolean left) {
		InformationEnergy information = loadInformation(stack, player);
		Boolean canUse = canShoot(player, stack, left);
		if (canUse) {
			if(this.doesShoot(information, player, stack, left)) {
				this.onEnergyWpnShoot(stack, world, player, information, left);
			}
			ItemHelper.setItemInUse(player, stack, this.getMaxItemUseDuration(stack), left); 
		}

		return;
	}
	
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationEnergy information, boolean left) {
		
		Entity e = null;
		try {
		switch(constructorType) {
		case -1:
			e = new EntityBullet(par2World, player, par1ItemStack, left);
			break;
		case 0:
			e = vaildConstructor.newInstance(par2World, player, this.getDamage(left));
			break;
		case 1:
			e = vaildConstructor.newInstance(par2World, player, par1ItemStack, left);
			break;
		}
		} catch(Exception ex) {
			ex.printStackTrace();
		}
		par2World.spawnEntityInWorld(e);
		if (!(player.capabilities.isCreativeMode))
			this.setItemDamageForStack(par1ItemStack, this.getItemDamageFromStack(par1ItemStack) + 1);
		par2World.playSoundAtEntity(player,this.getSoundShoot(left), 0.5F, 1.0F);
		doUplift(information, player);
		information.setLastTick_Shoot(left);
	}

	@Override
	public void onItemUsingTick(World world, EntityPlayer player, ItemStack stack, boolean type, int tickLeft) {
		InformationEnergy information = loadInformation(stack, player);

		if (doesShoot(information, player, stack, type))
			this.onEnergyWpnShoot(stack, world, player, information, type);

		else if (doesJam(information, player, stack, type))
			this.onEnergyWpnJam(stack, world, player, information, type);
	}
	
	// --------------------Utilities---------------------------

	public boolean canShoot(EntityPlayer player, ItemStack is, boolean side) {
		return (is.getMaxDamage() - is.getItemDamage() - 1 > 0)
				|| player.capabilities.isCreativeMode;
	}

	/**
	 * Determine if the shoot method should be called this tick.
	 * 
	 * @return If the shoot method should be called in this tick or not.
	 */
	public boolean doesShoot(InformationEnergy inf, EntityPlayer player,
			ItemStack itemStack, boolean side) {
		boolean canUse;
		canUse = canShoot(player, itemStack, side);
		return getShootTime(side) != 0 && canUse
				&& inf.getDeltaTick(side) >= getShootTime(side);
	}
	
	public void onEnergyWpnJam(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationEnergy information, boolean side) {
		par2World.playSoundAtEntity(par3Entity, getSoundJam(side), 0.5F, 1.0F);
		information.setLastTick(side);
	}
	
	/**
	 * Determine if the onBulletWpnJam() method should be called this tick.
	 * 
	 * @return If the jam method should be called in this tick or not.
	 */
	public boolean doesJam(InformationEnergy inf, EntityPlayer player,
			ItemStack itemStack, boolean left) {
		Boolean canUse;
		canUse = canShoot(player, itemStack, left);
		return (jamTime != 0 && !canUse && inf.getDeltaTick(left) > jamTime);
	}

	
	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getPushForce(boolean)
	 */
	@Override
	public double getPushForce(boolean left) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getDamage(boolean)
	 */
	@Override
	public int getDamage(boolean left) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getOffset(boolean)
	 */
	@Override
	public int getOffset(boolean left) {
		// TODO Auto-generated method stub
		return 0;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#doWeaponUplift()
	 */
	@Override
	public boolean doWeaponUplift() {
		return false;
	}
	
	// -------------------Interfaces-------------------

	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public String getSoundShoot(boolean left) {
		return "";
	}
	

	/**
	 * Get the gun jamming sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public String getSoundJam(boolean left) {
		return "";
	}

	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	public int getShootTime(boolean left) {
		return 10;
	}

	/**
	 * Set the gun jamming time.
	 * 
	 * @param jamTime
	 */
	public final void setJamTime(int par1) {
		jamTime = par1;
	}
	
	//@Override
	//public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	
	//---------------------------------Disablings--------------------------
	
    /**
     * Return the itemDamage represented by this ItemStack. Defaults to the itemDamage field on ItemStack, but can be overridden here for other sources such as NBT.
     *
     * @param stack The itemstack that is damaged
     * @return the damage value
     */
    public int getItemDamageFromStack(ItemStack stack)
    {
    	NBTTagCompound nbt = loadNBT(stack);
    	return nbt.getInteger("ammo");
    }
    
    /**
     * Return the itemDamage display value represented by this itemstack.
     * @param stack the stack
     * @return the damage value
     */
    public int getItemDamageFromStackForDisplay(ItemStack stack)
    {
        return 0;
    }
    
    /**
     * Return if this itemstack is damaged. Note only called if {@link #isDamageable()} is true.
     * @param stack the stack
     * @return if the stack is damaged
     */
    public boolean isItemStackDamaged(ItemStack stack)
    {
        return false;
    }
    
    public void setItemDamageForStack(ItemStack stack, int damage)
    {
    	loadNBT(stack).setInteger("ammo", damage < 0 ? 0 : damage);
    }
    
    public NBTTagCompound loadNBT(ItemStack s) {
    	if(s.stackTagCompound == null)
    		s.stackTagCompound = new NBTTagCompound();
    	return s.stackTagCompound;
    }
    
    //------------------------Information Loading------------------------------
	
	@Override
	public InformationEnergy getInformation(ItemStack itemStack, World world) {
		InformationSet<InformationEnergy> set = WMInformation.getInformation(itemStack);
		return set == null ? null : set.getProperInf(world);
	}

	@Override
	public InformationEnergy loadInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer) {

		InformationEnergy inf = getInformation(par1ItemStack,
				par2EntityPlayer.worldObj);

		if (inf != null)
			return inf;

		double uniqueID = itemRand.nextDouble() * 65535D;
		inf = (InformationEnergy) WMInformation.addToList(uniqueID, createInformation(par1ItemStack, par2EntityPlayer))
				.getProperInf(par2EntityPlayer.worldObj);

		if (par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;

	}

	private InformationSet<InformationEnergy> createInformation(ItemStack is, EntityPlayer player) {
		InformationEnergy inf = new InformationEnergy(is);
		InformationEnergy inf2 = new InformationEnergy(is);
		return new InformationSet<InformationEnergy>(inf, inf2);
	}

}
