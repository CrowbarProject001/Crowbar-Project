package cn.lambdacraft.mob.entity;

import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

/**
 * 为便于mob管理的通用类
 * 
 * @author mkpoli
 *
 */
public abstract class CBCEntityMob extends EntityMob {
	
	public CBCEntityMob(World par1World) {
		super(par1World);
	}

	/**
	 * Change attributes
	 */
	protected void func_110147_ax() {
		super.func_110147_ax();
		this.func_110148_a(SharedMonsterAttributes.field_111267_a)
				.func_111128_a(getMaxHealth()); // Max Health
		if (getFollowRange() != 0)
			this.func_110148_a(SharedMonsterAttributes.field_111265_b)
					.func_111128_a(getFollowRange()); // Follow Range
		if (getKnockBackResistance() != 0)
			this.func_110148_a(SharedMonsterAttributes.field_111266_c)
					.func_111128_a(getKnockBackResistance()); // knockbackResistance
		this.func_110148_a(SharedMonsterAttributes.field_111263_d)
				.func_111128_a(getMoveSpeed()); // Move Speed
		this.func_110148_a(SharedMonsterAttributes.field_111264_e)
				.func_111128_a(getAttackDamage()); // Attack Damage
	}

	abstract protected double getMaxHealth();

	abstract protected double getFollowRange();

	abstract protected double getMoveSpeed();

	abstract protected double getKnockBackResistance();

	abstract protected double getAttackDamage();

	public abstract ResourceLocation getTexture();

	public float getHealth() {
		return this.func_110143_aJ();
	}

	public void setHealth(float health) {
		this.func_110149_m(health);
	}
}
