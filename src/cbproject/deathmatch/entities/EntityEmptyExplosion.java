package cbproject.deathmatch.entities;

import cbproject.deathmatch.utils.BulletManager;
import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class EntityEmptyExplosion extends Entity {

	public float strengh, radius, sndRadius;
	public int additionalDamage;
	public double velRadius;
	public EntityEmptyExplosion(World par1World, double x, double y, double z) {
		super(par1World);
		posX = x;
		posY = y + 1;
		posZ = z;
	}
	
	public EntityEmptyExplosion setExplodeProps(float s, float r, int add, double vr, float sr){
		radius = r;
		strengh = s;
		additionalDamage = add;
		velRadius = vr;
		sndRadius = sr;
		return this;
	}

	@Override
	protected void entityInit() {
		
	}
	
	@Override
	public void onUpdate(){
		if(ticksExisted > 2){
			BulletManager.Explode(worldObj, this, strengh, radius, posX, posZ, posZ, additionalDamage, velRadius, sndRadius);
			System.out.println("I've exploded in " + posX + " " + posY + " " + posZ + " strengh: " + strengh);
			setDead();
		}
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbttagcompound) {
		// TODO Auto-generated method stub
		
	}



}
