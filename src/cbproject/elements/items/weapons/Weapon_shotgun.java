package cbproject.elements.items.weapons;

import cbproject.CBCMod;
import cbproject.proxy.ClientProxy;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class Weapon_shotgun extends WeaponGeneralBullet {

	public Weapon_shotgun(int par1) {
		
		super(par1 , CBCMod.cbcItems.itemAmmo_9mm.itemID);
		
		setItemName("weapon_shotgun");
		
		setTextureFile(ClientProxy.ITEMS_TEXTURE_PATH);
		setIconCoord(5,2);

		setCreativeTab( CBCMod.cct );
		setMaxStackSize(1);
		setMaxDamage(9); // 最高伤害为18 0a0
		setNoRepair(); //不可修补
		
		shootTime = new int [1];
		shootTime[0] = 3;
		reloadTime = 60;
		jamTime = 10;
		
		pathSoundShoot = new String[1];
		pathSoundJam = new String[1];
		pathSoundReload = new String[1];
		
		pathSoundShoot[0] = "cbc.weapons.hksa";
		pathSoundJam[0] = "cbc.weapons.gunjam_a";
		pathSoundReload[0] = "cbc.weapons.nmmarr";
		
		mode = 0; //子弹+自动
		// TODO Auto-generated constructor stub
	}

	@Override
	public void onUpdate(ItemStack par1ItemStack, World par2World,
			Entity par3Entity, int par4, boolean par5) {
		// TODO Auto-generated method stub

	}

}
