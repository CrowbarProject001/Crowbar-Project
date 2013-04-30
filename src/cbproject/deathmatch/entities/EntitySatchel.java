package cbproject.deathmatch.entities;

import java.util.List;

import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;

/**
 * Satchel entity.
 * @author WeAthFolD
 *
 */
public class EntitySatchel extends EntityThrowable {
	
	public static double HEIGHT = 0.083, WIDTH1 = 0.2, WIDTH2 = 0.15;
	public int tickHit = 0;
	
	public EntitySatchel(World par1World) {
		super(par1World);
	}

	public EntitySatchel(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}

	public EntitySatchel(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
	}

	@Override
    public boolean canBeCollidedWith()
    {
        return true;
    }
	
	public void Explode(){
		
		BulletManager.Explode(worldObj, this, 3.0F, 4.0F, posX, posY, posZ, 35);
		this.setDead();
		
	}
	
    protected float getGravityVelocity()
    {
        return 0.025F;
    }
    
    protected float func_70182_d()
    {
    	return 0.7F;
    }
    
    
    @Override
    public AxisAlignedBB getBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(-WIDTH1, -HEIGHT, -WIDTH2, WIDTH1, HEIGHT, WIDTH2);
    }
    
    
    @Override
    public AxisAlignedBB getCollisionBox(Entity par1Entity)
    {
        return AxisAlignedBB.getBoundingBox(-WIDTH1, -HEIGHT, -WIDTH2, WIDTH1, HEIGHT, WIDTH2);
    }
    
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		if(var1.typeOfHit == EnumMovingObjectType.ENTITY)
			return;
		
		switch(var1.sideHit){
		case 1:
			if(!onGround){
				tickHit = ticksExisted;
				motionY = 0;
			}
			onGround = true;
			break;
		case 0:
			motionY = -0.01 * motionY;
			break;
		case 2:
		case 3:
			motionZ = - 0.2 * motionZ;
			break;
		case 4:
		case 5:
			motionX = - 0.2 * motionX;
			break;
		default:
			break;
		}
		
		float var10 = MathHelper.sqrt_double(motionX * motionX + motionZ * motionZ);
        this.prevRotationYaw = this.rotationYaw = (float)(Math.atan2(motionX, motionZ) * 180.0D / Math.PI);
        this.prevRotationPitch = this.rotationPitch = (float)(Math.atan2(motionY, (double)var10) * 180.0D / Math.PI);
		
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		NBTTagCompound nbt = getThrower().getEntityData();
		boolean doesExplode = nbt.getBoolean("doesExplode");
		if(doesExplode)
			Explode();
		
		if (this.onGround)
        {
            this.motionX *= 0.7D;
            this.motionZ *= 0.7D;
            this.motionY = 0;
        }
	}
    
}
