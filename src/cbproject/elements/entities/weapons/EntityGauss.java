package cbproject.elements.entities.weapons;

import cbproject.utils.weapons.MotionXYZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityGauss extends Entity {
	
	public EntityGauss(EntityLiving player,World par1World){
		
		super(par1World);
		this.posX = player.posX;
		this.posY = player.posY;
		this.posZ = player.posZ;
		motionX = player.motionX;
		motionY = player.motionY;
		motionZ = player.motionZ;
		
	}
	
	public EntityGauss(MotionXYZ player, World par2World){
		
		super(par2World);
		this.posX = player.posX;
		this.posY = player.posY;
		this.posZ = player.posZ;
		motionX = player.motionX;
		motionY = player.motionY;
		motionZ = player.motionZ;
		
	}

	@Override
	public void onUpdate(){
		super.onUpdate();
		if(this.ticksExisted > 10)
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
