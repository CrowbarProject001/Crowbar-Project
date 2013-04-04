package cbproject.elements.renderers.weapons;

import org.lwjgl.opengl.GL11;

import cbproject.elements.entities.weapons.EntityGauss;
import cbproject.proxy.ClientProxy;

import cpw.mods.fml.client.FMLClientHandler;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class RenderGaussRay extends RenderEntity {

	public static double RADIUS = 0.05F;
	@Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
		EntityGauss gauss = (EntityGauss)par1Entity;
		Vec3 begin = gauss.start;
		Vec3 end = gauss.end;
		System.out.println("Doing render");
        GL11.glPushMatrix();
        
        int tex = getTexture(ClientProxy.GAUSS_BEAM_PATH);
        GL11.glEnable(GL11.GL_TEXTURE_2D);
        GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
        Tessellator tessellator = Tessellator.instance;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        
        Vec3 v1, v2, v3, v4, v5, v6;
        v1 = Vec3.createVectorHelper(begin.xCoord - RADIUS, begin.yCoord - RADIUS, begin.zCoord - RADIUS);
        v2 = Vec3.createVectorHelper(begin.xCoord, begin.yCoord + RADIUS, begin.zCoord);
        v3 = Vec3.createVectorHelper(begin.xCoord + RADIUS, begin.yCoord - RADIUS, begin.zCoord + RADIUS);
        
        v4 = Vec3.createVectorHelper(end.xCoord - RADIUS, end.yCoord - RADIUS, end.zCoord - RADIUS);
        v5 = Vec3.createVectorHelper(end.xCoord, end.yCoord + RADIUS, end.zCoord);
        v6 = Vec3.createVectorHelper(end.xCoord + RADIUS, end.yCoord - RADIUS, end.zCoord + RADIUS);
        
        /*
         * 1,4,5,2
         * 3,2,5,6
         * 1,4,6,3
         */
        tessellator.startDrawingQuads();
        
        addVertex(v1, 0, 0);
        addVertex(v4, 0, 1);
        addVertex(v5, 1, 1);
        addVertex(v2, 1, 0);
        
        addVertex(v3, 0, 0);
        addVertex(v2, 0, 1);
        addVertex(v5, 1, 1);
        addVertex(v6, 1, 0);
        
        addVertex(v1, 0, 0);
        addVertex(v4, 0, 1);
        addVertex(v6, 1, 1);
        addVertex(v3, 1, 0);	
        
        tessellator.draw();
        
        GL11.glPopMatrix();
    }
	
    protected int getTexture(String path){
    	return FMLClientHandler.instance().getClient().renderEngine.getTexture(path);
    }
    
    protected void addVertex(Vec3 vec3, double texU, double texV){
    	Tessellator tessellator = Tessellator.instance;
    	tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
    }
    
}
