/**
 * 
 */
package cbproject.deathmatch.blocks.weapons;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.core.CBCMod;
import cbproject.core.misc.CBCBlocks;
import cbproject.core.props.ClientProps;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.register.DMBlocks;
import cbproject.deathmatch.utils.BulletManager;


import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * Tripmine Weapon block.
 * @author WeAthFolD
 */
public class BlockTripmine extends BlockContainer {
	
	public static final int RAY_RANGE = 30;
	public static final int NOTIFY_ID = 4098;
	
	public BlockTripmine(int par1) {
		
		super(par1, Material.circuits);
		setCreativeTab(CBCMod.cct);
		setUnlocalizedName("blockTripmine");
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("lambdacraft:blockTripmine");
    }
	
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
    	
    	 int var6 = par1World.getBlockMetadata(par2, par3, par4);
    	 int var7 = var6 & 3;
    	 Boolean var8 = false;
    	 //Check if the block still could exist
         if (!par1World.isBlockSolidOnSide(par2 - 1, par3, par4, SOUTH) && var7 == 3)
         {
             var8 = true;
         }

         if (!par1World.isBlockSolidOnSide(par2 + 1, par3, par4, NORTH) && var7 == 1)
         {
             var8 = true;
         }

         if (!par1World.isBlockSolidOnSide(par2, par3, par4 - 1, EAST) && var7 == 0)
         {
             var8 = true;
         }

         if (!par1World.isBlockSolidOnSide(par2, par3, par4 + 1, WEST) && var7 == 2)
         {
             var8 = true;
         }
         if (var8)
         {
             par1World.setBlockToAir(par2, par3, par4);
             return;
         }
         
    	if(par5 == NOTIFY_ID)
    		this.breakBlock(par1World, par2, par3, par4, 0, 0);   	
    	
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {    	
    	int var10 = par1World.getBlockMetadata(par2, par3, par4) & 3;
    	int i = (var10 == 3 || var10 == 1) ? par2: par4;
    	
    	//Retrieve the ray blocks
    	for(int j = 0; j <BlockTripmine.RAY_RANGE; i = (var10 == 0 || var10 == 3)? i+1 : i-1, j++){ 
    		int id = 0;
			if(var10 == 1 || var10 == 3){ //x+, x-鏂瑰悜
				id = par1World.getBlockId(i, par3, par4);
				if(id == DMBlocks.blockTripmineRay.blockID)
					par1World.setBlock(i, par3, par4, 0);
			} else { //z+, z-鏂瑰悜
				id = par1World.getBlockId(par2, par3, i);
				if(id == DMBlocks.blockTripmineRay.blockID)
					par1World.setBlock(par2, par3, i, 0);
			}
		}
    	
    	BulletManager.Explode(par1World, null, 0.5F, 4.0F, par2, par3, par4, 47);
    	par1World.setBlockToAir(par2, par3, par4);
    	super.breakBlock(par1World, par2, par3, par4, par5, par6);
    	
    }
    
    @Override
	public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    @Override
	public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
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
        float var6 = 0.15F;
        float var7 =  0.3F;
        if (var5 == 3) //X+
        {
            this.setBlockBounds(0.0F, 0.35F, 0.5F - var7, var6 * 2.0F, 0.65F, 0.5F + var7); // (0, 0.5) (0.3, 0.7), (0.2, 0.8)
        }
        else if (var5 == 1) //X-
        {
            this.setBlockBounds(1.0F - var6 * 2.0F, 0.3F, 0.5F - var7, 1.0F, 0.7F, 0.5F + var7);
        }
        else if (var5 == 0) //Z+
        {
            this.setBlockBounds(0.5F - var7, 0.3F, 0.0F, 0.5F + var7, 0.7F, var6 * 2.0F);
        }
        else if (var5 == 2) //Z-
        {
            this.setBlockBounds(0.5F - var7, 0.3F, 1.0F - var6 * 2.0F, 0.5F + var7, 0.7F, 1.0F);
        }
        
    }
	
    @Override
	public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        byte var10 = 0;

        if (par5 == 2 && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, WEST, true)) // Z-鏂瑰悜
        {
            var10 = 2;
        }

        if (par5 == 3 && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, EAST, true)) // Z+鏂瑰悜
        {
            var10 = 0;
        }

        if (par5 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3,  par4, NORTH, true)) // X-鏂瑰悜
        {
            var10 = 1;
        }

        if (par5 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, SOUTH, true)) // X+鏂瑰悜
        {
            var10 = 3;
        }
        
        int i = (var10 == 1 || var10 == 3)? par2 : par4;
        if(var10 == 0 || var10 == 3)i++;
        else i--;
        
		Boolean var0 = true;
		
		System.out.println("Playced meta: " + var10);
		int id;
		for(int j = 0; var0 && j <RAY_RANGE ; i = (var10 == 0 || var10 == 3)? i+1 : i-1, j++){
			if(var10 == 1 || var10 == 3){
				id = par1World.getBlockId(i, par3, par4);
				if(id == 0 || id == Block.snow.blockID){
				par1World.setBlock(i, par3, par4, DMBlocks.blockTripmineRay.blockID, var10, 0x04);
				} else var0 = false;
			} else {
				id = par1World.getBlockId(par2, par3, i);
				if(id == 0 || id == Block.snow.blockID){
					par1World.setBlock(par2, par3, i, DMBlocks.blockTripmineRay.blockID, var10, 0x04);
				} else var0 = false;
			}
		}
		
        return var10;
    }
    
    
    @Override
    public void onPostBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {}
    
	@Override
	public int getRenderType() {
		return ClientProps.RENDER_TYPE_EMPTY;
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
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTripmine();
	}


	 
}
