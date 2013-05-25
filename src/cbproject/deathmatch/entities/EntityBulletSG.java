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

import cbproject.deathmatch.items.wpns.WeaponGeneral;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.boss.EntityDragonPart;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

/**
 * TODO:未完成
 * @author Administrator
 *
 */
public class EntityBulletSG extends EntityBullet {

	private final BulletSG shotgun;
	
	public EntityBulletSG(World par1World, EntityLiving par2EntityLiving,
			ItemStack par3itemStack, BulletSG sg) {
		super(par1World, par2EntityLiving, par3itemStack);
		shotgun = sg;
	}

	public EntityBulletSG(World world) {
		super(world);
		shotgun = null;
	}
	
	@Override
	protected void entityInit(){
	}
	
	@Override
	public void doBlockCollision(MovingObjectPosition result){
		System.out.println("hit block");
	}
	
	@Override
	public void doEntityCollision(MovingObjectPosition result){
		
		
		System.out.println("hit!" + result.entityHit);
		if( result.entityHit == null)
			return;
		if(!(result.entityHit instanceof EntityLiving || result.entityHit instanceof EntityDragonPart || result.entityHit instanceof EntityEnderCrystal))
			return;
		WeaponGeneral item = (WeaponGeneral) itemStack.getItem();
		int damage = item.getDamage(0);
		shotgun.onBulletHit(result.entityHit, damage);
		
	}
	
	@Override
	public boolean canBeCollidedWith(){
		return false;
	}

}
