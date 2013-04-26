package cbproject.elements.blocks;

import java.util.Random;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.CBCMod;
import cbproject.elements.blocks.tileentities.TileEntityTripmineRay;
import cbproject.elements.blocks.weapons.BlockTripmine;
import cbproject.proxy.ClientProxy;
import cbproject.utils.BlockPos;


import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

/**
 * Tripmine Ray block, which was used with Tripmine block.
 * @author WeAthFolD
 */
public class BlockTripmineRay extends BlockContainer {

	public BlockTripmineRay(int par1) {
		super(par1, Material.circuits);
		setBlockUnbreakable();
		setLightValue(0.1F);
		setTickRandomly(true);
		setUnlocalizedName("blockTripmineRay");
	}
	
	@Override
	public int getRenderType() {
		return CBCMod.RENDER_TYPE_EMPTY;
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("lambdacraft:empty");
    }
	
    @Override
	public int getRenderBlockPass()
    {
        return 1;
    }
    
    @Override
    public int tickRate(World par1World)
    {
        return 10;
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
    public void updateTick(World par1World, int par2, int par3, int par4, Random par5Random)
    {
        if(getSource(par1World, par2, par3, par4, par1World.getBlockMetadata(par2, par3, par4)) == null)
        	par1World.setBlockToAir(par2, par3, par4);
    }
    
    @Override
	public int idPicked(World par1World, int par2, int par3, int par4)
    {
        return 0;
    }
    
    @Override
	public void onBlockClicked(World par1World, int par2, int par3, int par4, EntityPlayer par5EntityPlayer) {
    	onEntityCollidedWithBlock(par1World, par2, par3, par4, par5EntityPlayer);
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
    		par1World.setBlockToAir(par2, par3, par4);
    		return;
    	}
    	par1World.notifyBlockChange(pos.x, pos.y, pos.z, BlockTripmine.NOTIFY_ID);
        
    }

    
    @Override
    public void onNeighborBlockChange(World par1World, int par2, int par3, int par4, int par5) {
    	if(par5 == BlockTripmine.NOTIFY_ID){
    		par1World.setBlockToAir(par2, par3, par4);
    	}
    }
    
    
    @Override
    public void setBlockBoundsBasedOnState(IBlockAccess par1IBlockAccess, int par2, int par3, int par4)  {
    	
        int var5 = par1IBlockAccess.getBlockMetadata(par2, par3, par4);
        float h = 0.025F, w = 0.025F;
        if (var5 == 3 || var5 == 1) //X+, X-
        {
            this.setBlockBounds(0.0F, 0.5F - h, 0.5F - w, 1.0F, 0.5F + h, 0.5F + w); // (0, 1) (0.4, 0.6), (0.3, 0.7)
        } else {
            this.setBlockBounds(0.5F - w, 0.5F - h, 0.0F, 0.5F + w, 0.5F + h, 1.0F);
        }
        
        return;
    }
    
    /**
     * Get the source tripmine block.
     * @param as you see >)
     * @return BlockPos containg source block's x, y, z.(could be null)
     */
    public BlockPos getSource(World worldObj, int x, int y, int z, int var10){
    	
    	BlockPos blockPos = null;

    	int j = 0;
		for(int i = (var10 == 1 || var10 == 3)? x : z; j < BlockTripmine.RAY_RANGE; i = (var10 == 0 || var10 == 3)? i-1 : i + 1, j++){
			if(var10 == 1 || var10 == 3){
				if(worldObj.getBlockId(i, y, z) == CBCBlocks.blockTripmine.blockID){
					blockPos = new BlockPos(i, y, z, CBCBlocks.blockTripmine.blockID);
					blockPos.x = (var10 == 3)? blockPos.x +1: blockPos.x -1;
					return blockPos;
				} 
			} else {
				if(worldObj.getBlockId(x, y, i) == CBCBlocks.blockTripmine.blockID){
					blockPos = new BlockPos(x, y, i, CBCBlocks.blockTripmine.blockID);
					blockPos.z = (var10 == 0) ? blockPos.z+1:blockPos.z-1;
					return blockPos;
				}
			}
		}
		return blockPos;
		
    }
    
    @Override
    public AxisAlignedBB getCollisionBoundingBoxFromPool(World par1World, int par2, int par3, int par4)
    {
        return null;
    }

	@Override
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTripmineRay();
	}
    
}
