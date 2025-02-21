package com.iafenvoy.nezha;

import com.iafenvoy.nezha.registry.NZRenderers;

public class NeZhaClient {
    public static void init() {
        NZRenderers.registerEntityRenderers();
    }

    public static void process() {
        NZRenderers.registerModelPredicates();
        NZRenderers.registerBuiltinItemRenderers();
    }
}
