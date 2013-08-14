package cn.lambdacraft.deathmatch.items.wpns;

import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entities.EntityARGrenade;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
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
	public boolean canShoot(EntityPlayer player, ItemStack is) {
		InformationBullet inf = this.getInformation(is, player.worldObj);
		boolean side = getUsingSide(is);
		return side ? super.canShoot(player, is) : (player.capabilities.isCreativeMode || AmmoManager.hasAmmo(CBCItems.ammo_argrenade.itemID, player));
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {

		if (left) {
			super.onBulletWpnShoot(par1ItemStack, par2World, par3Entity, information, left);
		} else {
			if (par3Entity.capabilities.isCreativeMode || AmmoManager.tryConsume(par3Entity,
					CBCItems.ammo_argrenade.itemID, 1) == 0) {
				if(!par2World.isRemote) {
					par2World.spawnEntityInWorld(new EntityARGrenade(par2World, par3Entity));
					par2World.playSoundAtEntity(par3Entity,
							getSoundShoot(left), 0.5F, 1.0F);
				}
			}	
		}
		information.setLastTick();
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
