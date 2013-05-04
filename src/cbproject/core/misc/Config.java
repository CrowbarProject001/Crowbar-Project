package cbproject.core.misc;

import java.io.File;
import java.io.IOException;
import net.minecraftforge.common.Configuration;

/**
 * 
 * @author Mkpoli, WeAthFolD
 * @description CBCMod Configuration load class.
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
