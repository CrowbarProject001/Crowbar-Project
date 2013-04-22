package cbproject.elements.renderers.weapons;


import org.lwjgl.opengl.GL11;

import cbproject.CBCMod;
import cbproject.elements.renderers.RendererUtils;
import cbproject.proxy.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

public class RenderTripmineRay extends RendererUtils implements ISimpleBlockRenderingHandler {

	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		// TODO Auto-generated method stub
		renderBlockTripmine(renderer, block, world.getBlockMetadata(x, y, z), x, y, z, world);
		return true;
	}

    public boolean renderBlockTripmine(RenderBlocks renderer, Block par1Block,int metadata, int x, int y, int z, IBlockAccess blockAccess)
    {
    	
        Tessellator tessellator = Tessellator.instance;
        int var5 = metadata;

        setBound(par1Block);
        this.addCoord(x, y, z);
        Vec3 v1, v2, v3, v4, v5, v6, v7, v8;
        
        switch(var5){
        case 1:
        case 3:
        	v1 = Vec3.createVectorHelper(minX, y+0.5, minZ);
         	v2 = Vec3.createVectorHelper(minX, y+0.5, maxZ);
         	v3 = Vec3.createVectorHelper(maxX, y+0.5, maxZ);
         	v4 = Vec3.createVectorHelper(maxX, y+0.5, minZ);
         	
        	v5 = Vec3.createVectorHelper(minX, maxY, z + 0.5);
        	v6 = Vec3.createVectorHelper(maxX, maxY, z + 0.5);
        	v7 = Vec3.createVectorHelper(maxX, minY, z + 0.5);
        	v8 = Vec3.createVectorHelper(minX, minY, z + 0.5);
        	
        	break;
        	
        default:
        	v1 = Vec3.createVectorHelper(minX, y+0.5, minZ);
         	v2 = Vec3.createVectorHelper(minX, y+0.5, maxZ);
         	v3 = Vec3.createVectorHelper(maxX, y+0.5, maxZ);
         	v4 = Vec3.createVectorHelper(maxX, y+0.5, minZ);
         	
        	v5 = Vec3.createVectorHelper(x+0.5, maxY, minZ);
        	v6 = Vec3.createVectorHelper(x+0.5, maxY, maxZ);
        	v7 = Vec3.createVectorHelper(x+0.5, minY, maxZ);
        	v8 = Vec3.createVectorHelper(x+0.5, minY, minZ);
        	
        	break;
        }
        
        tessellator.setBrightness(par1Block.getMixedBrightnessForBlock(renderer.blockAccess, x, y, z));
        float var6 = 1.0F;
        int var7 = par1Block.colorMultiplier(renderer.blockAccess, x, y, z);
        float var8 = (var7 >> 16 & 255) / 255.0F;
        float var9 = (var7 >> 8 & 255) / 255.0F;
        float var10 = (var7 & 255) / 255.0F;
        if (EntityRenderer.anaglyphEnable)
        {
            float var11 = (var8 * 30.0F + var9 * 59.0F + var10 * 11.0F) / 100.0F;
            float var12 = (var8 * 30.0F + var9 * 70.0F) / 100.0F;
            float var13 = (var8 * 30.0F + var10 * 70.0F) / 100.0F;
            var8 = var11;
            var9 = var12;
            var10 = var13;
        }
        tessellator.setColorOpaque_F(var6 * var8, var6 * var9, var6 * var10);
        
        int tex = getTexture(ClientProxy.TRIPMINE_RAY_PATH);
     
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
        
        addVertex(v1 , 0, 1);
        addVertex(v2 , 1, 1);
        addVertex(v3 , 1, 0);
        addVertex(v4 , 0, 0);

        addVertex(v4 , 0, 0);
        addVertex(v3 , 1, 0);
        addVertex(v2 , 1, 1);
        addVertex(v1 , 0, 1);
        
        addVertex(v5 , 0, 1);
        addVertex(v6 , 1, 1);
        addVertex(v7 , 1, 0);
        addVertex(v8 , 0, 0);
        
        addVertex(v8 , 0, 0);
        addVertex(v7 , 1, 0);
        addVertex(v6 , 1, 1);
        addVertex(v5 , 0, 1);
        return true;
    }
	
	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		// TODO Auto-generated method stub
		return CBCMod.RENDER_TYPE_TRIPMINE_RAY;
	}

    public void setBlockBounds(double par1, double par3, double par5, double par7, double par9, double par11)
    {
            this.minX = par1;
            this.maxX = par7;
            this.minY = par3;
            this.maxY = par9;
            this.minZ = par5;
            this.maxZ = par11;
    }
    
    public void addCoord(double offX, double offY, double offZ){
    	
    	minX += offX;
    	maxX += offX;
    	minY += offY;
    	maxY += offY;
    	minZ += offZ;
    	maxZ += offZ;
    	
    }
}
