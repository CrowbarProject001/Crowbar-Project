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

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import cpw.mods.fml.common.Mod.Instance;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPostInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.event.FMLServerStartingEvent;

import cbproject.core.module.CBCSubModule;
import cbproject.core.module.ModuleInit;
import cbproject.core.module.ModuleInit.EnumInitType;

/**
 * 子模块注册。
 * @author WeAthFolD
 */
public class CBCModuleRegisty {

	private static ArrayList<Class> modules = new ArrayList();
	
	/**
	 * 注册一个子模块。请在LambdaCraft的preInit被调用前使用它。
	 * @param path 子模块的绝对路径。
	 * @return 注册成功与否
	 */
	public static boolean registerModule(String path){
		Class module;
		try {
			module = Class.forName(path);
			System.out.println("Attempting to load sub module " + module);
		} catch (ClassNotFoundException e) {
			System.err.println("Didn't find module in :" + path);
			return false;
		}
		if(module.getAnnotation(CBCSubModule.class) == null){
			System.err.println(module + " is not a available module.");
			return false;
		}
		modules.add(module);
		return true;
	}
	
	public void preInit(FMLPreInitializationEvent event){
		for(Class m : modules){
			for(Method me : getRequiredInit(m, EnumInitType.PREINIT)){
				try {
					me.invoke(getInstance(m), event);
				} catch (Exception e){
					System.err.println("Failed in calling preInit in :" + m + ", reason " + e);
					e.printStackTrace();
				}
			}
		}
	}
	
	public void init(FMLInitializationEvent event){
		for(Class m : modules){
			for(Method me : getRequiredInit(m, EnumInitType.INIT)){
				try {
					me.invoke(getInstance(m), event);
				} catch (Exception e){
					System.err.println("Failed in calling Init in :" + m + ", reason " + e);
					e.printStackTrace();
				}
			}
		}
	}
	
	public void postInit(FMLPostInitializationEvent event){
		for(Class m : modules){
			for(Method me : getRequiredInit(m, EnumInitType.POSTINIT)){
				try {
					me.invoke(getInstance(m), event);
				} catch (Exception e){
					System.err.println("Failed in calling postInit in :" + m);
				}
			}
		}
	}
	
	public void serverStarting(FMLServerStartingEvent event){
		for(Class m : modules){
			for(Method me : getRequiredInit(m, EnumInitType.SVINIT)){
				try {
					me.invoke(getInstance(m), event);
				} catch (Exception e){
					System.err.println("Failed in calling serverStarting in :" + m);
					e.printStackTrace();
				}
			}
		}
	}
	
	public void clientInit(){
		for(Class m : modules){
			for(Method me : getRequiredInit(m, EnumInitType.CLINIT)){
				try {
					me.invoke(getInstance(m));
				} catch (Exception e){
					System.err.println("Failed in calling clientInit in :" + m);
				}
			}
		}
	}
	
	private Set<Method> getRequiredInit(Class module, EnumInitType type){
		Set<Method> list = new HashSet<Method>();
		for(Method m : module.getMethods()){
			ModuleInit ann = m.getAnnotation(ModuleInit.class);
			if(ann == null)
				continue;
			if(ann.value().equals(type)){
				list.add(m);
			}
		}
		System.out.println(list);
		return list;
	}
	
	
	
	private Object getInstance(Class module){
		for(Field f : module.getDeclaredFields()){
			Instance i = f.getAnnotation(Instance.class);
			if(i == null)
				continue;
			try {
				Object value = f.get(null);
				if(value != null){
					System.out.println(module + " : " + f.get(null));
					return value;
				}
				f.set(null, module.newInstance());
				System.out.println(module + " : " + f.get(null));
				return f.get(null);
			} catch (Exception e1) {
				System.err.println("Wrongly declared instance " + f);
				continue;
			}
		}
		System.err.println("Couldn't find the right instance of the module.");
		return null;
	}

}
