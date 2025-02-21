package com.iafenvoy.nezha;

import com.iafenvoy.nezha.registry.*;

public final class NeZha {
    public static final String MOD_ID = "ne_zha";

    public static void init() {
        NZEntities.REGISTRY.register();
        NZItemGroups.REGISTRY.register();
        NZItems.REGISTRY.register();
    }

    public static void process() {
        NZPowerCategories.init();
        NZPowers.init();
    }
}
