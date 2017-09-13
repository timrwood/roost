package com.timwoodcreates.roost.inventory;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class ContainerCollector extends Container {

	private final IInventory collectorInventory;

	public ContainerCollector(InventoryPlayer playerInventory, IInventory collectorInventory) {
		this.collectorInventory = collectorInventory;

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(collectorInventory, x + y * 9, 8 + x * 18, 18 + y * 18));
			}
		}

		for (int y = 0; y < 3; y++) {
			for (int x = 0; x < 9; x++) {
				this.addSlotToContainer(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 85 + y * 18));
			}
		}

		for (int x = 0; x < 9; x++) {
			this.addSlotToContainer(new Slot(playerInventory, x, 8 + x * 18, 143));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return collectorInventory.isUseableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = null;
		Slot slot = inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < 27) {
				if (!this.mergeItemStack(current, 27, 63, true)) {
					return null;
				}
			} else if (!this.mergeItemStack(current, 0, 27, false)) {
				return null;
			}

			if (current.stackSize == 0) {
				slot.putStack((ItemStack) null);
			} else {
				slot.onSlotChanged();
			}

			if (current.stackSize == previous.stackSize) {
				return null;
			}

			slot.onPickupFromSlot(playerIn, current);
		}
		return previous;
	}

}
