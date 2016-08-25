package fr.dydy70310.lifeisperipheral.misc;

import net.minecraftforge.common.AchievementPage;

public class AchievementPageHandler {

 static AchievementPage AchievementPageComputer = new AchievementPage("Computercraft", AchievementList.AchievementArray);
	public static void registerPages(){
		AchievementPage.registerAchievementPage(AchievementPageComputer);
	}
}
