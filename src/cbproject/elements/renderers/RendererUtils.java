package cbproject.elements.renderers;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import cpw.mods.fml.client.FMLClientHandler;

public class RendererUtils {
	
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
    
    protected void addVertex(Vec3 vec3, double texU, double texV){
    	Tessellator tessellator = Tessellator.instance;
    	tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
    }
    
    protected void setBound(Block block){
    	
    	minX = block.getBlockBoundsMinX();
    	minY = block.getBlockBoundsMinY();
    	minZ = block.getBlockBoundsMinZ();
    	maxX = block.getBlockBoundsMaxX();
    	maxY = block.getBlockBoundsMaxY();
    	maxZ = block.getBlockBoundsMaxZ();
    	
    }
    
    protected int getTexture(String path){
    	return FMLClientHandler.instance().getClient().renderEngine.getTexture(path);
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
