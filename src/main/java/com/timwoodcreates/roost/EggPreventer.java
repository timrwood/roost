package com.timwoodcreates.roost;

import net.minecraft.entity.passive.EntityChicken;
import net.minecraftforge.event.entity.living.LivingEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EggPreventer {
    @SubscribeEvent
    public static void onLivingUpdate(LivingEvent.LivingUpdateEvent e) {
        if(e.getEntity() instanceof EntityChicken) {
            EntityChicken chicken = (EntityChicken) e.getEntity();
            if(chicken.timeUntilNextEgg == 0) {
                chicken.timeUntilNextEgg = 999999999;
            }
        }
    }
}
