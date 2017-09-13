package com.timwoodcreates.roost.gui;

import com.timwoodcreates.roost.RoostGui;
import com.timwoodcreates.roost.inventory.ContainerBreeder;
import com.timwoodcreates.roost.inventory.ContainerCollector;
import com.timwoodcreates.roost.inventory.ContainerRoost;
import com.timwoodcreates.roost.tileentity.TileEntityBreeder;
import com.timwoodcreates.roost.tileentity.TileEntityCollector;
import com.timwoodcreates.roost.tileentity.TileEntityRoost;

import net.minecraft.client.gui.inventory.GuiChest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.IGuiHandler;

public class GuiHandler implements IGuiHandler {

	@Override
	public Object getServerGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == RoostGui.ROOST) {
			return new ContainerRoost(player.inventory, (TileEntityRoost) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == RoostGui.BREEDER) {
			return new ContainerBreeder(player.inventory,
					(TileEntityBreeder) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == RoostGui.COLLECTOR) {
			return new ContainerCollector(player.inventory,
					(TileEntityCollector) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

	@Override
	public Object getClientGuiElement(int ID, EntityPlayer player, World world, int x, int y, int z) {
		if (ID == RoostGui.ROOST) {
			return new GuiRoost(player.inventory, (TileEntityRoost) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == RoostGui.BREEDER) {
			return new GuiBreeder(player.inventory, (TileEntityBreeder) world.getTileEntity(new BlockPos(x, y, z)));
		} else if (ID == RoostGui.COLLECTOR) {
			return new GuiChest(player.inventory, (TileEntityCollector) world.getTileEntity(new BlockPos(x, y, z)));
		}
		return null;
	}

}
