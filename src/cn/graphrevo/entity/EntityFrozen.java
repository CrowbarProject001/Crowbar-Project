/**
 * Code by Lambda Innovation, 2013.
 */
package cn.graphrevo.entity;

import net.minecraft.block.Block;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * 
 */
public class EntityFrozen extends EntityThrowable {

	//伤害值
	int damage = 10;
	
	/**
	 * 在客户端同步时被调用的构造器。
	 */
	public EntityFrozen(World par1World) {
		super(par1World);
		setSize(0.8F, 0.8F);
	}

	/**
	 * 我们在服务端生成时调用的构造器。
	 */
	public EntityFrozen(World par1World, EntityLivingBase entityLivingBase, int dmg) {
		super(par1World, entityLivingBase);
		this.damage = dmg;
		Vec3 vec3 = entityLivingBase.getLookVec();
		this.setThrowableHeading(vec3.xCoord, vec3.yCoord, vec3.zCoord, func_70182_d(), 1.0F);
		setSize(0.8F, 0.8F);
	}

	/**
	 * 当实体碰撞到其他世界物品时被调用。进行碰撞计算。
	 */
	@Override
	protected void onImpact(MovingObjectPosition mop) {
		Vec3 vec3 = mop.hitVec; //获取碰撞结果的“碰撞位置”Vec3
		int beginX = (int) (vec3.xCoord - 4),
			beginY = (int) (vec3.yCoord - 2),
			beginZ = (int) (vec3.zCoord - 4);
		
		for(int x = beginX + 8; x >= beginX; x--) //替换水
			for(int y = beginY + 3; y >= beginY; y--)
				for(int z = beginZ + 8; z >= beginZ; z--) {
					int blockID = worldObj.getBlockId(x, y, z);
					if(blockID != 0) { //无差别冰冻方块
						worldObj.setBlock(x, y, z, Block.ice.blockID, 0, 0x03);
					}
				}
		
		//伤害实体
		if(mop.typeOfHit == EnumMovingObjectType.ENTITY) {
			mop.entityHit.attackEntityFrom(DamageSource.causeThornsDamage(this), damage);
		}
		
		this.setDead();
	}
	
	/**
	 * 获取实体的初速度值。
	 */
    protected float func_70182_d()
    {
        return 0.17F * damage;
    }
    
    /**
     * 获取重力加速度的大小。
     */
    protected float getGravityVelocity()
    {
        return 0.05F; //重力影响极小
    }

}
