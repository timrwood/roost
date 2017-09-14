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
		return collectorInventory.isUsableByPlayer(playerIn);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < collectorInventory.getSizeInventory()) {
				if (!mergeItemStack(current, collectorInventory.getSizeInventory(), inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(current, 0, collectorInventory.getSizeInventory(), false)) {
				return ItemStack.EMPTY;
			}

			if (current.isEmpty()) {
				slot.putStack(ItemStack.EMPTY);
			} else {
				slot.onSlotChanged();
			}
		}

		return previous;
	}

}
