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

import net.minecraft.block.Block;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.client.render.RendererSidedCube;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.mob.blocks.TileSentryRay;
import cn.lambdacraft.mob.register.CBCMobBlocks;


/**
 * @author WeAthFolD
 *
 */
public class RenderSentryRay extends RendererSidedCube {

	public RenderSentryRay() {
		super(CBCMobBlocks.sentryRay);
	}

	@Override
	public String getTexture(TileEntity te, int side, int metadata) {
		return ClientProps.HEVCHARGER_BACK;
	}

	@Override
	public void renderTileEntityAt(TileEntity tile, double x, double y,
			double z, float f) {
		this.doRender(tile, x, y, z, f);
		TileSentryRay ray = (TileSentryRay) tile, ray2 = ray.linkedBlock;
		double width = 0.05;
		if(ray2 != null) {
			Vec3 v = ray2.getRayOffset();
			Vec3 va = ray.getRayOffset();
			double x1 = ray2.xCoord + v.xCoord - ray.xCoord, y1 =  ray2.yCoord + v.yCoord - ray.yCoord, z1 = ray2.zCoord + v.zCoord - ray.zCoord;
			x += va.xCoord;
			y += va.yCoord;
			z += va.zCoord;
			Vec3 v1 = getVec3(0, 0, -width).addVector(x, y, z), v2 = getVec3(
					0, 0, width).addVector(x, y, z), v3 = getVec3(
					0, 0, width).addVector(x1, y1, z1), v4 = getVec3(
					0, 0, -width).addVector(x1, y1, z1);

			Vec3 v5 = getVec3(-width, 0, 0).addVector(x, y, z), v6 = getVec3(
					width, 0, 0).addVector(x, y, z), v7 = getVec3(
					width, 0, 0).addVector(x1, y1, z1), v8 = getVec3(
					-width, 0, 0).addVector(x1, y1, z1);

			Vec3 v9 = getVec3(0, -width, 0).addVector(x, y, z), v10 = getVec3(
					0, width, 0).addVector(x, y, z), v11 = getVec3(
					0, width, 0).addVector(x1, y1, z1), v12 = getVec3(
					0, -width, 0).addVector(x1, y1, z1);
			GL11.glPushMatrix();
			Tessellator t = Tessellator.instance;
			RenderUtils.loadTexture(ClientProps.HORNET_TRAIL_PATH);
			t.startDrawingQuads();
			t.setColorRGBA_F(0.8F, 0.8F, 0.8F, 1.0F);

			RenderUtils.addVertex(v1, 0, 0);
			RenderUtils.addVertex(v2, 0, 1);
			RenderUtils.addVertex(v3, 1, 1);
			RenderUtils.addVertex(v4, 1, 0);

			RenderUtils.addVertex(v4, 1, 0);
			RenderUtils.addVertex(v3, 1, 1);
			RenderUtils.addVertex(v2, 0, 1);
			RenderUtils.addVertex(v1, 0, 0);

			RenderUtils.addVertex(v5, 0, 0);
			RenderUtils.addVertex(v6, 0, 1);
			RenderUtils.addVertex(v7, 1, 1);
			RenderUtils.addVertex(v8, 1, 0);

			RenderUtils.addVertex(v8, 1, 0);
			RenderUtils.addVertex(v7, 1, 1);
			RenderUtils.addVertex(v6, 0, 1);
			RenderUtils.addVertex(v5, 0, 0);

			RenderUtils.addVertex(v9, 0, 0);
			RenderUtils.addVertex(v10, 0, 1);
			RenderUtils.addVertex(v11, 1, 1);
			RenderUtils.addVertex(v12, 1, 0);

			RenderUtils.addVertex(v12, 1, 0);
			RenderUtils.addVertex(v11, 1, 1);
			RenderUtils.addVertex(v10, 0, 1);
			RenderUtils.addVertex(v9, 0, 0);

			t.draw();
			GL11.glPopMatrix();
		}
	}
	
	private Vec3 getVec3(double x, double y, double z) {
		return Vec3.createVectorHelper(x, y, z);
	}

}
