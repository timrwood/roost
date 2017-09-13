package com.timwoodcreates.roost.util;

import net.minecraft.inventory.IInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;

public class UtilNBTTagCompoundHelper {

	public static void readInventoryFromNBT(IInventory inventory, NBTTagCompound tagCompound) {
		int count = inventory.getSizeInventory();
		NBTTagList tagList = tagCompound.getTagList("Items", 10);
		inventory.clear();

		for (int i = 0; i < tagList.tagCount(); i++) {
			NBTTagCompound slotCompound = tagList.getCompoundTagAt(i);
			int slot = slotCompound.getByte("Slot");

			if (slot >= 0 && slot < count) {
				inventory.setInventorySlotContents(slot, ItemStack.loadItemStackFromNBT(slotCompound));
			}
		}
	}

	public static void writeInventoryToNBT(IInventory inventory, NBTTagCompound tagCompound) {
		int count = inventory.getSizeInventory();
		NBTTagList tagList = new NBTTagList();

		for (int i = 0; i < count; ++i) {
			ItemStack stack = inventory.getStackInSlot(i);
			if (stack != null) {
				NBTTagCompound slotCompound = new NBTTagCompound();
				slotCompound.setByte("Slot", (byte) i);
				stack.writeToNBT(slotCompound);
				tagList.appendTag(slotCompound);
			}
		}

		tagCompound.setTag("Items", tagList);
	}

}
