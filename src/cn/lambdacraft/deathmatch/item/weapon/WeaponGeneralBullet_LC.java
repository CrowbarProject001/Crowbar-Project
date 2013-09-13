/**
 * 
 */
package cn.lambdacraft.deathmatch.item.weapon;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.hud.IHudTipProvider;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.weaponmod.api.WeaponHelper;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;

/**
 * @author WeAthFolD
 *
 */
public class WeaponGeneralBullet_LC extends WeaponGeneralBullet implements IHudTipProvider {
	
	public WeaponGeneralBullet_LC(int par1, int par2ammoID) {
		super(par1, par2ammoID);
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneralBullet#onUpdate(net.minecraft.item.ItemStack, net.minecraft.world.World, net.minecraft.entity.Entity, int, boolean)
	 */
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getPushForce(boolean)
	 */
	@Override
	public double getPushForce(boolean left) {
		return 2;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getDamage(boolean)
	 */
	@Override
	public int getDamage(boolean left) {
		return 10;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#getOffset(boolean)
	 */
	@Override
	public int getOffset(boolean left) {
		return 5;
	}

	/* (non-Javadoc)
	 * @see cn.weaponmod.api.weapon.WeaponGeneral#doWeaponUplift()
	 */
	@Override
	public boolean doWeaponUplift() {
		return GeneralProps.doWeaponUplift;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

			@Override
			public Icon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if (Item.itemsList[ammoID] != null) {
					return Item.itemsList[ammoID].getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return (itemStack.getMaxDamage() - itemStack.getItemDamage() - 1)
						+ "|"
						+ WeaponHelper.getAmmoCapacity(ammoID, player.inventory);
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}

		};
		return tips;
	}

}
