package fr.dydy70310.lifeisperipheral.misc;


import dan200.computercraft.ComputerCraft.Blocks;
import dan200.computercraft.ComputerCraft.Items;
import fr.dydy70310.lifeisperipheral.register.BlockMods;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.Achievement;


public class  AchievementList {
	
	public static Achievement achievementCraftComputer = (new Achievement("achievement.craftComputer","craftComputer",0,0,Blocks.computer,(Achievement)null).initIndependentStat().registerStat());
	public static Achievement achievementCraftCable = (new Achievement("achievement.craftCable","craftCable",2,1,Blocks.cable,achievementCraftComputer).registerStat());
	public static Achievement achievementCraftAdvancedNoteBlock = (new Achievement("achievement.craftAdvancedNoteBlock","craftAdvancedNoteBlock",-1,2,BlockMods.AdvancedNoteBlock,achievementCraftComputer).registerStat());
	public static Achievement achievementDropTreasureDisk = (new Achievement("achievement.DropTreasureDisk","dropTreasureDisk",-1,-1,Items.treasureDisk,(Achievement)null).setSpecial().initIndependentStat().registerStat());
	public static Achievement achievementCraftWirelessModem = (new Achievement("achievement.CraftWirelessModem","craftWirelessModem",3,2,(new ItemStack(Blocks.peripheral,1,1)),achievementCraftCable).registerStat());
	
	
	public static Achievement[] AchievementArray = {
		achievementCraftComputer,
		achievementCraftCable,
		achievementCraftAdvancedNoteBlock,
		achievementDropTreasureDisk,
		achievementCraftWirelessModem
	};
	
}


