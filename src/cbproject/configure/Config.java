package cbproject.configure;

import java.io.File;
import java.io.IOException;
import net.minecraftforge.common.Configuration;

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
	        throw new Exception("û�г�ʼ�������ļ���");
	    }
	    return config.get("general", PropertyName, DefaultValue).value;
	}
	public int GetItemID(String ItemName, int DefaultValue) throws Exception
	{
	    if(this == null)
	    {
	        throw new Exception("û�г�ʼ�������ļ���");
	    }
	    return config.getItem("item", "ID." + ItemName, DefaultValue).getInt();
	}
	public int GetBlockID(String BlockName, int DefaultID) throws Exception
	{
	    if( this  == null)
	    {
	        throw new Exception("û�г�ʼ�������ļ���");
	    }
	    return config.getBlock("ID." + BlockName, DefaultID).getInt();
	}
	public void SaveConfig()
	{
	    config.save();
	}
}
