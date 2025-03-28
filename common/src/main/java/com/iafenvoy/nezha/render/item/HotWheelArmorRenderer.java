package com.iafenvoy.nezha.render.item;

import com.iafenvoy.neptune.render.armor.IArmorRendererBase;
import com.iafenvoy.nezha.NeZha;
import com.iafenvoy.nezha.render.model.HotWheelModel;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.BipedEntityModel;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Identifier;

import java.util.Collections;
import java.util.Map;

public class HotWheelArmorRenderer implements IArmorRendererBase<LivingEntity> {
    @Override
    public BipedEntityModel<LivingEntity> getHumanoidArmorModel(LivingEntity livingEntity, ItemStack itemStack, EquipmentSlot equipmentSlot, BipedEntityModel<LivingEntity> bipedEntityModel) {
        BipedEntityModel<LivingEntity> armorModel = new BipedEntityModel<>(new ModelPart(Collections.emptyList(), Map.of(
                "head", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "hat", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "body", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "right_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "left_arm", new ModelPart(Collections.emptyList(), Collections.emptyMap()),
                "right_leg", HotWheelModel.getTexturedModelData().createModel(),
                "left_leg", HotWheelModel.getTexturedModelData().createModel())));
        armorModel.sneaking = livingEntity.isSneaking();
        armorModel.riding = livingEntity.hasVehicle();
        armorModel.child = livingEntity.isBaby();
        return armorModel;
    }

    @Override
    public Identifier getArmorTexture(ItemStack itemStack, Entity entity, EquipmentSlot equipmentSlot) {
        return Identifier.of(NeZha.MOD_ID, "textures/entity/hot_wheel.png");
    }
}
