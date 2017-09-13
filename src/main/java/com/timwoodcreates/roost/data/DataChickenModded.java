package com.timwoodcreates.roost.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import com.setycz.chickens.ChickensRegistry;
import com.setycz.chickens.ChickensRegistryItem;
import com.setycz.chickens.chicken.EntityChickensChicken;
import com.timwoodcreates.roost.RoostItems;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextFormatting;
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
			ChickensRegistryItem chicken = ChickensRegistry.getByType(tagCompound.getInteger(TYPE_KEY));
			if (chicken != null) return new DataChickenModded(chicken, tagCompound);
		}
		return null;
	}

	public static DataChicken getDataFromStack(ItemStack stack) {
		ChickensRegistryItem chicken = chickensRegistryItemForStack(stack);
		if (chicken != null) return new DataChickenModded(chicken, stack.getTagCompound());

		return null;
	}

	public static void getItemCageSubItems(Item itemIn, CreativeTabs tab, List<ItemStack> subItems) {
		for (ChickensRegistryItem item : getChickenRegistryItems()) {
			ItemStack stack = new ItemStack(itemIn, 1, 0);
			NBTTagCompound tagCompound = new NBTTagCompound();
			tagCompound.setInteger(MOD_ID_KEY, MOD_ID_CHICKENS);
			tagCompound.setInteger(CHICKEN_ID_KEY, item.getId());
			tagCompound.setInteger(GAIN_KEY, 1);
			tagCompound.setInteger(GROWTH_KEY, 1);
			tagCompound.setInteger(STRENGTH_KEY, 1);
			stack.setTagCompound(tagCompound);
			subItems.add(stack);
		}
	}

	@Optional.Method(modid = "chickens")
	private static List<ChickensRegistryItem> getChickenRegistryItems() {
		Comparator<ChickensRegistryItem> comparator = new Comparator<ChickensRegistryItem>() {
			@Override
			public int compare(ChickensRegistryItem left, ChickensRegistryItem right) {
				if (left.getTier() != right.getTier()) return left.getTier() - right.getTier();
				return left.getId() - right.getId();
			}
		};

		Collection<ChickensRegistryItem> chickens = ChickensRegistry.getItems();
		List<ChickensRegistryItem> list = new ArrayList<ChickensRegistryItem>(chickens);
		Collections.sort(list, comparator);
		return list;
	}

	private static ChickensRegistryItem chickensRegistryItemForStack(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound == null || tagCompound.getInteger(MOD_ID_KEY) != MOD_ID_CHICKENS) return null;
		return ChickensRegistry.getByType(tagCompound.getInteger(CHICKEN_ID_KEY));
	}

	private int gain = 1;
	private int growth = 1;
	private ItemStack item;
	private int maxLayTime = 12000;
	private int minLayTime = 6000;
	private int strength = 1;
	private int tier = 0;
	private int type = 0;

	private DataChickenModded(ChickensRegistryItem chicken, NBTTagCompound compound) {
		super(chicken.getEntityName(), "entity." + chicken.getEntityName() + ".name");
		type = chicken.getId();
		tier = chicken.getTier();
		item = chicken.createLayItem();
		minLayTime = chicken.getMinLayTime();
		maxLayTime = chicken.getMaxLayTime();

		if (compound != null) {
			gain = Math.max(1, Math.min(10, compound.getInteger(GAIN_KEY)));
			growth = Math.max(1, Math.min(10, compound.getInteger(GROWTH_KEY)));
			strength = Math.max(1, Math.min(10, compound.getInteger(STRENGTH_KEY)));
		}
	}

	@Override
	public void addInfoToTooltip(List<String> tooltip) {
		tooltip.add(TextFormatting.GRAY + "Tier: " + tier);
		tooltip.add(TextFormatting.GRAY + "Growth: " + growth);
		tooltip.add(TextFormatting.GRAY + "Gain: " + gain);
		tooltip.add(TextFormatting.GRAY + "Strength: " + strength);
	}

	@Override
	public EntityChicken buildEntity(World world) {
		EntityChickensChicken chicken = new EntityChickensChicken(world);
		chicken.readEntityFromNBT(createTagCompound());
		chicken.setChickenType(type);
		return chicken;
	}

	@Override
	public void spawnEntity(World world, BlockPos pos) {
		EntityChickensChicken chicken = new EntityChickensChicken(world);
		chicken.readEntityFromNBT(createTagCompound());
		chicken.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
		chicken.onInitialSpawn(world.getDifficultyForLocation(pos), null);
		chicken.setChickenType(type);
		chicken.setGrowingAge(getLayTime());
		world.spawnEntityInWorld(chicken);
	}

	@Override
	public ItemStack buildChickenStack() {
		ItemStack stack = new ItemStack(RoostItems.ITEM_CHICKEN);
		NBTTagCompound tagCompound = createTagCompound();
		tagCompound.setInteger(MOD_ID_KEY, MOD_ID_CHICKENS);
		tagCompound.setInteger(CHICKEN_ID_KEY, type);
		stack.setTagCompound(tagCompound);
		return stack;
	}

	@Override
	public ItemStack createDropStack() {
		ItemStack stack = item.copy();
		stack.stackSize = gain >= 10 ? 3 : gain >= 5 ? 2 : 1;
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
		return minLayTime + rand.nextInt(maxLayTime - minLayTime);
	}

	@Override
	public boolean isEqual(DataChicken other) {
		if (other instanceof DataChickenModded) {
			DataChickenModded o = (DataChickenModded) other;
			return (type == o.type) && (growth == o.growth) && (gain == o.gain) && (strength == o.strength);
		}
		return false;
	}

	@Override
	public String toString() {
		return "DataChickenModded [name=" + getName() + " type=" + type + ", tier=" + tier + ", gain=" + gain
				+ ", growth=" + growth + ", strength=" + strength + "]";
	}
}
