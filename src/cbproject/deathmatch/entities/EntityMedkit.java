package cbproject.deathmatch.entities;

import java.util.List;

import cbproject.core.utils.EntitySelectorPlayer;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityMedkit extends EntityProjectile {


	public EntityMedkit(World world) {
		super(world);
		this.setSize(0.85F, 0.2F);
	}

	@Override
	protected float getHeadingVelocity() {
		return 0;
	}

	@Override
	protected float getMotionYOffset() {
		return 0;
	}

	@Override
	protected float getGravityVelocity() {
		return 0.4F;
	}
	
	@Override
	public void onUpdate(){
		super.onUpdate();
		AxisAlignedBB box = AxisAlignedBB.getBoundingBox(posX-0.15, posY-0.3, posZ - 0.15, posX + 0.15, posY + 0.3, posZ + 0.15);
		List<EntityPlayer> list = worldObj.getEntitiesWithinAABBExcludingEntity(this, box, new EntitySelectorPlayer());
		if(list == null)
			return;
		EntityPlayer player = list.get(0);
		player.heal(8);
		this.playSound("cbc.entity.medkit", 0.5F, 1.0F);
		this.setDead();
	}

	@Override
	protected void onCollide(MovingObjectPosition result) {
	}

	@Override
	protected void entityInit() {

	}

}
