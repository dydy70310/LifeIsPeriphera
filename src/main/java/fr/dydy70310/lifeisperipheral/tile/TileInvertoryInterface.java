package fr.dydy70310.lifeisperipheral.tile;

import java.util.HashMap;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import fr.dydy70310.lifeisperipheral.Utils.Util;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityChest;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileInvertoryInterface extends TileEntity implements IPeripheral {

	public static class InvertoryInterfaceRegistry {
		public static HashMap<TileInvertoryInterface, Boolean> invertoryInterfaces = new HashMap<TileInvertoryInterface, Boolean>();
		public static HashMap<IComputerAccess, Boolean> computers = new HashMap<IComputerAccess, Boolean>();
	}
	
	public static String[] methods = { "getItemsInInventory","getItemInSlot","getInventorySize","getInventoryType","getMethods"};
	  public BlockPos pos;
	  public World world;
	  public EnumFacing side;
	  
	@Override
	public String getType() {
		return "InventoryInterface";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
		
		switch (method){
		    case 0:{ 
		    	BlockPos bpos = Util.poswithfacing(pos, side);
				TileEntity tile = world.getTileEntity(bpos);
				EnumFacing side = EnumFacing.NORTH;
				if (tile != null){
					if (tile instanceof TileEntityChest) {
						TileEntityChest  chest = (TileEntityChest) tile;
						TileEntityChest chest2 = null;
						chest.checkForAdjacentChests();
						if(chest.adjacentChestXPos != null){
							chest2 = chest.adjacentChestXPos;
							side = EnumFacing.EAST;
							//chest1
						}
						else if(chest.adjacentChestXNeg != null){
							chest2 = chest.adjacentChestXNeg;
							side = EnumFacing.WEST;
							//chest2
						}
						else if(chest.adjacentChestZPos != null){
							chest2 = chest.adjacentChestZPos;
							side = EnumFacing.SOUTH;
							//chest1
						}
						else if(chest.adjacentChestZNeg != null){
							chest2 = chest.adjacentChestZNeg;
							side = EnumFacing.NORTH;
							//chest2
						}
						
						HashMap infoInv = new HashMap();
						if (chest2 == null){
							for(int j = 0;j < chest.getSizeInventory() ;j++){
			    				ItemStack InventoryInfos = chest.getStackInSlot(j);
			    				HashMap StackInfos = Util.getInfo(InventoryInfos,j + 1);
				    			infoInv.put(j+1,StackInfos);
			    			}
							return new Object[] {infoInv};
						}else{
							if(side == EnumFacing.EAST || side == EnumFacing.SOUTH){
								for(int j = 0;j < (chest.getSizeInventory() + chest2.getSizeInventory()) ;j++){
									ItemStack InventoryInfos = null;
									if(j < 27){
										InventoryInfos = chest.getStackInSlot(j);
									}
									if(j >= 27){
										InventoryInfos = chest2.getStackInSlot(chest.getSizeInventory() - j);
									}
				    				HashMap StackInfos = Util.getInfo(InventoryInfos,j + 1);
					    			infoInv.put(j+1,StackInfos);
				    			}
							}else{
								TileEntityChest chest3 = null;
								chest3 = chest;
								chest = chest2;
								chest2 = chest3;
								
								for(int j = 0;j < (chest.getSizeInventory() + chest2.getSizeInventory()) ;j++){
									ItemStack InventoryInfos = null;
									if(j <= chest.getSizeInventory()){
										InventoryInfos = chest.getStackInSlot(j);
									}
									if(j > chest.getSizeInventory()){
										InventoryInfos = chest2.getStackInSlot(chest.getSizeInventory() - j);
									}
				    				HashMap StackInfos = Util.getInfo(InventoryInfos,j + 1);
					    			infoInv.put(j+1,StackInfos);
				    			}
							}
							return new Object[] {infoInv};
						}
					}
					return null;
				}
				return null;
		    }
		    
		    case 1:{
		    	BlockPos bpos = Util.poswithfacing(pos, side);
				TileEntity tile = world.getTileEntity(bpos);
				EnumFacing side = EnumFacing.NORTH;
				if(arguments.length == 1){
					if (arguments[0] instanceof Double) {
						if (tile != null){
							
							if (tile instanceof TileEntityChest) {
								TileEntityChest  chest = (TileEntityChest) tile;
								TileEntityChest chest2 = null;
								chest.checkForAdjacentChests();
								if(chest.adjacentChestXPos != null){
									chest2 = chest.adjacentChestXPos;
									side = EnumFacing.EAST;
									//chest1
								}
								else if(chest.adjacentChestXNeg != null){
									chest2 = chest.adjacentChestXNeg;
									side = EnumFacing.WEST;
									//chest2
								}
								else if(chest.adjacentChestZPos != null){
									chest2 = chest.adjacentChestZPos;
									side = EnumFacing.SOUTH;
									//chest1
								}
								else if(chest.adjacentChestZNeg != null){
									chest2 = chest.adjacentChestZNeg;
									side = EnumFacing.NORTH;
									//chest2
								}
								HashMap infoInv = new HashMap();
								Double s = (Double) arguments[0];

								if (chest2 == null){
									if(s >= 1 && s <= chest.getSizeInventory()){
										s = s-1;
										if(s >= 0 && s <= chest.getSizeInventory()){
											int slot = s.intValue();
											ItemStack InventoryInfos = chest.getStackInSlot(slot);
											HashMap StackInfos = Util.getInfo(InventoryInfos);
							    			return new Object[] {StackInfos};
										}else{
											return new Object[] {"This slot is out of bounds !"};
										}
									}else{
										String string = "This slot is out of bounds ! Please enter en valid slot between : 1 " + chest.getSizeInventory();
										return new Object[] {string};
									}
								}else{
									if(s >= 1 && s <= (chest.getSizeInventory() + chest2.getSizeInventory())){
										s = s-1;
										if(s >= 0 && s <= (chest.getSizeInventory() + chest2.getSizeInventory())){
											if(side == EnumFacing.EAST || side == EnumFacing.SOUTH){
												int slot = s.intValue();
												ItemStack InventoryInfos = null;
												if(slot < chest.getSizeInventory()){
													InventoryInfos = chest.getStackInSlot(slot);
												}else{
													slot = slot - chest2.getSizeInventory();
													InventoryInfos = chest2.getStackInSlot(slot);
												}
												HashMap StackInfos = Util.getInfo(InventoryInfos,slot);
								    			return new Object[] {StackInfos};
											}else{
												TileEntityChest chestuseless = null;
												chestuseless = chest;
												chest = chest2;
												chest2 = chestuseless;
												
												int slot = s.intValue();
												ItemStack InventoryInfos = null;
												
												if(slot < chest.getSizeInventory()){
													InventoryInfos = chest.getStackInSlot(slot);
												}else{
													slot = slot - chest2.getSizeInventory();
													InventoryInfos = chest2.getStackInSlot(slot);
												}
												HashMap StackInfos = Util.getInfo(InventoryInfos,slot);
								    			return new Object[] {StackInfos};
											}
										}else{
											return new Object[] {"This slot is out of bounds !"};
										}
									}else{
										String string = "This slot is out of bounds ! Please enter en valid slot between : 1 " + (chest.getSizeInventory() + chest2.getSizeInventory());
										return new Object[] {string};
									}
									
								}
							}
							return null;
						}
						return null;
					}else{
						return new Object[] {"getItemInSlot(Number)"};
					}
				}else{
					return new Object[] {"getItemInSlot(Slot)"};
				}
		    }
		    case 2:{
		    	BlockPos bpos = Util.poswithfacing(pos, side);
				TileEntity tile = world.getTileEntity(bpos);
				if (tile != null){
					
					if (tile instanceof TileEntityChest) {
						
						TileEntityChest  chest = (TileEntityChest) tile;
						TileEntityChest chest2 = null;
						chest.checkForAdjacentChests();
						if(chest.adjacentChestXPos != null){
							chest2 = chest.adjacentChestXPos;
						}
						else if(chest.adjacentChestXNeg != null){
							chest2 = chest.adjacentChestXNeg;
						}
						else if(chest.adjacentChestZPos != null){
							chest2 = chest.adjacentChestZPos;
						}
						else if(chest.adjacentChestZNeg != null){
							chest2 = chest.adjacentChestZNeg;
						}
						
						int size = chest.getSizeInventory();
						if(chest2 != null){
							size = size + chest2.getSizeInventory();
						}
						return new Object[] {size};
					}
					return null;
				}
				return null;
		    }
		    case 3:{
		    	BlockPos bpos = Util.poswithfacing(pos, side);
				TileEntity tile = world.getTileEntity(bpos);
				if (tile != null) {
					return new Object[] {tile.getClass().getSimpleName()};
				}
				return new Object[] {};
		    }
		    case 4:{
				if (arguments.length > 0) {
					if (arguments[0] instanceof String) {
						if (arguments[0].equals("getItemsInInventory")) {
							return new Object[] {"getItemsInInventory()","This function return a table of all container slots with datas about the slots"};
						}
						if (arguments[0].equals("getItemInSlot")) {
							return new Object[] {"getItemInSlot(Slot)","getItemInSlot(Number)","This function return a data table of one container slot in particular"};
						}
						if (arguments[0].equals("getInventorySize")) {
							return new Object[] {"getInventorySize()","This function return the number of total container slots"};
						}
						if (arguments[0].equals("getInventorySize")) {
							return new Object[] {"getInventorySize()","This function return the number of total container slots"};
						}
						if (arguments[0].equals("getInventoryType")) {
							return new Object[] {"getInventoryType()","This function return the name of the container type"};
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
					return methods;
				}
		    }
		}
		
			
		return null;
	}


	
	@Override
	public void attach(IComputerAccess computer) {
		InvertoryInterfaceRegistry.invertoryInterfaces.put(this, true);
		InvertoryInterfaceRegistry.computers.put(computer, true);
		
	}
	
	@Override
	public void detach(IComputerAccess computer) {
		InvertoryInterfaceRegistry.invertoryInterfaces.remove(this);
		InvertoryInterfaceRegistry.computers.remove(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}
	
}