package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.util.GenericUtils;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.entities.EntityBullet;

public class Weapon_Shotgun extends WeaponGeneralBullet_LC {

	public static int BUCKSHOT_COUNT[] = { 8, 16 };

	public Weapon_Shotgun(int par1) {

		super(par1, CBCItems.ammo_shotgun.itemID);

		setUnlocalizedName("weapon_shotgun");
		iconName = "weapon_shotgun";
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(9);
		setNoRepair(); // 不可修补

		setReloadTime(10);
		setJamTime(20);
		setLiftProps(18F, 1.5F);

	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
	}

	@Override
	public void onBulletWpnReload(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationBullet information) {

		int dmg = par1ItemStack.getItemDamage();
		if (dmg <= 0) {
			information.setLastTick(false);
			information.isReloading = false;
			return;
		}
		if (par2World.isRemote)
			return;

		if (WeaponHelper.consumeAmmo(player, this, 1) == 0) {
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);
			par2World.playSoundAtEntity(player, getSoundReload(), 0.5F,
					1.0F);
		} else
			information.isReloading = false;

		information.setLastTick(false);
		return;
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {

		int maxDmg = par1ItemStack.getMaxDamage() - 1;
		if (par1ItemStack.getItemDamage() >= maxDmg) {
			information.setLastTick(left);
			return;
		}
		int count = left ? 8 : 16;
		for (int i = 0; i < count; i++) {
			par2World.spawnEntityInWorld(new EntityBullet(par2World,
					par3Entity, par1ItemStack, left));
		}
		if (!par2World.isRemote) {
			par2World.playSoundAtEntity(par3Entity, getSoundShoot(left), 0.5F,
					1.0F);
		}

		if (par3Entity instanceof EntityPlayer) {
			doUplift(information, par3Entity);
			if (!par3Entity.capabilities.isCreativeMode) {
				par1ItemStack.damageItem(left ? 1 : 2,
						par3Entity);
			}
		}

		information.setLastTick(left);
		par3Entity.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));

	}

	@Override
	public String getSoundShoot(boolean left) {
		return left ? "cbc.weapons.sbarrela" : "cbc.weapons.sbarrelb";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "cbc.weapons.scocka";
	}

	@Override
	public String getSoundReload() {
		return GenericUtils.getRandomSound("cbc.weapons.reload", 3);
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 20 : 35;
	}

	@Override
	public double getPushForce(boolean left) {
		return left ? 1.2 : 2;
	}

	@Override
	public int getDamage(boolean left) {
		return 3;
	}

	@Override
	public int getOffset(boolean left) {
		return left ? 8 : 17;
	}

}
