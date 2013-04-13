package cbproject.elements.items.weapons;

import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityEgonRay;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.AmmoManager;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationEnergy;
import cbproject.utils.weapons.InformationSet;

public class Weapon_egon extends WeaponGeneralEnergy {

	public static String SND_WINDUP = "cbc.weapons.egon_windup", 
			SND_RUN = "cbc.weapons.egon_run",
			SND_OFF = "cbc.weapons.egon_off";
	
	public Weapon_egon(int par1) {
		
		super(par1, CBCMod.cbcItems.itemAmmo_uranium.itemID, 1);
		setCreativeTab(CBCMod.cct);
		setItemName("weapon_egon");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(1,3);
		setJamTime(20);
		
		setLiftProps(1, 0);
	}

	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		
		InformationSet inf = getInformation(par1ItemStack, par2World);
		if(inf.getProperEnergy(par2World).isShooting && inf.getProperEnergy(par2World).ammoManager.getAmmoCapacity() > 0)
			par2World.playSoundAtEntity(par3EntityPlayer, SND_OFF, 0.5F, 1.0F);
		inf.getProperEnergy(par2World).isShooting = false;
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		InformationEnergy inf = onEnergyWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		if(inf == null)
			return;
		int dTick = inf.ticksExisted - inf.lastTick;
		if(inf.isShooting){
			if(inf.ticksExisted > 79 && (dTick - 79) % 42 == 0){
				par2World.playSoundAtEntity(par3Entity, SND_RUN, 0.5F, 1.0F);
			}
			if(dTick % 3 == 0 && !par2World.isRemote){
				inf.ammoManager.consumeAmmo(1);
				if(inf.ammoManager.ammoCapacity == 0){
					par2World.playSoundAtEntity(par3Entity, SND_OFF, 0.5F, 1.0F);
					inf.isShooting = false;
				}
			}
		}
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		
		InformationEnergy inf = loadInformation(par1ItemStack, par3EntityPlayer).getProperEnergy(par2World);
		processRightClick( inf, par1ItemStack, par2World, par3EntityPlayer);
		if(inf.isShooting){
			par2World.spawnEntityInWorld(new EntityEgonRay(par2World, par3EntityPlayer, par1ItemStack));
			par2World.playSoundAtEntity(par3EntityPlayer, SND_WINDUP, 0.5F, 1.0F);
		}
		inf.ticksExisted = inf.lastTick = 0;
		
		return par1ItemStack;

    }
	
	@Override
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationEnergy information ){
		
		BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "fire");
		
    	if(par3Entity instanceof EntityPlayerSP){
    		doRecover(information, (EntityPlayer) par3Entity);
    	}
    	
		return;
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 300;
    }

	@Override
	public double getPushForce(int mode) {
		return 0;
	}

	@Override
	public int getDamage(int mode) {
		return 10;
	}

	@Override
	public int getOffset(int mode) {
		return 2;
	}

	@Override
	public int getShootTime(int mode) {
		return 4;
	}

	@Override
	public String getSoundShoot(int mode) {
		return "";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}
	
}
