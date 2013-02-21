package cbproject.elements.blocks.ammos;

import cbproject.CBCMod;
import cbproject.elements.items.ammos.ItemUranium;
import cbproject.proxy.ClientProxy;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;

public class Ammo_uranium extends AmmoGeneric{

	public Ammo_uranium(int par1,int par2, Material par2Material) {
		
		super(par1, par2,Material.iron);
		
		SetAmmoDrop( CBCMod.cbcItems.itemUranium.itemID , 1);
		setTextureFile( ClientProxy.BLOCKS_TEXTURE_PATH );
		setBlockName( "AmmoUranium" );
		setResistance( 0.1F );
		setHardness( 0.1F );
		setCreativeTab( CBCMod.cct );
		
	}

}
