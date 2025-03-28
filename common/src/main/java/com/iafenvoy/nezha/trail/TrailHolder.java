package com.iafenvoy.nezha.trail;

import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class TrailHolder {
    private final List<TrailPoint> verticalPoints = new LinkedList<>();
    private final List<TrailPoint> horizontalPoints = new LinkedList<>();
    private final List<TrailPoint> verticalRenderPoints = new LinkedList<>();
    private final List<TrailPoint> horizontalRenderPoints = new LinkedList<>();
    private final TrailProvider provider;
    private final float width;
    private float oldLength;
    private float length;

    public TrailHolder(TrailProvider provider, float width, int length) {
        this.provider = provider;
        this.width = width;
        this.length = length;
    }

    public List<TrailPoint> getVerticalRenderPoints() {
        return this.verticalRenderPoints;
    }

    public List<TrailPoint> getHorizontalRenderPoints() {
        return this.horizontalRenderPoints;
    }

    public float getWidth() {
        return this.width;
    }

    public float getLength() {
        return this.length;
    }

    public void setLength(float length) {
        this.length = length;
    }

    private List<TrailPoint> getPoints(boolean vertical) {
        return vertical ? this.verticalPoints : this.horizontalPoints;
    }

    public void update(TrailPoint point, boolean vertical) {
        List<TrailPoint> points = this.getPoints(vertical);
        points.add(0, point);
        if (points.size() > this.provider.getMaxTrailLength())
            points.remove(points.size() - 1);
    }

    public void update(Vec3d pos, Vec3d delta) {
        this.oldLength = this.length;
        float yaw = ESMathUtil.positionToYaw(delta);
        float pitch = ESMathUtil.positionToPitch(delta);
        Vec3d upper = ESMathUtil.rotationToPosition(pos, this.width / 2f, pitch - 90, yaw);
        Vec3d lower = ESMathUtil.rotationToPosition(pos, this.width / 2f, pitch + 90, yaw);
        this.update(new TrailPoint(upper, lower), true);
        Vec3d offset = upper.subtract(lower).crossProduct(delta).normalize().multiply(this.width / 2);
        Vec3d upper1 = pos.add(offset);
        Vec3d lower1 = pos.add(offset.multiply(-1));
        this.update(new TrailPoint(upper1, lower1), false);
    }

    public void prepareRender(Vec3d pos, Vec3d delta, float partialTicks) {
        float yaw = ESMathUtil.positionToYaw(delta);
        float pitch = ESMathUtil.positionToPitch(delta);
        Vec3d upper = ESMathUtil.rotationToPosition(pos, this.width / 2f, pitch - 90, yaw);
        Vec3d lower = ESMathUtil.rotationToPosition(pos, this.width / 2f, pitch + 90, yaw);
        this.verticalRenderPoints.clear();
        this.verticalRenderPoints.addAll(this.verticalPoints);
        this.prepare(new TrailPoint(upper, lower), true, partialTicks);
        Vec3d offset = upper.subtract(lower).crossProduct(delta).normalize().multiply(this.width / 2);
        Vec3d upper1 = pos.add(offset);
        Vec3d lower1 = pos.add(offset.multiply(-1));
        this.horizontalRenderPoints.clear();
        this.horizontalRenderPoints.addAll(this.horizontalPoints);
        this.prepare(new TrailPoint(upper1, lower1), false, partialTicks);
    }

    private void prepare(TrailPoint point, boolean vertical, float partialTicks) {
        List<TrailPoint> points = vertical ? this.verticalRenderPoints : this.horizontalRenderPoints;
        List<TrailPoint> modified = new ArrayList<>();
        points.add(0, point);
        float totalLength = 0;
        float renderLength = MathHelper.lerp(partialTicks, this.oldLength, this.length);
        for (int i = 0; i < points.size() - 1; i++) {
            TrailPoint from = points.get(i);
            TrailPoint to = points.get(i + 1);
            float distance = (float) from.center().distanceTo(to.center());
            totalLength += distance;
            if (totalLength > renderLength) {
                points.set(i + 1, this.interpolateTrailPoint((totalLength - renderLength) / distance, to, from));
                modified.addAll(points.subList(0, i + 2));
                totalLength = renderLength;
                break;
            }
        }
        if (!modified.isEmpty()) {
            points.clear();
            points.addAll(modified);
        }
        float currentLength = 0;
        for (int i = 0; i < points.size() - 1; i++) {
            TrailPoint from = points.get(i);
            TrailPoint to = points.get(i + 1);
            float distance = (float) from.center().distanceTo(to.center());
            points.set(i, points.get(i).withWidth((totalLength - currentLength) * (this.width / totalLength)));
            currentLength += distance;
        }
        if (points.size() > 1) points.set(points.size() - 1, points.get(points.size() - 1).withWidth(0.01f));
    }

    private TrailPoint interpolateTrailPoint(float progress, TrailPoint first, TrailPoint second) {
        return new TrailPoint(ESMathUtil.lerpVec(progress, first.upper(), second.upper()), ESMathUtil.lerpVec(progress, first.lower(), second.lower()));
    }

    public record TrailPoint(Vec3d upper, Vec3d lower) {
        public Vec3d center() {
            return this.lower().add(this.upper().subtract(this.lower()).multiply(0.5));
        }

        public float width() {
            return (float) this.upper().distanceTo(this.lower());
        }

        public TrailPoint withWidth(float width) {
            Vec3d center = this.center();
            Vec3d upperVec = this.upper().subtract(center);
            Vec3d lowerVec = this.lower().subtract(center);
            return new TrailPoint(center.add(upperVec.normalize().multiply(width / 2)), center.add(lowerVec.normalize().multiply(width / 2)));
        }

        public TrailPoint offset(Vec3d vec3d) {
            return new TrailPoint(this.upper.add(vec3d), this.lower.add(vec3d));
        }
    }
}
