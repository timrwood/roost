package com.timwoodcreates.roost.inventory;

import com.timwoodcreates.roost.inventory.slot.SlotChicken;
import com.timwoodcreates.roost.inventory.slot.SlotReadOnly;
import com.timwoodcreates.roost.inventory.slot.SlotSeeds;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerBreeder extends Container {

	private final IInventory breederInventory;
	private int progress;

	public ContainerBreeder(InventoryPlayer playerInventory, IInventory breederInventory) {
		this.breederInventory = breederInventory;

		addSlotToContainer(new SlotChicken(breederInventory, 0, 44, 20));
		addSlotToContainer(new SlotChicken(breederInventory, 1, 62, 20));
		addSlotToContainer(new SlotSeeds(breederInventory, 2, 8, 20));

		for (int i = 0; i < 3; ++i) {
			addSlotToContainer(new SlotReadOnly(breederInventory, i + 3, 116 + i * 18, 20));
		}

		for (int i = 0; i < 3; ++i) {
			for (int j = 0; j < 9; ++j) {
				addSlotToContainer(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 51 + i * 18));
			}
		}

		for (int k = 0; k < 9; ++k) {
			addSlotToContainer(new Slot(playerInventory, k, 8 + k * 18, 109));
		}
	}

	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		return breederInventory.isUsableByPlayer(playerIn);
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, this.breederInventory);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < listeners.size(); ++i) {
			IContainerListener listener = listeners.get(i);

			if (progress != breederInventory.getField(0)) {
				listener.sendWindowProperty(this, 0, breederInventory.getField(0));
			}
		}

		progress = breederInventory.getField(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		breederInventory.setField(id, data);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < breederInventory.getSizeInventory()) {
				if (!mergeItemStack(current, breederInventory.getSizeInventory(), inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(current, 0, 3, false)) {
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
