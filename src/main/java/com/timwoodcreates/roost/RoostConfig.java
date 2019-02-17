package com.timwoodcreates.roost;

import net.minecraftforge.common.config.Config;
import net.minecraftforge.common.config.Config.Comment;
import net.minecraftforge.common.config.Config.LangKey;
import net.minecraftforge.common.config.Config.RangeDouble;
import net.minecraftforge.common.config.ConfigManager;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Config(modid = Roost.MODID)
public class RoostConfig {

	@Comment("The speed multiplier for the roost. Higher is faster.")
	@LangKey("config.roost.roostSpeed")
	@RangeDouble(min = 0.01d, max = 100d)
	public static double roostSpeed = 1d;

	@Comment("The speed multiplier for the breeder. Higher is faster.")
	@LangKey("config.roost.breederSpeed")
	@RangeDouble(min = 0.01d, max = 100d)
	public static double breederSpeed = 1d;

	@Comment("Prevent vanilla chickens from laying eggs. Of interest to modpack makers only.")
	public static boolean disableEggLaying = false;

	public static void sync() {
		ConfigManager.sync(Roost.MODID, Config.Type.INSTANCE);
	}

	@Mod.EventBusSubscriber(modid = Roost.MODID)
	public static class SyncHandler {

		@SubscribeEvent
		public static void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
			if (event.getModID().equals(Roost.MODID)) {
				RoostConfig.sync();
				Roost.LOGGER.info("Configuration has been saved.");
			}
		}
	}
}
