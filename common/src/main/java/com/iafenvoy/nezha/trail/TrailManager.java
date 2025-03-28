package com.iafenvoy.nezha.trail;

import com.iafenvoy.nezha.trail.proxy.TrailProxy;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

public class TrailManager {
    @ApiStatus.Internal
    public static TrailProxy PROXY = TrailProxy.Empty.INSTANCE;

    public static void addTrail(Entity entity, TrailProvider provider) {
        PROXY.addTrail(entity, provider);
    }
}
