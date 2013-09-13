package cn.lambdacraft.mob.item;

import cn.lambdacraft.core.CBCMod;
import cn.lambdacraft.core.item.CBCGenericItem;
import cn.lambdacraft.mob.entity.EntitySnark;
import cn.lambdacraft.mob.util.MobHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class LCMobSpawner extends CBCGenericItem {

	protected Class<? extends Entity> entClass = EntitySnark.class;
	
	public LCMobSpawner(int par1) {
		super(par1);
		this.setCreativeTab(CBCMod.cct);
		setIAndU("snark");
	}
	
	public LCMobSpawner(int id, Class<? extends Entity> entityClass, String name) {
		super(id);
		setCreativeTab(CBCMod.cct);
		entClass = entityClass;
		setIAndU(name);
	}

	/* (non-Javadoc)
	 * @see net.minecraft.item.Item#onItemUse(net.minecraft.item.ItemStack, net.minecraft.entity.player.EntityPlayer, net.minecraft.world.World, int, int, int, int, float, float, float)
	 */
	@Override
	public boolean onItemUse(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer, World par3World, int par4, int par5, int par6, int par7, float par8, float par9, float par10) {
		// TODO Auto-generated method stub
		if (par3World.isRemote)
			return false;

//		MobSpawnHandler.spawnCreature(par2World, entClass,
//				par3EntityPlayer);
		if(!par3World.isRemote)
			MobHelper.spawnCreature(par3World, par2EntityPlayer, entClass, par4 + 0.5d, par2EntityPlayer.posY, par6 + 0.5d);
		if (!(par2EntityPlayer.capabilities.isCreativeMode)) {
			par1ItemStack.stackSize--;
		}
		par2EntityPlayer.setItemInUse(par1ItemStack,
				this.getMaxItemUseDuration(par1ItemStack));
		return true;
	}
	
	/**
	 * How long it takes to use or consume an item
	 */
	@Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack) {
		return 10;
	}
}
