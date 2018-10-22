package com.timwoodcreates.roost.inventory.slot;

import com.timwoodcreates.roost.tileentity.TileEntityChickenContainer;

import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class SlotSeeds extends Slot {
	public SlotSeeds(IInventory inventoryIn, int index, int xPosition, int yPosition) {
		super(inventoryIn, index, xPosition, yPosition);
	}

	@Override
	public void onSlotChanged() {
		super.onSlotChanged();
		if (this.inventory instanceof TileEntityChickenContainer) {
			((TileEntityChickenContainer) this.inventory).willNeedToUpdateChickenInfo();
		}
	}

	@Override
	public boolean isItemValid(ItemStack stack) {
		return TileEntityChickenContainer.isSeed(stack);
	}
}
