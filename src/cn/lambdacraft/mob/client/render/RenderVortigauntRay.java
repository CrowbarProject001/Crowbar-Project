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
package cn.lambdacraft.mob.client.render;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.core.utils.MotionXYZ;
import cn.lambdacraft.deathmatch.client.render.RenderEgonRay;
import cn.lambdacraft.deathmatch.entities.fx.EntityEgonRay;
import cn.lambdacraft.mob.entities.EntityVortigauntRay;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;

/**
 * @author WeAthFolD
 *
 */
public class RenderVortigauntRay extends RenderEgonRay {

	public static final double WIDTH = 0.2F;
	
	/* (non-Javadoc)
	 * @see net.minecraft.client.renderer.entity.Render#doRender(net.minecraft.entity.Entity, double, double, double, float, float)
	 */
	@Override
	public void doRender(Entity entity, double d0, double d1, double d2,
			float f, float f1) {
		EntityVortigauntRay ray = (EntityVortigauntRay)entity;

		tessellator = Tessellator.instance;

		GL11.glPushMatrix();

		double dx = ray.destX - ray.startX;
		double dy = ray.destY - ray.startY;
		double dz = ray.destZ - ray.startZ;
		double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
		float angle = ray.ticksExisted;
		float du = -ray.ticksExisted * 0.05F;
		double tx = 0.1, tz = 0.2;

		double ty = -0.63;
		Vec3 v1 = newV3(0, 0, -WIDTH).addVector(tx, ty, tz), v2 = newV3(0, 0,
				WIDTH).addVector(tx, ty, tz), v3 = newV3(d, 0, -WIDTH), v4 = newV3(
				d, 0, WIDTH),

		v5 = newV3(0, WIDTH, 0).addVector(tx, ty, tz), v6 = newV3(0, -WIDTH, 0)
				.addVector(tx, ty, tz), v7 = newV3(d, -WIDTH, 0), v8 = newV3(d,
				WIDTH, 0);

		// Translations and rotations
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_CULL_FACE);
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);

		GL11.glTranslatef((float) d0, (float) d1 + 0.3F, (float) d2);
		GL11.glRotatef(270.0F - ray.rotationYaw, 0.0F, 1.0F, 0.0F); // 左右旋转
		GL11.glRotatef(ray.rotationPitch, 0.0F, 0.0F, -1.0F); // 上下旋转
		// GL11.glRotatef(angle, 1.0F, 0, 0);
		int rand = (int) (Math.random() * 3.0);
		this.loadTexture(ClientProps.VORTIGAUNT_RAY_PATH[rand]);

		tessellator.startDrawingQuads();
		tessellator.setColorRGBA(255, 255, 255, 200);
		
		addVertex(v1, 0 + du, 0);
		addVertex(v2, 0 + du, 1);
		addVertex(v3, d + du, 1);
		addVertex(v4, d + du, 0);

		addVertex(v5, 0 + du, 0);
		addVertex(v6, 0 + du, 1);
		addVertex(v7, d + du, 1);
		addVertex(v8, d + du, 0);

		tessellator.draw();

		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glPopMatrix();
	}

}
