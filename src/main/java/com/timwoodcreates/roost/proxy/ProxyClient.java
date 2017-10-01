package com.timwoodcreates.roost.proxy;

import com.timwoodcreates.roost.RoostBlocks;
import com.timwoodcreates.roost.RoostItems;

import net.minecraft.block.Block;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ProxyClient extends ProxyCommon {

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		setModel(RoostBlocks.BLOCK_ROOST);
		setModel(RoostBlocks.BLOCK_BREEDER);
		setModel(RoostBlocks.BLOCK_COLLECTOR);
		setModel(RoostItems.ITEM_CATCHER);
		setModel(RoostItems.ITEM_CHICKEN);
	}

	private static void setModel(Block block) {
		setModel(Item.getItemFromBlock(block));
	}

	private static void setModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
