package cbproject.elements.renderers.weapons;


import org.lwjgl.opengl.GL11;

import cbproject.CBCMod;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.elements.items.weapons.Weapon_satchel;
import cbproject.elements.renderers.RendererUtils;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationSatchel;
import cbproject.utils.weapons.InformationSet;


import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.ItemRenderer;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MathHelper;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;
import net.minecraftforge.client.IItemRenderer.ItemRenderType;
import net.minecraftforge.client.IItemRenderer.ItemRendererHelper;

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
		
		int mode;
		InformationSet set = CBCMod.wpnInformation.getInformation(item);
		if(set == null)
			mode = 0;
		else mode = set.clientReference.mode;
		GL11.glPushMatrix();
		
		bindTextureByItem(item);
		float width = (mode == 0) ? 0.15F : 0.0625F;
		RendererUtils.renderItemIn2d(t, width);
		
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
		int mode;
		InformationSet set = CBCMod.wpnInformation.getInformation(item);
		if(set == null)
			mode = 0;
		else mode = set.clientReference.mode;
		int tex = RendererUtils.getTexture(ClientProxy.ITEM_SATCHEL_PATH[mode]);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
	}

}
