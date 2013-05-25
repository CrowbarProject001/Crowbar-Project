package cbproject.core.block;

import cbproject.core.CBCMod;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;

public class CBCGenericBlock extends Block{

	private String texturePath;
	
	public CBCGenericBlock(int par1, Material par2Material) {
		super(par1, par2Material);
		setCreativeTab(CBCMod.cct);
	}
	
	public void setTexturePath(String path){
		texturePath = path;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("lambdacraft:" + texturePath);
    }

}
