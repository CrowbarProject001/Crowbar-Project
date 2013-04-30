package cbproject.deathmatch.entities;

import cbproject.deathmatch.items.wpns.Weapon_egon;
import cbproject.deathmatch.utils.InformationEnergy;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Egon ray rendering entity.
 * @author WeAthFolD
 *
 */
public class EntityEgonRay extends EntityThrowable {

	public ItemStack item;
	public EntityEgonRay(World par1World,EntityLiving ent, ItemStack itemStack){
		super(par1World, ent);
		ignoreFrustumCheck = true;
		item = itemStack;
	}
	

	@Override
	public void onUpdate(){
		InformationEnergy inf = ((Weapon_egon)item.getItem()).getInformation(item, worldObj);
		if(inf == null || !(inf.isShooting && ((Weapon_egon)item.getItem()).canShoot((EntityPlayer) getThrower(), item)))
			this.setDead();
		
		EntityLiving ent = getThrower();
		this.setLocationAndAngles(ent.posX, ent.posY, ent.posZ, ent.rotationYawHead, ent.rotationPitch);
		
		float var3 = 0.4F;
		this.motionX = -MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3;
        this.motionZ = MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3;
        this.motionY = -MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * var3;
	}
	
	@Override
	protected float func_70182_d(){
		return 0.0F;
	}

	@Override
	protected float getGravityVelocity(){
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {

	}

}
