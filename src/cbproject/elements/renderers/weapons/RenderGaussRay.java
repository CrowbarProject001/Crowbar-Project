package cbproject.elements.renderers.weapons;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;

public class RenderGaussRay extends RenderEntity {

	public RenderGaussRay() {
		// TODO Auto-generated constructor stub
	}
	
	@Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9)
    {
        GL11.glPushMatrix();
        renderOffsetAABB(par1Entity.boundingBox, par2 - par1Entity.lastTickPosX, par4 - par1Entity.lastTickPosY, par6 - par1Entity.lastTickPosZ);
        GL11.glDisable(GL11.GL_TEXTURE_2D);
        Tessellator var7 = Tessellator.instance;
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        var7.startDrawingQuads();
        var7.setNormal(0.0F, 0.0F, -1.0F);
        var7.setNormal(0.0F, 0.0F, 1.0F);
        var7.setNormal(0.0F, -1.0F, 0.0F);
        var7.setNormal(0.0F, 1.0F, 0.0F);
        var7.setNormal(-1.0F, 0.0F, 0.0F);
        var7.setNormal(1.0F, 0.0F, 0.0F);
        var7.setTranslation(0.0D, 0.0D, 0.0D);
        var7.draw();
        GL11.glPopMatrix();
    }
	
}
