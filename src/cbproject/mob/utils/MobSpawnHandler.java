package cbproject.mob.utils;

import cbproject.api.entities.IEntityLink;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.MathHelper;
import net.minecraft.world.World;

public class MobSpawnHandler {

	/**
	 * 把一个奇怪的生物生成到世界中。
	 */
	public static Entity spawnCreature(World par0World,
			Class <? extends EntityLiving> c, EntityPlayer thrower) {
	
		Entity entity = null;

		try {
			entity = c.getConstructor(World.class).newInstance(par0World);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		if (entity != null && entity instanceof EntityLiving) {
			EntityLiving entityliving = (EntityLiving) entity;
			float f = 0.4F;
			double motionX = -MathHelper.sin(thrower.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(thrower.rotationPitch / 180.0F * (float)Math.PI) * f;
			double motionZ = MathHelper.cos(thrower.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(thrower.rotationPitch / 180.0F * (float)Math.PI) * f;
			entityliving.setLocationAndAngles(thrower.posX + motionX * 2, thrower.posY, thrower.posZ + motionZ * 2, thrower.rotationYawHead, 0.0F);
			entityliving.rotationYawHead = entityliving.rotationYaw;
            entityliving.renderYawOffset = entityliving.rotationYaw;
            if(entityliving instanceof IEntityLink){
            	((IEntityLink)entityliving).setLinkedEntity(thrower);
            }
			entityliving.initCreature();
			par0World.spawnEntityInWorld(entity);
			entityliving.playLivingSound();
		}

		return entity;
	}
	
    private static void setEntityHeading(Entity ent, double par1, double par3, double par5, double moveSpeed)
    {
        float f2 = MathHelper.sqrt_double(par1 * par1 + par3 * par3 + par5 * par5);
        par1 /= f2;
        par3 /= f2;
        par5 /= f2;
        par1 *= moveSpeed;
        par3 *= moveSpeed;
        par5 *= moveSpeed;
        ent.motionX = par1;
        ent.motionY = par3;
        ent.motionZ = par5;
        float f3 = MathHelper.sqrt_double(par1 * par1 + par5 * par5);
        ent.prevRotationYaw = ent.rotationYaw = (float)(Math.atan2(par1, par5) * 180.0D / Math.PI);
        ent.prevRotationPitch = ent.rotationPitch = (float)(Math.atan2(par3, f3) * 180.0D / Math.PI);
    }

}
