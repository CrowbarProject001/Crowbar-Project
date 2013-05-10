package cbproject.deathmatch.entities;

import cbproject.core.props.ClientProps;
import cbproject.deathmatch.entities.fx.EntityTrailFX;
import cbproject.deathmatch.register.DMItems;
import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * RPG rocket entity.
 * @author WeAthFolD
 *
 */
public class EntityRocket extends EntityThrowable {

	private EntityRPGDot dot;
	public static double TURNING_SPEED = 0.3;
	
	public EntityRocket(World par1World, EntityPlayer player, ItemStack is) {
		super(par1World, player);
		rotationPitch = player.rotationPitch;
		rotationYaw = player.rotationYaw;
		worldObj.playSoundAtEntity(this, "cbc.weapons.rocket", 0.3F, 1.0F);
		this.dot = DMItems.weapon_RPG.getRPGDot(is, par1World, player);
		if(par1World.isRemote)
			par1World.spawnEntityInWorld(new EntityTrailFX(par1World, this).setSampleFreq(1).setTrailWidth(0.4F).setTextures(ClientProps.RPG_TRAIL_PATH[0], ClientProps.RPG_TRAIL_PATH[1]).setDoesRenderEnd(true).setDecayTime(60));
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0, 0.0, 0.0);
		if(this.isBurning())
			Explode();
		if(ticksExisted % 45 == 0)
			worldObj.playSoundAtEntity(this, "cbc.weapons.rocket", 0.5F, 1.0F);
		if(dot == null || this.isDead)
			return;
		if(dot.isDead){
			dot = null;
			return;
		}
		
		double dx = dot.posX - this.posX, dy = dot.posY - this.posY, dz = dot.posZ - this.posZ;
        this.setRocketHeading(dx, dy, dz, this.func_70182_d());
	}
	
    /**
     * Similar to setArrowHeading, it's point the throwable entity to a x, y, z direction.
     */
    public void setRocketHeading(double par1, double par3, double par5, float par7)
    {
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= (double)f2;
        par3 /= (double)f2;
        par5 /= (double)f2;
        par1 *= (double)par7;
        par3 *= (double)par7;
        par5 *= (double)par7;
        if(Math.abs(this.motionX - par1) < TURNING_SPEED);
        double dx = par1 - motionX, dy = par3 - motionY, dz = par5 - motionZ;
        float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        
        if(Math.abs(dx) < TURNING_SPEED)
        	this.motionX = par1;
        else this.motionX += (dx > 0) ? TURNING_SPEED : -TURNING_SPEED;
        if(Math.abs(dy) < TURNING_SPEED)
        	this.motionY = par3;
        else this.motionY += (dy > 0) ? TURNING_SPEED : -TURNING_SPEED;
        if(Math.abs(dz) < TURNING_SPEED)
        	this.motionZ = par5;
        else this.motionZ += (dz > 0) ? TURNING_SPEED : -TURNING_SPEED;
       
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(par3, (double)f3) * 180.0D / Math.PI);
    }
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		Explode();
	}
	
	private void Explode(){
		BulletManager.Explode(worldObj, this, 5.0F, 5.0F, posX, posY, posZ, 50);
		this.setDead();
	}
	
	@Override
    protected float getGravityVelocity()
    {
        return 0.0F;
    }
    
	@Override
    protected float func_70182_d()
    {
    	return 4.0F;
    }
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}

}
