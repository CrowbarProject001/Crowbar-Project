package cbproject.elements.entities.weapons;

import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.weapons.InformationEnergy;
import cbproject.utils.weapons.InformationWeapon;
import cbproject.utils.weapons.MotionXYZ;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBulletGauss extends EntityBullet {

	public Vec3 start;
	private MotionXYZ motion;
	public EntityBulletGauss(World par1World, EntityLiving par2EntityLiving,
			ItemStack par3itemStack, String particle) {
		
		super(par1World, par2EntityLiving, par3itemStack, particle);
		start = Vec3.createVectorHelper(par2EntityLiving.posX, par2EntityLiving.posY, par2EntityLiving.posZ);
		motion = new MotionXYZ(par2EntityLiving);
		worldObj.spawnEntityInWorld(new EntityGauss(par1World, par2EntityLiving, motion.motionX, motion.motionY, motion.motionZ));
	}
	
	protected void doBlockCollision(MovingObjectPosition result){	
		
	}
	
	
	@Override
	protected void onImpact(MovingObjectPosition par1)
	{    
		super.onImpact(par1);
		
	}
	
	public void doEntityCollision(MovingObjectPosition result){
	
		if( result.entityHit == null || (!(result.entityHit instanceof EntityLiving)))
			return;		
		
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		InformationEnergy inf = item.getInformation(itemStack).getProperEnergy(worldObj);
		int mode = inf.mode;
		if(mode == 0){
			double pf = item.pushForce[mode];
			double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ * pf;
			EntityLiving mob = (EntityLiving) result.entityHit;
	
			mob.attackEntityFrom(DamageSource.causeMobDamage(player), item.damage[mode]);
			mob.addVelocity(dx, dy, dz);
		} else doCharge(result, inf, item);
		
	}
	
	private void doCharge(MovingObjectPosition result, InformationEnergy information, WeaponGeneral item) {

		int mode = information.mode;
		
		double pf = item.pushForce[mode];
		double dx = motion.motionX * pf, dy = motion.motionY * pf, dz = motion.motionZ * pf;
		int damage = (int) Math.round(information.charge * 0.66);
		EntityLiving mob = (EntityLiving) result.entityHit;
		
		mob.attackEntityFrom(DamageSource.causeMobDamage(player), damage);
		mob.addVelocity(dx, dy, dz);
		dx *= damage/20;
		dy *= damage/20;
		dz *= damage/20;
		
	}

}
