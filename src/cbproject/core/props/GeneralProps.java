package cbproject.core.props;

import cpw.mods.fml.common.registry.EntityRegistry;
import cbproject.core.misc.Config;

public class GeneralProps {
	public static Boolean ignoreBlockDestroy = false;
	
	public static void loadProps(Config config){
		try {
			ignoreBlockDestroy = config.getBoolean("ignoreBlockDestroy", false);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
