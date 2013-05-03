/**
 * 
 */
package cbproject.deathmatch.entities;

import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 * @description AR Grenade entity class used by 9mmAR.
 */
public class EntityARGrenade extends EntityThrowable {

	public EntityARGrenade(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	/**
	 * Explode once impact.
	 */
	@Override
	protected void onImpact(MovingObjectPosition par1) {
		Explode(par1);
	}

	private void Explode(MovingObjectPosition pos) {
		BulletManager.Explode(worldObj, this, 3.0F, 4.0F, posX, posY, posZ, 35);
		this.setDead();
	}

	@Override
	protected float getGravityVelocity() {
		return 0.03F;
	}

	@Override
	protected float func_70182_d() {
		return 1.5F;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

}
