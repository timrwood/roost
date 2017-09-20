package com.timwoodcreates.roost.integration.jei.catching;

import mezz.jei.api.gui.IDrawable;
import mezz.jei.api.gui.IDrawableStatic;
import mezz.jei.api.gui.ITickTimer;
import net.minecraft.client.Minecraft;

public class CatchingRecipeDrawable implements IDrawable {
	private final IDrawableStatic drawable;
	private final ITickTimer tickTimer;

	public CatchingRecipeDrawable(IDrawableStatic drawable, ITickTimer tickTimer) {
		this.drawable = drawable;
		this.tickTimer = tickTimer;
	}

	@Override
	public int getWidth() {
		return 24;
	}

	@Override
	public int getHeight() {
		return 14;
	}

	@Override
	public void draw(Minecraft minecraft) {
		draw(minecraft, 0, 0);
	}

	@Override
	public void draw(Minecraft minecraft, int xOffset, int yOffset) {
		int animationValue = tickTimer.getValue();
		int maxValue = tickTimer.getMaxValue();
		int maskTop = 14 * animationValue;
		int maskBottom = 14 * (maxValue - animationValue - 1);
		drawable.draw(minecraft, xOffset, yOffset - maskTop, maskTop, maskBottom, 0, 0);
	}

}
