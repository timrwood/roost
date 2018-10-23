package com.timwoodcreates.roost.config;

import java.io.File;

import com.timwoodcreates.roost.Roost;

import net.minecraft.client.resources.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class ConfigurationHandler {
	public static Configuration config;
	public float breederSpeed = 1f;
	public float roostSpeed = 1f;
	public int stackSize = 16;

	private static float MIN_VALUE = 0.01f;
	private static float MAX_VALUE = 100f;
	
	private static String CATEGORY_REQUIRES_RESTART = "other";

	public ConfigurationHandler(File configFile) {
		MinecraftForge.EVENT_BUS.register(this);

		config = new Configuration(configFile).setCategoryRequiresMcRestart(CATEGORY_REQUIRES_RESTART, true).setCategoryComment(CATEGORY_REQUIRES_RESTART, "Options in this category require the game to be restarted to apply");
		config.load();

		update();
	}

	private void update() {
		String category = Configuration.CATEGORY_GENERAL;
		breederSpeed = config.getFloat("roost.breederSpeed", category, breederSpeed, MIN_VALUE, MAX_VALUE, I18n.format("config.breeder.speed"));
		roostSpeed = config.getFloat("roost.roostSpeed", category, roostSpeed, MIN_VALUE, MAX_VALUE, I18n.format("config.roost.speed"));
		stackSize = config.getInt("roost.stackSize", CATEGORY_REQUIRES_RESTART, 16, 1, 64, I18n.format("config.chicken_item.stack_size"));

		if (config.hasChanged()) config.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equalsIgnoreCase(Roost.MODID)) update();
	}
}
