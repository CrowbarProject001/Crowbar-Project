package cbproject.crafting.blocks;

import cbproject.core.CBCMod;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;

public class BlockRefined extends BlockOre {

	public BlockRefined(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setHardness(2.0F);
		setUnlocalizedName("refined");
	}
	
	@Override
	public void registerIcons(IconRegister par1IconRegister)
    {
        blockIcon = par1IconRegister.registerIcon("lambdacraft:steel");
    }

}
