package cn.weaponmod.util;

public class GenericUtils {

	public static float wrapYawAngle(float f) {
		if(f > 180.0F)
			f -= 360.0F;
		else if(f < -180.0F)
			f = 360.0F - f;
		return f;
	}

}
