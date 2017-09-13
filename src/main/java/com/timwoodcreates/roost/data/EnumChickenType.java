package com.timwoodcreates.roost.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.IStringSerializable;

public enum EnumChickenType implements IStringSerializable {
	EMPTY("empty", 0.000),
	ALUMINUM("aluminum", 0.002),
	AMBER("amber", 0.004),
	AMETHYST("amethyst", 0.006),
	ARDITE("ardite", 0.008),
	BASALZ_ROD("basalz_rod", 0.010),
	BLACK("black", 0.012),
	BLAZE("blaze", 0.014),
	BLITZ_ROD("blitz_rod", 0.016),
	BLIZZ_ROD("blizz_rod", 0.018),
	BLOOD_SLIME("blood_slime", 0.020),
	BLUE("blue", 0.022),
	BLUE_SLIME("blue_slime", 0.024),
	BLUTONIUM("blutonium", 0.026),
	BOOPBEEP("boopbeep", 0.028),
	BRASS("brass", 0.030),
	BRONZE("bronze", 0.032),
	BROWN("brown", 0.034),
	BULLET("bullet", 0.036),
	CINNABAR("cinnabar", 0.038),
	CLAY("clay", 0.040),
	COAL("coal", 0.042),
	COLBALT("colbalt", 0.044),
	CONDUCTIVE_IRON("conductive_iron", 0.046),
	CONSTANTAN("constantan", 0.048),
	COPPER("copper", 0.050),
	CUPRONICKEL("cupronickel", 0.052),
	CYAN("cyan", 0.054),
	CYANITE("cyanite", 0.056),
	DARK_STEEL("dark_steel", 0.058),
	DIAMOND("diamond", 0.060),
	DRACONIUM("draconium", 0.062),
	DRACONIUM_AWAKENED("draconium_awakened", 0.064),
	ELECTRICAL_STEEL("electrical_steel", 0.066),
	ELECTRUM("electrum", 0.068),
	ELEMENTIUM("elementium", 0.070),
	EMERALD("emerald", 0.072),
	ENDER("ender", 0.074),
	ENDERIUM("enderium", 0.076),
	ENERGETIC_ALLOY("energetic_alloy", 0.078),
	FLINT("flint", 0.080),
	FUNWAY("funway", 0.082),
	GARNET("garnet", 0.084),
	GHAST("ghast", 0.086),
	GLASS("glass", 0.088),
	GLOWSTONE("glowstone", 0.090),
	GOLD("gold", 0.092),
	GRAPHITE("graphite", 0.094),
	GRAY("gray", 0.096),
	GREEN("green", 0.098),
	GUNPOWDER("gunpowder", 0.100),
	INVAR("invar", 0.102),
	IRIDIUM("iridium", 0.104),
	IRON("iron", 0.106),
	KNIGHT_SLIME("knight_slime", 0.108),
	LAVA("lava", 0.110),
	LEAD("lead", 0.112),
	LEATHER("leather", 0.114),
	LIGHT_BLUE("light_blue", 0.116),
	LIME("lime", 0.118),
	LOG("log", 0.120),
	LUDICRITE("ludicrite", 0.122),
	LUMIUM("lumium", 0.124),
	MAGENTA("magenta", 0.126),
	MAGMA("magma", 0.128),
	MAGMA_SLIME("magma_slime", 0.130),
	MALACHITE("malachite", 0.132),
	MANASTEEL("manasteel", 0.134),
	MANYULLYN("manyullyn", 0.136),
	MITHRIL("mithril", 0.138),
	MRAMERICAN("mramerican", 0.140),
	NETHERWART("netherwart", 0.142),
	NICKEL("nickel", 0.144),
	OBSIDIAN("obsidian", 0.146),
	ORANGE("orange", 0.148),
	OSMIUM("osmium", 0.150),
	OSTO("osto", 0.152),
	P_CRYSTAL("p_crystal", 0.154),
	P_SHARD("p_shard", 0.156),
	PERIDOT("peridot", 0.158),
	PIG_IRON("pig_iron", 0.160),
	PINK("pink", 0.162),
	PLATINUM("platinum", 0.164),
	PULSATING_IRON("pulsating_iron", 0.166),
	PURPLE("purple", 0.168),
	PURPLE_SLIME("purple_slime", 0.170),
	QUARTZ("quartz", 0.172),
	RED("red", 0.174),
	REDSTONE("redstone", 0.176),
	REDSTONE_ALLOY("redstone_alloy", 0.178),
	RICH_SLAG("rich_slag", 0.180),
	RUBY("ruby", 0.182),
	SALTPETER("saltpeter", 0.184),
	SAND("sand", 0.186),
	SAPPHIRE("sapphire", 0.188),
	SIGNALUM("signalum", 0.190),
	SILICON("silicon", 0.192),
	SILVER_DYE("silver_dye", 0.194),
	SILVERORE("silverore", 0.196),
	SLAG("slag", 0.198),
	SLIME("slime", 0.200),
	SMART("smart", 0.202),
	SNOWBALL("snowball", 0.204),
	SOUL_SAND("soul_sand", 0.206),
	SOULARIUM("soularium", 0.208),
	STEEL("steel", 0.210),
	STRING("string", 0.212),
	SULFUR("sulfur", 0.214),
	TANZANITE("tanzanite", 0.216),
	TERRASTEEL("terrasteel", 0.218),
	TIN("tin", 0.220),
	TOPAZ("topaz", 0.222),
	UNKNOWN("unknown", 0.224),
	URANIUM("uranium", 0.226),
	VANILLA("vanilla", 0.228),
	VIBRANT_ALLOY("vibrant_alloy", 0.230),
	WATER("water", 0.232),
	WHITE("white", 0.234),
	XP("xp", 0.236),
	YELLORIUM("yellorium", 0.238),
	YELLOW("yellow", 0.240),
	ZINC("zinc", 0.242);

	private final String name;
	private final double itemIndex;

	private static final Map<String, EnumChickenType> lookup = new HashMap<String, EnumChickenType>();

	static {
		for (EnumChickenType chickenType : EnumChickenType.values()) {
			lookup.put(chickenType.getName(), chickenType);
		}
	}

	private EnumChickenType(String name, double itemIndex) {
		this.itemIndex = itemIndex;
		this.name = name;
	}

	@Override
	public String toString() {
		return this.name;
	}

	@Override
	public String getName() {
		return this.name;
	}

	public double getItemIndex() {
		return itemIndex;
	}

	public static EnumChickenType get(String name) {
		return lookup.get(name);
	}
}