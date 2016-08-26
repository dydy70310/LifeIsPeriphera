package fr.dydy70310.lifeisperipheral.register;

import dan200.computercraft.ComputerCraft;
import fr.dydy70310.lifeisperipheral.MainLIP;
import fr.dydy70310.lifeisperipheral.Reference;
import fr.dydy70310.lifeisperipheral.blocks.BlockChatInterface;
import fr.dydy70310.lifeisperipheral.blocks.BlockEntityDetector;
import fr.dydy70310.lifeisperipheral.blocks.BlockEventSimulator;
import fr.dydy70310.lifeisperipheral.blocks.BlockInventoryInterface;
import fr.dydy70310.lifeisperipheral.blocks.BlockWorldInterface;
import fr.dydy70310.lifeisperipheral.tile.TileChatInterface;
import fr.dydy70310.lifeisperipheral.tile.TileEntityDetector;
import fr.dydy70310.lifeisperipheral.tile.TileInventoryInterface;
import fr.dydy70310.lifeisperipheral.tile.TileWorldInterface;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.model.ModelResourceLocation;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.common.registry.GameRegistry;

public class BlockMods {

	
	public static Block EntityDetector;
	public static Block AdvancedNoteBlock;
	public static Block ChatInterface;
	public static Block WorldInterface;
	public static Block InventoryInterface;
	public static Block EventSimulator;
	public static Block AdminChest;
	public static void init(){
		
		EntityDetector = new BlockEntityDetector().setUnlocalizedName("EntityDetector").setCreativeTab(MainLIP.LifeIsPeripheral).setHardness(4.5F);
		//AdvancedNoteBlock = new BlockAdvancedNoteBlock().setUnlocalizedName("AdvancedNoteBlock").setCreativeTab(MainLIP.LifeIsPeripheral).setHardness(4.5F);
		ChatInterface = new BlockChatInterface().setUnlocalizedName("ChatInterface").setCreativeTab(MainLIP.LifeIsPeripheral).setHardness(4.5F);
		WorldInterface = new BlockWorldInterface().setUnlocalizedName("WorldInterface").setCreativeTab(MainLIP.LifeIsPeripheral).setHardness(4.5F);
		InventoryInterface = new BlockInventoryInterface().setUnlocalizedName("InventoryInterface").setCreativeTab(MainLIP.LifeIsPeripheral).setHardness(4.5F);
		EventSimulator = new BlockEventSimulator().setUnlocalizedName("EventSimulator").setCreativeTab(MainLIP.LifeIsPeripheral).setHardness(4.5F);
		//AdminChest = new BlockAdminChest().setUnlocalizedName("AdminChest").setCreativeTab(MainLIP.LifeIsPeripheral).setHardness(4.5F);
	}
	
	public static void register(){
		//EntityDetector
		GameRegistry.registerBlock(EntityDetector,EntityDetector.getUnlocalizedName().substring(5));
		GameRegistry.addRecipe(new ItemStack(BlockMods.EntityDetector,1),new Object[] {"%%%","KCS","%%%",'K', Items.ender_eye ,'C', ComputerCraft.Blocks.computer, 'S', Items.sign, '%', Items.gold_ingot} );
		GameRegistry.registerTileEntity(TileEntityDetector.class, "EntityDetector");
		
		//AdvancedNoteBlock
		/*GameRegistry.registerBlock(AdvancedNoteBlock,AdvancedNoteBlock.getUnlocalizedName().substring(5));
		GameRegistry.addRecipe(new ItemStack(BlockMods.AdvancedNoteBlock,1),new Object[] {"###","#T#","###",'#', Items.gold_ingot ,'T', Blocks.noteblock} );
		GameRegistry.registerTileEntity(TileAdvancedNoteBlock.class, "AdvancedNoteBlock");*/

		//ChatInterface
		GameRegistry.registerBlock(ChatInterface,ChatInterface.getUnlocalizedName().substring(5));
		GameRegistry.addRecipe(new ItemStack(BlockMods.ChatInterface,1),new Object[] {"%%%","PTP","%%%",'T', ComputerCraft.Blocks.computer, 'P', Items.sign, '%', Items.gold_ingot} );
		GameRegistry.registerTileEntity(TileChatInterface.class, "ChatInterface");

		//WorldInterface
		GameRegistry.registerBlock(WorldInterface,WorldInterface.getUnlocalizedName().substring(5));
		GameRegistry.addRecipe(new ItemStack(BlockMods.WorldInterface,1),new Object[] {"%C%","STS","%H%",'C', Items.compass ,'T', ComputerCraft.Blocks.turtle, 'S', Items.sign, '%', Items.gold_ingot, 'H', Items.clock} );
		GameRegistry.registerTileEntity(TileWorldInterface.class, "WorldInterface");
		
		//ChestInterface
		GameRegistry.registerBlock(InventoryInterface,InventoryInterface.getUnlocalizedName().substring(5));
		GameRegistry.addRecipe(new ItemStack(BlockMods.InventoryInterface,1),new Object[] {"%%%","CTS","%%%",'C', Blocks.chest ,'T', ComputerCraft.Blocks.turtle, 'S', Items.sign, '%', Items.gold_ingot} );
		GameRegistry.registerTileEntity(TileInventoryInterface.class, "InventoryInterface");
		
		//EventSimulator
		/*GameRegistry.registerBlock(EventSimulator,EventSimulator.getUnlocalizedName().substring(5));
		GameRegistry.addRecipe(new ItemStack(BlockMods.EventSimulator,1),new Object[] {"%C%","CPC","%B%",'C', ComputerCraft.Blocks.cable ,'P', ComputerCraft.Blocks.computer, 'B', Items.book, '%', Items.gold_ingot} );
		GameRegistry.registerTileEntity(TileEventSimulator.class, "EventSimulator");*/
		
		//AdminChest
		/*GameRegistry.registerBlock(AdminChest,AdminChest.getUnlocalizedName().substring(5));
		GameRegistry.registerTileEntity(TileAdminChest.class, "AdminChest");*/
		
	}
	
	public static void registerRenders(){
		registerRender(EntityDetector);
		//registerRender(AdvancedNoteBlock);
		registerRender(ChatInterface);
		registerRender(WorldInterface);
		registerRender(InventoryInterface);
		//registerRender(EventSimulator);
	}
	
	 public static void registerRender(Block block){
		 
		 Item item = Item.getItemFromBlock(block);
		 Minecraft.getMinecraft().getRenderItem().getItemModelMesher().register(item, 0,new ModelResourceLocation(Reference.ModID + ":" + item.getUnlocalizedName().substring(5),"inventory"));
		 
	 }
	
}
