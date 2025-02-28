package com.iafenvoy.nezha.render.item;

import com.iafenvoy.neptune.render.DynamicItemRenderer;
import com.iafenvoy.neptune.render.RenderConstants;
import com.iafenvoy.neptune.render.model.CircleShieldModel;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.render.VertexConsumerProvider;
import net.minecraft.client.render.model.json.ModelTransformationMode;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.RotationAxis;

@Environment(EnvType.CLIENT)
public class SkyShieldItemRenderer implements DynamicItemRenderer {
    private final CircleShieldModel<Entity> model;

    public SkyShieldItemRenderer() {
        this.model = new CircleShieldModel<>(CircleShieldModel.getTexturedModelData().createModel());
    }

    @Override
    public void render(ItemStack itemStack, ModelTransformationMode modelTransformationMode, MatrixStack matrixStack, VertexConsumerProvider vertexConsumerProvider, int light, int overlay) {
        matrixStack.push();
        matrixStack.multiply(RotationAxis.POSITIVE_Y.rotationDegrees(180));
        matrixStack.translate(-0.5, 0, 0);
        this.model.render(matrixStack, vertexConsumerProvider.getBuffer(RenderLayer.getEntityTranslucentEmissive(RenderConstants.WHITE_TEXTURE)), light, overlay, 1, 1, 0, 0.2f);
        matrixStack.pop();
    }
}
