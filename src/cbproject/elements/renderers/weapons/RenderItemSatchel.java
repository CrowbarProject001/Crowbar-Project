package cbproject.elements.renderers.weapons;

import org.lwjgl.opengl.GL11;

import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.elements.items.weapons.Weapon_satchel;
import cbproject.utils.weapons.InformationSatchel;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

public class RenderItemSatchel implements IItemRenderer {

	Tessellator t = Tessellator.instance;
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch(type){
		case EQUIPPED:
			return true;
		case INVENTORY:
			return true;

		default:
			return false;	
		}
		
	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		switch(helper){
		case ENTITY_ROTATION:
		case ENTITY_BOBBING:
		case EQUIPPED_BLOCK:
			return true;
			
		default:
			return false;
			
		}
	}
	
	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch(type){
		case EQUIPPED:
			renderEquipped(item, (RenderBlocks)data[0], (EntityLiving) data[1]);
			break;
		case INVENTORY:
			renderInventory(item, (RenderBlocks) data[0]);
			break;
		default:
			break;
			
		}

	}
	
	public void renderEquipped(ItemStack item, RenderBlocks render, EntityLiving entity){
		
		ItemRenderer.renderItemIn2D(t, 0.0F, 0.0F, 1.0F, 1.0F, 0.0625F); 

	}
	
	public void renderInventory(ItemStack item, RenderBlocks render){	
		
		if(item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		
        int index = item.getTagCompound().getInteger("mode");
        
        index = (index == 0) ? 64 : 66;
        float v1 = MathHelper.floor_float(index / 16)/16.0F;
		float u1 = (index % 16)/16.0F;
		float u2 = u1 + 1/16F;
		float v2 = v1 + 1/16F;
		
		t.startDrawingQuads();
		t.addVertexWithUV(0, 0, 0, u1, v1);
		t.addVertexWithUV(0, 16, 0, u1, v2);
		t.addVertexWithUV(16, 16, 0, u2, v2);
		t.addVertexWithUV(16, 0, 0, u2, v1);
		
		/*
		t.addVertexWithUV(16, 1, 0, u2, v2);
		t.addVertexWithUV(0, 1, 0, u1, v2);
		t.addVertexWithUV(0, 0, 0, u1, v1);
		t.addVertexWithUV(1, 0, 0, u2, v1);
		*/
		t.draw();
		
	}

    protected void addVertex(Vec3 vec3, double texU, double texV){
    	Tessellator tessellator = Tessellator.instance;
    	tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
    }
    
	

}
