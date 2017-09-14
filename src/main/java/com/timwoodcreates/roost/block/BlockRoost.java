package com.timwoodcreates.roost.block;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.RoostGui;
import com.timwoodcreates.roost.data.DataChicken;
import com.timwoodcreates.roost.data.EnumChickenType;
import com.timwoodcreates.roost.tileentity.TileEntityRoost;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.ChunkCache;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class BlockRoost extends BlockContainer {

	public static final PropertyDirection FACING = BlockHorizontal.FACING;
	public static final PropertyEnum<EnumChickenType> CHICKEN = PropertyEnum.<EnumChickenType>create("chicken",
			EnumChickenType.class);

	public BlockRoost() {
		super(Material.WOOD);

		setHardness(2.0F);
		setResistance(5.0F);
		setSoundType(SoundType.WOOD);
		setDefaultState(blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(CHICKEN,
				EnumChickenType.EMPTY));
	}

	@Override
	protected BlockStateContainer createBlockState() {
		return new BlockStateContainer(this, FACING, CHICKEN);
	}

	@Override
	public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer,
			ItemStack stack) {
		worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return new TileEntityRoost();
	}

	@Override
	public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn,
			EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
		if (worldIn.isRemote) {
			return true;
		}

		TileEntityRoost tileEntity = (TileEntityRoost) worldIn.getTileEntity(pos);

		if (tileEntity == null) {
			return false;
		}

		if (playerIn.isSneaking() && tileEntity.pullChickenOut(playerIn)) {
			return true;
		}

		if (tileEntity.putChickenIn(playerIn.getHeldItem(hand))) {
			return true;
		}

		playerIn.openGui(Roost.INSTANCE, RoostGui.ROOST, worldIn, pos.getX(), pos.getY(), pos.getZ());

		return true;
	}

	@Override
	public void breakBlock(World worldIn, BlockPos pos, IBlockState state) {
		TileEntity tileEntity = worldIn.getTileEntity(pos);

		if (tileEntity instanceof TileEntityRoost) {
			InventoryHelper.dropInventoryItems(worldIn, pos, (TileEntityRoost) tileEntity);
		}

		super.breakBlock(worldIn, pos, state);
	}

	@Override
	public EnumBlockRenderType getRenderType(IBlockState state) {
		return EnumBlockRenderType.MODEL;
	}

	@Override
	@SideOnly(Side.CLIENT)
	public BlockRenderLayer getBlockLayer() {
		return BlockRenderLayer.CUTOUT;
	}

	@Override
	public IBlockState getStateFromMeta(int meta) {
		return this.getDefaultState().withProperty(FACING, EnumFacing.getHorizontal(meta));
	}

	@Override
	public int getMetaFromState(IBlockState state) {
		return state.getValue(FACING).getHorizontalIndex();
	}

	@Override
	public IBlockState getActualState(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		EnumChickenType chickenType = EnumChickenType.EMPTY;
		TileEntity tileEntity = worldIn instanceof ChunkCache
				? ((ChunkCache) worldIn).getTileEntity(pos, Chunk.EnumCreateEntityType.CHECK)
				: worldIn.getTileEntity(pos);

		if (tileEntity instanceof TileEntityRoost) {
			DataChicken chickenData = ((TileEntityRoost) tileEntity).createChickenData();
			if (chickenData != null) chickenType = EnumChickenType.get(chickenData.getName());
		}

		if (chickenType == null) chickenType = EnumChickenType.UNKNOWN;

		return state.withProperty(CHICKEN, chickenType);
	}
}
