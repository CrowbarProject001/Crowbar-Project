/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.deathmatch.entities;

import cbproject.deathmatch.utils.BulletManager;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * 十字弩的爆炸弩箭实体。
 * @author WeAthFolD
 *
 */
public class EntityCrossbowArrow extends EntityThrowable {

	public EntityCrossbowArrow(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
	}
	
	public EntityCrossbowArrow(World world){
		super(world);
	}
	
	@Override
	protected void onImpact(MovingObjectPosition var1) {
		Explode();	
	}
	
	private void Explode(){
		BulletManager.Explode(worldObj, this, 1.0F, 3.0F, posX, posY, posZ, 30);
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
    	return 5.0F;
    }
	
	@Override
	public boolean canBeCollidedWith()
	{
	    return true;
	}

}
