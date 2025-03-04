package com.iafenvoy.nezha.forge;

import com.iafenvoy.nezha.NeZha;
import com.iafenvoy.nezha.NeZhaClient;
import dev.architectury.platform.Platform;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(NeZha.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public final class NeZhaForge {
    @SuppressWarnings("removal")
    public NeZhaForge() {
        EventBuses.registerModEventBus(NeZha.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        NeZha.init();
        if (Platform.getEnv() == Dist.CLIENT)
            NeZhaClient.init();
    }

    @SubscribeEvent
    public static void onInit(FMLCommonSetupEvent event) {
        event.enqueueWork(NeZha::process);
    }
}
