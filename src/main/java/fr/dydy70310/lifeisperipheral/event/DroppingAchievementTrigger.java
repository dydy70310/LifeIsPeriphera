package fr.dydy70310.lifeisperipheral.event;

import dan200.computercraft.ComputerCraft.Items;
import fr.dydy70310.lifeisperipheral.MainLIP;
import fr.dydy70310.lifeisperipheral.misc.AchievementList;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemPickupEvent;

public class DroppingAchievementTrigger {
	
	@SubscribeEvent
	public void whenSmthisDropped(ItemPickupEvent e){
		if (e.pickedUp.getEntityItem().getItem().equals(Items.treasureDisk)){
			MainLIP.LOGGER.info("GOT IT !");
			e.player.addStat(AchievementList.achievementDropTreasureDisk, 1);
		}
	}
}
