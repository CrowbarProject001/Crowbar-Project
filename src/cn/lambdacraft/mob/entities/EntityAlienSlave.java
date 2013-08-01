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
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 */
public class EntityAlienSlave extends EntityMob implements IRangedAttackMob {

	public boolean isCharging;
	public int chargeTick;
	private int lastAttackTick;
	
    private EntityAIArrowAttack aiArrowAttack = new EntityAIArrowAttack(this, 0.55F, 20, 60, 15.0F);
    private EntityAIAttackOnCollide aiAttackOnCollide = new EntityAIAttackOnCollide(this, EntityPlayer.class, 0.31F, false);
    
	/**
	 * @param par1World
	 */
	public EntityAlienSlave(World par1World) {
		super(par1World);
        this.texture = ClientProps.VORTIGAUNT_PATH;
        this.moveSpeed = 0.55F;
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(4, new EntityAIWander(this, this.moveSpeed));
        this.tasks.addTask(4, this.aiArrowAttack);
        this.tasks.addTask(4, this.aiAttackOnCollide);
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityPlayer.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityVillager.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityHeadcrab.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityCreeper.class, 16.0F, 0, true));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityZombie.class, 16.0F, 0, true));
	}
	
	@Override
	public void entityInit() {
		super.entityInit();
		this.setSize(1.5F, 1.8F);
		dataWatcher.addObject(20, Byte.valueOf((byte) 0));
		dataWatcher.addObject(21, Short.valueOf((short) 0));
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(worldObj.isRemote) {
			isCharging = dataWatcher.getWatchableObjectByte(20) == 1;
			chargeTick = dataWatcher.getWatchableObjectShort(21);
		} else {
			dataWatcher.updateObject(20, isCharging? (byte)1 : (byte)0);
			dataWatcher.updateObject(21, (short)chargeTick);
			if(isCharging && this.entityToAttack != null) {
				System.out.println("Charging "+ chargeTick);
				if(++chargeTick >= 55) {
					isCharging = false;
					EntityVortigauntRay ray = new EntityVortigauntRay(worldObj, this, entityToAttack);
					worldObj.spawnEntityInWorld(ray);
					lastAttackTick = ticksExisted;
				}
			}
		}
	}
	
    /**
     * Disables a mob's ability to move on its own while true.
     */
	@Override
    protected boolean isMovementCeased()
    {
        return !isCharging;
    }
	
    /**
     * Attack the specified entity using a ranged attack.
     */
	@Override
    public void attackEntityWithRangedAttack(EntityLiving par1EntityLiving, float par2)
    {
    	System.out.println("attacking");
    	if(!isCharging && ticksExisted - lastAttackTick > 90) {
    		isCharging = true;
    		chargeTick = 0;
    	}
    	entityToAttack = par1EntityLiving;
    }

	/* (non-Javadoc)
	 * @see net.minecraft.entity.EntityLiving#getMaxHealth()
	 */
	@Override
	public int getMaxHealth() {
		return 20;
	}
	
	@Override
    public int getAttackStrength(Entity par1Entity)
    {
    	return 5;
    }
	
    /**
     * Returns true if the newer Entity AI code should be run
     */
    public boolean isAIEnabled()
    {
        return true;
    }

}
