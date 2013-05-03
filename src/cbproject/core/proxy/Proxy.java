package cbproject.core.proxy;

import cpw.mods.fml.common.registry.LanguageRegistry;
import cbproject.core.CBCMod;
import cbproject.core.props.GeneralProps;
import cbproject.core.register.CBCPacketHandler;
import cbproject.crafting.network.NetWeaponCrafter;
import net.minecraft.util.StringTranslate;

public class Proxy {
	
	public void init() {
		String currentLang = StringTranslate.getInstance().getCurrentLanguage();
		if(currentLang != "en_US")
			LanguageRegistry.instance().loadLocalization("/cbproject/lang/" + currentLang + ".properties", currentLang, false);
		else LanguageRegistry.instance().loadLocalization("/cbproject/lang/en_US.properties", "en_US", false);
		CBCPacketHandler.addChannel("CBCCrafter", new NetWeaponCrafter());
		GeneralProps.loadProps(CBCMod.config);
	}
	
}
