package com.timwoodcreates.roost.inventory.slot;

import com.timwoodcreates.roost.data.DataChicken;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotChicken extends Slot {

	public SlotChicken(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return DataChicken.isChicken(stack);
	}
}
