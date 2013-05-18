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
package cbproject.deathmatch.blocks;

import java.util.Random;

import cbproject.core.CBCMod;
import cbproject.crafting.blocks.TileEntityWeaponCrafter;
import cbproject.deathmatch.blocks.tileentities.TileEntityArmorCharger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityFurnace;
import net.minecraft.util.Icon;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * @author Administrator
 *
 */
public class BlockArmorCharger extends BlockContainer {

	public Icon iconSide, iconTop, iconBottom, iconMain;
	
	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockArmorCharger(int par1) {
		super(par1, Material.rock);
		this.setUnlocalizedName("armorcharger");
		setCreativeTab(CBCMod.cct);
	}
	
    @Override
	public void registerIcons(IconRegister par1IconRegister)
    {
        iconSide = par1IconRegister.registerIcon("lambdacraft:ac_side");
        iconTop = par1IconRegister.registerIcon("lambdacraft:ac_top");
        iconBottom = par1IconRegister.registerIcon("lambdacraft:ac_bottom");
        iconMain = par1IconRegister.registerIcon("lambdacraft:ac_main");
        blockIcon = iconTop;
    }
	
    @SideOnly(Side.CLIENT)
    @Override
    public Icon getIcon(int par1, int par2)
    {
        if(par1 < 1)
        	return iconBottom;
        if(par1 < 2)
        	return iconTop;
        if(par1 == par2)
        	return iconMain;
        return iconSide;
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityArmorCharger();
	}
	
    @Override
    public boolean onBlockActivated(World world, int x, int y, int z,
                    EntityPlayer player, int idk, float what, float these, float are) {
            TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
            if (tileEntity == null || player.isSneaking()) {
                    return false;
            }
            player.openGui(CBCMod.instance, 0, world, x, y, z);
            return true;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
            dropItems(world, x, y, z);
            super.breakBlock(world, x, y, z, par5, par6);
    }
    
    private void dropItems(World world, int x, int y, int z){
        Random rand = new Random();

        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (!(tileEntity instanceof IInventory)) {
                return;
        }
        TileEntityArmorCharger inventory = (TileEntityArmorCharger) tileEntity;

        for (ItemStack item : inventory.slots) {

                if (item != null && item.stackSize > 0) {
                        float rx = rand.nextFloat() * 0.8F + 0.1F;
                        float ry = rand.nextFloat() * 0.8F + 0.1F;
                        float rz = rand.nextFloat() * 0.8F + 0.1F;

                        EntityItem entityItem = new EntityItem(world,
                                        x + rx, y + ry, z + rz,
                                        new ItemStack(item.itemID, item.stackSize, item.getItemDamage()));

                        if (item.hasTagCompound()) {
                                entityItem.getEntityItem().setTagCompound((NBTTagCompound) item.getTagCompound().copy());
                        }

                        float factor = 0.05F;
                        entityItem.motionX = rand.nextGaussian() * factor;
                        entityItem.motionY = rand.nextGaussian() * factor + 0.2F;
                        entityItem.motionZ = rand.nextGaussian() * factor;
                        world.spawnEntityInWorld(entityItem);
                        item.stackSize = 0;
                }
        }
}

/**
 * Called when the block is placed in the world.
 */
@Override
public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLiving par5EntityLiving, ItemStack par6ItemStack)
{
    int l = MathHelper.floor_double((double)(par5EntityLiving.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;

    if (l == 0)
    {
        par1World.setBlockMetadataWithNotify(par2, par3, par4, 2, 2);
    }

    if (l == 1)
    {
        par1World.setBlockMetadataWithNotify(par2, par3, par4, 5, 2);
    }

    if (l == 2)
    {
        par1World.setBlockMetadataWithNotify(par2, par3, par4, 3, 2);
    }

    if (l == 3)
    {
        par1World.setBlockMetadataWithNotify(par2, par3, par4, 4, 2);
    }

    if (par6ItemStack.hasDisplayName())
    {
        ((TileEntityFurnace)par1World.getBlockTileEntity(par2, par3, par4)).func_94129_a(par6ItemStack.getDisplayName());
    }
}

}
