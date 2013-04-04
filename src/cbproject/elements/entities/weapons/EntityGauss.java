package cbproject.elements.entities.weapons;

import cbproject.utils.weapons.MotionXYZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityGauss extends Entity {
	
	
	public Vec3 start, end;
	
	public EntityGauss(World par1World, Vec3 par2, Vec3 par3){
		
		super(par1World);
		start = par2;
		end = par3;
		
	}
	
	public EntityGauss(World par2World, MotionXYZ player, MovingObjectPosition result ){
		
		super(par2World);
		this.posX = player.posX;
		this.posY = player.posY;
		this.posZ = player.posZ;
		motionX = player.motionX;
		motionY = player.motionY;
		motionZ = player.motionZ;
		
		start = player.asVec3(par2World);
		end = result.hitVec;
		
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if(this.ticksExisted > 10) //存在0.5秒
			this.setDead();
	}
	
	@Override
	protected void entityInit() {
		// TODO Auto-generated method stub

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub

	}

}
