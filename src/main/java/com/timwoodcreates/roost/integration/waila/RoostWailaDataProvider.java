package com.timwoodcreates.roost.integration.waila;

import java.util.List;

import com.timwoodcreates.roost.tileentity.TileEntityBreeder;
import com.timwoodcreates.roost.tileentity.TileEntityRoost;

import mcp.mobius.waila.api.IWailaConfigHandler;
import mcp.mobius.waila.api.IWailaDataAccessor;
import mcp.mobius.waila.api.IWailaDataProvider;
import mcp.mobius.waila.api.IWailaRegistrar;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class RoostWailaDataProvider implements IWailaDataProvider {
	private static final RoostWailaDataProvider INSTANCE = new RoostWailaDataProvider();

	public static void register(IWailaRegistrar registrar) {
		registrar.registerTailProvider(INSTANCE, TileEntityRoost.class);
		registrar.registerTailProvider(INSTANCE, TileEntityBreeder.class);
		registrar.registerNBTProvider(INSTANCE, TileEntityRoost.class);
		registrar.registerNBTProvider(INSTANCE, TileEntityBreeder.class);
	}

	@Override
	public ItemStack getWailaStack(IWailaDataAccessor accessor, IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaHead(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaBody(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		return null;
	}

	@Override
	public List<String> getWailaTail(ItemStack itemStack, List<String> tooltip, IWailaDataAccessor accessor,
			IWailaConfigHandler config) {
		TileEntity tileEntity = accessor.getTileEntity();

		if (tileEntity instanceof TileEntityRoost) {
			((TileEntityRoost) tileEntity).addInfoToTooltip(tooltip, accessor.getNBTData());
		}

		if (tileEntity instanceof TileEntityBreeder) {
			((TileEntityBreeder) tileEntity).addInfoToTooltip(tooltip, accessor.getNBTData());
		}

		return tooltip;
	}

	@Override
	public NBTTagCompound getNBTData(EntityPlayerMP player, TileEntity tileEntity, NBTTagCompound tag, World world,
			BlockPos pos) {
		if (tileEntity instanceof TileEntityRoost) ((TileEntityRoost) tileEntity).storeInfoForTooltip(tag);
		if (tileEntity instanceof TileEntityBreeder) ((TileEntityBreeder) tileEntity).storeInfoForTooltip(tag);
		return tag;
	}

}
