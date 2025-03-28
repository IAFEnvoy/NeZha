package com.iafenvoy.nezha.trail;

import com.iafenvoy.neptune.util.Color4i;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.Vec3d;

import java.util.function.Predicate;

public class EntityTrailProvider<T extends Entity> implements TrailProvider {
    private final T entity;
    private final Color4i color;
    private final float width;
    private final int length;
    private final Vec3d offset;
    private final Predicate<T> removeCondition;

    protected EntityTrailProvider(T entity, Color4i color, float width, int length, Vec3d offset, Predicate<T> removeCondition) {
        this.entity = entity;
        this.color = color;
        this.width = width;
        this.length = length;
        this.offset = offset;
        this.removeCondition = removeCondition;
    }

    @Override
    public TrailHolder createTail() {
        return new TrailHolder(this, this.width, this.length);
    }

    @Override
    public void updateTrail(TrailHolder effect) {
        Vec3d oldPos = new Vec3d(this.entity.prevX, this.entity.prevY, this.entity.prevZ);
        effect.update(oldPos.add(0, this.entity.getHeight() / 2, 0), this.entity.getPos().subtract(oldPos));
        if (this.entity.isRemoved()) effect.setLength(Math.max(effect.getLength() - 0.9f, 0));
    }

    @Override
    public TrailHolder.TrailPoint adjustPoint(TrailHolder.TrailPoint point, boolean vertical, float partialTicks) {
        return point.offset(this.offset);
    }

    @Override
    public Color4i getTrailColor() {
        return this.color;
    }

    @Override
    public boolean shouldRemove() {
        return this.removeCondition.test(this.entity);
    }

    public void apply() {
        TrailManager.addTrail(this.entity, this);
    }

    public static <T extends Entity> Builder<T> builder(T entity) {
        return new Builder<>(entity);
    }

    public static class Builder<T extends Entity> {
        protected final T entity;
        protected Color4i color = new Color4i(160 / 255f, 164 / 255f, 195 / 255f, 1f);
        protected float width = 0.3f;
        protected int length = 7;
        protected Vec3d offset = Vec3d.ZERO;
        protected Predicate<T> removeCondition = e -> true;

        public Builder(T entity) {
            this.entity = entity;
        }

        public Builder<T> color(Color4i color) {
            this.color = color;
            return this;
        }

        public Builder<T> width(float width) {
            this.width = width;
            return this;
        }

        public Builder<T> length(int length) {
            this.length = length;
            return this;
        }

        public Builder<T> removeCondition(Predicate<T> removeCondition) {
            this.removeCondition = removeCondition;
            return this;
        }

        public Builder<T> offset(Vec3d offset) {
            this.offset = offset;
            return this;
        }

        public EntityTrailProvider<T> build() {
            return new EntityTrailProvider<>(this.entity, this.color, this.width, this.length, this.offset, this.removeCondition);
        }
    }
}
