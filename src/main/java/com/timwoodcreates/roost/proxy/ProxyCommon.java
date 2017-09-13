package com.timwoodcreates.roost.proxy;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.RoostBlocks;
import com.timwoodcreates.roost.RoostItems;
import com.timwoodcreates.roost.gui.GuiHandler;
import com.timwoodcreates.roost.tileentity.TileEntityBreeder;
import com.timwoodcreates.roost.tileentity.TileEntityCollector;
import com.timwoodcreates.roost.tileentity.TileEntityRoost;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.ShapedOreRecipe;

public class ProxyCommon {

	public void preInit(FMLPreInitializationEvent e) {
		NetworkRegistry.INSTANCE.registerGuiHandler(Roost.INSTANCE, new GuiHandler());

		registerBlocks();
		registerItems();
		registerTileEntities();
		registerRecipies();
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
		registerShapedRecipe(RoostBlocks.BLOCK_ROOST, "WWW", "W W", "HHH", 'W', "plankWood", 'H', Blocks.HAY_BLOCK);
		registerShapedRecipe(RoostBlocks.BLOCK_BREEDER, "WWW", "WSW", "HHH", 'W', "plankWood", 'H', Blocks.HAY_BLOCK,
				'S', Items.WHEAT_SEEDS);
		registerShapedRecipe(RoostBlocks.BLOCK_COLLECTOR, "WCW", "WHW", "WOW", 'W', "plankWood", 'C',
				RoostItems.ITEM_CHICKEN, 'H', Blocks.HOPPER, 'O', "chest");
		registerShapedRecipe(RoostItems.ITEM_CATCHER, "E", "S", "F", 'E', "egg", 'S', "stickWood", 'F', "feather");
	}

	private void registerShapedRecipe(Block block, Object... recipe) {
		GameRegistry.addRecipe(new ShapedOreRecipe(block, recipe));
	}

	private void registerShapedRecipe(Item item, Object... recipe) {
		GameRegistry.addRecipe(new ShapedOreRecipe(item, recipe));
	}

	private void registerBlock(Block block, String name) {
		block.setUnlocalizedName(Roost.MODID + "." + name);
		block.setRegistryName(new ResourceLocation(Roost.MODID, name));
		block.setCreativeTab(Roost.TAB);
		GameRegistry.register(block);

		ItemBlock item = new ItemBlock(block);
		item.setUnlocalizedName(block.getUnlocalizedName());
		item.setRegistryName(block.getRegistryName());
		GameRegistry.register(item);
	}

	private void registerItem(Item item, String name) {
		item.setUnlocalizedName(Roost.MODID + "." + name);
		item.setRegistryName(new ResourceLocation(Roost.MODID, name));
		item.setCreativeTab(Roost.TAB);
		GameRegistry.register(item);
	}

	private void registerTileEntity(Class<? extends TileEntity> tile, String name) {
		GameRegistry.registerTileEntity(tile, Roost.MODID + ":" + name);
	}
}
