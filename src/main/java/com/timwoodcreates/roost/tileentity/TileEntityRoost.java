package com.timwoodcreates.roost.tileentity;

import java.util.List;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.RoostConfig;
import com.timwoodcreates.roost.data.DataChicken;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentTranslation;

public class TileEntityRoost extends TileEntityChickenContainer {

	private static final String CHICKEN_KEY = "Chicken";
	private static final String COMPLETE_KEY = "Complete";
	private static int CHICKEN_SLOT = 0;

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

		if (!isItemValidForSlot(CHICKEN_SLOT, newStack)) {
			return false;
		}

		if (oldStack.isEmpty()) {
			setInventorySlotContents(CHICKEN_SLOT, newStack.splitStack(16));
			markDirty();
			playPutChickenInSound();
			return true;
		}

		if (oldStack.isItemEqual(newStack) && ItemStack.areItemStackTagsEqual(oldStack, newStack)) {
			int itemsAfterAdding = Math.min(oldStack.getCount() + newStack.getCount(), 16);
			int itemsToAdd = itemsAfterAdding - oldStack.getCount();
			if (itemsToAdd > 0) {
				newStack.splitStack(itemsToAdd);
				oldStack.grow(itemsToAdd);
				markDirty();
				playPutChickenInSound();
				return true;
			}
		}

		return false;
	}

	public boolean pullChickenOut(EntityPlayer playerIn) {
		ItemStack spawnStack = getStackInSlot(CHICKEN_SLOT);

		if (spawnStack.isEmpty()) {
			return false;
		}

		playerIn.inventory.addItemStackToInventory(spawnStack);
		setInventorySlotContents(CHICKEN_SLOT, ItemStack.EMPTY);

		markDirty();
		playPullChickenOutSound();

		return true;
	}

	private void playPutChickenInSound() {
		getWorld().playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_ADD_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	private void playPullChickenOutSound() {
		getWorld().playSound(null, pos, SoundEvents.ENTITY_ITEMFRAME_REMOVE_ITEM, SoundCategory.BLOCKS, 1.0F, 1.0F);
	}

	public void addInfoToTooltip(List<String> tooltip, NBTTagCompound tag) {
		if (tag.hasKey(CHICKEN_KEY)) {
			DataChicken chicken = DataChicken.getDataFromTooltipNBT(tag.getCompoundTag(CHICKEN_KEY));
			tooltip.add(chicken.getDisplaySummary());
		}

		if (tag.hasKey(COMPLETE_KEY)) {
			tooltip.add(new TextComponentTranslation("container.roost.progress", formatProgress(tag.getDouble(COMPLETE_KEY))).getFormattedText());
		}
	}

	public void storeInfoForTooltip(NBTTagCompound tag) {
		DataChicken chicken = getChickenData(CHICKEN_SLOT);
		if (chicken == null) return;
		tag.setTag(CHICKEN_KEY, chicken.buildTooltipNBT());
		tag.setDouble(COMPLETE_KEY, getProgress());
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
		return new TextComponentTranslation(getName());
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

	@Override
	protected double speedMultiplier() {
		return RoostConfig.roostSpeed;
	}

}
