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
package cbproject.core.register;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.HashSet;

import cbproject.core.CBCMod;
import cbproject.core.misc.Config;

import net.minecraft.block.Block;
import net.minecraft.item.Item;

/**
 * 
 * 被注册类中标记的域必须为静态域。
 * @see cbproject.core.props.GeneralProps
 * @author WeAthFolD
 *
 */
public class GeneralRegistry {
	
	private static final int BLOCK_BEGIN = 400;
	private static Config config;
	
	/**
	 * 获得一个空的物品ID。（调用Config配置）
	 * @param name 物品名字
	 * @param cat 物品分类
	 * @see cbproject.core.props.GeneralProps
	 * @return 获取的ID
	 */
	public static int getItemId(String name, int cat) {
		config = CBCMod.config;
		try {
			return config.GetItemID(name, getEmptyItemId(cat)) - 256;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	
	/**
	 * 获得一个空的方块ID。（调用Config配置）
	 * @param name 方块名字
	 * @param cat 方块分类
	 * @see cbproject.core.props.GeneralProps
	 * @return 获取的ID
	 */
	public static int getBlockId(String name, int cat) {
		config = CBCMod.config;
		try {
			return config.GetBlockID(name, getEmptyBlockId(cat));
		} catch (Exception e) {
			e.printStackTrace();
		}
		return -1;
	}
	
	private static int getEmptyItemId(int cat) {
		int begin = 3840;
		begin += cat * 50;
		int theId = 0;
		for(int i = 0; i < 50; i++) {
			theId = begin + i;
			if(Item.itemsList[theId] == null)
				return theId;
		}
		return -1;
	}
	
	private static int getEmptyBlockId(int cat) {
		int begin = 400;
		begin += cat * 50;
		int theId = 0;
		for(int i = 0; i < 50; i++) {
			theId = begin + i;
			if(Block.blocksList[theId] == null)
				return theId;
		}
		return -1;
	}
	
	
 
}
