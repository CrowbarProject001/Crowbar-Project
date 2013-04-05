/**
 * 
 */
package cbproject.elements.blocks.weapons;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;

import java.util.List;
import java.util.Random;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.MotionXYZ;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockTripWireSource;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author WeAthFolD
 * CBC Mod Tripmine Weapon
 */

public class BlockTripmine extends Block {
	
	public static final int RAY_RANGE = 30;
	public static final int NOTIFY_ID = 4098;
	
	public BlockTripmine(int par1) {
		
		super(par1, 65, Material.circuits);
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setCreativeTab(CBCMod.cct);
		setBlockName("tripmine");
		
	}
	

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
    	
    	 int var6 = par1World.getBlockMetadata(par2, par3, par4);
    	 int var7 = var6 & 3;
    	 Boolean var8 = false;
    	 //判断能否继续存在
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
             this.dropBlockAsItem(par1World, par2, par3, par4, par5, 0);
             par1World.setBlockWithNotify(par2, par3, par4, 0);
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
    	
    	//收回光束方块
    	for(int j = 0; j <BlockTripmine.RAY_RANGE; i = (var10 == 0 || var10 == 3)? i+1 : i-1, j++){ 
    		int id = 0;
			if(var10 == 1 || var10 == 3){ //x+, x-方向
				id = par1World.getBlockId(i, par3, par4);
				if(id == CBCMod.cbcBlocks.blockTripmineRay.blockID)
					par1World.setBlock(i, par3, par4, 0);
			} else { //z+, z-方向
				id = par1World.getBlockId(par2, par3, i);
				if(id == CBCMod.cbcBlocks.blockTripmineRay.blockID)
					par1World.setBlock(par2, par3, i, 0);
			}
		}
    	
    	Explode(par1World, par2, par3, par4);
    	par1World.setBlockWithNotify(par2, par3, par4, 0);
    	super.breakBlock(par1World, par2, par3, par4, par5, par6);
    	
    }
    
    public int quantityDropped(Random par1Random)
    {
        return 0;
    }

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return 0;
    }

    private void Explode(World worldObj, int posX, int posY, int posZ){
    	
		float var1=1.5F; //手雷的0.25倍
		double dmg = 20.0F;
	    for (int var3 = 0; var3 < 8; ++var3)
	    {
	            worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0D, 0.0D, 0.0D);
	    }    
	    
		worldObj.createExplosion(null, posX, posY, posZ, var1, true);
		
		AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(posX-4, posY-4, posZ-4, posX+4, posY+4, posZ+4);
		List entitylist = worldObj.getEntitiesWithinAABBExcludingEntity(null, par2);
		if(entitylist.size() > 0){
			for(int i=0;i<entitylist.size();i++){
				Entity ent = (Entity)entitylist.get(i);
				if(!ent.isEntityInvulnerable() && ent instanceof EntityLiving){
					int damage = 
							(int) (Math.pow(
						( Math.pow(ent.posX-posX,2) + 
					      Math.pow(ent.posY-posY,2) + 
					      Math.pow(ent.posZ-posZ,2) ) , 0.5) 
					     /4 * dmg) ;
					
					if( ent instanceof EntityPlayer && ((EntityPlayer)ent).capabilities.isCreativeMode)
						return;
					
					ent.attackEntityFrom(DamageSource.explosion2, damage);
					ent.setFire(20);
				}
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
	
    public int onBlockPlaced(World par1World, int par2, int par3, int par4, int par5, float par6, float par7, float par8, int par9)
    {
        byte var10 = 0;

        if (par5 == 2 && par1World.isBlockSolidOnSide(par2, par3, par4 + 1, WEST, true)) // Z-方向
        {
            var10 = 2;
        }

        if (par5 == 3 && par1World.isBlockSolidOnSide(par2, par3, par4 - 1, EAST, true)) // Z+方向
        {
            var10 = 0;
        }

        if (par5 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3,  par4, NORTH, true)) // X-方向
        {
            var10 = 1;
        }

        if (par5 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, SOUTH, true)) // X+方向
        {
            var10 = 3;
        }
        
        int i = (var10 == 1 || var10 == 3)? par2 : par4;
        if(var10 == 0 || var10 == 3)i++;
        else i--;
        
		Boolean var0 = true;
		
		System.out.println("Playced meta: " + var10);
		for(int j = 0; var0 && j <RAY_RANGE ; i = (var10 == 0 || var10 == 3)? i+1 : i-1, j++){
			if(var10 == 1 || var10 == 3){
				if(par1World.getBlockId(i, par3, par4) == 0){
				par1World.setBlockAndMetadataWithUpdate(i, par3, par4, CBCMod.cbcBlocks.blockTripmineRay.blockID, var10, true);
				} else var0 = false;
			} else {
				if(par1World.getBlockId(par2, par3, i) == 0){
					par1World.setBlockAndMetadataWithUpdate(par2, par3, i, CBCMod.cbcBlocks.blockTripmineRay.blockID, var10, true);
				} else var0 = false;
			}
		}
		
        return var10;
    }
    
    
    @Override
    public void onPostBlockPlaced(World par1World, int par2, int par3, int par4, int par5) {}
    
	@Override
	public int getRenderType() {
		return CBCMod.RENDER_TYPE_TRIPMINE;
	}

	public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
	 {
	     return null;
	 }

	 public boolean isOpaqueCube()
	 {
		 return false;
     }

	 public boolean renderAsNormalBlock()
	 {
	     return false;
	 }


	 
}
