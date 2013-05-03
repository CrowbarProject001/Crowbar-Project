package cbproject.deathmatch.blocks;

import cbproject.core.CBCMod;
import net.minecraft.block.BlockOre;

public class BlockRefined extends BlockOre {

	public BlockRefined(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cct);
		setHardness(2.0F);
	}

}
