/**
 * 
 */
package cbproject.elements.renderers;

import org.lwjgl.opengl.GL11;

import cbproject.CBCMod;
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
public class RenderTripmine implements ISimpleBlockRenderingHandler {
	
    /** The minimum X value for rendering (default 0.0). */
    public double minX;

    /** The maximum X value for rendering (default 1.0). */
    public double maxX;

    /** The minimum Y value for rendering (default 0.0). */
    public double minY;

    /** The maximum Y value for rendering (default 1.0). */
    public double maxY;

    /** The minimum Z value for rendering (default 0.0). */
    public double minZ;

    /** The maximum Z value for rendering (default 1.0). */
    public double maxZ;

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
    
    public void swap(double a, double b){
    	if( a > b ){
    		double c = a;
    		a = b;
    		b = c;
    	}
    }
	
    public boolean renderBlockTripmine(RenderBlocks renderer, Block par1Block,int metadata, int x, int y, int z, IBlockAccess blockAccess)
    {
    	
        Tessellator tessellator = Tessellator.instance;
        int var5 = metadata & 3;
        float var6 = 0.25F;
        float var7 =  0.3F;
        /*
        if (var5 == 3) //X+
        {
            this.setBlockBounds(0.0F, 0.3F, 0.5F - var7, var6 * 2.0F, 0.7F, 0.5F + var7); // (0, 0.5) (0.3, 0.7), (0.2, 0.8)
        }
        else if (var5 == 1) //X-
        {
            this.setBlockBounds(1.0F - var6 * 2.0F, 0.3F, 0.5F - var7, 1.0F, 0.7F, 0.5F + var7);
        }
        else if (var5 == 0) //Z+
        {
            this.setBlockBounds(0.5F - var7, 0.3F, 0.0F, 0.5F + var7, 0.7F, var6 * 2.0F);
        }
        else if (var5 == 2) //Z-
        {
            this.setBlockBounds(0.5F - var7, 0.3F, 1.0F - var6 * 2.0F, 0.5F + var7, 0.7F, 1.0F);
        }
        */
        setBound(par1Block);
        this.addCoord(x, y, z);
        swap(minX, maxX);
        swap(minY, maxY);
        swap(minZ, maxZ);
        
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
        
        
        /* 渲染6面
         * 1 ： v1 v2 v3 v4
         * 2 : v5 v6 v7 v8
         * 3 : v1 v5 v8 v4
         * 4 : v2 v6 v7 v3
         * 5 : v1 v5 v6 v2
         * 6 : v4 v8 v7 v3
         */
        int side = getTexture(ClientProxy.TRIPMINE_SIDE_PATH), front = getTexture(ClientProxy.TRIPMINE_FRONT_PATH),
        		top = getTexture(ClientProxy.TRIPMINE_TOP_PATH);

        
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
        return true;
    }
	
    private void addVertex(Vec3 vec3, double texU, double texV){
    	Tessellator tessellator = Tessellator.instance;
    	tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
    }
    
    private void setBound(Block block){
    	
    	minX = block.getBlockBoundsMinX();
    	minY = block.getBlockBoundsMinY();
    	minZ = block.getBlockBoundsMinZ();
    	maxX = block.getBlockBoundsMaxX();
    	maxY = block.getBlockBoundsMaxY();
    	maxZ = block.getBlockBoundsMaxZ();
    	
    }
    
    private int getTexture(String path){
    	return FMLClientHandler.instance().getClient().renderEngine.getTexture(path);
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
