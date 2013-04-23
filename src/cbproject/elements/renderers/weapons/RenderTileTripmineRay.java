package cbproject.elements.renderers.weapons;

import org.lwjgl.opengl.GL11;

import cbproject.proxy.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.EntityRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTileTripmineRay extends TileEntitySpecialRenderer {

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

	public static void addVertex(Vec3 vec3, double texU, double texV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}

	protected void setBound(Block block) {
		minX = block.getBlockBoundsMinX();
		minY = block.getBlockBoundsMinY();
		minZ = block.getBlockBoundsMinZ();
		maxX = block.getBlockBoundsMaxX();
		maxY = block.getBlockBoundsMaxY();
		maxZ = block.getBlockBoundsMaxZ();
	}
	
	public void setBlockBounds(double par1, double par3, double par5,
			double par7, double par9, double par11) {
		this.minX = par1;
		this.maxX = par7;
		this.minY = par3;
		this.maxY = par9;
		this.minZ = par5;
		this.maxZ = par11;
	}

	public void addCoord(double offX, double offY, double offZ) {

		minX += offX;
		maxX += offX;
		minY += offY;
		maxY += offY;
		minZ += offZ;
		maxZ += offZ;

	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float f) {
		
	       	Tessellator tessellator = Tessellator.instance;
	       	this.bindTextureByName(ClientProxy.TRIPMINE_RAY_PATH);
	        int var5 = tileentity.getBlockMetadata();
	        setBound(tileentity.blockType);
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
	        GL11.glPushMatrix();
	        GL11.glDisable(GL11.GL_LIGHTING);
	        
	        tessellator.startDrawingQuads();
	        
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
	        

	        tessellator.draw();

	        GL11.glEnable(GL11.GL_LIGHTING);
	        GL11.glPopMatrix();
	}

}
