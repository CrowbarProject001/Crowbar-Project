package cbproject.elements.blocks.ammos;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraft.entity.player.*;
import net.minecraft.item.Item;

public abstract class AmmoGeneric extends Block{
	int ItemID;
	int stack;
	public AmmoGeneric(int par1, int par2, Material par3Material) {
		super(par1, par2, par3Material);
		// TODO Auto-generated constructor stub
	}
	
	public AmmoGeneric(int par1, Material par2Mat){
		super(par1,par2Mat);
	}
	
	public void SetAmmoDrop(int par1ID, int par2Stack){
		ItemID = par1ID;
		stack = par2Stack;
		
	}
	
	@Override
	public void onEntityCollidedWithBlock(World par1World, int par2, int par3, int par4, Entity par5Entity) {
		if(par5Entity instanceof EntityPlayerMP){
			this.dropBlockAsItem(par1World, par2, par3, par4, 0 , 0 );
		}
	}

    public int idDropped(int par1, Random par2Random, int par3)
    {
        return ItemID;
    }
	
	}
