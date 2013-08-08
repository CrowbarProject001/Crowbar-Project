package cn.lambdacraft.deathmatch.client.render;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.api.weapon.InformationBullet;
import cn.lambdacraft.api.weapon.WeaponGeneralBullet;
import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.deathmatch.utils.ItemHelper;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

public class RenderBulletWeapon implements IItemRenderer {
	
	Minecraft mc = Minecraft.getMinecraft();
	float tx = 0F, ty = 0F, tz = 0F;
	float width = 0.05F;
	private WeaponGeneralBullet weaponType;

	public RenderBulletWeapon(WeaponGeneralBullet weapon, float w) {
		weaponType = weapon;
		width = w / 2F;
	}

	public RenderBulletWeapon(WeaponGeneralBullet weapon, float width, float x,
			float y, float z) {
		this(weapon, width);
		tx = x;
		ty = y;
		tz = z;
	}

	@Override
	public boolean handleRenderType(ItemStack item, ItemRenderType type) {
		// TODO Auto-generated method stub
		switch (type) {
		case EQUIPPED:
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
		Tessellator t = Tessellator.instance;
		
		int mode = 0;
		if (item.stackTagCompound != null)
			mode = item.getTagCompound().getInteger("mode");
		
		GL11.glPushMatrix();

		RenderUtils.renderItemIn2d(entity, item, width);
		if(entity instanceof EntityPlayer) {
			InformationBullet inf = weaponType.getInformation(item, entity.worldObj);
			WeaponGeneralBullet wpn = (WeaponGeneralBullet) item.getItem();
			EntityPlayer ep = (EntityPlayer) entity;
			if(ItemHelper.getUsingTickLeft(ep) > 0 && wpn.canShoot(ep, item) && (inf != null && inf.getDeltaTick() < 3))
				RenderMuzzleFlash.renderItemIn2d(t, tx, ty, tz);
		}
			
		GL11.glPopMatrix();
	}

	protected void addVertex(Vec3 vec3, double texU, double texV) {
		Tessellator tessellator = Tessellator.instance;
		tessellator.addVertexWithUV(vec3.xCoord, vec3.yCoord, vec3.zCoord,
				texU, texV);
	}

	private void bindTextureByItem(ItemStack item) {
		if (item.stackTagCompound == null)
			item.stackTagCompound = new NBTTagCompound();
		int mode = item.getTagCompound().getInteger("mode");
		int tex = RenderUtils.getTexture(ClientProps.ITEM_SATCHEL_PATH[mode]);
		GL11.glBindTexture(GL11.GL_TEXTURE_2D, tex);
	}

}
