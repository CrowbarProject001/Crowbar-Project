/**
 * 
 */
package cn.lambdacraft.mob.client.renderer;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.mob.client.model.ModelSnark;
import cn.weaponmod.api.client.render.RenderModelItem;

/**
 * @author WeAthFolD
 *
 */
public class RenderSnark extends RenderModelItem {

	/**
	 * @param mdl
	 * @param texture
	 */
	public RenderSnark() {
		super(new ModelSnark(), ClientProps.SQUEAK_MOB_PATH);
		this.setRotationY(-195);
		this.setScale(1.4F);
		this.setOffset(0F, 1.35F, -.15F);
		this.setEquipForward(0.4F);
		this.setRenderInventory(false);
	}
	
	@Override
	protected float getModelAttribute(ItemStack item, EntityLivingBase entity) {
		return entity.ticksExisted / 10F;
	}

}
