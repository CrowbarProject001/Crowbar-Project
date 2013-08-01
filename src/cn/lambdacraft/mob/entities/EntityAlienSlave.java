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
package cn.lambdacraft.mob.entities;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.core.utils.BlockPos;
import cn.lambdacraft.core.utils.GenericUtils;
import cn.lambdacraft.deathmatch.entities.EntityBullet;
import cn.lambdacraft.mob.register.CBCMobItems;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.ai.EntityAIArrowAttack;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIFleeSun;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIRestrictSun;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class EntityAlienSlave extends EntityMob {

	public boolean isCharging;
	public int chargeTick;
	private int lastAttackTick;
	
	boolean lastTickFleeing = false;
	
	public HashSet<Vec3> electrolyze_left = new HashSet(), electrolyze_right = new HashSet();
	
	/**
	 * @param par1World
	 */
	public EntityAlienSlave(World par1World) {
		super(par1World);
        this.texture = ClientProps.VORTIGAUNT_PATH;
        this.moveSpeed = 0.65F;
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		this.setSize(1.5F, 1.8F);
		dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		dataWatcher.addObject(21, Short.valueOf((short) 0));
		dataWatcher.addObject(22, Integer.valueOf(0));
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		//Sync&Calculate Charging
		if(worldObj.isRemote) {
			boolean preisCharging = isCharging;
			if(!preisCharging) {
				chargeTick = dataWatcher.getWatchableObjectShort(21);
				isCharging = dataWatcher.getWatchableObjectByte(20) == 1;
				if(isCharging) {
					this.updateElectrolyzes();
				}
			} else isCharging = dataWatcher.getWatchableObjectByte(20) == 1;
			entityToAttack = worldObj.getEntityByID(dataWatcher.getWatchableObjectInt(22));
		} else {
			dataWatcher.updateObject(20, isCharging? (byte)1 : (byte)0);
			dataWatcher.updateObject(21, (short)chargeTick);
			dataWatcher.updateObject(22, entityToAttack == null ? 0 : entityToAttack.entityId);
			
			//Path movement
			if(this.entityToAttack != null ) {
				double distanceSq = this.getDistanceSqToEntity(entityToAttack);
				if(distanceSq > 25.0) {
					//original pathNavigate
					lastTickFleeing = false;
					if(lastTickFleeing || rand.nextInt(20) == 0)
						this.setPathToEntity(worldObj.getPathEntityToEntity(this, entityToAttack, 16.0F, true, false, false, true));
				} else if(!lastTickFleeing || rand.nextInt(20) == 0){
					//else try to get away from the player
					lastTickFleeing = true;
					int x = (int) (3 * posX - 2 * entityToAttack.posX), y = (int) (3 * posY - 2 * entityToAttack.posY), z = (int) (3 * posZ - 2 * entityToAttack.posZ);
					x += rand.nextInt(5) - 2;
					y += rand.nextInt(3) - 1;
					z += rand.nextInt(5) - 2;
					this.setPathToEntity(worldObj.getEntityPathToXYZ(this, x , y, z, 16.0F, true, false, false, true));
				}
			} else {
				if(this.rand.nextInt(30) == 0 || this.fleeingTick > 0) {
					this.updateWanderPath();
				}
			}
		}
		
		if(isCharging && this.entityToAttack != null) {
			if(++chargeTick >= 30) {
				isCharging = false;
				Entity ray = worldObj.isRemote ? new EntityVortigauntRay(worldObj, this, entityToAttack) : new EntityBullet(worldObj, this, entityToAttack, 6);
				worldObj.spawnEntityInWorld(ray);
				this.playSound("cbc.mobs.zapa", 0.5F, 1.0F);
				this.playSound(GenericUtils.getRandomSound("cbc.weapons.electro", 3), 0.5F, 1.0F);
				lastAttackTick = ticksExisted;
				electrolyze_left.clear();
				electrolyze_right.clear();
			}
		}
		
	}
	
	private void updateElectrolyzes() {
		Set<BlockPos> set_left = GenericUtils.getBlocksWithinAABB(worldObj, AxisAlignedBB.getBoundingBox(posX - 2, posY - 1, posZ - 2, posX + 2, posY + 2, posZ + 2));
		Iterator<BlockPos> iterator = set_left.iterator();
		int cnt = 0;
		while(iterator.hasNext() && ++cnt < 10) {
			BlockPos bp = iterator.next();
			HashSet set = cnt < 5 ? electrolyze_left : electrolyze_right;
			set.add(Vec3.createVectorHelper
					(bp.x + rand.nextDouble() - 0.5 - posX, bp.y + rand.nextDouble() - 0.5 - posY, bp.z + rand.nextDouble() - 0.5 - posZ));
		}
	}

	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		if(par2 >= 3.0F && par2 <= 16.0F && ticksExisted - lastAttackTick > 40 && rand.nextInt(5) == 0 && !isCharging) {
			this.playSound("cbc.mobs.zapd", 0.5F, 1.0F);
			isCharging = true;
			chargeTick = 0;
		} else {
			super.attackEntity(par1Entity, par2);
		}
	}
	
	@Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, int par2)
    {
    	if(super.attackEntityFrom(par1DamageSource, par2)) {
    		this.electrolyze_left.clear();
    		this.electrolyze_right.clear();
    		this.isCharging = false;
    		lastAttackTick = ticksExisted;
    		return true;
    	} else return false;
    }
	
    /**
     * Disables a mob's ability to move on its own while true.
     */
	@Override
    protected boolean isMovementCeased()
    {
        return true;
    }
	
	@Override
	protected boolean isMovementBlocked()
	{
		return isCharging;
	}
	
    public EntityItem dropItemWithOffset(int par1, int par2, float par3)
    {
        return this.entityDropItem(new ItemStack(par1, par2, 4), par3);
    }
    
    @Override
    public int getDropItemId() {
    	return CBCMobItems.bioTissue.itemID;
    }
	
	@Override
    protected Entity findPlayerToAttack()
    {
    	Entity e = super.findPlayerToAttack();
    	if(e != null && rand.nextInt(3) == 0) {
    		this.playSound(GenericUtils.getRandomSound("cbc.mobs.slv_alert", 3), 0.5F, 1.0F);
    	}
    	return e;
    }
	
	@Override
    protected String getLivingSound()
    {
    	return GenericUtils.getRandomSound("cbc.mobs.slv_word", 8);
    }
    
    /**
     * Returns the sound this mob makes when it is hurt.
     */
	@Override
    protected String getHurtSound()
    {
        return GenericUtils.getRandomSound("cbc.mobs.slv_pain", 2);
    }
    
	@Override
    protected String getDeathSound()
    {
        return GenericUtils.getRandomSound("cbc.mobs.slv_die", 2);
    }
	
	/* (non-Javadoc)
	 * @see net.minecraft.entity.EntityLiving#getMaxHealth()
	 */
	@Override
	public int getMaxHealth() {
		return 16;
	}
	
	@Override
    public int getAttackStrength(Entity par1Entity)
    {
    	return 5;
    }
}
