package cbproject.utils.weapons;

import net.minecraft.world.World;

public class InformationSet {
	public double signID;
	public InformationWeapon clientReference, serverReference;
	public InformationSet(InformationWeapon clientRef, InformationWeapon serverRef, double par3ID) {
		
		clientReference = clientRef;
		serverReference = serverRef;
		signID = par3ID;
		
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
