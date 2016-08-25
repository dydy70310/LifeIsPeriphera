package fr.dydy70310.lifeisperipheral.proxy;

import fr.dydy70310.lifeisperipheral.Reference;
import fr.dydy70310.lifeisperipheral.register.BlockMods;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderItem;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class ClientProxy extends CommonProxy{

	@Override
	public void registerRenders(){
		

		BlockMods.registerRenders();
		
		//EntityDetector
		registerBlockTexture(BlockMods.EntityDetector, "EntityDetector");
	    Item itemBlockEntityDetector= GameRegistry.findItem("lifeisperipheral", "EntityDetector");
	    ModelResourceLocation EntityDetectorModelResourceLocation = new ModelResourceLocation(Reference.ModID + ":EntityDetector", "inventory");
	    ModelLoader.setCustomModelResourceLocation(itemBlockEntityDetector, 0, EntityDetectorModelResourceLocation);
	    
		//AdvancedNoteBlock
		/*registerBlockTexture(BlockMods.AdvancedNoteBlock, "AdvancedNoteBlock");
	    Item itemBlockAdvancedNoteBlock = GameRegistry.findItem("lifeisperipheral", "AdvancedNoteBlock");
	    ModelResourceLocation AdvancedNoteBlockModelResourceLocation = new ModelResourceLocation(Reference.ModID + ":AdvancedNoteBlock", "inventory");
	    ModelLoader.setCustomModelResourceLocation(itemBlockAdvancedNoteBlock, 0, AdvancedNoteBlockModelResourceLocation);*/
	    
		//ChatInterface
		registerBlockTexture(BlockMods.ChatInterface, "ChatInterface");
	    Item itemBlockChatInterface = GameRegistry.findItem("lifeisperipheral", "ChatInterface");
	    ModelResourceLocation ChatInterfaceModelResourceLocation = new ModelResourceLocation(Reference.ModID + ":ChatInterface", "inventory");
	    ModelLoader.setCustomModelResourceLocation(itemBlockChatInterface, 0, ChatInterfaceModelResourceLocation);
	    
		//WorldInterface
		registerBlockTexture(BlockMods.WorldInterface, "WorldInterface");
	    Item itemBlockWorldInterface = GameRegistry.findItem("lifeisperipheral", "WorldInterface");
	    ModelResourceLocation WorldInterfaceModelResourceLocation = new ModelResourceLocation(Reference.ModID + ":WorldInterface", "inventory");
	    ModelLoader.setCustomModelResourceLocation(itemBlockWorldInterface, 0, WorldInterfaceModelResourceLocation);
	    
		//ChestInterface
		registerBlockTexture(BlockMods.InventoryInterface, "InventoryInterface");
	    Item itemBlockInventoryInterface = GameRegistry.findItem("lifeisperipheral", "InventoryInterface");
	    ModelResourceLocation InventoryInterfaceModelResourceLocation = new ModelResourceLocation(Reference.ModID + ":InventoryInterface", "inventory");
	    ModelLoader.setCustomModelResourceLocation(itemBlockInventoryInterface, 0, InventoryInterfaceModelResourceLocation);
	    
		//EventSimulator
		/*registerBlockTexture(BlockMods.EventSimulator, "EventSimulator");
	    Item itemBlockEventSimulator = GameRegistry.findItem("lifeisperipheral", "EventSimulator");
	   ModelResourceLocation EventSimulatorModelResourceLocation = new ModelResourceLocation(Reference.ModID + ":EventSimulator", "inventory");
	    ModelLoader.setCustomModelResourceLocation(itemBlockEventSimulator, 0, EventSimulatorModelResourceLocation);*/
	    
		//EventSimulator
		/*registerBlockTexture(BlockMods.AdminChest, "AdminChest");
	    Item itemBlockAdminChest = GameRegistry.findItem("lifeisperipheral", "AdminChest");
	    ModelResourceLocation AdminChestModelResourceLocation = new ModelResourceLocation(Reference.ModID + ":AdminChest", "inventory");
	    ModelLoader.setCustomModelResourceLocation(itemBlockAdminChest, 0, AdminChestModelResourceLocation);*/
	  }
	
    @Override
    public void registerBlockTexture(final Block block, final String blockName) {
        registerBlockTexture(block, blockName, 0);
    }
    
    
    

    @Override
    public void registerBlockTexture(final Block block, final String blockName, int meta) {
        RenderItem renderItem = Minecraft.getMinecraft().getRenderItem();
        renderItem.getItemModelMesher().register(Item.getItemFromBlock(block), meta, new ModelResourceLocation(Reference.ModID + ":" + blockName,"inventory"));
    }

}
