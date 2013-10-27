/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.client.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

import org.lwjgl.opengl.GL11;

/**
 * 简单成翔的模型渲染器。
 * 
 * @author WeAthFolD
 */
public class RenderModel extends Render {

	private ModelBase model;
	private float modelScale;
	private String texture;
	private float yOffset;
	
	/**
	 * 
	 */
	public RenderModel(ModelBase m, String texturePath, float scale) {
		model = m;
		modelScale = scale;
		texture = texturePath;
	}
	
	public RenderModel setYOffset(float off) {
		yOffset = off;
		return this;
	}

	@Override
	public void doRender(Entity entity, double par2, double par4, double par6,
			float par8, float par9) {

		GL11.glPushMatrix();
		RenderUtils.loadTexture(texture);
		GL11.glTranslatef((float) par2, (float) par4 + 2 * entity.height + yOffset, (float) par6);
		GL11.glRotatef(180.0F - par8, 0.0F, 1.0F, 0.0F);
		GL11.glScalef(-1.0F, -1.0F, 1.0F);
		this.model.render(entity, 0.0F, 0.0F, -0.1F, 0.0F, 0.0F, 0.0625F * modelScale);
		GL11.glPopMatrix();

	}


	@Override
	protected ResourceLocation getEntityTexture(Entity entity) {
		// TODO 自动生成的方法存根
		return null;
	}

}
