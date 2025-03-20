package com.iafenvoy.nezha;

import com.iafenvoy.nezha.registry.NZRenderers;

public class NeZhaClient {
    public static void init() {
        NZRenderers.registerEntityRenderers();
    }

    public static void process() {
        NZRenderers.registerRenderLayers();
        NZRenderers.registerModelPredicates();
        NZRenderers.registerBuiltinItemRenderers();
    }
}
