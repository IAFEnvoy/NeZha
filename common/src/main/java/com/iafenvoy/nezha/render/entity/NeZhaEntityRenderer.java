package com.iafenvoy.nezha.render.entity;

import com.iafenvoy.neptune.render.CommonPlayerLikeEntityRenderer;
import com.iafenvoy.nezha.entity.NeZhaEntity;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.entity.EntityRendererFactory;
import net.minecraft.client.util.math.MatrixStack;

@Environment(EnvType.CLIENT)
public class NeZhaEntityRenderer extends CommonPlayerLikeEntityRenderer<NeZhaEntity> {
    public NeZhaEntityRenderer(EntityRendererFactory.Context ctx) {
        super(ctx);
    }

    @Override
    public void render(NeZhaEntity mobEntity, float f, float g, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int i) {
        super.render(mobEntity, f, g, matrixStack, vertexConsumerProvider, i);
    }
}
