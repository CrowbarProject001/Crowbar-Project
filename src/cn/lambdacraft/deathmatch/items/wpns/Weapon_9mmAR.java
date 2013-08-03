package cn.lambdacraft.deathmatch.items.wpns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.crafting.register.CBCItems;
import cn.lambdacraft.deathmatch.entities.EntityARGrenade;
import cn.lambdacraft.deathmatch.utils.AmmoManager;
import cn.lambdacraft.deathmatch.utils.ItemHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

/**
 * 9mm Assault Rifle class. Mode I : Bullet, II : AR Grenade.
 * 
 * @author WeAthFolD
 * 
 */
public class Weapon_9mmAR extends WeaponGeneralBullet {

	public Weapon_9mmAR(int par1) {

		super(par1, CBCItems.ammo_9mm2.itemID);
		setIAndU("weapon_9mmar");
		setCreativeTab(CBCMod.cct);
		setMaxDamage(51);

		setReloadTime(60);
		setJamTime(10);
		setLiftProps(8, 2);
	}

	@Override
	public boolean canShoot(EntityPlayer player, ItemStack is) {
		InformationBullet inf = this.getInformation(is, player.worldObj);
		boolean side = getUsingSide(is);
		return side ? super.canShoot(player, is) : (player.capabilities.isCreativeMode || AmmoManager.hasAmmo(CBCItems.ammo_argrenade.itemID, player));
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4,
				par5);
	}

	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3Entity, InformationBullet information, boolean left) {

		if (left) {
			super.onBulletWpnShoot(par1ItemStack, par2World, par3Entity, information, left);
		} else {
			if (par3Entity.capabilities.isCreativeMode || AmmoManager.tryConsume(par3Entity,
					CBCItems.ammo_argrenade.itemID, 1) == 0) {
				if(!par2World.isRemote) {
					par2World.spawnEntityInWorld(new EntityARGrenade(par2World, par3Entity));
					par2World.playSoundAtEntity(par3Entity,
							getSoundShoot(left), 0.5F, 1.0F);
				}
			}	
		}
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
	public String getSoundShoot(boolean left) {
		return left ? "cbc.weapons.hksa"
				: (itemRand.nextFloat() * 2 > 1 ? "cbc.weapons.glauncher"
						: "cbc.weapons.glauncherb");
	}

	@Override
	public String getSoundJam(boolean left) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(boolean left) {
		return "cbc.weapons.nmmarr";
	}

	@Override
	public int getShootTime(boolean left) {
		return left ? 4 : 20;
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
				return (itemStack.getMaxDamage() - itemStack.getItemDamage() - 1) + "|" + AmmoManager.getAmmoCapacity(ammoID, player.inventory);
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
				return String.valueOf(AmmoManager.getAmmoCapacity(CBCItems.ammo_argrenade.itemID, player.inventory));
			}

			@Override
			public int getTextureSheet(ItemStack itemStack) {
				return itemStack.getItemSpriteNumber();
			}
			
		};
		return tips;
	}
}
