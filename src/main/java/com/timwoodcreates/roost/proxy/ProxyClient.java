package com.timwoodcreates.roost.proxy;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.RoostBlocks;
import com.timwoodcreates.roost.RoostItems;

import com.timwoodcreates.roost.RoostTextures;
import com.timwoodcreates.roost.data.DataChicken;
import com.timwoodcreates.roost.render.ModelBlockRoost;
import com.timwoodcreates.roost.render.ModelItemChicken;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.client.model.ModelLoaderRegistry;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;

import java.util.Objects;

@Mod.EventBusSubscriber(Side.CLIENT)
public class ProxyClient extends ProxyCommon {

	@Override
	public void preInit(FMLPreInitializationEvent e) {
		ModelLoaderRegistry.registerLoader(ModelItemChicken.ModelItemChickenLoader.INSTANCE);
		ModelLoaderRegistry.registerLoader(ModelBlockRoost.ModelBlockRoostLoader.INSTANCE);
		super.preInit(e);
	}

	@SubscribeEvent
	public static void registerModels(ModelRegistryEvent event) {
		setModel(RoostBlocks.BLOCK_ROOST);
		setModel(RoostBlocks.BLOCK_BREEDER);
		setModel(RoostBlocks.BLOCK_COLLECTOR);
		setModel(RoostItems.ITEM_CATCHER);
		setModel(RoostItems.ITEM_CHICKEN);
	}

	@Override
	public void loadComplete(FMLLoadCompleteEvent e) {
		// Only reload resources if another mod (e.g. ContentTweaker) has added chickens that weren't initially loaded
		DataChicken.getAllChickens().stream()
				.map(DataChicken::getTextureName)
				.filter(Objects::nonNull)
				.filter(item -> !RoostTextures.stockTextures.contains(item))
				.findAny()
				.ifPresent(item -> {
					Roost.LOGGER.warn("Chicken \"" + item + "\" was not registered during initial resource load. **RELOADING RESOURCES AGAIN**");
					Minecraft.getMinecraft().refreshResources();
				});

	}

	private static void setModel(Block block) {
		setModel(Item.getItemFromBlock(block));
	}

	private static void setModel(Item item) {
		ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "inventory"));
	}
}
