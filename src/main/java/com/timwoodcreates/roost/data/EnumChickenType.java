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
	CHROME("chrome", 0.046),
	CINNABAR("cinnabar", 0.048),
	CLAY("clay", 0.050),
	COAL("coal", 0.052),
	COBALT("cobalt", 0.054),
	CONDUCTIVE_IRON("conductive_iron", 0.056),
	CONSTANTAN("constantan", 0.058),
	CONSTRUCTIONCORE("constructioncore", 0.060),
	COPPER("copper", 0.062),
	CUPRONICKEL("cupronickel", 0.064),
	CYAN("cyan", 0.066),
	CYANITE("cyanite", 0.068),
	DARK_STEEL("dark_steel", 0.070),
	DARKGEM("darkgem", 0.072),
	DEMONMETAL("demonmetal", 0.074),
	DESTRUCTIONCORE("destructioncore", 0.076),
	DIAMANTINECRYSTAL("diamantinecrystal", 0.078),
	DIAMOND("diamond", 0.080),
	DRACONIUM("draconium", 0.082),
	DRACONIUM_AWAKENED("draconium_awakened", 0.084),
	ELECTRICAL_STEEL("electrical_steel", 0.086),
	ELECTRUM("electrum", 0.088),
	ELEMENTIUM("elementium", 0.090),
	EMERADICCRYSTAL("emeradiccrystal", 0.092),
	EMERALD("emerald", 0.094),
	ENDER("ender", 0.096),
	ENDERIUM("enderium", 0.098),
	ENERGETIC_ALLOY("energetic_alloy", 0.100),
	ENORICRYSTAL("enoricrystal", 0.102),
	FLINT("flint", 0.104),
	FUNWAY("funway", 0.106),
	GARNET("garnet", 0.108),
	GHAST("ghast", 0.110),
	GLASS("glass", 0.112),
	GLOWSTONE("glowstone", 0.114),
	GOLD("gold", 0.116),
	GRAPHITE("graphite", 0.118),
	GRAY("gray", 0.120),
	GREEN("green", 0.122),
	GUNPOWDER("gunpowder", 0.124),
	IMPROVEDPROCESSOR("improvedprocessor", 0.126),
	INVAR("invar", 0.128),
	IRIDIUM("iridium", 0.130),
	IRON("iron", 0.132),
	KNIGHT_SLIME("knight_slime", 0.134),
	LAVA("lava", 0.136),
	LEAD("lead", 0.138),
	LEATHER("leather", 0.140),
	LIGHT_BLUE("light_blue", 0.142),
	LIME("lime", 0.144),
	LOG("log", 0.146),
	LUDICRITE("ludicrite", 0.148),
	LUMIUM("lumium", 0.150),
	LUNARREACTIVEDUST("lunarreactivedust", 0.152),
	MAGENTA("magenta", 0.154),
	MAGICALWOOD("magicalwood", 0.156),
	MAGMA("magma", 0.158),
	MAGMA_SLIME("magma_slime", 0.160),
	MALACHITE("malachite", 0.162),
	MANASTEEL("manasteel", 0.164),
	MANYULLYN("manyullyn", 0.166),
	MITHRIL("mithril", 0.168),
	MOONSTONE("moonstone", 0.170),
	MRAMERICAN("mramerican", 0.172),
	NETHERWART("netherwart", 0.174),
	NICKEL("nickel", 0.176),
	OBSIDIAN("obsidian", 0.178),
	ORANGE("orange", 0.180),
	OSMIUM("osmium", 0.182),
	OSTO("osto", 0.184),
	P_CRYSTAL("p_crystal", 0.186),
	P_SHARD("p_shard", 0.188),
	PALISCRYSTAL("paliscrystal", 0.190),
	PERIDOT("peridot", 0.192),
	PIG_IRON("pig_iron", 0.194),
	PINK("pink", 0.196),
	PINKSLIME("pinkslime", 0.198),
	PLATINUM("platinum", 0.200),
	PULSATING_IRON("pulsating_iron", 0.202),
	PURPLE("purple", 0.204),
	PURPLE_SLIME("purple_slime", 0.206),
	QUARTZ("quartz", 0.208),
	QUARTZENRICHEDIRON("quartzenrichediron", 0.210),
	RED("red", 0.212),
	REDSTONE("redstone", 0.214),
	REDSTONE_ALLOY("redstone_alloy", 0.216),
	REDSTONECRYSTAL("redstonecrystal", 0.218),
	REFINEDIRON("refinediron", 0.220),
	RESTONIACRYSTAL("restoniacrystal", 0.222),
	RICH_SLAG("rich_slag", 0.224),
	RUBBER("rubber", 0.226),
	RUBY("ruby", 0.228),
	SALT("salt", 0.230),
	SALTPETER("saltpeter", 0.232),
	SAND("sand", 0.234),
	SAPPHIRE("sapphire", 0.236),
	SIGNALUM("signalum", 0.238),
	SILICON("silicon", 0.240),
	SILVER_DYE("silver_dye", 0.242),
	SILVERORE("silverore", 0.244),
	SLAG("slag", 0.246),
	SLIME("slime", 0.248),
	SMART("smart", 0.250),
	SNOWBALL("snowball", 0.252),
	SOUL_SAND("soul_sand", 0.254),
	SOULARIUM("soularium", 0.256),
	STEEL("steel", 0.258),
	STONEBURNT("stoneburnt", 0.260),
	STRING("string", 0.262),
	SULFUR("sulfur", 0.264),
	TANZANITE("tanzanite", 0.266),
	TERRASTEEL("terrasteel", 0.268),
	TIN("tin", 0.270),
	TITANIUM("titanium", 0.272),
	TOPAZ("topaz", 0.274),
	TUNGSTEN("tungsten", 0.276),
	TUNGSTENSTEEL("tungstensteel", 0.278),
	UNKNOWN("unknown", 0.280),
	URANIUM("uranium", 0.282),
	VANILLA("vanilla", 0.284),
	VIBRANT_ALLOY("vibrant_alloy", 0.286),
	VINTEUM("vinteum", 0.288),
	VOIDCRYSTAL("voidcrystal", 0.290),
	WATER("water", 0.292),
	WHITE("white", 0.294),
	XP("xp", 0.296),
	YELLORIUM("yellorium", 0.298),
	YELLOW("yellow", 0.300),
	YELLOWGARNET("yellowgarnet", 0.302),
	ZINC("zinc", 0.304);

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