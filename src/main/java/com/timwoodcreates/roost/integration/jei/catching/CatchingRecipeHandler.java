package com.timwoodcreates.roost.integration.jei.catching;

import mezz.jei.api.recipe.IRecipeHandler;
import mezz.jei.api.recipe.IRecipeWrapper;

public class CatchingRecipeHandler implements IRecipeHandler<CatchingRecipe> {

	@Override
	public Class<CatchingRecipe> getRecipeClass() {
		return CatchingRecipe.class;
	}

	@Override
	public String getRecipeCategoryUid() {
		return CatchingRecipeCategory.ID;
	}

	@Override
	public String getRecipeCategoryUid(CatchingRecipe recipe) {
		return CatchingRecipeCategory.ID;
	}

	@Override
	public IRecipeWrapper getRecipeWrapper(CatchingRecipe recipe) {
		return recipe;
	}

	@Override
	public boolean isRecipeValid(CatchingRecipe recipe) {
		return true;
	}

}
