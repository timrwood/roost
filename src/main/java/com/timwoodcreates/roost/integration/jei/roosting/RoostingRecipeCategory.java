package com.timwoodcreates.roost.integration.jei.roosting;

import com.timwoodcreates.roost.Roost;

import mezz.jei.api.IGuiHelper;
import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableAnimated;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.IGuiItemStackGroup;
import mezz.jei.api.gui.IRecipeLayout;
import mezz.jei.api.ingredients.IIngredients;
import mezz.jei.api.recipe.BlankRecipeCategory;
import mezz.jei.api.recipe.IRecipeWrapper;
import mezz.jei.util.Translator;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;

public class RoostingRecipeCategory extends BlankRecipeCategory<IRecipeWrapper> {

	public static final String ID = "roost.roosting";
	protected static final int inputSlot = 0;
	protected static final int outputSlot = 1;
	private static final ResourceLocation JEI_GUI_TEXTURES = new ResourceLocation(Roost.MODID, "textures/gui/jei.png");

	private final IDrawable background;
	private final String localizedName;
	private final IDrawableAnimated arrow;

	public RoostingRecipeCategory(IGuiHelper guiHelper) {
		IDrawableStatic arrowDrawable = guiHelper.createDrawable(JEI_GUI_TEXTURES, 72, 18, 26, 16);
		arrow = guiHelper.createAnimatedDrawable(arrowDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
		background = guiHelper.createDrawable(JEI_GUI_TEXTURES, 0, 18, 72, 18);
		localizedName = Translator.translateToLocal("gui.jei.roost.category.roosting");
	}

	@Override
	public String getUid() {
		return ID;
	}

	@Override
	public String getTitle() {
		return localizedName;
	}

	@Override
	public String getModName() {
		return Roost.NAME;
	}

	@Override
	public IDrawable getBackground() {
		return background;
	}

	@Override
	public void drawExtras(Minecraft minecraft) {
		arrow.draw(minecraft, 23, 1);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(inputSlot, true, 0, 0);
		guiItemStacks.init(outputSlot, false, 54, 0);

		guiItemStacks.set(ingredients);
	}

}
