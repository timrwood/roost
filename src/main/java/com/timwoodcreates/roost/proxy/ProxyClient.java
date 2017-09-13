package com.timwoodcreates.roost.proxy;

import com.timwoodcreates.roost.RoostBlocks;
import com.timwoodcreates.roost.RoostItems;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class ProxyClient extends ProxyCommon {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		super.preInit(e);

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RoostBlocks.BLOCK_ROOST), 0,
				new ModelResourceLocation("roost:roost", "inventory"));

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RoostBlocks.BLOCK_BREEDER), 0,
				new ModelResourceLocation("roost:breeder", "inventory"));

		ModelLoader.setCustomModelResourceLocation(Item.getItemFromBlock(RoostBlocks.BLOCK_COLLECTOR), 0,
				new ModelResourceLocation("roost:collector", "inventory"));

		ModelLoader.setCustomModelResourceLocation(RoostItems.ITEM_CATCHER, 0,
				new ModelResourceLocation("roost:catcher", "inventory"));

		ModelLoader.setCustomModelResourceLocation(RoostItems.ITEM_CHICKEN, 0,
				new ModelResourceLocation("roost:chicken", "inventory"));
	}
}
