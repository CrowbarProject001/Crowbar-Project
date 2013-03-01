package cbproject.utils.weapons;

public class InformationBulletWeapon {
	//357,9mmAR,9mmhandgun等的辅助类
	public int ticksExisted, lastTick;
	public Boolean canUse, isShooting,isReloading;
	public double signID;

	public InformationBulletWeapon(double par1ID, Boolean par2CanUse, Boolean par3Shooting, Boolean par4Reloading, int par6Tick) {
		ticksExisted = 0;
		lastTick = 0;
		
		signID = par1ID;
		canUse = par2CanUse;
		isShooting = par3Shooting;
		isReloading = par4Reloading;

	}
	
	public void updateTick(){
		ticksExisted++;
	}
	
	public void setLastTick(){
		lastTick = ticksExisted;
	}

}
