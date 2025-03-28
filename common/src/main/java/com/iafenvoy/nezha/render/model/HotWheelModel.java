package com.iafenvoy.nezha.render.model;

import net.minecraft.client.model.*;
import net.minecraft.client.render.VertexConsumer;
import net.minecraft.client.render.entity.model.EntityModel;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.entity.Entity;

public class HotWheelModel extends EntityModel<Entity> {
    private final ModelPart model;

    public HotWheelModel(ModelPart root) {
        this.model = root.getChild("model");
    }

    public static TexturedModelData getTexturedModelData() {
        ModelData modelData = new ModelData();
        ModelPartData modelPartData = modelData.getRoot();
        modelPartData.addChild("model", ModelPartBuilder.create()
                        .uv(0, 0).cuboid(-1.0F, -10.0F, -6.0F, 2.0F, 12.0F, 12.0F, new Dilation(0.0F))
                        .uv(18, 1).cuboid(-1.0F, -8.0F, 4.0F, 2.0F, 8.0F, 0.0F, new Dilation(0.0F))
                        .uv(22, 1).cuboid(-1.0F, -8.0F, -4.0F, 2.0F, 8.0F, 0.0F, new Dilation(0.0F))
                        .uv(14, 1).cuboid(-1.0F, 0.0F, -4.0F, 2.0F, 0.0F, 8.0F, new Dilation(0.0F))
                        .uv(10, 1).cuboid(-1.0F, -8.0F, -4.0F, 2.0F, 0.0F, 8.0F, new Dilation(0.0F)),
                ModelTransform.pivot(0.0F, 24.0F, 0.0F));
        return TexturedModelData.of(modelData, 32, 32);
    }

    @Override
    public void setAngles(Entity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void render(MatrixStack matrices, VertexConsumer vertexConsumer, int light, int overlay, float red, float green, float blue, float alpha) {
        this.model.render(matrices, vertexConsumer, light, overlay, red, green, blue, alpha);
    }
}