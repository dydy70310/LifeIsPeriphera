package fr.dydy70310.lifeisperipheral.register;

import fr.dydy70310.lifeisperipheral.tile.TileChatInterface;
import fr.dydy70310.lifeisperipheral.tile.TileWorldInterface;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.event.ServerChatEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class LifeIsPeripheralEventHandler {
	public static int lastId = (int) 0;
	public static String lastMessage = "";
 
	@SubscribeEvent
	public void onServeurChatEvent(ServerChatEvent event){
		if(event.message.length() >= 2){
			String msg = event.message.substring(0, 2);
			if(msg.equals("##")){
				event.setCanceled(true);
				startEvent(event.username,event.message);
			}
			else if(msg.equals("$$")){
				event.setCanceled(true);
				
				String[] s = event.message.split(" ");
				if(s[0].length() > 10 || s[0].length() <= 2){
					ChatComponentText text = new ChatComponentText(EnumChatFormatting.RED+"Usage : $$ComputerID message");
			        ChatStyle style = new ChatStyle();
			        text.setChatStyle(style); 
					event.player.addChatMessage(text);
				}else{
					String id = "";
					for (int i = 2;i < s[0].length();i++){
						id = id + s[0].charAt(i);
					}
					lastId = Integer.valueOf(id);
					String Message = "";
					for(int i = 1 ; i < s.length ; i++){
						Message = Message + s[i] + " ";
					}
					lastMessage = Message;
					TileChatInterface.onMessageId(lastId, event.username, lastMessage);
				}
			}else{
				startEvent(event.username,event.message);
			}
			
		}else{
			startEvent(event.username,event.message);
		}	
	}
	
	@SubscribeEvent
	public void onJoinEvent(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedInEvent event){
		TileChatInterface.onPlayerJoin(event.player.getName());
	}
	
	@SubscribeEvent
	public void onLeftEvent(net.minecraftforge.fml.common.gameevent.PlayerEvent.PlayerLoggedOutEvent event){
		TileChatInterface.onPlayerLeft(event.player.getName());
	}
	
	
	@SubscribeEvent
	public void onPlayerDeath(LivingDeathEvent event){
		if(event.entity instanceof EntityPlayer){
			startEvent(event.entity.getName(), event.source.getDeathMessage(event.entityLiving).getUnformattedText());
		}

	}
	
	@SubscribeEvent
	 public void onPlaySoundAtEntity(PlaySoundAtEntityEvent e) {
		if (e != null) {
			TileWorldInterface.onAmbiantSoundPlayed(e.entity, e.name, e.volume, e.pitch);
		}
	}
	
	public void startEvent(String sender,String msg){
		
		TileChatInterface.onServerMessage(sender,msg);
	}
	
}