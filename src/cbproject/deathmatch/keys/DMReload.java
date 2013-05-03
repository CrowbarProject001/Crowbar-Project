package cbproject.deathmatch.keys;

import cbproject.core.misc.CBCKey;

public class DMReload implements CBCKey {

	@Override
	public void onKeyDown() {
		System.out.println("Reload Key Down");
	}

	@Override
	public void onKeyUp() {
	}

}
