package cbproject.mob.entities;

import java.util.List;

import cbproject.api.entities.IPlayerLink;
import cbproject.mob.register.MobRegistry;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.enchantment.EnchantmentThorns;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class EntitySnark extends EntityMob implements IPlayerLink{

	public static final float MOVE_SPEED = 2.0F;
	private EntityPlayer thrower;
	
	public EntitySnark(World par1World) {
		super(par1World);
		this.texture = "/mob/spider.png";
		this.setSize(0.4F, 0.3F);
		this.moveSpeed = MOVE_SPEED;
		this.experienceValue = 0;
	}
	
	@Override
	public int getMaxHealth() {
		return 1;
	}
	
	@Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
        int i = this.getAttackStrength(par1Entity);
        boolean flag = par1Entity.attackEntityFrom(DamageSource.causeMobDamage(this), i);
        this.playSound("cbc.mobs.sqk_deploy", 0.5F, 1.0F);

        return flag;
    }
	
	@Override
	/**
	 * Called when the entity is attacked.
	 */
	public boolean attackEntityFrom(DamageSource par1DamageSource, int par2) {
		if (par1DamageSource == DamageSource.fall
				|| par1DamageSource == DamageSource.fallingBlock) {
			return false;
		} else if (super.attackEntityFrom(par1DamageSource, par2)) {
			Entity entity = par1DamageSource.getEntity();
			if (entity != this) {
				this.entityToAttack = entity;
			}

			return true;
		}
		return false;
	}

	/**
	 * Finds the closest player within 16 blocks to attack, or null if this
	 * Entity isn't interested in attacking (Animals, Spiders at day, peaceful
	 * PigZombies).
	 */
	@Override
	protected Entity findPlayerToAttack() {
		AxisAlignedBB boundingBox = AxisAlignedBB.getBoundingBox(posX - 8.0, posY - 8.0, posZ - 8.0, posX +8.0, posY +8.0, posZ +8.0);
		List<EntityLiving> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, boundingBox, MobRegistry.selectorLiving);
		EntityLiving entity = null;
		double distance = 10000.0F;
		for(EntityLiving s : list){
			double dx = s.posX - posX, dy = s.posY - posY, dz = s.posZ - posZ;
			double d = Math.sqrt(dx * dx + dy * dy + dz * dz);
			if(s instanceof EntitySnark || (s == thrower && ticksExisted < 80 ))
				continue;
			if(d < distance){
				entity = s;
				distance = d;
			}
		}
		if(entity == null)
			return null;
		return entity;
	}

	/**
	 * Returns the sound this mob makes while it's alive.
	 */
	@Override
	protected String getLivingSound() {
		int random = (int) Math.round(Math.random() * 3);
		return random < 1 ? "cbc.mobs.sqk_hunta"
				: (random < 2 ? "cbc.mobs.sqk_huntb" : "cbc.mobs.sqk_huntc");
	}

	/**
	 * Returns the sound this mob makes when it is hurt.
	 */
	@Override
	protected String getHurtSound() {
		return "";
	}

	/**
	 * Returns the sound this mob makes on death.
	 */
	@Override
	protected String getDeathSound() {
		return "cbc.mobs.sqk_die";
	}

	/**
	 * Basic mob attack. Default to touch of death in EntityCreature. Overridden
	 * by each mob to define their attack.
	 */
	protected void attackEntity(Entity par1Entity, float par2) {
		if (par2 > 2.0F && par2 < 6.0F && this.rand.nextInt(10) == 0) {
			if (this.onGround) {
				double d0 = par1Entity.posX - this.posX;
				double d1 = par1Entity.posZ - this.posZ;
				float f2 = MathHelper.sqrt_double(d0 * d0 + d1 * d1);
				this.motionX = d0 / (double) f2 * 0.2 + this.motionX * 0.20;
				this.motionZ = d1 / (double) f2 * 0.2 + this.motionZ * 0.20;
				this.motionY = 0.40;
			}
		} else {
			super.attackEntity(par1Entity, par2);
		}
	}

	/**
	 * Returns the item ID for the item the mob drops on death.
	 */
	@Override
	protected int getDropItemId() {
		return 0;
	}

	/**
	 * Drop 0-2 items of this living's type. @param par1 - Whether this entity
	 * has recently been hit by a player. @param par2 - Level of Looting used to
	 * kill this mob.
	 */
	protected void dropFewItems(boolean par1, int par2) {
	}

	/**
	 * Get this Entity's EnumCreatureAttribute
	 */
	public EnumCreatureAttribute getCreatureAttribute() {
		return EnumCreatureAttribute.ARTHROPOD;
	}

	public boolean isPotionApplicable(PotionEffect par1PotionEffect) {
		return par1PotionEffect.getPotionID() == Potion.poison.id ? false
				: super.isPotionApplicable(par1PotionEffect);
	}

	/**
	 * Initialize this creature.
	 */
	@Override
	public void initCreature() {
		this.playSound("cbc.mobs.sqk_deploy", 0.5F, 1.0F);
	}

	/**
	 * Returns the amount of damage a mob should deal.
	 */
	@Override
	public int getAttackStrength(Entity par1Entity) {
		return 2;
	}

	@Override
	public EntityPlayer getLinkedPlayer() {
		return this.thrower;
	}

	@Override
	public void setLinkedPlayer(EntityPlayer pla) {
		thrower = pla;
	}

}
