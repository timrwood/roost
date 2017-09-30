package com.timwoodcreates.roost.integration.jei;

import java.util.LinkedList;
import java.util.List;

import com.timwoodcreates.roost.RoostBlocks;
import com.timwoodcreates.roost.RoostItems;
import com.timwoodcreates.roost.data.DataChicken;
import com.timwoodcreates.roost.integration.jei.breeding.BreedingRecipe;
import com.timwoodcreates.roost.integration.jei.breeding.BreedingRecipeCategory;
import com.timwoodcreates.roost.integration.jei.breeding.BreedingRecipeHandler;
import com.timwoodcreates.roost.integration.jei.catching.CatchingRecipe;
import com.timwoodcreates.roost.integration.jei.catching.CatchingRecipeCategory;
import com.timwoodcreates.roost.integration.jei.catching.CatchingRecipeHandler;
import com.timwoodcreates.roost.integration.jei.roosting.RoostingRecipe;
import com.timwoodcreates.roost.integration.jei.roosting.RoostingRecipeCategory;
import com.timwoodcreates.roost.integration.jei.roosting.RoostingRecipeHandler;

import mezz.jei.api.BlankModPlugin;
import mezz.jei.api.IJeiHelpers;
import mezz.jei.api.IModRegistry;
import mezz.jei.api.ISubtypeRegistry;
import mezz.jei.api.ISubtypeRegistry.ISubtypeInterpreter;
import mezz.jei.api.JEIPlugin;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

@JEIPlugin
public class RoostJEIPlugin extends BlankModPlugin {

	@Override
	public void registerItemSubtypes(ISubtypeRegistry subtypeRegistry) {
		subtypeRegistry.registerSubtypeInterpreter(RoostItems.ITEM_CHICKEN, new ItemChickenSubtypeInterpreter());
	}

	@Override
	public void register(IModRegistry registry) {
		IJeiHelpers jeiHelpers = registry.getJeiHelpers();

		List<BreedingRecipe> breedingRecipies = new LinkedList<BreedingRecipe>();
		List<CatchingRecipe> catchingRecipies = new LinkedList<CatchingRecipe>();
		List<RoostingRecipe> roostingRecipies = new LinkedList<RoostingRecipe>();

		registry.addRecipeCategories(new BreedingRecipeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new CatchingRecipeCategory(jeiHelpers.getGuiHelper()));
		registry.addRecipeCategories(new RoostingRecipeCategory(jeiHelpers.getGuiHelper()));

		registry.addRecipeHandlers(new BreedingRecipeHandler());
		registry.addRecipeHandlers(new CatchingRecipeHandler());
		registry.addRecipeHandlers(new RoostingRecipeHandler());

		for (DataChicken chicken : DataChicken.getAllChickens()) {
			if (chicken.hasParents()) breedingRecipies.add(new BreedingRecipe(chicken));
			roostingRecipies.add(new RoostingRecipe(chicken));
			catchingRecipies.add(new CatchingRecipe(chicken));
		}

		registry.addRecipes(breedingRecipies);
		registry.addRecipes(catchingRecipies);
		registry.addRecipes(roostingRecipies);

		registry.addRecipeCategoryCraftingItem(new ItemStack(RoostBlocks.BLOCK_BREEDER), BreedingRecipeCategory.ID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(RoostItems.ITEM_CATCHER), CatchingRecipeCategory.ID);
		registry.addRecipeCategoryCraftingItem(new ItemStack(RoostBlocks.BLOCK_ROOST), RoostingRecipeCategory.ID);
	}

	private class ItemChickenSubtypeInterpreter implements ISubtypeInterpreter {
		@Override
		public String getSubtypeInfo(ItemStack itemStack) {
			NBTTagCompound tagCompound = itemStack.getTagCompound();
			if (tagCompound == null) return null;
			int modId = tagCompound.getInteger(DataChicken.MOD_ID_KEY);
			int chickenId = tagCompound.getInteger(DataChicken.CHICKEN_ID_KEY);
			return modId + ":" + chickenId;
		}
	}
}
