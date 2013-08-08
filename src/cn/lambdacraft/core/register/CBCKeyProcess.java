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
package cn.lambdacraft.core.register;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.WrongUsageException;
import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * 统一处理按键的实用类。 请使用addKey(...)注册按键绑定。详见函数本身说明
 * 
 * @author WeAthFolD
 */
public class CBCKeyProcess implements ITickHandler {

	private static List<KeyBinding> bindings = new ArrayList();
	private static List<IKeyProcess> processes = new ArrayList();
	private static List<Boolean> repeats = new ArrayList();
	
	private KeyBinding[] bindingArray;
	private IKeyProcess[] processArray;
	private Boolean[] repeatArray;
	private boolean[] keyDown;
	
	public static CBCKeyProcess instance;
	public static final int MOUSE_LEFT = -100, MOUSE_MIDDLE = -98, MOUSE_RIGHT = -99;

	public CBCKeyProcess() {
		if (instance == null)
			instance = this;
		bindingArray = bindings.toArray(new KeyBinding[0]);
		processArray = processes.toArray(new IKeyProcess[0]);
		repeatArray = repeats.toArray(new Boolean[0]);
		bindings = null;
		processes = null;
		repeats = null;
		keyDown = new boolean[bindingArray.length];
		System.gc();
	}

	@Override
	public String getLabel() {
		return "LambdaCraft Keys";
	}
	
    /**
     * Not to be overridden - KeyBindings are tickhandlers under the covers
     */
    @Override
    public final void tickStart(EnumSet<TickType> type, Object... tickData)
    {
        keyTick(type, false);
    }

    /**
     * Not to be overridden - KeyBindings are tickhandlers under the covers
     */
    @Override
    public final void tickEnd(EnumSet<TickType> type, Object... tickData)
    {
        keyTick(type, true);
    }

    private void keyTick(EnumSet<TickType> type, boolean tickEnd)
    {
        for (int i = 0; i < bindingArray.length; i++)
        {
            KeyBinding keyBinding = bindingArray[i];
            int keyCode = keyBinding.keyCode;
            boolean state = (keyCode < 0 ? Mouse.isButtonDown(keyCode + 100) : Keyboard.isKeyDown(keyCode));
            if (state != keyDown[i] || (state && repeatArray[i]))
            {
            	IKeyProcess proc = processArray[i];
            	
                if (state)
                {
                    proc.onKeyDown(tickEnd);
                }
                else
                {
                   proc.onKeyUp(tickEnd);
                }
                if (tickEnd)
                {
                    keyDown[i] = state;
                }
            }

        }
    }

	@Override
	public EnumSet<TickType> ticks() {
		return EnumSet.of(TickType.CLIENT);
	}

	/**
	 * 在按键处理中添加一个键位。请务必在preInit（Init之前）调用这个函数。
	 * 
	 * @param key
	 *            按键
	 * @param isRep
	 *            是否重复
	 * @param process
	 *            对应的处理类
	 */
	public static void addKey(KeyBinding key, boolean isRep, IKeyProcess process) {
		if (instance != null)
			throw new WrongUsageException("Trying to add a key after the process is instanted.");

		bindings.add(key);
		processes.add(process);
		repeats.add(isRep);
	}

}
