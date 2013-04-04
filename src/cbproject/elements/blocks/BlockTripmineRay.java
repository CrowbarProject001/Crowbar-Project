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
		setBlockUnbreakable();
	}
	
    public int getRenderType()
    {
        return CBCMod.RENDER_TYPE_TRIPMINE_RAY;
    }
    
    public int getRenderBlockPass()
    {
        return 1;
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
    public void onBlockAdded(World par1World, int par2, int par3, int par4)
    {
        System.out.println("Metadata added: " + par1World.getBlockMetadata(par2, par3, par4));
    }
    
    @Override
    public void breakBlock(World par1World, int par2, int par3, int par4, int par5, int par6)
    {
    	BlockPos pos = getSource(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4));
    	
    	if(pos == null)
    		return;
    	par1World.notifyBlockChange(pos.x, pos.y, pos.z, BlockTripmine.NOTIFY_ID);
        return;
    }
    
    @Override
    public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity)
    {
    	
    	BlockPos pos = getSource(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4));
    	if(pos == null){
    		System.out.println("Exception: didnt find source.");
    		par1World.setBlockWithNotify(par2, par3, par4, 0);
    		return;
    	}
    	par1World.notifyBlockChange(pos.x, pos.y, pos.z, BlockTripmine.NOTIFY_ID);
        
    }

    
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
    	if(par5 == BlockTripmine.NOTIFY_ID){
    		par1World.setBlockWithNotify(par2, par3, par4, 0);
    	}
    }
    
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)  {
    	
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float h = 0.1F, w = 0.1F;
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
    	switch(var10){
    	case 3:
    		System.out.println("Searching in x-");
    		break;
    	case 1:
    		System.out.println("Searching in x+");
    		break;
    	case 0:
    		System.out.println("Searching in z-");
    		break;
    		default:
    			System.out.println("Searching in z+");
    	}
    	int j = 0;
		for(int i = (var10 == 1 || var10 == 3)? x : z; j < BlockTripmine.RAY_RANGE; i = (var10 == 0 || var10 == 3)? i-1 : i + 1, j++){
			if(var10 == 1 || var10 == 3){
				if(worldObj.getBlockId(i, y, z) == CBCMod.cbcBlocks.blockTripmine.blockID){
					blockPos = new BlockPos(i, y, z, CBCMod.cbcBlocks.blockTripmine.blockID);
					blockPos.x = (var10 == 3)? blockPos.x +1: blockPos.x -1;
					return blockPos;
				} 
			} else {
				if(worldObj.getBlockId(x, y, i) == CBCMod.cbcBlocks.blockTripmine.blockID){
					blockPos = new BlockPos(x, y, i, CBCMod.cbcBlocks.blockTripmine.blockID);
					blockPos.z = (var10 == 0) ? blockPos.z+1:blockPos.z-1;
					return blockPos;
				}
			}
		}
		return blockPos;
		
    }
    

    


}
