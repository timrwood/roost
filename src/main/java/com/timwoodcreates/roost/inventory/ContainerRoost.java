package com.timwoodcreates.roost.inventory;

import com.timwoodcreates.roost.inventory.slot.SlotChicken;
import com.timwoodcreates.roost.inventory.slot.SlotReadOnly;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IContainerListener;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ContainerRoost extends Container {

	private final IInventory tileRoost;
	private int progress;

	public ContainerRoost(InventoryPlayer playerInventory, IInventory roostInventory) {
		this.tileRoost = roostInventory;

		addSlotToContainer(new SlotChicken(roostInventory, 0, 26, 20));

		for (int i = 0; i < 4; ++i) {
			addSlotToContainer(new SlotReadOnly(roostInventory, i + 1, 80 + i * 18, 20));
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
		return tileRoost.isUsableByPlayer(playerIn);
	}

	@Override
	public void addListener(IContainerListener listener) {
		super.addListener(listener);
		listener.sendAllWindowProperties(this, tileRoost);
	}

	@Override
	public void detectAndSendChanges() {
		super.detectAndSendChanges();

		for (int i = 0; i < listeners.size(); ++i) {
			IContainerListener listener = listeners.get(i);

			if (progress != tileRoost.getField(0)) {
				listener.sendWindowProperty(this, 0, tileRoost.getField(0));
			}
		}

		progress = tileRoost.getField(0);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void updateProgressBar(int id, int data) {
		tileRoost.setField(id, data);
	}

	@Override
	public ItemStack transferStackInSlot(EntityPlayer playerIn, int fromSlot) {
		ItemStack previous = ItemStack.EMPTY;
		Slot slot = inventorySlots.get(fromSlot);

		if (slot != null && slot.getHasStack()) {
			ItemStack current = slot.getStack();
			previous = current.copy();

			if (fromSlot < tileRoost.getSizeInventory()) {
				if (!mergeItemStack(current, tileRoost.getSizeInventory(), inventorySlots.size(), true)) {
					return ItemStack.EMPTY;
				}
			} else if (!mergeItemStack(current, 0, 1, false)) {
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
