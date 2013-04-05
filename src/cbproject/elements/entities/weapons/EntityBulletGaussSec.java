package cbproject.elements.entities.weapons;

import cbproject.utils.weapons.MotionXYZ;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityBulletGaussSec extends EntityBullet {

	public EntityBulletGaussSec(World par1World, EntityLiving par2EntityLiving,
			ItemStack par3itemStack, String particle) {
		super(par1World, par2EntityLiving, par3itemStack, "");
		// TODO Auto-generated constructor stub
	}

	/*
	 * 正式使用的Constructor
	 * typeOfRay:0 穿射光线 1反射光线
	 */
	public EntityBulletGaussSec(MotionXYZ begin, MotionXYZ end,
			ItemStack itemStack, EntityLiving entityPlayer, World worldObj,
			int damage, MovingObjectPosition result, int typeOfRay) {
		super(worldObj, entityPlayer, itemStack, "");
		double du;
		MotionXYZ real = new MotionXYZ(begin);
		/*
		 * sideHit: Which side was hit. 
		 * If its -1 then it went the full length of the ray trace. 
		 * Bottom = 0, Top = 1, East = 2, West = 3, North = 4, South = 5.
		 * 
		 * EAST: +X, WEST: -X, NORTH: -Z SOUTH: +Z
		 */
		
		if(typeOfRay == 0){
			switch(result.sideHit){
			case 0:
			case 1:
				du = (end.posY - begin.posY) / begin.motionY + (1/begin.motionY);
				break;
			case 2: //MotionX < 0
			case 3:
				du = (end.posX - begin.posX) / begin.motionX + (1/begin.motionX);
				break;
			case 4: //MoZ > 0
			case 5:
				du = (end.posZ - begin.posZ) / begin.motionZ + (1/begin.motionZ);
				break;
			default:
				return;
			}
		} else {
			if(result.sideHit == 1 || result.sideHit == 2 || result.sideHit == 5){
				du = (end.posX - begin.posX) / begin.motionX;
			} else {
				du = (end.posX - begin.posX ) / begin.motionX + Math.abs(1/begin.motionX);
			}
		}
		real.updateMotion(du);
		
		motionX = end.motionX;
		motionY = end.motionY;
		motionZ = end.motionZ;
		posX = real.posX;
		posY = real.posY;
		posZ = real.posZ;
		this.setThrowableHeading(motionX, motionY, motionZ, func_70182_d(), 1.0F);
		if(typeOfRay == 0)
			System.out.println("Penetration." + posX + " " + posY + " " + posZ + " \n" +
					"Motion: " + motionX + " " + motionY + " " + motionZ );
		else
			System.out.println("Refelection." + posX + " " + posY + " " + posZ + " \n" +
					"Motion: " + motionX + " " + motionY + " " + motionZ );
		
		motion = new MotionXYZ(this);
		worldObj.spawnEntityInWorld(new EntityGauss(motion, worldObj, entityPlayer));
	}
	
	@Override
	public float func_70182_d(){
		return 1.0F;
	}
	

}
