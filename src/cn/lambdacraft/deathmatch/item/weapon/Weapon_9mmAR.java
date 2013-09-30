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
		InformationBullet inf = (InformationBullet) this.getInformation(is, player.worldObj);
		return side ? super.canShoot(player, is, side) : (player.capabilities.isCreativeMode || WeaponHelper.hasAmmo(CBCItems.ammo_argrenade.itemID, player));
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	protected Entity getBulletEntity(ItemStack stack, World world, EntityPlayer player, boolean left) {
		return left ? super.getBulletEntity(stack, world, player, left) : world.isRemote ? null : new EntityARGrenade(world, player);
	}

	@Override
	public String getSoundShoot(boolean left) {
		return left ? "lambdacraft:weapons.hksa" : (itemRand.nextFloat() * 2 > 1 ? "lambdacraft:weapons.glauncher" : "lambdacraft:weapons.glauncherb");
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 4 : 20;
	}
	
}
