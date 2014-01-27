/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.client.render;

import org.lwjgl.opengl.GL11;

import cn.graphrevo.proxy.GRClientProps;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

/**
 * 冰冻魔法效果的渲染器。
 * @author WeAthFolD
 */
public class RenderFrozen extends Render {
	
	public RenderFrozen() {
	}

	/* 
	 * 进行实际渲染的函数。
	 */
	@Override
	public void doRender(Entity entity, double x, double y, double z,
			float f, float f1) {
		Tessellator t = Tessellator.instance; //获取实例
		TextureManager renderEngine = Minecraft.getMinecraft().renderEngine; //获取渲染引擎（读作TextureManager 233）
		final double WIDTH = 1.0F, LENGTH = 2 * WIDTH; //实际上是半WIDTH和半LENGTH
		
		GL11.glPushMatrix(); //推栈
		
		renderEngine.bindTexture(GRClientProps.TEX_FROZEN_MAGIC); //绑定贴图
		GL11.glTranslated(x, y, z); //贴图位移
		GL11.glDisable(GL11.GL_LIGHTING); //关闭光照
		GL11.glEnable(GL11.GL_BLEND); //开启混合
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA); //混合模式设定
		GL11.glDisable(GL11.GL_CULL_FACE); //关闭面剔除
		
		GL11.glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F); //横向旋转
		GL11.glRotatef(-entity.rotationPitch, 1.0F, 0.0F, 0.0F);//上下旋转
		
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, 240f, 240f); //光照相关
		t.startDrawingQuads(); //开始绘制
		t.setBrightness(15728880); //光照相关
		
		//典型的正交绘制方法，横向+纵向
		t.addVertexWithUV(-WIDTH, 0, -LENGTH, 0.0, 0.0); //左上
		t.addVertexWithUV(WIDTH, 0, -LENGTH, 0.0, 1.0); //左下
		t.addVertexWithUV(WIDTH, 0, LENGTH, 1.0, 1.0); //右下
		t.addVertexWithUV(-WIDTH, 0, LENGTH, 1.0, 0.0); //右上
		
		t.addVertexWithUV(0, -WIDTH, -LENGTH, 0.0, 0.0); //左上
		t.addVertexWithUV(0, WIDTH, -LENGTH, 0.0, 1.0); //左下
		t.addVertexWithUV(0, WIDTH, LENGTH, 1.0, 1.0); //右下
		t.addVertexWithUV(0, -WIDTH, LENGTH, 1.0, 0.0); //右上
		
		t.draw();
		
		//恢复原本设定
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_BLEND); 
		GL11.glPopMatrix(); //弹栈
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		return GRClientProps.TEX_FROZEN_MAGIC; //我也不知道这个函数有什么用
	}

}
