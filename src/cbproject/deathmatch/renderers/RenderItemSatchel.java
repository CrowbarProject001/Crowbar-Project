package cbproject.deathmatch.renderers;


import org.lwjgl.opengl.GL11;

import cbproject.core.props.ClientProps;
import cbproject.core.renderers.RenderUtils;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderItemSatchel implements IItemRenderer {

	Tessellator t = Tessellator.instance;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case EQUIPPED:
			return true;
		case INVENTORY:
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
		case INVENTORY:
			renderInventory(item, (RenderBlocks) data[0]);
			break;
		default:
			break;

		}

	}

	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLiving entity) {
		
		if(item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		int mode = item.getTagCompound().getInteger("mode");
		GL11.glPushMatrix();
		
		bindTextureByItem(item);
		float width = (mode == 0) ? 0.15F : 0.0625F;
		RenderUtils.renderItemIn2d(t, width);
		
		GL11.glPopMatrix();

	}

	public void renderInventory(ItemStack item, RenderBlocks render) {

		GL11.glPushMatrix();

		bindTextureByItem(item);
		t.startDrawingQuads();
		t.addVertexWithUV(0, 0, 0, 0.0, 0.0);
		t.addVertexWithUV(0, 16, 0, 0.0, 1.0);
		t.addVertexWithUV(16, 16, 0, 1.0, 1.0);
		t.addVertexWithUV(16, 0, 0, 1.0, 0.0);
		t.draw();
		
		GL11.glPopMatrix();
	}

	protected void addVertex(Vec3 vec3, double texU, double texV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}
	
	private void bindTextureByItem(ItemStack item){
		if(item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		int mode = item.getTagCompound().getInteger("mode");
		int tex = RenderUtils.getTexture(ClientProps.ITEM_SATCHEL_PATH[mode]);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
	}

}
