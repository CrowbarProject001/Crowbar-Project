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
 * LambdaCraft是完全开源的。它的发布遵从《LambdaCraft开源协议》你允许阅读，修改以及调试运行
 * 源代码， 然而你不允许将源代码以另外任何的方式发布，除非你得到了版权所有者的许可。
 */
package cbproject.core.misc;

import java.io.File;
import java.io.IOException;
import net.minecraftforge.common.Configuration;

/**
 * 公用的设置读取类。
 * @author Mkpoli, WeAthFolD
 */
public class Config {
	
	private static Configuration config;
	
	public Config(File configFile)
	{
	    if(!configFile.exists())
	    {
	        try
	        {
	            configFile.createNewFile();
	        }
	        catch(IOException e)
	        {
	            System.out.println(e);
	            return;
	        }
	    }
	    config = new Configuration(configFile);
	    config.load();
	}
	
	public  void InitliazeConfig(File ConfigFile)
	{
	    if(this != null)
	    {
	        return;
	    }
	    config = new Configuration(ConfigFile);
	}
	
	public String GetGeneralProperties(String PropertyName, String DefaultValue) throws Exception
	{
	    if(this == null)
	    {
	        throw new NullPointerException();
	    }
	    return config.get("general", PropertyName, DefaultValue).getString();
	}
	
	public Boolean getBoolean(String name, Boolean defaultValue)throws Exception{
		if(this == null){
			throw new NullPointerException();
		}
		return config.get("general", name, defaultValue).getBoolean(defaultValue);
	}
	
	public int getInteger(String name, Integer defaultValue)throws Exception{
		if(this == null){
			throw new NullPointerException();
		}
		return config.get("general", name, defaultValue).getInt();
	}
	
	public int GetItemID(String ItemName, int DefaultValue) throws Exception
	{
	    if(this == null)
	    {
	        throw new NullPointerException();
	    }
	    return config.getItem("item", "ID." + ItemName, DefaultValue).getInt();
	}
	
	public int GetBlockID(String BlockName, int DefaultID) throws Exception
	{
	    if( this  == null)
	    {
	        throw new NullPointerException();
	    }
	    return config.getBlock("ID." + BlockName, DefaultID).getInt();
	}
	
	public int GetKeyCode(String keyName, int defaultKey) throws Exception{
		if(this == null)
			 throw new NullPointerException();
		return config.get("key", "KB." + keyName, defaultKey).getInt();
	}
	
	public void SaveConfig()
	{
	    config.save();
	}
	
}
