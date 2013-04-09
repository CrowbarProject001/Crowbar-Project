package cbproject.elements.renderers.weapons;

import cbproject.elements.renderers.RendererUtils;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

/*
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
		int mode = item.getTagCompound().getInteger("mode");
        int index = 50;
        
        float v1 = MathHelper.floor_float(index / 16)/16.0F;
		float u1 = (index % 16)/16.0F;
		float u2 = u1 + 1/16F;
		float v2 = v1 + 1/16F;
		float width = (mode == 0) ? 0.15F : 0.0625F;
		
		RendererUtils.renderItemIn2d(t, u1, v1, u2, v2, width); 

	}

}
