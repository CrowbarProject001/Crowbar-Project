package cn.lambdacraft.deathmatch.items.wpns;

import java.util.List;

import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.weapon.IModdable;
import cn.lambdacraft.deathmatch.entities.EntityRPGDot;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cn.lambdacraft.deathmatch.utils.InformationRPG;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class Weapon_RPG extends Weapon_RPG_Raw implements IModdable {


	public Weapon_RPG(int par1) {
		super(par1);
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {

		InformationRPG inf = (InformationRPG) super.onBulletWpnUpdate(
				par1ItemStack, par2World, par3Entity, par4, par5);
		if (par2World.isRemote || inf == null)
			return;
		int mode = getMode(par1ItemStack);
		if (mode == 0)
			return;
		EntityPlayer player = (EntityPlayer) par3Entity;
		EntityRPGDot dot = getRPGDot(inf, par2World, player);
		if (dot == null || dot.isDead) {
			dot = new EntityRPGDot(par2World, player);
			par2World.spawnEntityInWorld(dot);
			this.setRPGDot(inf, dot);
		}
	}

	public static EntityRPGDot getRPGDot(InformationRPG inf, World world,
			EntityPlayer player) {
		return inf.currentDot;
	}

	public EntityRPGDot getRPGDot(ItemStack is, World world, EntityPlayer player) {
		return (loadInformation(is, player)).currentDot;
	}

	public void setRPGDot(InformationRPG inf, EntityRPGDot dot) {
		inf.currentDot = dot;
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
	
	@Override
	public void onModeChange(ItemStack item, EntityPlayer player, int newMode) {
		item.setItemDamage(newMode);
	}
	
	@Override
	public int getMode(ItemStack item) {
		return item.getItemDamage();
	}
	@Override
	public int getMaxModes() {
		return 2;
	}
	
	@Override
	public String getModeDescription(int mode) {
		return "mode.rpg" + mode;
	}
	
	@SideOnly(Side.CLIENT)
	@Override
	public void addInformation(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, List par3List, boolean par4) {
		
	}

}
