package cn.lambdacraft.mob.items;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.mob.entities.EntitySnark;
import cn.lambdacraft.mob.utils.MobSpawnHandler;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_snark extends CBCGenericItem {

	public Weapon_snark(int par1) {
		super(par1);
		this.setCreativeTab(CBCMod.cct);
		setUnlocalizedName("snark");
		setIconName("snark");
	}

	/**
	 * Called whenever this item is equipped and the right mouse button is
	 * pressed. Args: itemStack, world, entityPlayer
	 */
	@Override
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World,
			EntityPlayer par3EntityPlayer) {
		if (par2World.isRemote)
			return par1ItemStack;

		MobSpawnHandler.spawnCreature(par2World, EntitySnark.class,
				par3EntityPlayer);
		if (!(par3EntityPlayer.capabilities.isCreativeMode)) {
			par1ItemStack.stackSize--;
		}
		par3EntityPlayer.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
	}

	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 10;
	}
}
