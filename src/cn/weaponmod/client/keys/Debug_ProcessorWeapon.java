/**
 * 
 */
package cn.weaponmod.client.keys;

import net.minecraftforge.client.IItemRenderer;
import cn.weaponmod.api.client.render.RenderModelBulletWeapon;
import cn.weaponmod.api.client.render.RenderModelItem;
import cn.weaponmod.client.keys.Debug_KeyMoving.EnumKey;

/**
 * @author WeAthFolD
 *
 */
public class Debug_ProcessorWeapon extends Debug_ProcessorModel<RenderModelBulletWeapon> {

	@Override
	public boolean isProcessAvailable(IItemRenderer render, int mode) {
		return render instanceof RenderModelBulletWeapon;
	}
	
	@Override
	public String doProcess(RenderModelBulletWeapon render, EnumKey key, int mode,
			int ticks) {
		String s = super.doProcess(render, key, mode, ticks);
		float amtToAdd = ticks * ticks * 0.002F;
		if(mode == 5) {
			s = onSetMuzzleOffset(render, key, amtToAdd);
			System.out.println(s);
		}
		return s;
	}
	
	private String onSetMuzzleOffset(RenderModelBulletWeapon render, EnumKey key, float factor) {
		switch (key) {
		case UP:
			render.ty += factor;
			break;
		case DOWN:
			render.ty -= factor;
			break;
		case LEFT:
			render.tx -= factor;
			break;
		case RIGHT:
			render.tx += factor;
			break;
		case FORWARD:
			render.tz += factor;
			break;
		case BACK:
			render.tz -= factor;
			break;
		}
		return "[MUZZLE OFFSET : (" + render.tx + ", " + render.ty + ", " + render.tz + ") ]";
	}
	
	@Override
	public String getDescription(int mode) {
		if(mode <= 4)
			return super.getDescription(mode);
		else if(mode == 5)
			return "Muzzflash offset XYZ";
		else return null;
	}

}
