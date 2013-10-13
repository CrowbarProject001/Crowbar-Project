/**
 * 
 */
package cn.weaponmod.client.command;

import cn.weaponmod.client.keys.Debug_MovingProcessor;
import net.minecraft.command.CommandBase;
import net.minecraft.command.ICommandSender;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatMessageComponent;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.StatCollector;

/**
 * @author WeAthFolD
 *
 */
public class Command_SetMode extends CommandBase {

	private static int mode;
	private static Debug_MovingProcessor activeProcessor;
	
	public static int getMode() {
		return mode;
	}

	/* (non-Javadoc)
	 * @see net.minecraft.command.ICommand#getCommandName()
	 */
	@Override
	public String getCommandName() {
		return "setmode";
	}

	/* (non-Javadoc)
	 * @see net.minecraft.command.ICommand#getCommandUsage(net.minecraft.command.ICommandSender)
	 */
	@Override
	public String getCommandUsage(ICommandSender ics) {
		return "/setmode [mode]";
	}
	
	public static void setActiveProcessor(EntityPlayer player, Debug_MovingProcessor pr) {
		if(pr != activeProcessor)
			printProcessor(player, pr);
		activeProcessor = pr;
	}
	
	private static void printProcessor(EntityPlayer player, Debug_MovingProcessor pr) {
		if(pr != null) {
			player.sendChatToPlayer(ChatMessageComponent.func_111066_d("--------------------ACTIVE DEBUGGER INF------------------"));
			for(int i = 0; pr.getDescription(i) != null; i++) {
				player.sendChatToPlayer(ChatMessageComponent.func_111066_d(EnumChatFormatting.GREEN + "Mode " + i + " : " + EnumChatFormatting.WHITE + pr.getDescription(i)));
			}
		}
	}

	/* (non-Javadoc)
	 * @see net.minecraft.command.ICommand#processCommand(net.minecraft.command.ICommandSender, java.lang.String[])
	 */
	@Override
	public void processCommand(ICommandSender ics, String[] astring) {
		if(astring.length == 0) {
			ics.sendChatToPlayer(ChatMessageComponent.func_111066_d("Current Mode : " + EnumChatFormatting.RED + mode));
			printProcessor(this.getCommandSenderAsPlayer(ics), activeProcessor);
		} else if(astring.length == 1) {
			int num = Integer.valueOf(astring[0]);
			if(num >= 0) {
				mode = num;
				ics.sendChatToPlayer(ChatMessageComponent.func_111066_d("Current Mode : " + EnumChatFormatting.RED  + mode));
				if(activeProcessor == null)
					ics.sendChatToPlayer(ChatMessageComponent.func_111066_d("Debugger Unavailable"));
				else ics.sendChatToPlayer(ChatMessageComponent.func_111066_d(activeProcessor.getDescription(mode)));
			}
		}
	}

}
