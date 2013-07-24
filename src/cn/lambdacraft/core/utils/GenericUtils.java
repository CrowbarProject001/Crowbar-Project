/** 
 * Copyright (c) LambdaCraft Modding Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.half-life.cn/
 * 
 * LambdaCraft is open-source. It is distributed under the terms of the
 * LambdaCraft Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》。你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cn.lambdacraft.core.utils;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

import net.minecraft.command.IEntitySelector;
import net.minecraft.entity.Entity;

/**
 * 通用效用工具？
 * @author WeAthFolD
 */
public class GenericUtils {

	public static IEntitySelector selectorLiving = new EntitySelectorLiving(),
			selectorPlayer = new EntitySelectorPlayer();

	/**
	 * 获取Entity到一个点距离的平方。
	 * @param ent
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static double distanceSqTo(Entity ent, double x, double y, double z) {
		if (ent == null)
			throw new NullPointerException();
		double dx = (ent.posX - x), dy = (ent.posY - y), dz = (ent.posZ - z);
		return dx * dx + dy * dy + dz * dz;
	}
	
	public static float wrapYawAngle(float f) {
		if(f > 180.0F)
			f -= 360.0F;
		else if(f < -180.0F)
			f = 360.0F - f;
		return f;
	}
	
	/**
	 * 获取Entity的体积。
	 * @param e
	 * @return
	 */
	public static float getEntitySize(Entity e) {
		return e.width * e.width * e.height;
	}

	/**
	 * 获取Entity到一个点的距离。
	 * @param ent
	 * @param x
	 * @param y
	 * @param z
	 * @return
	 */
	public static double distanceTo(Entity ent, double x, double y, double z) {
		return Math.sqrt(distanceSqTo(ent, x, y, z));
	}
	
	/**
	 * 获取一个随机音效，目录名遵循"soundname[a,b,c,d,...]"
	 * @param sndPath
	 * @param countSounds
	 * @return
	 */
	public static String getRandomSound(String sndPath, int countSounds) {
		int a = (int) (Math.random() * countSounds);
		return sndPath.concat(String.valueOf((char)('a' + a)));
	}

	public static <T, U> T getKeyByValueFromMap(Map<T, U>map, Object value) {
        T o = null;  
        ArrayList all = new ArrayList(); // 建一个数组用来存放符合条件的KEY值  
        Set set = map.entrySet();  
        Iterator it = set.iterator();  
        while (it.hasNext()) {  
            Map.Entry<T, U> entry = (Map.Entry) it.next();  
            if (entry.getValue().equals(value)) {  
                o = entry.getKey();  
                return o;
            }  
        }
		return null; 
	}
	
}
