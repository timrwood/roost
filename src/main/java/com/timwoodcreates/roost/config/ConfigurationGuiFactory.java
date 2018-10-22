package com.timwoodcreates.roost.config;

import com.timwoodcreates.roost.Roost;

import net.minecraft.client.gui.GuiScreen;
import net.minecraftforge.common.config.ConfigCategory;
import net.minecraftforge.common.config.ConfigElement;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.DefaultGuiFactory;
import net.minecraftforge.fml.client.config.GuiConfig;
import net.minecraftforge.fml.client.config.IConfigElement;

import java.util.List;

public class ConfigurationGuiFactory extends DefaultGuiFactory {
	public ConfigurationGuiFactory() {
		super(Roost.MODID, GuiConfig.getAbridgedConfigPath(ConfigurationHandler.config.toString()));
	}

	@Override
	public GuiScreen createConfigGui(GuiScreen parent) {
		final ConfigCategory category = ConfigurationHandler.config.getCategory(Configuration.CATEGORY_GENERAL);
		final List<IConfigElement> elements = new ConfigElement(category).getChildElements();

		return new GuiConfig(parent, elements, this.modid, false, false, this.title);
	}
}
