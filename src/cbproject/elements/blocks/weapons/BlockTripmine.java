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
import net.minecraft.block.BlockTripWireSource;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * CBC Mod Tripmine Weapon
 */

public class BlockTripmine extends Block {
	
	public static final int RAY_RANGE = 30;
	
	public BlockTripmine(int par1) {
		
		super(par1, 38, Material.circuits);
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setCreativeTab(CBCMod.cct);
		setBlockName("tripmine");
		
	}
	

    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
    	if(par5 == CBCMod.cbcBlocks.blockTripmineRay.blockID)
    		Explode(par1World, par2, par3, par4);
    }
    
    private void Explode(World worldObj, int posX, int posY, int posZ){
		
		float var1=2.5F; //手雷的0.25倍
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
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)
    {

        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 3;
        float var6 = 0.15F;
        float var7 =  0.3F;
        if (var5 == 3) //X+
        {
            this.setBlockBounds(0.0F, 0.3F, 0.5F - var7, var6 * 2.0F, 0.7F, 0.5F + var7); // (0, 0.5) (0.3, 0.7), (0.2, 0.8)
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

        if (par5 == 4 && par1World.isBlockSolidOnSide(par2 + 1, par3, par4, NORTH, true)) // X-方向
        {
            var10 = 1;
        }

        if (par5 == 5 && par1World.isBlockSolidOnSide(par2 - 1, par3, par4, SOUTH, true)) // X+方向
        {
            var10 = 3;
        }
        
        int i = (var10 == 1 || var10 == 3)? par2 : par4;
		Boolean var0 = true;
		
		for(; var0 ; i = (var10 == 0 || var10 == 3)? i+1 : i-1){
			if(var10 == 1 || var10 == 3){
				if(par1World.getBlockId(i, par3, par4) == 0){
				par1World.setBlockAndMetadata(i, par3, par4, CBCMod.cbcBlocks.blockTripmineRay.blockID, par9);
				} else var0 = false;
			} else {
				if(par1World.getBlockId(par2, par3, i) == 0){
					par1World.setBlockAndMetadata(par2, par3, i, CBCMod.cbcBlocks.blockTripmineRay.blockID, par9);
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
