package fr.dydy70310.lifeisperipheral.Utils;

import java.awt.Rectangle;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

import fr.dydy70310.lifeisperipheral.tile.TileChatInterface;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.common.util.Constants.NBT;

public class Util {

	public static String DoubleToString(Double d,int numberAfterComa) {
		DecimalFormat df = new DecimalFormat ( ) ;
		df.setMaximumFractionDigits (numberAfterComa);
		df.setDecimalSeparatorAlwaysShown (false); 
		String s = df.format(d);
		return s;
	}
	
	public static BlockPos poswithfacing(BlockPos bpos,EnumFacing face){
		BlockPos pos = bpos;
		if(face.equals(EnumFacing.NORTH)){
			pos = bpos.north();
		}
		else if(face.equals(EnumFacing.SOUTH)){
			pos = bpos.south();
		}
		else if(face.equals(EnumFacing.EAST)){
			pos = bpos.east();
		}
		else if(face.equals(EnumFacing.WEST)){
			pos = bpos.west();
		}
		else if(face.equals(EnumFacing.UP)){
			pos = bpos.up();
		}
		else if(face.equals(EnumFacing.DOWN)){
			pos = bpos.down();
		}
		return pos;
		
	}

	
	
	public static HashMap GetTags(NBTTagCompound Ctag) {
		HashMap infos = new HashMap();
		if (Ctag != null) {
			Iterator iterator = Ctag.getKeySet().iterator();
			while (iterator.hasNext()) {
				String currentTag = (String)iterator.next();
				if (Ctag.hasKey(currentTag, NBT.TAG_END)) {
					//Rien a mettre ici
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_BYTE)) {
					infos.put(currentTag, ((Byte)Ctag.getByte(currentTag)).intValue());
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_SHORT)) {
					infos.put(currentTag, ((Short)Ctag.getShort(currentTag)).intValue());
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_INT)) {
					infos.put(currentTag, ((Integer)Ctag.getInteger(currentTag)).intValue());
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_LONG)) {
					infos.put(currentTag, ((Long)Ctag.getLong(currentTag)).intValue());
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_FLOAT)) {
					infos.put(currentTag, ((Float)Ctag.getFloat(currentTag)).intValue());
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_DOUBLE)) {
					infos.put(currentTag, ((Double)Ctag.getDouble(currentTag)).intValue());
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_STRING)) {
					infos.put(currentTag, Ctag.getString(currentTag).toString());
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_LIST)) {
					HashMap newInfos = new HashMap();
					NBTTagList lst = Ctag.getTagList(currentTag, NBT.TAG_COMPOUND);
					for (int i=0; i < lst.tagCount(); i++) {
						newInfos.put(i+1, GetTags(lst.getCompoundTagAt(i)));
					}
					infos.put(currentTag, newInfos);
				}
				else if (Ctag.hasKey(currentTag, NBT.TAG_COMPOUND)) {
					infos.put(currentTag, GetTags(Ctag.getCompoundTag(currentTag)));
				}
			}
		}
		return infos;
	}
	
	
	public static HashMap getInfo(ItemStack itemstack,int slot){
		HashMap StackInfos = new HashMap();
		HashMap EnchantsList = new HashMap();
		if (itemstack != null){
			StackInfos.put("slot", slot);
			StackInfos.put("stackSize", itemstack.stackSize);
			StackInfos.put("displayName", itemstack.getDisplayName());
			StackInfos.put("name", itemstack.getItem().getRegistryName());
			StackInfos.put("lifeDuration", itemstack.getMaxDamage() - itemstack.getItemDamage());
			StackInfos.put("lifeMaxDuration", itemstack.getMaxDamage());
			StackInfos.put("maxStackSize", itemstack.getMaxStackSize());
			StackInfos.put("hasDisplayName", itemstack.hasDisplayName());
			StackInfos.put("metadata", itemstack.getMetadata());
			StackInfos.put("repairCost", itemstack.getRepairCost());
			StackInfos.put("isItemDamaged", itemstack.isItemDamaged());
			StackInfos.put("isItemEnchantable", itemstack.isItemEnchantable());
			StackInfos.put("isEnchanted", itemstack.isItemEnchanted());
			StackInfos.put("isStackable", itemstack.isStackable());
			if (itemstack.serializeNBT() != null) {
				StackInfos.put("datatags", GetTags(itemstack.serializeNBT()));
			}
			//Enchantements
			NBTTagList lst = itemstack.serializeNBT().getCompoundTag("tag").getTagList("ench", NBT.TAG_COMPOUND);
			for (int m = 0; m <  lst.tagCount() ;m++) {
				HashMap Enchant = new HashMap();
				NBTTagCompound item = (NBTTagCompound) lst.getCompoundTagAt(m);
				Enchant.put("enchantName", Enchantment.getEnchantmentById(item.getInteger("id")).getName().replace("enchantment.", ""));
				Enchant.put("enchantLvl",  item.getInteger("lvl"));
				EnchantsList.put(m+1, Enchant);
			}
			StackInfos.put("Enchants", EnchantsList);
		}
		return StackInfos;
	}

	public static HashMap getInfo(ItemStack itemstack){
		HashMap StackInfos = new HashMap();
		HashMap EnchantsList = new HashMap();
		if (itemstack != null){
			StackInfos.put("stackSize", itemstack.stackSize);
			StackInfos.put("displayName", itemstack.getDisplayName());
			StackInfos.put("name", itemstack.getItem().getRegistryName());
			StackInfos.put("lifeDuration", itemstack.getMaxDamage() - itemstack.getItemDamage());
			StackInfos.put("lifeMaxDuration", itemstack.getMaxDamage());
			StackInfos.put("maxStackSize", itemstack.getMaxStackSize());
			StackInfos.put("hasDisplayName", itemstack.hasDisplayName());
			StackInfos.put("metadata", itemstack.getMetadata());
			StackInfos.put("repairCost", itemstack.getRepairCost());
			StackInfos.put("isItemDamaged", itemstack.isItemDamaged());
			StackInfos.put("isItemEnchantable", itemstack.isItemEnchantable());
			StackInfos.put("isEnchanted", itemstack.isItemEnchanted());
			StackInfos.put("isStackable", itemstack.isStackable());
			if (itemstack.serializeNBT() != null) {
				StackInfos.put("datatags", GetTags(itemstack.serializeNBT()));
			}
			//Enchantements
			NBTTagList lst = itemstack.serializeNBT().getCompoundTag("tag").getTagList("ench", NBT.TAG_COMPOUND);
			for (int m = 0; m <  lst.tagCount() ;m++) {
				HashMap Enchant = new HashMap();
				NBTTagCompound item = (NBTTagCompound) lst.getCompoundTagAt(m);
				Enchant.put("enchantName", Enchantment.getEnchantmentById(item.getInteger("id")).getName().replace("enchantment.", ""));
				Enchant.put("enchantLvl",  item.getInteger("lvl"));
				EnchantsList.put(m+1, Enchant);
			}
			StackInfos.put("Enchants", EnchantsList);
		}
		return StackInfos;
	}

	public static String checkside(String side){
		side = side.toLowerCase();
				if (side.equals("left") || side.equals("right") || side.equals("front") || side.equals("back") || side.equals("top") || side.equals("bottom")) {
					return side;
				}
				else
				{
					return null;
				}
				
	}
	
	public static BlockPos blockside(BlockPos bpos,EnumFacing face, String side){
		BlockPos pos = bpos;
		String nSide = checkside(side);
		if (nSide != null) {
			
		if(face.equals(EnumFacing.NORTH)){
			if (nSide.equals("left")) {
				pos = bpos.east();
			}
			else if (nSide.equals("right")) {
				pos = bpos.west();
			}
			else if (nSide.equals("front")) {
				pos = bpos.north();
			}
			else if (nSide.equals("back")) {
				pos = bpos.south();
			}
			else if (nSide.equals("top")) {
				pos = bpos.up();
			}
			else if (nSide.equals("bottom")) {
				pos = bpos.down();
			}
			
		}
		else if(face.equals(EnumFacing.SOUTH)){
			if (nSide.equals("left")) {
				pos = bpos.west();
			}
			else if (nSide.equals("right")) {
				pos = bpos.east();
			}
			else if (nSide.equals("front")) {
				pos = bpos.south();
			}
			else if (nSide.equals("back")) {
				pos = bpos.north();
			}
			else if (nSide.equals("top")) {
				pos = bpos.up();
			}
			else if (nSide.equals("bottom")) {
				pos = bpos.down();
			}
		}
		else if(face.equals(EnumFacing.EAST)){
			if (nSide.equals("left")) {
				pos = bpos.south();
			}
			else if (nSide.equals("right")) {
				pos = bpos.north();
			}
			else if (nSide.equals("front")) {
				pos = bpos.east();
			}
			else if (nSide.equals("back")) {
				pos = bpos.west();
			}
			else if (nSide.equals("top")) {
				pos = bpos.up();
			}
			else if (nSide.equals("bottom")) {
				pos = bpos.down();
			}
		}
		else if(face.equals(EnumFacing.WEST)){
			if (nSide.equals("left")) {
				pos = bpos.north();
			}
			else if (nSide.equals("right")) {
				pos = bpos.south();
			}
			else if (nSide.equals("front")) {
				pos = bpos.west();
			}
			else if (nSide.equals("back")) {
				pos = bpos.east();
			}
			else if (nSide.equals("top")) {
				pos = bpos.up();
			}
			else if (nSide.equals("bottom")) {
				pos = bpos.down();
			}
		}
		else if(face.equals(EnumFacing.UP)){
			pos = bpos.up();
		}
		else if(face.equals(EnumFacing.DOWN)){
			pos = bpos.down();
		}
		
		
		return pos;
		}
		else
		return null;
		
	}
	
	
	
}
