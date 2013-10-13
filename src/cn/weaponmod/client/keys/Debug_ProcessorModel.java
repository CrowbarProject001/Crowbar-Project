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
public class Debug_ProcessorModel<T extends RenderModelItem> implements
		Debug_MovingProcessor<T> {

	// 0: stdOffset XYZ
	// 1: stdRotation XYZ
	// 2: scale
	// 3: equipOffset XYZ
	// 4: invOffset XY && invScale
	@Override
	public boolean isProcessAvailable(IItemRenderer render, int mode) {
		return render instanceof RenderModelItem && !(render instanceof RenderModelBulletWeapon);
	}

	@Override
	public String doProcess(T render, EnumKey key, int mode,
			int ticks) {
		float amtToAdd = ticks * ticks * 0.002F;
		String s = "";
		switch (mode) {
		case 0:
			s = onSetOffset(render, key, amtToAdd);
			break;
		case 1:
			s = onSetRotation(render, key, amtToAdd);
			break;
		case 2:
			s = onSetScale(render, key, amtToAdd);
			break;
		case 3:
			s = onSetEquipOffset(render, key, amtToAdd);
			break;
		case 4:
			s = onSetInvScale(render, key, amtToAdd);
			break;
		}
		if(s != "")
			System.out.println(s);
		return s;
	}

	private String onSetOffset(RenderModelItem render, EnumKey key, float factor) {
		switch (key) {
		case UP:
			render.yOffset += factor;
			break;
		case DOWN:
			render.yOffset -= factor;
			break;
		case LEFT:
			render.xOffset -= factor;
			break;
		case RIGHT:
			render.xOffset += factor;
			break;
		case FORWARD:
			render.zOffset += factor;
			break;
		case BACK:
			render.zOffset -= factor;
			break;
		}
		return "[OFFSET : (" + render.xOffset + ", " + render.yOffset + ", "
				+ render.zOffset + ") ]";
	}

	private String onSetEquipOffset(RenderModelItem render, EnumKey key,
			float factor) {
		switch (key) {
		case UP:
			render.equipOffsetY += factor;
			break;
		case DOWN:
			render.equipOffsetY -= factor;
			break;
		case LEFT:
			render.equipOffsetX -= factor;
			break;
		case RIGHT:
			render.equipOffsetX += factor;
			break;
		case FORWARD:
			render.equipOffsetZ += factor;
			break;
		case BACK:
			render.equipOffsetZ -= factor;
			break;
		}
		return "[EQUIP OFFSET : (" + render.equipOffsetX + ", "
				+ render.equipOffsetY + ", " + render.equipOffsetZ + ") ]";
	}

	private String onSetRotation(RenderModelItem render, EnumKey key,
			float factor) {
		switch (key) {
		case UP:
			render.rotationY += factor;
			break;
		case DOWN:
			render.rotationY -= factor;
			break;
		case LEFT:
			render.rotationX -= factor;
			break;
		case RIGHT:
			render.rotationX += factor;
			break;
		case FORWARD:
			render.rotationZ += factor;
			break;
		case BACK:
			render.rotationZ -= factor;
			break;
		}
		return "[ROTATION: " + "(" + render.rotationX + ", " + render.rotationY
				+ ", " + render.rotationZ + ") ]";
	}

	private String onSetScale(RenderModelItem render, EnumKey key, float factor) {
		switch (key) {
		case UP:
		case FORWARD:
			render.scale += factor;
			break;
		case DOWN:
		case BACK:
			render.scale -= factor;
			break;
		default:
			break;
		}
		return "[SCALE: " + render.scale + "]";
	}

	private String onSetInvScale(RenderModelItem render, EnumKey key,
			float factor) {
		switch (key) {
		case UP:
			render.invOffsetY += factor;
			break;
		case DOWN:
			render.invOffsetY -= factor;
			break;
		case LEFT:
			render.invOffsetX -= factor;
			break;
		case RIGHT:
			render.invOffsetX += factor;
			break;
		case FORWARD:
			render.invScale += factor;
			break;
		case BACK:
			render.invScale -= factor;
			break;
		}
		return "[INV OFFSET : (" + render.invOffsetX + ", " + render.invOffsetY
				+ ") + , SCALE : " + render.invScale + "]";
	}

	@Override
	public String getDescription(int mode) {
		switch(mode) {
		case 0:
			return "Std Offset XYZ";
		case 1:
			return "Std Rotation XYZ";
		case 2:
			return "Std Scale";
		case 3:
			return "Equipped Offset XYZ";
		case 4:
			return "Inventory Offset XY & invScale";
		default:
			return null;
		}
	}
}
