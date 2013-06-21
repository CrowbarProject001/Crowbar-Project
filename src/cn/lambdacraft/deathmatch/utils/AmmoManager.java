package cn.lambdacraft.deathmatch.utils;

import cn.lambdacraft.api.weapon.WeaponGeneral;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.item.*;

public class AmmoManager {

	/**
	 * Tries to consume a specific amount of ammo in player's inventory.
	 * 
	 * @return how many are left to be consumed
	 */
	public static int consumeAmmo(EntityPlayer player, WeaponGeneral item,
			int amount) {

		return tryConsume(player, item.ammoID, amount);

	}

	/**
	 * Tries to consume one specific item in player's inventory.
	 * 
	 * @return how many are left not consumed
	 */
	public static int tryConsume(EntityPlayer player, int itemID, int amount) {

		int left = amount;
		ItemStack is;
		if (Item.itemsList[itemID].getItemStackLimit() > 1) {

			for (int i = 0; i < player.inventory.mainInventory.length; i++) {
				is = player.inventory.mainInventory[i];
				if (is != null && is.itemID == itemID) {
					if (is.stackSize > left) {
						player.inventory.decrStackSize(i, left);
						return 0;
					} else {
						left -= is.stackSize;
						player.inventory.decrStackSize(i, is.stackSize);
					}
				}
			}
			return left;

		} else {

			for (int i = 0; i < player.inventory.mainInventory.length; i++) {
				is = player.inventory.mainInventory[i];
				int stackCap;
				if (is != null && is.itemID == itemID) {
					stackCap = is.getMaxDamage() - is.getItemDamage() - 1;
					if (stackCap > left) {
						is.damageItem(left, player);
						return 0;
					} else {
						left -= stackCap;
						is.setItemDamage(is.getMaxDamage() - 1);
					}
				}
			}
			return left;

		}
	}

	/**
	 * determine if player have any ammo for reloading/energy weapon shooting.
	 */
	public static boolean hasAmmo(WeaponGeneral is, EntityPlayer player) {
		for (ItemStack i : player.inventory.mainInventory) {
			if (i == null)
				continue;
			if (i.itemID == is.ammoID) {
				if (i.isStackable())
					return true;
				else if (i.getItemDamage() < i.getMaxDamage() - 1)
					return true;
			}
		}
		return false;
	}
	
	public static int getAmmoCapacity(int itemID, InventoryPlayer inv) {
		int cnt = 0;
		for(ItemStack s : inv.mainInventory) {
			if(s != null && s.itemID == itemID) {
				cnt += s.getMaxStackSize() == 1 ? s.getMaxDamage() - s.getItemDamage() - 1 : s.stackSize;
			}
		}
		return cnt;
	}

	public static boolean hasAmmo(int itemID, EntityPlayer player) {
		return player.inventory.hasItem(itemID);
	}

	public static int consumeInventoryItem(ItemStack[] inv, int itemID,
			int count) {
		int left = count;
		ItemStack is;
		if (Item.itemsList[itemID].getItemStackLimit() > 1) {

			for (int i = 0; i < inv.length; i++) {
				is = inv[i];
				if (is != null && is.itemID == itemID) {
					if (is.stackSize > left) {
						inv[i].splitStack(left);
						return 0;
					} else {
						left -= is.stackSize;
						inv[i] = null;
					}
				}
			}
			return left;

		} else
			return left;
	}

	public static int consumeInventoryItem(ItemStack[] inv, int itemID,
			int count, int startFrom) {
		int left = count;
		ItemStack is;
		if (Item.itemsList[itemID].getItemStackLimit() > 1) {

			for (int i = startFrom; i < inv.length; i++) {
				is = inv[i];
				if (is != null && is.itemID == itemID) {
					if (is.stackSize > left) {
						inv[i].splitStack(left);
						return 0;
					} else {
						left -= is.stackSize;
						inv[i] = null;
					}
				}
			}
			return left;

		} else
			return left;
	}

}
