package cn.lambdacraft.deathmatch.items.wpns;

import cn.lambdacraft.api.hud.ISpecialCrosshair;
import cn.lambdacraft.api.weapon.InformationEnergy;
import cn.lambdacraft.api.weapon.WeaponGeneralEnergy;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.utils.MotionXYZ;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entities.EntityBulletGaussSec.EnumGaussRayType;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cn.lambdacraft.deathmatch.utils.GaussBulletManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * 喜闻乐见的Gauss！有穿墙和击飞效果喔~
 * @author WeAthFolD
 *
 */
public class Weapon_Gauss extends WeaponGeneralEnergy implements ISpecialCrosshair {

	
	public static String SND_CHARGE_PATH = "cbc.weapons.gauss_charge",
			SND_CHARGEA_PATH[] = { "cbc.weapons.gauss_windupa",
					"cbc.weapons.gauss_windupb", "cbc.weapons.gauss_windupc",
					"cbc.weapons.gauss_windupd" },
			SND_SHOOT_PATH = "cbc.weapons.gaussb";

	public Weapon_Gauss(int par1) {
		super(par1, CBCItems.ammo_uranium.itemID, 2);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("weapon_gauss");
		setJamTime(20);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void registerIcons(IconRegister par1IconRegister) {
		this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_gauss");
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {

		InformationEnergy inf = onEnergyWpnUpdate(par1ItemStack,
				par2World, par3Entity, par4, par5);
		if (inf == null)
			return;
		inf.rotationAngle += inf.rotationVelocity;
		int mode = getMode(par1ItemStack);
		if (mode == 1)
			onChargeModeUpdate(inf, par1ItemStack, par2World,
					(EntityPlayer) par3Entity, par4, par5);
		else if(inf.rotationVelocity > 0)
			inf.rotationVelocity -= 0.5;
	}

	@Override
	public Boolean doesShoot(InformationEnergy inf, ItemStack itemStack,
			EntityPlayer player) {
		int mode = getMode(itemStack);
		return mode == 0 && super.doesShoot(inf, itemStack, player);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		InformationEnergy inf = loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick(inf, par1ItemStack, par2World, par3EntityPlayer);
		int mode = getMode(par1ItemStack);
		if (mode == 0) {
			inf.rotationVelocity = 15.0F;
			return par1ItemStack;
		}

		if (mode == 1) {
			inf.resetState();
			inf.isShooting = true;
			if (canShoot(par3EntityPlayer, par1ItemStack))
				par2World.playSoundAtEntity(par3EntityPlayer,
						SND_CHARGEA_PATH[0], 0.5F, 1.0F);
		}
		return par1ItemStack;

	}

	public void onChargeModeUpdate(InformationEnergy inf,
			ItemStack par1ItemStack, World par2World, EntityPlayer player,
			int par4, boolean par5) {

		final int OVER_CHARGE_LIMIT = 160;
		final int CHARGE_TIME_LIMIT = 41;

		if (inf.isShooting) {
			int ticksChange = inf.getDeltaTick();
			inf.chargeTime++;
			if(inf.rotationVelocity <= 15)
				inf.rotationVelocity += 1;

			Boolean canUse = this.canShoot(player, par1ItemStack);
			Boolean ignoreAmmo = player.capabilities.isCreativeMode;

			if (canUse && inf.chargeTime < CHARGE_TIME_LIMIT)
				inf.charge++;

			if (!canUse)
				player.stopUsingItem();

			if (!ignoreAmmo && inf.ticksExisted <= CHARGE_TIME_LIMIT
					&& inf.chargeTime % 4 == 0)
				AmmoManager.consumeAmmo(player, this, 1);

			// OverCharge!
			if (inf.chargeTime > OVER_CHARGE_LIMIT) {
				inf.isShooting = false;
				inf.resetState();
				player.attackEntityFrom(DamageSource.causeMobDamage(player), 15);
			}

			int i = -1;
			if (inf.ticksExisted == 9) {
				i = 1;
			} else if (inf.ticksExisted == 18) {
				i = 2;
			} else if (inf.ticksExisted == 27) {
				i = 3;
			}
			if (i > 0)
				par2World.playSoundAtEntity(player, SND_CHARGEA_PATH[i], 0.5F,
						1.0F);

			if (inf.chargeTime >= 30 && inf.chargeTime % 15 == 0) {
				inf.setLastTick();
				par2World
						.playSoundAtEntity(player, SND_CHARGE_PATH, 0.5F, 1.0F);
			}
		} else if(inf.rotationVelocity > 0)
			inf.rotationVelocity -= 0.5;

	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		InformationEnergy inf = getInformation(par1ItemStack, par2World);
		int mode = getMode(par1ItemStack);
		if (inf == null)
			return;

		if (mode == 0) {
			super.onPlayerStoppedUsing(par1ItemStack, par2World,
					par3EntityPlayer, par4);
			return;
		}

		// Do the charge attack part
		int charge = (inf.charge > 60 ? 60 : inf.charge);
		if (charge <= 6) {
			inf.isShooting = false;
			return;
		}
		int damage = charge * 2 / 3;
		double vel = charge / 14.0;

		MotionXYZ var0 = MotionXYZ.getPosByPlayer2(par3EntityPlayer);
		double dx = var0.motionX * vel, dy = var0.motionY * vel, dz = var0.motionZ
				* vel;

		inf.isShooting = false;
		par3EntityPlayer.addVelocity(-dx, -dy, -dz);
		if (!par2World.isRemote) {
			par2World.playSoundAtEntity(par3EntityPlayer, SND_SHOOT_PATH, 0.5F,
					1.0F);
			GaussBulletManager.Shoot(par1ItemStack, par3EntityPlayer, par2World);
		}

	}

	@Override
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationEnergy information) {
		int mode = getMode(par1ItemStack);
		if(!par2World.isRemote)
		GaussBulletManager.Shoot2(EnumGaussRayType.NORMAL, par2World, player,
				par1ItemStack, null, null, getDamage(mode));
		par2World.playSoundAtEntity(player, getSoundShoot(mode), 0.5F, 1.0F);
		AmmoManager.consumeAmmo(player, this, 2);
		information.setLastTick();
		return;
	}
	
	@SideOnly(Side.CLIENT)
	public float getRotationForStack(ItemStack is, EntityLiving living) {
		if(!(living instanceof EntityPlayer))
			return 0.0F;
		InformationEnergy inf = loadInformation(is, (EntityPlayer) living);
		if(inf == null)
			return 0;
		return inf.rotationAngle / 0.01745329252F;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 500;
	}

	@Override
	public int getShootTime(int mode) {
		return 5;
	}

	@Override
	public String getSoundShoot(int mode) {
		return mode == 0 ? SND_SHOOT_PATH : "";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public int getDamage(int mode) {
		return 8;
	}

	@Override
	public double getPushForce(int mode) {
		return 1;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}

	@Override
	public String getModeDescription(int mode) {
		return mode == 0 ? "mode.gauss1" : "mode.gauss2";
	}

	@Override
	public int getHalfWidth() {
		return 8;
	}

}
