package com.timwoodcreates.roost;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;

public class RoostTab extends CreativeTabs {

	public RoostTab() {
		super("roost");
	}

	@Override
	public Item getTabIconItem() {
		return RoostItems.ITEM_CHICKEN;
	}

}
