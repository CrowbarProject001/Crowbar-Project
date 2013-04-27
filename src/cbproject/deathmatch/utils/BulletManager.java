package cbproject.deathmatch.utils;

import java.util.List;

import cbproject.core.utils.BlockPos;
import cbproject.deathmatch.entities.EntityBullet;
import cbproject.deathmatch.items.wpns.WeaponGeneral;



import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumMovingObjectType;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.util.Vec3;
import net.minecraft.world.World;

public class BulletManager {


	private static final double BB_SIZE = 0.5D;
	private static final int ENTITY_TRACE_RANGE = 128;

	public BulletManager() {
		// TODO Auto-generated constructor stub
	}
	
	public static void Shoot(ItemStack itemStack, EntityLiving entityPlayer, World worldObj, String effect){
		worldObj.spawnEntityInWorld(new EntityBullet(worldObj, entityPlayer, itemStack, effect));
	}
	
}
