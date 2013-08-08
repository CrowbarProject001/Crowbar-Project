package cn.lambdacraft.deathmatch.utils;

import java.util.List;

import cn.lambdacraft.core.network.NetExplosion;
import cn.lambdacraft.core.proxy.GeneralProps;
import cn.lambdacraft.core.utils.CBCExplosion;
import cn.lambdacraft.deathmatch.entities.EntityBullet;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BulletManager {

	private static final double BB_SIZE = 0.5D;
	private static final int ENTITY_TRACE_RANGE = 128;

	public static void Shoot(ItemStack itemStack, EntityLiving entityPlayer,
			World worldObj) {
		worldObj.spawnEntityInWorld(new EntityBullet(worldObj, entityPlayer,
				itemStack));
	}
	
	public static void Shoot(int damage, EntityLiving entityPlayer,
			World worldObj) {
		worldObj.spawnEntityInWorld(new EntityBullet(worldObj, entityPlayer,
				damage));
	}

	public static void doEntityAttack(Entity ent, DamageSource ds, int damage,
			double dx, double dy, double dz) {
		if (GeneralProps.doPlayerDamage)
			ent.attackEntityFrom(ds, damage);
		// ent.addVelocity(dx, dy, dz);
	}

	public static void doEntityAttack(Entity ent, DamageSource ds, int damage) {
		doEntityAttack(ent, ds, damage, 0, 0, 0);
	}

	public static void Explode(World world, Entity entity, float strengh,
			double radius, double posX, double posY, double posZ,
			int additionalDamage) {
		Explode(world, entity, strengh, radius, posX, posY, posZ,
				additionalDamage, 1.0, 1.0F);
	}
	
	public static MovingObjectPosition rayTraceBlocksAndEntities(World world, Vec3 vec1, Vec3 vec2) {
		MovingObjectPosition mop = rayTraceEntities(null, world, vec1, vec2);
		if(mop == null)
			return world.rayTraceBlocks(vec1, vec2);
		return mop;
	}
	
	public static MovingObjectPosition rayTraceBlocksAndEntities(IEntitySelector selector, World world, Vec3 vec1, Vec3 vec2, Entity... exclusion) {
		MovingObjectPosition mop = rayTraceEntities(selector, world, vec1, vec2, exclusion);
		if(mop == null)
			return world.rayTraceBlocks(vec1, vec2);
		return mop;
	}
	
	public static MovingObjectPosition rayTraceEntities(IEntitySelector selector, World world, Vec3 vec1, Vec3 vec2, Entity... exclusion) {
        Entity entity = null;
        AxisAlignedBB boundingBox = getBoundingBox(vec1, vec2);
        List list = world.getEntitiesWithinAABBExcludingEntity(null, boundingBox.expand(1.0D, 1.0D, 1.0D), selector);
        double d0 = 0.0D;

        for (int j = 0; j < list.size(); ++j)
        {
            Entity entity1 = (Entity)list.get(j);

            Boolean b = entity1.canBeCollidedWith();
            if(!b)
            	continue;
            for(Entity e : exclusion) {
            	if(e == entity1)
            		b = false;
            }
            if (entity1.canBeCollidedWith() && b)
            {
                float f = 0.3F;
                AxisAlignedBB axisalignedbb = entity1.boundingBox.expand(f, f, f);
                MovingObjectPosition movingobjectposition1 = axisalignedbb.calculateIntercept(vec1, vec2);

                if (movingobjectposition1 != null)
                {
                    double d1 = vec1.distanceTo(movingobjectposition1.hitVec);

                    if (d1 < d0 || d0 == 0.0D)
                    {
                        entity = entity1;
                        d0 = d1;
                    }
                }
            }
        }

        if (entity != null)
        {
            return new MovingObjectPosition(entity);
        }
        return null;
	}
	
	private static AxisAlignedBB getBoundingBox(Vec3 vec1, Vec3 vec2) {
		double minX = 0.0, minY = 0.0, minZ = 0.0, maxX = 0.0, maxY = 0.0, maxZ = 0.0;
		if(vec1.xCoord < vec2.xCoord) {
			minX = vec1.xCoord;
			maxX = vec2.xCoord;
		} else {
			minX = vec2.xCoord;
			maxX = vec1.xCoord;
		}
		if(vec1.yCoord < vec2.yCoord) {
			minY = vec1.yCoord;
			maxY = vec2.yCoord;
		} else {
			minY = vec2.yCoord;
			maxY = vec1.yCoord;
		}
		if(vec1.zCoord < vec2.zCoord) {
			minZ = vec1.zCoord;
			maxZ = vec2.zCoord;
		} else {
			minZ = vec2.zCoord;
			maxZ = vec1.zCoord;
		}
		return AxisAlignedBB.getBoundingBox(minX, minY, minZ, maxX, maxY, maxZ);
	}

	public static void Explode(World world, Entity entity, float strengh,
			double radius, double posX, double posY, double posZ,
			int additionalDamage, double velocityRadius, float soundRadius) {

		CBCExplosion explosion = new CBCExplosion(world, entity, posX, posY,
				posZ, strengh).setSoundFactor(soundRadius).setVelocityFactor(
				velocityRadius);
		explosion.isSmoking = true;
		explosion.isFlaming = false;

		explosion.doExplosionA();
		explosion.doExplosionB(true);

		if (additionalDamage <= 0 || !GeneralProps.doPlayerDamage)
			return;

		AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(posX - 4, posY - 4,
				posZ - 4, posX + 4, posY + 4, posZ + 4);
		List entitylist = world
				.getEntitiesWithinAABBExcludingEntity(null, par2);
		if (entitylist.size() > 0) {
			for (int i = 0; i < entitylist.size(); i++) {
				Entity ent = (Entity) entitylist.get(i);
				if (ent instanceof EntityLiving) {
					double distance = Math.sqrt(Math.pow(ent.posX - posX, 2)
							+ Math.pow(ent.posY - posY, 2)
							+ Math.pow(ent.posZ - posZ, 2));
					int damage = (int) ((1 - distance / 6.928) * additionalDamage);
					ent.attackEntityFrom(
							DamageSource.setExplosionSource(explosion
									.asDefaultExplosion()), damage);
				}
			}
		}
		NetExplosion.sendNetPacket(world, (float) posX, (float) posY,
				(float) posZ, strengh);
	}

	@SideOnly(Side.CLIENT)
	public static void clientExplode(World world, float strengh, double posX,
			double posY, double posZ) {
		CBCExplosion explosion = new CBCExplosion(world, null, posX, posY,
				posZ, strengh);
		explosion.isSmoking = true;
		explosion.isFlaming = false;

		explosion.doExplosionA();
		explosion.doExplosionB(true);
	}
}
