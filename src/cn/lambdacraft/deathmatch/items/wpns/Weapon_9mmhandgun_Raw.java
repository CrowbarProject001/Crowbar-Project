package cn.lambdacraft.deathmatch.items.wpns;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;

public class Weapon_9mmhandgun_Raw extends WeaponGeneralBullet {

	public Weapon_9mmhandgun_Raw(int par1) {

		super(par1, CBCItems.ammo_9mm.itemID);

		setUnlocalizedName("weapon_9mmhandgun");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(18);
		setIconName("weapon_9mmhandgun");
		setNoRepair();

		setReloadTime(60);
		setJamTime(10);
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
	public String getSoundShoot(boolean left) {
		return "cbc.weapons.plgun_c";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(boolean left) {
		return "cbc.weapons.nmmclipa";
	}

	@Override
	public int getShootTime(boolean left) {
		return  left ? 10 : 0;
	}

	@Override
	public double getPushForce(boolean left) {
		return 0.5;
	}

	@Override
	public int getDamage(boolean left) {
		return 3;
	}

	@Override
	public int getOffset(boolean left) {
		return 1;
	}

}
