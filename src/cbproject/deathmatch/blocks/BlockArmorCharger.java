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

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author Administrator
 *
 */
public class BlockArmorCharger extends BlockContainer {

	public Icon iconSide, iconTop, iconBottom, iconMain;
	protected final float WIDTH = 0.3F, HEIGHT = 0.4F, LENGTH = 0.1F;
	
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
        return iconMain;
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityArmorCharger();
	}
	
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
		TileEntityArmorCharger te = (TileEntityArmorCharger) par1World.getBlockTileEntity(par2, par3, par4);
        if (par1World.isBlockIndirectlyGettingPowered(par2, par3, par4))
        {
            te.isRSActivated = true;
        } else {
        	te.isRSActivated = false;
        }
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
    
	@Override
	public boolean isOpaqueCube()
	 {
		 return false;
     }

	@Override
	public boolean renderAsNormalBlock()
	 {
	     return false;
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

    @Override
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
        ForgeDirection dir = ForgeDirection.getOrientation(par5);
        return (dir == NORTH && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, NORTH)) ||
               (dir == SOUTH && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, SOUTH)) ||
               (dir == WEST  && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, WEST )) ||
               (dir == EAST  && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, EAST ));
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {
    	
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float var6 = HEIGHT;
        float var7 =  WIDTH;
        if (var5 == 3) //X+
        {
            this.setBlockBounds(0.0F, 0.5F - var6, 0.5F - var7, LENGTH * 2.0F, 0.5F + var6, 0.5F + var7); // (0, 0.5) (0.3, 0.7), (0.2, 0.8)
        }
        else if (var5 == 1) //X-
        {
            this.setBlockBounds(1.0F - LENGTH * 2.0F, 0.5F - var6, 0.5F - var7, 1.0F, 0.5F + var6, 0.5F + var7);
        }
        else if (var5 == 0) //Z+
        {
            this.setBlockBounds(0.5F - var7, 0.5F - var6, 0.0F, 0.5F + var7, 0.5F + var6, LENGTH * 2.0F);
        }
        else if (var5 == 2) //Z-
        {
            this.setBlockBounds(0.5F - var7, 0.5F - var6, 1.0F - LENGTH * 2.0F, 0.5F + var7, 0.5F + var6, 1.0F);
        }
        
    }
	
    @Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        byte var10 = 0;

        if (par5 == 2 && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, WEST, true))
        {
            var10 = 3;
        }

        if (par5 == 3 && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, EAST, true))
        {
            var10 = 1;
        }

        if (par5 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3,  par4, NORTH, true))
        {
            var10 = 0;
        }

        if (par5 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, SOUTH, true))
        {
            var10 = 2;
        }
		
        return var10;
    }

}
