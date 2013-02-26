package cbproject.elements.entities.weapons;

import cbproject.CBCMod;
import cbproject.utils.weapons.AmmoManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/*自动武器的实体处理类
 * 实现武器:357,9mmHandgun,9mmAR(模式1)。
 */
public class EntityAutomateWeapon extends Entity {
	
	NBTTagCompound nbt;
	public Boolean isAlive , canUse , isShooting , isReloading ;
	public int shootTime , reloadTime;
	public String pathShoot,pathReload,pathJam;
	
	public AmmoManager ammoManager;
	public ItemStack instance;
	
	private EntityPlayer entityPlayer;
	private int lastTick;
	
	public EntityAutomateWeapon( int par1ShootTime , int par2ReloadTime, String par3Shoot, String par4Reload, String par35Jam,
			ItemStack par5ItemStack , EntityPlayer par6EntityPlayer ) {
		
		super( par6EntityPlayer.worldObj );
		nbt = par5ItemStack.stackTagCompound;
		shootTime = par1ShootTime;
		reloadTime = par2ReloadTime;
		instance = par5ItemStack;
		entityPlayer = par6EntityPlayer;

		pathJam = par35Jam;
		pathShoot = par3Shoot;
		pathReload = par4Reload;
		lastTick = 0;
		
		//ammoManager = new AmmoManager( par6EntityPlayer, par5ItemStack );
		System.out.println("Entity Inited.");
		readEntityFromNBT(nbt);
		
	}

	@Override
    public void onUpdate() {
		super.onUpdate();
		System.out.println("Entity Updated.");
		//装弹函数
		if( isReloading && this.ticksExisted - lastTick >= reloadTime ){
			//CheckAmmoCapacity
			int dmg = instance.getItemDamage();
			if( dmg <= 0){
				isReloading = false;
				canUse = true;
				
				lastTick = ticksExisted;
				return;
			}
			//int cap = this.ammoManager.ammoCapacity;
			int cap = 33333;
			if( dmg >= cap ){
				//ammoManager.clearAmmo( entityPlayer );
				instance.setItemDamage( instance.getItemDamage() - cap);
			} else {
				//ammoManager.consumeAmmo( dmg );
				instance.setItemDamage( 0 );
			}
			
			if( instance.getItemDamage() >=17 )
				canUse = false;
			isReloading = false;
			
			nbt.setBoolean( "canUse", canUse);
			nbt.setBoolean( "isReloading", isReloading);
			
			lastTick = ticksExisted;
			return;
		}
		
		//射击
		if(isShooting && canUse && ticksExisted - lastTick >= shootTime ){
			if( instance.getItemDamage() >= 17 ){
				
				canUse = false;
				
				nbt.setBoolean( "canUse", canUse);
				lastTick = ticksExisted;
				return;
			}
			
			//playSoundAtEntity
			instance.damageItem( 1 , null);
			CBCMod.bulletManager.Shoot(entityPlayer, worldObj);
			System.out.println("Bang!");
			
			lastTick = ticksExisted;
			return;
			
		}
        
		//卡住：播放音效
		if( isShooting && !canUse && ticksExisted - lastTick >= shootTime){
			if( instance.getItemDamage() < 17){
				canUse = true;
				
				nbt.setBoolean("canUse", canUse);
				return;
			}
			System.out.println("Jammed");
			//playSoundAtEntity
			lastTick = ticksExisted;
		}
    }

	
	@Override
	protected void readEntityFromNBT(NBTTagCompound var1) {

		isAlive = var1.getBoolean( "isAlive" );
		canUse = var1.getBoolean( "canUse" );
		isShooting = var1.getBoolean( "isShooting" );
		isReloading = var1.getBoolean( "isReloading" );
		
		return;

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound var1) {
		// TODO Auto-generated method stub

	}

	@Override
	protected void entityInit() {
		System.out.println("init");
		// TODO Auto-generated method stub
	}

}
