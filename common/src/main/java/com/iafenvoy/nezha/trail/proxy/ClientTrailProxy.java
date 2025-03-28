package com.iafenvoy.nezha.trail.proxy;

import com.iafenvoy.nezha.trail.TrailProvider;
import com.iafenvoy.nezha.trail.TrailEffect;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
@Environment(EnvType.CLIENT)
public enum ClientTrailProxy implements TrailProxy {
    INSTANCE;

    @Override
    public void addTrail(Entity entity, TrailProvider provider) {
        TrailEffect.create(entity, provider);
    }
}
