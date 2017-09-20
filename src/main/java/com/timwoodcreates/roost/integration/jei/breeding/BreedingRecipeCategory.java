package com.timwoodcreates.roost.integration.jei.breeding;

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

public class BreedingRecipeCategory extends BlankRecipeCategory<IRecipeWrapper> {

	public static final String ID = "roost.breeding";
	protected static final int parentASlot = 0;
	protected static final int parentBSlot = 1;
	protected static final int childSlot = 2;
	private static final ResourceLocation JEI_GUI_TEXTURES = new ResourceLocation(Roost.MODID, "textures/gui/jei.png");

	private final IDrawable background;
	private final String localizedName;
	private final IDrawableAnimated hearts;

	public BreedingRecipeCategory(IGuiHelper guiHelper) {
		IDrawableStatic heartsDrawable = guiHelper.createDrawable(JEI_GUI_TEXTURES, 90, 0, 26, 12);
		hearts = guiHelper.createAnimatedDrawable(heartsDrawable, 200, IDrawableAnimated.StartDirection.LEFT, false);
		background = guiHelper.createDrawable(JEI_GUI_TEXTURES, 0, 0, 90, 18);
		localizedName = Translator.translateToLocal("gui.jei.roost.category.breeding");
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
		hearts.draw(minecraft, 41, 3);
	}

	@Override
	public void setRecipe(IRecipeLayout recipeLayout, IRecipeWrapper recipeWrapper, IIngredients ingredients) {
		IGuiItemStackGroup guiItemStacks = recipeLayout.getItemStacks();

		guiItemStacks.init(parentASlot, true, 0, 0);
		guiItemStacks.init(parentBSlot, true, 18, 0);
		guiItemStacks.init(childSlot, false, 72, 0);

		guiItemStacks.set(ingredients);
	}

}
