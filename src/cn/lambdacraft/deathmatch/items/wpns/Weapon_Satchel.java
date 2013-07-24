/**
 * 
 */
package cn.lambdacraft.deathmatch.items.wpns;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.hud.IHudTipProvider;
import cn.lambdacraft.api.weapon.IModdable;
import cn.lambdacraft.api.weapon.InformationWeapon;
import cn.lambdacraft.api.weapon.WeaponGeneral;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.deathmatch.client.HEVRenderingUtils;
import cn.lambdacraft.deathmatch.entities.EntitySatchel;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Remote detonation bomb. Mode I : Setting mode. Mode II : Detonating mode.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_Satchel extends CBCGenericItem implements IHudTipProvider, IModdable {

	public Icon iconSetting;

	public Weapon_Satchel(int par1) {

		super(par1);
		setUnlocalizedName("weapon_satchel");
		setIconName("weapon_satchel");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(64);

	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		iconSetting = reg.registerIcon("lambdacraft:weapon_satchel1");
	}

	@SideOnly(Side.CLIENT)
	@Override
	/**
	 * Gets an icon index based on an item's damage value
	 */
	public Icon getIconFromDamage(int par1) {
		return par1 == 0 ? this.itemIcon : this.iconSetting;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		
		int mode = getMode(par1ItemStack);

		NBTTagCompound nbt = par3EntityPlayer.getEntityData();
		int count = nbt.getInteger("satchelCount");
		// Max 6 satchel
		if (mode == 0) { // Setting mode
			if (count > 5)
				return par1ItemStack;

			nbt.setBoolean("doesExplode", false);
			if (!par2World.isRemote) {
				EntitySatchel ent = new EntitySatchel(par2World,
						par3EntityPlayer);
				par2World.spawnEntityInWorld(ent);
			}
			nbt.setInteger("satchelCount", ++count);
			if (!par3EntityPlayer.capabilities.isCreativeMode)
				--par1ItemStack.stackSize;

		} else { // Detonating mode
			nbt.setBoolean("doesExplode", true);
			nbt.setInteger("satchelCount", 0);
		}

		par3EntityPlayer.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		par3EntityPlayer.setEating(false);
		return par1ItemStack;
	}
	
	@Override
	public void onModeChange(ItemStack item, EntityPlayer player, int newMode) {
		item.setItemDamage(newMode);
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 100;
	}

	@Override
	public String getModeDescription(int mode) {
		return mode == 0 ? "mode.satchel1" : "mode.satchel2";
	}

	@Override
	@SideOnly(Side.CLIENT)
	public IHudTip[] getHudTip(ItemStack itemStack, EntityPlayer player) {
		IHudTip[] tips = new IHudTip[1];
		tips[0] = new IHudTip() {

			@Override
			public Icon getRenderingIcon(ItemStack itemStack,
					EntityPlayer player) {
				return HEVRenderingUtils.getHudSheetIcon(32, 48, "satchel");
			}

			@Override
			public String getTip(ItemStack itemStack, EntityPlayer player) {
				return String.valueOf(AmmoManager.getAmmoCapacity(itemID, player.inventory));
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return 5;
			}
			
		};
		return tips;
	}

	@Override
	public int getMode(ItemStack item) {
		return item.getItemDamage();
	}

	@Override
	public int getMaxModes() {
		return 2;
	}
	

}
