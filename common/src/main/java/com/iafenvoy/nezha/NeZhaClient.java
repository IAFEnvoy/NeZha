package com.iafenvoy.nezha;

import com.iafenvoy.neptune.accessory.AccessoryManager;
import com.iafenvoy.neptune.util.Color4i;
import com.iafenvoy.nezha.registry.NZItems;
import com.iafenvoy.nezha.registry.NZRenderers;
import com.iafenvoy.nezha.trail.EntityTrailProvider;
import com.iafenvoy.nezha.trail.Proxies;
import com.iafenvoy.nezha.trail.TrailEffect;
import com.iafenvoy.nezha.trail.TrailManager;
import com.iafenvoy.nezha.trail.proxy.ClientTrailProxy;
import dev.architectury.event.events.client.ClientTickEvent;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Vec3d;

import java.util.Optional;

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
        Proxies.PROXY = ClientTrailProxy.INSTANCE;
        TrailManager.register(entity -> entity instanceof LivingEntity living && AccessoryManager.getEquipped(living, AccessoryManager.Place.FEET).isOf(NZItems.HOT_WHEEL.get()) ? Optional.of(new TrailEffect(living, EntityTrailProvider.builder(living)
                .offset(new Vec3d(0, -1.5, 0))
                .color(new Color4i(255, 69, 0, 255))
                .width(0.75f)
                .removeCondition(l -> !AccessoryManager.getEquipped(l, AccessoryManager.Place.FEET).isOf(NZItems.HOT_WHEEL.get()))
                .build())) : Optional.empty());
    }
}
