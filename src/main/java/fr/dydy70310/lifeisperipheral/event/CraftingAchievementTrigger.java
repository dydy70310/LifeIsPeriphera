package fr.dydy70310.lifeisperipheral.event;

import dan200.computercraft.ComputerCraft.Blocks;
import fr.dydy70310.lifeisperipheral.MainLIP;
import fr.dydy70310.lifeisperipheral.misc.AchievementList;
import fr.dydy70310.lifeisperipheral.register.BlockMods;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent.ItemCraftedEvent;

public class CraftingAchievementTrigger {
	
	@SubscribeEvent
	public void whenSmthisCrafted(ItemCraftedEvent e){
		MainLIP.LOGGER.info(e);
		if (e.crafting.isItemEqual(new ItemStack(Blocks.computer,1))){
			e.player.addStat(AchievementList.achievementCraftComputer, 1);
		}else if (e.crafting.isItemEqual(new ItemStack(Blocks.cable,1))){
			e.player.addStat(AchievementList.achievementCraftCable, 1);
		}else if (e.crafting.isItemEqual(new ItemStack(Blocks.peripheral,1,1))){
			e.player.addStat(AchievementList.achievementCraftWirelessModem,1);
		}else if (e.crafting.isItemEqual(new ItemStack(BlockMods.AdvancedNoteBlock,1))){
			e.player.addStat(AchievementList.achievementCraftAdvancedNoteBlock,1);
		}
	}
}
