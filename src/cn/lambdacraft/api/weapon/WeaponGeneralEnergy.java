package cn.lambdacraft.api.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.hud.IHudTipProvider;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import cn.lambdacraft.deathmatch.utils.ItemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public abstract class WeaponGeneralEnergy extends WeaponGeneral implements IHudTipProvider {

	public int jamTime;

	public WeaponGeneralEnergy(int par1, int par2AmmoID) {
		super(par1, par2AmmoID);
		type = 1;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,EntityPlayer par3EntityPlayer, int par4) {}
	
	@Override
	public void onItemClick(World world, EntityPlayer player, ItemStack stack, boolean left) {
		this.setUsingSide(stack, left);
		
		boolean side = this.getUsingSide(stack);
		
		Boolean canUse = canShoot(player, stack);
		InformationEnergy inf = loadInformation(stack, player);
		if(canUse) {
			if (doesShoot(inf, stack, player))
				onEnergyWpnShoot(stack, world, player, inf);
			ItemHelper.setItemInUse(player, stack, this.getMaxItemUseDuration(stack), left);
		}
	}
	
	@Override
	public void onItemUsingTick(World world, EntityPlayer player, ItemStack stack, boolean type, int tickLeft) {
    	InformationEnergy inf = loadInformation(stack, player);
    	if (doesJam(inf, stack, player)) 
			this.onEnergyWpnJam(stack, world, player, inf);
		
    	if (doesShoot(inf, stack, player)) 
			this.onEnergyWpnShoot(stack, world, player, inf);
	}

	public InformationEnergy onEnergyWpnUpdate(ItemStack par1ItemStack,
			World par2World, Entity par3Entity, int par4, boolean par5) {

		InformationEnergy information = (InformationEnergy) super.onWpnUpdate(
				par1ItemStack, par2World, par3Entity, par4, par5);
		return information;

	}
	/**
	 * Determine if the shoot method should be called this tick.
	 * 
	 * @return If the shoot method should be called in this tick or not.
	 */
	public boolean doesShoot(InformationEnergy inf, ItemStack itemStack,
			EntityPlayer player) {
		boolean canUse = canShoot(player, itemStack);
		boolean side = this.getUsingSide(itemStack);
		return getShootTime(side) > 0 && canUse && inf.getDeltaTick() >= getShootTime(side);
	}

	/**
	 * Determine if the jam method should be called this tick.
	 * 
	 * @return If the jam method should be called in this tick or not.
	 */
	public boolean doesJam(InformationEnergy inf, ItemStack itemStack,
			EntityPlayer player) {
		boolean canUse = canShoot(player, itemStack);
		return !canUse && inf.getDeltaTick() >= jamTime;
	}

	/**
	 * Very normal Energy shooting process, currently not used.
	 */
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationEnergy information) {

		boolean side = this.getUsingSide(par1ItemStack);

		par2World.playSoundAtEntity(par3Entity, getSoundShoot(side), 0.5F, 1.0F);
		BulletManager.Shoot(par1ItemStack, par3Entity, par2World);
		information.setLastTick();

		if (par3Entity instanceof EntityPlayer) {
			doUplift(information, par3Entity);
			EntityPlayer player = par3Entity;
			if (!par3Entity.capabilities.isCreativeMode) {
				AmmoManager.consumeAmmo(player, this, 1);
			}
		}

		return;
	}

	public void onEnergyWpnJam(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationEnergy information) {

		int maxDmg = par1ItemStack.getMaxDamage();
		boolean side = this.getUsingSide(par1ItemStack);

		if (par1ItemStack.getItemDamage() < maxDmg) {
			return;
		}

		par2World.playSoundAtEntity(par3Entity, getSoundJam(side), 0.5F,
				0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
		information.setLastTick();

	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 600;
	}
	
	//----------------------Utilitiies------------------
	@Override
	public InformationEnergy loadInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer) {

		InformationEnergy inf = getInformation(par1ItemStack,
				par2EntityPlayer.worldObj);
		if (inf != null)
			return inf;

		double uniqueID = itemRand.nextDouble() * 65535D;

		inf = (InformationEnergy) CBCWeaponInformation.addToList(uniqueID,
				createInformation(par1ItemStack, par2EntityPlayer))
				.getProperInf(par2EntityPlayer.worldObj);

		if (par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;

	}

	@Override
	public InformationEnergy getInformation(ItemStack itemStack, World world) {
		InformationSet<InformationEnergy> set = CBCWeaponInformation.getInformation(itemStack);
		return set == null ? null : set.getProperInf(world);
	}

	private InformationSet createInformation(ItemStack is, EntityPlayer player) {
		InformationEnergy inf = new InformationEnergy(is, player);
		InformationEnergy inf2 = new InformationEnergy(is, player);
		return new InformationSet(inf, inf2);
	}
	
	public boolean canShoot(EntityPlayer player, ItemStack is) {
		return  (player.capabilities.isCreativeMode || AmmoManager.hasAmmo(this, player)) && getShootTime(getUsingSide(is)) > 0;
	}
	
	//----------------------------Interfaces---------------------------
	
	/**
	 * Set the jam time.
	 * @param jamTime
	 */
	public final void setJamTime(int par1) {
		jamTime = par1;
	}
	
	/**
	 * get the shoot time for the weapon corresponding to the mode.
	 * 
	 * @param mode
	 * @return shootTime
	 */
	public abstract int getShootTime(boolean side);
	
	@Override
	public abstract void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5);

	/**
	 * get the damage for the weapon corresponding to the mode.
	 * 
	 * @param mode
	 * @return damage
	 */
	@Override
	public abstract int getDamage(boolean side);

	/**
	 * Get the shoot sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundShoot(boolean side);

	/**
	 * Get the jam sound path corresponding to the mode.
	 * 
	 * @param mode
	 * @return sound path
	 */
	public abstract String getSoundJam(boolean side);
	
	//--------------------CLIENT------------------
	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

			@Override
			public Icon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if(Item.itemsList[ammoID] != null){
					return Item.itemsList[ammoID].getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return String.valueOf(AmmoManager.getAmmoCapacity(ammoID, player.inventory));
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}
			
		};
		return tips;
	}


}
