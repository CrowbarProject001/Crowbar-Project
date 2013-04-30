package cbproject.deathmatch.entities;

import cbproject.core.utils.MotionXYZ;
import cbproject.deathmatch.items.wpns.WeaponGeneral;
import cbproject.deathmatch.utils.BulletManager;
import cbproject.deathmatch.utils.InformationWeapon;



import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Bullet entity class which handles all bullet weapons.
 * @author WeAthFolD
 *
 */
public class EntityBullet extends EntityThrowable {
	
	protected InformationWeapon information;
	protected ItemStack itemStack;
	protected MotionXYZ motion;
	private String effect;
	
	public EntityBullet(World par1World, EntityLiving par2EntityLiving, ItemStack par3itemStack, String eff) {
		
		super(par1World, par2EntityLiving);
		
		itemStack = par3itemStack;
		if( itemStack == null || !(itemStack.getItem() instanceof WeaponGeneral) )
			this.setDead();
		
		effect = eff;
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		information = item.getInformation(par3itemStack, par1World);
		if(information == null)
			this.setDead();
		
		int mode = information.mode;
		int offset = item.getOffset(mode);
		//motion = new MotionXYZ(par2EntityLiving, mode);
		motion = new MotionXYZ(par2EntityLiving, offset);
		
        this.setThrowableHeading(motion.motionX, motion.motionY, motion.motionZ, this.func_70182_d(), 1.0F);
        if(effect == "smoke")
        	par1World.spawnParticle(effect, posX, posY,
        			posZ, motionX/25, motionY/25, motionZ/25);
        
	}

    @Override
	protected float getGravityVelocity()
    {
        return 0.0F;
    }
    
    @Override
	protected float func_70182_d()
    {
    	return 50.0F;
    }
    
	@Override
	protected void onImpact(MovingObjectPosition par1)
	{    
		
	    switch(par1.typeOfHit){
	    case TILE:
	    	doBlockCollision(par1);
	    	break;
	    case ENTITY:
	    	doEntityCollision(par1);
	    	break;
	    }
	    this.setDead();
	    
	}
	
	protected void doBlockCollision(MovingObjectPosition result){	
		
	}
	
	public void doEntityCollision(MovingObjectPosition result){
	
		if( result.entityHit == null)
			return;
		if(!(result.entityHit instanceof EntityLiving || result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
			return;
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		InformationWeapon inf = item.getInformation(itemStack, worldObj);
		int mode = inf.mode;
		double pf = item.getPushForce(mode);
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ * pf;
		BulletManager.doEntityAttack(result.entityHit, DamageSource.causeMobDamage(getThrower()), item.getDamage(mode), dx, dy, dz);
		
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}
	
}
