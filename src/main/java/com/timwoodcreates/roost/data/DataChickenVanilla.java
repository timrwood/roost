package com.timwoodcreates.roost.data;

import java.util.Arrays;
import java.util.List;

import com.timwoodcreates.roost.RoostConfig;
import com.timwoodcreates.roost.RoostItems;

import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityChicken;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemMonsterPlacer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class DataChickenVanilla extends DataChicken {

	private static final String VANILLA_TYPE = "minecraft:chicken";

	public static DataChicken getDataFromStack(ItemStack stack) {
		NBTTagCompound tagCompound = stack.getTagCompound();
		if (tagCompound == null || !tagCompound.getString(CHICKEN_ID_KEY).equals(VANILLA_TYPE)) return null;
		return new DataChickenVanilla();
	}

	public static DataChicken getDataFromTooltipNBT(NBTTagCompound tagCompound) {
		if (tagCompound == null || !tagCompound.getString(CHICKEN_ID_KEY).equals(VANILLA_TYPE)) return null;
		return new DataChickenVanilla();
	}

	public static DataChicken getDataFromEntity(Entity entity) {
		if (entity instanceof EntityChicken) return new DataChickenVanilla();
		return null;
	}

	public static DataChicken getDataFromName(String name) {
		if (name.equals("minecraft:vanilla")) return new DataChickenVanilla();
		return null;
	}

	public static void addAllChickens(List<DataChicken> chickens) {
		chickens.add(new DataChickenVanilla());
	}

	public DataChickenVanilla() {
		super("vanilla", "entity.Chicken.name");
	}

	@Override
	public boolean isEqual(DataChicken other) {
		return (other instanceof DataChickenVanilla);
	}

	@Override
	public ItemStack createDropStack() {
		Item item = rand.nextInt(3) > 0 && !RoostConfig.disableEggLaying ? Items.EGG : Items.FEATHER;
		return new ItemStack(item, 1);
	}

	@Override
	public EntityChicken buildEntity(World world) {
		return new EntityChicken(world);
	}

	@Override
	public void spawnEntity(World world, BlockPos pos) {
		EntityChicken chicken = new EntityChicken(world);
		chicken.setPosition(pos.getX() + 0.5, pos.getY(), pos.getZ() + 0.5);
		chicken.onInitialSpawn(world.getDifficultyForLocation(pos), null);
		chicken.setGrowingAge(getLayTime());
		world.spawnEntity(chicken);
	}

	@Override
	public ItemStack buildChickenStack() {
		ItemStack stack = new ItemStack(RoostItems.ITEM_CHICKEN);
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setString(CHICKEN_ID_KEY, VANILLA_TYPE);
		stack.setTagCompound(tagCompound);
		return stack;
	}

	@Override
	public NBTTagCompound buildTooltipNBT() {
		NBTTagCompound tagCompound = new NBTTagCompound();
		tagCompound.setString(CHICKEN_ID_KEY, VANILLA_TYPE);
		return tagCompound;
	}

	@Override
	public boolean hasParents() {
		return true;
	}

	@Override
	public List<ItemStack> buildParentChickenStack() {
		return Arrays.asList(buildChickenStack(), buildChickenStack());
	}

	@Override
	public ItemStack buildCaughtFromStack() {
		ItemStack stack = new ItemStack(Items.SPAWN_EGG);
		ItemMonsterPlacer.applyEntityIdToItemStack(stack, new ResourceLocation("chicken"));
		return stack;
	}

	@Override
	public String toString() {
		return "DataChickenVanilla [name=" + getName() + "]";
	}

	public String getChickenType() {
		return "minecraft:vanilla";
	}
}
