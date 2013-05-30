package cbproject.deathmatch.items.wpns;

import cbproject.core.CBCMod;
import cbproject.core.utils.CBCWeaponInformation;
import cbproject.crafting.register.CBCItems;
import cbproject.deathmatch.entities.EntityCrossbowArrow;
import cbproject.deathmatch.utils.BulletManager;
import cbproject.deathmatch.utils.InformationBullet;
import cbproject.deathmatch.utils.InformationSet;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Crossbow in HLDM Style.
 * Mode I : Non-delay, sniper rifle style.
 * Mode II : Explosive arrow.
 * @author WeAthFolD
 */
public class Weapon_crossbow extends WeaponGeneralBullet {

	public Weapon_crossbow(int par1) {
		super(par1 , CBCItems.ammo_bow.itemID, 2);
		
		setUnlocalizedName("weapon_crossbow");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(6);
		setNoRepair();
		setIconName("weapon_crossbow");
		
		setLiftProps(10, 5);
		setReloadTime(40);
		
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		processRightClick(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
    }
	
	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){
		
		Boolean canUse = this.canShoot(par3Entity, par1ItemStack);
		int mode = getMode(par1ItemStack);
		if(!canUse){
			information.setLastTick();
			return;
		}

		par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 1.0F);	
		switch(mode){
		case 0:
			if(!par2World.isRemote)
				BulletManager.Shoot(par1ItemStack, par3Entity, par2World);
			break;
		case 1:
			if(!par2World.isRemote)
				par2World.spawnEntityInWorld(new EntityCrossbowArrow(par2World, par3Entity));
			break;
		default:
			break;
		}
		information.setLastTick();
    	if(par3Entity instanceof EntityPlayer){
    		doUplift(information, par3Entity);
    		if(!par3Entity.capabilities.isCreativeMode ){
    				par1ItemStack.damageItem( 1 , par3Entity);
    				par3Entity.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
    		}
    	}
		
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{	
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
	}

	@Override
	public String getSoundShoot(int mode) {
		return "cbc.weapons.xbow_fire";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "cbc.weapons.xbow_reload";
	}

	@Override
	public int getShootTime(int mode) {
		return 30;
	}

	@Override
	public double getPushForce(int mode) {
		return 1.5;
	}

	@Override
	public int getDamage(int mode) {
		return 20;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}

	@Override
	public String getModeDescription(int mode) {
		return mode == 0 ? "mode.bow1" : "mode.bow2";
	}

	public boolean isBowPulling(ItemStack item) {
		InformationSet inf = CBCWeaponInformation.getInformation(item);
		InformationBullet information = (InformationBullet) (inf == null ? null : inf.clientReference);
		if(information == null)
			return false;
		return !(information.isShooting && information.getDeltaTick() < 17);
	}
}
