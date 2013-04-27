package cbproject.deathmatch.blocks;

import java.util.Random;

import cbproject.core.CBCMod;
import cbproject.core.proxy.ClientProxy;


import net.minecraft.block.Block;
import net.minecraft.block.BlockOre;
import net.minecraft.block.material.Material;

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
