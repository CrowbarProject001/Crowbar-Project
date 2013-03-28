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
	
	public InformationBulletWeapon getClientAsBullet(){
		return (InformationBulletWeapon)clientReference;
	}
	
	public InformationBulletWeapon getServerAsBullet(){
		return (InformationBulletWeapon)serverReference;
	}

	public InformationBulletWeapon getProperBullet(World world){
		return (InformationBulletWeapon) ((world.isRemote)? clientReference: serverReference);
	}

}
