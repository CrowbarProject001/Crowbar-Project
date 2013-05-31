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
package cbproject.crafting.render;

import org.lwjgl.opengl.GL11;

import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RenderUtils;
import cbproject.crafting.blocks.TileWire;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.Vec3;
import net.minecraftforge.common.ForgeDirection;

/**
 * @author WeAthFolD
 * 
 */
public class RenderWire extends TileEntitySpecialRenderer {

	public static float WIDTH = 0.16666F;
	/**
	 * 
	 */
	public RenderWire() {
		// TODO Auto-generated constructor stub
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer#
	 * renderTileEntityAt(net.minecraft.tileentity.TileEntity, double, double,
	 * double, float)
	 */
	@Override
	public void renderTileEntityAt(TileEntity tileentity, double d0, double d1,
			double d2, float f) {
		TileWire wire = (TileWire) tileentity;
		Tessellator t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslated(d0 + 0.5, d1 + 0.5, d2 + 0.5);
		renderWireBox(t, -1, wire.renderSides);
		for(int i = 0; i < 6; i++ ) {
			if(wire.renderSides[i])
				renderWireBox(t, i, wire.renderSides);
		}
		GL11.glPopMatrix();
	}

	private void renderWireBox(Tessellator t, int side, boolean[] theArray) {
		Vec3 v1 = RenderUtils.newV3(-WIDTH, -WIDTH, -WIDTH),
				v2 = RenderUtils.newV3(WIDTH, -WIDTH, -WIDTH),
				v3 = RenderUtils.newV3(WIDTH, -WIDTH, WIDTH),
				v4 = RenderUtils.newV3(-WIDTH, -WIDTH, WIDTH),
				v5 = RenderUtils.newV3(-WIDTH, WIDTH, -WIDTH),
				v6 = RenderUtils.newV3(WIDTH, WIDTH, -WIDTH),
				v7 = RenderUtils.newV3(WIDTH, WIDTH, WIDTH),
				v8 = RenderUtils.newV3(-WIDTH, WIDTH, WIDTH);
		GL11.glPushMatrix();
		float dx = 0.0F, dy = 0.0F, dz = 0.0F;
		switch(side) {
		case 0:
			dy = -2 * WIDTH;
			break;
		case 1:
			dy = 2 *WIDTH;
			break;
		case 4:
			dx = -2 *WIDTH;
			break;
		case 5:
			dx = 2 *WIDTH;
			break;
		case 2:
			dz = -2 *WIDTH;
			break;
		case 3:
			dz = 2 *WIDTH;
			break;
		}
		GL11.glTranslatef(dx, dy, dz);
		for(int i = 0; i < 6; i++) {
			if(!doesRenderSide(side, i, theArray))
				continue;
			Vec3 vec1 = null, vec2 = null, vec3 = null, vec4 = null;
			dx = 0.0F;
			dy = 0.0F;
			dz = 0.0F;
			switch(i) {
			case 0:
				vec1 = v4;
				vec2 = v3;
				vec3 = v2;
				vec4 = v1;
				dy = -WIDTH;
				break;
			case 1:
				vec1 = v5;
				vec2 = v6;
				vec3 = v7;
				vec4 = v8;
				dy = WIDTH;
				break;
			case 4:
				vec1 = v1;
				vec2 = v5;
				vec3 = v8;
				vec4 = v4;
				dx = -WIDTH;
				break;
			case 5:
				vec1 = v2;
				vec2 = v3;
				vec3 = v7;
				vec4 = v6;
				dx = WIDTH;
				break;
			case 2:
				vec1 = v1;
				vec2 = v2;
				vec3 = v6;
				vec4 = v5;
				dz = -WIDTH;
				break;
			case 3:
				vec1 = v4;
				vec2 = v8;
				vec3 = v7;
				vec4 = v3;
				dz = WIDTH;
				break;
			}
			if(side == i) {
				RenderUtils.loadTexture(ClientProps.WIRE_SIDE_PATH);
			} else {
				RenderUtils.loadTexture(ClientProps.WIRE_MAIN_PATH);
			}
			GL11.glPushMatrix();
			t.startDrawingQuads();
			t.setNormal(2*dx, 2*dy, 2*dz);
			RenderUtils.addVertex(vec4, 0.0, 1.0);
			RenderUtils.addVertex(vec3, 1.0, 1.0);
			RenderUtils.addVertex(vec2, 1.0, 0.0);
			RenderUtils.addVertex(vec1, 0.0, 0.0);
			t.draw();
			GL11.glPopMatrix();
		}
		GL11.glPopMatrix();
	}
	
	private boolean doesRenderSide(int blockSide, int subSide, boolean[] theArray) {
		ForgeDirection[] dirs = ForgeDirection.values();
		if(blockSide == -1) {
			if(theArray[subSide])
				return false;
			return true;
		}
		if(dirs[blockSide].getOpposite().ordinal() == subSide)
			return false;
		return true;
	}
}
