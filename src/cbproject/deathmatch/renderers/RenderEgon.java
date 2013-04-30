package cbproject.deathmatch.renderers;


import org.lwjgl.opengl.GL11;

import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RendererUtils;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

/**
 * Weapon Egon Renderer Class.
 */
public class RenderEgon implements IItemRenderer {

	Tessellator t = Tessellator.instance;
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch(type){
		case EQUIPPED:
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

		default:
			break;
			
		}

	}
	
	public void renderEquipped(ItemStack item, RenderBlocks render, EntityLiving entity){
		
		if(item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		
        int tex = RendererUtils.getTexture(ClientProps.EGON_EQUIPPED_PATH);
		float width = 0.0625F;
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		RendererUtils.renderItemIn2d(t, width); 

	}

}
