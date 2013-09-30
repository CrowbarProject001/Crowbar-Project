/**
 * 
 */
package cn.lambdacraft.xen.client;

import appeng.api.config.FuzzyMode;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.particle.EntityPortalFX;
import net.minecraft.world.World;

/**
 * @author Administrator
 *
 */
@SideOnly(Side.CLIENT)
public class EntityXenPortalFX extends EntityPortalFX {

	/**
	 * @param par1World
	 * @param par2
	 * @param par4
	 * @param par6
	 * @param par8
	 * @param par10
	 * @param par12
	 */
	public EntityXenPortalFX(World par1World, double par2, double par4,
			double par6, double par8, double par10, double par12) {
		super(par1World, par2, par4, par6, par8, par10, par12);
		float f = this.rand.nextFloat() * 0.6F + 0.4F;
		this.particleRed = this.particleGreen = this.particleBlue = 1.0F * f;
		this.particleBlue *= 0.3F;
        this.particleGreen *= 0.9F;
        this.particleRed *= 0.3F;
	}

}
