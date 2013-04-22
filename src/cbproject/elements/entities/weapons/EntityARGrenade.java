/**
 * 
 */
package cbproject.elements.entities.weapons;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
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

		float var1 = 2.0F;
		for (int var3 = 0; var3 < 8; ++var3) {
			this.worldObj.spawnParticle("smoke", this.posX, this.posY,
					this.posZ, 0.0D, 0.0D, 0.0D);
		}

		Explosion ex = worldObj.createExplosion(this, pos.hitVec.xCoord, pos.hitVec.yCoord,
				pos.hitVec.zCoord, var1, true);

		AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(posX - 4, posY - 4,
				posZ - 4, posX + 4, posY + 4, posZ + 4);
		List entitylist = this.worldObj.getEntitiesWithinAABBExcludingEntity(
				this, par2);
		if (entitylist.size() > 0) {
			for (int i = 0; i < entitylist.size(); i++) {
				Entity ent = (Entity) entitylist.get(i);
				if (!ent.isEntityInvulnerable() && ent instanceof EntityLiving) {
					int damage = (int) (Math.pow((Math.pow(ent.posX - posX, 2)
							+ Math.pow(ent.posY - posY, 2) + Math.pow(ent.posZ
							- posZ, 2)), 0.33) * 0.33 * 25);

					if (ent instanceof EntityPlayer
							&& ((EntityPlayer) ent).capabilities.isCreativeMode)
						return;

					ent.attackEntityFrom(DamageSource.setExplosionSource(ex), damage);
					ent.setFire(20);

				}
			}
		}
		this.setDead();
	}

	protected float getGravityVelocity() {
		return 0.03F;
	}

	protected float func_70182_d() {
		return 1.5F;
	}
	
	@Override
	public boolean canBeCollidedWith() {
		return true;
	}

}
