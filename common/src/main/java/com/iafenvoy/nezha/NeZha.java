package com.iafenvoy.nezha;

import com.iafenvoy.neptune.accessory.AccessoryManager;
import com.iafenvoy.nezha.registry.*;
import com.mojang.logging.LogUtils;
import org.slf4j.Logger;

public final class NeZha {
    public static final String MOD_ID = "ne_zha";
    public static final Logger LOGGER = LogUtils.getLogger();

    public static void init() {
        NZBlocks.REGISTRY.register();
        NZEntities.REGISTRY.register();
        NZItemGroups.REGISTRY.register();
        NZItems.REGISTRY.register();

        NZEntities.init();
    }

    public static void process() {
        NZBehaviours.init();
        NZAbilityCategories.init();
        NZAbilities.init();

        AccessoryManager.register(NZItems.HOT_WHEEL.get());
    }
}
