package com.iafenvoy.nezha;

import com.iafenvoy.nezha.registry.NZRenderers;
import com.iafenvoy.nezha.trail.TrailEffect;
import com.iafenvoy.nezha.trail.TrailManager;
import com.iafenvoy.nezha.trail.proxy.ClientTrailProxy;
import dev.architectury.event.events.client.ClientTickEvent;

public class NeZhaClient {
    public static void init() {
        NZRenderers.registerEntityRenderers();
    }

    public static void process() {
        NZRenderers.registerRenderLayers();
        NZRenderers.registerModelPredicates();
        NZRenderers.registerBuiltinItemRenderers();
        NZRenderers.registerArmorRenderers();

        ClientTickEvent.CLIENT_POST.register(TrailEffect::tick);
        TrailManager.PROXY = ClientTrailProxy.INSTANCE;
    }
}
