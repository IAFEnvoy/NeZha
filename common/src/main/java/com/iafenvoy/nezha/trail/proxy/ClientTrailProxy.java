package com.iafenvoy.nezha.trail.proxy;

import com.iafenvoy.nezha.trail.TrailEffect;
import com.iafenvoy.nezha.trail.TrailProvider;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
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

    @Override
    public int getEntityLight(Entity entity, float tickDelta) {
        return MinecraftClient.getInstance().getEntityRenderDispatcher().getLight(entity, tickDelta);
    }
}
