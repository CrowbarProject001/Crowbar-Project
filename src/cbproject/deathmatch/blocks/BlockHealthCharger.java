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

import cbproject.api.tile.IUseable;
import cbproject.core.CBCMod;
import cbproject.core.block.CBCBlockContainer;
import cbproject.core.keys.KeyUse;
import cbproject.core.props.ClientProps;
import cbproject.core.props.GeneralProps;
import cbproject.deathmatch.blocks.tileentities.TileEntityHealthCharger;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author Administrator
 *
 */
public class BlockHealthCharger extends CBCBlockContainer implements IUseable {

	protected final float WIDTH = 0.3F, HEIGHT = 0.4F, LENGTH = 0.08F;
	
	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockHealthCharger(int par1) {
		super(par1, Material.rock);
		this.setUnlocalizedName("healthcharger");
		this.setIconName("health");
		this.setGuiId(GeneralProps.GUI_ID_HEALTH);
		setCreativeTab(CBCMod.cct);
	}

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityHealthCharger();
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public int getRenderType() {
		return ClientProps.RENDER_TYPE_EMPTY;
	}
	
    /**
     * Lets the block know when one of its neighbor changes. Doesn't know which neighbor changed (coordinates passed are
     * their own) Args: x, y, z, neighbor blockID
     */
	@Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5)
    {
		TileEntityHealthCharger te = (TileEntityHealthCharger) par1World.getBlockTileEntity(par2, par3, par4);
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
            player.openGui(CBCMod.instance, GeneralProps.GUI_ID_HEALTH, world, x, y, z);
            return true;
    }
    
    @Override
    public void breakBlock(World world, int x, int y, int z, int par5, int par6) {
        TileEntity tileEntity = world.getBlockTileEntity(x, y, z);
        if (!(tileEntity instanceof TileEntityHealthCharger)) {
        	super.breakBlock(world, x, y, z, par5, par6);
                return;
        }
        TileEntityHealthCharger inventory = (TileEntityHealthCharger) tileEntity;
        dropItems(world, x, y, z, inventory.slots);
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
        if (var5 == 5) //X+
        {
            this.setBlockBounds(0.0F, 0.5F - var6, 0.5F - var7, LENGTH * 2.0F, 0.5F + var6, 0.5F + var7); // (0, 0.5) (0.3, 0.7), (0.2, 0.8)
        }
        else if (var5 == 4) //X-
        {
            this.setBlockBounds(1.0F - LENGTH * 2.0F, 0.5F - var6, 0.5F - var7, 1.0F, 0.5F + var6, 0.5F + var7);
        }
        else if (var5 == 3) //Z+
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
            var10 = 2;
        }

        if (par5 == 3 && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, EAST, true))
        {
            var10 = 3;
        }

        if (par5 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3,  par4, NORTH, true))
        {
            var10 = 4;
        }

        if (par5 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, SOUTH, true))
        {
            var10 = 5;
        }
		
        return var10;
    }

	@Override
	public void onBlockUse(World world, EntityPlayer player, int bx,
			int by, int bz) {
		
		TileEntity te =  world.getBlockTileEntity(bx, by, bz);
		if(te == null)
			return;
		TileEntityHealthCharger te2 = (TileEntityHealthCharger) te;
		String path = te2.currentEnergy > 0 ? "cbc.entities.medshot" : "cbc.entities.medshotno";
		world.playSoundAtEntity(player, path, 0.5F, 1.0F);
		KeyUse.setBlockInUse(player, bx, by, bz);
		if(te2.currentEnergy > 0)
			te2.startUsing(player);
		
	}

	@Override
	public void onBlockStopUsing(World world, EntityPlayer player, int bx,
			int by, int bz) {
		TileEntity te =  world.getBlockTileEntity(bx, by, bz);
		if(te == null)
			return;
		TileEntityHealthCharger te2 = (TileEntityHealthCharger) te;
		te2.stopUsing(player);
	}

}