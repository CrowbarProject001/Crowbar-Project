/**
 * 
 */
package cbproject.elements.items.weapons;

import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntitySatchel;
import cbproject.misc.CBCKeyProcess;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSatchel;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;
import net.minecraft.client.renderer.texture.IconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
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
		
		InformationSet information = loadInformation(par1ItemStack, (EntityPlayer)par3Entity);
		if(CBCKeyProcess.modeChange){
			CBCKeyProcess.onModeChange(par1ItemStack, information, (EntityPlayer) par3Entity, maxModes);
		}
		return information.getProperInf(par2World);

	}
	
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		
		if(par1ItemStack.stackSize == 1)
			return par1ItemStack;
		
		InformationSet information = loadInformation(par1ItemStack, par3EntityPlayer);
		if(information == null)
			return par1ItemStack;
		
		InformationSatchel inf = information.getProperSatchel(par2World);
		
		int mode = inf.mode;
		//Max 6 satchel
		if(mode == 0){ //Setting mode
			
			if(inf.list.size() > 5)
				return par1ItemStack;
			
			if(par3EntityPlayer.capabilities.isCreativeMode || par1ItemStack.stackSize > 1){
				EntitySatchel ent = new EntitySatchel(par2World, par3EntityPlayer);
				inf.list.add(ent);
				par2World.spawnEntityInWorld(ent);
			}
			if( !par3EntityPlayer.capabilities.isCreativeMode)
				par1ItemStack.splitStack(1);
			
		} else { //Detonating mode
			
			for(int i = 0; i < inf.list.size(); i++){
				EntitySatchel ent = (EntitySatchel) inf.list.get(i);
				ent.Explode();
			}
			inf.list.clear();
			
		}
		
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		par3EntityPlayer.setEating(false);
		return par1ItemStack;
    }
	
	@Override
	public InformationSet loadInformation(ItemStack itemStack,
			EntityPlayer entityPlayer) {
		
		InformationSet inf = getInformation(itemStack);
		if(inf != null){
			return inf;
		}
		InformationSatchel server = new InformationSatchel(entityPlayer, itemStack);
		InformationSatchel client = new InformationSatchel(entityPlayer, itemStack);
		double uniqueID = Math.random()*65535D;
		
		inf = new InformationSet(client, server);
		CBCMod.wpnInformation.addToList(uniqueID, inf);

		if(itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		
		itemStack.stackTagCompound.setDouble("uniqueID", uniqueID);
		
		return inf;
	}
	
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
		return mode == 0 ? "Setting mode" : "Detonating mode";
	}


}
