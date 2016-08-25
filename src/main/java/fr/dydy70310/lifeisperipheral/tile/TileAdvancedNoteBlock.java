package fr.dydy70310.lifeisperipheral.tile;

import java.util.HashMap;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import fr.dydy70310.lifeisperipheral.register.BlockMods;
import net.minecraft.block.material.Material;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.world.World;

public class TileAdvancedNoteBlock extends TileEntity implements IPeripheral {

	public static class AdvancedNoteBlockRegistry {
		public static HashMap<TileAdvancedNoteBlock, Boolean> advancedNoteBlocks = new HashMap<TileAdvancedNoteBlock, Boolean>();
		public static HashMap<IComputerAccess, Boolean> computers = new HashMap<IComputerAccess, Boolean>();
	}

	public static  String[] methods = {"setPitch","triggerNote","setInstrument","getPitch","getInstrument","setVolume","getVolume"};
	public BlockPos pos;
	public World world;
	public EnumFacing side;
	public byte currentNote = 0;
	public byte currentInstrument = 0;
	public byte currentVolume = 3;
	  
	@Override
	public String getType() {
		return "AdvancedNoteBlock";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
        switch(method){
	        case 0:
	            if (arguments.length!= 1){
	                return new Object[]{"Usage noteblock.setPitch(number)"};
	            }else{
	                if ( arguments[0] instanceof Double){
	                	Double arg = (Double) arguments[0];
	                	int range = arg.intValue();
	                	if (range >= 0 || range <= 24 ){
	                		currentNote = (byte)range;
	                		break;
	                	}else{
	                		return new Object[]{"Usage noteblock.setPitch(integer from 0 to 24)"};
	                	}
	                }else{
	                    return new Object[]{"Usage noteblock.setPitch(integer from 0 to 24)"};
	                }
	            }
	        case 1:
	        	triggerNote(this.world,this.pos);
	        	return new Object[]{true};
	        case 2:
	        	if (arguments.length!= 1){
	                return new Object[]{"Usage noteblock.setInstrument(number)"};
	            }else{
	                if (arguments[0] instanceof Double){
	                	Double arg = (Double) arguments[0];
	                    int range =  arg.intValue();
	                    if (range>=0 && range <= 4) {
	                    	currentInstrument = (byte)range;
	                    	break;
	                    }else{
	                    	return new Object[]{"Usage noteblock.Instrument(integer from 0 to 4)"};
	                    }
	                }else{
	                    return new Object[]{"Usage noteblock.Instrument(integer from 0 to 4)"};
	                }
	            }
	        case 3:
	        	return new Object[]{currentNote};
	        case 4:
	        	return new Object[]{currentInstrument};
	        case 5:
	        	if(arguments.length != 1){
	        		return new Object[]{"Usage noteblock.setVolume(number)"};
	        	}else{
	        		if (arguments[0] instanceof Double){
	        			Double arg = (Double) arguments[0];
	        			int range = arg.intValue();
	        			if (range>=0 && range < 10){
	        				currentVolume = (byte) range;
	        				break;
	        			}else{
	        				return new Object[]{"Usage noteblock.setVolume(number from 0 to 9 included)"};	
	        			}
	        		}else{
	        			return new Object[]{"Usage noteblock.setVolume(number from 0 to 9 included)"};
	        		}
	        	}
	        case 6:
	        	return new Object[]{currentVolume};
        }
        	return null;
	}
	@Override
	public void attach(IComputerAccess computer) {
		//MainAddonCC.LOGGER.info("ATTACH");
		AdvancedNoteBlockRegistry.advancedNoteBlocks.put(this, true);
		AdvancedNoteBlockRegistry.computers.put(computer, true);
		
	}

	@Override
	public void detach(IComputerAccess computer) {
	//	MainAddonCC.LOGGER.info("DETACH");
		AdvancedNoteBlockRegistry.advancedNoteBlocks.remove(this);
		AdvancedNoteBlockRegistry.computers.remove(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

	public void triggerNote(World worldIn, BlockPos pos){
		if (worldIn.getBlockState(pos.offset(side)).getBlock().getMaterial() == Material.air){
			int param;
			param = currentNote*10+currentVolume;
			worldIn.addBlockEvent(pos, BlockMods.AdvancedNoteBlock, currentInstrument, param);
			
		}
	}
	

	
}

