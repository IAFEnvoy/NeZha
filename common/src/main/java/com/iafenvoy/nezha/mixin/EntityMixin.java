package com.iafenvoy.nezha.mixin;

import com.iafenvoy.neptune.accessory.AccessoryManager;
import com.iafenvoy.nezha.registry.NZItems;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.math.Box;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {
    @Shadow
    private Box boundingBox;

    @Inject(method = "getBoundingBox", at = @At("HEAD"), cancellable = true)
    private void handleBondingBox(CallbackInfoReturnable<Box> cir) {
        if ((Object) this instanceof LivingEntity living)
            if (AccessoryManager.getEquipped(living, AccessoryManager.Place.FEET).isOf(NZItems.HOT_WHEEL.get()))
                cir.setReturnValue(this.boundingBox.stretch(0, -1, 0));
    }
}
