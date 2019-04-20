package com.timwoodcreates.roost.data;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.setycz.chickens.ChickensMod;
import com.setycz.chickens.entity.EntityChickensChicken;
import com.setycz.chickens.handler.SpawnType;
import com.setycz.chickens.item.ItemSpawnEgg;
import com.setycz.chickens.registry.ChickensRegistry;
import com.setycz.chickens.registry.ChickensRegistryItem;
import com.timwoodcreates.roost.RoostItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.Optional;

@Optional.Interface(iface = "com.setycz.chickens.ChickensRegistry", modid = "chickens")
public class DataChickenModded extends DataChicken {

	private static final String GAIN_KEY = "Gain";
	private static final String GROWTH_KEY = "Growth";
	private static final String STRENGTH_KEY = "Strength";
	private static final String TYPE_KEY = "Type";

	public static DataChicken getDataFromEntity(Entity entity) {
		if (entity instanceof EntityChickensChicken) {
			NBTTagCompound tagCompound = entity.writeToNBT(new NBTTagCompound());
			ChickensRegistryItem chicken = ChickensRegistry.getByRegistryName(tagCompound.getString(TYPE_KEY));
			if (chicken != null) return new DataChickenModded(chicken, tagCompound);
		}
		return null;
	}

	public static DataChicken getDataFromTooltipNBT(NBTTagCompound tagCompound) {
		ChickensRegistryItem chicken = ChickensRegistry.getByRegistryName(tagCompound.getString(CHICKEN_ID_KEY));
		if (chicken != null) return new DataChickenModded(chicken, tagCompound);
		return null;
	}

	public static DataChicken getDataFromStack(ItemStack stack) {
		ChickensRegistryItem chicken = chickensRegistryItemForStack(stack);
		if (chicken != null) return new DataChickenModded(chicken, stack.getTagCompound());

		return null;
	}

	public static DataChicken getDataFromName(String name) {
		ChickensRegistryItem chicken = ChickensRegistry.getByRegistryName(name);
		if (chicken != null) return new DataChickenModded(chicken, null);

		return null;
	}

	public static void addAllChickens(List<DataChicken> chickens) {
		for (ChickensRegistryItem item : getChickenRegistryItems()) {
			chickens.add(new DataChickenModded(item, null));
		}
	}

	private static List<ChickensRegistryItem> getChickenRegistryItems() {
		Comparator<ChickensRegistryItem> comparator = new Comparator<ChickensRegistryItem>() {
			@Override
			public int compare(ChickensRegistryItem left, ChickensRegistryItem right) {
				if (left.getTier() != right.getTier()) return left.getTier() - right.getTier();
				return left.getEntityName().compareTo(right.getEntityName());
			}
		};

		Collection<ChickensRegistryItem> chickens = ChickensRegistry.getItems();
		List<ChickensRegistryItem> list = new ArrayList<ChickensRegistryItem>(chickens);
		Collections.sort(list, comparator);
		return list;
	}

	private static ChickensRegistryItem chickensRegistryItemForStack(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound == null) return null;
		return ChickensRegistry.getByRegistryName(tagCompound.getString(CHICKEN_ID_KEY));
	}

	private int gain = 1;
	private int growth = 1;
	private int strength = 1;
	private ChickensRegistryItem chicken;

	private DataChickenModded(ChickensRegistryItem chickenIn, NBTTagCompound compound) {
		super(chickenIn.getEntityName(), "entity." + chickenIn.getEntityName() + ".name");
		chicken = chickenIn;

		if (compound != null) {
			gain = Math.max(1, Math.min(10, compound.getInteger(GAIN_KEY)));
			growth = Math.max(1, Math.min(10, compound.getInteger(GROWTH_KEY)));
			strength = Math.max(1, Math.min(10, compound.getInteger(STRENGTH_KEY)));
		}
	}

	public String getChickenType() {
		return chicken.getRegistryName().toString();
	}

	public String getTextureName() {
		return chicken.getRegistryName().getResourcePath();
	}

	@Override
	public void addInfoToTooltip(List<String> tooltip) {
		if (growth > 1 && gain > 1 && strength > 1) {
			addStatsInfoToTooltip(tooltip);
		} else {
			addStatlessInfoToTooltip(tooltip);
		}
	}

	private void addStatsInfoToTooltip(List<String> tooltip) {
		tooltip.add(new TextComponentTranslation("item.roost.chicken.growth", growth).getFormattedText());
		tooltip.add(new TextComponentTranslation("item.roost.chicken.gain", gain).getFormattedText());
		tooltip.add(new TextComponentTranslation("item.roost.chicken.strength", strength).getFormattedText());
	}

	private void addStatlessInfoToTooltip(List<String> tooltip) {
		ChickensRegistryItem parent1 = chicken.getParent1();
		ChickensRegistryItem parent2 = chicken.getParent2();

		if (parent1 != null && parent2 != null) {
			String n1 = new TextComponentTranslation("entity." + parent1.getEntityName() + ".name").getFormattedText();
			String n2 = new TextComponentTranslation("entity." + parent2.getEntityName() + ".name").getFormattedText();
			tooltip.add(new TextComponentTranslation("item.roost.chicken.parent1", n1).getFormattedText());
			tooltip.add(new TextComponentTranslation("item.roost.chicken.parent2", n2).getFormattedText());
		}

		if (chicken.canSpawn() && chicken.getSpawnType() != SpawnType.NONE) {
			String spawnType = chicken.getSpawnType().name().toLowerCase();
			tooltip.add(new TextComponentTranslation("item.roost.chicken.spawning." + spawnType).getFormattedText());
		}
	}

	@Override
	public NBTTagCompound buildTooltipNBT() {
		NBTTagCompound tagCompound = createTagCompound();
		tagCompound.setString(CHICKEN_ID_KEY, chicken.getRegistryName().toString());
		return tagCompound;
	}

	@Override
	public EntityChicken buildEntity(World world) {
		EntityChickensChicken chicken = new EntityChickensChicken(world);
		chicken.readEntityFromNBT(createTagCompound());
		chicken.setChickenType(getChickenType());
		return chicken;
	}

	@Override
	public void spawnEntity(World world, BlockPos pos) {
		EntityChickensChicken chicken = new EntityChickensChicken(world);
		chicken.readEntityFromNBT(createTagCompound());
		chicken.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
		chicken.onInitialSpawn(world.getDifficultyForLocation(pos), null);
		chicken.setChickenType(getChickenType());
		chicken.setGrowingAge(getLayTime());
		world.spawnEntity(chicken);
	}

	@Override
	public ItemStack buildChickenStack() {
		ItemStack stack = new ItemStack(RoostItems.ITEM_CHICKEN);
		NBTTagCompound tagCompound = createTagCompound();
		tagCompound.setString(CHICKEN_ID_KEY, chicken.getRegistryName().toString());
		stack.setTagCompound(tagCompound);
		return stack;
	}

	@Override
	public boolean hasParents() {
		return chicken.getParent1() != null && chicken.getParent2() != null;
	}

	@Override
	public List<ItemStack> buildParentChickenStack() {
		if (!hasParents()) return null;
		DataChicken parent1 = new DataChickenModded(chicken.getParent1(), null);
		DataChicken parent2 = new DataChickenModded(chicken.getParent2(), null);
		return Arrays.asList(parent1.buildChickenStack(), parent2.buildChickenStack());
	}

	@Override
	public ItemStack buildCaughtFromStack() {
		ItemStack stack = new ItemStack(ChickensMod.spawnEgg);
		ItemSpawnEgg.applyEntityIdToItemStack(stack, chicken.getRegistryName());
		return stack;
	}

	@Override
	public ItemStack createDropStack() {
		ItemStack stack = chicken.createLayItem();
		stack.setCount(gain >= 10 ? 3 : gain >= 5 ? 2 : 1);
		return stack;
	}

	public NBTTagCompound createTagCompound() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setInteger(GAIN_KEY, gain);
		tagCompound.setInteger(GROWTH_KEY, growth);
		tagCompound.setInteger(STRENGTH_KEY, strength);
		return tagCompound;
	}

	@Override
	public int getAddedTime(ItemStack stack) {
		return growth * super.getAddedTime(stack);
	}

	@Override
	public int getLayTime() {
		int minLayTime = chicken.getMinLayTime();
		int maxLayTime = chicken.getMaxLayTime();
		return minLayTime + rand.nextInt(maxLayTime - minLayTime);
	}

	@Override
	public boolean isEqual(DataChicken other) {
		if (other instanceof DataChickenModded) {
			DataChickenModded o = (DataChickenModded) other;
			return (getChickenType().equals(o.getChickenType())) && (growth == o.growth) && (gain == o.gain) && (strength == o.strength);
		}
		return false;
	}

	@Override
	public String toString() {
		return "DataChickenModded [name=" + getName() + " type=" + getChickenType() + ", gain=" + gain + ", growth=" + growth + ", strength=" + strength + "]";
	}

	@Override
	public String getDisplaySummary() {
		return super.getDisplaySummary() + " " + growth + "/" + gain + "/" + strength;
	}
}
