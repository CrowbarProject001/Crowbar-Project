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
package cn.lambdacraft.deathmatch.client.render;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.api.client.IItemModel;
import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.client.models.ModelGauss;
import cn.lambdacraft.deathmatch.register.DMItems;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

/**
 * @author WeAthFolD
 *
 */
public class RenderGauss implements IItemRenderer {


	Tessellator t = Tessellator.instance;
	IItemModel model = new ModelGauss();

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case EQUIPPED:
		//case ENTITY:
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
			renderEquipped(item, (RenderBlocks) data[0], (EntityLiving) data[1]);
			break;

		default:
			break;

		}

	}

	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLiving entity) {

		if (item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();

		GL11.glPushMatrix();
		RenderUtils.loadTexture(ClientProps.HECHARGER_BACK_PATH);
		float scale = 0.0625F;
		GL11.glTranslatef(1.0F, 0.5F, 0F);
		GL11.glRotatef(90F, 0, -1, 0);
		GL11.glRotatef(40F, 1, 0, 0);
		GL11.glScalef(-1F, -1F, 1F);
		GL11.glTranslatef(0F, -1.5F, 0F);
		model.render(item, scale, DMItems.weapon_gauss.getRotationForStack(item, entity));
		GL11.glPopMatrix();
	}

}
