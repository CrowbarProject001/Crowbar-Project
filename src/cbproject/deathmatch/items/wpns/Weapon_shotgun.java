package cbproject.deathmatch.items.wpns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.crafting.register.CBCItems;
import cbproject.deathmatch.entities.BulletSG;
import cbproject.deathmatch.entities.EntityBulletSG;
import cbproject.deathmatch.utils.AmmoManager;
import cbproject.deathmatch.utils.InformationBullet;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_shotgun extends WeaponGeneralBullet {

	public static int BUCKSHOT_COUNT[] = {8, 16};
	
	public Weapon_shotgun(int par1) {
		
		super(par1 , CBCItems.ammo_shotgun.itemID, 2);
		
		setUnlocalizedName("weapon_shotgun");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(9); 
		setNoRepair(); //不可修补

		setReloadTime(10);
		setJamTime(20);
		setLiftProps(30, 5);
		
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_shotgun");
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
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
	}

	@Override
    public void onBulletWpnReload(ItemStack par1ItemStack, World par2World, EntityPlayer player, InformationBullet information ){
		
		int mode = getMode(par1ItemStack);
		int dmg = par1ItemStack.getItemDamage();
		if( dmg <= 0){
			information.setLastTick();
			information.isReloading = false;
			return;
		}
		if(par2World.isRemote)
			return;
		
		if( AmmoManager.consumeAmmo(player, this, 1) == 0){
			par1ItemStack.setItemDamage( par1ItemStack.getItemDamage() - 1);
		    par2World.playSoundAtEntity(player, getSoundReload(mode), 0.5F, 1.0F);	
    	} else 
    		information.isReloading = false;
			
    	information.setLastTick();
		return;
    }
    
	@Override
    public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){

    	int maxDmg = par1ItemStack.getMaxDamage() -1;
    	int mode = getMode(par1ItemStack);
		if( par1ItemStack.getItemDamage() >= maxDmg ){
			information.setLastTick();
			return;
		}
		if(par3Entity instanceof EntityPlayer){	
    		doUplift(information, par3Entity);
    		if(!par3Entity.capabilities.isCreativeMode){
    				par1ItemStack.damageItem( ( mode == 0 || mode == 3 ) ? 1 : 2 , par3Entity);
    		}	
    	}

		information.setLastTick();
		par3Entity.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		if(par2World.isRemote)
			return;
		int count = mode == 0 ? 8 : 16;
		BulletSG sg = new BulletSG(par2World, par3Entity, par1ItemStack, mode);
		for(int i = 0; i < count ; i++)
			par2World.spawnEntityInWorld(new EntityBulletSG(par2World, par3Entity, par1ItemStack,sg));
		sg.postInit();
		par2World.spawnEntityInWorld(sg);
		par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 1.0F);	
		
    }

	@Override
	public String getSoundShoot(int mode) {
		switch(mode){
		case 0 :
			return "cbc.weapons.sbarrela";
		case 1 :
			return "cbc.weapons.sbarrelb";
		case 2 :
			return "cbc.weapons.sbarrela_a";
		default:
			return "cbc.weapons.sbarrelb_a";
		}
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.scocka";
	}

	@Override
	public String getSoundReload(int mode) {
		
		int index = (int) (3 * Math.random());
		if(index == 0)
			return  "cbc.weapons.reloada";
		if(index == 1)
			return  "cbc.weapons.reloadb";
		return  "cbc.weapons.reloadc";
					
	}

	@Override
	public int getShootTime(int mode) {
		return mode == 0? 20: 35;
	}

	@Override
	public double getPushForce(int mode) {
		return mode == 0 ? 1.2 : 2;
	}

	@Override
	public int getDamage(int mode) {
		return 4;
	}

	@Override
	public int getOffset(int mode) {
		// TODO Auto-generated method stub
		return (mode == 0) ? 0 : 3;
	}

	@Override
	public String getModeDescription(int mode) {
		return mode == 0 ?  "mode.sg1" : "mode.sg2";
	}

}
