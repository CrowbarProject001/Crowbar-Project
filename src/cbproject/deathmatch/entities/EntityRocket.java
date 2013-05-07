package cbproject.deathmatch.entities;

import cbproject.deathmatch.register.DMItems;
import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * RPG rocket entity.
 * @author WeAthFolD
 *
 */
public class EntityRocket extends EntityThrowable {

	private EntityRPGDot dot;
	public static double VEL_CHANGE_SPEED = 0.2;
	
	public EntityRocket(World par1World, EntityPlayer player, ItemStack is) {
		super(par1World, player);
		rotationPitch = player.rotationPitch;
		rotationYaw = player.rotationYaw;
		worldObj.playSoundAtEntity(this, "cbc.weapons.rocket", 0.3F, 1.0F);
		this.dot = DMItems.weapon_RPG.getRPGDot(is, par1World, player);
		System.out.println(dot);
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		worldObj.spawnParticle("smoke", posX, posY, posZ, 0.0, 0.0, 0.0);
		if(this.isBurning())
			Explode();
		if(ticksExisted % 45 == 0)
			worldObj.playSoundAtEntity(this, "cbc.weapons.rocket", 0.5F, 1.0F);
		if(dot == null)
			return;
		if(dot.isDead){
			dot = null;
			return;
		}
		if(dot.posX > posX)
			motionX += VEL_CHANGE_SPEED;
		else motionX -= VEL_CHANGE_SPEED;
		if(dot.posY > posY)
			motionY += VEL_CHANGE_SPEED;
		else motionY -= VEL_CHANGE_SPEED;
		if(dot.posZ > posZ)
			motionZ += VEL_CHANGE_SPEED;
		else motionZ -= VEL_CHANGE_SPEED;
	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		Explode();
	}
	
	private void Explode(){
		BulletManager.Explode(worldObj, this, 5.0F, 5.0F, posX, posY, posZ, 40);
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
    	return 2F;
    }
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}

}
