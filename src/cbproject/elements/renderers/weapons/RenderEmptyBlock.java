/**
 * 
 */
package cbproject.elements.renderers.weapons;


import org.lwjgl.opengl.GL11;

import cbproject.CBCMod;
import cbproject.elements.blocks.weapons.BlockTripmine;
import cbproject.elements.renderers.RendererUtils;
import cbproject.proxy.ClientProxy;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.util.Vec3;
import net.minecraft.world.IBlockAccess;
import net.minecraftforge.client.ForgeHooksClient;
import net.minecraftforge.client.MinecraftForgeClient;
import cpw.mods.fml.client.FMLClientHandler;
import cpw.mods.fml.client.registry.ISimpleBlockRenderingHandler;

/**
 * @author Administrator
 *
 */
public class RenderEmptyBlock implements ISimpleBlockRenderingHandler {
	
	@Override
	public void renderInventoryBlock(Block block, int metadata, int modelID,
			RenderBlocks renderer) {
	}

	
	@Override
	public boolean renderWorldBlock(IBlockAccess world, int x, int y, int z,
			Block block, int modelId, RenderBlocks renderer) {
		return true;
	}
    
	@Override
	public boolean shouldRender3DInInventory() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public int getRenderId() {
		return CBCMod.RENDER_TYPE_EMPTY;
	}

}
