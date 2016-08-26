package fr.dydy70310.lifeisperipheral.blocks;


import java.awt.Rectangle;
import java.util.ArrayList;
import java.util.List;

import com.google.common.collect.Lists;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import fr.dydy70310.lifeisperipheral.Utils.MultiBlockInfos;
import fr.dydy70310.lifeisperipheral.Utils.Util;
import fr.dydy70310.lifeisperipheral.tile.TileAdvancedNoteBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockState;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockPos;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockAdvancedNoteBlock extends BlockContainer implements IPeripheralProvider {
	
	private static final List<String> INSTRUMENTS = Lists.newArrayList(new String[] {"harp", "bd", "snare", "hat", "bassattack"});

	

    public static final PropertyDirection DIRECTIONBLOCK = PropertyDirection.create("direction", EnumFacing.Plane.HORIZONTAL);
    
    
	public BlockAdvancedNoteBlock() 
	{
		super(Material.rock);

		this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.setDefaultState(getDefaultState().withProperty(DIRECTIONBLOCK, EnumFacing.NORTH));
	}
	
	@Override
	public IPeripheral getPeripheral(World world, BlockPos bpos, EnumFacing side) 
	{
		TileEntity tile = world.getTileEntity(bpos);
		TileAdvancedNoteBlock noteblock = new TileAdvancedNoteBlock();
		noteblock.pos = bpos;
		noteblock.world = world;
		noteblock.side = world.getBlockState(bpos).getValue(DIRECTIONBLOCK);
		
		BlockPos pos1;
		if (tile == null)
			return null;
		if (tile instanceof TileAdvancedNoteBlock) {
			
			return noteblock;
		}
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileAdvancedNoteBlock();
	}
	
	@Override
	public boolean isOpaqueCube() {
	    return false;
	}
	
	@Override
	public int getRenderType() {
	  return 3;
	}
	
	
    public boolean isPassable(IBlockAccess worldIn, BlockPos pos)
    {
        return false;
    }
   
    public boolean isFullCube()
    {
        return true;
    }
   
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockAccess worldIn, BlockPos pos, EnumFacing side)
    {
        return true;
    }
    

    public boolean onBlockEventReceived(World worldIn, BlockPos pos, IBlockState state, int eventID, int eventParam)
    {
     int note = eventParam/10;
     int volume = eventParam%10;
        net.minecraftforge.event.world.NoteBlockEvent.Play e = new net.minecraftforge.event.world.NoteBlockEvent.Play(worldIn, pos, state, note, eventID);
        if (net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(e)) return false;
        eventID = e.instrument.ordinal();
        eventParam = e.getVanillaNoteId();
        float f = (float)Math.pow(2.0D, (double)(note - 12) / 12.0D);
        worldIn.playSoundEffect((double)pos.getX() + 0.5D, (double)pos.getY() + 0.5D, (double)pos.getZ() + 0.5D, "note." + this.getInstrument(eventID),(float)volume, f);
        worldIn.spawnParticle(EnumParticleTypes.NOTE, (double)pos.getX() + 0.5D, (double)pos.getY() + 1.2D, (double)pos.getZ() + 0.5D, (double)eventParam / 24.0D, 0.0D, 0.0D, new int[0]);
        return true;
    }

    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
    	this.setDefaultFacing(world, pos, state);
		TileAdvancedNoteBlock noteblock = new TileAdvancedNoteBlock();
		noteblock.pos = pos;
		noteblock.world = world;
		noteblock.side = world.getBlockState(pos).getValue(DIRECTIONBLOCK);
		noteblock.setPos(pos);
    	
		
    	List<TileEntity> TileEntityList = new ArrayList();
    	List<TileAdvancedNoteBlock> SaveList = new ArrayList();
    	SaveList.add(noteblock);
    	boolean quit = false;

    	while (quit == false) {

    		if (SaveList.size() > 0) {
    			TileAdvancedNoteBlock blockSelec = SaveList.get(0);
    			
    			TileEntity leftBlock = world.getTileEntity(Util.blockside(blockSelec.getPos(), world.getBlockState(blockSelec.getPos()).getValue(DIRECTIONBLOCK), "left"));
    			TileEntity rightBlock = world.getTileEntity(Util.blockside(blockSelec.getPos(), world.getBlockState(blockSelec.getPos()).getValue(DIRECTIONBLOCK), "right"));
    			TileEntity topBlock = world.getTileEntity(Util.blockside(blockSelec.getPos(), world.getBlockState(blockSelec.getPos()).getValue(DIRECTIONBLOCK), "top"));
    			TileEntity bottomBlock = world.getTileEntity(Util.blockside(blockSelec.getPos(), world.getBlockState(blockSelec.getPos()).getValue(DIRECTIONBLOCK), "bottom"));

    			if (leftBlock instanceof TileAdvancedNoteBlock) {
    				if (!TileEntityList.contains(leftBlock)) {
    					if (!SaveList.contains((TileAdvancedNoteBlock)leftBlock)) { 
    						SaveList.add((TileAdvancedNoteBlock)leftBlock);
    					}
    				}
    			}
    			
    			if (rightBlock instanceof TileAdvancedNoteBlock) {
    				if (!TileEntityList.contains(rightBlock)) {
    					if (!SaveList.contains((TileAdvancedNoteBlock)rightBlock)) {
    						SaveList.add((TileAdvancedNoteBlock)rightBlock);
    					}
    				}
    			}
    			
    			if (topBlock instanceof TileAdvancedNoteBlock) {
    				if (!TileEntityList.contains(topBlock)) {
    					if (!SaveList.contains((TileAdvancedNoteBlock)topBlock)) { 
    						SaveList.add((TileAdvancedNoteBlock)topBlock);
    					}
    				}
    			}
    			
    			if (bottomBlock instanceof TileAdvancedNoteBlock) {
    				if (!TileEntityList.contains(bottomBlock)) {
    					if (!SaveList.contains((TileAdvancedNoteBlock)topBlock)) { 
    						SaveList.add((TileAdvancedNoteBlock)bottomBlock);
    					}	
    				}
    			}
    			
    			TileEntityList.add(world.getTileEntity(blockSelec.getPos()));
    			SaveList.remove(0);
    			
    		}
    		else
    		{
    			quit = true;
    		}
    	}
    	//System.out.println(TileEntityList.size());
		System.out.println(ShapesCalc(TileEntityList).size());
        
		
		 
    }
    
    
    public List<MultiBlockInfos> CompacteRectangles(List<MultiBlockInfos> lst){
    	CompactesRectangleRecursif(lst);
    	return ListeFinale;
    }
	
    public List<MultiBlockInfos> ListeFinale = new ArrayList();
    public void CompactesRectangleRecursif(List<MultiBlockInfos> r) {
    	List<MultiBlockInfos> lst1 = new ArrayList();
    	List<MultiBlockInfos> lst2 = new ArrayList();
    	
    	for (MultiBlockInfos rec : r) {
    		lst1.add(rec);
    	}
    	
    	MultiBlockInfos nRec;
    	boolean ajout = false;
    	
    	for (MultiBlockInfos rec : r) {
    		if (lst1.contains(rec)) {
    			nRec = rec;
    			lst1.remove(rec);
    			for (MultiBlockInfos rec2 : r) {
    				Rectangle collideRec = new Rectangle(); 
    				collideRec.setRect(nRec.rectangle().getX() - 1,nRec.rectangle().getY() - 1,nRec.rectangle().getWidth() + 2,nRec.rectangle().getHeight() +2);
    				if (!nRec.equals(rec2)) {
    					if (lst1.contains(rec2)) {
    						if (rec2.facing().equals(nRec.facing())) {
    							if (rec2.rectangle().getX() == nRec.rectangle().getX()) {
    								if (rec2.rectangle().getWidth() == nRec.rectangle().getWidth()) {
    									if (rec2.rectangle().intersects(collideRec)) {
    										Rectangle nRectangle = new Rectangle();
    										nRectangle.setRect(nRec.rectangle().getX(), Math.min(nRec.rectangle().getY(),  rec2.rectangle().getY()),  nRec.rectangle().getWidth(),nRec.rectangle().getHeight() + rec2.rectangle().getHeight());
    										MultiBlockInfos nRec2 = new MultiBlockInfos(nRectangle, nRec.facing());
    										ajout = true;
    										lst1.remove(rec2);
    										nRec = nRec2;
    									}
    								}
    							}
    							else if (rec2.rectangle().getY() == nRec.rectangle().getY()) {
    								if (rec2.rectangle().getHeight() == nRec.rectangle().getHeight()) {
    									if (rec2.rectangle().intersects(collideRec)) {
    										Rectangle nRectangle = new Rectangle();
    										nRectangle.setRect(Math.min(nRec.rectangle().getX(), rec2.rectangle().getX()),nRec.rectangle().getY(), nRec.rectangle().getWidth() + rec2.rectangle().getHeight(), nRec.rectangle().getHeight());
    										MultiBlockInfos nRec2 = new MultiBlockInfos(nRectangle, nRec.facing());
    										ajout = true;
    										lst1.remove(rec2);
    										nRec = nRec2;
    									}
    								}
    							}
    						}
    					}
    				}
    			}
    			
    			lst2.add(nRec);
    			
    		}
    	}
    	
    	if (ajout == true) {
    		CompactesRectangleRecursif(lst2);
    	}
    	else
    	{
    		ListeFinale = r;
    	}
    }
    
    private List<MultiBlockInfos> ShapesCalc(List<TileEntity> t) {
    	List<MultiBlockInfos> ShapeListe = new ArrayList();
    	List<MultiBlockInfos> recList = new ArrayList();
    	if (t.size() > 0) {
    		TileEntity blockSelec = t.get(0);
    		World world = blockSelec.getWorld();
	    	BlockPos leftBlock = Util.blockside(blockSelec.getPos(), world.getBlockState(blockSelec.getPos()).getValue(DIRECTIONBLOCK), "left");
	    	BlockPos pos = new BlockPos(blockSelec.getPos().getX() - leftBlock.getX(),blockSelec.getPos().getY() - leftBlock.getY(),blockSelec.getPos().getZ() - leftBlock.getZ());
	    	
	    	
	    	if (pos.getX() == 0) {
	    		for (TileEntity tileentity : t) {
	    			Rectangle rec = new Rectangle();
	    			rec.setRect(tileentity.getPos().getZ(), tileentity.getPos().getY(), 1, 1);
	    			recList.add(new MultiBlockInfos(rec,tileentity.getWorld().getBlockState(tileentity.getPos()).getValue(DIRECTIONBLOCK)));
	    		}
	    			
	    	}
	    	else
	    	{
	    		for (TileEntity tileentity : t) {
	    			Rectangle rec = new Rectangle();
	    			rec.setRect(tileentity.getPos().getX(), tileentity.getPos().getY(), 1, 1);
	    			recList.add(new MultiBlockInfos(rec,tileentity.getWorld().getBlockState(tileentity.getPos()).getValue(DIRECTIONBLOCK)));
	    		}
	    	}
	    	ShapeListe = CompacteRectangles(recList);
	    	}
    	return ShapeListe;
    }
    
    private void setDefaultFacing(World world, BlockPos pos, IBlockState state)
    {
        if(!world.isRemote)
        {
            Block blockN = world.getBlockState(pos.north()).getBlock();
            Block blockS = world.getBlockState(pos.south()).getBlock();
            Block blockW = world.getBlockState(pos.west()).getBlock();
            Block blockE = world.getBlockState(pos.east()).getBlock();
 
            EnumFacing facing = (EnumFacing)state.getValue(DIRECTIONBLOCK);
 
            if(facing == EnumFacing.NORTH && blockN.isFullBlock() && !blockS.isFullBlock())
            {
                facing = EnumFacing.SOUTH;
            }
            else if(facing == EnumFacing.SOUTH && blockS.isFullBlock() && !blockN.isFullCube())
            {
                facing = EnumFacing.NORTH;
            }
            else if(facing == EnumFacing.EAST && blockE.isFullBlock() && !blockW.isFullCube())
            {
                facing = EnumFacing.WEST;
            }
            else if(facing == EnumFacing.WEST && blockW.isFullBlock() && !blockE.isFullBlock())
            {
                facing = EnumFacing.EAST;
            }
        }
    }
    
    
    
    @Override
    public IBlockState onBlockPlaced(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer)
    {
    		
        return this.getDefaultState().withProperty(DIRECTIONBLOCK, placer.getHorizontalFacing().getOpposite());
    }
    @Override
    @SideOnly(Side.CLIENT)
    public IBlockState getStateForEntityRender(IBlockState state)
    {
        return this.getDefaultState().withProperty(DIRECTIONBLOCK, EnumFacing.SOUTH);
    }
    @Override
    public IBlockState getStateFromMeta(int meta)
    {
        EnumFacing enumfacing = EnumFacing.getFront(meta);
 
        if(enumfacing.getAxis() == EnumFacing.Axis.Y)
        {
            enumfacing = EnumFacing.NORTH;
        }
 
        return this.getDefaultState().withProperty(DIRECTIONBLOCK, enumfacing);
    }
    @Override
    public int getMetaFromState(IBlockState state)
    {
        return ((EnumFacing)state.getValue(DIRECTIONBLOCK)).getIndex();
    }
    
    
    
    private String getInstrument(int id)
    {
        if (id < 0 || id >= INSTRUMENTS.size())
        {
            id = 0;
        }

        return (String)INSTRUMENTS.get(id);
    }
    
    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {DIRECTIONBLOCK});
    }
}