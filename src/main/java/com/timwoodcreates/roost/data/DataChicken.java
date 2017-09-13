package com.timwoodcreates.roost.data;

import java.util.List;
import java.util.Random;
import java.util.regex.Pattern;

import com.google.common.base.CaseFormat;
import com.timwoodcreates.roost.RoostItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Loader;

public class DataChicken {

	protected static final String CHICKEN_ID_KEY = "Chicken";
	protected static final String MOD_ID_KEY = "Mod";
	protected static final int MOD_ID_VANILLA = 0;
	protected static final int MOD_ID_CHICKENS = 1;
	private static final Pattern REMOVE_CHICKENS_PREFIX = Pattern.compile("_?chick(en)?s?_?");

	public static DataChicken getDataFromEntity(Entity entity) {
		if (entity == null) return null;

		DataChicken data = null;

		if (Loader.isModLoaded("chickens")) data = DataChickenModded.getDataFromEntity(entity);

		if (data == null) data = DataChickenVanilla.getDataFromEntity(entity);

		return data;
	}

	public static DataChicken getDataFromStack(ItemStack stack) {
		if (!isChicken(stack)) return null;

		DataChicken data = null;

		if (Loader.isModLoaded("chickens")) data = DataChickenModded.getDataFromStack(stack);

		if (data == null) data = DataChickenVanilla.getDataFromStack(stack);

		return data;
	}

	public static void getItemCageSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		DataChickenVanilla.getItemCageSubItems(itemIn, tab, subItems);

		if (Loader.isModLoaded("chickens")) {
			DataChickenModded.getItemCageSubItems(itemIn, tab, subItems);
		}
	}

	public static boolean isChicken(ItemStack stack) {
		return stack != null && stack.getItem() == RoostItems.ITEM_CHICKEN;
	}

	private String name;
	protected String i18nName;

	protected Random rand = new Random();

	public DataChicken(String name, String i18nName) {
		super();
		String underscoredName = CaseFormat.LOWER_CAMEL.to(CaseFormat.LOWER_UNDERSCORE, name);
		String cleanedName = REMOVE_CHICKENS_PREFIX.matcher(underscoredName).replaceAll("");
		this.name = cleanedName;
		this.i18nName = i18nName;
	}

	public void addInfoToTooltip(List<String> tooltip) {
	}

	public ItemStack buildChickenStack() {
		return null;
	}

	public EntityChicken buildEntity(World world) {
		return null;
	}

	public void spawnEntity(World world, BlockPos pos) {
	}

	private static ItemStack createChildStack(DataChicken chickenA, DataChicken chickenB, World world) {
		if (chickenA.getClass() != chickenB.getClass()) return chickenA.buildChickenStack();
		EntityChicken parentA = chickenA.buildEntity(world);
		EntityChicken parentB = chickenB.buildEntity(world);
		if (parentA == null || parentB == null) return null;
		DataChicken childData = DataChicken.getDataFromEntity(parentA.createChild(parentB));
		if (childData == null) return null;
		return childData.buildChickenStack();
	}

	public ItemStack createChildStack(DataChicken other, World world) {
		if (rand.nextBoolean()) return createChildStack(this, other, world);
		return createChildStack(other, this, world);
	}

	public ItemStack createDropStack() {
		return null;
	}

	public int getAddedTime(ItemStack stack) {
		return stack == null ? 0 : Math.max(0, stack.stackSize);
	}

	public int getLayTime() {
		return 6000 + rand.nextInt(6000);
	}

	public String getName() {
		return name;
	}

	public String getI18NName() {
		return i18nName;
	}

	public boolean isEqual(DataChicken other) {
		return true;
	}

}
