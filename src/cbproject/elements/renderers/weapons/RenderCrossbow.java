package cbproject.elements.renderers.weapons;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.TextureFXManager;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.world.storage.MapData;
import net.minecraftforge.client.IItemRenderer;

public class RenderCrossbow implements IItemRenderer {

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
		case FIRST_PERSON_MAP:
			EntityPlayer entityPlayer = (EntityPlayer)data[0];
			RenderEngine renderEngine = (RenderEngine)data[1];
			MapData mapData = (MapData)data[2];
			break;
		case EQUIPPED:
			RenderBlocks render = (RenderBlocks)data[0];
			EntityLiving ent = (EntityLiving)data[1];
			int index = item.getItem().getIconIndex(item);
			//38 = 16*2 + 6
			float a = MathHelper.floor_float(index / 16);
			float b = (index % 16)/16.0F;
			a /= 16.0F;
			renderItemIn2D(tesselator, b, a, b + 1/16F, a + 1/16F, 0.5F);
		case INVENTORY:
			
		default:
			break;
		}
		return;
	}
	
	/*1st person
	 * Z:前后 正方向朝前 左右
	 * X:上下 正方向向下
	 * Y:左右 正方形向左
	 * 
	 */
    public static void renderItemIn2D(Tessellator par0Tessellator, float par1, float par2, float par3, float par4, float par5)
    {
        float var6 = 1.0F;
        /*
        par0Tessellator.startDrawingQuads();
        par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
        par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, (double)par1, (double)par4);
        par0Tessellator.addVertexWithUV((double)var6, 0.0D, 0.0D, (double)par3, (double)par4);
        par0Tessellator.addVertexWithUV((double)var6, 1.0D, 0.0D, (double)par3, (double)par2);
        par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, (double)par1, (double)par2);
        par0Tessellator.draw();
        */
       
        for(int i=0; i<20; i++){
        	float var1 = i/100;
        	par0Tessellator.startDrawingQuads(); 
        	par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
        	par0Tessellator.addVertexWithUV(1,1,-1+ var1, (double)par1, (double)par2);
        	par0Tessellator.addVertexWithUV(1,0,-1+ var1, (double)par1, (double)par4);
        	par0Tessellator.addVertexWithUV(0,0,0+ var1, (double)par3, (double)par4);
        	par0Tessellator.addVertexWithUV(0,1,0+ var1, (double)par3, (double)par2);
        	par0Tessellator.draw();
        }
        

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
            var8 = (float)var7 / tileSize;
            var9 = par1 + (par3 - par1) * var8 - tx;
            var10 = var6 * var8;
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
