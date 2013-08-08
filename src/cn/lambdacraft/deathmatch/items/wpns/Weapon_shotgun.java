package cn.lambdacraft.deathmatch.items.wpns;

import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entities.EntityBullet;
import cn.lambdacraft.deathmatch.entities.EntityBulletSG;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_Shotgun extends WeaponGeneralBullet {

	public static int BUCKSHOT_COUNT[] = { 8, 16 };

	public Weapon_Shotgun(int par1) {

		super(par1, CBCItems.ammo_shotgun.itemID);

		setUnlocalizedName("weapon_shotgun");
		setIconName("weapon_shotgun");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(9);
		setNoRepair(); // 不可修补

		setReloadTime(10);
		setJamTime(20);
		setLiftProps(30, 5);

	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
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

		boolean left = getUsingSide(par1ItemStack);
		int dmg = par1ItemStack.getItemDamage();
		if (dmg <= 0) {
			information.setLastTick();
			information.isReloading = false;
			return;
		}
		if (par2World.isRemote)
			return;

		if (AmmoManager.consumeAmmo(player, this, 1) == 0) {
			par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() - 1);
			par2World.playSoundAtEntity(player, getSoundReload(left), 0.5F,
					1.0F);
		} else
			information.isReloading = false;

		information.setLastTick();
		return;
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {

		int maxDmg = par1ItemStack.getMaxDamage() - 1;
		if (par1ItemStack.getItemDamage() >= maxDmg) {
			information.setLastTick();
			return;
		}
		int count = left ? 8 : 16;
		for (int i = 0; i < count; i++) {
			if(!par2World.isRemote)
				par2World.spawnEntityInWorld(new EntityBulletSG(par2World,
						par3Entity, par1ItemStack));
			else par2World.spawnEntityInWorld(new EntityBullet(par2World,
					par3Entity, par1ItemStack));
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

		information.setLastTick();
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
	public String getSoundReload(boolean left) {
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
