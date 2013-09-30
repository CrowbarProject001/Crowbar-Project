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

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cn.weaponmod.api.client.IItemModel;
import cn.weaponmod.client.render.RenderUtils;

/**
 * 物品模型的渲染器。提供了大量的设置功能以辅助位置调整。
 * 说明：建模位置最好在Techne中的中心点（0, 0, 0），不是地面，否则需要设置Offset。
 * 如果在物品栏中显示过大，需要调整invOffset。
 * @author WeAthFolD
 *
 */
public class RenderModelItem implements IItemRenderer {

	Tessellator t = Tessellator.instance;
	IItemModel model;
	String texturePath;
	Minecraft mc = Minecraft.getMinecraft();
	
	/**
	 * 模型处于标准位置的旋转角度。
	 */
	public float rotationY = 0.0F, rotationX = 0.0F, rotationZ = 0.0F;
	/**
	 * 模型处于标准位置的位移量。
	 */
	public float xOffset = 0.00F, yOffset = 0.0F, zOffset = 0.0F;
	
	/**
	 * 装备时模型向前移动的量。
	 */
	public float equip_forward = 0.0F;
	
	/**
	 * 物品栏渲染的大小缩放比例。
	 */
	public float invScale = 1.0F;
	
	/**
	 * 物品栏渲染的位移。
	 */
	public float invOffsetX = 0.0F, invOffsetY = 0.0F;
	
	/**
	 * 是否在物品栏渲染时帮助旋转。（向上45度）
	 */
	public boolean helpRotation = true;
	
	/**
	 * 是否渲染物品栏。
	 */
	public boolean renderInventory = true;
	
	/**
	 * 是否渲染物品实体。
	 */
	public boolean renderEntityItem = true;
	
	/**
	 * 是否让物品栏中的模型一直旋转。
	 */
	public boolean inventorySpin = true;
	
	protected static Random RNG = new Random();
	public float scale = 1.0F;
	
	/**
	 * 喜闻乐见的构造器。
	 * @param mdl 模型类
	 * @param texture 对应的贴图文件
	 */
	public RenderModelItem(IItemModel mdl, String texture) {
		model = mdl;
		texturePath = texture;
	}
	
	public RenderModelItem setRenderInventory(boolean b) {
		renderInventory = b;
		return this;
	}
	
	public RenderModelItem setRenderEntityItem(boolean b) {
		renderEntityItem = b;
		return this;
	}
	
	public RenderModelItem setInventorySpin(boolean b) {
		inventorySpin = b;
		return this;
	}
	
	public RenderModelItem setEquipForward(float b) {
		equip_forward = b;
		return this;
	}
	
	public RenderModelItem setRotationY(float ro) {
		rotationY = ro;
		return this;
	}
	
	public RenderModelItem setRotationX(float ro) {
		rotationX = ro;
		return this;
	}
	public RenderModelItem setRotationZ(float ro) {
		rotationZ = ro;
		return this;
	}
	
	public RenderModelItem setScale(float s) {
		scale = s;
		return this;
	}
	
	public RenderModelItem disableInvRotation() {
		helpRotation = false;
		return this;
	}
	
	public RenderModelItem setInvScale(float s) {
		invScale = s;
		return this;
	}
	
	public RenderModelItem setOffset(float offsetX, float offsetY) {
		xOffset = offsetX;
		yOffset = offsetY;
		return this;
	}
	
	public RenderModelItem setOffset(float offsetX, float offsetY, float offsetZ) {
		xOffset = offsetX;
		yOffset = offsetY;
		zOffset = offsetZ;
		return this;
	}
	
	public RenderModelItem setInvOffset(float offsetX, float offsetY) {
		invOffsetX = offsetX;
		invOffsetY = offsetY;
		return this;
	}
	
	public RenderModelItem setInformationFrom(RenderModelItem a) {
		
		this.renderInventory = a.renderInventory;
		this.invOffsetX = a.invOffsetX;
		this.invOffsetY = a.invOffsetY;
		this.helpRotation = a.helpRotation;
		this.setInvScale(a.invScale);
		
		this.setOffset(a.xOffset, a.yOffset, a.zOffset);
		this.rotationY = a.rotationY;
		this.rotationX = a.rotationX;
		this.rotationZ = a.rotationZ;
		
		this.scale = a.scale;
		return this;
	}
	
	@Override
	public final boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			return true;
		case ENTITY:
			return renderEntityItem;
		case INVENTORY:
			return renderInventory;

		default:
			return false;
		}

	}

	@Override
	public final boolean shouldUseRenderHelper(ItemRenderType type, ItemStack item,
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
	public final void renderItem(ItemRenderType type, ItemStack item, Object... data) {
		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
			renderEquipped(item, (RenderBlocks) data[0], (EntityLivingBase) data[1], type);
			break;
		case ENTITY:
			renderEntityItem((RenderBlocks)data[0], (EntityItem) data[1]);
			break;
		case INVENTORY:
			renderInventory();
			break;
		default:
			break;

		}

	}
	
	public void renderInventory() {
		
		GL11.glPushMatrix();
		GL11.glDisable(GL11.GL_CULL_FACE);
		
		RenderUtils.loadTexture(texturePath);
		GL11.glTranslatef(invOffsetX + 8.0F, invOffsetY + 8.0F, 0.0F);
		GL11.glScalef(16F * invScale, 16F * invScale, 16F * invScale);
		float rotation = 145F;
		if(inventorySpin) {
			rotation = Minecraft.getSystemTime() / 100F;
		}
		GL11.glRotatef(rotation, 0, 1, 0);
		if(helpRotation)
			GL11.glRotatef(37.5F, -1, 0, 0);
		GL11.glScalef(-1F, -1F, 1F);
		
		renderAtStdPosition();
		
		GL11.glEnable(GL11.GL_CULL_FACE);
		GL11.glPopMatrix();
	}
	
	public void renderEntityItem(RenderBlocks render, EntityItem ent) {
		
		GL11.glPushMatrix();
		RenderUtils.loadTexture(texturePath);
		
		//GL11.glTranslatef(0F , 0.5F , 0F);
		GL11.glRotatef(90F, 0, -1, 0);
		GL11.glRotatef(30F, 1, 0, 0);
		
		renderAtStdPosition();
		
		GL11.glPopMatrix();
	}
	
	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity, ItemRenderType type) {

		if (item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();

		GL11.glPushMatrix();
		RenderUtils.loadTexture(texturePath);
		float sc2 = 0.0625F;
		GL11.glRotatef(40F, 0, 0, 1);
		GL11.glTranslatef(equip_forward, 0F, 0F);
		GL11.glRotatef(90, 0, -1, 0);
		renderAtStdPosition(getModelAttribute(item, entity));
		
		GL11.glPopMatrix();
	}
	
	/**
	 * 如果参数被设置正确，模型应该被渲染在（0, 0, 0）为中心的四周。
	 */
	protected final void renderAtStdPosition() {
		renderAtStdPosition(0.0F);
	}
	
	protected final void renderAtStdPosition(float i) {
		GL11.glScalef(scale, scale, scale);
		GL11.glTranslatef(xOffset, yOffset, zOffset);
		GL11.glScalef(-1F , -1F , 1F );
		GL11.glRotatef(rotationZ, 0, 0, 1);
		GL11.glRotatef(rotationY + 180F, 0, 1, 0);
		GL11.glRotatef(rotationX, 1, 0, 0);
		model.render(null, 0.0625F, i);
		GL11.glEnable(GL11.GL_CULL_FACE);
	}
	
	protected float getModelAttribute(ItemStack item, EntityLivingBase entity) {
		return 0.1F;
	}


}
