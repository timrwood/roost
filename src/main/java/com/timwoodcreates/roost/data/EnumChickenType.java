package com.timwoodcreates.roost.data;

import java.util.HashMap;
import java.util.Map;

import net.minecraft.util.IStringSerializable;

public enum EnumChickenType implements IStringSerializable {
	EMPTY("empty", 0.000),
	ADVANCEDPROCESSOR("advancedprocessor", 0.002),
	ALUMINUM("aluminum", 0.004),
	AMBER("amber", 0.006),
	AMETHYST("amethyst", 0.008),
	ARDITE("ardite", 0.010),
	BASALZ_ROD("basalz_rod", 0.012),
	BASICPROCESSOR("basicprocessor", 0.014),
	BLACK("black", 0.016),
	BLACKQUARTZ("blackquartz", 0.018),
	BLAZE("blaze", 0.020),
	BLITZ_ROD("blitz_rod", 0.022),
	BLIZZ_ROD("blizz_rod", 0.024),
	BLOOD_SLIME("blood_slime", 0.026),
	BLUE("blue", 0.028),
	BLUE_SLIME("blue_slime", 0.030),
	BLUTONIUM("blutonium", 0.032),
	BOOPBEEP("boopbeep", 0.034),
	BRASS("brass", 0.036),
	BRONZE("bronze", 0.038),
	BROWN("brown", 0.040),
	BULLET("bullet", 0.042),
	CABLE("cable", 0.044),
	CINNABAR("cinnabar", 0.046),
	CLAY("clay", 0.048),
	COAL("coal", 0.050),
	COBALT("cobalt", 0.052),
	CONDUCTIVE_IRON("conductive_iron", 0.054),
	CONSTANTAN("constantan", 0.056),
	CONSTRUCTIONCORE("constructioncore", 0.058),
	COPPER("copper", 0.060),
	CUPRONICKEL("cupronickel", 0.062),
	CYAN("cyan", 0.064),
	CYANITE("cyanite", 0.066),
	DARK_STEEL("dark_steel", 0.068),
	DARKGEM("darkgem", 0.070),
	DEMONMETAL("demonmetal", 0.072),
	DESTRUCTIONCORE("destructioncore", 0.074),
	DIAMANTINECRYSTAL("diamantinecrystal", 0.076),
	DIAMOND("diamond", 0.078),
	DRACONIUM("draconium", 0.080),
	DRACONIUM_AWAKENED("draconium_awakened", 0.082),
	ELECTRICAL_STEEL("electrical_steel", 0.084),
	ELECTRUM("electrum", 0.086),
	ELEMENTIUM("elementium", 0.088),
	EMERADICCRYSTAL("emeradiccrystal", 0.090),
	EMERALD("emerald", 0.092),
	ENDER("ender", 0.094),
	ENDERIUM("enderium", 0.096),
	ENERGETIC_ALLOY("energetic_alloy", 0.098),
	ENORICRYSTAL("enoricrystal", 0.100),
	FLINT("flint", 0.102),
	FUNWAY("funway", 0.104),
	GARNET("garnet", 0.106),
	GHAST("ghast", 0.108),
	GLASS("glass", 0.110),
	GLOWSTONE("glowstone", 0.112),
	GOLD("gold", 0.114),
	GRAPHITE("graphite", 0.116),
	GRAY("gray", 0.118),
	GREEN("green", 0.120),
	GUNPOWDER("gunpowder", 0.122),
	IMPROVEDPROCESSOR("improvedprocessor", 0.124),
	INVAR("invar", 0.126),
	IRIDIUM("iridium", 0.128),
	IRON("iron", 0.130),
	KNIGHT_SLIME("knight_slime", 0.132),
	LAVA("lava", 0.134),
	LEAD("lead", 0.136),
	LEATHER("leather", 0.138),
	LIGHT_BLUE("light_blue", 0.140),
	LIME("lime", 0.142),
	LOG("log", 0.144),
	LUDICRITE("ludicrite", 0.146),
	LUMIUM("lumium", 0.148),
	LUNARREACTIVEDUST("lunarreactivedust", 0.150),
	MAGENTA("magenta", 0.152),
	MAGICALWOOD("magicalwood", 0.154),
	MAGMA("magma", 0.156),
	MAGMA_SLIME("magma_slime", 0.158),
	MALACHITE("malachite", 0.160),
	MANASTEEL("manasteel", 0.162),
	MANYULLYN("manyullyn", 0.164),
	MITHRIL("mithril", 0.166),
	MOONSTONE("moonstone", 0.168),
	MRAMERICAN("mramerican", 0.170),
	NETHERWART("netherwart", 0.172),
	NICKEL("nickel", 0.174),
	OBSIDIAN("obsidian", 0.176),
	ORANGE("orange", 0.178),
	OSMIUM("osmium", 0.180),
	OSTO("osto", 0.182),
	P_CRYSTAL("p_crystal", 0.184),
	P_SHARD("p_shard", 0.186),
	PALISCRYSTAL("paliscrystal", 0.188),
	PERIDOT("peridot", 0.190),
	PIG_IRON("pig_iron", 0.192),
	PINK("pink", 0.194),
	PINKSLIME("pinkslime", 0.196),
	PLATINUM("platinum", 0.198),
	PULSATING_IRON("pulsating_iron", 0.200),
	PURPLE("purple", 0.202),
	PURPLE_SLIME("purple_slime", 0.204),
	QUARTZ("quartz", 0.206),
	QUARTZENRICHEDIRON("quartzenrichediron", 0.208),
	RED("red", 0.210),
	REDSTONE("redstone", 0.212),
	REDSTONE_ALLOY("redstone_alloy", 0.214),
	REDSTONECRYSTAL("redstonecrystal", 0.216),
	RESTONIACRYSTAL("restoniacrystal", 0.218),
	RICH_SLAG("rich_slag", 0.220),
	RUBBER("rubber", 0.222),
	RUBY("ruby", 0.224),
	SALT("salt", 0.226),
	SALTPETER("saltpeter", 0.228),
	SAND("sand", 0.230),
	SAPPHIRE("sapphire", 0.232),
	SIGNALUM("signalum", 0.234),
	SILICON("silicon", 0.236),
	SILVER_DYE("silver_dye", 0.238),
	SILVERORE("silverore", 0.240),
	SLAG("slag", 0.242),
	SLIME("slime", 0.244),
	SMART("smart", 0.246),
	SNOWBALL("snowball", 0.248),
	SOUL_SAND("soul_sand", 0.250),
	SOULARIUM("soularium", 0.252),
	STEEL("steel", 0.254),
	STONEBURNT("stoneburnt", 0.256),
	STRING("string", 0.258),
	SULFUR("sulfur", 0.260),
	TANZANITE("tanzanite", 0.262),
	TERRASTEEL("terrasteel", 0.264),
	TIN("tin", 0.266),
	TOPAZ("topaz", 0.268),
	UNKNOWN("unknown", 0.270),
	URANIUM("uranium", 0.272),
	VANILLA("vanilla", 0.274),
	VIBRANT_ALLOY("vibrant_alloy", 0.276),
	VINTEUM("vinteum", 0.278),
	VOIDCRYSTAL("voidcrystal", 0.280),
	WATER("water", 0.282),
	WHITE("white", 0.284),
	XP("xp", 0.286),
	YELLORIUM("yellorium", 0.288),
	YELLOW("yellow", 0.290),
	ZINC("zinc", 0.292);

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