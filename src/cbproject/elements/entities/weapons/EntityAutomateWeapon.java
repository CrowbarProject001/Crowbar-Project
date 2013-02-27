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
		shootTime = par1ShootTime;
		reloadTime = par2ReloadTime;
		instance  = par5ItemStack;
		entityPlayer = par6EntityPlayer;

		pathJam = par35Jam;
		pathShoot = par3Shoot;
		pathReload = par4Reload;
		lastTick = 0;
		
		//ammoManager = new AmmoManager( par6EntityPlayer, par5ItemStack );
		System.out.println("Entity Inited.");
		par5ItemStack.damageItem( 5 , null);
		instance.damageItem( 5 , null );
		System.out.println ("Item Damage: " + instance.getItemDamage());
		this.addEntityID(instance.stackTagCompound);
		readEntityFromNBT(instance.stackTagCompound);
		
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
	
	@Override
    public boolean addEntityID(NBTTagCompound par1NBTTagCompound)
    {
        String var2 = this.getEntityString();
        System.out.print(var2);
        if (!this.isDead && var2 != null)
        {
            par1NBTTagCompound.setString("id", var2);
            this.writeToNBT(par1NBTTagCompound);
            return true;
        }
        else
        {
            return false;
        }
    }

}
