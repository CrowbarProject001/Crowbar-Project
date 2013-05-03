package cbproject.crafting.blocks;

import java.util.Random;

import cbproject.core.CBCMod;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockUraniumOre extends BlockOre {

	public BlockUraniumOre(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setHardness(3.0F);
		setResistance(5.0F);
		setUnlocalizedName("uranium");
		setHardness(2.5F);
	}
	
    @Override
	public void registerIcons(IconRegister par1IconRegister)
    {
        blockIcon = par1IconRegister.registerIcon("lambdacraft:uranium");
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
