package fr.dydy70310.lifeisperipheral.register;

import fr.dydy70310.lifeisperipheral.tile.TileChatInterface;
import fr.dydy70310.lifeisperipheral.tile.TileWorldInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LifeIsPeripheralEventHandler {

	@SubscribeEvent
	public void onServeurChatEvent(ServerChatEvent event){
		if(event.message.length() >= 2){
			String msg = event.message.substring(0, 2);
			if(msg.equals("##")){
				event.setCanceled(true);
				startEvent(event.username,event.message);
			}else{
				startEvent(event.username,event.message);
			}
		}else{
			startEvent(event.username,event.message);
		}	
	}
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event){
		if(event.entity instanceof EntityPlayer){
			startEvent(event.entity.getName(), event.source.getDeathMessage(event.entityLiving).getUnformattedText());
		}

	}
	
	
	public void startEvent(String sender,String msg){
		
		TileChatInterface.onServerMessage(sender,msg);
	}
	
}