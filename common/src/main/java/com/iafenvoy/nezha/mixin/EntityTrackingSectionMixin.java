package com.iafenvoy.nezha.mixin;

import com.iafenvoy.nezha.trail.TrailEffect;
import com.iafenvoy.nezha.trail.TrailManager;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.world.entity.EntityLike;
import net.minecraft.world.entity.EntityTrackingSection;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Environment(EnvType.CLIENT)
@Mixin(EntityTrackingSection.class)
public class EntityTrackingSectionMixin<T extends EntityLike> {
    @Inject(method = "add", at = @At("RETURN"))
    private void onClientAddEntity(T entity, CallbackInfo ci) {
        TrailManager.collect(entity).forEach(TrailEffect::create);
    }

    @Inject(method = "remove", at = @At("RETURN"))
    private void onClientRemoveEntity(T entity, CallbackInfoReturnable<Boolean> cir) {
        TrailEffect.removeByUuid(entity.getUuid());
    }
}
