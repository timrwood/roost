package com.timwoodcreates.roost.integration.jei.roosting;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class RoostingRecipeHandler implements IRecipeHandler<RoostingRecipe> {

	@Override
	public Class<RoostingRecipe> getRecipeClass() {
		return RoostingRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return RoostingRecipeCategory.ID;
	}

	@Override
	public String getRecipeCategoryUid(RoostingRecipe recipe) {
		return RoostingRecipeCategory.ID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(RoostingRecipe recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(RoostingRecipe recipe) {
		return true;
	}

}
