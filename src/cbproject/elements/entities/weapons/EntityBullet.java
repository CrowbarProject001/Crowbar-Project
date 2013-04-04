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

public class EntityBullet extends EntityThrowable {
	
	protected static final double SCALE = 0.01D;
	protected InformationWeapon information;
	protected World world;
	protected ItemStack itemStack;
	protected EntityLiving player;
	protected MotionXYZ motion;
	
	public EntityBullet(World par1World, EntityLiving par2EntityLiving, ItemStack par3itemStack, String particle) {
		
		this(par1World, par2EntityLiving);
		
		itemStack = par3itemStack;
		player = par2EntityLiving;
		if(itemStack == null)
			return;
		if(!(itemStack.getItem() instanceof WeaponGeneral))
			return;
		
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		information = item.getSpecInformation(itemStack, par1World);
		if(information == null)
			return;
		
		int mode = information.mode;
		int offset = item.offset[mode];
		motion = new MotionXYZ(par2EntityLiving, 0);
		float var3 = 0.4F;
		double dx = (Math.random() -1) * 2 * offset * SCALE,
			   dy = (Math.random() -1) * 2 * offset * SCALE,
			   dz = (Math.random() -1) * 2 * offset * SCALE;
		
        this.motionX += dx;
        this.motionZ += dy;
        this.motionY += dz;
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
        
        par1World.spawnParticle(particle, posX, posY, posZ, motionX, motionY, motionZ);
        
	}
	
	public EntityBullet(World par1World, EntityLiving par2EntityLiving) {
		
		super(par1World, par2EntityLiving);
	}


    protected float getGravityVelocity()
    {
        return 0.0F;
    }
    
    protected float func_70182_d()
    {
    	return 100.0F;
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
		double pf = item.pushForce[mode];
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ * pf;
		EntityLiving mob = (EntityLiving) result.entityHit;
	
		mob.attackEntityFrom(DamageSource.causeMobDamage(player), item.damage[mode]);
		mob.addVelocity(dx, dy, dz);
		
	}
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}
	


	
}
