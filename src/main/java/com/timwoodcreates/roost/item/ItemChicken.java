package com.timwoodcreates.roost.item;

import java.util.List;

import javax.annotation.Nullable;

import com.timwoodcreates.roost.data.DataChicken;
import com.timwoodcreates.roost.tileentity.TileEntityRoost;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
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
		setMaxStackSize(16);
		setHasSubtypes(true);
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
		if (data != null) {
			data.addInfoToTooltip(tooltip);
		} else {
			NBTTagCompound tag = stack.getTagCompound();
			if (tag != null) {
				String chicken = tag.getString(DataChicken.CHICKEN_ID_KEY);
				if (chicken.length() > 0) {
					tooltip.add("Broken chicken, id = \"" + chicken + "\"");
				}
			}
		}
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


}
