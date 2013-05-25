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
package cbproject.deathmatch.items;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Facing;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import cbproject.api.energy.item.ICustomEnItem;
import cbproject.core.CBCMod;
import cbproject.core.item.ElectricItem;
import cbproject.deathmatch.entities.EntityBattery;
import cbproject.deathmatch.entities.EntityProjectile;

/**
 * @author Administrator
 *
 */
public class ItemBattery extends ElectricItem {
	
	public ItemBattery(int par1) {
		super(par1);
		setUnlocalizedName("hevbattery");
		this.setIconName("battery");
		this.tier = 2;
		this.transferLimit = 128;
		this.maxCharge = EntityBattery.EU_PER_BATTERY;
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	
	
    @Override
	public boolean onItemUse(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4, int par5,
			int par6, int par7, float par8, float par9, float par10) {
   /* 	if(!par3World.isRemote){
    		EntityBattery ent = new EntityBattery(par3World, par2EntityPlayer, EntityBattery.EU_PER_BATTERY - par1ItemStack.getItemDamage());
    		par3World.spawnEntityInWorld(ent);
    		par1ItemStack.splitStack(1);
    	}
    */
    	return placeTheEntity(par1ItemStack, par2EntityPlayer, par3World, par4, par5, par6, par7);
    	//return true;
	}
    	
    /**
     * 放置指定的Entity
     * 
     * @param par1ItemStack
     * @param par2EntityPlayer
     * @param par3World
     * @param par4x
     * @param par5y
     * @param par6z
     * @param par7Side
     * @return true/false 233 大概无用吧
     */
	private boolean placeTheEntity(ItemStack par1ItemStack,
			EntityPlayer par2EntityPlayer, World par3World, int par4x, int par5y,
			int par6z, int par7Side) {
		if (par3World.isRemote)
			return true; //Remote判定
		int theBlockId = par3World.getBlockId(par4x, par5y, par6z); //玩家面对的方块的ID
		//par4x += Facing.offsetsXForSide[par7Side]; //给出这个轴在那一面得到所要求的方块的偏移量。
		//par5y += Facing.offsetsYForSide[par7Side];
		//par6z += Facing.offsetsZForSide[par7Side];
		par4x++;
		par5y++;
		par6z++;
		double d0 = 0.0D;
		if (par7Side == 1 && Block.blocksList[theBlockId] != null
				&& Block.blocksList[theBlockId].getRenderType() == 11) {
			d0 = 0.5D;
		}

		Entity entity = spawnTheBattery(par1ItemStack, par3World, par1ItemStack.getItemDamage(),
				(double) par4x + 0.5D, (double) par5y + d0, (double) par6z + 0.5D);

			if (entity != null && !par2EntityPlayer.capabilities.isCreativeMode) {
				--par1ItemStack.stackSize;
			}
		return true;
	}

    public static Entity spawnTheBattery(ItemStack parFu1ItemStack,World par0World, int par1, double par2, double par4, double par6)
    {
            Entity entity = null;
            for (int j = 0; j < 1; ++j)
            {
                entity = new EntityBattery(par0World, EntityBattery.EU_PER_BATTERY - parFu1ItemStack.getItemDamage());

                if (entity != null && entity instanceof EntityProjectile)
                {
                	EntityProjectile entityProjectile = (EntityProjectile)entity;
                    entity.setPosition(par2, par4, par6); //Change the position
                    par0World.spawnEntityInWorld(entity); //Spawn it
                }
            }
            return entity;	
    }
    
	public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
   // 	if(!par2World.isRemote){
   // 		EntityBattery ent = new EntityBattery(par2World, par3EntityPlayer, EntityBattery.EU_PER_BATTERY - par1ItemStack.getItemDamage());
    //		par2World.spawnEntityInWorld(ent);
    //		par1ItemStack.splitStack(1);}
        return par1ItemStack;
    }
	
	@Override
	public boolean canUse(ItemStack itemStack, int amount) {
		return itemStack.getMaxDamage() - itemStack.getItemDamage() > 0;
	}

	@Override
	public boolean canShowChargeToolTip(ItemStack itemStack) {
		return true;
	}

}
