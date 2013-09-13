/** 
 * Copyright (c) Lambda Innovation Team, 2013
 * 版权许可：LambdaCraft 制作小组， 2013.
 * http://lambdacraft.cn/
 * 
 * The mod is open-source. It is distributed under the terms of the
 * Lambda Innovation Open Source License. It grants rights to read, modify, compile
 * or run the code. It does *NOT* grant the right to redistribute this software
 * or its modifications in any form, binary or source, except if expressively
 * granted by the copyright holder.
 *
 * 本Mod是完全开源的，你允许参考、使用、引用其中的任何代码段，但不允许将其用于商业用途，在引用的时候，必须注明原作者。
 */
package cn.weaponmod.events;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;

import net.minecraft.client.settings.KeyBinding;
import net.minecraft.command.WrongUsageException;

import org.lwjgl.input.Keyboard;
import org.lwjgl.input.Mouse;

import cpw.mods.fml.common.ITickHandler;
import cpw.mods.fml.common.TickType;

/**
 * @author WeAthFolD
 *
 */
public class WMKeys implements ITickHandler {

	private static List<KeyBinding> bindings = new ArrayList();
	private static List<IKeyProcess> processes = new ArrayList();
	private static List<Boolean> repeats = new ArrayList();
	
	private KeyBinding[] bindingArray;
	private IKeyProcess[] processArray;
	private Boolean[] repeatArray;
	private boolean[] keyDown;
	
	public static WMKeys instance;
	public static final int MOUSE_LEFT = -100, MOUSE_MIDDLE = -98, MOUSE_RIGHT = -99;

	public WMKeys() {
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
		return "MyWeaponary Keys";
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
