package cn.lambdacraft.api.weapon;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.util.StatCollector;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.hud.IHudTipProvider;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import cn.lambdacraft.deathmatch.utils.ItemHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * General bullet weapon class, including 9mmhandgun, rpg, etc...
 * 
 * @author WeAthFolD
 * 
 */
public abstract class WeaponGeneralBullet extends WeaponGeneral implements IHudTipProvider {

	public int reloadTime;
	public int jamTime;

	public WeaponGeneralBullet(int par1, int par2ammoID) {

		super(par1, par2ammoID);

		upLiftRadius = 10;
		recoverRadius = 2;
		type = 0;

	}

	
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, boolean left) {
		InformationBullet information = loadInformation(stack, player);
		Boolean canUse = canShoot(player, stack);
		this.setUsingSide(stack, left);
		if (canUse) {
			if (!information.isReloading) {
				if(this.doesShoot(information, player, stack)) {
					this.onBulletWpnShoot(stack, world, player, information, left);
				}
				ItemHelper.setItemInUse(player, stack, this.getMaxItemUseDuration(stack), left);
			}
		} else {
			onSetReload(stack, player);
		}

		return;
	}
	
	public void onItemUsingTick(World world, EntityPlayer player, ItemStack stack, boolean type, int tickLeft) {
		InformationBullet information = loadInformation(stack, player);

		if (doesShoot(information, player, stack))
			this.onBulletWpnShoot(stack, world, player, information, type);

		else if (doesJam(information, player, stack))
			this.onBulletWpnJam(stack, world, player, information);
	}

	public InformationBullet onBulletWpnUpdate(ItemStack par1ItemStack,
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

	public boolean canShoot(EntityPlayer player, ItemStack is) {
		return (is.getMaxDamage() - is.getItemDamage() - 1 > 0)
				|| player.capabilities.isCreativeMode;
	}

	/**
	 * Determine if the shoot method should be called this tick.
	 * 
	 * @return If the shoot method should be called in this tick or not.
	 */
	public boolean doesShoot(InformationBullet inf, EntityPlayer player,
			ItemStack itemStack) {
		boolean canUse;
		boolean side = this.getUsingSide(itemStack);
		canUse = canShoot(player, itemStack);
		return getShootTime(side) != 0 && canUse
				&& inf.getDeltaTick() >= getShootTime(side) && !inf.isReloading;
	}

	/**
	 * Determine if the Reload method should be called this tick.
	 * 
	 * @return If the Reload method should be called in this tick or not.
	 */
	public boolean doesReload(InformationBullet inf, ItemStack itemStack) {
		return (inf.isReloading && inf.getDeltaTick() >= this.reloadTime);
	}

	/**
	 * Determine if the onBulletWpnJam() method should be called this tick.
	 * 
	 * @return If the jam method should be called in this tick or not.
	 */
	public boolean doesJam(InformationBullet inf, EntityPlayer player,
			ItemStack itemStack) {
		Boolean canUse;
		canUse = canShoot(player, itemStack);
		return (jamTime != 0 && !canUse && inf.getDeltaTick() > jamTime);
	}

	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information, boolean left) {

		BulletManager.Shoot(par1ItemStack, player, par2World);
		if (!(player.capabilities.isCreativeMode))
			par1ItemStack.damageItem(1, player);
		par2World.playSoundAtEntity(player,this.getSoundShoot(this.getUsingSide(par1ItemStack)), 0.5F, 1.0F);
		doUplift(information, player);
		information.setLastTick();
	}

	public void onBulletWpnJam(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {
		boolean side = this.getUsingSide(par1ItemStack);
		par2World.playSoundAtEntity(par3Entity, getSoundJam(side), 0.5F, 1.0F);
		information.setLastTick();
	}

	public boolean onSetReload(ItemStack itemStack, EntityPlayer player) {
		InformationBullet inf = loadInformation(itemStack, player);
		boolean side = this.getUsingSide(itemStack);
		if (itemStack.getItemDamage() <= 0)
			return false;
		if (!inf.isReloading && itemStack.getItemDamage() > 0) {
			player.worldObj.playSoundAtEntity(player, getSoundReload(side),
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
	public InformationBullet getInformation(ItemStack itemStack, World world) {
		InformationSet<InformationBullet> set = CBCWeaponInformation.getInformation(itemStack);
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
		inf = (InformationBullet) CBCWeaponInformation.addToList(uniqueID, createInformation(par1ItemStack, par2EntityPlayer))
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
	public abstract String getSoundShoot(boolean left);

	/**
	 * Get the gun jamming sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundJam(boolean left);

	/**
	 * Get the reload sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundReload(boolean left);

	/**
	 * Get the shoot time corresponding to the mode.
	 * 
	 * @param mode
	 * @return shoot time
	 */
	public abstract int getShootTime(boolean left);

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

	// ------------------Client-----------------------
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

	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

			@Override
			public Icon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if (Item.itemsList[ammoID] != null) {
					return Item.itemsList[ammoID].getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return (itemStack.getMaxDamage() - itemStack.getItemDamage() - 1)
						+ "|"
						+ AmmoManager.getAmmoCapacity(ammoID, player.inventory);
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}

		};
		return tips;
	}

}
