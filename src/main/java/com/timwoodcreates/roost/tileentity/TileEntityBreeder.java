package com.timwoodcreates.roost.tileentity;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.block.BlockBreeder;
import com.timwoodcreates.roost.data.DataChicken;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;

public class TileEntityBreeder extends TileEntityChickenContainer {

	@Override
	protected void isFullOfChickensChanged(boolean isFull) {
		BlockBreeder.setIsBreedingState(isFull, worldObj, pos);
	}

	@Override
	protected void spawnChickenDrop() {
		DataChicken left = getChickenData(0);
		DataChicken right = getChickenData(1);
		if (left != null && right != null) {
			putStackInOutput(left.createChildStack(right, worldObj));
			playSpawnSound();
			spawnParticles();
		}
	}

	private void playSpawnSound() {
		worldObj.playSound(null, pos, SoundEvents.ENTITY_CHICKEN_EGG, SoundCategory.NEUTRAL, 0.5F, 0.8F);
	}

	private void spawnParticles() {
		spawnParticle(-0.1d, 0.5d, 0, 0.2d);
		spawnParticle(0.5d, -0.1d, 0.2d, 0);
		spawnParticle(1.1d, 0.5d, 0, 0.2d);
		spawnParticle(0.5d, 1.1d, 0.2d, 0);
	}

	private void spawnParticle(double x, double z, double xOffset, double zOffset) {
		if (worldObj instanceof WorldServer) {
			WorldServer worldServer = (WorldServer) worldObj;
			worldServer.spawnParticle(EnumParticleTypes.HEART, pos.getX() + x, pos.getY() + 0.5d, pos.getZ() + z, 2,
					xOffset, 0.2d, zOffset, 0.02D);
		}
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock() != newState.getBlock());
	}

	@Override
	public String getName() {
		return "container." + Roost.MODID + ".breeder";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(getName());
	}

	@Override
	public int getSizeInventory() {
		return 6;
	}

	@Override
	public int getSizeChickenInventory() {
		return 2;
	}

	@Override
	protected int requiredSeedsForDrop() {
		return 2;
	}

}
