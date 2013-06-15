package cbproject.core.renderers;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Icon;
import net.minecraft.util.Vec3;
import cpw.mods.fml.client.FMLClientHandler;

/**
 * 一些有用的渲染器功能。
 * @author WeAthFolD
 * 
 */
public class RenderUtils {

	/**
	 * 添加带UV的三维顶点。
	 * 
	 * @param vec3
	 * @param texU
	 * @param texV
	 */
	public static void addVertex(Vec3 vec3, double texU, double texV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}

	/**
	 * 添加不带UV的三维顶点。
	 * 
	 * @param vec3
	 */
	public static void addVertex(Vec3 vec3) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertex(vec3.xCoord, vec3.yCoord, vec3.zCoord);
	}

	/**
	 * 获取某个贴图的ID。
	 * 
	 * @param path
	 *            贴图路径
	 * @return
	 */
	public static int getTexture(String path) {
		return FMLClientHandler.instance().getClient().renderEngine
				.getTexture(path);
	}

	/**
	 * 获取并加载一个贴图。
	 * 
	 * @param path
	 *            贴图路径
	 */
	public static void loadTexture(String path) {
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, getTexture(path));
	}

	public static void renderItemIn2d(EntityLiving par1EntityLiving,
			ItemStack stackToRender, double w) {
		renderItemIn2d(par1EntityLiving, stackToRender, w, null);
	}

	/**
	 * 将Item渲染成一个有厚度的薄片。
	 * 
	 * @param t
	 * @param w
	 *            宽度
	 */
	public static void renderItemIn2d(EntityLiving par1EntityLiving,
			ItemStack stackToRender, double w, Icon specialIcon) {

		Vec3 a1 = newV3(0, 0, w), a2 = newV3(1, 0, w), a3 = newV3(1, 1, w), a4 = newV3(
				0, 1, w), a5 = newV3(0, 0, -w), a6 = newV3(1, 0, -w), a7 = newV3(
				1, 1, -w), a8 = newV3(0, 1, -w);

		Icon icon = stackToRender.getIconIndex();
		if(specialIcon != null)
			icon = specialIcon;
		
		Minecraft mc = Minecraft.getMinecraft();

		if (icon == null) {
			GL11.glPopMatrix();
			return;
		}
		
		if (stackToRender.getItemSpriteNumber() == 0) {
			mc.renderEngine.bindTexture("/terrain.png");
		} else {
			mc.renderEngine.bindTexture("/gui/items.png");
		}
		
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
	public static void renderItemIn2D(Tessellator par0Tessellator, float par1,
			float par2, float par3, float par4, int par5, int par6, float par7) {
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 0.0F, 1.0F);
		par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0D, par1, par4);
		par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0D, par3, par4);
		par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0D, par3, par2);
		par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0D, par1, par2);
		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 0.0F, -1.0F);
		par0Tessellator.addVertexWithUV(0.0D, 1.0D, 0.0F - par7, par1, par2);
		par0Tessellator.addVertexWithUV(1.0D, 1.0D, 0.0F - par7, par3, par2);
		par0Tessellator.addVertexWithUV(1.0D, 0.0D, 0.0F - par7, par3, par4);
		par0Tessellator.addVertexWithUV(0.0D, 0.0D, 0.0F - par7, par1, par4);
		par0Tessellator.draw();
		float f5 = par5 * (par1 - par3);
		float f6 = par6 * (par4 - par2);
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(-1.0F, 0.0F, 0.0F);
		int k;
		float f7;
		float f8;

		for (k = 0; k < f5; ++k) {
			f7 = k / f5;
			f8 = par1 + (par3 - par1) * f7 - 0.5F / par5;
			par0Tessellator.addVertexWithUV(f7, 0.0D, 0.0F - par7, f8, par4);
			par0Tessellator.addVertexWithUV(f7, 0.0D, 0.0D, f8, par4);
			par0Tessellator.addVertexWithUV(f7, 1.0D, 0.0D, f8, par2);
			par0Tessellator.addVertexWithUV(f7, 1.0D, 0.0F - par7, f8, par2);
		}

		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(1.0F, 0.0F, 0.0F);
		float f9;

		for (k = 0; k < f5; ++k) {
			f7 = k / f5;
			f8 = par1 + (par3 - par1) * f7 - 0.5F / par5;
			f9 = f7 + 1.0F / f5;
			par0Tessellator.addVertexWithUV(f9, 1.0D, 0.0F - par7, f8, par2);
			par0Tessellator.addVertexWithUV(f9, 1.0D, 0.0D, f8, par2);
			par0Tessellator.addVertexWithUV(f9, 0.0D, 0.0D, f8, par4);
			par0Tessellator.addVertexWithUV(f9, 0.0D, 0.0F - par7, f8, par4);
		}

		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, 1.0F, 0.0F);

		for (k = 0; k < f6; ++k) {
			f7 = k / f6;
			f8 = par4 + (par2 - par4) * f7 - 0.5F / par6;
			f9 = f7 + 1.0F / f6;
			par0Tessellator.addVertexWithUV(0.0D, f9, 0.0D, par1, f8);
			par0Tessellator.addVertexWithUV(1.0D, f9, 0.0D, par3, f8);
			par0Tessellator.addVertexWithUV(1.0D, f9, 0.0F - par7, par3, f8);
			par0Tessellator.addVertexWithUV(0.0D, f9, 0.0F - par7, par1, f8);
		}

		par0Tessellator.draw();
		par0Tessellator.startDrawingQuads();
		par0Tessellator.setNormal(0.0F, -1.0F, 0.0F);

		for (k = 0; k < f6; ++k) {
			f7 = k / f6;
			f8 = par4 + (par2 - par4) * f7 - 0.5F / par6;
			par0Tessellator.addVertexWithUV(1.0D, f7, 0.0D, par3, f8);
			par0Tessellator.addVertexWithUV(0.0D, f7, 0.0D, par1, f8);
			par0Tessellator.addVertexWithUV(0.0D, f7, 0.0F - par7, par1, f8);
			par0Tessellator.addVertexWithUV(1.0D, f7, 0.0F - par7, par3, f8);
		}

		par0Tessellator.draw();
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
