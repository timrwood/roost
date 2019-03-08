package com.timwoodcreates.roost.proxy;

import java.util.LinkedList;
import java.util.List;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.RoostBlocks;
import com.timwoodcreates.roost.RoostItems;
import com.timwoodcreates.roost.gui.GuiHandler;
import com.timwoodcreates.roost.tileentity.TileEntityBreeder;
import com.timwoodcreates.roost.tileentity.TileEntityCollector;
import com.timwoodcreates.roost.tileentity.TileEntityRoost;

import net.minecraft.block.Block;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLLoadCompleteEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;

@Mod.EventBusSubscriber
public class ProxyCommon {

	private static List<Block> blocksToRegister = new LinkedList<Block>();
	private static List<Item> itemsToRegister = new LinkedList<Item>();

	public void preInit(FMLPreInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Roost.INSTANCE, new GuiHandler());

		registerBlocks();
		registerItems();
		registerTileEntities();
		registerRecipies();
	}

	public void loadComplete(FMLLoadCompleteEvent e) {
	}

	@SubscribeEvent
	public static void registerBlocks(RegistryEvent.Register<Block> event) {
		for (Block block : blocksToRegister) {
			event.getRegistry().register(block);
		}
	}

	@SubscribeEvent
	public static void registerItems(RegistryEvent.Register<Item> event) {
		for (Item item : itemsToRegister) {
			event.getRegistry().register(item);
		}
	}

	private void registerBlocks() {
		registerBlock(RoostBlocks.BLOCK_ROOST, "roost");
		registerBlock(RoostBlocks.BLOCK_BREEDER, "breeder");
		registerBlock(RoostBlocks.BLOCK_COLLECTOR, "collector");
	}

	private void registerItems() {
		registerItem(RoostItems.ITEM_CATCHER, "catcher");
		registerItem(RoostItems.ITEM_CHICKEN, "chicken");
	}

	private void registerTileEntities() {
		registerTileEntity(TileEntityRoost.class, "roost");
		registerTileEntity(TileEntityBreeder.class, "breeder");
		registerTileEntity(TileEntityCollector.class, "collector");
	}

	private void registerRecipies() {
		GameRegistry.addSmelting(RoostItems.ITEM_CHICKEN, new ItemStack(Items.COOKED_CHICKEN), 0.35f);
	}

	private void registerBlock(Block block, String name) {
		block.setUnlocalizedName(Roost.MODID + "." + name);
		block.setRegistryName(new ResourceLocation(Roost.MODID, name));
		block.setCreativeTab(Roost.TAB);
		blocksToRegister.add(block);

		ItemBlock item = new ItemBlock(block);
		item.setUnlocalizedName(block.getUnlocalizedName());
		item.setRegistryName(block.getRegistryName());
		itemsToRegister.add(item);
	}

	private void registerItem(Item item, String name) {
		item.setUnlocalizedName(Roost.MODID + "." + name);
		item.setRegistryName(new ResourceLocation(Roost.MODID, name));
		item.setCreativeTab(Roost.TAB);
		itemsToRegister.add(item);
	}

	private void registerTileEntity(Class<? extends TileEntity> tile, String name) {
		GameRegistry.registerTileEntity(tile, Roost.MODID + ":" + name);
	}
}
