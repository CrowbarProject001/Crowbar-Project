/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.client.render;

import cn.graphrevo.proxy.GRClientProps;
import cn.graphrevo.proxy.GRClientProxy;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.renderer.tileentity.TileEntitySpecialRenderer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.common.ForgeDirection;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

/**
 * @author WeAthFolD
 *
 */
public class RenderTripmine implements ISimpleBlockRenderingHandler {

	private static float THICKNESS = 0.2F, HWIDTH = 0.15F, HHEIGHT = 0.1F;
	
	/**
	 * 
	 */
	public RenderTripmine() {
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler#renderInventoryBlock(net.minecraft.block.Block, int, int, net.minecraft.client.renderer.RenderBlocks)
	 */
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
		// TODO Auto-generated method stub

	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler#renderWorldBlock(net.minecraft.world.IBlockAccess, int, int, int, net.minecraft.block.Block, int, net.minecraft.client.renderer.RenderBlocks)
	 */
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		Tessellator t = Tessellator.instance;
		
		TextureManager engine = Minecraft.getMinecraft().renderEngine;
		
		int metadata = world.getBlockMetadata(x, y, z);
		ForgeDirection dir = ForgeDirection.values()[metadata];
		float offsetX = x + dir.offsetX * 0.2F, offsetY = y + dir.offsetY * 0.2F, offsetZ = z + dir.offsetZ * 0.2F;
		
		//正面
		engine.bindTexture(GRClientProps.TEX_TRIPMINE[2]);
		t.addTranslation(offsetX + dir.offsetX * THICKNESS, offsetY + dir.offsetY * THICKNESS, offsetZ + dir.offsetZ * THICKNESS);
		t.addVertexWithUV(-HWIDTH, HHEIGHT, 0.0F, 0.0F, 0.0F);
		t.addVertexWithUV(-HWIDTH, -HHEIGHT, 0.0F, 0.0F, 1.0F);
		t.addVertexWithUV(HWIDTH, -HHEIGHT, 0.0F, 1.0F, 1.0F);
		t.addVertexWithUV(HWIDTH, HHEIGHT, 0.0F, 1.0F, 0.0F);
		t.draw();
		
		t.startDrawingQuads();
		return true;
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler#shouldRender3DInInventory()
	 */
	@Override
	public boolean shouldRender3DInInventory() {
		return false;
	}

	/* (non-Javadoc)
	 * @see cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler#getRenderId()
	 */
	@Override
	public int getRenderId() {
		return GRClientProxy.RENDER_ID_TRIPMINE;
	}

}
