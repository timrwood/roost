package com.timwoodcreates.roost.integration.jei.catching;

import com.timwoodcreates.roost.data.DataChicken;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class CatchingRecipe extends BlankRecipeWrapper {

	private DataChicken chicken;

	public CatchingRecipe(DataChicken chickenIn) {
		chicken = chickenIn;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, chicken.buildCaughtFromStack());
		ingredients.setOutput(ItemStack.class, chicken.buildChickenStack());
	}

}
