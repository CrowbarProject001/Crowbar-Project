package cn.lambdacraft.core.client;

import java.util.Map;
import java.util.Random;

import org.lwjgl.opengl.GL11;

import com.google.common.collect.Maps;

import cn.lambdacraft.core.client.shape.*;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.Vec3;

/**
 * 一些有用的渲染器功能。
 * 
 * @author WeAthFolD
 * 
 */
public class RenderUtils {

	public static ShapeCircle shapeCircle = new ShapeCircle();
	public static ShapeSphere shapeSphere = new ShapeSphere();
	private static Tessellator t = Tessellator.instance;
	public static Random rand = new Random();
	
	private static Map<String, ResourceLocation> srcMap  = Maps.newHashMap();
	
	public static void renderShadow_Held() {
		GL11.glDepthFunc(GL11.GL_EQUAL);
    	GL11.glDisable(GL11.GL_LIGHTING);
    	Minecraft.getMinecraft().renderEngine.func_110577_a(new ResourceLocation(("textures/misc/enchanted_item_glint.png")));
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
    	float f7 = 1.00F;
    	GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
    	GL11.glMatrixMode(GL11.GL_TEXTURE);
    	GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        ItemRenderer.renderItemIn2D(Tessellator.instance, 0.0F, 0.0F, 1.0F, 1.0F, 256, 256, 0.0625F);
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
	}
	
	public static void renderShadow_Inventory() {
		GL11.glDepthFunc(GL11.GL_EQUAL);
    	GL11.glDisable(GL11.GL_LIGHTING);
    	loadTexture("textures/misc/enchanted_item_glint.png");
    	GL11.glEnable(GL11.GL_BLEND);
    	GL11.glBlendFunc(GL11.GL_SRC_COLOR, GL11.GL_ONE);
    	float f7 = 0.76F;
    	GL11.glColor4f(0.5F * f7, 0.25F * f7, 0.8F * f7, 1.0F);
    	GL11.glMatrixMode(GL11.GL_TEXTURE);
    	GL11.glPushMatrix();
        float f8 = 0.125F;
        GL11.glScalef(f8, f8, f8);
        float f9 = Minecraft.getSystemTime() % 3000L / 3000.0F * 8.0F;
        GL11.glTranslatef(f9, 0.0F, 0.0F);
        GL11.glRotatef(-50.0F, 0.0F, 0.0F, 1.0F);
        t.startDrawingQuads();
        t.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
		t.addVertexWithUV(0.0, 16.0, 0.0, 0.0, 1.0);
		t.addVertexWithUV(16.0, 16.0, 0.0, 1.0, 1.0);
		t.addVertexWithUV(16.0, 0.0, 0.0, 1.0, 0.0);
        t.draw();
        GL11.glPopMatrix();
        GL11.glPushMatrix();
        GL11.glScalef(f8, f8, f8);
        f9 = Minecraft.getSystemTime() % 4873L / 4873.0F * 8.0F;
        GL11.glTranslatef(-f9, 0.0F, 0.0F);
        GL11.glRotatef(10.0F, 0.0F, 0.0F, 1.0F);
        t.startDrawingQuads();
        t.addVertexWithUV(0.0, 0.0, 0.0, 0.0, 0.0);
		t.addVertexWithUV(0.0, 16.0, 0.0, 0.0, 1.0);
		t.addVertexWithUV(16.0, 16.0, 0.0, 1.0, 1.0);
		t.addVertexWithUV(16.0, 0.0, 0.0, 1.0, 0.0);
        t.draw();
        GL11.glPopMatrix();
        GL11.glMatrixMode(GL11.GL_MODELVIEW);
        GL11.glDisable(GL11.GL_BLEND);
        GL11.glEnable(GL11.GL_LIGHTING);
        GL11.glDepthFunc(GL11.GL_LEQUAL);
	}
	
	public static void renderItemInventory(ItemStack item) {
		Icon icon = item.getIconIndex();
		if(icon != null) {
			t.startDrawingQuads();
			t.addVertexWithUV(0.0, 0.0, 0.0, icon.getMinU(), icon.getMinV());
			t.addVertexWithUV(0.0, 16.0, 0.0, icon.getMinU(), icon.getMaxV());
			t.addVertexWithUV(16.0, 16.0, 0.0, icon.getMaxU(), icon.getMaxV());
			t.addVertexWithUV(16.0, 0.0, 0.0, icon.getMaxU(), icon.getMinV());
			t.draw();
		}
	}
	
	/**
	 * 添加带UV的三维顶点。
	 * 
	 * @param vec3
	 * @param texU
	 * @param texV
	 */
	public static void addVertex(Vec3 vec3, double texU, double texV) {
		t.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord, texU, texV);
	}

	/**
	 * 添加不带UV的三维顶点。
	 * 
	 * @param vec3
	 */
	public static void addVertex(Vec3 vec3) {
		t.addVertex(vec3.xCoord, vec3.yCoord, vec3.zCoord);
	}

	/**
	 * 获取某个贴图的ID。
	 * 
	 * @param path
	 *            贴图路径
	 * @return
	 */
	//public static int getTexture(String path) {
	//	ResourceLocation r=new ResourceLocation(path);
	//	TextureObject a=Minecraft.getMinecraft().renderEngine
	//			.func_110581_b(r);
	//	return a.func_110552_b();
	//}

	/**
	 * 获取并加载一个贴图。
	 * 
	 * @param path
	 *            贴图路径
	 */
	public static void loadTexture(String path) {
		ResourceLocation src = srcMap.get(path);
		if(src == null) {
			src = new ResourceLocation(path);
			srcMap.put(path, src);
		}
		Minecraft.getMinecraft().renderEngine.func_110577_a(src);
	}

	public static void renderItemIn2d(EntityLivingBase entity,
			ItemStack stackToRender, double w) {
		renderItemIn2d(entity, stackToRender, w, null);
	}

	/**
	 * 将Item渲染成一个有厚度的薄片。
	 * 
	 * @param t
	 * @param w
	 *            宽度
	 */
	public static void renderItemIn2d(Entity par1EntityLivingBase,
			ItemStack stackToRender, double w, Icon specialIcon) {

		Vec3 a1 = newV3(0, 0, w), a2 = newV3(1, 0, w), a3 = newV3(1, 1, w), a4 = newV3(
				0, 1, w), a5 = newV3(0, 0, -w), a6 = newV3(1, 0, -w), a7 = newV3(
				1, 1, -w), a8 = newV3(0, 1, -w);

		Icon icon = stackToRender.getIconIndex();
		if (specialIcon != null)
			icon = specialIcon;

		Minecraft mc = Minecraft.getMinecraft();

		if (icon == null) 
			return;

		mc.renderEngine.func_110577_a(mc.renderEngine.func_130087_a(stackToRender.getItemSpriteNumber()));
		
		//if (stackToRender.getItemSpriteNumber() == 0) {
		//	mc.renderEngine.bindTexture("/terrain.png");
		//} else {
		//	mc.renderEngine.bindTexture("/gui/items.png");
		//}

		float u1 = 0.0F, v1 = 0.0F, u2 = 0.0F, v2 = 0.0F;
		u1 = icon.getMinU();
		v1 = icon.getMinV();
		u2 = icon.getMaxU();
		v2 = icon.getMaxV();

		Tessellator t;
		t = Tessellator.instance;
		GL11.glPushMatrix();
		t.startDrawingQuads();
		t.setNormal(0.0F, 0.0F, 1.0F);
		addVertex(a1, u2, v2);
		addVertex(a2, u1, v2);
		addVertex(a3, u1, v1);
		addVertex(a4, u2, v1);
		t.draw();

		t.startDrawingQuads();
		t.setNormal(0.0F, 0.0F, -1.0F);
		addVertex(a8, u2, v1);
		addVertex(a7, u1, v1);
		addVertex(a6, u1, v2);
		addVertex(a5, u2, v2);
		t.draw();

		int var7;
		float var8;
		float var9;
		float var10;
		/*
		 * Gets the width/16 of the currently bound texture, used to fix the
		 * side rendering issues on textures != 16
		 */
		int tileSize = 32;
		float tx = 1.0f / (32 * tileSize);
		float tz = 1.0f / tileSize;

		t.startDrawingQuads();
		t.setNormal(-1.0F, 0.0F, 0.0F);
		for (var7 = 0; var7 < tileSize; ++var7) {
			var8 = (float) var7 / tileSize;
			var9 = u2 - (u2 - u1) * var8 - tx;
			var10 = 1.0F * var8;
			t.addVertexWithUV(var10, 0.0D, -w, var9, v2);
			t.addVertexWithUV(var10, 0.0D, w, var9, v2);
			t.addVertexWithUV(var10, 1.0D, w, var9, v1);
			t.addVertexWithUV(var10, 1.0D, -w, var9, v1);

			t.addVertexWithUV(var10, 1.0D, w, var9, v1);
			t.addVertexWithUV(var10, 0.0D, w, var9, v2);
			t.addVertexWithUV(var10, 0.0D, -w, var9, v2);
			t.addVertexWithUV(var10, 1.0D, -w, var9, v1);
		}
		t.draw();

		GL11.glPopMatrix();

	}

	/**
	 * Renders an item held in hand as a 2D texture with thickness(Old version)
	 */
	public static void renderItemIn2D(Tessellator t, float par1,
			float par2, float par3, float par4, int par5, int par6, float par7) {
		t.startDrawingQuads();
		t.setNormal(0.0F, 0.0F, 1.0F);
		t.addVertexWithUV(0.0D, 0.0D, 0.0D, par1, par4);
		t.addVertexWithUV(1.0D, 0.0D, 0.0D, par3, par4);
		t.addVertexWithUV(1.0D, 1.0D, 0.0D, par3, par2);
		t.addVertexWithUV(0.0D, 1.0D, 0.0D, par1, par2);
		t.draw();
		t.startDrawingQuads();
		t.setNormal(0.0F, 0.0F, -1.0F);
		t.addVertexWithUV(0.0D, 1.0D, 0.0F - par7, par1, par2);
		t.addVertexWithUV(1.0D, 1.0D, 0.0F - par7, par3, par2);
		t.addVertexWithUV(1.0D, 0.0D, 0.0F - par7, par3, par4);
		t.addVertexWithUV(0.0D, 0.0D, 0.0F - par7, par1, par4);
		t.draw();
		float f5 = par5 * (par1 - par3);
		float f6 = par6 * (par4 - par2);
		t.startDrawingQuads();
		t.setNormal(-1.0F, 0.0F, 0.0F);
		int k;
		float f7;
		float f8;

		for (k = 0; k < f5; ++k) {
			f7 = k / f5;
			f8 = par1 + (par3 - par1) * f7 - 0.5F / par5;
			t.addVertexWithUV(f7, 0.0D, 0.0F - par7, f8, par4);
			t.addVertexWithUV(f7, 0.0D, 0.0D, f8, par4);
			t.addVertexWithUV(f7, 1.0D, 0.0D, f8, par2);
			t.addVertexWithUV(f7, 1.0D, 0.0F - par7, f8, par2);
		}

		t.draw();
		t.startDrawingQuads();
		t.setNormal(1.0F, 0.0F, 0.0F);
		float f9;

		for (k = 0; k < f5; ++k) {
			f7 = k / f5;
			f8 = par1 + (par3 - par1) * f7 - 0.5F / par5;
			f9 = f7 + 1.0F / f5;
			t.addVertexWithUV(f9, 1.0D, 0.0F - par7, f8, par2);
			t.addVertexWithUV(f9, 1.0D, 0.0D, f8, par2);
			t.addVertexWithUV(f9, 0.0D, 0.0D, f8, par4);
			t.addVertexWithUV(f9, 0.0D, 0.0F - par7, f8, par4);
		}

		t.draw();
		t.startDrawingQuads();
		t.setNormal(0.0F, 1.0F, 0.0F);

		for (k = 0; k < f6; ++k) {
			f7 = k / f6;
			f8 = par4 + (par2 - par4) * f7 - 0.5F / par6;
			f9 = f7 + 1.0F / f6;
			t.addVertexWithUV(0.0D, f9, 0.0D, par1, f8);
			t.addVertexWithUV(1.0D, f9, 0.0D, par3, f8);
			t.addVertexWithUV(1.0D, f9, 0.0F - par7, par3, f8);
			t.addVertexWithUV(0.0D, f9, 0.0F - par7, par1, f8);
		}

		t.draw();
		t.startDrawingQuads();
		t.setNormal(0.0F, -1.0F, 0.0F);

		for (k = 0; k < f6; ++k) {
			f7 = k / f6;
			f8 = par4 + (par2 - par4) * f7 - 0.5F / par6;
			t.addVertexWithUV(1.0D, f7, 0.0D, par3, f8);
			t.addVertexWithUV(0.0D, f7, 0.0D, par1, f8);
			t.addVertexWithUV(0.0D, f7, 0.0F - par7, par1, f8);
			t.addVertexWithUV(1.0D, f7, 0.0F - par7, par3, f8);
		}

		t.draw();
	}

	/**
	 * 创建一个新的Vec3顶点。
	 * 
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static Vec3 newV3(double x, double y, double z) {
		return Vec3.createVectorHelper(x, y, z);
	}

}
