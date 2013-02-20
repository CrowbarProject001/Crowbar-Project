package cbproject.elements.entities.weapons;

import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.item.ItemStack;
import net.minecraft.util.MathHelper;
import net.minecraft.util.MovingObjectPosition;
import net.minecraft.world.World;

public class EntityHGrenade extends EntityThrowable {

	public EntityHGrenade(World par1World) {
		super(par1World);
	}

    public EntityHGrenade(World par1World, EntityLiving par2EntityLiving)
    {
        super(par1World);
        float var3 = 0.2F; //�ٶ�Ϊѩ���һ��
        this.motionX = (double)(-MathHelper.sin(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionZ = (double)(MathHelper.cos(this.rotationYaw / 180.0F * (float)Math.PI) * MathHelper.cos(this.rotationPitch / 180.0F * (float)Math.PI) * var3);
        this.motionY = (double)(-MathHelper.sin((this.rotationPitch + this.func_70183_g()) / 180.0F * (float)Math.PI) * var3);
        this.setThrowableHeading(this.motionX, this.motionY, this.motionZ, this.func_70182_d(), 1.0F);
    }

    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 100;	//��Tickô��(5s)
    }

	public EntityHGrenade(World par1World, double par2, double par4, double par6) {
		super(par1World, par2, par4, par6);
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void onImpact(MovingObjectPosition var1) {
		// TODO Auto-generated method stub
		return;
	}
	


}
