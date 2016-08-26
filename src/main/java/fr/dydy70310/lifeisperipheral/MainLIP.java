package fr.dydy70310.lifeisperipheral;

import org.apache.logging.log4j.Logger;

import dan200.computercraft.api.ComputerCraftAPI;
import fr.dydy70310.lifeisperipheral.GUI.GUIHandler;
import fr.dydy70310.lifeisperipheral.blocks.BlockChatInterface;
import fr.dydy70310.lifeisperipheral.blocks.BlockEntityDetector;
import fr.dydy70310.lifeisperipheral.blocks.BlockInventoryInterface;
import fr.dydy70310.lifeisperipheral.blocks.BlockWorldInterface;
import fr.dydy70310.lifeisperipheral.config.ConfigHandler;
import fr.dydy70310.lifeisperipheral.event.CraftingAchievementTrigger;
import fr.dydy70310.lifeisperipheral.event.DroppingAchievementTrigger;
import fr.dydy70310.lifeisperipheral.misc.AchievementPageHandler;
import fr.dydy70310.lifeisperipheral.proxy.CommonProxy;
import fr.dydy70310.lifeisperipheral.register.BlockMods;
import fr.dydy70310.lifeisperipheral.register.LifeIsPeripheralEventHandler;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@Mod(modid = Reference.ModID, name = Reference.ModMame, version = Reference.ModVersion, dependencies = "required-after:ComputerCraft")

public class MainLIP {
	@Instance(Reference.ModID)
	public static MainLIP instance;
	@SidedProxy(clientSide = Reference.ClientProxyClass, serverSide = Reference.ServeurProxyClass)
	public static CommonProxy proxy;
	public static Logger LOGGER;
	public static CreativeTabs LifeIsPeripheral = new CreativeTabs("LifeIsPeripheral") {

		@SideOnly(Side.CLIENT)
		public Item getTabIconItem() {
			Item items = Item.getItemFromBlock(BlockMods.EntityDetector);
			return items;
		}
	};

	@EventHandler
	public void preInit(FMLPreInitializationEvent event) {
		LOGGER = event.getModLog();
		ConfigHandler.init(event.getSuggestedConfigurationFile());
	}

	@EventHandler
	public void init(FMLInitializationEvent event) {

		ComputerCraftAPI.registerPeripheralProvider(new BlockEntityDetector());
		ComputerCraftAPI.registerPeripheralProvider(new BlockChatInterface());
		ComputerCraftAPI.registerPeripheralProvider(new BlockWorldInterface());
		ComputerCraftAPI.registerPeripheralProvider(new BlockInventoryInterface());
		MinecraftForge.EVENT_BUS.register(new LifeIsPeripheralEventHandler());
		// ComputerCraftAPI.registerPeripheralProvider(new BlockEventSimulator());
		// ComputerCraftAPI.registerPeripheralProvider(new BlockAdminChest());
		AchievementPageHandler.registerPages();
		MinecraftForge.EVENT_BUS.register(new DroppingAchievementTrigger());
		MinecraftForge.EVENT_BUS.register(new CraftingAchievementTrigger());

		NetworkRegistry.INSTANCE.registerGuiHandler(this, new GUIHandler());

		BlockMods.init();
		BlockMods.register();
		proxy.registerRenders();

	}

	@EventHandler
	public void postInit(FMLPostInitializationEvent event) {

	}
}
