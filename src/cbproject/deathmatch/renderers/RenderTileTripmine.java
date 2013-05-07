package cbproject.deathmatch.renderers;

import org.lwjgl.opengl.GL11;

import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RendererUtils;
import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.blocks.weapons.BlockTripmine;
import cbproject.deathmatch.register.DMBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTileTripmine extends TileEntitySpecialRenderer {


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
	
	protected void setBlockBounds(double par1, double par3, double par5,
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
        int var5 = tileentity.getBlockMetadata() & 3;
        BlockTripmine block = (BlockTripmine)DMBlocks.blockTripmine;
        TileEntityTripmine tileEntity = (TileEntityTripmine) tileentity;
        
        block.setBlockBoundsBasedOnState(tileentity.worldObj, tileentity.xCoord, tileentity.yCoord, tileentity.zCoord);
        setBound(block);
        this.addCoord(x, y, z);
        Vec3 v1, v2, v3, v4, v5, v6, v7, v8;
        switch(var5){
        case 1:
        case 3:
        	v1 = RendererUtils.newV3(minX, minY, minZ);
        	v2 = RendererUtils.newV3(minX, minY, maxZ);
        	v3 = RendererUtils.newV3(minX, maxY, maxZ);
        	v4 = RendererUtils.newV3(minX, maxY, minZ);
        	
        	v5 = RendererUtils.newV3(maxX, minY, minZ);
        	v6 = RendererUtils.newV3(maxX, minY, maxZ);
        	v7 = RendererUtils.newV3(maxX, maxY, maxZ);
        	v8 = RendererUtils.newV3(maxX, maxY, minZ);
        	
        	break;
        	
        default:
        	v1 = RendererUtils.newV3(minX, minY, minZ);
        	v2 = RendererUtils.newV3(maxX, minY, minZ);
        	v3 = RendererUtils.newV3(maxX, maxY, minZ);
        	v4 = RendererUtils.newV3(minX, maxY, minZ);
        	
        	v5 = RendererUtils.newV3(minX, minY, maxZ);
        	v6 = RendererUtils.newV3(maxX, minY, maxZ);
        	v7 = RendererUtils.newV3(maxX, maxY, maxZ);
        	v8 = RendererUtils.newV3(minX, maxY, maxZ);
        	
        	break;
        }
        GL11.glPushMatrix();
        this.bindTextureByName(ClientProps.TRIPMINE_SIDE_PATH);

        tessellator.startDrawingQuads();
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
        
        this.bindTextureByName(ClientProps.TRIPMINE_FRONT_PATH);
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
        
        this.bindTextureByName(ClientProps.TRIPMINE_TOP_PATH);
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
        GL11.glPopMatrix();
        
        //Tripmine ray drawing
        float h = 0.025F;
        double du = tileEntity.getRayDistance();
		MotionXYZ end = new MotionXYZ(tileEntity.endX, tileEntity.endY, tileEntity.endZ, 0, 0, 0);
		minY = y + 0.5 - h;
		maxY = y + 0.5 + h;
		switch(var5){
		case 3:
			minX = x - 1;
			maxX = x + du;
			minZ = z + 0.5 - h;
			maxZ = z + 0.5 + h;
			break;
		case 1:
			minX = x - du;
			maxX = x + 1;
			minZ = z + 0.5 - h;
			maxZ = z + 0.5 + h;
			break;
		case 0:
			minZ = z - 1;
			maxZ = z + du;
			minX = x + 0.5 - h;
			maxX = x + 0.5 + h;
			break;
		case 2:
			minZ = z - du;
			maxZ = z + 1;
			minX = x + 0.5 - h;
			maxX = x + 0.5 + h;
			break;
		}
		
        this.bindTextureByName(ClientProps.TRIPMINE_RAY_PATH);
        v1 = RendererUtils.newV3(minX, y+0.5, minZ);
     	v2 = RendererUtils.newV3(minX, y+0.5, maxZ);
     	v3 = RendererUtils.newV3(maxX, y+0.5, maxZ);
     	v4 = RendererUtils.newV3(maxX, y+0.5, minZ);
        switch(var5){
        case 1:
        case 3:
        	v5 = RendererUtils.newV3(minX, maxY, z + 0.5);
        	v6 = RendererUtils.newV3(maxX, maxY, z + 0.5);
        	v7 = RendererUtils.newV3(maxX, minY, z + 0.5);
        	v8 = RendererUtils.newV3(minX, minY, z + 0.5);
        	break;
        	
        default:
        	v5 = RendererUtils.newV3(x+0.5, maxY, minZ);
        	v6 = RendererUtils.newV3(x+0.5, maxY, maxZ);
        	v7 = RendererUtils.newV3(x+0.5, minY, maxZ);
        	v8 = RendererUtils.newV3(x+0.5, minY, minZ);
        	
        	break;
        }
        
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glColor3d(1.0, 1.0, 1.0);
        tessellator.startDrawingQuads();
        
	    addVertex(v1 , 0, 1);
        addVertex(v2 , du, 1);
        addVertex(v3 , du, 0);
        addVertex(v4 , 0, 0);

        addVertex(v4 , 0, 0);
        addVertex(v3 , du, 0);
        addVertex(v2 , du, 1);
        addVertex(v1 , 0, 1);
        
        addVertex(v5 , 0, 1);
        addVertex(v6 , du, 1);
        addVertex(v7 , du, 0);
        addVertex(v8 , 0, 0);
        
        addVertex(v8 , 0, 0);
        addVertex(v7 , du, 0);
        addVertex(v6 , du, 1);
        addVertex(v5 , 0, 1);
        

        tessellator.draw();

        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glPopMatrix();
        
        return;
	}
	

}
