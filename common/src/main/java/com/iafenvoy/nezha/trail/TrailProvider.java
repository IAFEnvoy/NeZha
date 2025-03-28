package com.iafenvoy.nezha.trail;

import com.iafenvoy.neptune.util.Color4i;

public interface TrailProvider {
    TrailHolder createTail();

    void updateTrail(TrailHolder effect);

    default TrailHolder.TrailPoint adjustPoint(TrailHolder.TrailPoint point, boolean vertical, float partialTicks) {
        return point;
    }

    Color4i getTrailColor();

    default boolean isTrailFullBright() {
        return false;
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
}
