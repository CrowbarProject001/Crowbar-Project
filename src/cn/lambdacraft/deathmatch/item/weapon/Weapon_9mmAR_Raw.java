/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.weaponmod.api.WeaponHelper;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_9mmAR_Raw extends WeaponGeneralBullet_LC {

	public Weapon_9mmAR_Raw(int par1) {

		super(par1, CBCItems.ammo_9mm2.itemID);
		setIAndU("weapon_9mmar");
		setCreativeTab(CBCMod.cct);
		setMaxDamage(51);

		setReloadTime(60);
		setJamTime(10);
		setLiftProps(5, .7F);
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
	public String getSoundShoot(boolean left) {
		return "cbc.weapons.hksa";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload() {
		return "cbc.weapons.nmmarr";
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 4 : 0;
	}

	@Override
	public double getPushForce(boolean left) {
		return 0;
	}

	@Override
	public int getDamage(boolean left) {
		return 4;
	}

	@Override
	public int getOffset(boolean left) {
		return 0;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[2];
		tips[1] = new IHudTip() {

			@Override
			public Icon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if(Item.itemsList[ammoID] != null){
					return Item.itemsList[ammoID].getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return (itemStack.getMaxDamage() - itemStack.getItemDamage() - 1) + "|" + WeaponHelper.getAmmoCapacity(ammoID, player.inventory);
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}
			
		};
		
		tips[0] = new IHudTip() {

			@Override
			public Icon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				if(Item.itemsList[ammoID] != null){
					return CBCItems.ammo_argrenade.getIconIndex(itemStack);
				}
				return null;
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return String.valueOf(WeaponHelper.getAmmoCapacity(CBCItems.ammo_argrenade.itemID, player.inventory));
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}
			
		};
		return tips;
	}

}
