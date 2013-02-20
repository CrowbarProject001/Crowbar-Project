package cbproject.elements.items;

import cbproject.misc.CCT;
import cbproject.proxy.ClientProxy;
import cbproject.CBCMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.src.ModLoader;
import net.minecraft.world.World;

public class Test_Item extends Item {

	public Test_Item(int par1) {
		
		super(par1);

		setItemName("Test_Item");
		setCreativeTab(CBCMod.cct);
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH );
	//	setIconIndex(0);
	}

	@Override
    public boolean onItemUse(ItemStack itemStack, EntityPlayer entityPlayer,
    		World world, int x, int y, int z, int sd, float xo, float yo, float zo)
    {
		if (world.isRemote) return true;
		
//		for(int dx =-4 ; dx<= -4; dx++)
//		{
//			for(int dz =-4 ; dz<= -4; dz++)
//			{
		//if (Math.abs(dx) + Math.abs(dz) > 6 ) continue;
				
				int new_x = x;// + dx;
				int new_y = y;
				int new_z = z; //+ dz;
				
				set_snow_or_ice(world, new_x ,new_y ,new_z );
//			}
//		}
		
		return true;

    }

	private void set_snow_or_ice(World world, int x, int y, int z) 
	{
		int block_id = world.getBlockId(x,y,z);
		
		if (block_id == 0) return;
		
		if (block_id == Block.sand.blockID || block_id == Block.dirt.blockID ||  block_id == Block.grass.blockID){
			world.setBlock(x, y, z, Block.snow.blockID);
		}
		
		if(block_id == Block.waterMoving.blockID || block_id == Block.waterStill.blockID )
			world.setBlock(x, y, z, Block.ice.blockID);
		
	}
}