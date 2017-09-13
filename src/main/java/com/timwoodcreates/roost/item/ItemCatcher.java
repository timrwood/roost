package com.timwoodcreates.roost.item;

import java.util.Random;

import com.timwoodcreates.roost.data.DataChicken;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class ItemCatcher extends Item {

	public ItemCatcher() {
		super();
		maxStackSize = 1;
		setMaxDamage(64);
	}

	/**
	 * Returns true if the item can be used on the given entity.
	 */
	// /summon chickens.ChickensChicken ~ ~ ~ {Type:201,Gain:8,Growth:9,Strength:10}
	@Override
	public boolean itemInteractionForEntity(ItemStack itemstack, EntityPlayer player, EntityLivingBase entity,
			EnumHand hand) {
		Vec3d pos = new Vec3d(entity.posX, entity.posY, entity.posZ);
		World world = entity.worldObj;

		if (!captureChicken(entity, world.isRemote)) {
			return false;
		}

		if (world.isRemote) {
			spawnParticles(pos, world);
		}

		world.playSound(player, entity.posX, entity.posY, entity.posZ, SoundEvents.ENTITY_CHICKEN_HURT,
				entity.getSoundCategory(), 1.0F, 1.0F);

		itemstack.damageItem(1, player);

		return true;
	}

	private boolean captureChicken(EntityLivingBase entity, boolean isRemote) {
		DataChicken chickenData = DataChicken.getDataFromEntity(entity);

		if (chickenData == null) return false;

		if (!isRemote) {
			EntityItem item = entity.entityDropItem(chickenData.buildChickenStack(), 1.0F);
			item.motionX = 0;
			item.motionY = 0.2D;
			item.motionZ = 0;
			entity.worldObj.removeEntity(entity);
		}

		return true;
	}

	private void spawnParticles(Vec3d pos, World world) {
		Random rand = new Random();

		for (int k = 0; k < 20; ++k) {
			double xCoord = pos.xCoord + (rand.nextDouble() * 0.6D) - 0.3D;
			double yCoord = pos.yCoord + (rand.nextDouble() * 0.6D);
			double zCoord = pos.zCoord + (rand.nextDouble() * 0.6D) - 0.3D;
			double xSpeed = rand.nextGaussian() * 0.02D;
			double ySpeed = rand.nextGaussian() * 0.2D;
			double zSpeed = rand.nextGaussian() * 0.02D;
			world.spawnParticle(EnumParticleTypes.EXPLOSION_NORMAL, xCoord, yCoord, zCoord, xSpeed, ySpeed, zSpeed,
					new int[0]);
		}
	}

}
