package cbproject.deathmatch.renderers;

import org.lwjgl.opengl.GL11;

import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RenderUtils;
import cbproject.core.renderers.RendererSidedCube;
import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.blocks.BlockTripmine;
import cbproject.deathmatch.blocks.tileentities.TileEntityTripmine;
import cbproject.deathmatch.register.DMBlocks;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;

public class RenderTileTripmine extends RendererSidedCube {


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
	
	public RenderTileTripmine(Block b){
		super(b);
	}
	
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double x, double y,
			double z, float f) {
		
		this.doRender(tileentity, x, y, z, f);
		
		Tessellator tessellator = Tessellator.instance;
        int var5 = tileentity.getBlockMetadata();
        BlockTripmine block = DMBlocks.blockTripmine;
        TileEntityTripmine tileEntity = (TileEntityTripmine) tileentity;

        Vec3 v1, v2, v3, v4, v5, v6, v7, v8;
        //Tripmine ray drawing
        float h = 0.025F;
        double du = tileEntity.getRayDistance();
		MotionXYZ end = new MotionXYZ(tileEntity.endX, tileEntity.endY, tileEntity.endZ, 0, 0, 0);
		minY = y + 0.5 - h;
		maxY = y + 0.5 + h;
		switch(var5){
		case 5:
			minX = x - 1;
			maxX = x + du;
			minZ = z + 0.5 - h;
			maxZ = z + 0.5 + h;
			break;
		case 4:
			minX = x - du;
			maxX = x + 1;
			minZ = z + 0.5 - h;
			maxZ = z + 0.5 + h;
			break;
		case 3:
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
        v1 = RenderUtils.newV3(minX, y+0.5, minZ);
     	v2 = RenderUtils.newV3(minX, y+0.5, maxZ);
     	v3 = RenderUtils.newV3(maxX, y+0.5, maxZ);
     	v4 = RenderUtils.newV3(maxX, y+0.5, minZ);
        switch(var5){
        case 5:
        case 4:
        	v5 = RenderUtils.newV3(minX, maxY, z + 0.5);
        	v6 = RenderUtils.newV3(maxX, maxY, z + 0.5);
        	v7 = RenderUtils.newV3(maxX, minY, z + 0.5);
        	v8 = RenderUtils.newV3(minX, minY, z + 0.5);
        	break;
        	
        default:
        	v5 = RenderUtils.newV3(x+0.5, maxY, minZ);
        	v6 = RenderUtils.newV3(x+0.5, maxY, maxZ);
        	v7 = RenderUtils.newV3(x+0.5, minY, maxZ);
        	v8 = RenderUtils.newV3(x+0.5, minY, minZ);
        	
        	break;
        }
        
        GL11.glPushMatrix();
        GL11.glDisable(GL11.GL_LIGHTING);
        GL11.glBlendFunc(GL11.GL_ONE, GL11.GL_ONE_MINUS_SRC_ALPHA);
        tessellator.startDrawingQuads();
        tessellator.setColorRGBA_F(1, 1, 1, 0.7F);
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
        
	}

	@Override
	public String getTexture(int side, int metadata) {
		if(side == 0 || side == 1)
			return ClientProps.TRIPMINE_TOP_PATH;
		if(side == metadata)
			return ClientProps.TRIPMINE_FRONT_PATH;
		return ClientProps.TRIPMINE_SIDE_PATH;
	}
	

}
