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
package cn.lambdacraft.xen.render;

import org.lwjgl.opengl.GL11;

import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.xen.block.BlockXenPortal;
import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.entity.Entity;
import net.minecraft.world.IBlockAccess;

/**
 * TODO:
 * 这个方法好像不能调用Translate，推荐换成TileEntitySpecialRenderer
 * 建议参考RenderGlow，做一个面向玩家的单面贴图渲染，效率高效果也差不多
 * by WeAthFolD
 * @see cn.lambdacraft.deathmatch.client.render.RenderGlow
 * @author mkpoli
 *
 */
public class RenderXenPortal implements ISimpleBlockRenderingHandler {
	
	public boolean renderPortal(BlockXenPortal par1BlockXenPortal, int par2, int par3, int par4) {
		Tessellator tessellator = Tessellator.instance;
		GL11.glPushMatrix();
		GL11.glTranslatef(par2, par3, par4);
		GL11.glPopMatrix();
		return true;
	}

	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		renderer.renderBlockAsItem(block, 0, 1.0F);
	}

	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		renderPortal((BlockXenPortal) block, x, y, z);
		return true;
	}

	@Override
	public boolean shouldRender3DInInventory() {
		return true;
	}

	@Override
	public int getRenderId() {
		return ClientProps.RENDER_ID_XENPORTAL;
	}
	
}
