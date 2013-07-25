package cn.lambdacraft.deathmatch.items.wpns;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.api.weapon.CBCWeaponInformation;
import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.api.weapon.InformationSet;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entities.EntityCrossbowArrow;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Crossbow in HLDM Style. Mode I : Non-delay, sniper rifle style. Mode II :
 * Explosive arrow.
 * 
 * @author WeAthFolD
 */
public class Weapon_Crossbow extends WeaponGeneralBullet {

	public Icon[] sideIcons = new Icon[6];

	public Weapon_Crossbow(int par1) {
		super(par1, CBCItems.ammo_bow.itemID, 2);

		setUnlocalizedName("weapon_crossbow");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(6);
		setNoRepair();
		setIconName("weapon_crossbow");

		setLiftProps(10, 5);
		setReloadTime(40);

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		for (int i = 0; i < 6; i++) {
			sideIcons[i] = reg.registerIcon("lambdacraft:crossbow_side" + i);
		}
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

		par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 1.0F);
		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(new EntityCrossbowArrow(par2World,par3Entity, mode == 1));
		information.setLastTick();
		if (par3Entity instanceof EntityPlayer) {
			doUplift(information, par3Entity);
			if (!par3Entity.capabilities.isCreativeMode) {
					par1ItemStack.damageItem(1, par3Entity);
				par3Entity.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
			}
		}
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
	}

	@Override
	public String getSoundShoot(int mode) {
		return "cbc.weapons.xbow_fire";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "cbc.weapons.xbow_reload";
	}

	@Override
	public int getShootTime(int mode) {
		return 30;
	}

	@Override
	public double getPushForce(int mode) {
		return 1.5;
	}

	@Override
	public int getDamage(int mode) {
		return 20;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}

	@Override
	public String getModeDescription(int mode) {
		return mode == 0 ? "mode.bow1" : "mode.bow2";
	}

	public boolean isBowPulling(ItemStack item) {
		InformationSet inf = CBCWeaponInformation.getInformation(item);
		InformationBullet information = (InformationBullet) (inf == null ? null
				: inf.clientReference);
		if (information == null)
			return false;
		return !(information.getDeltaTick() < 17);
	}
}
