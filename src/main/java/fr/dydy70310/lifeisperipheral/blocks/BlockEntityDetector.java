package fr.dydy70310.lifeisperipheral.blocks;

import dan200.computercraft.api.peripheral.IPeripheral;
import dan200.computercraft.api.peripheral.IPeripheralProvider;
import fr.dydy70310.lifeisperipheral.tile.TileEntityDetector;
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
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockEntityDetector extends BlockContainer implements IPeripheralProvider {
	
	
	
	public BlockEntityDetector() {
		super(Material.rock);
        this.setBlockBounds(0.0F, 0.0F, 0.0F, 1.0F, 1.0F, 1.0F);
        this.setDefaultState(getDefaultState().withProperty(DIRECTIONBLOCK, EnumFacing.NORTH));
	}
	
	@Override
	public IPeripheral getPeripheral(World world, BlockPos bpos, EnumFacing side) {
		TileEntity tile = world.getTileEntity(bpos);
		TileEntityDetector detector = new TileEntityDetector();
		detector.pos = bpos;
		detector.world = world;
		
		BlockPos pos1;
		if (tile == null)
			return null;
		if (tile instanceof TileEntityDetector) {
			
			return detector;
		}
		return null;
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityDetector();
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
   
   
    public static final PropertyDirection DIRECTIONBLOCK = PropertyDirection.create("direction", EnumFacing.Plane.HORIZONTAL);
   
    @Override
    public void onBlockAdded(World world, BlockPos pos, IBlockState state)
    {
        this.setDefaultFacing(world, pos, state);
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
    @Override
    protected BlockState createBlockState()
    {
        return new BlockState(this, new IProperty[] {DIRECTIONBLOCK});
    }
}