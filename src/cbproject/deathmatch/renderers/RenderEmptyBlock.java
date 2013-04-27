/**
 * 
 */
package cbproject.deathmatch.renderers;


import org.lwjgl.opengl.GL11;

import cbproject.core.CBCMod;
import cbproject.core.props.ClientProps;
import cbproject.core.proxy.ClientProxy;
import cbproject.core.renderers.RendererUtils;
import cbproject.deathmatch.blocks.weapons.BlockTripmine;

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
		return ClientProps.RENDER_TYPE_EMPTY;
	}

}
