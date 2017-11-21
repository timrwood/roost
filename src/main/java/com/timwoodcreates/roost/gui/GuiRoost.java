package com.timwoodcreates.roost.gui;

import java.util.List;

import com.google.common.collect.Lists;
import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.inventory.ContainerRoost;
import com.timwoodcreates.roost.tileentity.TileEntityRoost;

import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.util.ResourceLocation;

public class GuiRoost extends GuiContainer {

	private static final ResourceLocation ROOST_GUI_TEXTURES = new ResourceLocation(Roost.MODID, "textures/gui/roost.png");

	private final TileEntityRoost roostInventory;

	public GuiRoost(InventoryPlayer playerInventory, TileEntityRoost roostInventory) {
		super(new ContainerRoost(playerInventory, roostInventory));
		this.roostInventory = roostInventory;
		this.xSize = 176;
		this.ySize = 133;
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		this.drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerForegroundLayer(int mouseX, int mouseY) {
		fontRenderer.drawString(roostInventory.getDisplayName().getUnformattedText(), 8, 6, 4210752);
		fontRenderer.drawString(I18n.format("container.inventory"), 8, ySize - 96 + 2, 4210752);
		int x = guiLeft;
		int y = (height - ySize) / 2;

		if (mouseX > x + 48 && mouseX < x + 74 && mouseY > y + 20 && mouseY < y + 36) {
			List<String> list = Lists.<String>newArrayList();
			list.add(roostInventory.getFormattedProgress());
			drawHoveringText(list, mouseX - x, mouseY - y);
		}
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
		mc.getTextureManager().bindTexture(ROOST_GUI_TEXTURES);
		int x = guiLeft;
		int y = (height - ySize) / 2;

		drawTexturedModalRect(x, y, 0, 0, xSize, ySize);
		drawTexturedModalRect(x + 48, y + 20, 176, 0, getProgressWidth(), 16);
	}

	private int getProgressWidth() {
		double progress = roostInventory.getProgress();
		return progress == 0.0D ? 0 : 1 + (int) (progress * 25);
	}

}
