package com.timwoodcreates.roost.integration.jei.roosting;

import com.timwoodcreates.roost.data.DataChicken;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class RoostingRecipe extends BlankRecipeWrapper {

	private DataChicken chicken;

	public RoostingRecipe(DataChicken chickenIn) {
		chicken = chickenIn;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInput(ItemStack.class, chicken.buildChickenStack());
		ingredients.setOutput(ItemStack.class, chicken.createDropStack());
	}

}
