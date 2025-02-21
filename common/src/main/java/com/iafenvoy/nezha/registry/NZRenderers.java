package com.iafenvoy.nezha.registry;

import com.iafenvoy.neptune.render.DynamicItemRenderer;
import com.iafenvoy.nezha.render.SkyArrowEntityRenderer;
import com.iafenvoy.nezha.render.SkyShieldItemRenderer;
import dev.architectury.registry.client.level.entity.EntityRendererRegistry;
import dev.architectury.registry.item.ItemPropertiesRegistry;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.util.Identifier;

@Environment(EnvType.CLIENT)
public final class NZRenderers {
    public static void registerEntityRenderers() {
        EntityRendererRegistry.register(NZEntities.SKY_ARROW, SkyArrowEntityRenderer::new);
    }

    public static void registerModelPredicates() {
        ItemPropertiesRegistry.register(NZItems.SKY_BOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "pulling"), (stack, world, entity, seed) -> entity != null && entity.isUsingItem() && entity.getActiveItem() == stack ? 1.0F : 0.0F);
        ItemPropertiesRegistry.register(NZItems.SKY_BOW.get(), Identifier.of(Identifier.DEFAULT_NAMESPACE, "pull"), (stack, world, entity, seed) -> entity == null ? 0.0F : entity.getActiveItem() != stack ? 0.0F : (stack.getMaxUseTime() - entity.getItemUseTimeLeft()) / 60.0F);
    }

    public static void registerBuiltinItemRenderers() {
        DynamicItemRenderer.RENDERERS.put(NZItems.SKY_SHIELD.get(), new SkyShieldItemRenderer());
    }
}
