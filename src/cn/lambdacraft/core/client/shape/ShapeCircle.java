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

import cn.lambdacraft.core.client.ICustomShape;
import net.minecraft.util.Vec3;

/**
 * @author WeAthFolD
 *
 */
public class ShapeCircle implements ICustomShape {
	
	private double radius = 1.0;
	private int sampleFreq = 18;

	public ShapeCircle() {}
	
	public ShapeCircle(double d) {
		radius = d;
	}
	
	public ShapeCircle(int i, double d) {
		this(d);
		sampleFreq = i;
	}
	
	public ShapeCircle setRadius(double d) {
		radius = d;
		if(radius < 0.0)
			radius = 1.0;
		return this;
	}
	
	public ShapeCircle setSampleFreq(int i) {
		this.sampleFreq = i;
		if(sampleFreq <= 3)
			sampleFreq = 6;
		return this;
	}

	@Override
	public Vec3[] getVertices() {
		Vec3[] vecs = new Vec3[sampleFreq];
		for(int i = 0; i < sampleFreq; i++) {
			double d = 2.0 * i * Math.PI /sampleFreq;
			double x = Math.cos(d);
			double z = Math.sin(d);
			vecs[i] = Vec3.createVectorHelper(x * radius, 0.0, z * radius);
		}
		return vecs;
	}
	
	public Vec3[] getVertices(double rad) {
		setRadius(rad);
		return getVertices();
	}

	@Override
	public void draw() {
		throw new UnsupportedOperationException();
	}

}
