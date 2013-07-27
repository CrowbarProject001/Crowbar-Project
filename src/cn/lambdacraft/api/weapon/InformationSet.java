package cn.lambdacraft.api.weapon;

import net.minecraft.world.World;

/**
 * Client和Server信息的成对存储。 本来不需要的，但是为了解决Intergrate Server，暂时没有找到合适的方法 所以就先凑合用了
 * 总有一天要重写23333
 * @author WeAthFolD
 * 
 */
public final class InformationSet<T extends InformationWeapon> {

	public T clientReference, serverReference;

	public InformationSet(T clientRef,
			T serverRef) {
		clientReference = clientRef;
		serverReference = serverRef;
	}

	public T getProperInf(World world) {
		return (world.isRemote) ? clientReference : serverReference;
	}

}
