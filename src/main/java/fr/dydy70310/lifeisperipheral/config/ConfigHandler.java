package fr.dydy70310.lifeisperipheral.config;

import java.io.File;

import fr.dydy70310.lifeisperipheral.Reference;
import net.minecraftforge.common.config.Configuration;

public class ConfigHandler {
	
	  public static void init(File file)
	  {
	    Configuration config = new Configuration(file);
	    config.load();
	    if (config.getConfigFile() == null)
	    {
	      Reference.Range = config.get("EntityDetector", "range", Reference.Range).getDouble();
	      Reference.AllowServerMessage = config.get("ChatInterface", "AllowServerMessage", Reference.AllowServerMessage).getBoolean();
	      Reference.AllowPlayerMessage = config.get("ChatInterface", "AllowPlayMeerssage", Reference.AllowPlayerMessage).getBoolean();
	      Reference.AllowComputerMessage = config.get("ChatInterface", "AllowComputerMessage", Reference.AllowComputerMessage).getBoolean();
	    }
	    
	    config.addCustomCategoryComment("EntityDetector", "Define the maximum radius for detection of entity in the EntityDetector");
	    Reference.Range = config.get("EntityDetector", "range", Reference.Range).getDouble();
	    config.addCustomCategoryComment("ChatInterface", "'Rate' Define the number of maximum messages per seconds allowed to be sent\n'AllowServerMessage' Allow the ChatInterface to send messages in Global chat\n'AllowPlayerMessage' Allow the ChatInterface to send messages to players in private ");
	    Reference.Range = config.get("ChatInterface", "Rate", Reference.Rate).getDouble();
	    Reference.AllowServerMessage = config.get("ChatInterface", "AllowServerMessage", Reference.AllowServerMessage).getBoolean();
	    Reference.AllowPlayerMessage = config.get("ChatInterface", "AllowPlayerMessage", Reference.AllowPlayerMessage).getBoolean();
	    Reference.AllowComputerMessage = config.get("ChatInterface", "AllowComputerMessage", Reference.AllowComputerMessage).getBoolean();
	    
	    config.save();
	  }
}
