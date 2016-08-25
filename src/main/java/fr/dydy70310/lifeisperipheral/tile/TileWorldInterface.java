package fr.dydy70310.lifeisperipheral.tile;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;

import org.apache.commons.lang3.math.NumberUtils;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.shared.util.NBTUtil;
import fr.dydy70310.lifeisperipheral.Utils.Util;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.server.MinecraftServer;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeGenBase;
import net.minecraftforge.common.util.Constants.NBT;

public class TileWorldInterface extends TileEntity implements IPeripheral {

	public static class WorldInterfaceRegistry {
		public static HashMap<TileWorldInterface, Boolean> worldInterfaces = new HashMap<TileWorldInterface, Boolean>();
		public static HashMap<IComputerAccess, HashMap> computers = new HashMap<IComputerAccess, HashMap>();
	}
	
	public static String[] methods = { "getBiome","getWeather","getBlockInfos","setListeningRange","getRealDate","getPlayerList","getMethods"};
	  public BlockPos pos;
	  public World world;
	  
	@Override
	public String getType() {
		return "WorldInterface";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {

		switch (method){
        	case 0:
        		if (arguments.length >= 3) {
        			if (arguments[0] instanceof Double && arguments[1] instanceof Double && arguments[2] instanceof Double) {
        				BlockPos blockPos = new BlockPos((Double)arguments[0],(Double)arguments[1],(Double)arguments[2]);
                  		BiomeGenBase biome = world.getBiomeGenForCoords(blockPos);
                  		return new Object[]{biome.biomeName};
        			}
        			else
        			{
        				return new Object[]{"getBiome([Number],[Number],[Number])"};
        			}
        		}
        		else
        		{
            		BiomeGenBase biome = world.getBiomeGenForCoords(pos);
            		return new Object[]{biome.biomeName};
        		}

        	case 1:
        		if(world.isThundering() == true){
        			return new Object[]{"Thunder"};
        		}else if(world.isRaining() == true){
        			return new Object[]{"Rain"};
        		}else{
        			return new Object[]{"Clear"};
        		}
        	case 2:
        		if (arguments.length >= 3) {
        			if (arguments[0] instanceof Double && arguments[1] instanceof Double && arguments[2] instanceof Double) {
        				HashMap BlockInfos = new HashMap();
        				HashMap datatags = new HashMap();
        				BlockPos blockPos = new BlockPos((Double)arguments[0],(Double)arguments[1],(Double)arguments[2]);
        				TileEntity TileBlock = world.getTileEntity(blockPos);
        				BiomeGenBase biomeBlock = world.getBiomeGenForCoords(pos);
        				
        				if (TileBlock != null && TileBlock.serializeNBT() != null) {
        				datatags = Util.GetTags(TileBlock.serializeNBT());
        				}
        				BlockInfos.put("datatags",datatags);
        				BlockInfos.put("light", world.getLightBrightness(blockPos));
        				BlockInfos.put("opacity", world.getBlockLightOpacity(blockPos));
        				BlockInfos.put("lightLevel", world.getLightFromNeighbors(blockPos));
        				BlockInfos.put("blockName", world.getBlockState(blockPos).getBlock().getRegistryName());
        				BlockInfos.put("metadata", world.getBlockState(blockPos).getBlock().getMetaFromState(world.getBlockState(blockPos)));
        				BlockInfos.put("slipperiness", world.getBlockState(blockPos).getBlock().slipperiness);
        				BlockInfos.put("chunkX", world.getChunkFromBlockCoords(blockPos).xPosition);
        				BlockInfos.put("chunkZ", world.getChunkFromBlockCoords(blockPos).zPosition);
        				BlockInfos.put("isChunkLoaded", world.getChunkFromBlockCoords(blockPos).isLoaded());
        				BlockInfos.put("isPowered", world.isBlockPowered(blockPos));
        				BlockInfos.put("powerStrength", world.isBlockIndirectlyGettingPowered(blockPos));
        				BlockInfos.put("biome", biomeBlock.biomeName);
        				
        				return new Object[]{BlockInfos};
        			}
        			else
        			{
        				return new Object[]{"getBlockInfos(Number,Number,Number)"};
        			}
        		}
        		else
        		{
        			return new Object[]{"getBlockInfos(X,Y,Z)"};
        		}
        	case 3:{
        		if (arguments.length >= 1) {
        			if (arguments[0] instanceof Double) {				
        				HashMap infos = WorldInterfaceRegistry.computers.get(computer);
        				infos.remove("range");
        				infos.put("range",((Double) arguments[0]).intValue());
        				WorldInterfaceRegistry.computers.remove(computer);
        				WorldInterfaceRegistry.computers.put(computer, infos);
        				return new Object[]{arguments[0]};
        			}
        			else
        			{
        				return new Object[]{"setListeningRange(Number)"};
        			}
        		}
        		else
        		{
        			return new Object[]{(Integer)WorldInterfaceRegistry.computers.get(computer).get("range")};
        		}
        	}
        	case 4:{
        		Date date = new Date();
        		return new Object[]{date.toString()};
        	}
        	case 5:
        		HashMap PlayerList = new HashMap();
        		for (int i=0; i < MinecraftServer.getServer().getConfigurationManager().getPlayerList().size(); i++) {
        			PlayerList.put(i+1,((EntityPlayerMP)MinecraftServer.getServer().getConfigurationManager().getPlayerList().get(i)).getName());
        		}
        		return new Object[] {PlayerList};
        	case 6:
				if (arguments.length > 0) {
					if (arguments[0] instanceof String) {
						if (arguments[0].equals("getBiome")) {
							return new Object[] {"getBiome(([X],[Y],[Z]) or Nothing)","getBiome(([Number],[Number],[Number]) or nil)","This function return the biome type where the peripheral is situated or at coordinates in the world"};
						}
						if (arguments[0].equals("getWeather")) {
							return new Object[] {"getWeather()","This function return the weather in the world like 'Rain', 'Thunder', 'Clear'"};
						}
						if (arguments[0].equals("getBlockInfos")) {
							return new Object[] {"getBlockInfos(X,Y,Z)","getBlockInfos(Number,Number,Number)","This function return every informations about the block at one coordinate like name, metadatas, datatags, etc..."};
						}
						if (arguments[0].equals("setListeningRange")) {
							return new Object[] {"setListeningRange(Range or Nothing)","setListeningRange(Number or nil)","This function allow you to define the radius of listening, if there no argument to define the radius then the function return the actual radius of listening"};
						}
						if (arguments[0].equals("getRealDate")) {
							Date ndate = new Date();
							return new Object[] {"getRealDate()","This function return the date of the server like '"+ndate.toString()+"'"};
						}
						if (arguments[0].equals("getPlayerList")) {
							return new Object[] {"getPlayerList()","This function return the list of the online players"};
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
					return new Object[] { "getBiome","getWeather","getBlockInfos","setListeningRange","getRealDate","getPlayerList","getMethods","","EVENTS :","- sound_played : When a sound is played around a certain radius, the event return the name of the entity who played the sound, the type of the entity, the coordinates where the entity is, the distance between the entity and the peripheral, the name of the sound, his volume and his pitch","Example : entityName, entityType, coords, distance, soundName, volume, pitch = os.pullEvent('sound_played')"};
				}
		}
		
			
		return null;
	}


	
	@Override
	public void attach(IComputerAccess computer) {
		//MainAddonCC.LOGGER.info("ATTACH");
		HashMap infos = new HashMap();
		infos.put("range", 16);
		infos.put("pos", this.pos);
		infos.put("id", computer.getID());
		infos.put("worldInterfaces", this);
		boolean valide = true;
		for (TileWorldInterface worldInterface : WorldInterfaceRegistry.worldInterfaces.keySet()) {
			if (worldInterface.equals(this)) {
				valide = false;
				break;
			}
		}
		if (valide) {
			WorldInterfaceRegistry.worldInterfaces.put(this, true);
			WorldInterfaceRegistry.computers.put(computer, infos);
		}
		
	}
	
	@Override
	public void detach(IComputerAccess computer) {
	//	MainAddonCC.LOGGER.info("DETACH");
		WorldInterfaceRegistry.worldInterfaces.remove(this);
		WorldInterfaceRegistry.computers.remove(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}
	
	public static void onAmbiantSoundPlayed(Entity sender ,String soundname, float volume, float pitch) {
		for (IComputerAccess computer : WorldInterfaceRegistry.computers.keySet()) {
			BlockPos position =(BlockPos)WorldInterfaceRegistry.computers.get(computer).get("pos");
			double distance = sender.getDistance(position.getX(), position.getY(), position.getZ());
			String TypeEntity = sender.getClass().getSimpleName();
			
			
			
			int range = (Integer)WorldInterfaceRegistry.computers.get(computer).get("range");
			if (distance <= range) {
				HashMap coord = new HashMap();
				coord.put("x", sender.getPosition().getX());
				coord.put("y", sender.getPosition().getY());
				coord.put("z", sender.getPosition().getZ());
				computer.queueEvent("sound_played", new Object[] {sender.getName(), TypeEntity, coord, distance, soundname, volume, pitch});
			}
	}
		
	


	}	

}