package fr.dydy70310.lifeisperipheral.tile;

import java.util.HashMap;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import fr.dydy70310.lifeisperipheral.GUI.GUIHandler;
import fr.dydy70310.lifeisperipheral.Utils.Util;
import fr.dydy70310.lifeisperipheral.blocks.BlockAdminChest;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryBasic;
import net.minecraft.inventory.InventoryEnderChest;
import net.minecraft.inventory.IInvBasic;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.Packet;
import net.minecraft.network.play.server.S35PacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.IChatComponent;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.Constants.NBT;
import net.minecraftforge.fml.common.network.NetworkRegistry;

public class TileAdminChest extends TileEntity implements  IPeripheral, IInvBasic {

	public static class AdminChestRegistry {
		public static HashMap<TileAdminChest, Boolean> AdminChests = new HashMap<TileAdminChest, Boolean>();
		public static HashMap<IComputerAccess, HashMap> computers = new HashMap<IComputerAccess, HashMap>();
	}
	
	
	public static String[] methods = { "convertMoneyToNumber","convertNumberToMoney","lockId","lockSide","ChestSize"};
	  public BlockPos pos;
	  public World world;
	  public EnumFacing side;
	  public String customName = "AdminChest";
	  public int TotalCount = 27;
	  public InventoryBasic inventaire = new InventoryBasic(customName,true,27);
	  public boolean isLockedToId = false;
	  public int lockId = 0;
	  public boolean isLockedToSide = false;
	  public String lockSide = "back";
	  public boolean start = true;
	  
	@Override
	public String getType() {
		//this.inventaire.addInventoryChangeListener(this);
		return "AdminChest";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
		TileEntity backBlock =  world.getTileEntity(Util.blockside(this.pos, this.side, lockSide));
		
		boolean execValide = true;
		if (isLockedToId == true)  {
			if (lockId == computer.getID()) {
				if (isLockedToSide == true) {
					if (backBlock != null && backBlock.serializeNBT().getInteger("computerID") == computer.getID()) {
						execValide = true;
					}
					else
					{
						execValide = false;
					}
				}
				
			}
			else
			{
				execValide = false;
			}
		}
		else
		{
			if (isLockedToSide == true) {
				if (backBlock != null && backBlock.serializeNBT().getInteger("computerID") == computer.getID()) {
					execValide = true;
				}
				else
				{
					execValide = false;
				}
			}
		}
		
		if (execValide == true)  {
			switch (method){
	        	case 0:
	        		return new Object[] {"ok"};
	        	case 1:
	        		return new Object[] {"ok2"};
	        	case 2:
	        		if (arguments.length >= 2) {
	        			if (arguments[0] instanceof Double && arguments[1] instanceof Boolean) {
	        				isLockedToId = (Boolean)arguments[1];
	        				lockId = ((Double)arguments[0]).intValue();
	        				this.markDirty();
	        				return new Object[] {true, null};
	        			}
	        			else
	        			{
	        				return new Object[] {false,"lockId(Number, Boolean)",isLockedToId};
	        			}
	        		}
	        		else
	        		{
	        			return new Object[] {false,"lockId(ComputerID, Activer)"};
	        		}
	        		
	        	case 3:
	        		
	        		if (arguments.length >= 2) {
	        			if (arguments[0] instanceof String && arguments[1] instanceof Boolean) {
	        				if (Util.checkside((String)arguments[0]) != null) {
		        				isLockedToSide = (Boolean)arguments[1];
		        				lockSide = (String)arguments[0];
		        				this.markDirty();
		        				return new Object[] {true,null};
	        				}
	        				else
	        				{
	        					return new Object[] {false,"Side must be a valid side like (left, right, top, bottom, front, back)"};
	        				}
	        			}
	        			else
	        			{
	        				return new Object[] {false,"lockSide(String, Boolean)"};
	        			}
	        		}
	        		else
	        		{
	        			return new Object[] {false,"lockSide(Side, Activer)"};
	        		}
	        		
	        	case 4:
	        		if (arguments.length >= 1) {
	        			if (arguments[0] instanceof Double) {
	        				int SlotCount = ((Double) arguments[0]).intValue();
	        				InventoryBasic newInventaire = new InventoryBasic(this.inventaire.getName(), true, SlotCount);
	        				int SlotMini = Math.min(SlotCount, inventaire.getSizeInventory());
	        				for (int i=0; i < SlotMini; i++) {
	        					newInventaire.setInventorySlotContents(i, this.inventaire.getStackInSlot(i));
	        				}
	        			//	newInventaire.addInventoryChangeListener(this);
	        				this.inventaire = newInventaire;
	        				this.TotalCount = SlotCount;
	        				this.updateContainingBlockInfo();
	        				this.markDirty();
	        			}
	        			else
	        			{
	        				return new Object[] {false,"ChestSize(Number)"};
	        			}
	        		}
	        		else
	        		{
	        			return new Object[] {false,"ChestSize(SlotCount)"};	
	        		}
				}	
			}	
		return null;
		
	}
		


	
	@Override
	public void attach(IComputerAccess computer) {
		//MainAddonCC.LOGGER.info("ATTACH");
		
		HashMap infos = new HashMap();
		infos.put("pos", this.pos);
		infos.put("id", computer.getID());
		infos.put("AdminChests", this);
		boolean valide = true;
		for (TileAdminChest AdminChest : AdminChestRegistry.AdminChests.keySet()) {
			if (AdminChest.equals(this)) {
				valide = false;
				break;
			}
		}
		if (valide) {
			AdminChestRegistry.AdminChests.put(this, true);
			AdminChestRegistry.computers.put(computer, infos);
		}
		
	}
	
	  @Override
	   public void writeToNBT(NBTTagCompound nbt)
	   {
		  if (this.start == false) {
			  super.writeToNBT(nbt);
				NBTTagList items = new NBTTagList();
	
				for (int i = 0; i < this.inventaire.getSizeInventory(); ++i)
				{
					ItemStack itemstack = this.inventaire.getStackInSlot(i);
	
					if (itemstack != null)
					{
						NBTTagCompound nbttagcompound1 = new NBTTagCompound();
						nbttagcompound1.setByte("Slot", (byte)i);
						itemstack.writeToNBT(nbttagcompound1);
						items.appendTag(nbttagcompound1);
					}
				}
				
				nbt.setInteger("SlotCount", this.TotalCount);
				nbt.setBoolean("isLockedToId", this.isLockedToId);
				nbt.setBoolean("isLockedToSide", this.isLockedToSide);
				nbt.setString("lockSide", this.lockSide);
				nbt.setInteger("lockId", this.lockId);
				nbt.setString("Name", this.customName);
				nbt.setTag("Items", items);
				
				System.out.println("SlotCount : " + TotalCount);
				System.out.println("isLockedToId : " + isLockedToId);
				System.out.println("isLockedToSide : " + isLockedToSide);
				System.out.println("lockSide : " + lockSide);
				System.out.println("lockId : " + lockId);
				System.out.println("Name : " + customName);
		  }
		  this.start = false;
	   }
	  
		@Override
		public void readFromNBT(NBTTagCompound nbt) {
			super.readFromNBT(nbt);
			
			NBTTagList items = nbt.getTagList("Items", nbt.getId());
			
			for (int i = 0; i < items.tagCount(); ++i) {
				NBTTagCompound item = items.getCompoundTagAt(i);
				byte slot = item.getByte("Slot");
				if (slot >= 0 && slot < this.inventaire.getSizeInventory()) {
					this.inventaire.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(item));
				}
			}
			
			this.TotalCount = nbt.getInteger("SlotCount");
			this.isLockedToId = nbt.getBoolean("isLockedToId");
			this.isLockedToSide = nbt.getBoolean("isLockedToSide");
			this.lockSide = nbt.getString("lockSide");
			this.lockId = nbt.getInteger("lockId");
			this.customName = nbt.getString("Name");
			
			System.out.println("SlotCount : " + this.TotalCount);
			System.out.println("isLockedToId : " + this.isLockedToId);
			System.out.println("isLockedToSide : " + this.isLockedToSide);
			System.out.println("lockSide : " + this.lockSide);
			System.out.println("lockId : " + this.lockId);
			System.out.println("Name : " + this.customName);
		}
	  
	  @Override
	  public Packet getDescriptionPacket() {
	  NBTTagCompound tag = new NBTTagCompound();
	  this.writeToNBT(tag);
	  return new S35PacketUpdateTileEntity(this.getPos(), 0, tag);
	  }
	  	
	  @Override
	  public void onDataPacket(NetworkManager net, S35PacketUpdateTileEntity packet) {
	  readFromNBT(packet.getNbtCompound());
	  }
	
	@Override
	public void detach(IComputerAccess computer) {
	//	MainAddonCC.LOGGER.info("DETACH");
		AdminChestRegistry.AdminChests.remove(this);
		AdminChestRegistry.computers.remove(computer);
	}
	
	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

	@Override
	public void onInventoryChanged(InventoryBasic inv) {
		this.inventaire = inv;
	}

	public Boolean getIsLockedToId() {
		return this.isLockedToId;
	}


}