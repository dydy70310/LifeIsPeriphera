package fr.dydy70310.lifeisperipheral.tile;

import java.util.HashMap;
import java.util.List;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import fr.dydy70310.lifeisperipheral.MainLIP;
import fr.dydy70310.lifeisperipheral.Reference;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.ChatComponentText;
import net.minecraft.util.ChatStyle;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.world.World;

public class TileChatInterface extends TileEntity implements IPeripheral {

	public static class ChatInterfaceRegistry {
		public static HashMap<TileChatInterface, Boolean> chatInterfaces = new HashMap<TileChatInterface, Boolean>();
		public static HashMap<IComputerAccess, Boolean> computers = new HashMap<IComputerAccess, Boolean>();
	}
	
	
	

	
	public static String[] methods = {"sendGlobalMessage","sendPlayerMessage","setName","getName","getMethods"};
	  public BlockPos pos;
	  public World world;
	  public String name = "[-DefaultComputerName-]";
	  private long ActualRate = 0;
	  private long time = 0;
	@Override
	public String getType() {
		return "ChatInterface";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {

		if(this.name.equals("[-DefaultComputerName-]")){
			this.name = "Computer_" + computer.getID();
		}
		
		switch(method){
		
			case 0:
				if (ActualRate == Reference.Rate) {
					if (System.currentTimeMillis()-this.time >= 1000) {
						ActualRate = 0;
					}
				}
				if (ActualRate < Reference.Rate) {
					if(Reference.AllowServerMessage.equals(true)){
						if(arguments.length == 1){
							if (!this.name.equals("")) {
								if (arguments != null) {
									if (arguments[0] != null) {
										ChatComponentText text = new ChatComponentText("[" + this.name + "] " + arguments[0]);
							            ChatStyle style = new ChatStyle();
							            text.setChatStyle(style); 
										MinecraftServer.getServer().getConfigurationManager().sendChatMsg(text);
										System.out.println("ChatInterface:[name='"+this.name+"',x="+this.pos.getX()+",y="+this.pos.getY()+",z="+this.pos.getZ()+"] "+arguments[0]);
									}
								}
							}
							else
							{
								if (arguments != null) {
									if (arguments[0] != null) {
										ChatComponentText text = new ChatComponentText("" + arguments[0]);
							            ChatStyle style = new ChatStyle();
							            text.setChatStyle(style); 
										MinecraftServer.getServer().getConfigurationManager().sendChatMsg(text);
										System.out.println("ChatInterface:[name='"+this.name+"',x="+this.pos.getX()+",y="+this.pos.getY()+",z="+this.pos.getZ()+"] "+arguments[0]);
									}
								}
							}
							ActualRate += 1;
							if (ActualRate == Reference.Rate) {
								this.time = System.currentTimeMillis();
							}
							return new Object[] {true};
						}else{
							return new Object[] {"sendGlobalMessage(Message)"};
						}
					}else{
						return new Object[] {false,"This method is now allowed on this server"};
					}
				}	
				else
				{
					return new Object[] {false};
				}
			case 1:
				if (ActualRate == Reference.Rate) {
					if (System.currentTimeMillis()-this.time >= 1000) {
						ActualRate = 0;
					}
				}
				if (ActualRate < Reference.Rate) {
					if(Reference.AllowPlayerMessage.equals(true)){
						if(arguments.length == 2){
							
			    			List allp = this.world.playerEntities;
			    			EntityPlayer e = null;
			    			for(int i = 0;i < allp.size() ;i++){
			    				EntityPlayer ep = (EntityPlayer) allp.get(i);
			    				if (ep.getName().equals(arguments[0])){
			    					e = (EntityPlayer) allp.get(i);
			    				}
			    			}
			    			if(e == null){
			    				return new Object[] {false,"This player is not connected !"};
			    			}else{
			    				if (!this.name.equals("")) {
			    					if (arguments != null) {
				    					if (arguments[1] != null) {
											ChatComponentText text = new ChatComponentText(EnumChatFormatting.GRAY+"[" + this.name + EnumChatFormatting.GRAY +"] whispers to you: " + arguments[1]);
									        ChatStyle style = new ChatStyle();
									        text.setChatStyle(style); 
									        e.addChatMessage(text);
									        System.out.println("ChatInterface:[name='"+this.name+"',x="+this.pos.getX()+",y="+this.pos.getY()+",z="+this.pos.getZ()+"] whispers to "+arguments[0]+": "+arguments[1]);
				    					}	
			    					}
			    				}
							    else
							    {
			    					if (arguments != null) {
				    					if (arguments[1] != null) {
										ChatComponentText text = new ChatComponentText(EnumChatFormatting.GRAY+""+arguments[1]);
								        ChatStyle style = new ChatStyle();
								        text.setChatStyle(style); 
								        e.addChatMessage(text);
								        System.out.println("ChatInterface:[name='"+this.name+"',x="+this.pos.getX()+",y="+this.pos.getY()+",z="+this.pos.getZ()+"] whispers to "+arguments[0]+": "+arguments[1]);
				    					}
			    					}
							    }

					        ActualRate += 1;
							if (ActualRate == Reference.Rate) {
								this.time = System.currentTimeMillis();
							}
					        return new Object[] {true};
			    			}
						}else{
							return new Object[] {"sendPlayerMessage(Player, Message)"};
						}
					}else{
						return new Object[] {false,"This method is now allowed on this server"};
					}
				}

			case 2:
				if(arguments.length == 1){
					this.name = "" + arguments[0];
					return new Object[] {this.name};
				}else{
					return new Object[] {"setName(Name)"};
				}
			case 3:
				return new Object[] {this.name};
			case 4:
				if (arguments.length > 0) {
					if (arguments[0] instanceof String) {
						if (arguments[0].equals("sendGlobalMessage")) {
							return new Object[] {"sendGlobalMessage(Message)","sendGlobalMessage(String or Number)","This function allow to send a message to all players"};
						}
						if (arguments[0].equals("sendPlayerMessage")) {
							return new Object[] {"sendPlayerMessage(Player,Message)","sendPlayerMessage(String,String or Number)","This function allow to send a private message to a player"};
						}
						if (arguments[0].equals("getName")) {
							return new Object[] {"getName()","This function return the name of the ChatInterface who is displayed when the ChatInterface send a message"};
						}
						if (arguments[0].equals("setName")) {
							return new Object[] {"setName(Name)","setName(String)","This function set the name of the Chat Interface, the name is displayed when the ChatInterface send a message"};
						}
						if (arguments[0].equals("getMethods")) {
							return new Object[] {"getMethods(Name or Nothing)","getMethods(String or nil)","This function return informations about every function of this peripheral, if there no arguments, she returns every functions of the peripheral"};
						}
					}
					else
					{
						return new Object[] {"getMethods(String or nil)"};
					}
				}
				else
				{
					return new Object[] {"sendGlobalMessage","sendPlayerMessage","setName","getName","getMethods","","EVENTS :","- chat_message : When a player say something in the chat this event is triggered and return the player name and the message","Example : event, playerName, message = os.pullEvent('chat_message')","","INFORMATIONS : if you say something in the chat with ## before, this will never appear in the chat but it trigger the chat_message event"};
				}
		}
		return null;
	}


	
	@Override
	public void attach(IComputerAccess computer) {
		ChatInterfaceRegistry.chatInterfaces.put(this, true);
		ChatInterfaceRegistry.computers.put(computer, true);
	}
	
	@Override
	public void detach(IComputerAccess computer) {
		ChatInterfaceRegistry.chatInterfaces.remove(this);
		ChatInterfaceRegistry.computers.remove(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}
	
	public static void onServerMessage(String sender ,String chatmessage) {
		for (IComputerAccess computer : ChatInterfaceRegistry.computers.keySet()) {
			computer.queueEvent("chat_message", new Object[] {sender,chatmessage});
		}
	}

}