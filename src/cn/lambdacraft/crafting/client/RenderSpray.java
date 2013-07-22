/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.crafting.client;

import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MathHelper;

import static org.lwjgl.opengl.GL11.*;
import static org.lwjgl.opengl.GL12.*;

import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GL12;
import org.lwjgl.opengl.GL15;
import org.lwjgl.opengl.GL21;
import org.lwjgl.opengl.GL31;
import org.lwjgl.opengl.GL33;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.crafting.entities.EntitySpray;
import cn.lambdacraft.crafting.items.HLSpray;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

/**
 * 喷漆的渲染类
 * 
 * @author mkpoli 洋气书生(友情提供。。。)
 * 
 */
@SideOnly(Side.CLIENT)
public class RenderSpray extends Render {

	@Override
	public void doRender(Entity entity, double pos_x, double pos_y, double pos_z, float rotation_yaw, float partial_tick_time) {
		this.renderEntity((EntitySpray) entity, pos_x, pos_y, pos_z);
	}

	/*
	 * 载入Texture
	 */
	private void loadTexture(int title_id) {
		if (title_id >= 2) {
			// 从custom文件夹下载入制定文件名的bitmap
			loadTexture(ClientProps.HLSPRAY_DIC_PATH + (String) GenericUtils.getKeyByValueFromMap(HLSpray.listOfLogos, title_id - 2) + ".bmp");
			glTexEnvf(GL_TEXTURE_ENV, GL_TEXTURE_ENV_MODE, GL_MODULATE);
		}
		else
			// 普通喷漆
			loadTexture(ClientProps.SPRY_PATH[title_id]);
	}

	/**
	 * 渲染Spray
	 * 
	 * @param entity
	 *            Spray Entity
	 * @param pos_x
	 *            X
	 * @param pos_y
	 *            Y
	 * @param pos_z
	 *            Z
	 */
	public void renderEntity(EntitySpray entity, double pos_x, double pos_y, double pos_z) {
		entity.load_params();
		if (entity.title_id >= 2)
			entity.loadColor();
		
		glPushMatrix();
		glEnable(GL_RESCALE_NORMAL);
		glTranslatef((float) pos_x, (float) pos_y, (float) pos_z);
		glRotatef(entity.rotationYaw, 0.0F, 1.0F, 0.0F);

		loadTexture(entity.title_id);

		if (entity.title_id >= 2) {
			glEnable(GL_BLEND);

		}
		
		draw(entity);

		glDisable(GL_RESCALE_NORMAL);
		glDisable(GL11.GL_BLEND);
		glPopMatrix();
	}

	/**
	 * Draw size and lightmaps
	 * 
	 * @param entity
	 *            Spray Entity
	 */
	private void draw(EntitySpray entity) {

		float start_x; // x的起点(宽度的一半)
		float start_y; // Ditto.
		float half_thickness = 0.00000001f;

		if (entity.title_id >= 2) {
			start_x = -0.5F;
			start_y = 0.5F;
		} else {
			start_x = -EntitySpray.GRIDS_WIDTHS[entity.title_id] / 2.0F;
			start_y = EntitySpray.GRIDS_HEIGHTS[entity.title_id] / 2.0F;
		}

		for (float i = 0; i < ((entity.title_id >= 2) ? 1 : EntitySpray.GRIDS_WIDTHS[entity.title_id]); i++) { // i为从0到宽度的所有值
			for (float j = 0; j < ((entity.title_id >= 2) ? 1 : EntitySpray.GRIDS_HEIGHTS[entity.title_id]); j++) { // j为从0到高度的所有值

				float left = start_x + i;
				float right = left + 1;
				float top = start_y - j;
				float bottom = top - 1;

				this.render_light(entity, (right + left) / 2, (bottom + top) / 2);

				float texture_left;
				float texture_right;
				float texture_top;
				float texture_bottom;

				if (entity.title_id >= 2) {
					texture_left = i;
					texture_right = (i + 1);
					texture_top = j;
					texture_bottom = (j + 1);
					glColor3f(entity.color.red, entity.color.green, entity.color.blue);
					glBlendFunc(GL_ONE, GL_ZERO);
					glBlendFunc(GL_SRC_COLOR, GL_ONE_MINUS_SRC_COLOR);
				} else {
					texture_left = i / EntitySpray.GRIDS_WIDTHS[entity.title_id];
					texture_right = (i + 1) / EntitySpray.GRIDS_WIDTHS[entity.title_id];
					texture_top = j / EntitySpray.GRIDS_HEIGHTS[entity.title_id];
					texture_bottom = (j + 1) / EntitySpray.GRIDS_HEIGHTS[entity.title_id];
				}

				Tessellator tessellator = Tessellator.instance;
				tessellator.startDrawingQuads();

				// 正对玩家的一面，设置四个贴图顶点
				// setNormal 函数是设置贴图法线向量，关系到光照等
				// addVertexWithUV 函数，前三个参数是顶点的空间坐标，后两个参数是贴图上对应位置的偏移量（以比例表示）
				// 左上，右上，右下，左下。顺时针转一圈
				tessellator.setNormal(0.0F, 0.0F, -1.0F);
				tessellator.addVertexWithUV(right, top, half_thickness, texture_right, texture_top);
				tessellator.addVertexWithUV(left, top, half_thickness, texture_left, texture_top);
				tessellator.addVertexWithUV(left, bottom, half_thickness, texture_left, texture_bottom);
				tessellator.addVertexWithUV(right, bottom, half_thickness, texture_right, texture_bottom);

				// 背对玩家的一面
				tessellator.setNormal(0.0F, 0.0F, 1.0F);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, -half_thickness, 0, 0);

				// 上边框
				tessellator.setNormal(0.0F, 1.0F, 0.0F);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, half_thickness, 0, 0);

				// 下边框
				tessellator.setNormal(0.0F, -1.0F, 0.0F);
				tessellator.addVertexWithUV(right, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0, 0);

				// 左表面
				tessellator.setNormal(-1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(left, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(left, bottom, half_thickness, 0, 0);

				// 右表面
				tessellator.setNormal(1.0F, 0.0F, 0.0F);
				tessellator.addVertexWithUV(right, top, -half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, top, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, half_thickness, 0, 0);
				tessellator.addVertexWithUV(right, bottom, -half_thickness, 0, 0);

				tessellator.draw();
			}
		}
	}

	private void render_light(EntitySpray entity, float center_x, float center_y) {
		int x = MathHelper.floor_double(entity.posX);
		int y = MathHelper.floor_double(entity.posY + center_y);
		int z = MathHelper.floor_double(entity.posZ);

		if (entity.hanging_direction == 2) {
			x = MathHelper.floor_double(entity.posX - center_x);
		}

		if (entity.hanging_direction == 1) {
			z = MathHelper.floor_double(entity.posZ + center_x);
		}

		if (entity.hanging_direction == 0) {
			x = MathHelper.floor_double(entity.posX + center_x);
		}

		if (entity.hanging_direction == 3) {
			z = MathHelper.floor_double(entity.posZ - center_x);
		}

		int light = this.renderManager.worldObj.getLightBrightnessForSkyBlocks(x, y, z, 0);
		int var8 = light % 65536;
		int var9 = light / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var8, var9);
		glColor3f(1.0F, 1.0F, 1.0F);
	}
}