package com.iafenvoy.nezha.trail;

import com.iafenvoy.neptune.util.Color4i;
import com.iafenvoy.nezha.NeZha;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.render.*;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import org.joml.Matrix3f;
import org.joml.Matrix4f;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Environment(EnvType.CLIENT)
public class TrailEffect {
    private static final Identifier TRAIL_TEXTURE = Identifier.of(NeZha.MOD_ID, "textures/entity/concentrated_trail.png");
    private static final List<TrailEffect> TRAILS = new ArrayList<>();
    private final Entity entity;
    private final TrailProvider provider;
    private final TrailHolder effect;
    private boolean shouldRemove;

    public TrailEffect(Entity entity, TrailProvider provider) {
        this.entity = entity;
        this.provider = provider;
        this.effect = provider.createTail();
    }

    public static void create(Entity entity, TrailProvider provider) {
        TRAILS.add(new TrailEffect(entity, provider));
    }

    public static void tick(MinecraftClient client) {
        if (client.world != null) {
            List<TrailEffect> forRemove = new ArrayList<>();
            for (TrailEffect effect : TRAILS) {
                if (effect.shouldRemove()) forRemove.add(effect);
                else if (!client.isPaused()) effect.worldTick(client);
            }
            TRAILS.removeAll(forRemove);
            for (Entity entity : client.world.getEntities())
                if (entity instanceof TrailProvider provider && TRAILS.stream().noneMatch(effect1 -> effect1.getEntity().getId() == entity.getId()))
                    TRAILS.add(new TrailEffect(entity, provider));
        } else TRAILS.clear();
    }

    public static void renderAll(VertexConsumerProvider provider, MatrixStack matrices, float tickDelta) {
        Camera camera = MinecraftClient.getInstance().gameRenderer.getCamera();
        Vec3d cameraPos = camera.getPos();
        matrices.push();
        matrices.translate(-cameraPos.x, -cameraPos.y, -cameraPos.z);
        for (TrailEffect effect : TRAILS)
            if (!effect.shouldRemove())
                effect.render(provider, matrices, tickDelta);
        matrices.pop();
    }

    public Entity getEntity() {
        return this.entity;
    }

    public void worldTick(MinecraftClient client) {
        if (client.world == null || this.provider.shouldRemove()) {
            this.shouldRemove = true;
            return;
        }
        boolean shouldRender = false;
        for (Entity toRender : client.world.getEntities())
            if (toRender.getUuid().equals(this.entity.getUuid()))
                shouldRender = true;
        if (!shouldRender && !this.entity.isRemoved()) {
            this.shouldRemove = true;
            return;
        }
        if (this.entity.isRemoved() && this.effect.getLength() <= 0)
            this.shouldRemove = true;
        this.provider.updateTrail(this.effect);
    }

    public void render(VertexConsumerProvider provider, MatrixStack matrices, float tickDelta) {
        boolean entityRemoved = this.entity.isRemoved();
        float x = (float) (entityRemoved ? this.entity.getX() : MathHelper.lerp(tickDelta, this.entity.prevX, this.entity.getX()));
        float y = (float) (entityRemoved ? this.entity.getY() : MathHelper.lerp(tickDelta, this.entity.prevY, this.entity.getY()));
        float z = (float) (entityRemoved ? this.entity.getZ() : MathHelper.lerp(tickDelta, this.entity.prevZ, this.entity.getZ()));
        this.effect.prepareRender(new Vec3d(x, y, z).add(0, this.entity.getHeight() / 2, 0), new Vec3d(this.entity.getX(), this.entity.getY(), this.entity.getZ()).subtract(new Vec3d(this.entity.prevX, this.entity.prevY, this.entity.prevZ)), tickDelta);

        List<TrailHolder.TrailPoint> adjustedVertical = this.effect.getVerticalRenderPoints().stream().map(p -> this.provider.adjustPoint(p, true, tickDelta)).toList();
        this.effect.getVerticalRenderPoints().clear();
        this.effect.getVerticalRenderPoints().addAll(adjustedVertical);
        List<TrailHolder.TrailPoint> adjustedHorizontal = this.effect.getHorizontalRenderPoints().stream().map(p -> this.provider.adjustPoint(p, false, tickDelta)).toList();
        this.effect.getHorizontalRenderPoints().clear();
        this.effect.getHorizontalRenderPoints().addAll(adjustedHorizontal);

        Color4i trailColor = this.provider.getTrailColor();
        int light = this.provider.isTrailFullBright() ? LightmapTextureManager.MAX_LIGHT_COORDINATE : MinecraftClient.getInstance().getEntityRenderDispatcher().getLight(this.entity, tickDelta);
        this.renderTrail(provider, matrices, true, trailColor, light);
        if (this.provider.shouldRenderHorizontal()) this.renderTrail(provider, matrices, false, trailColor, light);
    }

    private void renderTrail(VertexConsumerProvider provider, MatrixStack matrices, boolean vertical, Color4i color, int light) {
        VertexConsumer consumer = provider.getBuffer(Layer.TRANSLUCENT_NO_DEPTH.apply(TRAIL_TEXTURE));
        float r = color.getR();
        float g = color.getG();
        float b = color.getB();
        float a = color.getA();
        List<TrailHolder.TrailPoint> points = vertical ? this.effect.getVerticalRenderPoints() : this.effect.getHorizontalRenderPoints();
        if (points.size() >= 2) {
            for (int i = 0; i < points.size() - 1; i++) {
                TrailHolder.TrailPoint from = points.get(i);
                TrailHolder.TrailPoint to = points.get(i + 1);
                Matrix4f pose = matrices.peek().getPositionMatrix();
                Matrix3f normal = matrices.peek().getNormalMatrix();
                consumer.vertex(pose, (float) from.upper().x, (float) from.upper().y, (float) from.upper().z).color(r, g, b, i == 0 ? 0 : a).texture(0, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0, 1, 0).next();
                consumer.vertex(pose, (float) to.upper().x, (float) to.upper().y, (float) to.upper().z).color(r, g, b, a).texture(1, 0).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0, 1, 0).next();
                consumer.vertex(pose, (float) to.lower().x, (float) to.lower().y, (float) to.lower().z).color(r, g, b, a).texture(1, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0, 1, 0).next();
                consumer.vertex(pose, (float) from.lower().x, (float) from.lower().y, (float) from.lower().z).color(r, g, b, i == 0 ? 0 : a).texture(0, 1).overlay(OverlayTexture.DEFAULT_UV).light(light).normal(normal, 0, 1, 0).next();
            }
        }
    }

    public boolean shouldRemove() {
        return this.shouldRemove;
    }

    public static final class Layer extends RenderLayer {
        public static final Function<Identifier, RenderLayer> TRANSLUCENT_NO_DEPTH = Util.memoize(location ->
                of(NeZha.MOD_ID + ":entity_translucent_no_depth", VertexFormats.POSITION_COLOR_TEXTURE_OVERLAY_LIGHT_NORMAL, VertexFormat.DrawMode.QUADS, TRANSLUCENT_BUFFER_SIZE, true, true, RenderLayer.MultiPhaseParameters.builder()
                        .program(ENTITY_TRANSLUCENT_EMISSIVE_PROGRAM)
                        .texture(new RenderPhase.Texture(location, false, false))
                        .transparency(TRANSLUCENT_TRANSPARENCY)
                        .cull(DISABLE_CULLING)
                        .writeMaskState(COLOR_MASK)
                        .overlay(ENABLE_OVERLAY_COLOR)
                        .build(true)));

        public Layer(String name, VertexFormat vertexFormat, VertexFormat.DrawMode drawMode, int expectedBufferSize, boolean hasCrumbling, boolean translucent, Runnable startAction, Runnable endAction) {
            super(name, vertexFormat, drawMode, expectedBufferSize, hasCrumbling, translucent, startAction, endAction);
        }
    }
}
