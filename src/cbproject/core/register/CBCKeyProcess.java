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

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.WrongUsageException;
import cpw.mods.fml.client.registry.KeyBindingRegistry.KeyHandler;
import cpw.mods.fml.common.TickType;


/**
 * 统一处理按键的实用类。
 * 请使用addKey(...)注册按键绑定。详见函数本身说明
 * @author WeAthFolD
 */
public class CBCKeyProcess extends KeyHandler{
	
	private static HashMap<KeyBinding, IKeyProcess>keyProcesses = new HashMap();
	private static List<KeyBinding>keyCodes = new ArrayList();
	private static List<Boolean>isRepeating = new ArrayList();
	
	public static CBCKeyProcess instance;
	
	public CBCKeyProcess(){
		super(toKeyBindingArray(), toBooleanArray());
		if(instance == null)
			instance = this;
	}
	
	@Override
	public String  getLabel() {
		return "LambdaCraft Keys";
	}

	@Override
	public void keyDown(EnumSet<TickType> types, KeyBinding kb,
			boolean tickEnd, boolean isRepeat) {
		if(tickEnd)
			return;
		if(keyProcesses.containsKey(kb.keyCode))
			keyProcesses.get(kb.keyCode).onKeyDown();
	}

	@Override
	public void keyUp(EnumSet<TickType> types, KeyBinding kb, boolean tickEnd) {
		if(tickEnd)
			return;
		if(keyProcesses.containsKey(kb.keyCode))
			keyProcesses.get(kb.keyCode).onKeyUp();
	}

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}
	
	/**
	 * 在按键处理中添加一个键位。请务必在preInit（Init之前）调用这个函数。
	 * @param key 按键
	 * @param isRep 是否重复
	 * @param process 对应的处理类
	 */
	public static void addKey(KeyBinding key, boolean isRep, IKeyProcess process){
		if(instance != null)
			throw new WrongUsageException("Trying to add a key after the process is instanted.");
		
		keyCodes.add(key);
		isRepeating.add(isRep);
		keyProcesses.put(key, process);
	}
	
	private static boolean[] toBooleanArray(){
		boolean[] b = new boolean[isRepeating.size()];
		for(int i = 0; i < b.length; i++){
			b[i] = isRepeating.get(i);
		}
		return b;
	}
	
	private static KeyBinding[] toKeyBindingArray(){
		KeyBinding kb[] = new KeyBinding[keyCodes.size()];
		for(int i = 0; i < kb.length; i++){
			kb[i] = keyCodes.get(i);
		}
		return kb;
	}
	
}
