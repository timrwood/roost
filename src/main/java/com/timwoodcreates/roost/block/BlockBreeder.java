package com.timwoodcreates.roost.block;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.RoostGui;
import com.timwoodcreates.roost.tileentity.TileEntityBreeder;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;

public class BlockBreeder extends BlockContainer {

	public static final PropertyBool IS_BREEDING = PropertyBool.create("is_breeding");
	public static final PropertyBool HAS_SEEDS = PropertyBool.create("has_seeds");

	public BlockBreeder() {
		super(Material.WOOD);

		setHardness(2.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);
		setDefaultState(blockState.getBaseState().withProperty(IS_BREEDING, false).withProperty(HAS_SEEDS, false));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, IS_BREEDING, HAS_SEEDS);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityBreeder();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX,
			float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}

		TileEntityBreeder tileEntity = (TileEntityBreeder) worldIn.getTileEntity(pos);

		if (tileEntity == null) {
			return false;
		}

		playerIn.openGui(Roost.INSTANCE, RoostGui.BREEDER, worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (tileEntity instanceof TileEntityBreeder) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityBreeder) tileEntity);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	public boolean isFullCube(IBlockState state) {
		return false;
	}

	@Override
	public boolean isOpaqueCube(IBlockState state) {
		return state.getValue(IS_BREEDING);
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return 0;
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		TileEntity tileEntity = worldIn instanceof ChunkCache ? ((ChunkCache) worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK)
				: worldIn.getTileEntity(pos);

		if (tileEntity instanceof TileEntityBreeder) {
			TileEntityBreeder tileEntityBreeder = (TileEntityBreeder) tileEntity;
			return state.withProperty(IS_BREEDING, tileEntityBreeder.isFullOfChickens()).withProperty(HAS_SEEDS, tileEntityBreeder.isFullOfSeeds());
		}
		return state;
	}
}
