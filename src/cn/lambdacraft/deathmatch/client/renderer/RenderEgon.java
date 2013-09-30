package cn.lambdacraft.deathmatch.client.renderer;

import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.deathmatch.register.DMItems;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

/**
 * Weapon Egon Renderer Class.
 */
public class RenderEgon implements IItemRenderer {

	Tessellator t = Tessellator.instance;

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		
		switch (type) {
		case EQUIPPED:
		case EQUIPPED_FIRST_PERSON:
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
		case EQUIPPED_FIRST_PERSON:
			renderEquipped(item, (RenderBlocks) data[0], (EntityLivingBase) data[1]);
			break;

		default:
			break;

		}

	}

	public void renderEquipped(ItemStack item, RenderBlocks render,
			EntityLivingBase entity) {

		if (item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();

		float width = 0.0625F;
		RenderUtils.renderItemIn2d(entity, item, width,
				DMItems.weapon_egon.iconEquipped);

	}

}
