package cbproject.deathmatch.items.wpns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.utils.CBCWeaponInformation;
import cbproject.crafting.register.CBCItems;
import cbproject.deathmatch.entities.EntityRPGDot;
import cbproject.deathmatch.entities.EntityRocket;
import cbproject.deathmatch.utils.AmmoManager;
import cbproject.deathmatch.utils.InformationBullet;
import cbproject.deathmatch.utils.InformationRPG;
import cbproject.deathmatch.utils.InformationSet;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.Icon;
import net.minecraft.world.World;

public class Weapon_RPG extends WeaponGeneralBullet {

	private static Icon iconDot;
	
	public Weapon_RPG(int par1) {
		super(par1,CBCItems.ammo_rpg.itemID, 2);
		
		setUnlocalizedName("weapon_rpg");
		setCreativeTab(CBCMod.cct);
		setMaxDamage(2);
		
		setJamTime(20);
		setLiftProps(20, 2);
	}

	@Override
    @SideOnly(Side.CLIENT)
    public void registerIcons(IconRegister par1IconRegister)
    {
        this.itemIcon = par1IconRegister.registerIcon("lambdacraft:weapon_rpg");
        iconDot = par1IconRegister.registerIcon("lambdacraft:rpg_dot");
    }
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		
		
		InformationRPG inf = (InformationRPG) super.onBulletWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
		if(par2World.isRemote)
			return;
		if(inf == null){
			if(!(par3Entity instanceof EntityPlayer))
				return;
			inf = getInformation(par1ItemStack, par2World);
			if(inf != null){
				if(inf.currentDot != null){
					inf.currentDot.setDead();
					inf.currentDot = null;
					return;
				}
			}
			return;
		}
		int mode = getMode(par1ItemStack);
		
		EntityPlayer player = (EntityPlayer) par3Entity;
		EntityRPGDot dot = getRPGDot(inf, par2World, player);
		if(mode == 0){
			if(dot != null){
				dot.setDead();
				inf.currentDot = null;
			}
			return;
		}
		
		if(dot == null || dot.isDead){
			dot = new EntityRPGDot(par2World, player);
			par2World.spawnEntityInWorld(dot);
			this.setRPGDot(inf, dot);
		}
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		processRightClick(par1ItemStack, par2World, par3EntityPlayer);
		return par1ItemStack;
    }
	
	@Override
	public void onBulletWpnShoot(ItemStack par1ItemStack, World par2World, EntityPlayer par3Entity, InformationBullet information ){
		information.setLastTick();
		int mode = getMode(par1ItemStack);
		par3Entity.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		if(par2World.isRemote)
			return;
		if(this.canShoot(par3Entity, par1ItemStack)){
			par2World.playSoundAtEntity(par3Entity, getSoundShoot(mode), 0.5F, 1.0F);
			par2World.spawnEntityInWorld(new EntityRocket(par2World, par3Entity, par1ItemStack));
			AmmoManager.consumeAmmo(par3Entity, this, 1);
		}
	}
	
	@Override
    public Boolean canShoot(EntityPlayer player, ItemStack is){
    	return AmmoManager.hasAmmo(this, player) || player.capabilities.isCreativeMode;
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		super.onPlayerStoppedUsing(par1ItemStack, par2World, par3EntityPlayer, par4);
	}
	
	public static EntityRPGDot getRPGDot(InformationRPG inf, World world, EntityPlayer player){
		return inf.currentDot;
	}
	
	public EntityRPGDot getRPGDot(ItemStack is, World world, EntityPlayer player){
		return (loadInformation(is, player)).currentDot;
	}
	
	public void setRPGDot(InformationRPG inf, EntityRPGDot dot){
		inf.currentDot = dot;
	}

	@Override
	public String getSoundShoot(int mode) {
		return "cbc.weapons.rocketfire";
	}

	@Override
	public String getSoundJam(int mode) {
		return "cbc.weapons.gunjam_a";
	}

	@Override
	public String getSoundReload(int mode) {
		return "";
	}

	@Override
	public int getShootTime(int mode) {
		return 40;
	}

	@Override
	public double getPushForce(int mode) {
		return 0;
	}

	@Override
	public int getDamage(int mode) {
		return 0;
	}

	@Override
	public int getOffset(int mode) {
		return 0;
	}

	@Override
	public String getModeDescription(int mode) {
		return (mode == 0)? "mode.rpg" : "mode.rpg2";
	}
	
	@Override
	public InformationRPG getInformation(ItemStack itemStack, World world){	
	   InformationSet set = CBCWeaponInformation.getInformation(itemStack);
	   return (InformationRPG) (set == null ? null : set.getProperInf(world));
	}
	    
	@Override
	public InformationRPG loadInformation(ItemStack par1ItemStack, EntityPlayer par2EntityPlayer){
		
		InformationRPG inf = getInformation(par1ItemStack, par2EntityPlayer.worldObj);

		if(inf != null)
			return inf;
		
		double uniqueID = Math.random()*65535D;
		inf = (InformationRPG) CBCWeaponInformation.addToList(uniqueID, createInformation(par1ItemStack, par2EntityPlayer)).getProperInf(par2EntityPlayer.worldObj);
		
		if(par1ItemStack.stackTagCompound == null)
			par1ItemStack.stackTagCompound = new NBTTagCompound();
		par1ItemStack.getTagCompound().setDouble("uniqueID", uniqueID);
		return inf;
		
	}
	
	private InformationSet createInformation(ItemStack is, EntityPlayer player){
		InformationRPG inf = new InformationRPG(is);
		InformationRPG inf2 = new InformationRPG(is);
		return new InformationSet(inf, inf2);
	}
		 

}
