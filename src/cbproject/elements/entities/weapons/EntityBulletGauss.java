package cbproject.elements.entities.weapons;

import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.weapons.InformationWeapon;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class EntityBulletGauss extends EntityBullet {

	public Vec3 start;
	public EntityBulletGauss(World par1World, EntityLiving par2EntityLiving,
			ItemStack par3itemStack, String particle) {
		super(par1World, par2EntityLiving, par3itemStack, particle);
		start = Vec3.createVectorHelper(par2EntityLiving.posX, par2EntityLiving.posY, par2EntityLiving.posZ);
		// TODO Auto-generated constructor stub
	}
	
	protected void doBlockCollision(MovingObjectPosition result){	
		
	}
	
	public void doEntityCollision(MovingObjectPosition result){
	
		if( result.entityHit == null || (!(result.entityHit instanceof EntityLiving)))
			return;
		
		worldObj.spawnEntityInWorld(new EntityGauss(worldObj, start, result.hitVec));
		
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		InformationWeapon inf = item.getInformation(itemStack).getProperInf(worldObj);
		int mode = inf.mode;
		if(mode == 0){
			double pf = item.pushForce[mode];
			double dx = motionX * pf, dy = motionY * pf, dz = motionZ * pf;
			EntityLiving mob = (EntityLiving) result.entityHit;
	
			mob.attackEntityFrom(DamageSource.causeMobDamage(player), item.damage[mode]);
			mob.addVelocity(dx, dy, dz);
		} else doCharge(result);
		
	}
	
	private void doCharge(MovingObjectPosition result) {
		// TODO Auto-generated method stub
		
	}

}
