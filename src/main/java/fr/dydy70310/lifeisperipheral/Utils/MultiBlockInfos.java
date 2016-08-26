package fr.dydy70310.lifeisperipheral.Utils;

import java.awt.Rectangle;

import net.minecraft.util.EnumFacing;

public class MultiBlockInfos {
	
	private EnumFacing side;
	private Rectangle rec;

	public MultiBlockInfos(Rectangle rectangle, EnumFacing facing) {
		this.side = facing;
		this.rec = rectangle;
	}
	
	public Rectangle rectangle() {
		return this.rec;
	}
	
	public EnumFacing facing() {
		return this.side;
	}
}
