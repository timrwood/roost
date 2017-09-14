package com.timwoodcreates.roost;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;

public class RoostTab extends CreativeTabs {

	public RoostTab() {
		super("roost");
	}

	@Override
	public ItemStack getTabIconItem() {
		return new ItemStack(RoostItems.ITEM_CHICKEN);
	}

}
