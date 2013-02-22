package cbproject.elements.entities;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBullet extends EntityThrowable {
	int damage;
	public EntityBullet(World par1World) {
		super(par1World);
	}

	public EntityBullet(World par1World, EntityLiving par2EntityLiving, int par3Dmg) {
		super(par1World, par2EntityLiving);
		damage = par3Dmg;
		// TODO Auto-generated constructor stub
	}

	public EntityBullet(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		// TODO Auto-generated method stub
	    for (int var3 = 0; var3 < 8; ++var3)
	    {
	            this.worldObj.spawnParticle("smoke", this.posX, this.posY, this.posZ, 0.0D, 0.0D, 0.0D);
	    }
	    if( var1.typeOfHit == EnumMovingObjectType.ENTITY )
	    {
	    	var1.entityHit.attackEntityFrom(DamageSource.explosion , damage);
	    }
	}
	
	@Override
	public float getGravityVelocity(){
		return 0.0F; //无重力影响
	}
	
	@Override
	public float func_70182_d()
	{
		return 1000.0F; //子弹速度
	}

}
