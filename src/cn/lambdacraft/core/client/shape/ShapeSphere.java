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
package cn.lambdacraft.core.client.shape;

import cn.lambdacraft.core.client.IMultipleCustomShape;
import cn.lambdacraft.core.client.RenderUtils;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;

/**
 * @author WeAthFolD
 *
 */
public class ShapeSphere implements IMultipleCustomShape {

	private int sampleFreq = 18;

	public ShapeSphere() {
	}
	
	public ShapeSphere(int sf) {
		sampleFreq = sf;
		if(sampleFreq <= 4)
			sampleFreq = 10;
	}

	@Override
	public Vec3[][] getVertices() {
		Vec3[][] vecs = new Vec3[sampleFreq][sampleFreq];
		for(int i = 0; i < sampleFreq; i++) {
			double d0 = sampleFreq / 2.0;
			double d1 = Math.abs(i - d0);
			double rad = Math.sqrt(d0 * d0 - d1 * d1) / d0;
			Vec3[] vec = RenderUtils.shapeCircle.getVertices(rad / 2.0);
			for(Vec3 v : vec)
				v.yCoord += i / (double)sampleFreq;
			vecs[i] = vec;
		}
		return vecs;
	}

	/* (non-Javadoc)
	 * @see cn.lambdacraft.core.client.IMultipleCustomShape#draw()
	 */
	@Override
	public void draw() {
		Vec3[][] vecs = getVertices();
		double u = 0.0, v = 0.0;
		double d0 = 1.0 / sampleFreq;
		Tessellator t = Tessellator.instance;
		t.startDrawingQuads();
		for(int i = 0; i < sampleFreq; i++) { // i : 纵向计数 从下往上
			for(int j = 0; j < sampleFreq; j++) { // j : 横向计数
				u = j / (double) sampleFreq;
				v = i / (double) sampleFreq;
				int ni = i == sampleFreq - 1 ? 0 : i + 1;
				int nj = j == sampleFreq - 1 ? 0 : j + 1;
				RenderUtils.addVertex(vecs[i][j], u, v);
				RenderUtils.addVertex(vecs[i][nj], u + d0, v);
				RenderUtils.addVertex(vecs[ni][nj], u + d0, v + d0);
				RenderUtils.addVertex(vecs[ni][j], u, v + d0);
			}
		}
		t.draw();
	}

}
