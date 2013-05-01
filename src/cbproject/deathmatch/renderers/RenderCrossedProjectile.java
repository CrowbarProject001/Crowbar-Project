package cbproject.deathmatch.renderers;

import org.lwjgl.opengl.GL11;

import cbproject.core.utils.MotionXYZ;
import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

public class RenderCrossedProjectile extends RenderEntity {

	protected double LENGTH;
	protected double HEIGHT;
	protected String TEXTURE_PATH;
	private static Tessellator tessellator = Tessellator.instance;
	
	public RenderCrossedProjectile(double l, double h, String texturePath) {
		LENGTH = l;
		HEIGHT = h;
		TEXTURE_PATH = texturePath;
	}
	
	@Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
		Entity gren = par1Entity;
		MotionXYZ motion = new MotionXYZ(gren);
		tessellator = Tessellator.instance;
		
        GL11.glPushMatrix();
        
        Vec3    v1 = newV3(0, HEIGHT,  0), 
        		v2 = newV3(0, -HEIGHT, 0), 
        		v3 = newV3(LENGTH, -HEIGHT, 0),
        		v4 = newV3(LENGTH, HEIGHT, 0),
        		
        		v5 = newV3(0, 0, -HEIGHT), 
        		v6 = newV3(0, 0, HEIGHT),
        		v7 = newV3(LENGTH, 0, HEIGHT),
        		v8 = newV3(LENGTH, 0, -HEIGHT);
        
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        GL11.glTranslatef((float)par2, (float)par4, (float)par6);   
        GL11.glDepthMask(false);
        loadTexture(TEXTURE_PATH);
        GL11.glRotatef(gren.rotationYaw - 270.0F, 0.0F, -1.0F, 0.0F); //左右旋转
        GL11.glRotatef(gren.rotationPitch, 0.0F, 0.0F, -1.0F); //上下旋转
        tessellator.startDrawingQuads();
        
        addVertex(v1, 0, 1);
        addVertex(v2, 1, 1);
        addVertex(v3, 1, 0);
        addVertex(v4, 0, 0);
        
        addVertex(v4, 0, 0);
        addVertex(v3, 1, 0);
        addVertex(v2, 1, 1);
        addVertex(v1, 0, 1);
              
        addVertex(v5, 0, 1);
        addVertex(v6, 1, 1);
        addVertex(v7, 1, 0);
        addVertex(v8, 0, 0);
       
        addVertex(v8, 0, 0);
        addVertex(v7, 1, 0);
        addVertex(v6, 1, 1);
        addVertex(v5, 0, 1);
        
        tessellator.draw();
        GL11.glPopMatrix();
    }
	
    protected int getTexture(String path){
    	return FMLClientHandler.instance().getClient().renderEngine.getTexture(path);
    }
    
    protected void addVertex(Vec3 vec3, double texU, double texV){
    	
    	tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
    }
    
    public static Vec3 newV3(double x, double y, double z){
    	return Vec3.createVectorHelper(x, y, z);
    }
}
