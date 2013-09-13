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
package cn.weaponmod.api.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cn.weaponmod.api.information.InformationBullet;
import cn.weaponmod.api.weapon.WeaponGeneralBullet;
import cn.weaponmod.client.render.RenderUtils;

/**
 * TODO:Muzzleflash显示需要大改
 * @author WeAthFolD
 *
 */
public class RenderBulletWeapon implements IItemRenderer {

	Minecraft mc = Minecraft.getMinecraft();
	float tx = 0F, ty = 0F, tz = 0F;
	float width = 0.05F;
	private WeaponGeneralBullet weaponType;
	private String texturePath;
	
	public RenderBulletWeapon(WeaponGeneralBullet weapon, float w, String tex) {
		weaponType = weapon;
		width = w / 2F;
		texturePath = tex;
	}
	
	public RenderBulletWeapon(WeaponGeneralBullet weapon, float w) {
		weaponType = weapon;
		width = w / 2F;
	}

	public RenderBulletWeapon(WeaponGeneralBullet weapon, float width, float x,
			float y, float z, String texPath) {
		this(weapon, width, texPath);
		tx = x;
		ty = y;
		tz = z;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case EQUIPPED:
			return true;
		default:
			return false;
		}

	}

	@Override
	public boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
			ItemRendererHelper helper) {
		switch (helper) {
		case ENTITY_ROTATION:
		case ENTITY_BOBBING:
			return true;

		default:
			return false;

		}
	}

	@Override
	public void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
		case EQUIPPED:
			renderEquipped(item, (RenderBlocks) data[0], (EntityLivingBase) data[1]);
			break;
		default:
			break;

		}

	}

	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity) {
		Tessellator t = Tessellator.instance;
		
		int mode = 0;
		if (item.stackTagCompound != null)
			mode = item.getTagCompound().getInteger("mode");
		
		GL11.glPushMatrix();

		RenderUtils.renderItemIn2d(entity, item, width);
		if(texturePath != null && entity instanceof EntityPlayer) {
			InformationBullet inf = weaponType.getInformation(item, entity.worldObj);
			EntityPlayer ep = (EntityPlayer) entity;
			if(inf.lastTick_left > 0)
				renderMuzzleflashIn2d(t, texturePath, tx, ty, tz);
		}
			
		GL11.glPopMatrix();
	}

	protected static void addVertex(Vec3 vec3, double texU, double texV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}
	
	public static void renderMuzzleflashIn2d(Tessellator t, String texture, double tx, double ty, double tz) {

		Vec3 a1 = RenderUtils.newV3(1.2, -0.4, -0.5), a2 = RenderUtils.newV3(1.2, 0.4, -0.5), a3 = RenderUtils.newV3(
				1.2, 0.4, 0.3), a4 = RenderUtils.newV3(1.2, -0.4, 0.3);

		float u1 = 0.0F, v1 = 0.0F, u2 = 1.0F, v2 = 1.0F;

		t = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glEnable(GL11.GL_BLEND);
		GL11.glDisable(GL11.GL_LIGHTING);
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
		RenderUtils.loadTexture(texture);
		
		GL11.glTranslated(tx, ty + 0.1F, tz + 0.1F);
		GL11.glRotatef(45, 0.0F, 0.0F, 1.0F);
		
		t.startDrawingQuads();
		t.setColorRGBA_F(0.8F, .8F, .8F, 1.0F);
		t.setBrightness(15728880);
		addVertex(a1, u2, v2);
		addVertex(a2, u2, v1);
		addVertex(a3, u1, v1);
		addVertex(a4, u1, v2);
		t.draw();
		
		GL11.glDisable(GL11.GL_BLEND);
		GL11.glEnable(GL11.GL_LIGHTING);
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();

	}

}
