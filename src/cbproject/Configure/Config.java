package cbproject.Configure;

import java.io.File;
import java.io.IOException;
import net.minecraftforge.common.Configuration;

public class Config {
	private static Config instance;
	private Configuration config;
	private Config(File configFile)
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
	public static void InitliazeConfig(File ConfigFile)
	{
	    if(Config.instance != null)
	    {
	        return;
	    }
	    Config.instance = new Config(ConfigFile);
	}
	public static String GetGeneralProperties(String PropertyName, String DefaultValue) throws Exception
	{
	    if(Config.instance == null)
	    {
	        throw new Exception("没有初始化配置文件！");
	    }
	    return Config.instance.config.get("general", PropertyName, DefaultValue).value;
	}
	public static int GetItemID(String ItemName, int DefaultValue) throws Exception
	{
	    if(Config.instance == null)
	    {
	        throw new Exception("没有初始化配置文件！");
	    }
	    return Config.instance.config.getItem("item", "ID." + ItemName, DefaultValue).getInt();
	}
	public static int GetBlockID(String BlockName, int DefaultID) throws Exception
	{
	    if(Config.instance == null)
	    {
	        throw new Exception("没有初始化配置文件！");
	    }
	    return Config.instance.config.getBlock("ID." + BlockName, DefaultID).getInt();
	}
	public static void SaveConfig()
	{
	    Config.instance.config.save();
	}
}
