package cbproject.elements.entities.ammos;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public abstract class EntityAmmo extends EntityThrowable {

	public EntityAmmo(World par1World) {
		super(par1World);
	}

	public EntityAmmo(World par1World, EntityLiving par2EntityLiving) {
		super(par1World, par2EntityLiving);
		// TODO Auto-generated constructor stub
	}

	public EntityAmmo(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		//Stops here
		this.motionX = 0.0;
		this.motionY = 0.0;
		this.motionZ = 0.0;
		setThrowableHeading(motionX,motionY,motionZ,0.0F,0.5F);
		
	}

}
