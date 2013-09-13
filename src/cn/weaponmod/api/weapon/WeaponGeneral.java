package cn.weaponmod.api.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.feature.ISpecialUseable;
import cn.weaponmod.api.information.InformationWeapon;

public abstract class WeaponGeneral extends Item implements ISpecialUseable {

	public int type;
	public int ammoID;
	public float upLiftRadius, recoverRadius;
	protected String iconName = "";
	
	/**
	 * 
	 * @param par1
	 *            物品的ID.
	 * @param par2AmmoID
	 *            对应子弹的ID.(不一定被使用）
	 * @param par3MaxModes
	 *            最大模式数。（不一定被使用）
	 */
	public WeaponGeneral(int par1, int par2AmmoID) {
		super(par1);
		setMaxStackSize(1);
		this.setFull3D();
		ammoID = par2AmmoID;
	}
	
	public WeaponGeneral setIAndU(String name) {
		iconName = name;
		setUnlocalizedName(name);
		return this;
	}
	
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:" + iconName);
    }

	/**
	 * 设置武器上抬的参数。
	 * 
	 * @param uplift
	 *            每次射击上抬的角度。
	 * @param recover
	 *            每tick回复角度。
	 */
	public void setLiftProps(float uplift, float recover) {
		upLiftRadius = uplift;
		recoverRadius = recover;
	}

	/**
	 * 进行武器的Tick相关计算和主要功能，请在子类的onUpdate(...)中调用它。
	 */
	public InformationWeapon onWpnUpdate(ItemStack par1ItemStack,
			World par2World, Entity par3Entity, int par4, boolean par5) {

		if (!(par3Entity instanceof EntityPlayer) || !par5)
			return null;

		InformationWeapon information = loadInformation(par1ItemStack, (EntityPlayer) par3Entity);
		if (information == null) 
			return null;
		
		information.updateTick();
		
		if (information.isRecovering) {
			par3Entity.rotationPitch += recoverRadius;
			information.recoverTick++;
			if (information.recoverTick >= (upLiftRadius / recoverRadius))
				information.isRecovering = false;
		}
		
		if(par3Entity instanceof EntityLivingBase && doesAbortAnim(par1ItemStack, (EntityLivingBase) par3Entity)) {
			((EntityLivingBase)par3Entity).isSwingInProgress = false;
		}
		
		return information;

	}
	
	public boolean doesAbortAnim(ItemStack itemStack, EntityLivingBase player) {
		return true;
	}
	
    @Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity)
    {
    	return doesAbortAnim(stack, player);
    }
    
    @Override
	public boolean onBlockStartBreak(ItemStack itemstack, int i, int j, int k, EntityPlayer player)
    {
    	return doesAbortAnim(itemstack, player);
    }
    
	/**
	 * 进行射击时枪口上抬。
	 */
	public void doUplift(InformationWeapon information,
			EntityPlayer entityPlayer) {
		if (!doWeaponUplift())
			return;

		if (!information.isRecovering)
			information.originPitch = entityPlayer.rotationPitch;
		entityPlayer.rotationPitch -= upLiftRadius;
		information.isRecovering = true;
		information.recoverTick = 0;

	}

	/**
	 * 加载武器信息。
	 * 
	 * @return 需要的武器信息，可能为null
	 */
	public abstract InformationWeapon loadInformation(ItemStack par1Itack, EntityPlayer entityPlayer);

	/**
	 * 获取武器信息。
	 * 
	 * @return 需要的武器信息，可能为null
	 */
	public InformationWeapon getInformation(ItemStack itemStack, World world) {
		return WMInformation.getInformation(itemStack).getProperInf(world);
	}

	/**
	 * 获得武器对生物的附加推动力。
	 * 
	 * @param mode
	 *            当前武器模式
	 */
	public abstract double getPushForce(boolean left);

	/**
	 * 获得武器对生物的伤害。
	 * 
	 * @param mode
	 *            当前武器模式
	 */
	public abstract int getDamage(boolean left);

	/**
	 * 获得武器射击的位置偏移。
	 * 
	 * @param mode
	 *            当前武器模式
	 */
	public abstract int getOffset(boolean left);
	
	public abstract boolean doWeaponUplift();
}
