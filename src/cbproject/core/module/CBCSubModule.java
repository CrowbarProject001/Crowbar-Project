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
import java.lang.annotation.*;

/**
 * 用来指定一个类为LambdaCraft的子模块。
 * 在模块类中用@Instance来指定类实例（static）
 * 在模块中使用@ModuleInit来制定对应的加载函数。
 * 如果想在别的Mod中使用这个方法，请使用required-before
 * 并在preInit中注册。
 * @see ModuleInit
 * @see cpw.mods.fml.common.Mod.Instance
 * @author WeAthFolD
 *
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface CBCSubModule {
	public String value();
}
