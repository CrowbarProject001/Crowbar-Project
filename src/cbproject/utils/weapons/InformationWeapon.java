package cbproject.utils.weapons;

public class InformationWeapon {
	
	public int ticksExisted, lastTick, recoverTick, mode;
	public AmmoManager ammoManager;
	public Boolean isRecovering, modeChange;
	public double signID;
	public float originPitch;
	
	public InformationWeapon() {
		isRecovering = false;
		modeChange = false;
	}
	
	public void updateTick(){
		ticksExisted++;
	}
	
	public void setLastTick(){
		lastTick = ticksExisted;
	}

	public int getDeltaTick(){
		return ticksExisted - lastTick;
	}

}
