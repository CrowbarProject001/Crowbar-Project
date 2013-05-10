/**
 * 
 */
package cbproject.api.entities;

import net.minecraft.entity.player.EntityPlayer;

/**
 * @author WeAthFolD
 *
 */
public interface IPlayerLink {
	
	EntityPlayer getLinkedPlayer();
	
	void setLinkedPlayer(EntityPlayer player);
}
