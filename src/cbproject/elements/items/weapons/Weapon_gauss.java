package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityGaussRay;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.AmmoManager;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.GaussBulletManager;
import cbproject.utils.weapons.InformationEnergy;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.MotionXYZ;

public class Weapon_gauss extends WeaponGeneralEnergy {

	public static String SND_CHARGE_PATH = "cbc.weapons.gauss_chargeb", 
			SND_CHARGEA_PATH = "cbc.weapons.gauss_chargea",
			SND_SHOOT_PATH = "cbc.weapons.gaussb";
	
	public Weapon_gauss(int par1) {
		
		super(par1, CBCMod.cbcItems.itemAmmo_uranium.itemID, 2);
		
		setCreativeTab(CBCMod.cct);
		setItemName("weapon_gauss");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(0,3);

		String jam[] = {"cbc.weapons.gunjam_a"};
		int shoot[] = { 5 , 0}, dmg[] = {8, 0}, off[] = { 0, 2 };
		double push[] = { 1, 1};
		
		setJamTime(20);
		
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		
		InformationEnergy information = onEnergyWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		if(information == null)
				return;
		if(information.mode == 1)
			onChargeModeUpdate(information, par1ItemStack, par2World, par3Entity, par4, par5);
		
	}
	
	@Override
	public Boolean doShoot(InformationEnergy inf){
		return inf.mode == 0 && super.doShoot(inf) ;
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		
		InformationEnergy inf = loadInformation(par1ItemStack, par3EntityPlayer).getProperEnergy(par2World);

		processRightClick( inf, par1ItemStack, par2World, par3EntityPlayer);
		if(inf.mode == 1) {
			
			inf.isShooting = true;
			inf.charge = inf.chargeTime = 0;
			inf.setLastTick();
			inf.ammoManager.setAmmoInformation(par3EntityPlayer);
			Boolean canUse = inf.ammoManager.getAmmoCapacity() > 0 || par3EntityPlayer.capabilities.isCreativeMode;
			if(canUse)
				par2World.playSoundAtEntity(par3EntityPlayer, SND_CHARGEA_PATH, 0.5F, 1.0F);
			
		}
		par3EntityPlayer.setItemInUse(par1ItemStack,this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
		
    }
	
	public  void onChargeModeUpdate(InformationEnergy inf, ItemStack par1ItemStack, World par2World, 
			Entity par3Entity, int par4, boolean par5){
		
		int var1 = 29;
		int var2 = 180; //最大蓄力9s
		int var3 = 60;
		int var4 = 100;
		Boolean isShooting = inf.isShooting;
		Boolean ignoreAmmo = false;
		
		if(par3Entity instanceof EntityPlayer){
			if(((EntityPlayer)par3Entity).capabilities.isCreativeMode)
				ignoreAmmo = true;
		} else ignoreAmmo = true;
		if(isShooting){
			int ticksChange = inf.getDeltaTick();
			inf.chargeTime++;
			
			if(ignoreAmmo || inf.ammoManager.getAmmoCapacity() > 0 )
				inf.charge++;
			
			if(!ignoreAmmo && ticksChange <= var3 && inf.chargeTime %6 == 0)
				inf.ammoManager.consumeAmmo(1);
			
			if(inf.charge >= var1 && ticksChange > var1){
				inf.setLastTick();
				System.out.println("Sound Played");
				par2World.playSoundAtEntity(par3Entity, SND_CHARGE_PATH, 0.5F, 1.0F);
			}
		}
			
		//OverCharge!
		if(inf.charge > var2){ 
			isShooting = false;
			inf.charge = 0;
			par3Entity.attackEntityFrom(DamageSource.causeMobDamage((EntityLiving) (par3Entity)), 19);
		}
		
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		InformationSet i = getInformation(par1ItemStack, par2World);
		if(i == null)
			return;
		InformationEnergy inf = i.getProperEnergy(par2World);
		if(inf.mode == 0){
			super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
			return;
		}
		
		int charge = (inf.charge > 60? 60 : inf.charge); //最大蓄力3秒(10点)
		if(charge == 0)
			return;
		int damage = charge * 2/3; //最大为40
		double vel = charge / 15; //最大为4

		MotionXYZ var0 = MotionXYZ.getPosByPlayer2(par3EntityPlayer);
		double dx = var0.motionX * vel, dy = var0.motionY * vel, dz = var0.motionZ * vel;
		
		inf.isShooting = false;
		par2World.playSoundAtEntity(par3EntityPlayer, SND_SHOOT_PATH,  
				0.5F, 1.0F);
		par3EntityPlayer.addVelocity(-dx, -dy, -dz);
		GaussBulletManager.Shoot(par1ItemStack, par3EntityPlayer, par2World);
	
	}
	
	@Override
	public void onEnergyWpnShoot(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationEnergy information ){	
		super.onEnergyWpnShoot(par1ItemStack, par2World, par3Entity, information);
		par2World.spawnEntityInWorld(new EntityGaussRay(par2World, (EntityLiving) par3Entity));
		return;
	}
	
	@Override
	public void onEnergyWpnJam(ItemStack par1ItemStack, World par2World, Entity par3Entity, InformationEnergy information ){
		if(information.mode == 0 || (information.mode != 0 && information.isShooting == false)){
			super.onEnergyWpnJam(par1ItemStack, par2World, par3Entity, information);
			return;
		}
			
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 201; //10s
    }

	@Override
	public int getShootTime(int mode) {
		return 5;
	}

	@Override
	public String getSoundShoot(int mode) {
		return mode == 0 ? SND_SHOOT_PATH : "";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public int getDamage(int mode) {
		return 8;
	}

	@Override
	public double getPushForce(int mode) {
		return 1;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}


}
