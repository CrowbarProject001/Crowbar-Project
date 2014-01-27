/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.block;

import static net.minecraftforge.common.ForgeDirection.DOWN;
import static net.minecraftforge.common.ForgeDirection.EAST;
import static net.minecraftforge.common.ForgeDirection.NORTH;
import static net.minecraftforge.common.ForgeDirection.SOUTH;
import static net.minecraftforge.common.ForgeDirection.UP;
import static net.minecraftforge.common.ForgeDirection.WEST;
import cn.graphrevo.GraphRevo;
import cn.graphrevo.proxy.GRClientProxy;
import cn.graphrevo.tileentity.TileEntityTripmine;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockPistonBase;
import net.minecraft.block.material.Material;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.ForgeDirection;

/**
 * 阔剑地雷的方块类。
 * @author WeAthFolD
 *
 */
public class BlockTripmine extends BlockContainer {

	/**
	 * @param par1
	 * @param par2Material
	 */
	public BlockTripmine(int blockID) {
		super(blockID, Material.rock);
		this.setHardness(0.0F); //一触即碎
		this.setCreativeTab(GraphRevo.cct);
		this.setUnlocalizedName("gr_tripmine");
	}

	@Override
	/**
	 * 返回我们的TileEntity实例。
	 */
	public TileEntity createNewTileEntity(World world) {
		return new TileEntityTripmine();
	}
	
	@Override
	/**
	 * 当方块被放置时调用。根据玩家的朝向设置地雷的metadata。
	 */
    public void onBlockPlacedBy(World par1World, int par2, int par3, int par4, EntityLivingBase par5EntityLivingBase, ItemStack par6ItemStack)
    {
        int l = ForgeDirection.values()
        		[determineOrientation(par1World, par2, par3, par4, par5EntityLivingBase)]
        				.getOpposite().ordinal(); //为方便使用了ForgeDirection以及BlockPiston的判断函数
        System.out.println("Setting metadata : " + l);
        par1World.setBlockMetadataWithNotify(par2, par3, par4, l, 2);
    }
	
    /**
     * gets the way this piston should face for that entity that placed it.
     */
    public static int determineOrientation(World world, int x, int y, int z, EntityLivingBase entity)
    {
        int l = MathHelper.floor_double((double)(entity.rotationYaw * 4.0F / 360.0F) + 0.5D) & 3;
        return l == 0 ? 2 : (l == 1 ? 5 : (l == 2 ? 3 : (l == 3 ? 4 : 0)));
    }
    
    /**
     * checks to see if you can place this block can be placed on that side of a block: BlockLever overrides
     */
    @Override
    public boolean canPlaceBlockOnSide(World par1World, int par2, int par3, int par4, int par5)
    {
	     return true;
    }
    
    @Override
    public int getRenderType()
    {
        return GRClientProxy.RENDER_ID_TRIPMINE;
    }

}
