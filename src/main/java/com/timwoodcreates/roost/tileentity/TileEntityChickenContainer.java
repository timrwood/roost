package com.timwoodcreates.roost.tileentity;

import java.text.DecimalFormat;

import javax.annotation.Nullable;

import com.timwoodcreates.roost.data.DataChicken;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;

public abstract class TileEntityChickenContainer extends TileEntity implements ISidedInventory, ITickable {

	private static final DecimalFormat FORMATTER = new DecimalFormat("0.0%");

	private NonNullList<ItemStack> inventory = NonNullList.<ItemStack>withSize(getSizeInventory(), ItemStack.EMPTY);
	private boolean mightNeedToUpdateChickenInfo = true;
	private boolean skipNextTimerReset = false;
	private int timeUntilNextDrop = 0;
	private int timeElapsed = 0;

	private DataChicken[] chickenData = new DataChicken[getSizeChickenInventory()];
	private boolean fullOfChickens = false;

	// Update

	@Override
	public void update() {
		if (!getWorld().isRemote) {
			updateChickenInfoIfNeeded();
			updateTimerIfNeeded();
			spawnChickenDropIfNeeded();
			skipNextTimerReset = false;
		}
	}

	private void updateChickenInfoIfNeeded() {
		if (!mightNeedToUpdateChickenInfo) return;

		boolean fullOfChickensNow = true;

		for (int i = 0; i < chickenData.length; i++) {
			updateChickenInfoIfNeededForSlot(i);
			fullOfChickensNow = fullOfChickensNow && (chickenData[i] != null);
		}

		if (fullOfChickensNow != fullOfChickens) {
			fullOfChickens = fullOfChickensNow;
			isFullOfChickensChanged(fullOfChickens);
		}
		mightNeedToUpdateChickenInfo = false;
	}

	private void updateChickenInfoIfNeededForSlot(int slot) {
		DataChicken oldChicken = chickenData[slot];
		DataChicken newChicken = createChickenData(slot);

		boolean wasCreated = oldChicken == null && newChicken != null;
		boolean wasDeleted = oldChicken != null && newChicken == null;
		boolean wasChanged = oldChicken != null && newChicken != null && !oldChicken.isEqual(newChicken);

		if (wasCreated || wasChanged || wasDeleted) {
			chickenData[slot] = newChicken;
			if (!skipNextTimerReset) resetTimer();
		}
	}

	protected DataChicken getChickenData(int slot) {
		if (slot >= chickenData.length || slot < 0) return null;
		return chickenData[slot];
	}

	protected DataChicken createChickenData(int slot) {
		return DataChicken.getDataFromStack(getStackInSlot(slot));
	}

	private void updateTimerIfNeeded() {
		if (fullOfChickens && hasEnoughSeeds() && !outputIsFull()) {
			timeElapsed += getTimeElapsed();
			markDirty();
		}
	}

	private int getTimeElapsed() {
		int time = Integer.MAX_VALUE;
		for (int i = 0; i < chickenData.length; i++) {
			if (chickenData[i] == null) return 0;
			time = Math.min(time, chickenData[i].getAddedTime(getStackInSlot(i)));
		}
		return time;
	}

	public double getProgress() {
		return timeUntilNextDrop == 0 ? 0 : (double) timeElapsed / (double) timeUntilNextDrop;
	}

	public String getFormattedProgress() {
		return formatProgress(getProgress());
	}

	public String formatProgress(double progress) {
		return FORMATTER.format(progress);
	}

	private void spawnChickenDropIfNeeded() {
		if (fullOfChickens && hasEnoughSeeds() && (timeElapsed >= timeUntilNextDrop)) {
			if (timeUntilNextDrop > 0) {
				decrStackSize(getSizeChickenInventory(), requiredSeedsForDrop());
				spawnChickenDrop();
			}
			resetTimer();
		}
	}

	private void resetTimer() {
		timeElapsed = 0;
		timeUntilNextDrop = 0;
		for (int i = 0; i < chickenData.length; i++) {
			if (chickenData[i] != null) {
				timeUntilNextDrop = Math.max(timeUntilNextDrop, chickenData[i].getLayTime());
			}
		}
		markDirty();
	}

	protected abstract void isFullOfChickensChanged(boolean isFull);

	protected abstract void spawnChickenDrop();

	protected abstract int getSizeChickenInventory();

	protected abstract int requiredSeedsForDrop();

	private boolean outputIsFull() {
		int max = getSizeInventory();

		for (int i = getOutputStackIndex(); i < max; i++) {
			ItemStack stack = getStackInSlot(i);
			if (stack.getCount() < stack.getMaxStackSize()) return false;
		}
		return true;
	}

	protected boolean hasEnoughSeeds() {
		int needed = requiredSeedsForDrop();
		if (needed == 0) return true;
		ItemStack stack = getStackInSlot(getSizeChickenInventory());
		return stack.getCount() >= needed;
	}

	private int getOutputStackIndex() {
		if (requiredSeedsForDrop() > 0) {
			return getSizeChickenInventory() + 1;
		}
		return getSizeChickenInventory();
	}

	protected ItemStack putStackInOutput(ItemStack stack) {
		int max = getSizeInventory();

		for (int i = getOutputStackIndex(); i < max && !stack.isEmpty(); i++) {
			stack = insertStack(stack, i);
		}

		markDirty();

		return stack;
	}

	private ItemStack insertStack(ItemStack stack, int index) {
		int max = Math.min(stack.getMaxStackSize(), getInventoryStackLimit());

		ItemStack outputStack = getStackInSlot(index);
		if (outputStack.isEmpty()) {
			if (stack.getCount() >= max) {
				setInventorySlotContents(index, stack);
				stack = ItemStack.EMPTY;
			} else {
				setInventorySlotContents(index, stack.splitStack(max));
			}
		} else if (canCombine(outputStack, stack)) {
			if (outputStack.getCount() < max) {
				int itemsToMove = Math.min(stack.getCount(), max - outputStack.getCount());
				stack.shrink(itemsToMove);
				outputStack.grow(itemsToMove);
			}
		}

		return stack;
	}

	private boolean canCombine(ItemStack a, ItemStack b) {
		if (a.getItem() != b.getItem()) return false;
		if (a.getMetadata() != b.getMetadata()) return false;
		if (a.getCount() > a.getMaxStackSize()) return false;
		return ItemStack.areItemStackTagsEqual(a, b);
	}

	@Override
	public int getSizeInventory() {
		return 5;
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
		if (index < getSizeChickenInventory()) mightNeedToUpdateChickenInfo = true;
		return ItemStackHelper.getAndSplit(inventory, index, count);
	}

	@Override
	public ItemStack removeStackFromSlot(int index) {
		if (index < getSizeChickenInventory()) mightNeedToUpdateChickenInfo = true;
		return ItemStackHelper.getAndRemove(inventory, index);
	}

	@Override
	public void setInventorySlotContents(int index, ItemStack stack) {
		inventory.set(index, stack);

		if (stack.getCount() > getInventoryStackLimit()) {
			stack.setCount(getInventoryStackLimit());
		}

		if (index < getSizeChickenInventory()) mightNeedToUpdateChickenInfo = true;
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
		return isItemValidForSlot(index, itemStackIn);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return index >= getOutputStackIndex();
	}

	@Override
	public boolean isItemValidForSlot(int index, ItemStack stack) {
		if (index < getSizeChickenInventory()) return DataChicken.isChicken(stack);
		if (index < getOutputStackIndex()) return isSeed(stack);
		return false;
	}

	public static boolean isSeed(ItemStack stack) {
		Item item = stack.getItem();
		return (item == Items.WHEAT_SEEDS || item == Items.MELON_SEEDS || item == Items.PUMPKIN_SEEDS || item == Items.BEETROOT_SEEDS);
	}

	@Override
	public void clear() {
		inventory.clear();
	}

	@Override
	public int getField(int id) {
		switch (id) {
		case 0:
			return timeUntilNextDrop;
		case 1:
			return timeElapsed;
		default:
			return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
		case 0:
			timeUntilNextDrop = value;
			break;
		case 1:
			timeElapsed = value;
			break;
		}
	}

	@Override
	public int getFieldCount() {
		return 2;
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		int count = getSizeInventory();
		int[] itemSlots = new int[count];

		for (int i = 0; i < count; i++) {
			itemSlots[i] = i;
		}

		return itemSlots;
	}

	@Override
	public void readFromNBT(NBTTagCompound compound) {
		super.readFromNBT(compound);
		clear();
		ItemStackHelper.loadAllItems(compound, inventory);
		timeUntilNextDrop = compound.getInteger("TimeUntilNextChild");
		timeElapsed = compound.getInteger("TimeElapsed");
		skipNextTimerReset = true;
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
		super.writeToNBT(compound);
		ItemStackHelper.saveAllItems(compound, inventory);
		compound.setInteger("TimeUntilNextChild", timeUntilNextDrop);
		compound.setInteger("TimeElapsed", timeElapsed);
		return compound;
	}

	private IItemHandler itemHandler;

	@SuppressWarnings("unchecked")
	@Override
	@Nullable
	public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
		if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
			if (itemHandler == null) itemHandler = new SidedInvWrapper(this, EnumFacing.DOWN);
			return (T) itemHandler;
		}
		return super.getCapability(capability, facing);
	}

	@Override
	public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
		return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
	}

}
