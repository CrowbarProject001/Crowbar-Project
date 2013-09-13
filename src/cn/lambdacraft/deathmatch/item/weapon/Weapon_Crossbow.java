package cn.lambdacraft.deathmatch.item.weapon;

import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.client.RenderTickHandler;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entity.EntityCrossbowArrow;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.feature.IModdable;
import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.information.InformationSet;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * Crossbow in HLDM Style. Mode I : Non-delay, sniper rifle style. Mode II :
 * Explosive arrow.
 * 
 * @author WeAthFolD
 */
public class Weapon_Crossbow extends WeaponGeneralBullet_LC implements IModdable {

	public Icon[] sideIcons = new Icon[6];

	public Weapon_Crossbow(int par1) {
		super(par1, CBCItems.ammo_bow.itemID);

		setUnlocalizedName("weapon_crossbow");
		setCreativeTab(CBCMod.cct);
		setMaxStackSize(1);
		setMaxDamage(6);
		setNoRepair();
		iconName = "weapon_crossbow";

		setLiftProps(10, .75F);
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
		super.onWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
		
		//TODO:Left to be finished
		if(par5 && getMode(par1ItemStack) == 1) {
			 RenderTickHandler.isZooming = true;
		}
		if(!par5) {
			NBTTagCompound nbt = loadCompound(par1ItemStack);
			nbt.setInteger("mode", 0);
		}
		
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {

		par2World.playSoundAtEntity(par3Entity, getSoundShoot(left), 0.5F, 1.0F);
		if (!par2World.isRemote)
			par2World.spawnEntityInWorld(new EntityCrossbowArrow(par2World,par3Entity, getMode(par1ItemStack) == 1));
		information.setLastTick(left);
		if (par3Entity instanceof EntityPlayer) {
			doUplift(information, par3Entity);
			if (!par3Entity.capabilities.isCreativeMode) {
					par1ItemStack.damageItem(1, par3Entity);
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
	public String getSoundShoot(boolean left) {
		return "cbc.weapons.xbow_fire";
	}

	@Override
	public String getSoundJam(boolean left) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload() {
		return "cbc.weapons.xbow_reload";
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 30 : 0;
	}

	@Override
	public double getPushForce(boolean left) {
		return 1.5;
	}

	@Override
	public int getDamage(boolean left) {
		return 20;
	}

	@Override
	public int getOffset(boolean left) {
		return 0;
	}
	
	public static boolean isBowPulling(ItemStack item) {
		InformationSet inf = WMInformation.getInformation(item);
		InformationBullet information = (InformationBullet) (inf == null ? null
				: inf.clientReference);
		if (information == null)
			return false;
		return !(information.getDeltaTick(true) < 17);
	}

	@Override
	public void onModeChange(ItemStack item, EntityPlayer player, int newMode) {
		NBTTagCompound nbt = loadCompound(item);
		nbt.setInteger("mode", newMode);
	}

	@Override
	public int getMode(ItemStack item) {
		return loadCompound(item).getInteger("mode");
	}

	@Override
	public int getMaxModes() {
		return 2;
	}

	@Override
	public String getModeDescription(int mode) {
		return "mode.crossbow" + mode;
	}
	
	private NBTTagCompound loadCompound(ItemStack itemStack) {
		if(itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		return itemStack.stackTagCompound;
	}
}
