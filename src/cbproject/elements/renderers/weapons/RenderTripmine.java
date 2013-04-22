/**
 * 
 */
package cbproject.elements.renderers.weapons;


import org.lwjgl.opengl.GL11;

import cbproject.CBCMod;
import cbproject.elements.renderers.RendererUtils;
import cbproject.proxy.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

/**
 * @author Administrator
 *
 */
public class RenderTripmine extends RendererUtils implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		renderBlockTripmine(renderer, block, world.getBlockMetadata(x, y, z), x, y, z, world);
		return false;
	}

	
    public boolean renderBlockTripmine(RenderBlocks renderer, Block par1Block,int metadata, int x, int y, int z, IBlockAccess blockAccess)
    {
    	
        Tessellator tessellator = Tessellator.instance;
        int var5 = metadata & 3;

        setBound(par1Block);
        this.addCoord(x, y, z);
        Vec3 v1, v2, v3, v4, v5, v6, v7, v8;
        switch(var5){
        case 1:
        case 3:
        	v1 = Vec3.createVectorHelper(minX, minY, minZ);
        	v2 = Vec3.createVectorHelper(minX, minY, maxZ);
        	v3 = Vec3.createVectorHelper(minX, maxY, maxZ);
        	v4 = Vec3.createVectorHelper(minX, maxY, minZ);
        	
        	v5 = Vec3.createVectorHelper(maxX, minY, minZ);
        	v6 = Vec3.createVectorHelper(maxX, minY, maxZ);
        	v7 = Vec3.createVectorHelper(maxX, maxY, maxZ);
        	v8 = Vec3.createVectorHelper(maxX, maxY, minZ);
        	
        	break;
        	
        default:
        	v1 = Vec3.createVectorHelper(minX, minY, minZ);
        	v2 = Vec3.createVectorHelper(maxX, minY, minZ);
        	v3 = Vec3.createVectorHelper(maxX, maxY, minZ);
        	v4 = Vec3.createVectorHelper(minX, maxY, minZ);
        	
        	v5 = Vec3.createVectorHelper(minX, minY, maxZ);
        	v6 = Vec3.createVectorHelper(maxX, minY, maxZ);
        	v7 = Vec3.createVectorHelper(maxX, maxY, maxZ);
        	v8 = Vec3.createVectorHelper(minX, maxY, maxZ);
        	
        	break;
        }
        
        int side = getTexture(ClientProxy.TRIPMINE_SIDE_PATH), front = getTexture(ClientProxy.TRIPMINE_FRONT_PATH),
        		top = getTexture(ClientProxy.TRIPMINE_TOP_PATH);

        GL11.glPushMatrix();
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, side);

        if(var5 == 1 || var5 == 3){
        	
        	addVertex(v4 , 0, 0);
            addVertex(v8 , 1, 0);
            addVertex(v5 , 1, 1);
            addVertex(v1 , 0, 1);
             
            addVertex(v7 , 1, 0);
            addVertex(v3 , 0, 0);
            addVertex(v2 , 0, 1);
            addVertex(v6 , 1, 1);
            
        } else {
        	 	
            addVertex(v8 , 1, 0); 
            addVertex(v4 , 0, 0);
            addVertex(v1 , 0, 1);
            addVertex(v5 , 1, 1);
            
             
            addVertex(v7 , 1, 0); 
            addVertex(v6 , 1, 1);
            addVertex(v2 , 0, 1);
            addVertex(v3 , 0, 0);
            
        }
        tessellator.draw();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, front);
        tessellator.startDrawingQuads();
        if( var5 == 0 ){
        	
        	addVertex(v5, 0, 1);
            addVertex(v6, 1, 1);
            addVertex(v7, 1, 0);
            addVertex(v8, 0, 0);
            
        } else if(var5 == 1){
 
        	addVertex(v1, 0, 1);
            addVertex(v2, 1, 1);
            addVertex(v3, 1, 0);
            addVertex(v4, 0, 0);
            
        } else if(var5 == 2 ){
        	
        	addVertex(v1, 1, 1);
            addVertex(v4, 0, 1);
            addVertex(v3, 0, 0);
            addVertex(v2, 1, 0); 
            
        } else {

            addVertex(v7, 1, 0);
            addVertex(v6, 1, 1);
            addVertex(v5, 0, 1);
            addVertex(v8, 0, 0);
            
        }
        tessellator.draw();
        
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, top);
        tessellator.startDrawingQuads();
        if(var5 == 1 || var5 == 3){
        	addVertex(v4 , 0, 0);
        	addVertex(v3 , 1, 0);
        	addVertex(v7 , 1, 1);
        	addVertex(v8 , 0, 1);
        	
        	addVertex(v6 , 1, 1); 
            addVertex(v2 , 1, 0);
            addVertex(v1 , 0, 0);
            addVertex(v5 , 0, 1); 
        } else {
        	addVertex(v7 , 1, 1);
        	addVertex(v3 , 1, 0);
        	addVertex(v4 , 0, 0);
        	addVertex(v8 , 0, 1);
        	          
            addVertex(v1 , 0, 0); 
            addVertex(v2 , 1, 0);
            addVertex(v6 , 1, 1); 
            addVertex(v5 , 0, 1); 
        }
        tessellator.draw();
        
        tessellator.startDrawingQuads();
        GL11.glPopMatrix();
        return true;
    }
	

    
	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		return CBCMod.RENDER_TYPE_TRIPMINE;
	}

}
