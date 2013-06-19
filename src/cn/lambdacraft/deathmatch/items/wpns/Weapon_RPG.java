package cn.lambdacraft.deathmatch.items.wpns;

import cn.lambdacraft.api.weapon.CBCWeaponInformation;
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
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class Weapon_RPG extends WeaponGeneralBullet {


	public Weapon_RPG(int par1) {
		super(par1, CBCItems.ammo_rpg.itemID, 2);

		setIAndU("weapon_rpg");
		setCreativeTab(CBCMod.cct);
		setMaxDamage(2);

		setJamTime(20);
		setLiftProps(20, 2);
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

	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		processRightClick(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information) {
		information.setLastTick();
		int mode = getMode(par1ItemStack);
		par3Entity.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		if (par2World.isRemote)
			return;
		if (this.canShoot(par3Entity, par1ItemStack)) {
			par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F,
					1.0F);
			par2World.spawnEntityInWorld(new EntityRocket(par2World,
					par3Entity, par1ItemStack));
			AmmoManager.consumeAmmo(par3Entity, this, 1);
		}
	}

	@Override
	public Boolean canShoot(EntityPlayer player, ItemStack is) {
		return AmmoManager.hasAmmo(this, player)
				|| player.capabilities.isCreativeMode;
	}

	@Override
	public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer, int par4) {
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer,
				par4);
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
	public String getSoundShoot(int mode) {
		return "cbc.weapons.rocketfire";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "";
	}

	@Override
	public int getShootTime(int mode) {
		return 40;
	}

	@Override
	public double getPushForce(int mode) {
		return 0;
	}

	@Override
	public int getDamage(int mode) {
		return 0;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}

	@Override
	public String getModeDescription(int mode) {
		return (mode == 0) ? "mode.rpg" : "mode.rpg1";
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

		double uniqueID = Math.random() * 65535D;
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

}
