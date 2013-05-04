/**
 * 
 */
package cbproject.deathmatch.items.wpns;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.core.CBCMod;
import cbproject.core.utils.CBCWeaponInformation;
import cbproject.deathmatch.entities.EntitySatchel;
import cbproject.deathmatch.utils.AmmoManager;
import cbproject.deathmatch.utils.InformationWeapon;
import cbproject.deathmatch.utils.InformationSet;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * Remote detonation bomb.
 * Mode I : Setting mode.
 * Mode II : Detonating mode.
 * @author WeAthFolD
 *
 */
public class Weapon_satchel extends WeaponGeneral {


	public Weapon_satchel(int par1) {
		
		super(par1, 0, 2);
		setUnlocalizedName("weapon_satchel");
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(64);

		maxModes = 2;
		upLiftRadius = recoverRadius = 0;
		
		double push[] = {0, 0};
		int dam[] = {0, 0}, offset[] = {0, 0};
		
	}
	
    @SideOnly(Side.CLIENT)
    public void updateIcons(IconRegister par1IconRegister)
    {
        this.iconIndex = par1IconRegister.registerIcon("lambdacraft:weapon_satchel");
    }
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	public InformationWeapon onWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		
		if(!(par3Entity instanceof EntityPlayer))
			return null;
		ItemStack currentItem = ((EntityPlayer)par3Entity).inventory.getCurrentItem();
		if(currentItem == null || !currentItem.equals(par1ItemStack))
			return null;
		
		InformationWeapon information = loadInformation(par1ItemStack, (EntityPlayer)par3Entity);
		return information;

	}
	
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		InformationWeapon inf = loadInformation(par1ItemStack, par3EntityPlayer);
		if(inf == null)
			return par1ItemStack;
		
		int mode = getMode(par1ItemStack);
		
		NBTTagCompound nbt = par3EntityPlayer.getEntityData();
		int count = nbt.getInteger("satchelCount");
		
		//Max 6 satchel
		if(mode == 0){ //Setting mode
			if(count > 5)
				return par1ItemStack;
			
			nbt.setBoolean("doesExplode", false);
			EntitySatchel ent = new EntitySatchel(par2World, par3EntityPlayer);
			par2World.spawnEntityInWorld(ent);	
			nbt.setInteger("satchelCount", ++count);
			if( !par3EntityPlayer.capabilities.isCreativeMode)
				AmmoManager.tryConsume(par3EntityPlayer,this.itemID , 1);
			
		} else { //Detonating mode
			nbt.setBoolean("doesExplode", true);
			nbt.setInteger("satchelCount", 0);
		}
		
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		par3EntityPlayer.setEating(false);
		return par1ItemStack;
    }
	
	@Override
	public InformationWeapon getInformation(ItemStack itemStack, World world){	
	   InformationSet set = CBCWeaponInformation.getInformation(itemStack);
	   return set == null ? null : set.getProperInf(world);
	}
	    
	
	@Override
	public InformationWeapon loadInformation(ItemStack itemStack,
			EntityPlayer entityPlayer) {
		
		InformationWeapon inf = getInformation(itemStack, entityPlayer.worldObj);
		if(inf != null){
			return inf;
		}

		double uniqueID = Math.random()*65535D;
		CBCWeaponInformation.addToList(uniqueID, createInformation(itemStack, entityPlayer));

		if(itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		
		itemStack.stackTagCompound.setDouble("uniqueID", uniqueID);
		
		return inf;
	}
	
	private InformationSet createInformation(ItemStack is, EntityPlayer player){
		InformationWeapon inf = new InformationWeapon(is);
		InformationWeapon inf2 = new InformationWeapon(is);
		return new InformationSet(inf, inf2);
	}
	
    @Override
	public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 100;
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
		return mode == 0 ? "mode.satchel1" : "mode.satchel2";
	}


}
