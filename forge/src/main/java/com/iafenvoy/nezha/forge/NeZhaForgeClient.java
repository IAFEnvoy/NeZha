package com.iafenvoy.nezha.forge;

import com.iafenvoy.nezha.NeZhaClient;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class NeZhaForgeClient {
    @SubscribeEvent
    public static void onInit(FMLClientSetupEvent event) {
        event.enqueueWork(NeZhaClient::process);
    }
}
