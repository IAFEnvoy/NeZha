package com.iafenvoy.nezha.trail.proxy;

import com.iafenvoy.nezha.trail.TrailProvider;
import net.minecraft.entity.Entity;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public interface TrailProxy {
    void addTrail(Entity entity, TrailProvider provider);

    enum Empty implements TrailProxy {
        INSTANCE;

        @Override
        public void addTrail(Entity entity, TrailProvider provider) {
        }
    }
}
