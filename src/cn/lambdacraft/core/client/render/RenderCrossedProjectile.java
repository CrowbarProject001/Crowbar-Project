package cn.lambdacraft.core.client.render;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.utils.MotionXYZ;

import cpw.mods.fml.client.FMLClientHandler;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderEntity;
import net.minecraft.entity.Entity;
import net.minecraft.util.Vec3;

/**
 * 交叉投射物渲染（枪榴弹，RPG火箭使用）
 * 
 */
public class RenderCrossedProjectile extends Render {

	protected double LENGTH;
	protected double HEIGHT;
	protected String TEXTURE_PATH;
	protected boolean renderTexture = true;
	protected float colorR, colorG, colorB;
	protected boolean ignoreLight = false;
	
	public RenderCrossedProjectile(double l, double h, String texturePath) {
		LENGTH = l;
		HEIGHT = h;
		TEXTURE_PATH = texturePath;
	}
	
	public RenderCrossedProjectile(double l, double h, float a, float b, float c) {
		LENGTH = l;
		HEIGHT = h;
		setColor3f(a, b, c);
	}
	
	public RenderCrossedProjectile setColor3f(float a, float b, float c) {
		renderTexture = false;
		colorR = a;
		colorG = b;
		colorB = c;
		return this;
	}
	
	public RenderCrossedProjectile setIgnoreLight(boolean b) {
		ignoreLight = b;
		return this;
	}

	@Override
	public void doRender(Entity entity, double par2, double par4,
			double par6, float par8, float par9) {
		MotionXYZ motion = new MotionXYZ(entity);
		Tessellator tessellator = Tessellator.instance;

		GL11.glPushMatrix();

		Vec3 v1 = newV3(0, HEIGHT, 0), v2 = newV3(0, -HEIGHT, 0), v3 = newV3(
				LENGTH, -HEIGHT, 0), v4 = newV3(LENGTH, HEIGHT, 0),

		v5 = newV3(0, 0, -HEIGHT), v6 = newV3(0, 0, HEIGHT), v7 = newV3(LENGTH,
				0, HEIGHT), v8 = newV3(LENGTH, 0, -HEIGHT);

		GL11.glTranslatef((float) par2, (float) par4, (float) par6);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		
		if(renderTexture)
			loadTexture(TEXTURE_PATH);
		else {
			GL11.glDisable(GL11.GL_TEXTURE_2D);
			GL11.glColor3f(colorR, colorG, colorB);
		}
		
		if(ignoreLight)
			GL11.glDisable(GL11.GL_LIGHTING);
		
		GL11.glRotatef(270.0F - entity.rotationYaw, 0.0F, -1.0F, 0.0F); // 左右旋转
		GL11.glRotatef(entity.rotationPitch, 0.0F, 0.0F, -1.0F); // 上下旋转
		
		if(ignoreLight) 
			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f);
		tessellator.startDrawingQuads();
		if(ignoreLight) 
			tessellator.setBrightness(15728880);
		
		RenderUtils.addVertex(v1, 0, 1);
		RenderUtils.addVertex(v2, 1, 1);
		RenderUtils.addVertex(v3, 1, 0);
		RenderUtils.addVertex(v4, 0, 0);
		
		RenderUtils.addVertex(v5, 0, 1);
		RenderUtils.addVertex(v6, 1, 1);
		RenderUtils.addVertex(v7, 1, 0);
		RenderUtils.addVertex(v8, 0, 0);
		
		tessellator.draw(); 
		
		GL11.glEnable(GL11.GL_TEXTURE_2D);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

	protected int getTexture(String path) {
		return FMLClientHandler.instance().getClient().renderEngine
				.getTexture(path);
	}

	public static Vec3 newV3(double x, double y, double z) {
		return Vec3.createVectorHelper(x, y, z);
	}
}
