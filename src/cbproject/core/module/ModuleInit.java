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
package cbproject.core.module;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 使用这个注解为子模块添加加载函数。
 * @see CBCSubModule
 * @author WeAthFolD
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface ModuleInit {
	public enum EnumInitType{
		
		/**
		 * 预加载函数。函数的参数应该为 (FMLPreInitializationEvent event)
		 */
		PREINIT,
		/**
		 * 正式加载函数。函数的参数应该为 (FMLInitializationEvent event)
		 */
		INIT,
		/**
		 * 后期加载函数。函数的参数应该为 (FMLPostInitializationEvent event)
		 */
		POSTINIT,
		/**
		 * 服务端加载函数。函数的参数应该为 (FMLServerStartingEvent event)
		 */
		SVINIT,
		/**
		 * 客户端加载函数。函数的参数应该为 (void)
		 */
		CLINIT,
		/**
		 * 无行为。
		 */
		NONE
	}
	
	public EnumInitType value() default EnumInitType.NONE;
}
