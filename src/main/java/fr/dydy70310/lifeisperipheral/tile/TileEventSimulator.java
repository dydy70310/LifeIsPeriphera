package fr.dydy70310.lifeisperipheral.tile;

import java.util.ArrayList;
import java.util.HashMap;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import fr.dydy70310.lifeisperipheral.Utils.Util;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileEventSimulator extends TileEntity implements IPeripheral {

	public static class EventSimulatorRegistry {
		public static HashMap<TileEventSimulator, Boolean> EventSimulators = new HashMap<TileEventSimulator, Boolean>();
		public static HashMap<IComputerAccess, HashMap> computers = new HashMap<IComputerAccess, HashMap>();
	}
	
	public static String[] methods = { "callEvent"};
	  public BlockPos pos;
	  public World world;
	  public EnumFacing side;
	  
	@Override
	public String getType() {
		return "EventSimulator";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {

		switch (method){
        	case 0:
        		if (arguments.length == 2) {
        			if (arguments[0] instanceof String && arguments[1] instanceof String){
        				BlockPos npos = Util.blockside(this.pos, this.side, (String)arguments[0]);
        				if (npos != null) {
        					
        					TileEntity block = this.world.getTileEntity(npos);
        					if (block != null) {
        						
        						Block bloc = block.getWorld().getBlockState(npos).getBlock();
        						
        						 if (bloc.getRegistryName().equals("computercraft:CC-Turtle")||bloc.getRegistryName().equals("computercraft:CC-Computer")||bloc.getRegistryName().equals("computercraft:command_computer")||bloc.getRegistryName().equals("computercraft:CC-TurtleExpanded")||bloc.getRegistryName().equals("computercraft:CC-TurtleAdvanced")) 
        						  {
        							 
        							Integer id = block.serializeNBT().getInteger("computerID");
     		        				for (IComputerAccess computerEntry : EventSimulatorRegistry.computers.keySet()) {
    		        					Integer registerID = (Integer)EventSimulatorRegistry.computers.get(computerEntry).get("id");
    		        					if (id.equals(registerID)) {
    		        						computerEntry.queueEvent((String)arguments[1], new Object[] {});
    		        						
    		        						break;
    		        					}
    		        				}
        						 }
        					}
        				}

        			}
        			else
        			{
        				return new Object[] {"callEvent(String, String, [String])"};
        			}
        		}
        		else if (arguments.length > 2)
        		{
        			
        		
        			if (arguments[0] instanceof String && arguments[1] instanceof String){
        				BlockPos npos = Util.blockside(this.pos, this.side, (String)arguments[0]);
        				if (npos != null) {
        					
        					TileEntity block = this.world.getTileEntity(npos);
        					if (block != null) {
        						
        						Block bloc = block.getWorld().getBlockState(npos).getBlock();
        						
        						 if (bloc.getRegistryName().equals("computercraft:CC-Turtle")||bloc.getRegistryName().equals("computercraft:CC-Computer")||bloc.getRegistryName().equals("computercraft:command_computer")||bloc.getRegistryName().equals("computercraft:CC-TurtleExpanded")||bloc.getRegistryName().equals("computercraft:CC-TurtleAdvanced")) 
        						  {
        							 
        							Integer id = block.serializeNBT().getInteger("computerID");
     		        				for (IComputerAccess computerEntry : EventSimulatorRegistry.computers.keySet()) {
    		        					Integer registerID = (Integer)EventSimulatorRegistry.computers.get(computerEntry).get("id");
    		        					if (id.equals(registerID)) {
    		        						
    		        						ArrayList liste = new ArrayList();
    		        						for (int j=2; j < arguments.length; j++) {
    		        							liste.add(arguments[j]);
    		        						}
    		        						
    		        					 Object[] objet = liste.toArray();
    		        						
    		        						computerEntry.queueEvent((String)arguments[1], objet);
    		        						
    		        						
    		        						break;
    		        					}
    		        				}
        						 }
        					}
        				}

        			}
        			
        		}
        		else
        		{
        			return new Object[] {"callEvent(Side, EventName, [Arugments])"};
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
		infos.put("EventSimulators", this);
		
		boolean valide = true;
		for (TileEventSimulator EventSimulator : EventSimulatorRegistry.EventSimulators.keySet()) {
			HashMap n = new HashMap();
			n.put(this, true);
			if (EventSimulator.equals(n)) {
				valide = false;
				break;
			}
		}
		if (valide) {
			EventSimulatorRegistry.EventSimulators.put(this, true);
			EventSimulatorRegistry.computers.put(computer, infos);
		}
		
	}
	
	@Override
	public void detach(IComputerAccess computer) {
	//	MainAddonCC.LOGGER.info("DETACH");
		EventSimulatorRegistry.EventSimulators.remove(this);
		EventSimulatorRegistry.computers.remove(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}
	

}