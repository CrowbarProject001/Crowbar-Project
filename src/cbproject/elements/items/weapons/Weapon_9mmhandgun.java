package cbproject.elements.items.weapons;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import cbproject.utils.weapons.AmmoManager;


public class Weapon_9mmhandgun extends WeaponGeneral {
	/*9mmHandgun:两种模式
	 * 模式I(mode = 0 ):低速射击模式，准确度较高
	 * 模式II (mode = 1):高速射击模式，准确度较低
	 * 都是自动模式
	 */
	public static final int ShootTime[]={10,5};
	public static final int ReloadTime = 60; //3s
	
	Boolean canUse;
	Boolean isShooting;
	Boolean isReloading;
	Boolean isReloadSuccess;
	int tick;
	int lastTick;
	
	public Weapon_9mmhandgun(int par1) {
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID);
		
		setItemName("weapon_9mmhandgun");
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(2,2);
		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		this.maxStackSize = 1;
		setMaxDamage(19); // 最低伤害值为2，防止Minecraft删除物品
		setNoRepair(); //不可修补
		
		mode = 0;
		canUse = false;
		isShooting = false;
		isReloading = false;
		isReloadSuccess = false;

		tick = lastTick = 0;
		
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World, Entity par3Entity, int par4, boolean par5) {
		tick++;
		System.out.println("isShooting = " + isShooting);
		System.out.println("isReloading = "+ isReloading);
		//if(canUse && !isReloading && isShooting && ( tick - lastTick >= ShootTime[mode] || lastTick == 0)){
		/*
		if(isShooting){
			CBCMod.bulletManager.Shoot((EntityPlayer) par3Entity,par2World);
			par1ItemStack.damageItem(1, (EntityLiving)par3Entity);
			//par2World.playSoundAtEntity
			if( par1ItemStack.getItemDamage() <= 2)
				this.canUse = false;
			lastTick = tick;
			System.out.println("Bang!");
			return;
		}
		*/
		if(isShooting && !isReloading && !canUse && (tick - lastTick >= ShootTime[mode])){
		//if(isShooting && !canUse){
			//par2World.playSoundAtEntity(par3Entity,"cbc.weapons.9mmhandgunjam", 0.5F, 0.5F);
			System.out.println("Jammed");
			lastTick = tick;
			return;
		}
		/*
		if(isReloading){
			if(tick - lastTick >= ReloadTime){
				if(ammoManager.consumeAmmo(17))
					par1ItemStack.setItemDamage(19);
				else{
					par1ItemStack.setItemDamage(par1ItemStack.getItemDamage() + ammoManager.ammoCapacity);
					ammoManager.clearAmmo((EntityPlayer)par3Entity);
				}
			}
		}
		*/
	}

	@Override
    public boolean onItemUseFirst(ItemStack stack, EntityPlayer player, World world, int x, int y, int z, int side, float hitX, float hitY, float hitZ)
    {
		//this.ammoManager = new AmmoManager(player , stack);
		if( stack.getItemDamage() > 2)
			this.canUse = true;
			
        return true;
    }
	
	@Override
    public ItemStack onItemRightClick(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer)
    {
		if(!this.canUse){
			if(par1ItemStack.getItemDamage() > 2)
				canUse = true;
			else if(Reload()){
				isShooting = true;
			}else
				return par1ItemStack;
		}
		isShooting = true;
        return par1ItemStack;
    }
	
	@Override
    public void onPlayerStoppedUsing(ItemStack par1ItemStack, World par2World, EntityPlayer par3EntityPlayer, int par4) 
	{
		this.isShooting = false;
	}

	private Boolean Reload(){
		if(isReloading)return false;
		//isReloading = true;
		lastTick = tick;
		return true;
	}


}
