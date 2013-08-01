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

import cn.lambdacraft.core.proxy.ClientProps;
import cn.lambdacraft.core.utils.GenericUtils;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIAttackOnCollide;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAIBreakDoor;
import net.minecraft.entity.ai.EntityAIHurtByTarget;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveThroughVillage;
import net.minecraft.entity.ai.EntityAIMoveTwardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWander;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

/**
 * 喜闻乐见的僵尸，第一次写mob 肯定很渣233
 * @author mkpoli
 */
public class EntityHLZombie extends EntityMob {

	public int damagetype;
	public int damage;
	protected int playtick = 0;
	public Entity entityToAttack;
	public int tickCountAttack;
	protected EntityAIBase aiAttackPlayer, aiAttackVillager;

	public EntityHLZombie(World par1World) {
		super(par1World);
		this.texture = ClientProps.ZOMBIE_MOB_PATH;
		this.moveSpeed = 0.23F;
		this.experienceValue = 10;
		this.getNavigator().setBreakDoors(true);
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIBreakDoor(this));
        this.tasks.addTask(2, new EntityAIAttackOnCollide(this, EntityPlayer.class, this.moveSpeed, false));
        this.tasks.addTask(3, new EntityAIAttackOnCollide(this, EntityVillager.class, this.moveSpeed, true));
        this.tasks.addTask(4, new EntityAIMoveTwardsRestriction(this, this.moveSpeed));
        this.tasks.addTask(5, new EntityAIMoveThroughVillage(this, this.moveSpeed, false));
        this.tasks.addTask(6, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true));
        aiAttackPlayer = new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true);
        this.targetTasks.addTask(2, aiAttackPlayer);
        aiAttackVillager = new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, false);
        this.targetTasks.addTask(2, aiAttackVillager);
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		this.dataWatcher.addObject(20, Byte.valueOf((byte)0));
	}

	@Override
	public int getMaxHealth() {
		return 30;
	}
	
	@Override
	public void onUpdate() {
		boolean preIsBurning = this.isBurning();
		super.onUpdate();
		updateStats();
		if(isAttacking()) {
			System.out.println(worldObj.isRemote + " Attacking " + tickCountAttack);
			if(--tickCountAttack <= 0)
				doRealAttack();
		}
		if (!this.worldObj.isRemote) {
			if (this.isBurning()) {
				this.targetTasks.removeTask(aiAttackPlayer);
				this.targetTasks.removeTask(aiAttackVillager);
				onBuring();
			} else if(preIsBurning)
				this.attackEntityFrom(DamageSource.causeMobDamage(this), 200);
		}
	}
	
	public boolean isAttacking() {
		return (worldObj.isRemote && tickCountAttack > 0) || this.entityToAttack != null;
	}

	public boolean attackEntityAsMob(Entity par1Entity) {
		if(this.isBurning())
			return false;
		damagetype = this.worldObj.rand.nextInt(3);
		if (damagetype == 0) {
			this.worldObj.playSoundAtEntity(par1Entity, "cbc.mobs.zo_attacka", 0.5f, 1.0f);
			damage = 6;
		} else {
			this.worldObj.playSoundAtEntity(par1Entity, "cbc.mobs.zo_attackb", 0.5f, 1.0f);
			damage = 3;
		}
		this.entityToAttack = par1Entity;
		this.tickCountAttack = damagetype == 0 ? 10 : 5;
		return false;
	}
	
	protected void doRealAttack() {
		if(!worldObj.isRemote) {
			System.out.println(this + " " + entityToAttack.hurtResistantTime);
			entityToAttack.attackEntityFrom(DamageSource.causeMobDamage(this), damage);
		}
		this.entityToAttack = null;
		this.tickCountAttack = 0;
	}

	@Override
	public void onDeath(DamageSource par1DamageSource) {
		super.onDeath(par1DamageSource);
		if (!this.worldObj.isRemote && !par1DamageSource.isFireDamage()) {
			EntityHeadcrab entityHeadcrab = new EntityHeadcrab(this.worldObj);
			entityHeadcrab.setLocationAndAngles(this.posX, this.posY + this.height, this.posZ, MathHelper.wrapAngleTo180_float(this.worldObj.rand.nextFloat() * 360.0F), 0.0F);
			this.worldObj.spawnEntityInWorld(entityHeadcrab);
			entityHeadcrab.setEntityHealth(10);
		}
	}
	
	public void updateStats() {
		if(worldObj.isRemote) {
			Byte a = dataWatcher.getWatchableObjectByte(20);
			if(!this.isAttacking() && a != 0) {
				this.damagetype = a - 1;
				tickCountAttack = damagetype == 0 ? 10 : 5;
			} 
		}
		else {
			dataWatcher.updateObject(20, Byte.valueOf((byte)((isAttacking() ? 1 : 0) * (this.damagetype + 1))));
		}
	}
	
	public void onBuring() {
		if(playtick >= 40) {
			this.worldObj.playSoundAtEntity(this, GenericUtils.getRandomSound("cbc.mobs.zo_moan_loop", 3), 0.5F, 1.0F);
			playtick = 0;
		}
		++playtick;
		this.moveSpeed = 1.0f;
	}

	@Override
    protected int getDropItemId()
    {
        return Item.rottenFlesh.itemID;
    }
	
    /**
     * Returns true if the newer Entity AI code should be run
     */
    protected boolean isAIEnabled()
    {
        return true;
    }
    
	@Override
	protected String getLivingSound() {
		return GenericUtils.getRandomSound("cbc.mobs.zo_idle", 3);
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return GenericUtils.getRandomSound("cbc.mobs.zo_pain", 2);
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return GenericUtils.getRandomSound("cbc.mobs.hc_die", 2);
	}
    
	/* (non-Javadoc)
	 * @see net.minecraft.entity.monster.EntityMob#attackEntity(net.minecraft.entity.Entity, float)
	 */
	@Override
	protected void attackEntity(Entity par1Entity, float par2) {
		super.attackEntity(par1Entity, par2);
	}	
    
}
