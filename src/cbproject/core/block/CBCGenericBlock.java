package cbproject.core.block;

import java.util.List;

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