package cn.lambdacraft.api.weapon;

import java.util.List;

import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cn.lambdacraft.deathmatch.utils.BulletManager;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;

/**
 * General bullet weapon class, including 9mmhandgun, rpg, etc...
 * 
 * @author WeAthFolD
 * 
 */
public abstract class WeaponGeneralBullet extends WeaponGeneral {

	public int reloadTime;
	public int jamTime;

	public WeaponGeneralBullet(int par1, int par2ammoID, int par3maxModes) {

		super(par1, par2ammoID, par3maxModes);
		maxModes = par3maxModes;

		upLiftRadius = 10;
		recoverRadius = 2;
		type = 0;

	}

	/**
	 * Generally do the itemRightClick processing.
	 */
	public InformationBullet processRightClick(ItemStack par1ItemStack,
			World par2World, EntityPlayer par3EntityPlayer) {

		InformationBullet information = loadInformation(par1ItemStack,
				par3EntityPlayer);
		Boolean canUse = (par1ItemStack.getMaxDamage()
				- par1ItemStack.getItemDamage() - 1 > 0);
		int mode = getMode(par1ItemStack);

		if (canUse) {
			if (information.getDeltaTick() >= getShootTime(mode)) {
				this.onBulletWpnShoot(par1ItemStack, par2World,
						par3EntityPlayer, information);
			}
			information.isShooting = true;
		} else {
			onSetReload(par1ItemStack, par3EntityPlayer);
		}

		par3EntityPlayer.setItemInUse(par1ItemStack,
				getMaxItemUseDuration(par1ItemStack));
		return information;
	}

	public InformationBullet onBulletWpnUpdate(ItemStack par1ItemStack,
			World par2World, Entity par3Entity, int par4, boolean par5) {

		InformationBullet information = (InformationBullet) super.onWpnUpdate(
				par1ItemStack, par2World, par3Entity, par4, par5);

		if (information == null) {
			information = getInformation(par1ItemStack, par2World);
			if (information == null)
				return null;

			information.isShooting = false;
			information.isReloading = false;
			return null;
		}
		information.updateTick();

		EntityPlayer player = (EntityPlayer) par3Entity;

		if (doesShoot(information, par1ItemStack))
			this.onBulletWpnShoot(par1ItemStack, par2World, player, information);

		if (doesReload(information, par1ItemStack))
			this.onBulletWpnReload(par1ItemStack, par2World, player,
					information);
		if (doesJam(information, par1ItemStack))
			this.onBulletWpnJam(par1ItemStack, par2World, player, information);

		return information;
	}

	public Boolean canShoot(EntityPlayer player, ItemStack is) {
		return (is.getMaxDamage() - is.getItemDamage() - 1 > 0)
				|| player.capabilities.isCreativeMode;
	}

	/**
	 * Determine if the shoot method should be called this tick.
	 * 
	 * @return If the shoot method should be called in this tick or not.
	 */
	public Boolean doesShoot(InformationBullet inf, ItemStack itemStack) {
		Boolean canUse;
		int mode = getMode(itemStack);
		canUse = (itemStack.getMaxDamage() - itemStack.getItemDamage() - 1 > 0);
		return (getShootTime(mode) != 0 && inf.isShooting && canUse && inf
				.getDeltaTick() >= getShootTime(mode));
	}

	/**
	 * Determine if the Reload method should be called this tick.
	 * 
	 * @return If the Reload method should be called in this tick or not.
	 */
	public Boolean doesReload(InformationBullet inf, ItemStack itemStack) {
		return (inf.isReloading && inf.getDeltaTick() >= this.reloadTime);
	}

	/**
	 * Determine if the onBulletWpnJam() method should be called this tick.
	 * 
	 * @return If the jam method should be called in this tick or not.
	 */
	public Boolean doesJam(InformationBullet inf, ItemStack itemStack) {
		Boolean canUse;
		canUse = (itemStack.getMaxDamage() - itemStack.getItemDamage() - 1 > 0);
		return (jamTime != 0 && inf.isShooting && !canUse && inf.getDeltaTick() > jamTime);
	}

	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information) {
		if (!(player.capabilities.isCreativeMode))
			par1ItemStack.damageItem(1, player);
		player.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		if (!par2World.isRemote)
			BulletManager.Shoot(par1ItemStack, player, par2World);
		par2World.playSoundAtEntity(player,
				this.getSoundShoot(this.getMode(par1ItemStack)), 0.5F, 1.0F);
		doUplift(information, player);
		information.setLastTick();
	}

	public void onBulletWpnJam(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {
		int mode = getMode(par1ItemStack);
		par2World.playSoundAtEntity(par3Entity, getSoundJam(mode), 0.5F, 1.0F);
		information.setLastTick();
	}

	public boolean onSetReload(ItemStack itemStack, EntityPlayer player) {

		InformationBullet inf = loadInformation(itemStack, player);
		int mode = getMode(itemStack);
		if (itemStack.getItemDamage() <= 0)
			return false;
		if (!inf.isReloading && itemStack.getItemDamage() > 0) {
			player.worldObj.playSoundAtEntity(player, getSoundReload(mode),
					0.5F, 1.0F);
			inf.isReloading = true;
			inf.setLastTick();
		}
		return true;
	}

	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {

		EntityPlayer player = par3Entity;

		int dmg = par1ItemStack.getItemDamage();
		if (dmg <= 0) {
			information.setLastTick();
			information.isReloading = false;
			return;
		}

		par1ItemStack.setItemDamage(AmmoManager.consumeAmmo(player, this,
				par1ItemStack.getItemDamage()));

		information.isReloading = false;
		information.setLastTick();

		return;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		InformationBullet inf = getInformation(par1ItemStack, par2World);
		inf.isShooting = false;
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		par3List.add(StatCollector.translateToLocal("ammocap.name")
				+ ": "
				+ (par1ItemStack.getMaxDamage()
						- par1ItemStack.getItemDamageForDisplay() - 1) + "/"
				+ (par1ItemStack.getMaxDamage() - 1));
	}

	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundShoot(int mode);

	/**
	 * Get the gun jamming sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundJam(int mode);

	/**
	 * Get the reload sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundReload(int mode);

	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	public abstract int getShootTime(int mode);

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
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5);

	@Override
	public InformationBullet getInformation(ItemStack itemStack, World world) {
		InformationSet set = CBCWeaponInformation.getInformation(itemStack);
		return set == null ? null : set.getProperBullet(world);
	}

	@Override
	public InformationBullet loadInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer) {

		InformationBullet inf = getInformation(par1ItemStack,
				par2EntityPlayer.worldObj);

		if (inf != null)
			return inf;

		double uniqueID = Math.random() * 65535D;
		inf = CBCWeaponInformation.addToList(uniqueID,
				createInformation(par1ItemStack, par2EntityPlayer))
				.getProperBullet(par2EntityPlayer.worldObj);

		if (par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;

	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 100;
	}

	private InformationSet createInformation(ItemStack is, EntityPlayer player) {
		InformationBullet inf = new InformationBullet(is);
		InformationBullet inf2 = new InformationBullet(is);
		return new InformationSet(inf, inf2);
	}

}
