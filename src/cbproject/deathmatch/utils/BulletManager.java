package cbproject.deathmatch.utils;

import java.util.List;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;

import cbproject.core.network.NetExplosion;
import cbproject.core.utils.CBCExplosion;
import cbproject.deathmatch.entities.EntityBullet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class BulletManager {

	private static final double BB_SIZE = 0.5D;
	private static final int ENTITY_TRACE_RANGE = 128;
	
	public static void Shoot(ItemStack itemStack, EntityLiving entityPlayer, World worldObj){
		worldObj.spawnEntityInWorld(new EntityBullet(worldObj, entityPlayer, itemStack));
	}
	
	public static void doEntityAttack(Entity ent, DamageSource ds, int damage, double dx, double dy, double dz){
		ent.attackEntityFrom(ds, damage);
		//ent.addVelocity(dx, dy, dz);
	}
	
	public static void doEntityAttack(Entity ent, DamageSource ds, int damage){
		doEntityAttack(ent, ds, damage, 0, 0, 0);
	}
	
	public static void Explode(World world,Entity entity, float strengh, double radius, double posX, double posY, double posZ, int additionalDamage){
		Explode(world, entity, strengh, radius, posX, posY, posZ, additionalDamage, 1.0, 1.0F);
	}
	
	public static void Explode(World world,Entity entity, float strengh, double radius, double posX, double posY, double posZ, int additionalDamage, double velocityRadius, float soundRadius){
		
		CBCExplosion explosion = new CBCExplosion(world, entity, posX, posY, posZ, strengh).setSoundFactor(soundRadius).setVelocityFactor(velocityRadius);
		explosion.isSmoking = true;
		explosion.isFlaming = false;
		
		explosion.doExplosionA();
		explosion.doExplosionB(true);
        
		if(additionalDamage <= 0)
			return;
		
		AxisAlignedBB par2 = AxisAlignedBB.getBoundingBox(posX-4, posY-4, posZ-4, posX+4, posY+4, posZ+4);
		List entitylist = world.getEntitiesWithinAABBExcludingEntity(null, par2);
		if(entitylist.size() > 0){
			for(int i=0;i<entitylist.size();i++){
				Entity ent = (Entity)entitylist.get(i);
				if(ent instanceof EntityLiving){
					double distance = Math.sqrt(Math.pow(ent.posX-posX,2) + Math.pow(ent.posY-posY,2) + Math.pow(ent.posZ-posZ,2));
					int damage = (int) ((1 - distance/6.928) * additionalDamage);
					ent.attackEntityFrom(DamageSource.setExplosionSource(explosion.asDefaultExplosion()), damage);
				}
			}
		}
		NetExplosion.sendNetPacket(world, (float)posX, (float)posY, (float)posZ, strengh);
	}
	
	@SideOnly(Side.CLIENT)
	public static void clientExplode(World world, float strengh, double posX, double posY, double posZ){
		CBCExplosion explosion = new CBCExplosion(world, null, posX, posY, posZ, strengh);
		explosion.isSmoking = true;
		explosion.isFlaming = false;
		
		explosion.doExplosionA();
		explosion.doExplosionB(true);
	}
}
