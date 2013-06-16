package cbproject.deathmatch.items.wpns;

import cbproject.core.CBCMod;
import cbproject.crafting.register.CBCItems;
import cbproject.deathmatch.entities.EntityARGrenade;
import cbproject.deathmatch.utils.AmmoManager;
import cbproject.deathmatch.utils.InformationBullet;
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
public class Weapon_9mmAR extends WeaponGeneralBullet {

	public Weapon_9mmAR(int par1) {

		super(par1, CBCItems.ammo_9mm2.itemID, 2);
		setUnlocalizedName("weapon_9mmar");
		setIconName("weapon_9mmar");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(51);
		setNoRepair(); // 涓嶅彲淇ˉ

		setReloadTime(60);
		setJamTime(10);
		setLiftProps(8, 2);

	}

	@Override
	public Boolean canShoot(EntityPlayer player, ItemStack is) {
		InformationBullet inf = this.getInformation(is, player.worldObj);
		int mode = getMode(is);
		return mode == 0 ? super.canShoot(player, is) : AmmoManager.hasAmmo(
				CBCItems.ammo_argrenade.itemID, player);
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		processRightClick(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {

		int mode = getMode(par1ItemStack);
		if (mode == 0) {
			super.onBulletWpnShoot(par1ItemStack, par2World, par3Entity,
					information);
		} else {
			if (!par2World.isRemote)
				if (par3Entity.capabilities.isCreativeMode
						|| AmmoManager.tryConsume(par3Entity,
								CBCItems.ammo_argrenade.itemID, 1) == 0) {
					par2World.spawnEntityInWorld(new EntityARGrenade(par2World,
							par3Entity));
					par2World.playSoundAtEntity(par3Entity,
							getSoundShoot(mode), 0.5F, 1.0F);
				}
		}
		par3Entity.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));

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
	public String getSoundShoot(int mode) {
		return mode == 0 ? "cbc.weapons.hksa"
				: (Math.random() * 2 > 1 ? "cbc.weapons.glauncher"
						: "cbc.weapons.glauncherb");
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "cbc.weapons.nmmarr";
	}

	@Override
	public int getShootTime(int mode) {
		return mode == 0 ? 4 : 20;
	}

	@Override
	public double getPushForce(int mode) {
		return 0;
	}

	@Override
	public int getDamage(int mode) {
		return 4;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}

	@Override
	public String getModeDescription(int mode) {
		return mode == 0 ? "mode.9mmar1" : "mode.9mmar2";
	}

}
