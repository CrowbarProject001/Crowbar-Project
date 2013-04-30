package cbproject.deathmatch.blocks;

import java.util.Random;

import cbproject.core.CBCMod;
import net.minecraft.block.BlockOre;

public class BlockUraniumOre extends BlockOre {

	public BlockUraniumOre(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setHardness(3.0F);
		setResistance(5.0F);
		setUnlocalizedName("oreUranium");
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
