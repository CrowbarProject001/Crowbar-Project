package cn.lambdacraft.deathmatch.client.renderer;

import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.client.model.ModelEgonHead;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.weaponmod.api.client.IItemModel;
import cn.weaponmod.api.client.render.RenderModelItem;

import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.IItemRenderer;

/**
 * Weapon Egon Renderer Class.
 */
public class RenderEgon extends RenderModelItem {

	public RenderEgon() {
		super(new ModelEgonHead(), ClientProps.EGON_HEAD_PATH);
		this.renderInventory = false;
		this.setRotationY(179.35F);
		this.inventorySpin = false;
		this.setEquipOffset(0.438F, -0.152F, -0.172F);
		this.setOffset(0.0F, 0.2F, -0.302F);
		this.setScale(2.45F);
	}

	Tessellator t = Tessellator.instance;

}
