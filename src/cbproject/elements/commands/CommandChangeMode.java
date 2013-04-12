package cbproject.elements.commands;

import net.minecraft.item.*;
import cbproject.elements.items.weapons.WeaponGeneral;
import cbproject.utils.weapons.InformationSet;
import cbproject.utils.weapons.InformationWeapon;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;

public class CommandChangeMode extends CommandBase {

	public static final String USAGE = "/weaponmode";
	@Override
	public String getCommandName() {
		// TODO Auto-generated method stub
		return "weaponmode";
	}

	@Override
	public void processCommand(ICommandSender var1, String[] var2) {
		// TODO Auto-generated method stub
		
		EntityPlayer player = getCommandSenderAsPlayer(var1);
		if(var1 == null)
			System.out.println("The command must be execute as a player");
		
		ItemStack itemStack = player.inventory.getCurrentItem();
		if(itemStack.getItem() instanceof WeaponGeneral){
			
			WeaponGeneral item = (WeaponGeneral)itemStack.getItem();
			InformationSet information = item.loadInformation(itemStack, player);
			InformationWeapon inf = information.getProperInf(player.worldObj);
			if(inf.mode == item.maxModes -1)
				inf.mode = 0;
			else inf.mode++;
			if(player.worldObj.isRemote)
				var1.sendChatToPlayer("Client New mode : " + inf.mode);
			else var1.sendChatToPlayer("Server New mode : " + inf.mode);
			
		} else var1.sendChatToPlayer("Not holding a CBC Weapon 0a0");
			
	}

	@Override
    public String getCommandUsage(ICommandSender par1ICommandSender)
    {
        return USAGE;
    }
}
