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

	Tessellator t = Tessellator.instance;
	
	public RenderCrossbow() {
		
		// TODO Auto-generated constructor stub
	}

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
		
		//u1 v1 : 纵向的UV; u2 v2 : 横向的UV
		float u1 = 0, v1 = 0, u2 = 1/16F, v2 = 1/16F;
		
		float w = 0.05F;
		float w2 = 0.1F;
		renderItemIn2d(t, u1, v1, u2, v2, w);
		/*
		//纵向的绘制
		Vec3 a1 = newV3(0, 0, w), 
			 a2 = newV3(1, 0, w),
			 a3 = newV3(1, 1, w),
			 a4 = newV3(0, 1, w),
			 
			 a5 = newV3(0, 0, -w),
			 a6 = newV3(1, 0, -w),
			 a7 = newV3(1, 1, -w),
			 a8 = newV3(0, 1, -w);
			
		t = Tessellator.instance;
		
		t.startDrawingQuads();
		t.setNormal(0.0F, 0.0F, 1.0F);
		addVertex(a1, u2, v2);
		addVertex(a2, u1, v2);
		addVertex(a3, u1, v1);
		addVertex(a4, u2, v1);
		t.draw();
		
	    t.startDrawingQuads();
	    t.setNormal(0.0F, 0.0F, -1.0F); 
	    addVertex(a8, u2, v1);
	    addVertex(a7, u1, v1);
	    addVertex(a6, u1, v2);
	    addVertex(a5, u2, v2);
	    t.draw();
	    
       
        
        
        /* Gets the width/16 of the currently bound texture, used
         * to fix the side rendering issues on textures != 16 
        

        
        t.startDrawingQuads();
        t.setNormal(-1.0F, 0.0F, 0.0F);
        for (var7 = 0; var7 < tileSize; ++var7)
        { //从上到下绘制tileSize个平面，
            var8 = (float)var7 / tileSize; //绘制的像素位移(0-32)
            var9 = u2 - (u2 - u1) * var8 - tx; //u的位移
            var10 = 1.0F * var8; //=像素位移
            t.addVertexWithUV((double)var10, 0.0D, -w, (double)var9, (double)v2);
            t.addVertexWithUV((double)var10, 0.0D, w, (double)var9, (double)v2);
            t.addVertexWithUV((double)var10, 1.0D, w, (double)var9, (double)v1);
            t.addVertexWithUV((double)var10, 1.0D, -w, (double)var9, (double)v1);
            
            t.addVertexWithUV((double)var10, 1.0D, w, (double)var9, (double)v1);
            t.addVertexWithUV((double)var10, 0.0D, w, (double)var9, (double)v2);
            t.addVertexWithUV((double)var10, 0.0D, -w, (double)var9, (double)v2);
            t.addVertexWithUV((double)var10, 1.0D, -w, (double)var9, (double)v1);
        }
        t.draw();
	     */
		
	    //横向的绘制
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
        { //从-Z到Z绘制TileSize个平面
            var8 = (float)var7 / tileSize; //绘制的像素位移(0-32)
            var9 = u2 - (u2 - u1) * var8 - tx; //u的位移
            var10 = 1.0F * var8 - 0.5F; //=像素位移,从-.5到+.5
            
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
	
    public void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, float par5)
    {
        float var6 = 1.0F;
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)par1, (double)par4);
        par0Tessellator.addVertexWithUV((double)var6, 0.0D, 0.0D, (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV((double)var6, 1.0D, 0.0D, (double)par3, (double)par2);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)par1, (double)par2);
        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, (double)(0.0F - par5), (double)par1, (double)par2);
        par0Tessellator.addVertexWithUV((double)var6, 1.0D, (double)(0.0F - par5), (double)par3, (double)par2);
        par0Tessellator.addVertexWithUV((double)var6, 0.0D, (double)(0.0F - par5), (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, (double)(0.0F - par5), (double)par1, (double)par4);
        par0Tessellator.draw();
        
        /*
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(-1.0F, 0.0F, 0.0F);
        int var7;
        float var8;
        float var9;
        float var10;
        
        /* Gets the width/16 of the currently bound texture, used
         * to fix the side rendering issues on textures != 16 */
        /*
        int tileSize = TextureFXManager.instance().getTextureDimensions(GL11.glGetInteger(GL11.GL_TEXTURE_BINDING_2D)).width / 16;

        float tx = 1.0f / (32 * tileSize);
        float tz = 1.0f /  tileSize;

        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize; //绘制的像素位移
            var9 = par1 + (par3 - par1) * var8 - tx; //u的位移
            var10 = var6 * var8; //=像素位移
            par0Tessellator.addVertexWithUV((double)var10, 0.0D, (double)(0.0F - par5), (double)var9, (double)par4);
            par0Tessellator.addVertexWithUV((double)var10, 0.0D, 0.0D, (double)var9, (double)par4);
            par0Tessellator.addVertexWithUV((double)var10, 1.0D, 0.0D, (double)var9, (double)par2);
            par0Tessellator.addVertexWithUV((double)var10, 1.0D, (double)(0.0F - par5), (double)var9, (double)par2);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(1.0F, 0.0F, 0.0F);

        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = par1 + (par3 - par1) * var8 - tx;
            var10 = var6 * var8 + tz;
            par0Tessellator.addVertexWithUV((double)var10, 1.0D, (double)(0.0F - par5), (double)var9, (double)par2);
            par0Tessellator.addVertexWithUV((double)var10, 1.0D, 0.0D, (double)var9, (double)par2);
            par0Tessellator.addVertexWithUV((double)var10, 0.0D, 0.0D, (double)var9, (double)par4);
            par0Tessellator.addVertexWithUV((double)var10, 0.0D, (double)(0.0F - par5), (double)var9, (double)par4);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 1.0F, 0.0F);

        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = par4 + (par2 - par4) * var8 - tx;
            var10 = var6 * var8 + tz;
            par0Tessellator.addVertexWithUV(0.0D, (double)var10, 0.0D, (double)par1, (double)var9);
            par0Tessellator.addVertexWithUV((double)var6, (double)var10, 0.0D, (double)par3, (double)var9);
            par0Tessellator.addVertexWithUV((double)var6, (double)var10, (double)(0.0F - par5), (double)par3, (double)var9);
            par0Tessellator.addVertexWithUV(0.0D, (double)var10, (double)(0.0F - par5), (double)par1, (double)var9);
        }

        par0Tessellator.draw();
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, -1.0F, 0.0F);

        for (var7 = 0; var7 < tileSize; ++var7)
        {
            var8 = (float)var7 / tileSize;
            var9 = par4 + (par2 - par4) * var8 - tx;
            var10 = var6 * var8;
            par0Tessellator.addVertexWithUV((double)var6, (double)var10, 0.0D, (double)par3, (double)var9);
            par0Tessellator.addVertexWithUV(0.0D, (double)var10, 0.0D, (double)par1, (double)var9);
            par0Tessellator.addVertexWithUV(0.0D, (double)var10, (double)(0.0F - par5), (double)par1, (double)var9);
            par0Tessellator.addVertexWithUV((double)var6, (double)var10, (double)(0.0F - par5), (double)par3, (double)var9);
        }

        par0Tessellator.draw();
        */
    }
    

}
