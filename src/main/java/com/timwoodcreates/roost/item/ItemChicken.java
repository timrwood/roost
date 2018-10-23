package com.timwoodcreates.roost.item;

import java.util.List;

import javax.annotation.Nullable;

import com.timwoodcreates.roost.Roost;
import com.timwoodcreates.roost.data.DataChicken;
import com.timwoodcreates.roost.data.EnumChickenType;
import com.timwoodcreates.roost.tileentity.TileEntityRoost;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class ItemChicken extends Item {

	private static String I18N_NAME = "entity.Chicken.name";

	public ItemChicken() {
		super();
		setMaxStackSize(Roost.config.stackSize);
		setHasSubtypes(true);
		addPropertyOverride(new ResourceLocation("chicken"), new ItemChickenPropertyGetter());
	}

	@Override
	@SideOnly(Side.CLIENT)
	public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> subItems) {
		if (isInCreativeTab(tab)) {
			DataChicken.getItemChickenSubItems(tab, subItems);
		}
	}

	@Override
	public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		DataChicken data = DataChicken.getDataFromStack(stack);
		if (data != null) data.addInfoToTooltip(tooltip);
	}

	@Override
	@SideOnly(Side.CLIENT)
	public String getItemStackDisplayName(ItemStack stack) {
		DataChicken data = DataChicken.getDataFromStack(stack);
		if (data == null) return I18n.format(I18N_NAME);
		return data.getDisplayName();
	}

	@Override
	public EnumActionResult onItemUse(EntityPlayer playerIn, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY,
			float hitZ) {
		if (!worldIn.isRemote) {
			TileEntity tileEntity = worldIn.getTileEntity(pos);

			if (tileEntity != null && tileEntity instanceof TileEntityRoost) {
				putChickenIn(playerIn.getHeldItem(hand), (TileEntityRoost) tileEntity);
			} else {
				spawnChicken(playerIn.getHeldItem(hand), playerIn, worldIn, pos.offset(facing));
			}
		}

		return EnumActionResult.SUCCESS;
	}

	private void putChickenIn(ItemStack stack, TileEntityRoost tileEntity) {
		tileEntity.putChickenIn(stack);
	}

	private void spawnChicken(ItemStack stack, EntityPlayer playerIn, World worldIn, BlockPos pos) {
		DataChicken chickenData = DataChicken.getDataFromStack(stack);
		if (chickenData == null) return;
		chickenData.spawnEntity(worldIn, pos);
		stack.shrink(1);
	}

	private class ItemChickenPropertyGetter implements IItemPropertyGetter {
		@Override
		public float apply(ItemStack stack, World worldIn, EntityLivingBase entityIn) {
			DataChicken chickenData = DataChicken.getDataFromStack(stack);
			if (chickenData == null) return 1.0f;
			EnumChickenType chickenType = EnumChickenType.get(chickenData.getName());
			if (chickenType == null) chickenType = EnumChickenType.UNKNOWN;
			return (float) chickenType.getItemIndex();
		}
	}

}
