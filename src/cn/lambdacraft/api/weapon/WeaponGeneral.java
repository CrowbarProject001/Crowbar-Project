package cn.lambdacraft.api.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.core.proxy.GeneralProps;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * CBC General Weapon class.
 * 
 * @author WeAthFolD
 * 
 */
public abstract class WeaponGeneral extends CBCGenericItem implements IModdable {

	public int maxModes, type;
	public int ammoID;
	public double upLiftRadius, recoverRadius;

	/**
	 * 
	 * @param par1
	 *            物品的ID.
	 * @param par2AmmoID
	 *            对应子弹的ID.(不一定被使用）
	 * @param par3MaxModes
	 *            最大模式数。（不一定被使用）
	 */
	public WeaponGeneral(int par1, int par2AmmoID, int par3MaxModes) {
		super(par1);
		setMaxStackSize(1);
		this.setFull3D();
		maxModes = par3MaxModes;
		ammoID = par2AmmoID;
	}

	/**
	 * 设置武器上抬的参数。
	 * 
	 * @param uplift
	 *            每次射击上抬的角度。
	 * @param recover
	 *            每tick回复角度。
	 */
	public void setLiftProps(double uplift, double recover) {
		upLiftRadius = uplift;
		recoverRadius = recover;
	}

	/**
	 * 进行武器的Tick相关计算和主要功能，请在子类的onUpdate(...)中调用它。
	 */
	public InformationWeapon onWpnUpdate(ItemStack par1ItemStack,
			World par2World, Entity par3Entity, int par4, boolean par5) {

		if (!(par3Entity instanceof EntityPlayer))
			return null;

		InformationWeapon information = loadInformation(par1ItemStack,
				(EntityPlayer) par3Entity);

		ItemStack currentItem = ((EntityPlayer) par3Entity).inventory
				.getCurrentItem();
		if (currentItem == null || !currentItem.equals(par1ItemStack))
			return null;

		if (information == null) {
			return null;
		}

		if (information.isRecovering) {
			par3Entity.rotationPitch += recoverRadius;
			information.recoverTick++;
			if (information.recoverTick >= (upLiftRadius / recoverRadius))
				information.isRecovering = false;
		}
		return information;

	}

	/**
	 * 进行射击时枪口上抬。
	 */
	public void doUplift(InformationWeapon information,
			EntityPlayer entityPlayer) {
		if (!GeneralProps.doWeaponUplift)
			return;

		if (!information.isRecovering)
			information.originPitch = entityPlayer.rotationPitch;
		entityPlayer.rotationPitch -= upLiftRadius;
		information.isRecovering = true;
		information.recoverTick = 0;

	}

	/**
	 * 获取武器模式说明。(Unlocalized)
	 * 
	 * @param mode
	 *            武器模式
	 * @return mode 说明
	 */
	public abstract String getModeDescription(int mode);

	/**
	 * 加载武器信息。
	 * 
	 * @return 需要的武器信息，可能为null
	 */
	public abstract InformationWeapon loadInformation(ItemStack par1Itack,
			EntityPlayer entityPlayer);

	/**
	 * 获取武器信息。
	 * 
	 * @return 需要的武器信息，可能为null
	 */
	public InformationWeapon getInformation(ItemStack itemStack, World world) {
		return CBCWeaponInformation.getInformation(itemStack).getProperInf(
				world);
	}

	/**
	 * 获得武器对生物的附加推动力。
	 * 
	 * @param mode
	 *            当前武器模式
	 */
	public abstract double getPushForce(int mode);

	/**
	 * 获得武器对生物的伤害。
	 * 
	 * @param mode
	 *            当前武器模式
	 */
	public abstract int getDamage(int mode);

	/**
	 * 获得武器射击的位置偏移。
	 * 
	 * @param mode
	 *            当前武器模式
	 */
	public abstract int getOffset(int mode);

	/**
	 * 在切换模式时调用这个函数。
	 */
	public void onModeChange(ItemStack item, EntityPlayer player, int newMode) {
		if (item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		item.getTagCompound().setInteger("mode", newMode);
	}

	/**
	 * 获取武器的模式。
	 * 
	 * @param item
	 * @return
	 */
	public int getMode(ItemStack item) {
		if (item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		return item.getTagCompound().getInteger("mode");
	}
	
	@Override
	public int getMaxModes() {
		return maxModes;
	}

}
