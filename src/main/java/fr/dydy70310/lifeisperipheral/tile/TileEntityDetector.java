package fr.dydy70310.lifeisperipheral.tile;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import dan200.computercraft.api.lua.ILuaContext;
import dan200.computercraft.api.lua.LuaException;
import dan200.computercraft.api.peripheral.IComputerAccess;
import dan200.computercraft.api.peripheral.IPeripheral;
import fr.dydy70310.lifeisperipheral.Reference;
import fr.dydy70310.lifeisperipheral.Utils.Util;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.potion.PotionEffect;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.util.Constants.NBT;

public class TileEntityDetector extends TileEntity implements IPeripheral {

	public static class EntityDetectorRegistry {
		public static HashMap<TileEntityDetector, Boolean> entityDetectors = new HashMap<TileEntityDetector, Boolean>();
		public static HashMap<IComputerAccess, Boolean> computers = new HashMap<IComputerAccess, Boolean>();
	}
	
	public static String[] methods = { "getEntityListAdvanced","getEntityList","getPlayerDetail","getMethods"};
	  public BlockPos pos;
	  public World world;
	  public WorldServer worldserveur;
	  
	@Override
	public String getType() {
		return "EntityDetector";
	}

	@Override
	public String[] getMethodNames() {
		return methods;
	}

	@Override
	public Object[] callMethod(IComputerAccess computer, ILuaContext context, int method, Object[] arguments) throws LuaException, InterruptedException {
		
		switch (method){
		    case 0: 
		    	if(arguments.length < 1){
		    		return new Object[] {"getEntityListAdvanced(Range,[X],[Y],[Z])"};
		    	}else{
		    		boolean Valide = false;
		    		
		    		if (arguments.length >= 1 && arguments.length < 4 ) {
		    			if (arguments[0] instanceof Double) {
		    				Valide = true;
		    			}
		    		}
		    		else if (arguments.length >= 4) {
		    			if (arguments[0] instanceof Double && arguments[1] instanceof Double && arguments[2] instanceof Double && arguments[3] instanceof Double) {
		    				Valide = true;
		    			}
		    		}
		    		
			    	if (Valide == true) {
			    		Double arg = (Double) arguments[0];
			    		if ((arg - Reference.Range) <= 0){
				    		
				    		Double range = (Double) arguments[0];
					    	List list = this.world.loadedEntityList;
					    	HashMap map = new HashMap();
					    	if (list.size() != 0){
					    		Integer n = 0;
						    	for(int i = 0;i < list.size() ;i++){
						    		Entity e = null;
						    		
						    		if ((Entity) list.get(i) instanceof EntityLiving){
						    			EntityLiving e2 = (EntityLiving) list.get(i);
						    			Double distance = 0.0;
						    			if (arguments.length >= 1 && arguments.length < 4) {
						    				distance = e2.getDistance(pos.getX(), pos.getY(),pos.getZ());
						    			}
						    			else if (arguments.length >= 4)
						    			{	
						    				distance = e2.getDistance(((Double)arguments[1]).intValue(), ((Double)arguments[2]).intValue(), ((Double)arguments[3]).intValue());
						    			}
							    		if(distance < range){
							    			n +=1;
							    			
							    			HashMap EntityInfos = new HashMap();
							    			HashMap EntityItemInHand = new HashMap();
							    			HashMap EntityEffect = new HashMap();
							    			
							    			DecimalFormat df = new DecimalFormat ( ) ;
							    			df.setMaximumFractionDigits (2);
							    			df.setDecimalSeparatorAlwaysShown (false); 
							    			String s = df.format(distance);
							    			String TypeEntity = e2.getClass().getSimpleName();
							    			// Informations diverses
							    			EntityInfos.put("name", e2.getName().toString());
							    			EntityInfos.put("distance", s.toString());
							    			EntityInfos.put("type", TypeEntity.toString());
							    			EntityInfos.put("x", Double.toString(e2.posX));
							    			EntityInfos.put("y", Double.toString(e2.posY));
							    			EntityInfos.put("z", Double.toString(e2.posZ));
							    			EntityInfos.put("onFire", e2.isBurning());
							    			EntityInfos.put("explosionImmunity", e2.isImmuneToExplosions());
							    			EntityInfos.put("fireImmunity", e2.isImmuneToFire());
							    			EntityInfos.put("isInLava", e2.isInLava());
							    			EntityInfos.put("isInvisible", e2.isInvisible());
							    			EntityInfos.put("isInWater", e2.isInWater());
							    			EntityInfos.put("canBePushedByWater", e2.isPushedByWater());
							    			EntityInfos.put("isRiding", e2.isRiding());
							    			EntityInfos.put("isSilent", e2.isSilent());
							    			EntityInfos.put("isSneaking", e2.isSneaking());
							    			EntityInfos.put("isSprinting", e2.isSprinting());
							    			EntityInfos.put("isWet", e2.isWet());
							    			EntityInfos.put("pitch", e2.rotationPitch);
							    			EntityInfos.put("yaw", e2.rotationYaw);
							    			EntityInfos.put("headYaw", e2.getRotationYawHead());
							    			EntityInfos.put("UUID", e2.getUniqueID().toString());
							    			EntityInfos.put("lookX", e2.getLookVec().xCoord);
							    			EntityInfos.put("lookY", e2.getLookVec().yCoord);
							    			EntityInfos.put("lookZ", e2.getLookVec().zCoord);
							    			EntityInfos.put("hasCustomName", e2.hasCustomName());
							    			EntityInfos.put("riddenByEntity", e2.riddenByEntity);
							    			EntityInfos.put("customNameTag", e2.getCustomNameTag());
							    			EntityInfos.put("canBePushed", e2.canBePushed());
							    			EntityInfos.put("isAlive", e2.isEntityAlive());
							    			EntityInfos.put("portalCooldown", e2.getPortalCooldown());
							    			EntityInfos.put("maxInPortalTime", e2.getMaxInPortalTime());
							    			EntityInfos.put("outsideBorder", e2.isOutsideBorder());
							    			EntityInfos.put("chunkCoordX", e2.chunkCoordX);
							    			EntityInfos.put("chunkCoordY", e2.chunkCoordY);
							    			EntityInfos.put("chunkCoordZ", e2.chunkCoordZ);
							    			EntityInfos.put("motionX", e2.motionX);
							    			EntityInfos.put("motionY", e2.motionY);
							    			EntityInfos.put("motionZ", e2.motionZ);
							    			EntityInfos.put("onGround", e2.onGround);
							    			EntityInfos.put("datatags", Util.GetTags(e2.serializeNBT()));
							    			if (e2.riddenByEntity != null) {
							    				EntityInfos.put("riddenByEntity", e2.riddenByEntity.getName());
							    			}
							    			EntityInfos.put("health", e2.getHealth());
							    			Collection<PotionEffect> potionEffects =  e2.getActivePotionEffects();
							    			java.util.Iterator<PotionEffect> Iterator = potionEffects.iterator();
							    			if (potionEffects != null) {
							    				for(int j = 0;j < potionEffects.size();j++){
							    					HashMap EffectsInfos = new HashMap();
							    					if (Iterator.hasNext()) {
							    						PotionEffect Effet = Iterator.next();
							    						if (Effet != null) {
							    							EffectsInfos.put("name",Effet.getEffectName().replace("potion.", ""));
							    							EffectsInfos.put("duration",Effet.getDuration()/20);
							    							EffectsInfos.put("isAmbiant",Effet.getIsAmbient());
							    							EffectsInfos.put("showParticles",Effet.getIsShowParticles());
							    							EffectsInfos.put("id",Effet.getPotionID());
							    							EffectsInfos.put("amplifier",Effet.getAmplifier()+1);
							    							EntityEffect.put(j+1,EffectsInfos);
							    						}
							    					}
							    				}
							    			}
							    			
							    			// Item dans la main
							    			if (e2.getHeldItem() != null){
							    				HashMap EnchantsList = new HashMap();
							    				ItemStack item = e2.getHeldItem();
							    					EntityItemInHand.put("stackSize", item.stackSize);
							    					EntityItemInHand.put("displayName", item.getDisplayName());
							    					EntityItemInHand.put("name", item.getItem().getRegistryName());
							    					EntityItemInHand.put("lifeDuration", item.getMaxDamage() - item.getItemDamage());
							    					EntityItemInHand.put("lifeMaxDuration", item.getMaxItemUseDuration());
							    					EntityItemInHand.put("maxStackSize", item.getMaxStackSize());
							    					EntityItemInHand.put("hasDisplayName", item.hasDisplayName());
							    					EntityItemInHand.put("metadata", item.getMetadata());
							    					EntityItemInHand.put("repairCost", item.getRepairCost());
							    					EntityItemInHand.put("isItemDamaged", item.isItemDamaged());
							    					EntityItemInHand.put("isItemEnchantable", item.isItemEnchantable());
							    					EntityItemInHand.put("isEnchanted", item.isItemEnchanted());
							    					EntityItemInHand.put("isStackable",item.isStackable());
							    					EntityItemInHand.put("datatags",Util.GetTags(item.serializeNBT()));
								    				//Enchantements
								    					NBTTagList lst = item.serializeNBT().getCompoundTag("tag").getTagList("ench", NBT.TAG_COMPOUND);
									    				for (int m = 0; m <  lst.tagCount() ;m++) {
									    					HashMap Enchant = new HashMap();
									    					NBTTagCompound items = (NBTTagCompound) lst.getCompoundTagAt(m);
									    					Enchant.put("enchantName", Enchantment.getEnchantmentById(items.getInteger("id")).getName().replace("enchantment.", ""));
									    					Enchant.put("enchantLvl",  items.getInteger("lvl"));
									    					EnchantsList.put(m+1, Enchant);
									    				}
									    				EntityItemInHand.put("enchants", EnchantsList);
								    				
								    				
								    				
								    				EntityInfos.put("itemInHand",EntityItemInHand);
							    			}
							    			
							    			
							    			
							    			map.put(n,EntityInfos);
							    			
							    		}
						    		}
						    		if ((Entity) list.get(i) instanceof EntityPlayer){
						    			EntityPlayer e2 = (EntityPlayer) list.get(i);
						    			Double distance = 0.0;
						    			if (arguments.length >= 1 && arguments.length < 4) {
						    				distance = e2.getDistance(pos.getX(), pos.getY(),pos.getZ());
						    			}
						    			else if (arguments.length >= 4)
						    			{	
						    				distance = e2.getDistance(((Double)arguments[1]).intValue(), ((Double)arguments[2]).intValue(), ((Double)arguments[3]).intValue());
						    			}
							    		if(distance < range){
							    			n +=1;
							    			HashMap EntityInfos = new HashMap();
							    			HashMap EntityInventory = new HashMap();
							    			HashMap EntityEffect = new HashMap();
							    			HashMap EntityOpenContainerInventory = new HashMap();
							    			
							    			DecimalFormat df = new DecimalFormat ( ) ;
							    			df.setMaximumFractionDigits (2);
							    			df.setDecimalSeparatorAlwaysShown (false); 
							    			String s = df.format(distance);
							    			String TypeEntity = e2.getClass().getSimpleName();
							    			// Informations diverses
							    			EntityInfos.put("name", e2.getName().toString());
							    			EntityInfos.put("distance", s.toString());
							    			EntityInfos.put("type", TypeEntity.toString());
							    			EntityInfos.put("x", Double.toString(e2.posX));
							    			EntityInfos.put("y", Double.toString(e2.posY));
							    			EntityInfos.put("z", Double.toString(e2.posZ));
							    			EntityInfos.put("onFire", e2.isBurning());
							    			EntityInfos.put("explosionImmunity", e2.isImmuneToExplosions());
							    			EntityInfos.put("fireImmunity", e2.isImmuneToFire());
							    			EntityInfos.put("isInLava", e2.isInLava());
							    			EntityInfos.put("isInvisible", e2.isInvisible());
							    			EntityInfos.put("isInWater", e2.isInWater());
							    			EntityInfos.put("canBePushedByWater", e2.isPushedByWater());
							    			EntityInfos.put("isRiding", e2.isRiding());
							    			EntityInfos.put("isSilent", e2.isSilent());
							    			EntityInfos.put("isSneaking", e2.isSneaking());
							    			EntityInfos.put("isSprinting", e2.isSprinting());
							    			EntityInfos.put("isWet", e2.isWet());
							    			EntityInfos.put("pitch", e2.rotationPitch);
							    			EntityInfos.put("yaw", e2.rotationYaw);
							    			EntityInfos.put("headYaw", e2.getRotationYawHead());
							    			EntityInfos.put("UUID", e2.getUniqueID().toString());
							    			EntityInfos.put("lookX", e2.getLookVec().xCoord);
							    			EntityInfos.put("lookY", e2.getLookVec().yCoord);
							    			EntityInfos.put("lookZ", e2.getLookVec().zCoord);
							    			EntityInfos.put("hasCustomName", e2.hasCustomName());
							    			EntityInfos.put("customNameTag", e2.getCustomNameTag());
							    			EntityInfos.put("canBePushed", e2.canBePushed());
							    			EntityInfos.put("isAlive", e2.isEntityAlive());
							    			EntityInfos.put("portalCooldown", e2.getPortalCooldown());
							    			EntityInfos.put("maxInPortalTime", e2.getMaxInPortalTime());
							    			EntityInfos.put("outsideBorder", e2.isOutsideBorder());
							    			EntityInfos.put("chunkCoordX", e2.chunkCoordX);
							    			EntityInfos.put("chunkCoordY", e2.chunkCoordY);
							    			EntityInfos.put("chunkCoordZ", e2.chunkCoordZ);
							    			EntityInfos.put("motionX", e2.motionX);
							    			EntityInfos.put("motionY", e2.motionY);
							    			EntityInfos.put("motionZ", e2.motionZ);
							    			EntityInfos.put("onGround", e2.onGround);
							    			EntityInfos.put("health",e2.getHealth());
							    			EntityInfos.put("foodLevel",e2.getFoodStats().getFoodLevel());
							    			EntityInfos.put("totalArmor",e2.getTotalArmorValue());
							    			EntityInfos.put("canBreathUnderwater",e2.canBreatheUnderwater());
							    			EntityInfos.put("absorptionAmount",e2.getAbsorptionAmount());
							    			if (e2.riddenByEntity != null) {
							    				EntityInfos.put("riddenByEntity", e2.riddenByEntity.getName());
							    			}
							    			//Potions
							    			Collection<PotionEffect> potionEffects =  e2.getActivePotionEffects();
							    			java.util.Iterator<PotionEffect> Iterator = potionEffects.iterator();
							    			if (potionEffects != null) {
							    				for(int j = 0;j < potionEffects.size();j++){
							    					HashMap EffectsInfos = new HashMap();
							    					if (Iterator.hasNext()) {
							    						PotionEffect Effet = Iterator.next();
							    						if (Effet != null) {
							    							EffectsInfos.put("name",Effet.getEffectName().replace("potion.", ""));
							    							EffectsInfos.put("duration",Effet.getDuration()/20);
							    							EffectsInfos.put("isAmbiant",Effet.getIsAmbient());
							    							EffectsInfos.put("showParticles",Effet.getIsShowParticles());
							    							EffectsInfos.put("id",Effet.getPotionID());
							    							EffectsInfos.put("amplifier",Effet.getAmplifier()+1);
							    							EntityEffect.put(j+1,EffectsInfos);
							    						}
							    					}
							    				}
							    			}
							    			
							    			// OpenContainer
							    			for(int j = 0;j < e2.openContainer.inventoryItemStacks.size()-36 ;j++){
							    				ItemStack InventoryInfos = (ItemStack) e2.openContainer.inventoryItemStacks.get(j);
							    				HashMap StackInfos = new HashMap();
							    				HashMap EnchantsList = new HashMap();
							    				if (InventoryInfos != null){
								    				StackInfos.put("slotNumber", j+1);
								    				StackInfos.put("stackSize", InventoryInfos.stackSize);
								    				StackInfos.put("displayName", InventoryInfos.getDisplayName());
								    				StackInfos.put("name", InventoryInfos.getItem().getRegistryName());
								    				StackInfos.put("lifeDuration", InventoryInfos.getMaxDamage() - InventoryInfos.getItemDamage());
								    				StackInfos.put("lifeMaxDuration", InventoryInfos.getMaxDamage());
								    				StackInfos.put("maxStackSize", InventoryInfos.getMaxStackSize());
								    				StackInfos.put("hasDisplayName", InventoryInfos.hasDisplayName());
								    				StackInfos.put("metadata", InventoryInfos.getMetadata());
								    				StackInfos.put("repairCost", InventoryInfos.getRepairCost());
								    				StackInfos.put("isItemDamaged", InventoryInfos.isItemDamaged());
								    				StackInfos.put("isItemEnchantable", InventoryInfos.isItemEnchantable());
								    				StackInfos.put("isEnchanted", InventoryInfos.isItemEnchanted());
								    				StackInfos.put("isStackable", InventoryInfos.isStackable());
								    				StackInfos.put("datatags", Util.GetTags(InventoryInfos.serializeNBT()));
								    				//Enchantements
								    				NBTTagList lst = InventoryInfos.serializeNBT().getCompoundTag("tag").getTagList("ench", NBT.TAG_COMPOUND);
								    				for (int m = 0; m <  lst.tagCount() ;m++) {
								    					HashMap Enchant = new HashMap();
								    					NBTTagCompound item = (NBTTagCompound) lst.getCompoundTagAt(m);
								    					Enchant.put("enchantName", Enchantment.getEnchantmentById(item.getInteger("id")).getName().replace("enchantment.", ""));
								    					Enchant.put("enchantLvl",  item.getInteger("lvl"));
								    					EnchantsList.put(m+1, Enchant);
								    				}
								    				StackInfos.put("enchants", EnchantsList);
								    				
								    				EntityOpenContainerInventory.put(j+1,StackInfos);
							    				}
							    			}
							    			EntityInfos.put("openContainer",EntityOpenContainerInventory);
							    			
							    			//Inventaire
							    			for(int j = 0;j < e2.inventory.getSizeInventory() ;j++){
							    				ItemStack InventoryInfos = (ItemStack) e2.inventory.getStackInSlot(j);
							    				HashMap StackInfos = new HashMap();
							    				HashMap EnchantsList = new HashMap();
							    				
							    				if (InventoryInfos != null){
								    				StackInfos.put("slotNumber", j+1);
								    				StackInfos.put("stackSize", InventoryInfos.stackSize);
								    				StackInfos.put("displayName", InventoryInfos.getDisplayName());
								    				StackInfos.put("name", InventoryInfos.getItem().getRegistryName());
								    				StackInfos.put("lifeDuration", InventoryInfos.getMaxDamage() - InventoryInfos.getItemDamage());
								    				StackInfos.put("lifeMaxDuration", InventoryInfos.getMaxDamage());
								    				StackInfos.put("maxStackSize", InventoryInfos.getMaxStackSize());
								    				StackInfos.put("hasDisplayName", InventoryInfos.hasDisplayName());
								    				StackInfos.put("metadata", InventoryInfos.getMetadata());
								    				StackInfos.put("repairCost", InventoryInfos.getRepairCost());
								    				StackInfos.put("isItemDamaged", InventoryInfos.isItemDamaged());
								    				StackInfos.put("isItemEnchantable", InventoryInfos.isItemEnchantable());
								    				StackInfos.put("isEnchanted", InventoryInfos.isItemEnchanted());
								    				StackInfos.put("isStackable", InventoryInfos.isStackable());
								    				StackInfos.put("datatags", Util.GetTags(InventoryInfos.serializeNBT()));
								    				//Enchantements
									    				NBTTagList lst = InventoryInfos.serializeNBT().getCompoundTag("tag").getTagList("ench", NBT.TAG_COMPOUND);
									    				for (int m = 0; m <  lst.tagCount() ;m++) {
									    					HashMap Enchant = new HashMap();
									    					NBTTagCompound item = (NBTTagCompound) lst.getCompoundTagAt(m);
									    					Enchant.put("enchantName", Enchantment.getEnchantmentById(item.getInteger("id")).getName().replace("enchantment.", ""));
									    					Enchant.put("enchantLvl",  item.getInteger("lvl"));
									    					EnchantsList.put(m+1, Enchant);
									    				}
									    				StackInfos.put("enchants", EnchantsList);
									    			
								    				EntityInventory.put(j+1,StackInfos);
								    			}
								    		}
								    			EntityInfos.put("inventory",EntityInventory);
							    			map.put(n,EntityInfos);
							    			
							    		}
						    		}
						    	}
						    	return new Object[] {map};
					    	}
			    		}else{
			    			return new Object[] {"Max Range : " + Reference.Range};
			    		}
			    	}else{
			    		return new Object[] {"getEntityListAdvanced(Number,[Number],[Number],[Number])"};
			    	}
		    	}
		    case 1:
		    	if (arguments.length < 1) {
		    		
		    	}
		    	else {
		    		boolean Valide = false;
		    		
		    		if (arguments.length >= 1 && arguments.length < 4 ) {
		    			if (arguments[0] instanceof Double) {
		    				Valide = true;
		    			}
		    		}
		    		else if (arguments.length >= 4) {
		    			if (arguments[0] instanceof Double && arguments[1] instanceof Double && arguments[2] instanceof Double && arguments[3] instanceof Double) {
		    				Valide = true;
		    			}
		    		}
		    		if (Valide == true) {
			    		Double arg = (Double) arguments[0];
			    		if ((arg - Reference.Range) <= 0){
				    		Double range = (Double) arguments[0];
					    	List list = this.world.loadedEntityList;
					    	HashMap map = new HashMap();
					    	if (list.size() != 0){
					    		Integer n = 0;
						    	for(int i = 0;i < list.size() ;i++){
						    		Entity e = null;
						    		if ((Entity) list.get(i) instanceof EntityLiving){
						    			EntityLiving e2 = (EntityLiving) list.get(i);
						    			Double distance = 0.0;
						    			if (arguments.length >= 1 && arguments.length < 4) {
						    				distance = e2.getDistance(pos.getX(), pos.getY(),pos.getZ());
						    			}
						    			else if (arguments.length >= 4)
						    			{	
						    				distance = e2.getDistance(((Double)arguments[1]).intValue(), ((Double)arguments[2]).intValue(), ((Double)arguments[3]).intValue());
						    			}
							    		if(distance < range){
							    			n +=1;
							    			HashMap EntityInfos = new HashMap();
							    			EntityInfos.put("name", e2.getName().toString());
							    			EntityInfos.put("type", e2.getClass().getSimpleName());
							    			EntityInfos.put("x", e2.getPosition().getX());
							    			EntityInfos.put("y", e2.getPosition().getY());
							    			EntityInfos.put("z", e2.getPosition().getZ());
							    			map.put(n, EntityInfos);
							    		}
						    		}
						    		else if ((Entity) list.get(i) instanceof EntityPlayer){
						    			EntityPlayer e2 = (EntityPlayer) list.get(i);
						    			Double distance = 0.0;
						    			if (arguments.length >= 1 && arguments.length < 4) {
						    				distance = e2.getDistance(pos.getX(), pos.getY(),pos.getZ());
						    			}
						    			else if (arguments.length >= 4)
						    			{	
						    				distance = e2.getDistance(((Double)arguments[1]).intValue(), ((Double)arguments[2]).intValue(), ((Double)arguments[3]).intValue());
						    			}
							    		if(distance < range){
							    			n +=1;
							    			HashMap EntityInfos = new HashMap();
							    			EntityInfos.put("name", e2.getName().toString());
							    			EntityInfos.put("type", e2.getClass().getSimpleName());
							    			EntityInfos.put("x", e2.getPosition().getX());
							    			EntityInfos.put("y", e2.getPosition().getY());
							    			EntityInfos.put("z", e2.getPosition().getZ());
							    			map.put(n, EntityInfos);
							    		}
						    		}
						    	}
						    	return new Object[] {map};
					    	}
			    		}	
			    		else
			    		{
			    			return new Object[] {"Max Range : " + Reference.Range};
			    		}
		    		}
		    		
		    	}
		    case 2:
		    	if(arguments.length < 1){
		    		return new Object[] {"getPlayerDetail(PlayerName)"};
		    	}
		    	else
		    	{
		    		if (arguments[0] instanceof String) {
		    			HashMap map = new HashMap();
		    			List<EntityPlayer> allp = new ArrayList<EntityPlayer>();
		    			allp = this.world.playerEntities;
		    			for(int i = 0;i < allp.size() ;i++){
		    			EntityPlayer e = allp.get(i);
		    			
		    			if (e.getName().toString().equals(arguments[0].toString())){
		    			Double distance = e.getDistance(pos.getX(), pos.getY(),pos.getZ());
		    			HashMap EntityInfos = new HashMap();
		    			HashMap EntityInventory = new HashMap();
		    			HashMap EntityEffect = new HashMap();
		    			HashMap EntityOpenContainerInventory = new HashMap();
		    			
		    			DecimalFormat df = new DecimalFormat ( ) ;
		    			df.setMaximumFractionDigits (2);
		    			df.setDecimalSeparatorAlwaysShown (false); 
		    			String s = df.format(distance);
		    			String TypeEntity = e.getClass().getSimpleName();
		    			//Informations diverses
		    			EntityInfos.put("name", e.getName().toString());
		    			EntityInfos.put("distance", s.toString());
		    			EntityInfos.put("type", TypeEntity.toString());
		    			EntityInfos.put("x", Double.toString(e.posX));
		    			EntityInfos.put("y", Double.toString(e.posY));
		    			EntityInfos.put("z", Double.toString(e.posZ));
		    			EntityInfos.put("onFire", e.isBurning());
		    			EntityInfos.put("explosionImmunity", e.isImmuneToExplosions());
		    			EntityInfos.put("fireImmunity", e.isImmuneToFire());
		    			EntityInfos.put("isInLava", e.isInLava());
		    			EntityInfos.put("isInvisible", e.isInvisible());
		    			EntityInfos.put("isInWater", e.isInWater());
		    			EntityInfos.put("canBePushedByWater", e.isPushedByWater());
		    			EntityInfos.put("isRiding", e.isRiding());
		    			EntityInfos.put("isSilent", e.isSilent());
		    			EntityInfos.put("isSneaking", e.isSneaking());
		    			EntityInfos.put("isSprinting", e.isSprinting());
		    			EntityInfos.put("isWet", e.isWet());
		    			EntityInfos.put("pitch", e.rotationPitch);
		    			EntityInfos.put("yaw", e.rotationYaw);
		    			EntityInfos.put("headYaw", e.getRotationYawHead());
		    			EntityInfos.put("UUID", e.getUniqueID().toString());
		    			EntityInfos.put("lookX", e.getLookVec().xCoord);
		    			EntityInfos.put("lookY", e.getLookVec().yCoord);
		    			EntityInfos.put("lookZ", e.getLookVec().zCoord);
		    			EntityInfos.put("hasCustomName", e.hasCustomName());
		    			EntityInfos.put("riddenByEntity", e.riddenByEntity);
		    			EntityInfos.put("customNameTag", e.getCustomNameTag());
		    			EntityInfos.put("canBePushed", e.canBePushed());
		    			EntityInfos.put("isAlive", e.isEntityAlive());
		    			EntityInfos.put("portalCooldown", e.getPortalCooldown());
		    			EntityInfos.put("maxInPortalTime", e.getMaxInPortalTime());
		    			EntityInfos.put("outsideBorder", e.isOutsideBorder());
		    			EntityInfos.put("chunkCoordX", e.chunkCoordX);
		    			EntityInfos.put("chunkCoordY", e.chunkCoordY);
		    			EntityInfos.put("chunkCoordZ", e.chunkCoordZ);
		    			EntityInfos.put("motionX", e.motionX);
		    			EntityInfos.put("motionY", e.motionY);
		    			EntityInfos.put("motionZ", e.motionZ);
		    			EntityInfos.put("onGround", e.onGround);
		    			EntityInfos.put("isPlayerSleeping", e.isPlayerSleeping());
		    			if (e.riddenByEntity != null) {
		    				EntityInfos.put("riddenByEntity", e.riddenByEntity.getName());
		    			}
		    			EntityInfos.put("health",e.getHealth());
		    			EntityInfos.put("foodLevel",e.getFoodStats().getFoodLevel());
		    			EntityInfos.put("totalArmor",e.getTotalArmorValue());
		    			EntityInfos.put("canBreathUnderwater",e.canBreatheUnderwater());
		    			EntityInfos.put("absorptionAmount",e.getAbsorptionAmount());
		    			
		    			
		    			//Effets Potions
		    			Collection<PotionEffect> potionEffects =  e.getActivePotionEffects();
		    			java.util.Iterator<PotionEffect> Iterator = potionEffects.iterator();
		    			if (potionEffects != null) {
		    				for(int j = 0;j < potionEffects.size();j++){
		    					HashMap EffectsInfos = new HashMap();
		    					if (Iterator.hasNext()) {
		    						PotionEffect Effet = Iterator.next();
		    						if (Effet != null) {
		    							EffectsInfos.put("name",Effet.getEffectName().replace("potion.", ""));
		    							EffectsInfos.put("duration",Effet.getDuration()/20);
		    							EffectsInfos.put("isAmbiant",Effet.getIsAmbient());
		    							EffectsInfos.put("showParticles",Effet.getIsShowParticles());
		    							EffectsInfos.put("id",Effet.getPotionID());
		    							EffectsInfos.put("amplifier",Effet.getAmplifier()+1);
		    							EntityEffect.put(j+1,EffectsInfos);
		    						}
		    					}
		    				}
		    			}
		    			
		    			
		    			// OpenContainer
		    			for(int j = 0;j < e.openContainer.inventoryItemStacks.size()-36 ;j++){
		    				ItemStack InventoryInfos = (ItemStack) e.openContainer.inventoryItemStacks.get(j);
		    				HashMap StackInfos = new HashMap();
		    				HashMap EnchantsList = new HashMap();
		    				if (InventoryInfos != null){
			    				StackInfos.put("slotNumber", j+1);
			    				StackInfos.put("stackSize", InventoryInfos.stackSize);
			    				StackInfos.put("displayName", InventoryInfos.getDisplayName());
			    				StackInfos.put("name", InventoryInfos.getItem().getRegistryName());
			    				StackInfos.put("lifeDuration", InventoryInfos.getMaxDamage() - InventoryInfos.getItemDamage());
			    				StackInfos.put("lifeMaxDuration", InventoryInfos.getMaxDamage());
			    				StackInfos.put("maxStackSize", InventoryInfos.getMaxStackSize());
			    				StackInfos.put("hasDisplayName", InventoryInfos.hasDisplayName());
			    				StackInfos.put("metadata", InventoryInfos.getMetadata());
			    				StackInfos.put("repairCost", InventoryInfos.getRepairCost());
			    				StackInfos.put("isItemDamaged", InventoryInfos.isItemDamaged());
			    				StackInfos.put("isItemEnchantable", InventoryInfos.isItemEnchantable());
			    				StackInfos.put("isEnchanted", InventoryInfos.isItemEnchanted());
			    				StackInfos.put("isStackable", InventoryInfos.isStackable());
			    				StackInfos.put("datatags", Util.GetTags(InventoryInfos.serializeNBT()));
			    				//Enchantements
			    				NBTTagList lst = InventoryInfos.serializeNBT().getCompoundTag("tag").getTagList("ench", NBT.TAG_COMPOUND);
			    				for (int m = 0; m <  lst.tagCount() ;m++) {
			    					HashMap Enchant = new HashMap();
			    					NBTTagCompound item = (NBTTagCompound) lst.getCompoundTagAt(m);
			    					Enchant.put("enchantName", Enchantment.getEnchantmentById(item.getInteger("id")).getName().replace("enchantment.", ""));
			    					Enchant.put("enchantLvl",  item.getInteger("lvl"));
			    					EnchantsList.put(m+1, Enchant);
			    				}
			    				StackInfos.put("enchants", EnchantsList);
			    				
			    				EntityOpenContainerInventory.put(j+1,StackInfos);
		    				}
		    			}
		    			EntityInfos.put("openContainer",EntityOpenContainerInventory);
	    			
		    			
		    			//Inventaire
		    			for(int j = 0;j < e.inventory.getSizeInventory() ;j++){
		    				ItemStack InventoryInfos = (ItemStack) e.inventory.getStackInSlot(j);
		    				HashMap StackInfos = new HashMap();
		    				HashMap EnchantsList = new HashMap();
		    				if (InventoryInfos != null){
			    				StackInfos.put("slotNumber", j+1);
			    				StackInfos.put("stackSize", InventoryInfos.stackSize);
			    				StackInfos.put("displayName", InventoryInfos.getDisplayName());
			    				StackInfos.put("name", InventoryInfos.getItem().getRegistryName());
			    				StackInfos.put("lifeDuration", InventoryInfos.getMaxDamage() - InventoryInfos.getItemDamage());
			    				StackInfos.put("lifeMaxDuration", InventoryInfos.getMaxDamage());
			    				StackInfos.put("maxStackSize", InventoryInfos.getMaxStackSize());
			    				StackInfos.put("hasDisplayName", InventoryInfos.hasDisplayName());
			    				StackInfos.put("metadata", InventoryInfos.getMetadata());
			    				StackInfos.put("repairCost", InventoryInfos.getRepairCost());
			    				StackInfos.put("isItemDamaged", InventoryInfos.isItemDamaged());
			    				StackInfos.put("isItemEnchantable", InventoryInfos.isItemEnchantable());
			    				StackInfos.put("isEnchanted", InventoryInfos.isItemEnchanted());
			    				StackInfos.put("isStackable", InventoryInfos.isStackable());
			    				StackInfos.put("datatags", Util.GetTags(InventoryInfos.serializeNBT()));
			    				//Enchantements
			    				NBTTagList lst = InventoryInfos.serializeNBT().getCompoundTag("tag").getTagList("ench", NBT.TAG_COMPOUND);
			    				for (int m = 0; m <  lst.tagCount() ;m++) {
			    					HashMap Enchant = new HashMap();
			    					NBTTagCompound item = (NBTTagCompound) lst.getCompoundTagAt(m);
			    					Enchant.put("enchantName", Enchantment.getEnchantmentById(item.getInteger("id")).getName().replace("enchantment.", ""));
			    					Enchant.put("enchantLvl",  item.getInteger("lvl"));
			    					EnchantsList.put(m+1, Enchant);
			    				}
			    				StackInfos.put("enchants", EnchantsList);
			    				
		    				EntityInventory.put(j+1,StackInfos);
		    				}
		    			}
		    			EntityInfos.put("effects",EntityEffect);
		    			EntityInfos.put("inventory",EntityInventory);
		    			
		    			map.put(arguments[0].toString(),EntityInfos);
		    			return new Object[] {map};
		    			}
		    			}
		    		}
		    		else
		    		{
		    			return new Object[] {"getPlayerDetail(String)"};
		    		}
		    	}
		    case 3:
				if (arguments.length > 0) {
					if (arguments[0] instanceof String) {
						if (arguments[0].equals("getPlayerDetail")) {
							return new Object[] {"getPlayerDetail(PlayerName)","getPlayerDetail(String)","This function return every informations about the player like health, armor etc..."};
						}
						if (arguments[0].equals("getEntityList")) {
							return new Object[] {"getEntityList(Range,[X],[Y],[Z])","getEntityList(Number,[Number],[Number],[Number])","This function return information about every entities like monsters and players in a radius, the coordinates are optional and allow you define the center of the radius"};
						}
						if (arguments[0].equals("getEntityListAdvanced")) {
							return new Object[] {"getEntityListAdvanced(Range,[X],[Y],[Z])","getEntityListAdvanced(Number,[Number],[Number],[Number])","This function return every information about every entities like monsters and players in a radius, the coordinates are optional and allow you define the center of the radius"};
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
		
			
		return null;
	}


	
	@Override
	public void attach(IComputerAccess computer) {
		//MainAddonCC.LOGGER.info("ATTACH");
		EntityDetectorRegistry.entityDetectors.put(this, true);
		EntityDetectorRegistry.computers.put(computer, true);
		
	}
	
	public static ItemStack getStackInSlot(World world, HashMap targets, String targetName, int slot)
	  {
	    if (targets.containsKey(targetName))
	    {
	      Object target = targets.get(targetName);
	      if ((target instanceof IInventory))
	      {
	        IInventory inventory = (IInventory)target;
	        if (slot < inventory.getSizeInventory()) {
	          return inventory.getStackInSlot(slot);
	        }
	      }
	    }
	    return null;
	  }
	
	@Override
	public void detach(IComputerAccess computer) {
	//	MainAddonCC.LOGGER.info("DETACH");
		EntityDetectorRegistry.entityDetectors.remove(this);
		EntityDetectorRegistry.computers.remove(computer);
	}

	@Override
	public boolean equals(IPeripheral other) {
		return false;
	}

}