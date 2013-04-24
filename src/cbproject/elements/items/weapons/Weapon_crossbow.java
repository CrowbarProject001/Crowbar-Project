package cbproject.elements.items.weapons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntityCrossbowArrow;
import cbproject.elements.entities.weapons.EntityRocket;
import cbproject.proxy.ClientProxy;
import cbproject.utils.CBCWeaponInformation;
import cbproject.utils.weapons.BulletManager;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

/**
 * Crossbow in HLDM Style.
 * Mode I : Non-delay, sniper rifle like.
 * Mode II : Explosive arrow.
 * @author WeAthFolD
 */
public class Weapon_crossbow extends WeaponGeneralBullet {

	public Weapon_crossbow(int par1) {
		super(par1 , CBCMod.cbcItems.itemAmmo_bow.itemID, 2);
		
		setUnlocalizedName("weapon_crossbow");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(6);
		setNoRepair();
		
		setLiftProps(10, 5);
		setReloadTime(40);
		
	}
	
	@Override
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:weapon_crossbow");
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
		
		Boolean canUse = (par1ItemStack.getMaxDamage() - par1ItemStack.getItemDamage() -1 > 0);
		int mode = information.mode;
		if(!canUse){
			information.setLastTick();
			return;
		}

		par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 1.0F);	
		switch(information.mode){
		case 0:
			BulletManager.Shoot(par1ItemStack, (EntityLiving) par3Entity, par2World, "smoke");
			break;
		case 1:
			par2World.spawnEntityInWorld(new EntityCrossbowArrow(par2World, (EntityLiving) par3Entity));
			break;
		default:
			break;
		}
		information.setLastTick();
    	if(par3Entity instanceof EntityPlayer){
    		doUplift(information, par3Entity);
    		if(!((EntityPlayer)par3Entity).capabilities.isCreativeMode ){
    				par1ItemStack.damageItem( 1 , par3Entity);
    		}
    	}
		
	}
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{	
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
	}
	
	@Override
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 200;
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
		return mode == 0 ? "Normal mode" : "Explosive Arrow mode";
	}

	public boolean isBowPulling(ItemStack item) {
		InformationSet inf = CBCWeaponInformation.getInformation(item);
		InformationBullet information = (InformationBullet) (inf == null ? null : inf.clientReference);
		return !(information.isShooting && information.getDeltaTick() < 17);
	}
}
