package com.timwoodcreates.roost;

import com.timwoodcreates.roost.proxy.ProxyCommon;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

@Mod(modid = Roost.MODID, version = Roost.VERSION)
public class Roost {
	public static final String MODID = "roost";
	public static final String VERSION = "@VERSION@";
	public static final CreativeTabs TAB = new RoostTab();

	@Instance(MODID)
	public static Roost INSTANCE;

	@SidedProxy(clientSide = "com.timwoodcreates.roost.proxy.ProxyClient", serverSide = "com.timwoodcreates.roost.proxy.ProxyCommon")
	public static ProxyCommon PROXY;

	@EventHandler
	public void preInit(FMLPreInitializationEvent e) {
		FMLInterModComms.sendMessage("waila", "register",
				"com.timwoodcreates.roost.integration.waila.RoostWailaDataProvider.register");

		PROXY.preInit(e);
	}
}
