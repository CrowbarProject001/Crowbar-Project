package cbproject.elements.renderers.weapons;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import static cbproject.elements.renderers.RendererUtils.newV3;
import static cbproject.elements.renderers.RendererUtils.addVertex;
import static cbproject.elements.renderers.RendererUtils.renderItemIn2d;
import org.lwjgl.opengl.GL11;

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
			doRender(render, ent);
		default:
			break;
		}
		return;
	}
	
	public void doRender(RenderBlocks render, EntityLiving ent){
		
		t = Tessellator.instance;
		
		float u1 = 0, v1 = 0, u2 = 1/16F, v2 = 1/16F;
		float w = 0.05F;
		float w2 = 0.1F;
		renderItemIn2d(t, u1, v1, u2, v2, w); //Vertical rendering

	    //Horizonal rendering
	    Vec3    b1 = newV3(0, 0.0 + w2, -0.5),
	    		b2 = newV3(0, 0.0 + w2, 0.5),
	    		b3 = newV3(1.0, 1.0, 0.5),
	    		b4 = newV3(1.0, 1.0, -0.5),
	    		//下底面
	    		b5 = newV3(w2, 0.0, -0.5),
	    	    b6 = newV3(w2, 0.0, 0.5),
	    	    b7 = newV3(1.0 + w2, 1.0, 0.5),
	    	    b8 = newV3(1.0 + w2, 1.0, -0.5);
	    
	    u1 = 0;
	    v1 = 1/16F;
	    u2 = 1/16F;
	    v2 = 1/8F;
	    
	    t.startDrawingQuads();
		t.setNormal(1.0F, 0.0F, 0.0F);
		addVertex(b1, u2, v2);
		addVertex(b2, u1, v2);
		addVertex(b3, u1, v1);
		addVertex(b4, u2, v1);
		t.draw();
		
		t.startDrawingQuads();
		t.setNormal(-1.0F, 0.0F, 0.0F);
		addVertex(b8, u2, v1);
		addVertex(b7, u1, v1);
		addVertex(b6, u1, v2);
		addVertex(b5, u2, v2);
		t.draw();
		
        int var7;
        float var8;
        float var9;
        float var10;
        int tileSize = TextureFXManager.instance().getTextureDimensions(GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D)).width / 16;
        float tx = 1.0f / (32 * tileSize);
        float tz = 1.0f /  tileSize;
        
        t.startDrawingQuads();
        t.setNormal(-1.0F, 0.0F, 0.0F);
        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = u2 - (u2 - u1) * var8 - tx;
            var10 = 1.0F * var8 - 0.5F;
            
            t.addVertexWithUV(0.0, 0.0 + w2, var10, var9, v2);
            t.addVertexWithUV(1.0, 1.0, var10, var9, v1);
            t.addVertexWithUV(1.0+w2, 1.0, var10, var9, v1);
            t.addVertexWithUV(w2 , 0.0, var10, var9, v2);

            t.addVertexWithUV(w2 , 0.0, var10, var9, v1);
            t.addVertexWithUV(1.0+w2, 1.0, var10, var9, v1);
            t.addVertexWithUV(1.0, 1.0, var10, var9, v1);
            t.addVertexWithUV(0.0, 0.0 + w2, var10, var9, v2);
        }
        t.draw();
	    
	}

}
