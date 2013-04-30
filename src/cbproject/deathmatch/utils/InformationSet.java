package cbproject.deathmatch.utils;

import net.minecraft.world.World;

public class InformationSet {
	
	public InformationWeapon clientReference, serverReference;
	public InformationSet(InformationWeapon clientRef, InformationWeapon serverRef) {
		clientReference = clientRef;
		serverReference = serverRef;
	}
	
	public InformationBullet getClientAsBullet(){
		return (InformationBullet)clientReference;
	}
	
	public InformationBullet getServerAsBullet(){
		return (InformationBullet)serverReference;
	}

	public InformationBullet getProperBullet(World world){
		return (InformationBullet) ((world.isRemote)? clientReference: serverReference);
	}
	
	public InformationEnergy getProperEnergy(World world){
		return (InformationEnergy) ((world.isRemote)? clientReference: serverReference);
	}
	
	public InformationWeapon getProperInf(World world){
		return (world.isRemote)? clientReference: serverReference;
	}

}
