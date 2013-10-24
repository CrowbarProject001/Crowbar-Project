package cn.lambdacraft.deathmatch.client.renderer;

import static cn.lambdacraft.core.client.RenderUtils.addVertex;
import static cn.lambdacraft.core.client.RenderUtils.newV3;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderBlocks;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Vec3;
import net.minecraftforge.client.IItemRenderer;

import org.lwjgl.opengl.GL11;

import cn.lambdacraft.core.client.RenderUtils;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.core.proxy.ClientProxy;
import cn.lambdacraft.deathmatch.client.model.ModelCrossbow;
import cn.lambdacraft.deathmatch.item.weapon.Weapon_Crossbow;
import cn.lambdacraft.deathmatch.register.DMItems;
import cn.weaponmod.api.WMInformation;
import cn.weaponmod.api.client.IItemModel;
import cn.weaponmod.api.client.render.RenderModelItem;
import cn.weaponmod.api.information.InformationBullet;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;


@SideOnly(Side.CLIENT)
public class RenderCrossbow extends RenderModelItem {

	public RenderCrossbow() {
		super(new ModelCrossbow(), ClientProps.CROSSBOW_PATH);
		//教练用debugger实在是太方便了！
		setOffset(-0.004F, 0.316F, -0.454F);
		setRotation(0F, 173.95F, 0F);
		setScale(1.65F);
		this.setEquipOffset(0.906F, -0.364F, 0.058F);
		this.setInvRotation(-43.450F, -40.556F, 25.936F);
		this.setInvOffset(-2.45F, 3.8F);
		this.setInvScale(.78F);
		this.inventorySpin = false;
	}
	
	@Override
	protected float getModelAttribute(ItemStack item, EntityLivingBase entity) {
		if(entity instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entity;
			InformationBullet inf = (InformationBullet) WMInformation.getInformation(item, true);
			if(inf != null) {
				if(inf.isReloading)
					return 0.0F;
				if(inf.lastShooting_left && inf.getDeltaTick(true) < 15)
					return 1.0F;
			}
			return 2.0F;
		}
		return 2.0F;
	}

}
