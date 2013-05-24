package cbproject.core.block;

<<<<<<< HEAD
import java.util.List;
=======
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
>>>>>>> Cleanups

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.core.CBCMod;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CBCGenericBlock extends Block{
	
	protected String description;
	protected boolean useDescription = false;;
	private String iconName = "";
	
	public CBCGenericBlock (int id, Material material){
		super(id, material);
		setCreativeTab(CBCMod.cct);
	}
	
	public void setIconName(String name){
		this.iconName = name;
	}
	
    @SideOnly(Side.CLIENT)
    @Override
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.blockIcon = par1IconRegister.registerIcon("lambdacraft:" + iconName);
    }

}
