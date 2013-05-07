package cbproject.deathmatch.entities;

import cbproject.core.utils.MotionXYZ;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityRPGDot extends EntityThrowable {

	public static final double DOT_MAX_RANGE = 30.0;
	private EntityPlayer shooter;
	//-1:Render facing the player. else : Render on block surface.
	private int side;
	
	public EntityRPGDot(World par1World, EntityPlayer player) {
		super(par1World, player);
		System.out.println(player);
		shooter = player;
		updateDotPosition();
	}

	@Override
	protected void entityInit() {
	}
	
	@Override
	public void onUpdate(){
		updateDotPosition();
	}
	
	private void updateDotPosition(){
		MotionXYZ begin = new MotionXYZ(shooter);
		MotionXYZ end = new MotionXYZ(begin).updateMotion(DOT_MAX_RANGE);
		MovingObjectPosition result = worldObj.rayTraceBlocks(begin.asVec3(worldObj), end.asVec3(worldObj));
		System.out.println("pos : " + posX + " " + posY + " " + posZ);
		if(result != null){
			posX = result.hitVec.xCoord;
			posY = result.hitVec.yCoord;
			posZ = result.hitVec.zCoord;
			side = result.sideHit;
		} else {
			posX = end.posX;
			posY = end.posY;
			posZ = end.posZ;
			side = -1;
		}
	}

	public int getDotSide(){
		return side;
	}
	
	@Override
	protected float func_70182_d(){
		return 0.0F;
	}

	@Override
	protected float getGravityVelocity(){
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {

	}

}
