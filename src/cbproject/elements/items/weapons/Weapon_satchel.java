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
import cbproject.utils.CBCWeaponInformation;
import cbproject.utils.weapons.AmmoManager;
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
		
		InformationSatchel information = loadInformation(par1ItemStack, (EntityPlayer)par3Entity);
		if(CBCKeyProcess.modeChange){
			CBCKeyProcess.onModeChange(par1ItemStack, information, (EntityPlayer) par3Entity, maxModes);
		}
		return information;

	}
	
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		if(par1ItemStack.stackSize == 1)
			return par1ItemStack;
		System.out.println("To 1");
		InformationSatchel inf = loadInformation(par1ItemStack, par3EntityPlayer);
		if(inf == null)
			return par1ItemStack;
		System.out.println("To 2");
		int mode = inf.mode;
		inf.satchelIDs = this.getSatchelsFromPlayer(par3EntityPlayer);
		if(inf.satchelIDs.length < 6){
			int []temp = new int[6];
			for(int i = 0; i < inf.satchelIDs.length; i++)
				temp[i] = inf.satchelIDs[i];
			inf.satchelIDs = temp;
		}
		//Max 6 satchel
		if(mode == 0){ //Setting mode
			
			if(!inf.canAddSatchel())
				return par1ItemStack;
			System.out.println("To 3");
			EntitySatchel ent = new EntitySatchel(par2World, par3EntityPlayer);
			inf.addSatchel(ent.entityId);
			System.out.println(ent.entityId);
			par2World.spawnEntityInWorld(ent);	
			if( !par3EntityPlayer.capabilities.isCreativeMode)
				AmmoManager.tryConsume(par3EntityPlayer,this.itemID , 1);
			
		} else { //Detonating mode
			
			for(int id : inf.satchelIDs){
				Entity ent = par2World.getEntityByID(id);
				System.out.println(id);
				if(ent != null)
					System.out.println(ent.getEntityName());
				if(ent != null && ent instanceof EntitySatchel)
					((EntitySatchel)ent).Explode();
			}
			inf.clearSatchel();
			
		}
		
		System.out.println(inf.satchelIDs.toString());
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		par3EntityPlayer.setEating(false);
		writeInformationToPlayer(inf, par3EntityPlayer);
		return par1ItemStack;
    }
	
	public void writeInformationToPlayer(InformationSatchel inf, EntityPlayer player){
		NBTTagCompound nbt = player.getEntityData();
		nbt.setIntArray("satchelIDs", inf.satchelIDs);
	}
	
	public int[] getSatchelsFromPlayer(EntityPlayer player){
		return player.getEntityData().getIntArray("satchelIDs");
	}
	
	@Override
	public InformationSatchel getInformation(ItemStack itemStack, World world){	
	   InformationSet set = CBCWeaponInformation.getInformation(itemStack);
	   return set == null ? null : set.getProperSatchel(world);
	}
	    
	
	@Override
	public InformationSatchel loadInformation(ItemStack itemStack,
			EntityPlayer entityPlayer) {
		
		InformationSatchel inf = getInformation(itemStack, entityPlayer.worldObj);
		if(inf != null){
			return inf;
		}

		double uniqueID = Math.random()*65535D;
		CBCMod.wpnInformation.addToList(uniqueID, createInformation(itemStack, entityPlayer));

		if(itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		
		itemStack.stackTagCompound.setDouble("uniqueID", uniqueID);
		
		return inf;
	}
	
	private InformationSet createInformation(ItemStack is, EntityPlayer player){
		InformationSatchel inf = new InformationSatchel(player, is);
		InformationSatchel inf2 = new InformationSatchel(player, is);
		return new InformationSet(inf, inf2);
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
