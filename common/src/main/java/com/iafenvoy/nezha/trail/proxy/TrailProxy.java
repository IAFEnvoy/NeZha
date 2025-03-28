package com.iafenvoy.nezha.trail.proxy;

import com.iafenvoy.nezha.trail.TrailProvider;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface TrailProxy {
    int MAX_LIGHT = 15728880;

    void addTrail(Entity entity, TrailProvider provider);

    int getEntityLight(Entity entity, float tickDelta);

    enum Empty implements TrailProxy {
        INSTANCE;

        @Override
        public void addTrail(Entity entity, TrailProvider provider) {
        }

        @Override
        public int getEntityLight(Entity entity, float tickDelta) {
            return MAX_LIGHT;
        }
    }
}
