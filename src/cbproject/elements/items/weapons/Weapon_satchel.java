/**
 * 
 */
package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.elements.entities.weapons.EntitySatchel;
import cbproject.misc.CBCKeyProcess;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.InformationBullet;
import cbproject.utils.weapons.InformationSatchel;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

/**
 * @author WeAthFolD
 *
 * @description Remote detonation bomb.
 */
public class Weapon_satchel extends WeaponGeneral {


	public Weapon_satchel(int par1) {
		
		super(par1, 0, 2);
		setItemName("weapon_satchel");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(0,4);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(64);

		maxModes = 2;
		upLiftRadius = recoverRadius = 0;
		
		double push[] = {0, 0};
		int dam[] = {0, 0}, offset[] = {0, 0};
		
		setPushForce(push);
		setDamage(dam);
		setOffset(offset);
		
	}
	
	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		onWpnUpdate(par1ItemStack, par2World, par3Entity, par4, par5);
	}
	
	@Override
	public void onWpnUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5){
		
		if(!(par3Entity instanceof EntityPlayer))
			return;
		ItemStack currentItem = ((EntityPlayer)par3Entity).inventory.getCurrentItem();
		if(currentItem == null || !currentItem.equals(par1ItemStack))
			return;
		
		InformationWeapon information = loadInformation(par1ItemStack, (EntityPlayer) par3Entity, par2World);
		if(CBCKeyProcess.modeChange){
			CBCKeyProcess.modeChange = false;
			onModeChange(information, (EntityPlayer) par3Entity, par1ItemStack);	
		}

	}
	
	public void onModeChange(InformationWeapon inf, EntityPlayer player, ItemStack itemStack){
		
		super.onModeChange(itemStack, inf, player);
		if(itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		itemStack.stackTagCompound.setInteger("mode", inf.mode);
		
	}
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer){
		
		if(par1ItemStack.stackSize == 1)
			return par1ItemStack;
		
		InformationSatchel inf = loadInformation(par1ItemStack, par3EntityPlayer, par2World);
		inf.ammoManager.setAmmoInformation((EntityPlayer)par3EntityPlayer);
		int mode = par1ItemStack.stackTagCompound.getInteger("mode");
		//最多放置6个Satchel
		if(mode == 0){
			
			if(inf.list.size() > 5)
				return par1ItemStack;
			
			if(par3EntityPlayer.capabilities.isCreativeMode || inf.ammoManager.ammoCapacity > 1){
				EntitySatchel ent = new EntitySatchel(par2World, par3EntityPlayer);
				inf.list.add(ent);
				par2World.spawnEntityInWorld(ent);
			}
			if( !par3EntityPlayer.capabilities.isCreativeMode)
				inf.ammoManager.consumeAmmo(1);
			
		} else {
			
			for(int i = 0; i < inf.list.size(); i++){
				EntitySatchel ent = (EntitySatchel) inf.list.get(i);
				ent.Explode();
			}
			inf.list.clear();
			
		}
		
		par3EntityPlayer.setItemInUse(par1ItemStack, this.getMaxItemUseDuration(par1ItemStack));
		return par1ItemStack;
    }

	@Deprecated
	public InformationSet loadInformation(ItemStack par1ItemStack,
			EntityPlayer entityPlayer) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Deprecated
	public InformationSet getInformation(ItemStack itemStack) {
		return null;
	}
	
	public InformationSatchel loadInformation(ItemStack par1ItemStack, EntityPlayer entityPlayer, World world){
		return loadInformation(entityPlayer, par1ItemStack).getProperSatchel(world);
	}
	
	public InformationSet getInformation(EntityPlayer player){

	  	NBTTagCompound nbt = player.getEntityData();
	  	
    	double uniqueID = nbt.getDouble("uniqueID");
    	int id = nbt.getInteger("weaponID");
    	if(id == 0 || id >= listItemStack.size())
    		return null;
    	
    	InformationSet inf = (InformationSet) listItemStack.get(id);
    	if(inf.signID != uniqueID)
    		return null;
    	
    	return inf;
	}
	

	public InformationSet loadInformation(EntityPlayer entityPlayer, ItemStack itemStack) {
		
		InformationSet inf = getInformation(entityPlayer);
		if(inf != null)
			return inf;
		
		InformationSatchel server = new InformationSatchel(entityPlayer, itemStack);
		InformationSatchel client = new InformationSatchel(entityPlayer, itemStack);
		
		double uniqueID = Math.random()*65535D;
		int id = listItemStack.size();
		
		inf = new InformationSet(client, server, uniqueID);
		listItemStack.add(inf);
		NBTTagCompound nbt = entityPlayer.getEntityData();
		
		nbt.setDouble("uniqueID", uniqueID);
		nbt.setInteger("weaponID", id);
		
		//配合Renderer，写入模式信息
		if(itemStack.stackTagCompound == null)
			itemStack.stackTagCompound = new NBTTagCompound();
		itemStack.stackTagCompound.setInteger("mode", 0);
		
		return inf;
		
	}
	
    public int getMaxItemUseDuration(ItemStack par1ItemStack)
    {
        return 100;
    }
	
	public InformationSatchel getInformation(EntityPlayer entityPlayer, World world){
		InformationSet set = getInformation(entityPlayer);
		return (set == null) ? null : set.getProperSatchel(world);
	}


}
