package cbproject.deathmatch.entities;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class BulletSG extends EntityThrowable {

	private int[] damageArray;
	private Entity[] hitList;
	private int[] lastTicks;
	private final int bulletCount;
	private final ItemStack item;
	private boolean entityReady= false;
	
	public BulletSG(World par1World) {
		super(par1World);
		bulletCount = 0;
		item = null;
	}
	
	public BulletSG(World world, EntityPlayer shooter, ItemStack is, int mo){
		super(world, shooter);
		bulletCount = mo == 0 ? 8 : 16;
		item = is;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		if(!entityReady)
			postInit();
		if(ticksExisted > 20){
			for(int i = 0; i < bulletCount; i++){
				if(hitList[i] != null)
					applyDamage(i);
			}
			this.setDead();
			return;
		}
		for(int i = 0; i < bulletCount; i++){
			if(hitList[i] != null && ticksExisted - lastTicks[i] > 5)
				applyDamage(i);
		}
	}
	
	private void applyDamage(int i){
			hitList[i].attackEntityFrom(DamageSource.causeMobDamage(getThrower()), damageArray[i]);
			System.out.println("Damage applied on " + hitList[i] + ", damage : " + damageArray[i]);
			hitList[i] = null;
			damageArray[i] = 0;
			lastTicks[i] = 0;
	}

	public void postInit() {
		damageArray = new int[bulletCount];
		hitList = new Entity[bulletCount];
		lastTicks = new int[bulletCount];
		entityReady = true;
	}
	
	public void onBulletHit(Entity entity, int damage){
		int nullIndex = -1;
		for(int i = 0; i < bulletCount; i++){
			if(hitList[i] == null){
				if(nullIndex == -1)
					nullIndex = i;
				continue;
			}
			if(hitList[i] == entity){
				lastTicks[i] = this.ticksExisted;
				damageArray[i] += damage;
				return;
			}
		}
		if(nullIndex >= 0){
			lastTicks[nullIndex] = ticksExisted;
			damageArray[nullIndex] = damage;
			hitList[nullIndex] = entity;
		}
	}

	@Override
	public float func_70182_d(){
		return 0.0F;
	}
	
	@Override
	public float getGravityVelocity(){
		return 0.0F;
	}

	@Override
	protected void onImpact(MovingObjectPosition movingobjectposition) {
	}
	
	@Override
	public boolean canBeCollidedWith(){
		return false;
	}
	

}
