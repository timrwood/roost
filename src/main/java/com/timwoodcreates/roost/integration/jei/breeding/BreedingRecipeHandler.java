package com.timwoodcreates.roost.integration.jei.breeding;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class BreedingRecipeHandler implements IRecipeHandler<BreedingRecipe> {

	@Override
	public Class<BreedingRecipe> getRecipeClass() {
		return BreedingRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return BreedingRecipeCategory.ID;
	}

	@Override
	public String getRecipeCategoryUid(BreedingRecipe recipe) {
		return BreedingRecipeCategory.ID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(BreedingRecipe recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(BreedingRecipe recipe) {
		return true;
	}

}
