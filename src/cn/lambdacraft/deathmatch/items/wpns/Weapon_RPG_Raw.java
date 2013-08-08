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
package cn.lambdacraft.deathmatch.items.wpns;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.weapon.CBCWeaponInformation;
import cn.lambdacraft.api.weapon.IModdable;
import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.api.weapon.InformationSet;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entities.EntityRPGDot;
import cn.lambdacraft.deathmatch.entities.EntityRocket;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cn.lambdacraft.deathmatch.utils.InformationRPG;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * @author WeAthFolD
 *
 */
public class Weapon_RPG_Raw extends WeaponGeneralBullet {


	public Weapon_RPG_Raw(int par1) {
		super(par1, CBCItems.ammo_rpg.itemID);

		setIAndU("weapon_rpg");
		setCreativeTab(CBCMod.cct);
		this.hasSubtypes = true;

		setJamTime(20);
		setLiftProps(20, 2);
	}
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {

		InformationRPG inf = (InformationRPG) super.onBulletWpnUpdate(
				par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {
		information.setLastTick();
		par3Entity.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		if (par2World.isRemote)
			return;
		if (this.canShoot(par3Entity, par1ItemStack)) {
			par2World.playSoundAtEntity(par3Entity, getSoundShoot(left), 0.5F,
					1.0F);
			par2World.spawnEntityInWorld(new EntityRocket(par2World,
					par3Entity, par1ItemStack));
			AmmoManager.consumeAmmo(par3Entity, this, 1);
		}
	}

	@Override
	public boolean canShoot(EntityPlayer player, ItemStack is) {
		return AmmoManager.hasAmmo(this, player) || player.capabilities.isCreativeMode;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
	}

	@Override
	public String getSoundShoot(boolean side) {
		return "cbc.weapons.rocketfire";
	}

	@Override
	public String getSoundJam(boolean side) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(boolean side) {
		return "";
	}

	@Override
	public int getShootTime(boolean side) {
		return side ? 40 : 0;
	}

	@Override
	public double getPushForce(boolean side) {
		return 0;
	}

	@Override
	public int getDamage(boolean side) {
		return 0;
	}

	@Override
	public int getOffset(boolean side) {
		return 0;
	}

	@Override
	public InformationRPG getInformation(ItemStack itemStack, World world) {
		InformationSet set = CBCWeaponInformation.getInformation(itemStack);
		return (InformationRPG) (set == null ? null : set.getProperInf(world));
	}

	@Override
	public InformationRPG loadInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer) {

		InformationRPG inf = getInformation(par1ItemStack,
				par2EntityPlayer.worldObj);

		if (inf != null)
			return inf;

		double uniqueID = itemRand.nextDouble() * 65535D;
		inf = (InformationRPG) CBCWeaponInformation.addToList(uniqueID,
				createInformation(par1ItemStack, par2EntityPlayer))
				.getProperInf(par2EntityPlayer.worldObj);

		if (par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;

	}

	private InformationSet createInformation(ItemStack is, EntityPlayer player) {
		InformationRPG inf = new InformationRPG(is);
		InformationRPG inf2 = new InformationRPG(is);
		return new InformationSet(inf, inf2);
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

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
				return String.valueOf(AmmoManager.getAmmoCapacity(ammoID, player.inventory));
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}
			
		};
		return tips;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		
	}

}
