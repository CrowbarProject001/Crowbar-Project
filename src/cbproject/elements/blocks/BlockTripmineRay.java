package cbproject.elements.blocks;

import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.WEST;
import cbproject.CBCMod;
import cbproject.elements.blocks.weapons.BlockTripmine;
import cbproject.proxy.ClientProxy;
import cbproject.utils.BlockPos;
import net.minecraft.block.Block;
import net.minecraft.block.BlockTripWire;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.Direction;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class BlockTripmineRay extends BlockTripWire {

	public BlockTripmineRay(int par1) {
		super(par1);
		setTextureFile(ClientProxy.TRIPMINE_RAY_PATH);
	}
	
    public int getRenderType()
    {
        return CBCMod.RENDER_TYPE_TRIPMINE_RAY;
    }
    
    public int getRenderBlockPass()
    {
        return 1;
    }

    public int tickRate()
    {
        return 10;
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
    
    public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return 0;
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	BlockPos source = getSource(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4) & 3);
    	if(source == null)
    		return;
    	par1World.notifyBlockChange(source.x, source.y, source.z, this.blockID);
        return;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
        if (!par1World.isRemote)
        {
            if ((par1World.getBlockMetadata(par2, par3, par4) & 1) != 1)
            {
            	BlockPos source = getSource(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4) & 3);
            	if(source == null)
            		return;
            	par1World.notifyBlockChange(source.x, source.y, source.z, this.blockID);
            }
        }
    }
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)  {
    	
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4) & 3;
        float h = 0.1F, w = 0.2F;
        if (var5 == 3 || var5 == 1) //X+, X-
        {
            this.setBlockBounds(0.0F, 0.5F - h, 0.5F - w, 1.0F, 0.5F + h, 0.5F + w); // (0, 1) (0.4, 0.6), (0.3, 0.7)
        } else {
            this.setBlockBounds(0.5F - w, 0.5F - h, 0.0F, 0.5F + w, 0.5F + h, 1.0F);
        }
        
        return;
    }
    
    public BlockPos getSource(World worldObj, int x, int y, int z, int var10){
    	
    	BlockPos blockPos = null;
		for(int i = (var10 == 1 || var10 == 3)? x : z; i < BlockTripmine.RAY_RANGE; i = (var10 == 0 || var10 == 3)? i-1 : i + 1){
			if(var10 == 1 || var10 == 3){
				if(worldObj.getBlockId(i, y, z) == CBCMod.cbcBlocks.blockTripmine.blockID){
					blockPos = new BlockPos(i, y, z, CBCMod.cbcBlocks.blockTripmine.blockID);
					return blockPos;
				} 
			} else {
				if(worldObj.getBlockId(x, y, i) == CBCMod.cbcBlocks.blockTripmine.blockID){
					blockPos = new BlockPos(x, y, i, CBCMod.cbcBlocks.blockTripmine.blockID);
					return blockPos;
				}
			}
		}
		return null;
		
    }
    


}
