package fr.dydy70310.lifeisperipheral.GUI;

import fr.dydy70310.lifeisperipheral.tile.TileAdminChest;
import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ContainerChest;
import net.minecraft.inventory.IInventory;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GUIHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		// TODO Auto-generated method stub
		 TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z)); 
		 if(tileEntity instanceof TileAdminChest){
			 return new ContainerChest(player.inventory,((TileAdminChest)tileEntity).inventaire, player);
			 } 
		 return null; 
		 }

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		TileEntity tileEntity = world.getTileEntity(new BlockPos(x,y,z)); 
		if(tileEntity instanceof TileAdminChest){
			return new GuiChest(player.inventory,((TileAdminChest)tileEntity).inventaire);
			} 
		return null;
	}

}
