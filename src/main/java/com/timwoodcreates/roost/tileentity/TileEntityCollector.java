package com.timwoodcreates.roost.tileentity;

import javax.annotation.Nullable;

import com.timwoodcreates.roost.Roost;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityHopper;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.InvWrapper;

public class TileEntityCollector extends TileEntity implements ISidedInventory, ITickable {

	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
	private int searchOffset = 0;

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			updateSearchOffset();
			gatherItems();
		}
	}

	private void updateSearchOffset() {
		searchOffset = (searchOffset + 1) % 27;
	}

	private void gatherItems() {
		for (int x = -4; x < 5; x++) {
			int y = searchOffset / 9;
			int z = (searchOffset % 9) - 4;
			gatherItemAtPos(pos.add(x, y, z));
		}
	}

	private void gatherItemAtPos(BlockPos pos) {
		TileEntity tileEntity = getWorld().getTileEntity(pos);
		if (!(tileEntity instanceof TileEntityRoost)) return;

		TileEntityRoost tileEntityRoost = (TileEntityRoost) getWorld().getTileEntity(pos);

		int[] slots = tileEntityRoost.getSlotsForFace(null);

		for (int i : slots) {
			if (pullItemFromSlot(tileEntityRoost, i)) return;
		}
	}

	private boolean pullItemFromSlot(TileEntityRoost tileRoost, int index) {
		ItemStack itemStack = tileRoost.getStackInSlot(index);

		if (!itemStack.isEmpty() && tileRoost.canExtractItem(index, itemStack, null)) {
			ItemStack itemStack1 = itemStack.copy();
			ItemStack itemStack2 = TileEntityHopper.putStackInInventoryAllSlots(tileRoost, this,
					tileRoost.decrStackSize(index, 1), null);

			if (itemStack2.isEmpty()) {
				tileRoost.markDirty();
				markDirty();
				return true;
			}

			tileRoost.setInventorySlotContents(index, itemStack1);
		}

		return false;
	}

	@Override
	public String getName() {
		return "container." + Roost.MODID + ".collector";
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
		return 27;
	}

	@Override
	public ItemStack getStackInSlot(int index) {
		return inventory.get(index);
	}

	@Override
	public boolean isEmpty() {
		for (ItemStack itemStack : inventory) {
			if (!itemStack.isEmpty()) return false;
		}
		return true;
	}

	@Override
	public ItemStack decrStackSize(int index, int count) {
		return ItemStackHelper.getAndSplit(inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		return ItemStackHelper.getAndRemove(inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inventory.set(index, stack);

		if (stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		if (getWorld().getTileEntity(pos) != this) {
			return false;
		} else {
			return player.getDistanceSq(pos.getX() + 0.5D, pos.getY() + 0.5D, pos.getZ() + 0.5D) <= 64.0D;
		}
	}

	@Override
	public void openInventory(EntityPlayer player) {
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
		return true;
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return true;
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		return true;
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public int getField(int id) {
		return 0;
	}

	@Override
	public void setField(int id, int value) {
	}

	@Override
	public int getFieldCount() {
		return 0;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int[] itemSlots = new int[27];
		for (int i = 0; i < 27; i++) {
			itemSlots[i] = i;
		}
		return itemSlots;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		ItemStackHelper.loadAllItems(compound, inventory);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, inventory);
		return compound;
	}

	private IItemHandler itemHandler;

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (itemHandler == null) itemHandler = new InvWrapper(this);
			return (T) itemHandler;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

}
