package com.iafenvoy.nezha.trail;

import com.iafenvoy.neptune.util.Color4i;
import com.iafenvoy.nezha.trail.proxy.TrailProxy;
import net.minecraft.util.math.Vec3d;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

public interface TrailProvider {
    @Nullable UUID getOwnerUuid();

    TrailHolder createTail();

    void updateTrail(TrailHolder effect);

    Vec3d getCurrentPos();

    Color4i getTrailColor();

    default TrailHolder.TrailPoint adjustPoint(TrailHolder.TrailPoint point, boolean vertical, float partialTicks) {
        return point;
    }

    default int getTrailLight(float tickDelta) {
        return TrailProxy.MAX_LIGHT;
    }

    default boolean shouldRenderHorizontal() {
        return true;
    }

    default int getMaxTrailLength() {
        return 32;
    }

    default boolean shouldRemove() {
        return false;
    }

    default double maxDistance() {
        return 12 * 16;
    }
}
