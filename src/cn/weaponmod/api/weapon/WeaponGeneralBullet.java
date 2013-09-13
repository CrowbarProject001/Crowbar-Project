package cn.weaponmod.api.weapon;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.information.InformationSet;
import cn.weaponmod.events.ItemHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

public abstract class WeaponGeneralBullet extends WeaponGeneral {

	public int reloadTime;
	public int jamTime;
	public boolean isAutomatic = true;

	public WeaponGeneralBullet(int par1, int par2ammoID) {

		super(par1, par2ammoID);
		setMaxStackSize(1);
		upLiftRadius = 5.5F;
		recoverRadius = 0.6F;
		type = 0;

	}

	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, boolean left) {
		InformationBullet information = loadInformation(stack, player);
		Boolean canUse = canShoot(player, stack, left);
		if (canUse) {
			if(this.doesShoot(information, player, stack, left)) {
				this.onBulletWpnShoot(stack, world, player, information, left);
			}
			information.isReloading = false;
			if(isAutomatic)
				ItemHelper.setItemInUse(player, stack, this.getMaxItemUseDuration(stack), left); 
		} else {
			onSetReload(stack, player);
		}

		return;
	}
	
	@Override
	public void onItemUsingTick(World world, EntityPlayer player, ItemStack stack, boolean type, int tickLeft) {
		InformationBullet information = loadInformation(stack, player);

		if (doesShoot(information, player, stack, type))
			this.onBulletWpnShoot(stack, world, player, information, type);

		else if (doesJam(information, player, stack, type))
			this.onBulletWpnJam(stack, world, player, information, type);
	}

	@Override
	public InformationBullet onWpnUpdate(ItemStack par1ItemStack,
			World par2World, Entity par3Entity, int par4, boolean par5) {

		InformationBullet information = (InformationBullet) super.onWpnUpdate(
				par1ItemStack, par2World, par3Entity, par4, par5);

		if (information == null) {
			information = getInformation(par1ItemStack, par2World);
			if (information == null)
				return null;
			information.isReloading = false;
			return null;
		}

		EntityPlayer player = (EntityPlayer) par3Entity;
		if (doesReload(information, par1ItemStack))
			this.onBulletWpnReload(par1ItemStack, par2World, player,
					information);

		return information;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
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
	public boolean doesShoot(InformationBullet inf, EntityPlayer player,
			ItemStack itemStack, boolean side) {
		boolean canUse;
		canUse = canShoot(player, itemStack, side);
		return getShootTime(side) != 0 && canUse
				&& inf.getDeltaTick(side) >= getShootTime(side) && !inf.isReloading;
	}

	/**
	 * Determine if the Reload method should be called this tick.
	 * 
	 * @return If the Reload method should be called in this tick or not.
	 */
	public boolean doesReload(InformationBullet inf, ItemStack itemStack) {
		return (inf.isReloading && inf.getDeltaTick(false) >= this.reloadTime);
	}

	/**
	 * Determine if the onBulletWpnJam() method should be called this tick.
	 * 
	 * @return If the jam method should be called in this tick or not.
	 */
	public boolean doesJam(InformationBullet inf, EntityPlayer player,
			ItemStack itemStack, boolean left) {
		Boolean canUse;
		canUse = canShoot(player, itemStack, left);
		return (jamTime != 0 && !canUse && inf.getDeltaTick(left) > jamTime);
	}

	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information, boolean left) {

		WeaponHelper.Shoot(par1ItemStack, player, par2World, left);
		if (!(player.capabilities.isCreativeMode))
			this.setItemDamageForStack(par1ItemStack, this.getItemDamageFromStack(par1ItemStack) + 1);
		par2World.playSoundAtEntity(player,this.getSoundShoot(left), 0.5F, 1.0F);
		doUplift(information, player);
		information.setLastTick_Shoot(left);
		information.setMuzzleTick(left);
	}

	public void onBulletWpnJam(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean side) {
		par2World.playSoundAtEntity(par3Entity, getSoundJam(side), 0.5F, 1.0F);
		information.setLastTick(side);
	}

	public boolean onSetReload(ItemStack itemStack, EntityPlayer player) {
		InformationBullet inf = loadInformation(itemStack, player);
		if (itemStack.getItemDamage() <= 0)
			return false;
		
		if (!inf.isReloading && itemStack.getItemDamage() > 0) {
			if(WeaponHelper.hasAmmo(this, player))
				player.worldObj.playSoundAtEntity(player, getSoundReload(), 0.5F, 1.0F);
			ItemHelper.stopUsingItem(player, true);
			ItemHelper.stopUsingItem(player, false);
			inf.isReloading = true;
			inf.setLastTick(false);
		}
		return true;
	}

	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {

		EntityPlayer player = par3Entity;

		int dmg = par1ItemStack.getItemDamage();
		if (dmg <= 0) {
			information.setLastTick(false);
			information.isReloading = false;
			return;
		}

		par1ItemStack.setItemDamage(WeaponHelper.consumeAmmo(player, this, par1ItemStack.getItemDamage()));
		par3Entity.playSound(this.getSoundReloadFinish(), 0.5F, 1.0F);

		information.isReloading = false;
		information.setLastTick(false);

		return;
	}

	@Override
	public InformationBullet getInformation(ItemStack itemStack, World world) {
		InformationSet<InformationBullet> set = WMInformation.getInformation(itemStack);
		return set == null ? null : set.getProperInf(world);
	}

	@Override
	public InformationBullet loadInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer) {

		InformationBullet inf = getInformation(par1ItemStack,
				par2EntityPlayer.worldObj);

		if (inf != null)
			return inf;

		double uniqueID = itemRand.nextDouble() * 65535D;
		inf = (InformationBullet) WMInformation.addToList(uniqueID, createInformation(par1ItemStack, par2EntityPlayer))
				.getProperInf(par2EntityPlayer.worldObj);

		if (par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;

	}

	private InformationSet<InformationBullet> createInformation(ItemStack is, EntityPlayer player) {
		InformationBullet inf = new InformationBullet(is);
		InformationBullet inf2 = new InformationBullet(is);
		return new InformationSet<InformationBullet>(inf, inf2);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 600;
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
	 * Get the reload sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public String getSoundReload() {
		return "";
	}
	
	public String getSoundReloadFinish() {
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
	 * Set the reload time.
	 * 
	 * @param reloadTime
	 */
	public final void setReloadTime(int par1) {
		reloadTime = par1;
	}

	/**
	 * Set the gun jamming time.
	 * 
	 * @param jamTime
	 */
	public final void setJamTime(int par1) {
		jamTime = par1;
	}

	@Override
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5);
	
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

	// ------------------Client-----------------------
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("ammocap.name")
				+ ": "
				+ (par1ItemStack.getMaxDamage()
						- par1ItemStack.getItemDamage() - 1) + "/"
				+ (par1ItemStack.getMaxDamage() - 1));
	}
}
