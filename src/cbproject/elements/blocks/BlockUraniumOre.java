package cbproject.elements.blocks;

import java.util.Random;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;

public class BlockUraniumOre extends BlockOre {

	public BlockUraniumOre(int par1, int par2) {
		super(par1, par2);
		setCreativeTab(CBCMod.cct);
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setHardness(3.0F);
		setResistance(5.0F);
		setBlockName("oreUranium");
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public int idDropped(int par1, Random par2Random, int par3){
		return  this.blockID;
	}

	@Override
    public int quantityDropped(Random par1Random)
    {
        return 1;
    }

}
