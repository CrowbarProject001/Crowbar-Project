package cbproject.crafting.blocks;

import java.util.Random;

import cbproject.core.CBCMod;
import cbproject.core.register.CBCAchievements;
import net.minecraft.block.BlockOre;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;

public class BlockUraniumOre extends BlockOre {

	public BlockUraniumOre(int par1) {
		super(par1);
		setCreativeTab(CBCMod.cctMisc);
		setResistance(5.0F);
		setHardness(2.5F);
		setUnlocalizedName("uranium");
	}

	@Override
	public void registerIcons(IconRegister par1IconRegister) {
		blockIcon = par1IconRegister.registerIcon("lambdacraft:uranium");
	}

	@Override
	public int idDropped(int par1, Random par2Random, int par3) {
		return this.blockID;
	}

	@Override
	public int quantityDropped(Random par1Random) {
		return 1;
	}

	@Override
	public void onBlockHarvested(World par1World, int par2, int par3, int par4,
			int par5, EntityPlayer par6EntityPlayer) {
		CBCAchievements.getAchievement(par6EntityPlayer, CBCAchievements.nuclearRawMaterial);
	}

}
