package com.iafenvoy.nezha;

import com.iafenvoy.neptune.accessory.AccessoryManager;
import com.iafenvoy.neptune.trail.TrailRegistry;
import com.iafenvoy.neptune.trail.provider.EntityTrailProvider;
import com.iafenvoy.neptune.trail.provider.TrailProvider;
import com.iafenvoy.neptune.util.Color4i;
import com.iafenvoy.nezha.item.HotWheelItem;
import com.iafenvoy.nezha.registry.*;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.doubles.Double2ObjectFunction;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;
import org.slf4j.Logger;

import java.util.function.Function;

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
        Double2ObjectFunction<Function<Entity, TrailProvider>> hotWheel = offset -> EntityTrailProvider.builder()
                .offset(new Vec3d(offset, -1.5, 0), true)
                .color(new Color4i(255, 69, 0, 255))
                .width(0.2f)
                ::build;
        TrailRegistry.registerPredicate(HotWheelItem.TRIAL_ID, entity -> !(entity instanceof LivingEntity living) || !AccessoryManager.getEquipped(living, AccessoryManager.Place.FEET).isOf(NZItems.HOT_WHEEL.get()));
        TrailRegistry.register(HotWheelItem.TRIAL_ID, hotWheel.get(-0.15), hotWheel.get(0.15));
    }
}
