package com.timwoodcreates.roost.gui;

import java.text.DecimalFormat;
import java.util.List;

import com.google.common.collect.Lists;
import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.inventory.ContainerBreeder;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.util.ResourceLocation;

public class GuiBreeder extends GuiContainer {

	private static final ResourceLocation BREEDER_GUI_TEXTURES = new ResourceLocation(Roost.MODID,
			"textures/gui/breeder.png");

	private static final DecimalFormat FORMATTER = new DecimalFormat("0.0%");

	private final IInventory breederInventory;

	public GuiBreeder(InventoryPlayer playerInventory, IInventory breederInventory) {
		super(new ContainerBreeder(playerInventory, breederInventory));
		this.breederInventory = breederInventory;
		this.xSize = 176;
		this.ySize = 133;
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRendererObj.drawString(breederInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		fontRendererObj.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
		int x = guiLeft;
		int y = (height - ySize) / 2;

		if (mouseX > x + 84 && mouseX < x + 110 && mouseY > y + 22 && mouseY < y + 34) {
			List<String> list = Lists.<String>newArrayList();
			list.add(FORMATTER.format(getProgress()));
			drawHoveringText(list, mouseX - x, mouseY - y);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(BREEDER_GUI_TEXTURES);
		int x = guiLeft;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x + 84, y + 22, 176, 0, getProgressWidth(), 12);
	}

	private int getProgressWidth() {
		double progress = getProgress();
		return progress == 0.0D ? 0 : 1 + (int) (progress * 25);
	}

	private double getProgress() {
		double full = breederInventory.getField(0);
		return full == 0 ? 0 : breederInventory.getField(1) / full;
	}

}
