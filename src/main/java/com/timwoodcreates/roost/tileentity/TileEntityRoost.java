package com.timwoodcreates.roost.tileentity;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.data.DataChicken;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;

public class TileEntityRoost extends TileEntityChickenContainer {

	private static int CHICKEN_SLOT = 0;

	@Override
	protected void isFullOfChickensChanged(boolean isFull) {
		notifyBlockUpdate();
	}

	@Override
	protected void spawnChickenDrop() {
		DataChicken chicken = getChickenData(0);
		if (chicken != null) putStackInOutput(chicken.createDropStack());
	}

	public DataChicken createChickenData() {
		return createChickenData(0);
	}

	public boolean putChickenIn(ItemStack newStack) {
		ItemStack oldStack = getStackInSlot(CHICKEN_SLOT);
		if (newStack == null || !isItemValidForSlot(CHICKEN_SLOT, newStack)) {
			return false;
		}

		if (oldStack == null) {
			setInventorySlotContents(CHICKEN_SLOT, newStack.splitStack(16));
			markDirty();
			playPutChickenInSound();
			return true;
		}

		if (oldStack.isItemEqual(newStack) && ItemStack.areItemStackTagsEqual(oldStack, newStack)) {
			int itemsAfterAdding = Math.min(oldStack.stackSize + newStack.stackSize, 16);
			int itemsToAdd = itemsAfterAdding - oldStack.stackSize;
			if (itemsToAdd > 0) {
				newStack.splitStack(itemsToAdd);
				oldStack.stackSize += itemsToAdd;
				markDirty();
				playPutChickenInSound();
				return true;
			}
		}

		return false;
	}

	public boolean pullChickenOut(EntityPlayer playerIn) {
		ItemStack spawnStack = getStackInSlot(CHICKEN_SLOT);

		if (spawnStack == null) {
			return false;
		}

		playerIn.inventory.addItemStackToInventory(spawnStack);
		setInventorySlotContents(CHICKEN_SLOT, null);

		markDirty();
		playPullChickenOutSound();

		return true;
	}

	private void playPutChickenInSound() {
		worldObj.playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	private void playPullChickenOutSound() {
		worldObj.playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	@Override
	public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState) {
		return (oldState.getBlock() != newState.getBlock());
	}

	private void notifyBlockUpdate() {
		final IBlockState state = worldObj.getBlockState(pos);
		worldObj.notifyBlockUpdate(pos, state, state, 2);
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		return new SPacketUpdateTileEntity(pos, 0, getUpdateTag());
	}

	@Override
	public NBTTagCompound getUpdateTag() {
		return writeToNBT(new NBTTagCompound());
	}

	@Override
	public void onDataPacket(NetworkManager net, SPacketUpdateTileEntity pkt) {
		readFromNBT(pkt.getNbtCompound());
		notifyBlockUpdate();
	}

	// IInventory

	@Override
	public String getName() {
		return "container." + Roost.MODID + ".roost";
	}

	@Override
	public boolean hasCustomName() {
		return false;
	}

	@Override
	public ITextComponent getDisplayName() {
		return new TextComponentTranslation(this.getName());
	}

	@Override
	public int getSizeInventory() {
		return 5;
	}

	@Override
	public int getSizeChickenInventory() {
		return 1;
	};

	@Override
	protected int requiredSeedsForDrop() {
		return 0;
	}

}
