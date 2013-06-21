package cn.lambdacraft.deathmatch.items;

import java.util.EnumSet;

import org.lwjgl.opengl.GL11;

import scala.Char;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cn.lambdacraft.api.hud.IHudTip;
import cn.lambdacraft.api.hud.IHudTipProvider;
import cn.lambdacraft.api.weapon.WeaponGeneral;
import cn.lambdacraft.core.item.ElectricArmor;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.register.DMItems;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.RenderEngine;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumArmorMaterial;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Icon;
import net.minecraftforge.common.EnumHelper;

public class ArmorHEV extends ElectricArmor {

	private EnumSet<EnumAttachment> attatches = EnumSet.of(EnumAttachment.NONE);

	public static enum EnumAttachment {
		LONGJUMP(1), NONE(-1);
		private int slot;

		private EnumAttachment(int x) {
			this.slot = x;
		}

		public int getSlot() {
			return slot;
		}
	}

	public static int reductionAmount[] = { 5, 8, 5, 4 };
	protected static EnumArmorMaterial material = EnumHelper.addArmorMaterial(
			"armorHEV", 100000, reductionAmount, 0);
	private static ArmorProperties propChest = new ArmorProperties(3, 100.0,
			200), propDefault = new ArmorProperties(2, 100.0, 150),
			propNone = new ArmorProperties(2, 0.0, 0),
			propShoe = new ArmorProperties(2, 100.0, 125),
			propShoeFalling = new ArmorProperties(3, 100.0, 2500);

	public ArmorHEV(int par1, int armorType) {
		super(par1, material, 2, armorType);
		setUnlocalizedName("hev" + this.armorType);
		this.setIconName("hev" + armorType);
		this.setMaxCharge(100000);
		this.setTier(2);
		this.setTransferLimit(128);
		this.setEnergyPerDamage(500);
	}

	public ArmorHEV(int par1, EnumAttachment attach) {
		this(par1, attach.getSlot());
		this.setIconName("hev_" + attach.name().toLowerCase());
		this.attatches = EnumSet.of(attach);
		this.setDescription(attach.name().toLowerCase());
	}

	/**
	 * 获取HEV装甲的附件，目前半完成，硬编码。
	 * 
	 * @param is
	 * @param attach
	 * @return
	 */
	public boolean getAttachment(ItemStack is, EnumAttachment attach) {
		if (is != null && !(is.getItem() instanceof ArmorHEV))
			return false;
		return attatches.contains(attach);
	}

	@Override
	public String getArmorTexture(ItemStack stack, Entity entity, int slot,
			int layer) {
		if (stack.getItem().itemID == DMItems.armorHEVLeggings.itemID) {
			return ClientProps.HEV_ARMOR_PATH[1];
		} else {
			return ClientProps.HEV_ARMOR_PATH[0];
		}
	}

	@Override
	public boolean canProvideEnergy(ItemStack itemStack) {
		return true;
	}

	@Override
	public ArmorProperties getProperties(EntityLiving player, ItemStack armor,
			DamageSource source, double damage, int slot) {
		if (getItemCharge(armor) <= this.energyPerDamage)
			return propNone;
		if (source == DamageSource.fall) {
			if (slot == 0)
				return propShoeFalling;
			else
				return propNone;
		}
		return slot == 2 ? propChest : (slot == 0 ? propShoe : propDefault);
	}

	@Override
	public int getArmorDisplay(EntityPlayer player, ItemStack armor, int slot) {
		return (slot == 2) ? 8 : 4;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void renderHelmetOverlay(ItemStack stack, EntityPlayer player,
			ScaledResolution resolution, float partialTicks, boolean hasScreen,
			int mouseX, int mouseY) {
		RenderEngine eg = Minecraft.getMinecraft().renderEngine;
		Tessellator t = Tessellator.instance;
		int i = resolution.getScaledWidth();
		int j = resolution.getScaledHeight();
		GL11.glPushMatrix();
		GL11.glDepthMask(false);
		GL11.glBlendFunc(770, 771);
		GL11.glColor4f(1.0F,1.0F,1.0F,1.0F);
		GL11.glDisable(3008);
		GL11.glDisable(2929);
		eg.bindTexture(ClientProps.HEV_MASK_PATH);
		t.startDrawingQuads();
		t.setColorRGBA(255, 255, 255, 50);
		t.addVertexWithUV(0.0D, j, -90D, 0.0D, 1.0D);
		t.addVertexWithUV(i, j, -90D, 1.0D, 1.0D);
		t.addVertexWithUV(i, 0.0D, -90D, 1.0D, 0.0D);
		t.addVertexWithUV(0.0D, 0.0D, -90D, 0.0D, 0.0D);
		t.draw();
		GL11.glDepthMask(true);
		GL11.glEnable(2929);
		GL11.glEnable(3008);
		GL11.glPopMatrix();
	}


}
