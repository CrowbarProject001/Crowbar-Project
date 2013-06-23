package cn.lambdacraft.deathmatch.items.wpns;

import cn.lambdacraft.api.weapon.InformationEnergy;
import cn.lambdacraft.api.weapon.WeaponGeneralEnergy;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entities.EntityBulletEgon;
import cn.lambdacraft.deathmatch.entities.fx.EntityEgonRay;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cn.lambdacraft.deathmatch.utils.BulletManager;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * Egon energy weapon.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_Egon extends WeaponGeneralEnergy {

	public static String SND_WINDUP = "cbc.weapons.egon_windup",
			SND_RUN = "cbc.weapons.egon_run", SND_OFF = "cbc.weapons.egon_off";

	public Icon iconEquipped;

	public Weapon_Egon(int par1) {
		super(par1, CBCItems.ammo_uranium.itemID, 1);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("weapon_egon");
		setIconName("weapon_egon");
		setJamTime(20);
		setLiftProps(1, 0);
	}

	@SideOnly(Side.CLIENT)
	@Override
	public void registerIcons(IconRegister reg) {
		super.registerIcons(reg);
		iconEquipped = reg.registerIcon("lambdacraft:weapon_egon0");
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		InformationEnergy inf = getInformation(par1ItemStack, par2World);
		if (inf.isShooting && AmmoManager.hasAmmo(this, par3EntityPlayer))
			par2World.playSoundAtEntity(par3EntityPlayer, SND_OFF, 0.5F, 1.0F);
		inf.isShooting = false;
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		InformationEnergy inf = onEnergyWpnUpdate(par1ItemStack, par2World,
				par3Entity, par4, par5);
		if (inf == null)
			return;
		int dTick = inf.getDeltaTick();
		if (inf.isShooting) {
			if (inf.ticksExisted > 79 && (dTick - 79) % 42 == 0)
				par2World.playSoundAtEntity(par3Entity, SND_RUN, 0.5F, 1.0F);

			if (dTick % 3 == 0 && !par2World.isRemote) {
				EntityPlayer player = (EntityPlayer) par3Entity;
				AmmoManager.consumeAmmo((EntityPlayer) par3Entity, this, 1);
				if (!AmmoManager.hasAmmo(this, player)) {
					par2World
							.playSoundAtEntity(par3Entity, SND_OFF, 0.5F, 1.0F);
					inf.isShooting = false;
				}
			}
		}
	}

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {

		InformationEnergy inf = loadInformation(par1ItemStack, par3EntityPlayer);
		processRightClick(inf, par1ItemStack, par2World, par3EntityPlayer);

		if (inf.isShooting && canShoot(par3EntityPlayer, par1ItemStack)) {
			par2World.spawnEntityInWorld(new EntityBulletEgon(par2World, par3EntityPlayer, par1ItemStack));
			if (par2World.isRemote)
				par2World.spawnEntityInWorld(new EntityEgonRay(par2World,
						par3EntityPlayer, par1ItemStack));
			par2World.playSoundAtEntity(par3EntityPlayer, SND_WINDUP, 0.5F,
					1.0F);
		}

		inf.ticksExisted = inf.lastTick = 0;

		return par1ItemStack;

	}

	@Override
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer player, InformationEnergy information) {
		par2World.spawnEntityInWorld(new EntityBulletEgon(par2World, player, par1ItemStack));
		doUplift(information, player);
		player.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		return;
	}

	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 300;
	}

	@Override
	public double getPushForce(int mode) {
		return 0;
	}

	@Override
	public int getDamage(int mode) {
		return 10;
	}

	@Override
	public int getOffset(int mode) {
		return 2;
	}

	@Override
	public int getShootTime(int mode) {
		return 4;
	}

	@Override
	public String getSoundShoot(int mode) {
		return "";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getModeDescription(int mode) {
		return "mode.egon";
	}

}
