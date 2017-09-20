package com.timwoodcreates.roost.integration.jei.breeding;

import com.timwoodcreates.roost.data.DataChicken;

import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeWrapper;
import net.minecraft.item.ItemStack;

public class BreedingRecipe extends BlankRecipeWrapper {

	private DataChicken chicken;

	public BreedingRecipe(DataChicken chickenIn) {
		chicken = chickenIn;
	}

	@Override
	public void getIngredients(IIngredients ingredients) {
		ingredients.setInputs(ItemStack.class, chicken.buildParentChickenStack());
		ingredients.setOutput(ItemStack.class, chicken.buildChickenStack());
	}

}
