package cbproject.elements.renderers.weapons;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import static cbproject.elements.renderers.RendererUtils.addVertex;
import static cbproject.elements.renderers.RendererUtils.newV3;
import static cbproject.elements.renderers.RendererUtils.renderItemIn2d;

import org.lwjgl.opengl.GL11;

import cbproject.elements.items.weapons.Weapon_crossbow;
import cbproject.elements.renderers.RendererUtils;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBullet;


import cpw.mods.fml.client.TextureFXManager;

public class RenderCrossbow implements IItemRenderer {

	Tessellator t;
	
	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
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
		Tessellator tesselator = Tessellator.instance;
		switch(type){
		case EQUIPPED:
			RenderBlocks render = (RenderBlocks)data[0];
			EntityLiving ent = (EntityLiving)data[1];
			doRender(render, ent, item);
		default:
			break;
		}
		return;
	}
	
	public void doRender(RenderBlocks render, EntityLiving ent, ItemStack item){
		
		t = Tessellator.instance;
		
		Weapon_crossbow wpn = (Weapon_crossbow) item.getItem();
		InformationBullet inf = wpn.getInformation(item, ent.worldObj);
		float w = 0.05F;
		float w2 = 0.1F;
		int tex = RendererUtils.getTexture(ClientProxy.CROSSBOW_SIDE_PATH[5 - item.getItemDamage()]);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
		renderItemIn2d(t, w); //Vertical rendering

	    //Horizonal rendering
	    Vec3    b1 = newV3(0, 0.0 + w2, -0.5),
	    		b2 = newV3(0, 0.0 + w2, 0.5),
	    		b3 = newV3(1.0, 1.0, 0.5),
	    		b4 = newV3(1.0, 1.0, -0.5),
	    		//涓嬪簳闈�
	    		b5 = newV3(w2, 0.0, -0.5),
	    	    b6 = newV3(w2, 0.0, 0.5),
	    	    b7 = newV3(1.0 + w2, 1.0, 0.5),
	    	    b8 = newV3(1.0 + w2, 1.0, -0.5);
	    
	    tex = RendererUtils.getTexture(ClientProxy.CROSSBOW_FRONT_PATH[wpn.isBowPulling(item) ? 0: 1]);
	    
	    GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
	    t.startDrawingQuads();
		t.setNormal(1.0F, 0.0F, 0.0F);
		addVertex(b1, 1.0F, 1.0F);
		addVertex(b2, 0.0F, 1.0F);
		addVertex(b3, 0.0F, 0.0F);
		addVertex(b4, 1.0F, 0.0F);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(-1.0F, 0.0F, 0.0F);
		addVertex(b8, 1.0F, 0.0F);
		addVertex(b7, 0.0F, 0.0F);
		addVertex(b6, 0.0F, 1.0F);
		addVertex(b5, 1.0F, 1.0F);
		t.draw();
		
        int var7;
        float var8;
        float var9;
        float var10;
        int tileSize = 32;
        float tx = 1.0f / (32 * tileSize);
        float tz = 1.0f /  tileSize;
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
        t.startDrawingQuads();
        t.setNormal(-1.0F, 0.0F, 0.0F);
        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = 1.0F - var8 - tx;
            var10 = 1.0F * var8 - 0.5F;
            
            t.addVertexWithUV(0.0, 0.0 + w2, var10, var9, 1.0F);
            t.addVertexWithUV(1.0, 1.0, var10, var9, 0.0F);
            t.addVertexWithUV(1.0+w2, 1.0, var10, var9, 0.0F);
            t.addVertexWithUV(w2 , 0.0, var10, var9, 1.0F);

            t.addVertexWithUV(w2 , 0.0, var10, var9, 1.0F);
            t.addVertexWithUV(1.0+w2, 1.0, var10, var9, 0.0F);
            t.addVertexWithUV(1.0, 1.0, var10, var9, 0.0F);
            t.addVertexWithUV(0.0, 0.0 + w2, var10, var9, 1.0F);
        }
        t.draw();
	    
	}

}
