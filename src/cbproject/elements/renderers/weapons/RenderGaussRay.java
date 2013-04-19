package cbproject.elements.renderers.weapons;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;

import cbproject.elements.entities.weapons.EntityGaussRay;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.MotionXYZ;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import static cbproject.elements.renderers.RendererUtils.newV3;
/**
 * Gauss ray rendering class.
 * @author WeAthFolD
 *
 */
public class RenderGaussRay extends RenderEntity {

	public static double RADIUS = 0.05F;
	private static Tessellator tessellator = Tessellator.instance;
	
	@Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
		
		EntityGaussRay gauss = (EntityGaussRay)par1Entity;
		MotionXYZ motion = new MotionXYZ(gauss);
		MovingObjectPosition trace = gauss.worldObj.rayTraceBlocks(motion.asVec3(gauss.worldObj), motion.updateMotion(100.0F).asVec3(gauss.worldObj));
		Vec3 end = (trace == null)? motion.asVec3(gauss.worldObj) : trace.hitVec;
		tessellator = tessellator.instance;
		
        GL11.glPushMatrix();
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);
        GL11.glDepthMask(false);
        GL11.glDisable(GL11.GL_LIGHTING);
        loadTexture(ClientProxy.GAUSS_BEAM_PATH);
        double dx = end.xCoord - gauss.posX;
        double dy = end.yCoord - gauss.posY;
        double dz = end.zCoord - gauss.posZ;
        
        Vec3 v1, v2, v3, v4, v5, v6;
        v1 = newV3(- RADIUS,- RADIUS,- RADIUS);
        v2 = newV3(0, RADIUS, 0);
        v3 = newV3(RADIUS,-RADIUS,RADIUS);
        
        v4 = newV3(dx - RADIUS, dy - RADIUS, dz - RADIUS);
        v5 = newV3(dx, dy + RADIUS, dz);
        v6 = newV3(dx + RADIUS, dy - RADIUS, dz + RADIUS);
        
        double max = Math.abs(dx);
        
        tessellator.startDrawingQuads();
        
        addVertex(v5, 1, max);
        addVertex(v4, 0, max);
        addVertex(v1, 0, 0);
        addVertex(v2, 1, 0);
        
        addVertex(v5, 1, max);
        addVertex(v2, 1, 0);
        addVertex(v3, 0, 0);
        addVertex(v6, 0, max);
        
        addVertex(v1, 0, 0);
        addVertex(v4, 0, max); 
        addVertex(v6, 1, max);  
        addVertex(v3, 1, 0);	
        
        tessellator.draw();
        
        GL11.glPopMatrix();
    }
	
    protected int getTexture(String path){
    	return FMLClientHandler.instance().getClient().renderEngine.getTexture(path);
    }
    
    protected void addVertex(Vec3 vec3, double texU, double texV){
    	tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
    }
    
}
