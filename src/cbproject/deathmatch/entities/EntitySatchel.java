package cbproject.deathmatch.entities;

import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * Satchel entity.
 * @author WeAthFolD
 *
 */
public class EntitySatchel extends EntityProjectile {
	
	public static double HEIGHT = 0.083, WIDTH1 = 0.2, WIDTH2 = 0.15;
	public int tickHit = 0;
	public float rotationFactor;

	public EntitySatchel(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
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
	
	@Override
    protected float getGravityVelocity()
    {
        return 0.025F;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox()
    {
        return AxisAlignedBB.getBoundingBox(-WIDTH1, -HEIGHT, -WIDTH2, WIDTH1, HEIGHT, WIDTH2);
    }

	@Override
	protected float getHeadingVelocity() {
		return 0.7F;
	}

	@Override
	protected float getMotionYOffset() {
		return 0;
	}

	@Override
	protected void entityInit() {
		rotationFactor = 0.0F;
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		boolean doesExplode = getThrower().getEntityData().getBoolean("doesExplode");
		if(doesExplode)
			Explode();
		if(this.onGround){
			rotationFactor += 0.01F;
		} else rotationFactor += 3.0F;
		if(rotationFactor > 360.0F)
			rotationFactor = 0.0F;
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		
	}


	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		
	}


	@Override
	protected void onCollide(MovingObjectPosition result) {
		switch(result.sideHit){
		case 2:
		case 3:
			motionZ = -0.6 * motionZ;
			return;
		case 4:
		case 5:
			motionX = -0.6*motionX;
			return;
		default:
			return;
		}
	}

    
}
