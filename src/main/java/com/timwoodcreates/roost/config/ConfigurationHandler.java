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
	public float speedBreeder = 1f;
	public float speedRoost = 1f;

	private static float MIN_VALUE = 0.1f;
	private static float MAX_VALUE = 20f;

	public ConfigurationHandler(File configFile) {
		MinecraftForge.EVENT_BUS.register(this);

		config = new Configuration(configFile);
		config.load();

		update();
	}

	private void update() {
		String category = Configuration.CATEGORY_GENERAL;
		speedBreeder = config.getFloat("roost.speedBreeder", category, speedBreeder, MIN_VALUE, MAX_VALUE, I18n.format("config.speed.breeder"));
		speedRoost = config.getFloat("roost.speedRoost", category, speedRoost, MIN_VALUE, MAX_VALUE, I18n.format("config.speed.roost"));

		if (config.hasChanged()) config.save();
	}

	@SubscribeEvent
	public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent e) {
		if (e.getModID().equalsIgnoreCase(Roost.MODID)) update();
	}
}
