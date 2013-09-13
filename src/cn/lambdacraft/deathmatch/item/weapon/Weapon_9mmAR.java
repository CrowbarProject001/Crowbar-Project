package cn.lambdacraft.deathmatch.item.weapon;

import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityARGrenade;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.events.ItemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * 9mm Assault Rifle class. Mode I : Bullet, II : AR Grenade.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_9mmAR extends Weapon_9mmAR_Raw {

	public Weapon_9mmAR(int par1) {
		super(par1);
	}

	@Override
	public boolean canShoot(EntityPlayer player, ItemStack is, boolean side) {
		InformationBullet inf = this.getInformation(is, player.worldObj);
		return side ? super.canShoot(player, is, side) : (player.capabilities.isCreativeMode || WeaponHelper.hasAmmo(CBCItems.ammo_argrenade.itemID, player));
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
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
			ItemHelper.setItemInUse(player, stack, this.getMaxItemUseDuration(stack), left); 
		} else if(left) {
			onSetReload(stack, player);
		}
		return;
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {

		if (left) {
			super.onBulletWpnShoot(par1ItemStack, par2World, par3Entity, information, left);
		} else {
			if (par3Entity.capabilities.isCreativeMode || WeaponHelper.tryConsume(par3Entity, CBCItems.ammo_argrenade.itemID, 1) == 0) {
				if(!par2World.isRemote) {
					par2World.spawnEntityInWorld(new EntityARGrenade(par2World, par3Entity));
					par2World.playSoundAtEntity(par3Entity,
							getSoundShoot(left), 0.5F, 1.0F);
				}
			}	
		}
		information.setLastTick(left);
		return;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {

		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);

	}

	@Override
	public String getSoundShoot(boolean left) {
		return left ? "cbc.weapons.hksa" : (itemRand.nextFloat() * 2 > 1 ? "cbc.weapons.glauncher" : "cbc.weapons.glauncherb");
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 4 : 20;
	}
	
}
