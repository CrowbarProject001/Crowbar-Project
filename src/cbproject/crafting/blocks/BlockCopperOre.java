package cbproject.crafting.blocks;

import java.util.Random;

import cbproject.core.CBCMod;
import cbproject.core.register.CBCAchievements;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockCopperOre extends BlockOre {

	public BlockCopperOre(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cctMisc);
		setResistance(5.0F);
		setHardness(1.5F);
		setUnlocalizedName("copper");
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon("lambdacraft:copper");
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return this.blockID;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}
}
