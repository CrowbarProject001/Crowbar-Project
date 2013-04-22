package cbproject.elements.entities.weapons;

import java.util.List;

import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.weapons.InformationWeapon;
import cbproject.utils.weapons.MotionXYZ;



import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
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
		information = item.getSpecInformation(itemStack, par1World);
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

    protected float getGravityVelocity()
    {
        return 0.0F;
    }
    
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
	
		if( result.entityHit == null || (!(result.entityHit instanceof EntityLiving)))
			return;

		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		InformationWeapon inf = item.getInformation(itemStack).getProperInf(worldObj);
		int mode = inf.mode;
		double pf = item.getPushForce(mode);
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ * pf;
		EntityLiving mob = (EntityLiving) result.entityHit;
	
		mob.attackEntityFrom(DamageSource.causeMobDamage(getThrower()), item.getDamage(mode));
		mob.addVelocity(dx, dy, dz);
		if(effect == "fire")
			mob.setFire(10);
		
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}
	
}
